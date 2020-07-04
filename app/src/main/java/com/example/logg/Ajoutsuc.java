package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Ajoutsuc extends SidebarMenu {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajoutsuc);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_ajoutsuc, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("About");

        navigationView=(BottomNavigationView)findViewById(R.id.Bottom_nav) ;
        navigationView.setSelectedItemId(R.id.nav_add);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_view :{ startActivity(new Intent(Ajoutsuc.this,ViewlistP.class));
                        overridePendingTransition(0,0); return true;}

                    case R.id.nav_trash : {startActivity(new Intent(Ajoutsuc.this,Rapport.class)); overridePendingTransition(0,0); return true;}
                }
                return false;
            }
        });

    }
}
