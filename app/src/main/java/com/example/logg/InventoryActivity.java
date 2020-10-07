package com.example.logg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class InventoryActivity extends SidebarMenu {
TextView textListItem,stockCoast,  Profisetpertes,depense,Docum,invMovHis;
String dtinv="01/01/2000";
String duration="12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("duration")) {
                duration = intent.getStringExtra("duration");
            }
            if (intent.hasExtra("Date")){
                dtinv=intent.getStringExtra("Date");
            }
        }
        //inflate your activity layout here!
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_inventaire, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inventory");
        textListItem = (TextView)findViewById(R.id.textListItem);
        textListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(InventoryActivity.this,Rapport_Item_liste.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });
        stockCoast=(TextView)findViewById(R.id.textSTockCOast);
        stockCoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,Inventory_Stock_Coast_Repport.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });


                Profisetpertes=(TextView)findViewById(R.id.Profisetpertes);
        Profisetpertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,ProfitsEtPertes.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });

        depense=(TextView)findViewById(R.id.depe);
        depense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,ExpensesReport.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });
        Docum=(TextView)findViewById(R.id.DocRap);
        Docum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,DocumentReport.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });

       invMovHis =(TextView)findViewById(R.id.InvHisRap);
        invMovHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,InventoryMain.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });
    }
}
