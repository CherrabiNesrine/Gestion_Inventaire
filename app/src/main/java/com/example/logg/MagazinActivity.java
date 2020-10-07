package com.example.logg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;


public class MagazinActivity extends SidebarMenu {

    DataBaseM db;
    Magasin mag = new Magasin();
    ListView listView;
    ArrayList<Magasin> MAGZ = new ArrayList<>();
    static magAdap adpter = null;
    ArrayList<String> NOM = new ArrayList<>();
    ArrayList<String> Type = new ArrayList<>();
    ArrayList<String> UNIT = new ArrayList<>();
    String checkk = "";
    String show = "";
    ImageView imv ;
    TextView empt;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_magazins, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("warehouse");

        Intent intent = getIntent();
//recupriate the pervious page attribute
        if (intent != null) {

            if (intent.hasExtra("check")) {
                checkk = intent.getStringExtra("check");

            }
            if (intent.hasExtra("show")) {
                show = intent.getStringExtra("show");
            }

        }

        SharedPreferences preferences = getSharedPreferences("magasin", MODE_MULTI_PROCESS);
        String check = preferences.getString("remember", "");
        final String Name = preferences.getString("name", "");
        final String type = preferences.getString("type", "");
        final String unit = preferences.getString("unit", "");

        db = new DataBaseM(this);
        listView = (ListView) findViewById(R.id.listmag);
        Button magdel = (Button) findViewById(R.id.magdel);
        Button magedi = (Button) findViewById(R.id.magedi);
        adpter = new magAdap(this, NOM, Type, UNIT);
        listView.setAdapter(adpter);
        db.QueryData();

        Cursor cursorl = db.getData("SELECT * FROM mag where nomMAg ='" + Name + "'");

        if (check.equals("true") && checkk.equals("") && (cursorl != null) && (cursorl.getCount() > 0)) {

            Intent intent2 = new Intent(MagazinActivity.this, AjtProduit.class);
            intent2.putExtra("Nom", Name);
            intent2.putExtra("type", type);
            intent2.putExtra("unit", unit);


            startActivity(intent2);

        } else {
            if ((cursorl == null) || (cursorl.getCount() <= 0)) {
                Toast.makeText(getApplicationContext(), "this warehouse does not exist ! please set other one as default"
                        , Toast.LENGTH_LONG).show();
            }

            if (show.equals("show")) {
                final android.app.AlertDialog.Builder magalrt = new android.app.AlertDialog.Builder(MagazinActivity.this);
                final View vmag = LayoutInflater.from(MagazinActivity.this).inflate(R.layout.magw, null);
                TextView message = (TextView) vmag.findViewById(R.id.qntttmag);
                Button nacc = (Button) vmag.findViewById(R.id.btn_okkmag);
                message.setText("please you should first add or select a wherehouse ");
                magalrt.setView(vmag);
                final android.app.AlertDialog dialog = magalrt.create();
                dialog.show();
                nacc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
            }


            Cursor cursor = db.getData("SELECT * FROM mag ");
            MAGZ.clear();
            if (cursor==null || cursor.getCount()<=0){
                imv =(ImageView)findViewById(R.id.empty);
                empt =(TextView)findViewById(R.id.emptyTxt);
                imv.setVisibility(View.VISIBLE);
                empt.setVisibility(View.VISIBLE);
                empt.setText("No wareHouse found ");
                listView.setVisibility(View.GONE);
            }
            else {
                while (cursor.moveToNext()) {
                    String nom = cursor.getString(0);
                    String types = cursor.getString(1);
                    String units = cursor.getString(2);

                    NOM.add(nom);
                    Type.add(types);
                    UNIT.add(units);

                    adpter.notifyDataSetChanged();
                }
            }


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(MagazinActivity.this).create();
                    alertDialog.setTitle("Alert Dialog");
                    alertDialog.setMessage("we will make this werehouse as default where we will add your Products you can change it any time ^_^ !");
                    //alertDialog.setIcon(R.drawable.welcome);
                    final int I = i;
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();

                            SharedPreferences preferences = getSharedPreferences("magasin", MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("remember", "true");
                            editor.putString("name", NOM.get(I));
                            editor.putString("type", Type.get(I));
                            editor.putString("unit", UNIT.get(I));
                            editor.apply();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Not setting as default ", Toast.LENGTH_SHORT).show();

                            SharedPreferences preferences = getSharedPreferences("magasin", MODE_MULTI_PROCESS);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("magasin", "false");
                            editor.apply();


                        }

                    });
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.required);
                        }
                    });


                    alertDialog.show();

                }
            });




        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);

        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            final AlertDialog.Builder addmagg = new AlertDialog.Builder(MagazinActivity.this);
            final View view3 = LayoutInflater.from(MagazinActivity.this).inflate(R.layout.mag, null);
            addmagg.setView(view3);
            final AlertDialog dialog2 = addmagg.create();
            final EditText idMAG = (EditText) view3.findViewById(R.id.nomMAG);
            final AutoCompleteTextView MES = (AutoCompleteTextView) view3.findViewById(R.id.mesure);
            final AutoCompleteTextView TYP = (AutoCompleteTextView) view3.findViewById(R.id.tyP);
            Button kk = (Button) view3.findViewById(R.id.kk);
            final TextInputLayout lay5 = (TextInputLayout) view3.findViewById(R.id.textInputLayout5);
            final TextInputLayout lay6 = (TextInputLayout) view3.findViewById(R.id.lay6);
            final TextInputLayout lay7 = (TextInputLayout) view3.findViewById(R.id.lay7);
            ArrayAdapter<CharSequence> dataAdapter4 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.categorie, simple_list_item_1);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            TYP.setThreshold(0);
            TYP.setAdapter(dataAdapter4);
            TYP.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    TYP.showDropDown();
                    return false;
                }
            });

            ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.unit, simple_list_item_1);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            MES.setAdapter(dataAdapter);
            MES.setThreshold(1);
            MES.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    MES.showDropDown();
                    return false;
                }
            });


            dialog2.show();

            idMAG.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (idMAG.getText().toString().trim().equalsIgnoreCase("")) {

                        lay5.setError("This field can not be blank");

                    } else {
                        lay5.setError(null);
                    }

                }
            });
            TYP.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (TYP.getText().toString().trim().equalsIgnoreCase("")) {

                        lay6.setError("This field can not be blank");

                    } else {
                        lay6.setError(null);
                    }

                }
            });
            ArrayAdapter<CharSequence> dataAdapterM = ArrayAdapter.createFromResource(getApplicationContext(), R.array.unit, simple_list_item_1);
            dataAdapterM.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            MES.setThreshold(0);
            MES.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    MES.showDropDown();
                    return false;
                }
            });
            MES.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (MES.getText().toString().trim().equalsIgnoreCase("")) {

                        lay7.setError("This field can not be blank");

                    } else {
                        lay7.setError(null);
                    }

                }
            });

            kk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (idMAG.getText().toString().isEmpty()) {
                        lay5.setError("this filed can not be blank !");
                    } else if (MES.getText().toString().isEmpty()) {
                        lay6.setError("this filed can not be blank !");
                    } else if (TYP.getText().toString().isEmpty()) {
                        lay7.setError("this field can not be blank !");
                    } else if( !idMAG.getText().toString().isEmpty() && !MES.getText().toString().isEmpty() && !TYP.getText().toString().isEmpty()) {
                        mag.setNomMag(idMAG.getText().toString());
                        mag.setTypMag(TYP.getText().toString());
                        mag.setUnitMag(MES.getText().toString());
                        db.QueryData();
                        String nom = mag.getNomMag();
                        NOM.add(nom);
                        String type = mag.getTypMag();
                        Type.add(type);
                        String unit = mag.getUnitMag();
                        UNIT.add(unit);
                        try{
                        db.InsertDataMag(nom, type, unit);
                        adpter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "successed!", Toast.LENGTH_LONG);}
                        catch (Exception e){
                            Toast.makeText(getApplicationContext()," this name of warehouse already exist",Toast.LENGTH_LONG).show();
                        }




                        dialog2.dismiss();
                    }
                }
            });
        }
        return true;
    }
}