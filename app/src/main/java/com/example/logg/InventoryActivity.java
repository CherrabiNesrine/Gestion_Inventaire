package com.example.logg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class InventoryActivity extends SidebarMenu {
TextView textListItem,stockCoast,  Profisetpertes,depense,Docum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_inventaire, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inventory");
        textListItem = (TextView)findViewById(R.id.textListItem);
        textListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(InventoryActivity.this,Rapport_Item_liste.class);
                startActivity(intent);
            }
        });
        stockCoast=(TextView)findViewById(R.id.textSTockCOast);
        stockCoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,Inventory_Stock_Coast_Repport.class);
                startActivity(intent);
            }
        });


                Profisetpertes=(TextView)findViewById(R.id.Profisetpertes);
        Profisetpertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,ProfitsEtPertes.class);
                startActivity(intent);
            }
        });

        depense=(TextView)findViewById(R.id.depe);
        depense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,ExpensesReport.class);
                startActivity(intent);
            }
        });
        Docum=(TextView)findViewById(R.id.DocRap);
        Docum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this,DocumentReport.class);
                startActivity(intent);
            }
        });
    }
}
