package com.strictlygains;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.strictlygains.ui.login.LoginActivity;
import com.strictlygains.ui.main.SectionsPagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TabLayout tabs;

    FirebaseAuth fAuth;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fAuth = FirebaseAuth.getInstance();
        /*
        // menu drawer (login/logout options)
        Menu menuNav = navigationView.getMenu();

        MenuItem loginItem = menuNav.findItem(R.id.nav_login);
        MenuItem logoutItem = menuNav.findItem(R.id.nav_logout);
*/


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // invert
        ViewCompat.setLayoutDirection(tabs, ViewCompat.LAYOUT_DIRECTION_LTR);
        ViewCompat.setLayoutDirection(viewPager, ViewCompat.LAYOUT_DIRECTION_LTR);

        // copy the defaultExercises to local files if it does not yet exist
        if(!new File(getFilesDir(), "defaultExercises").exists())
            DataHelper.saveDefaultExercises(this, Objects.requireNonNull(DataHelper.loadExercises(this)));

        // Initialize exerciseHistory file with default workouts
        if (DataHelper.loadExercises(this, "exerciseHistory.json") == null) {
            ArrayList<Exercise> exerciseList = DataHelper.loadExercises(this);
            assert exerciseList != null;
            DataHelper.updateExerciseHistory(this, exerciseList);
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        switch(id) {
            case R.id.nav_logout:
                fAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }

        return false;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
*/

}