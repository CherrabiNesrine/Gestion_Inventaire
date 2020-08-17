
package com.example.logg;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.layout.simple_list_item_1;
import static com.example.logg.DataBaseM.Table_name;
import static com.example.logg.ajtproduit2.CompareDate;
import static java.lang.Integer.parseInt;

public class Miseajour extends SidebarMenu {
    byte[] bytes = new byte[0];
    EditText dnom, dqntt, dprix, desc, prixS, customer, supplier, Exp, FAB, qttmin,ENT,typP;
    TextView more,dcode,goback;
    LinearLayout lin,lin2;
    TextInputLayout textInputLayout2;
    LinearLayout lay1,lay2,lay3;
    boolean operation=false;
    boolean operationM=false;
    Produit p = new Produit();
    AutoCompleteTextView Unit, coins, scoins, Categorie, Scategorie;
    final Calendar myCalendar1 = Calendar.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Button del, edi,minus,plus;
    Button ok,op1,op2,op3;
    ImageView mag;
    EditText acc;
    DataBaseM db;
    String code = "";
    String nomm;
    String name = "";
    BottomNavigationView navigationView;
    final int REQUEST_CODE_GALLERY = 999;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
            if (intent.hasExtra("Nomm")) {
                name = intent.getStringExtra("Nomm");

            }
            bytes = intent.getByteArrayExtra("img");
            if (intent.hasExtra("Nom")) {
                nomm = intent.getStringExtra("Nom");

            }
        }






            //recupriate the pervious page attribute







        desc=(EditText)findViewById(R.id.dsc);
        typP=(EditText)findViewById(R.id.typPr);
        dcode = (TextView) findViewById(R.id.dcode);
        goback=(TextView)findViewById(R.id.goback);

        dnom = (EditText) findViewById(R.id.dnom);
        dqntt = (EditText) findViewById(R.id.dqntt);
        dprix = (EditText) findViewById(R.id.dprix);
        prixS = (EditText) findViewById(R.id.prixSM);
        customer= (EditText) findViewById(R.id.customerM);
        supplier= (EditText) findViewById(R.id.supplierM);
        Exp= (EditText) findViewById(R.id.EXPM);
        FAB= (EditText) findViewById(R.id.FabM);
        ENT=(EditText) findViewById(R.id.ENT);
        qttmin=(EditText)findViewById(R.id.qttminM);
        lay1=(LinearLayout)findViewById(R.id.layyy1M);
        lin2=(LinearLayout)findViewById(R.id.lin2);
        lay2=(LinearLayout)findViewById(R.id.layyy2M);
        lay3=(LinearLayout)findViewById(R.id.layyy3M);
        Unit=(AutoCompleteTextView)findViewById(R.id.unitM);
        coins=(AutoCompleteTextView)findViewById(R.id.coinsM);
        scoins=(AutoCompleteTextView)findViewById(R.id.ScoinsM);
        Categorie=(AutoCompleteTextView)findViewById(R.id.categorieM);
        Scategorie=(AutoCompleteTextView)findViewById(R.id.SouscategorieM);


        del = (Button) findViewById(R.id.del);
        edi = (Button) findViewById(R.id.edt);
        op1 = (Button) findViewById(R.id.op1M);
        op2 = (Button) findViewById(R.id.op2M);
        op3 = (Button) findViewById(R.id.op3M);
        more=(TextView) findViewById(R.id.more);
        lin=(LinearLayout)findViewById(R.id.Lin);
       minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        mag = (ImageView) findViewById(R.id.mag);
        ok = (Button) findViewById(R.id.ok);
        dcode.setText(code);
        dnom.setText(name);
        db = new DataBaseM(this);
        final Cursor[] cursor = {db.getData("SELECT * FROM prod WHERE ID ="+code)};
        Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();

        while (cursor[0].moveToNext()) {
            int qnt = cursor[0].getInt(7);
            String no= cursor[0].getString(1);
            String descrip = cursor[0].getString(15);
            String Coins = cursor[0].getString(11);
            String Scoins= cursor[0].getString(12);
            String unit = cursor[0].getString(13);
            String prixs= cursor[0].getString(14);
            String fournisseur = cursor[0].getString(17);
            String client= cursor[0].getString(18);
            String dateEntr= cursor[0].getString(10);
            String cat= cursor[0].getString(4);
            String scat= cursor[0].getString(5);
            String exp= cursor[0].getString(3);
            String fab= cursor[0].getString(2);
            String typPr= cursor[0].getString(9);
            String min =cursor[0].getString(19);
            byte[] bytess = cursor[0].getBlob(21);

            if(!exp.equals("01/01/2000")&& !fab.equals("01/01/2000")){
             lin2.setVisibility(View.VISIBLE);
             Exp.setText(exp);
             FAB.setText(fab);
            }
            coins.setText(Coins);
            dnom.setText(no);
            desc.setText(descrip);
            Unit.setText(unit);
            scoins.setText(Scoins);
            prixS.setText(prixs);
            supplier.setText(fournisseur);
            ENT.setText(dateEntr);
            typP.setText(typPr);
            qttmin.setText(min);
            customer.setText(client);
            dqntt.setText(Integer.toString(qnt));
            double prix = cursor[0].getDouble(8);
            dprix.setText(Double.toString(prix));
            Categorie.setText(cat);

            Scategorie.setText(scat);
           // Bitmap bitmap= ImageViewHalper.ImageFromDrawable(Miseajour.this,bytes);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytess, 0, bytess.length);
            bitmap=ImageViewHalper.ImageFromDrawable(Miseajour.this,bitmap);

            mag.setImageBitmap(bitmap);

        }

        mag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Miseajour.this);
                final View view = LayoutInflater.from(Miseajour.this).inflate(R.layout.ipic_ask, null);
                TextView title = (TextView) view.findViewById(R.id.ertitle);
                TextView message = (TextView) view.findViewById(R.id.messageer);
                Button acc = (Button) view.findViewById(R.id.btn_acc);
                Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                ImageView img = (ImageView) view.findViewById(R.id.help);
                title.setText("OPEN");
                img.setImageResource(R.drawable.ic_action_cameraa);
                message.setText("do you want to open camera or gallery  ");
                dateAlt.setView(view);
                acc.setText("CAMERA");
                nacc.setText("GALLERY ");
                final AlertDialog dialog = dateAlt.create();
                acc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(Miseajour.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Miseajour.this, new String[]{
                                    Manifest.permission.CAMERA}, 100);

                        }
                        dialog.dismiss();

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 100);

                    }
                });
                nacc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(Miseajour.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                    }
                });
                dialog.show();

                return false;
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Miseajour.this,ViewlistP.class);
                intent.putExtra("Nom",nomm);
                startActivity(intent);
            }
        });
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = false;
                if(op1.getText().toString().equals("close")&& b==false){
                    lay1.setVisibility(View.GONE);
                    op1.setText("open");
                    b=true;
                    op1.getBackground().setColorFilter(op1.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);


                }
                if(op1.getText().toString().equals("open")&& b==false){
                    op1.setText("close");
                    lay1.setVisibility(View.VISIBLE);
                    b=true;
                    op1.getBackground().setColorFilter(op1.getContext().getResources().getColor(R.color.colorvertbleu), PorterDuff.Mode.MULTIPLY);


                }


            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = false;
                if(op2.getText().toString().equals("close")&& b==false){
                    lay2.setVisibility(View.GONE);
                    op2.setText("open");
                    b=true;
                    op2.getBackground().setColorFilter(op2.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);


                }
                if(op2.getText().toString().equals("open")&& b==false){
                    lay2.setVisibility(View.VISIBLE);
                    b=true;
                    op2.setText("close");
                    op2.getBackground().setColorFilter(op2.getContext().getResources().getColor(R.color.colorvertbleu), PorterDuff.Mode.MULTIPLY);

                }


            }
        });

        op3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                boolean b = false;
                if(op3.getText().toString().equals("close")&& b==false){
                    lay3.setVisibility(View.GONE);
                    op3.setText("open");
                    b=true;
                    op3.getBackground().setColorFilter(op3.getContext().getResources().getColor(R.color.colorvertbleu), PorterDuff.Mode.MULTIPLY);

                }
                if(op3.getText().toString().equals("open")&& b==false){
                    lay3.setVisibility(View.VISIBLE);
                    b=true;
                    op3.setText("close");
                    op3.getBackground().setColorFilter(op3.getContext().getResources().getColor(R.color.colorvertbleu), PorterDuff.Mode.MULTIPLY);

                }


            }
        });


        edi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dnom.setEnabled(true);
                dprix.setEnabled(true);
                prixS.setEnabled(true);
                customer.setEnabled(true);
                supplier.setEnabled(true);
                mag.setClickable(true);
                Date d = new Date();
                p.setDateModif(d);

                if(lin2.getVisibility()==View.VISIBLE){
                    Exp.setFocusableInTouchMode(true);
                    FAB.setFocusableInTouchMode(true);
                    final DatePickerDialog.OnDateSetListener datep = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            java.util.Date Date = new java.util.Date();
                            if (CompareDate(myCalendar.getTime(), Date) == 1) {
                                FAB.setError("invalide date ");
                            } else {
                                FAB.setError(null);
                                FAB.setText(sdf.format(myCalendar.getTime()));
                                p.setFab(myCalendar.getTime());

                            }
                        }

                    };
                    FAB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(  Miseajour.this, datep, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        }
                    });

                    final DatePickerDialog.OnDateSetListener dateEXP = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar1.set(Calendar.YEAR, year);
                            myCalendar1.set(Calendar.MONTH, monthOfYear);
                            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            p.setExp(myCalendar1.getTime());
                            Exp.setText(sdf.format(myCalendar1.getTime()));

                        }

                    };


                    Exp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(v, "you should click on edit text to get the calander ", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            new DatePickerDialog(Miseajour.this, dateEXP, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });



                }
                Scategorie.setEnabled(true);
                Scategorie.setFocusableInTouchMode(true);
                String cat = Categorie.getText().toString();
                if (cat != null) {
                    if (cat.equals("Consumable")) {

                        ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.listCon, simple_list_item_1);
                        dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        Scategorie.setThreshold(1);
                        Scategorie.setAdapter(dataAdapter5);
                        Scategorie.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                Scategorie.showDropDown();
                                return false;
                            }
                        });


                    } else if (cat.equals("Not consumable")) {
                        ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.listNcons, simple_list_item_1);
                        dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        Scategorie.setThreshold(1);
                        Scategorie.setAdapter(dataAdapter5);
                    } else if (cat.equals("Spare parts")) {
                        ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpareLit, simple_list_item_1);
                        dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        Scategorie.setThreshold(1);
                        Scategorie.setAdapter(dataAdapter5);
                    } else if (cat.equals("Raw materials")) {

                    } else {
                        ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpareLit, simple_list_item_1);
                        dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        Scategorie.setThreshold(1);
                        Scategorie.setAdapter(dataAdapter5);
                    }
                } else {
                    ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpareLit, simple_list_item_1);
                    dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    Scategorie.setThreshold(1);
                    Scategorie.setAdapter(dataAdapter5);
                    Scategorie.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Scategorie.showDropDown();
                            return false;
                        }
                    });
                }
                ArrayAdapter<CharSequence> dataAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.coins, simple_list_item_1);
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                coins.setAdapter(dataAdapter2);
                coins.setThreshold(1);
                coins.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        coins.showDropDown();
                        return false;
                    }
                });

                //-----------------scoins(sale coins)
                ArrayAdapter<CharSequence> dataAdapter3 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.coins, simple_list_item_1);
                dataAdapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                scoins.setAdapter(dataAdapter3);
                scoins.setThreshold(1);
                scoins.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        scoins.showDropDown();
                        return false;
                    }
                });

                dnom.setEnabled(true);
                minus.setClickable(true);
                plus.setClickable(true);
                dnom.setFocusableInTouchMode(true);
                dqntt.setFocusableInTouchMode(true);
                dprix.setFocusableInTouchMode(true);
                prixS.setFocusableInTouchMode(true);
                customer.setFocusableInTouchMode(true);
                supplier.setFocusableInTouchMode(true);

                qttmin.setFocusableInTouchMode(true);
                ok.setVisibility(View.VISIBLE);



            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Rapport
                Date d = new Date();
                p.setDateModif(d);
                operationM=true;
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
                                    int Qntt = cursor[0].getInt(7);
                                    nv = Qntt - nv;
                                    if (nv > 0) {                                        ContentValues values = new ContentValues();
                                        values.put("quantite", nv);
                                        db.Update("prod",values, "ID=?", new String[]{code});
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
                                        db.Update("prod",values, "ID=?", new String[]{code});
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
                operation=true;
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
                                   db.Update("prod",values, "ID=?", new String[]{code});
                                    dqntt.setText(Integer.toString(nv));
                                    dialog.dismiss();

                                }
                                else{
                                    int nv=0;
                                    int Qntt = cursor[0].getInt(8);
                                    nv = Qntt + nv;

                                    ContentValues values = new ContentValues();
                                    values.put("quantite", nv);
                                   db.Update("prod",values, "ID=?", new String[]{code});
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
                values.put("sousCategorie",Scategorie.getText().toString());
                values.put("typePr",typP.getText().toString());
                values.put("pricS",prixS.getText().toString());
                values.put("dateF",FAB.getText().toString());
                values.put("datE",Exp.getText().toString());
                db.Update("prod",values, "ID=?", new String[]{code});
                if(operation==true && operationM == true){
                    db.InsertDataHistorique(code,Integer.parseInt(dqntt.getText().toString()),p.getDateModif(),"+");
                    db.InsertDataHistorique(code,Integer.parseInt(dqntt.getText().toString()),p.getDateModif(),"-");
                }
                if(operationM==true && operation==false){
                    db.InsertDataHistorique(code,Integer.parseInt(dqntt.getText().toString()),p.getDateModif(),"-");
                }
                if(operationM==false && operation==true){
                    db.InsertDataHistorique(code,Integer.parseInt(dqntt.getText().toString()),p.getDateModif(),"+");
                }
                ViewlistP.adpter.notifyDataSetChanged(); // you will stole this nina
                Intent intent = new Intent(Miseajour.this, ViewlistP.class);
                startActivity(intent);

            }
        });
      /*  navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
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
        });*/
      more.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(more.getText().toString().equals("MORE INFORMATION.. ")){
              lin.setVisibility(View.VISIBLE);
              more.setText("LESS INFORMATION ");}
              else {
                  lin.setVisibility(View.GONE);
                  more.setText("MORE INFORMATION.. ");
              }
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
                        Date d = new Date ();
                        ContentValues values = new ContentValues();
                        values.put("dateDel",sdf.format(d));
                        db.Update("prod",values, "ID=?", new String[]{code});
                        ViewlistP.adpter.notifyDataSetChanged();
                        dialog.dismiss();
                        Intent intent = new Intent(Miseajour.this, ViewlistP.class);
                        intent.putExtra("Nom",nomm);
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = ImageViewHalper.ImageFromDrawable(Miseajour.this,bitmap);
                mag.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap= ImageViewHalper.ImageFromDrawable(Miseajour.this,bitmap);
            mag.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] Imageviewtobyte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}