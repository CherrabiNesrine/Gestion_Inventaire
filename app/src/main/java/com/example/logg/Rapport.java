package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Rapport extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport);
        navigationView=(BottomNavigationView)findViewById(R.id.Bottom_nav) ;
        navigationView.setSelectedItemId(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_trash:{ startActivity(new Intent(Rapport.this,Ajoutsuc.class));
                        overridePendingTransition(0,0); return true;}

                    case R.id.nav_add : {startActivity(new Intent(Rapport.this,AjtProduit.class)); overridePendingTransition(0,0); return true;}
                }
                return false;
            }
        });
    }
}
