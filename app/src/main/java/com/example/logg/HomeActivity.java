package com.example.logg;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;


public class HomeActivity extends SidebarMenu {
RelativeLayout magazin ,product,settings,inventory,logout,about,Commande,depense;
static String UserHoldr,PrenomHoldr,NameHoldr,IdHoldr,JobHoldr,Passholdr;
byte[] image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.tryy, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");

        magazin=(RelativeLayout) findViewById(R.id.stores);
        product=(RelativeLayout) findViewById(R.id.product);
        inventory=(RelativeLayout) findViewById(R.id.inventory);
        logout=(RelativeLayout)findViewById(R.id.logout);
        about=(RelativeLayout)findViewById(R.id.about);
        Commande=(RelativeLayout)findViewById(R.id.Commande);
        depense=(RelativeLayout)findViewById(R.id.depense);
        Intent intent = getIntent();
        UserHoldr = intent.getStringExtra("user");
        PrenomHoldr = intent.getStringExtra("prenom");
        NameHoldr = intent.getStringExtra("nom");
        IdHoldr = intent.getStringExtra("id");
        JobHoldr = intent.getStringExtra("job");
        Passholdr = intent.getStringExtra("mdps");



        magazin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeActivity.this,"stores", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(HomeActivity.this, MagazinActivity.class);
                intent.putExtra("check","care");
                startActivity(intent);


            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"Inventories", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this, InventoryList.class));

            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Product", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, MagazinActivity.class);
                intent.putExtra("show","show");
                startActivity(intent);


            }
        });




        depense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Toast.makeText(HomeActivity.this,"Expenses", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(HomeActivity.this, DepenseMain.class));



            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Toast.makeText(HomeActivity.this,"See you soon !", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Toast.makeText(HomeActivity.this,"About us !", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this, AboutActivity.class));

            }
        });






    }



}