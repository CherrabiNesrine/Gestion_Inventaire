package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date extends SidebarMenu {
    EditText datef, dateE;
    Button txtNext;
    Produit p = new Produit();
    final Calendar myCalendar1 = Calendar.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    byte[] bytes=new byte[0];
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    BottomNavigationView navigationView;
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
            p.setCategorie(categorie);
            p.setCode(code);
            p.setName(name);
            p.setFactory(factory);

            bytes =intent.getByteArrayExtra("img");
            int i = intent.getIntExtra("Quentite", 0);
            p.setQuantite(i);
            p.setPrice(intent.getDoubleExtra("prix", 0));

        }

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_date, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add product");

        txtNext=(Button) findViewById(R.id.txtnext);
        dateE = (EditText) findViewById(R.id.dateE);
        datef = (EditText) findViewById(R.id.datef);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                java.util.Date Date = new java.util.Date();
                if (CompareDate(myCalendar.getTime(), Date) == 1) {
                    datef.setError("invalide date ");
                } else {
                    datef.setError(null);
                    datef.setText(sdf.format(myCalendar.getTime()));
                    p.setFab(myCalendar.getTime());

                }
            }

        };
        datef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Date.this, date, myCalendar
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
                dateE.setText(sdf.format(myCalendar1.getTime()));

            }

        };


        dateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "you should click on edit text to get the calander ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new DatePickerDialog(Date.this, dateEXP, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //expiration date verification
                if (dateE.getText().toString().isEmpty() ||datef.getText().toString().isEmpty() ){
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Date.this);
                    final View view= LayoutInflater.from(Date.this).inflate(R.layout.ipic_alrt_neg,null);
                    TextView title = (TextView)view.findViewById(R.id.ertitlen);
                    TextView message=(TextView)view.findViewById(R.id.messageern);
                    Button acc=(Button)view.findViewById(R.id.btn_acc);
                    Button nacc=(Button)view.findViewById(R.id.btn_nacc);
                    ImageView img = (ImageView)view.findViewById(R.id.help);
                    title.setText("Empty Fields");
                    message.setText("Please fill all fields");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    dialog.show();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }
                else if(dateE.isActivated()&& dateE.getText().toString().isEmpty()) {
                    dateE.setError("this field can not be blank ! ");
                }
                else if(datef.isActivated()&& datef.getText().toString().isEmpty()) {
                    datef.setError("this field can not be blank ! ");
                }

                else if (CompareDate(myCalendar.getTime(), myCalendar1.getTime()) == 1) {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Date.this);
                    final View view= LayoutInflater.from(Date.this).inflate(R.layout.ipic_alrt_neg,null);
                    TextView title = (TextView)view.findViewById(R.id.ertitlen);
                    TextView message=(TextView)view.findViewById(R.id.messageern);
                    Button acc=(Button)view.findViewById(R.id.btn_acc);
                    Button nacc=(Button)view.findViewById(R.id.btn_nacc);
                    ImageView img = (ImageView)view.findViewById(R.id.help);
                    title.setText("Not Valid");
                    message.setText("Expiration date Not Valid");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    dialog.show();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            dateE.setText(null);
                            datef.setText(null);
                        }
                    });

                    /*
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(Date.this);
                    builder2.setMessage("Expiration date Not Valide ");
                    builder2.setCancelable(true);

                    builder2.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder2.create();
                    alert11.show();
*/
                }

                else {//dates are accepted

                    Intent intent1 = new Intent(Date.this, Rest.class);
                    intent1.putExtra("categorie",p.getCategorie());
                    intent1.putExtra("factory",p.getFactory());
                    intent1.putExtra("Nom", p.getName());
                    intent1.putExtra("Quentite", p.getQuantite());
                    intent1.putExtra("prix",p.getPrice());
                    intent1.putExtra("code", p.getCode());

                    intent1.putExtra("datee",sdf.format(p.getExp()));
                    intent1.putExtra("datef",sdf.format(p.getFab()));
                    intent1.putExtra("img",bytes);
                    startActivity(intent1);
                }

            }
        });
        navigationView=(BottomNavigationView)findViewById(R.id.Bottom_nav) ;
        navigationView.setSelectedItemId(R.id.nav_add);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_view :{ startActivity(new Intent(Date.this,ViewlistP.class));
                        overridePendingTransition(0,0); return true;}

                    case R.id.nav_trash : {startActivity(new Intent(Date.this,Trash.class)); overridePendingTransition(0,0); return true;}
                }
                return false;
            }
        });

    }
    public int CompareDate(java.util.Date d1, java.util.Date d2) {
        /* if d1 apres d2 : return 1
           if d1 avant d2   : return 2
           if d1=d2 : return 0
         */
        if (d1.getYear() > d2.getYear()) {
            return 1;
        } else if (d1.getYear() < d2.getYear()) {
            return 2;
        } else {
            if (d1.getMonth() > d2.getMonth()) {
                return 1;
            } else if (d1.getMonth() < d2.getMonth()) {
                return 2;
            } else {
                if (d1.getDate() > d2.getDate()) {
                    return 1;
                } else if (d1.getDate() < d2.getDate()) {
                    return 2;
                } else {
                    return 0;
                }

            }
        }

    }
}
