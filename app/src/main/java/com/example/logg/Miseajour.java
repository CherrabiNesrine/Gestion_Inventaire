package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.logg.DataBaseHalperP.DataBase_name;
import static com.example.logg.DataBaseHalperP.Table_name;
import static java.lang.Integer.parseInt;

public class Miseajour extends SidebarMenu {
    byte[] bytes = new byte[0];
    EditText dcode, dnom, dqntt, dprix;
    Button del, edi,minus,plus;
    Button ok;
    ImageView mag;
    EditText acc;
    DataBaseHalperP db;
    String code = "";
    String name = "";
    BottomNavigationView navigationView;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_miseajour, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("update");

        Intent intent = getIntent();
//recupriate the pervious page attribute
        if (intent != null) {

            if (intent.hasExtra("code")) {
                code = intent.getStringExtra("code");

            }
            if (intent.hasExtra("Nom")) {
                name = intent.getStringExtra("Nom");

            }
            bytes = intent.getByteArrayExtra("img");
        }



        dcode = (EditText) findViewById(R.id.dcode);
        dnom = (EditText) findViewById(R.id.dnom);
        dqntt = (EditText) findViewById(R.id.dqntt);
        dprix = (EditText) findViewById(R.id.dprix);
        del = (Button) findViewById(R.id.del);
        edi = (Button) findViewById(R.id.edt);
        minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        mag = (ImageView) findViewById(R.id.mag);
        ok = (Button) findViewById(R.id.ok);
        dcode.setText(code);
        dnom.setText(name);
        db = new DataBaseHalperP(this);
        final Cursor[] cursor = {db.getData("SELECT * FROM prod WHERE ID ="+code)};
        Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();

        while (cursor[0].moveToNext()) {
            int qnt = cursor[0].getInt(8);
            String no= cursor[0].getString(1);
            dnom.setText(no);

            dqntt.setText(Integer.toString(qnt));
            double prix = cursor[0].getDouble(9);
            dprix.setText(Double.toString(prix));

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            mag.setImageBitmap(bitmap);

        }


        edi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dcode.setFocusableInTouchMode(true);

                minus.setClickable(true);
                plus.setClickable(true);
                dnom.setFocusableInTouchMode(true);


                dqntt.setFocusableInTouchMode(true);


               /* dmesure.setActivated(true);
                dmesure.setClickable(true);
                */

                dprix.setFocusableInTouchMode(true);

/*
                dcoins.setActivated(true);
                dcoins.setClickable(true);
              */

                ok.setVisibility(View.VISIBLE);


            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Rapport
                cursor[0] = db.getData("SELECT * FROM prod WHERE ID = " + code);
                if (cursor[0] != null) {
                    if (cursor[0].moveToNext()) {
                        final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Miseajour.this);
                        final View view= LayoutInflater.from(Miseajour.this).inflate(R.layout.camera,null);
                        TextView message=(TextView)view.findViewById(R.id.qntttm);
                        acc=(EditText) view.findViewById(R.id.btn_accqun);
                        Button nacc=(Button)view.findViewById(R.id.btn_okk);
                        message.setText("products exists  ");
                        dateAlt.setView(view);
                        final AlertDialog dialog = dateAlt.create();
                        dialog.show();
                        acc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        nacc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!acc.getText().toString().isEmpty()) {
                                    int nv = parseInt(acc.getText().toString());
                                    int Qntt = cursor[0].getInt(8);
                                    nv = Qntt - nv;
                                    if (nv > 0) {
                                        ContentValues values = new ContentValues();
                                        values.put("quantite", nv);
                                        db.Update(values, "ID=?", new String[]{code});
                                        dqntt.setText(Integer.toString(nv));
                                        dialog.dismiss();

                                    }
                                    else{
                                        final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Miseajour.this);
                                        final View view= LayoutInflater.from(Miseajour.this).inflate(R.layout.ipic_alrt_neg,null);
                                        TextView title = (TextView)view.findViewById(R.id.ertitlen);
                                        TextView message=(TextView)view.findViewById(R.id.messageern);
                                        Button accc=(Button)view.findViewById(R.id.btn_acc);
                                        Button nacc=(Button)view.findViewById(R.id.btn_nacc);
                                        ImageView img = (ImageView)view.findViewById(R.id.help);
                                        title.setText("Error");
                                        message.setText("the quantity can't be negative ");
                                        dateAlt.setView(view);
                                        final AlertDialog dialog2 = dateAlt.create();
                                        accc.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog2.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }
                                }
                                else{
                                    int nv=0;
                                    int Qntt = cursor[0].getInt(8);
                                        nv = Qntt - nv;

                                        ContentValues values = new ContentValues();
                                        values.put("quantite", nv);
                                        db.Update(values, "ID=?", new String[]{code});
                                        dqntt.setText(Integer.toString(nv));
                                        dialog.dismiss();
                                }
                                dialog.dismiss();

                            }
                        });





                    }

                }

            }

        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor[0] = db.getData("SELECT * FROM prod WHERE ID = " + code);
                if (cursor[0] != null) {
                    if (cursor[0].moveToNext()) {
                        final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Miseajour.this);
                        final View view= LayoutInflater.from(Miseajour.this).inflate(R.layout.camera,null);
                        TextView message=(TextView)view.findViewById(R.id.qntttm);
                        acc=(EditText) view.findViewById(R.id.btn_accqun);
                        Button nacc=(Button)view.findViewById(R.id.btn_okk);
                        message.setText("products exists  ");
                        dateAlt.setView(view);
                        final AlertDialog dialog = dateAlt.create();
                        dialog.show();
                        acc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        nacc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!acc.getText().toString().isEmpty()) {
                                    int nv = parseInt(acc.getText().toString());
                                    int Qntt = cursor[0].getInt(8);
                                    nv = nv + Qntt;
                                    ContentValues values = new ContentValues();
                                    values.put("quantite", nv);
                                    db.Update(values, "ID=?", new String[]{code});
                                    dqntt.setText(Integer.toString(nv));
                                    dialog.dismiss();

                                }
                                else{
                                    int nv=0;
                                    int Qntt = cursor[0].getInt(8);
                                    nv = Qntt + nv;

                                    ContentValues values = new ContentValues();
                                    values.put("quantite", nv);
                                    db.Update(values, "ID=?", new String[]{code});
                                    dqntt.setText(Integer.toString(nv));
                                    dialog.dismiss();
                                }
                                dialog.dismiss();

                            }
                        });





                    }

                }

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("ID", dcode.getText().toString());
                values.put("Nom", dnom.getText().toString());
                values.put("quantite", dqntt.getText().toString());
                values.put("prix", dprix.getText().toString());
                db.Update(values, "ID=?", new String[]{code});
                ViewlistP.adpter.notifyDataSetChanged();
                Intent intent = new Intent(Miseajour.this, ViewlistP.class);
                startActivity(intent);

            }
        });
        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_add: {
                        startActivity(new Intent(Miseajour.this, AjtProduit.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.nav_trash: {
                        startActivity(new Intent(Miseajour.this, Trash.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //Trach
                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Miseajour.this);
                final View view = LayoutInflater.from(Miseajour.this).inflate(R.layout.del, null);
                TextView title = (TextView) view.findViewById(R.id.titledel);
                TextView message = (TextView) view.findViewById(R.id.messageerdel);
                Button acc = (Button) view.findViewById(R.id.btn_accc);
                Button nacc = (Button) view.findViewById(R.id.cancel);
                ImageView img = (ImageView) view.findViewById(R.id.help);
                title.setText("");
                message.setText("Do you really want to delete this product ");
                dateAlt.setView(view);
                final AlertDialog dialog = dateAlt.create();
                acc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ////////////////////////////////////////////////////////////////////////////

                        //creation de la table operation
                        String  Operation="operation";
                        db.QueryData("CREATE TABLE IF NOT EXISTS "+Operation+"(ID varchar(13),Date date,operat varchar(1),PRIMARY KEY (ID,DATE),FOREIGN KEY(ID) REFERENCES "+Table_name+"(ID)");
                        Calendar calendar = Calendar.getInstance();
                        String nvOp="INSERT INTO"+Operation+"VALUES"+"("+code+","+sdf.format(calendar.getTime())+")";
                        db.QueryData(nvOp);


                        ////////////////////////////////////////////////////////////////////////////
                        //******************************************************
                        // creat table for trash






                        //*****************************************************
                        db.Delete("ID=? and Nom=?", new String[]{code, name});

                        ViewlistP.adpter.notifyDataSetChanged();

                        Intent intent = new Intent(Miseajour.this, ViewlistP.class);
                        startActivity(intent);
                    }
                });
                nacc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }
    /*
    public void insertData(String ID, String name, String factory, String datef, String datee, String cat, String sous, String matiere, String Qunt, String mesuree, String prix, String coins,String discription) {
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
        Toast.makeText(getApplicationContext(), "succsed", Toast.LENGTH_LONG).show();*/
}
