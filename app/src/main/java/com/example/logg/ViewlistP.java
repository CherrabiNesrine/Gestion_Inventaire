package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ViewlistP extends SidebarMenu{
    GridView gridView;
    ArrayList<Produit> prdcts;
    static GridAdapter adpter = null;
    DataBaseHalperP db;
    ArrayList<String> NOM=new ArrayList<>();
    ArrayList<String> CODE=new ArrayList<>();
    ArrayList<byte[]> Images=new ArrayList<>();
    Produit p = new Produit();
    BottomNavigationView navigationView;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_viewlist_p, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product's list");
        db = new DataBaseHalperP(this);
        gridView = (GridView) findViewById(R.id.gridview);
        prdcts = new ArrayList<>();
        txt=(TextView)findViewById(R.id.tvv);

        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_add: {
                        startActivity(new Intent(ViewlistP.this, AjtProduit.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.nav_trash: {
                        startActivity(new Intent(ViewlistP.this, Trash.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });


        adpter = new GridAdapter(this, NOM ,CODE ,Images);
        gridView.setAdapter(adpter);
        db.QueryData();
        Cursor cursor = db.getData("SELECT * FROM prod ");
        prdcts.clear();
            while (cursor.moveToNext()) {
                String code = cursor.getString(0);
                String nom = cursor.getString(1);
                byte[] image = cursor.getBlob(11);

                NOM.add(nom);
                CODE.add(code);
                Images.add(image);
                if (NOM.size()!=0){
                 txt.setText("PRODUCT'S LIST "); }
                else{
                    txt.setVisibility(View.VISIBLE);
                    txt.setText("Nothing to show ");
                }
            }
         adpter.notifyDataSetChanged();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewlistP.this,Miseajour.class);
                intent.putExtra("Nom", NOM.get(position));
                intent.putExtra("code",CODE.get(position));
                intent.putExtra("img",Images.get(position));
                startActivity(intent);
            }
        });
    }
   /* private class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return prdcts.size();
        }

        @Override
        public Object getItem(int i) {
            return prdcts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.grid,null);
            //getting view in row_data
            TextView namee = view1.findViewById(R.id.tnom);
            TextView codee = view1.findViewById(R.id.tcode);
            ImageView image = view1.findViewById(R.id.imv);

            namee.setText(prdcts.get(i).getName());
            codee.setText(prdcts.get(i).getCode());
            byte[] proding =prdcts.get(i).getImage();

            return view1;



        }
    }*/
}