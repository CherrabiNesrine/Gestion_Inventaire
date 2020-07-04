package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import static android.R.layout.simple_spinner_item;
import static com.example.logg.DataBaseFact.Table_name_F;

public class Raw extends SidebarMenu {
    Spinner spinner, spr;
    Button add;
    EditText  discription, autre;
    Produit p=new Produit() ;
    BottomNavigationView navigationView;
    byte[] bytes=new byte[0];
    DataBaseHalperP db;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
//recupriate the pervious page attribute
        if (intent != null) {
            String code = "";
            String factory = "";
            String name = "";
            String mesure = "";
            String coins= "";
            String categorie="";
            if (intent.hasExtra("code")) {
                code = intent.getStringExtra("code");
            }
            if (intent.hasExtra("Nom")) {
                name = intent.getStringExtra("Nom");
            }

            if (intent.hasExtra("categorie")) {
                categorie = intent.getStringExtra("categorie");
            }
            if (intent.hasExtra("factory")) {
                factory = intent.getStringExtra("factory");
            }

            bytes =intent.getByteArrayExtra("img");
            p.setCategorie(categorie);

            p.setCode(code);
            p.setName(name);
            p.setFactory(factory);

            int i = intent.getIntExtra("Quentite", 0);
            p.setQuantite(i);
            Toast.makeText(getApplicationContext(),Integer.toString(i),Toast.LENGTH_LONG).show();
            p.setPrice(intent.getDoubleExtra("prix", 0));

        }

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_raw, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Raw Materials products");

        db=new DataBaseHalperP(this);
        navigationView=(BottomNavigationView)findViewById(R.id.Bottom_nav) ;
        navigationView.setSelectedItemId(R.id.nav_add);
        discription = (EditText) findViewById(R.id.discription);
        autre = (EditText) findViewById(R.id.autre);
        spinner = (Spinner) findViewById(R.id.spinner);
        spr = (Spinner) findViewById(R.id.spr);
        add = (Button) findViewById(R.id.add2);
        autre.setVisibility(View.INVISIBLE);
        autre.setText(null);

        discription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (discription.getText().toString().trim().equalsIgnoreCase("")) {
                    discription.setError("This field can not be blank");
                }
                else {
                    discription.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (discription.getText().toString().trim().equalsIgnoreCase("")) {
                    discription.setError("This field can not be blank");
                }
                else {
                    discription.setError(null);
                }
            }
        });
        //programming spinner subCATEGORY
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.listRaw, simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //we found that raw matieriel have 2 sub categories
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
                p.setSous(text);
                //if the selector select Direct materials then we programme the categorie of the product
                if (text.equals("Direct materials")) {
                    //programming the second spinner spr
                    ArrayAdapter<CharSequence> dataAdapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.DirectRaw, simple_spinner_item);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spr.setAdapter(dataAdapter1);
                    spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String text = parent.getItemAtPosition(position).toString();
                            Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
                            if (text.equals("Others")) {
                                autre.setActivated(true);
                                autre.setClickable(true);
                                autre.setCursorVisible(true);
                                autre.setFocusable(true);
                                autre.setVisibility(View.VISIBLE);
                                autre.setFocusableInTouchMode(true);
                                p.setMatiere(autre.getText().toString());
                            } else {
                                p.setMatiere(text);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
                if (text.equals("Indirect materials")) {

                    ArrayAdapter<CharSequence> dataAdapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.InDirectRaw, simple_spinner_item);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spr.setAdapter(dataAdapter1);
                    spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String text = parent.getItemAtPosition(position).toString();
                            Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
                            if (text.equals("Others")) {
                                autre.setActivated(true);
                                autre.setClickable(true);
                                autre.setCursorVisible(true);
                                autre.setFocusable(true);
                                autre.setVisibility(View.VISIBLE);
                                autre.setFocusableInTouchMode(true);
                                discription.setBottom(12);
                                p.setMatiere(autre.getText().toString());
                            } else {
                                p.setMatiere(text);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_view :{ startActivity(new Intent(Raw.this,ViewlistP.class));
                        overridePendingTransition(0,0); return true;}

                    case R.id.nav_trash : {startActivity(new Intent(Raw.this,Trash.class)); overridePendingTransition(0,0); return true;}
                }
                return false;
            }
        });
//programming add2 buttom

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autre.isActivated()&&autre.getText().toString().isEmpty()) {
                    autre.setError("this field can not be blank ! ");
                }

                else {
                    db.QueryData();
                    db.InsertDate(p.getCode(), p.getName(), p.getFactory(),p.getFab(),p.getExp(),p.getCategorie(), p.getSous(), p.getMatiere(), p.getQuantite(),p.getPrice(), p.getDescription(),bytes);
                    Intent intent1 = new Intent(Raw.this, Ajoutsuc.class);
                    startActivity(intent1);
                }

            }
        });

    }
    /*public void insertData(String ID, String name, String factory, String datef, String datee, String cat, String sous, String matiere, String Qunt, String mesuree, String prix, String coins,String discription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHalperP.col_1, ID);
        contentValues.put(DataBaseHalperP.col_2, name);
        contentValues.put(DataBaseHalperP.col_3, factory);
        contentValues.put(DataBaseHalperP.col_4, datef);
        contentValues.put(DataBaseHalperP.col_5, datee);
        contentValues.put(DataBaseHalperP.col_6, cat);
        contentValues.put(DataBaseHalperP.col_7, sous);
        contentValues.put(DataBaseHalperP.col_8, matiere);
        contentValues.put(DataBaseHalperP.col_9, Qunt);
        contentValues.put(DataBaseHalperP.col_10, mesuree);
        contentValues.put(DataBaseHalperP.col_11, prix);
        contentValues.put(DataBaseHalperP.col_12,coins );
        contentValues.put(DataBaseHalperP.col_13, discription);
        long id = db.insert(DataBaseHalperP.Table_name, null, contentValues);
        Toast.makeText(getApplicationContext(), "succsed", Toast.LENGTH_LONG).show();

    }*/
    private byte[] Imageviewtobyte(ImageView image){
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray =stream.toByteArray();
        return byteArray;
    }
}
