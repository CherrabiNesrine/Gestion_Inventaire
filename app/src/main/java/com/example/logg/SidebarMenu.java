package com.example.logg;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import static com.example.logg.LoginActivity.Id;



public class SidebarMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    DrawerLayout drawer;String myname="",myprenom="";DataBaseM db;byte[] profileimg=null;
    SQLiteDatabase sqLiteDatabaseObj;
    TextView nameheader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nameheader=(TextView)findViewById(R.id.headername);
        Cursor cursor;
        db = new DataBaseM(this);
        db.QueryData();
        sqLiteDatabaseObj = db.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(DataBaseM.TABLE_NAME, null, " " + DataBaseM.Table_Column_ID + "=?", new String[]{Id}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                myname = cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_1_Name));
                myprenom=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_2_Prenom));
                profileimg = cursor.getBlob(cursor.getColumnIndex(DataBaseM.KEY_IMG));
            }
        }


        findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                else
                    drawer.openDrawer(GravityCompat.START);
            }
        });



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.headername);navUsername.setText(myname+" "+myprenom);
        ImageButton photo= (ImageButton) headerView.findViewById(R.id.img);
        Intent i = getIntent();
        if(profileimg !=null || i.hasExtra("image"))
        {photo.setImageBitmap(getImage(profileimg));}

    }



    protected void replaceContentLayout(int sourceId, int destinationId) {
        View contentLayout = findViewById(destinationId);

        ViewGroup parent = (ViewGroup) contentLayout.getParent();
        int index = parent.indexOfChild(contentLayout);

        parent.removeView(contentLayout);
        contentLayout = getLayoutInflater().inflate(sourceId, parent, false);
        parent.addView(contentLayout, index);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.profile) {

            Toast.makeText(getApplicationContext(), "My profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        } else if (id == R.id.Home) {
            Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }else if (id == R.id.Produit) {
            Toast.makeText(getApplicationContext(), "Product", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MagazinActivity.class);
            intent.putExtra("show","show");
            startActivity(intent);
        } else if (id == R.id.Inventaire) {
            Toast.makeText(getApplicationContext(), "Inventory", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InventoryList.class);
            intent.putExtra("check","care");
            startActivity(intent);
        } else if (id == R.id.Magazins) {
            Toast.makeText(getApplicationContext(), "Magazins", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MagazinActivity.class);
            intent.putExtra("check","care");
            startActivity(intent);
        } else if (id == R.id.Apropos) {
            Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AboutActivity.class);
            intent.putExtra("check","care");
            startActivity(intent);

            startActivity(intent);

        }
        else if (id == R.id.logout) {
            Toast.makeText(getApplicationContext(), "See you soon", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.Document) {
            Toast.makeText(getApplicationContext(), "Documents", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Documents.class);
            startActivity(intent);

        }
        else if (id == R.id.fact) {
            Toast.makeText(getApplicationContext(), "Operators", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,factoriesList.class);
            startActivity(intent);

        }
        else if (id == R.id.Expenses) {
            Toast.makeText(getApplicationContext(), "Expenses", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,DepenseMain.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}