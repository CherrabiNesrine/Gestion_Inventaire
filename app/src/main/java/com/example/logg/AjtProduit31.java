package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AjtProduit31 extends SidebarMenu{
    RadioGroup RG;
    RadioButton rbcons, rbncons, rbp, raw;
    Produit p = new Produit();
    private boolean CatIsEmpty = true;
    BottomNavigationView navigationView;
    byte[] bytes=new byte[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//recupriate the pervious page attribute
        if (intent != null) {
            String code = "";
            String factory = "";
            String name = "";
            if (intent.hasExtra("code")) {
                code = intent.getStringExtra("code");

            }
            if (intent.hasExtra("Nom")) {
                name = intent.getStringExtra("Nom");

            }
            if (intent.hasExtra("factory")) {
                factory = intent.getStringExtra("factory");

            }
            bytes =intent.getByteArrayExtra("img");
            p.setCode(code);
            p.setName(name);
            p.setFactory(factory);
            int i = intent.getIntExtra("Quentite", 0);
            p.setQuantite(i);
            p.setPrice(intent.getDoubleExtra("prix", 0));

        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_ajt_produit31, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add a product");


        navigationView=(BottomNavigationView)findViewById(R.id.Bottom_nav) ;
        navigationView.setSelectedItemId(R.id.nav_add);

        rbcons = (RadioButton) findViewById(R.id.rbcons);
        rbncons = (RadioButton) findViewById(R.id.rbncons);
        rbp = (RadioButton) findViewById(R.id.rbp);
        raw = (RadioButton) findViewById(R.id.raw);
        RG = (RadioGroup) findViewById(R.id.RG);






        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i;
                CatIsEmpty = false;
                i = RG.getCheckedRadioButtonId();
                RadioButton catt = (RadioButton) findViewById(i);
                p.setCategorie(catt.getText().toString());


            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_view :{ startActivity(new Intent(AjtProduit31.this,ViewlistP.class));
                        overridePendingTransition(0,0); return true;}

                    case R.id.nav_trash : {startActivity(new Intent(AjtProduit31.this,Trash.class)); overridePendingTransition(0,0); return true;}
                }
                return false;
            }
        });
        rbcons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(AjtProduit31.this);
                  final View view= LayoutInflater.from(AjtProduit31.this).inflate(R.layout.ipic_ask,null);
                  TextView title = (TextView)view.findViewById(R.id.ertitle);
                  TextView message=(TextView)view.findViewById(R.id.messageer);
                  Button acc=(Button)view.findViewById(R.id.btn_acc);
                  Button nacc=(Button)view.findViewById(R.id.btn_nacc);
                  ImageView img = (ImageView)view.findViewById(R.id.help);
                  title.setText("Date Man/Exp");
                  message.setText("have your product an Expiration and Manufacture date ? ");
                  acc.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent intent1 = new Intent(AjtProduit31.this,Date.class);
                          intent1.putExtra("categorie",p.getCategorie());
                          intent1.putExtra("factory",p.getFactory());
                          intent1.putExtra("Nom", p.getName());
                          intent1.putExtra("Quentite", p.getQuantite());
                          intent1.putExtra("prix",p.getPrice());
                          intent1.putExtra("code", p.getCode());

                          intent1.putExtra("img",bytes);
                          startActivity(intent1);
                      }
                  });
                  nacc.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent intent1 = new Intent(AjtProduit31.this,Rest.class);
                          intent1.putExtra("categorie",p.getCategorie());
                          intent1.putExtra("factory",p.getFactory());
                          intent1.putExtra("Nom", p.getName());
                          intent1.putExtra("Quentite", p.getQuantite());
                          intent1.putExtra("prix",p.getPrice());
                          intent1.putExtra("code", p.getCode());
                          intent1.putExtra("img",bytes);
                          startActivity(intent1);

                      }
                  });
                  dateAlt.setView(view);
                  dateAlt.show();

            }
        });
        raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjtProduit31.this,Raw.class);
                intent1.putExtra("categorie",p.getCategorie());
                intent1.putExtra("factory",p.getFactory());
                intent1.putExtra("Nom", p.getName());
                intent1.putExtra("Quentite", p.getQuantite());
                intent1.putExtra("prix",p.getPrice());
                intent1.putExtra("code", p.getCode());
                intent1.putExtra("img",bytes);
                startActivity(intent1);
            }
        });
        rbncons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjtProduit31.this,Rest.class);
                intent1.putExtra("categorie",p.getCategorie());
                intent1.putExtra("factory",p.getFactory());
                intent1.putExtra("Nom", p.getName());
                intent1.putExtra("Quentite", p.getQuantite());
                intent1.putExtra("prix",p.getPrice());
                intent1.putExtra("code", p.getCode());
                intent1.putExtra("img",bytes);
                startActivity(intent1);
            }
        });
        rbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AjtProduit31.this,Rest.class);
                intent1.putExtra("categorie",p.getCategorie());
                intent1.putExtra("factory",p.getFactory());
                intent1.putExtra("Nom", p.getName());
                intent1.putExtra("Quentite", p.getQuantite());
                intent1.putExtra("prix",p.getPrice());
                intent1.putExtra("code", p.getCode());
                intent1.putExtra("img",bytes);
                startActivity(intent1);
            }
        });

    }
}
