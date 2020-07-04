package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.regex.Pattern;


public class Factory extends SidebarMenu {
    EditText entreprise,addres,phone,email;
    Button txtv;
    FactoryE E=new FactoryE();

    BottomNavigationView navigationView;
    Produit p = new Produit();
    byte[] bytes=new byte[0];
    private boolean entrepriseIsEmpty=true;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//recupriate the pervious page attribute
        if (intent != null) {
            String code = "";
            String name = "";
            String mesure = "";
            String coins= "";
            if (intent.hasExtra("code")) {
                code = intent.getStringExtra("code");
                Toast.makeText(getApplicationContext(),code, Toast.LENGTH_LONG).show();
            }
            if (intent.hasExtra("Nom")) {
                name = intent.getStringExtra("Nom");
            }
            if (intent.hasExtra("mesure")) {
                mesure = intent.getStringExtra("mesure");
            }
            if (intent.hasExtra("coins")) {
                coins = intent.getStringExtra("coins");
            }
            p.setCode(code);
            p.setName(name);

            bytes = getIntent().getByteArrayExtra("img");

            int i = intent.getIntExtra("Quentite", 0);
            Toast.makeText(getApplicationContext(),Integer.toString(i),Toast.LENGTH_LONG).show();
            p.setQuantite(i);
            p.setPrice(intent.getDoubleExtra("prix", 0));

        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_factory, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add product");

        txtv=(Button)findViewById(R.id.txtv);
        openHelper=new DataBaseFact(this);
        entreprise=(EditText)findViewById(R.id.entreprise);
        addres=(EditText)findViewById(R.id.address);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_add);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_view :{ startActivity(new Intent(Factory.this,Rapport.class));
                        overridePendingTransition(0,0); return true;}

                    case R.id.nav_trash : {startActivity(new Intent(Factory.this,Ajoutsuc.class)); overridePendingTransition(0,0); return true;}
                }
                return false;
            }
        });

        entreprise.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (entreprise.getText().toString().trim().equalsIgnoreCase("")) {
                    entreprise.setError("This field can not be blank");

                }
                else {
                    entreprise.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (entreprise.getText().toString().trim().equalsIgnoreCase("")) {
                    entreprise.setError("This field can not be blank");
                }
                else {
                    entreprise.setError(null);
                }

            }
        });

        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!entreprise.getText().toString().isEmpty()) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        email.setError("please enter a valid email address");

                    } else {
                        p.setFactory(E.getName());
                        E.setAdress(addres.getText().toString());
                        E.setName(entreprise.getText().toString());
                        E.setEmail(email.getText().toString());
                        E.setPhone(phone.getText().toString());
                        db = openHelper.getWritableDatabase();
                        insertData(E.getName(), E.getEmail(), E.getPhone(), E.getAdress());

                        Intent intent = new Intent(Factory.this, AjtProduit31.class);

                        intent.putExtra("Nom", p.getName());
                        intent.putExtra("Quentite", p.getQuantite());

                        intent.putExtra("prix", p.getPrice());
                        intent.putExtra("code", p.getCode());

                        intent.putExtra("factory", p.getFactory());

                        intent.putExtra("img",bytes);
                        startActivity(intent);
                    }
                }
                else{
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Factory.this);
                    final View view= LayoutInflater.from(Factory.this).inflate(R.layout.ipic_alrt_neg,null);
                    TextView title = (TextView)view.findViewById(R.id.ertitlen);
                    TextView message=(TextView)view.findViewById(R.id.messageern);
                    Button acc=(Button)view.findViewById(R.id.btn_acc);
                    Button nacc=(Button)view.findViewById(R.id.btn_nacc);
                    ImageView img = (ImageView)view.findViewById(R.id.help);
                    title.setText("Empty Field");
                    message.setText("you have to fill at least factory field .");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            }
        });

    }

    public void insertData(String entreprise,String email,String phone,String address) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseFact.col_1, entreprise);
        contentValues.put(DataBaseFact.col_2, email);
        contentValues.put(DataBaseFact.col_3, phone);
        contentValues.put(DataBaseFact.col_4, address);
        long id = db.insert(DataBaseFact.Table_name_F, null, contentValues);
        Toast.makeText(getApplicationContext(), "succsed", Toast.LENGTH_LONG).show();

    }

}
