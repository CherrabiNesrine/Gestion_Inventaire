package com.example.logg;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InventoryMain extends SidebarMenu {
LinearLayout creat,valuation,reports,status,statistics;
    String duration="12" ;
    String dtinv="01/01/2000";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date nvDate = new Date();
    SimpleDateFormat sd = new SimpleDateFormat("YY");
    String SnvDate = sd.format(nvDate);
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
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_inventory_main, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inventory main ");


        valuation=(LinearLayout)findViewById(R.id.invVal);
        reports=(LinearLayout)findViewById(R.id.invRP);
        statistics=(LinearLayout)findViewById(R.id.stat);



        valuation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"inventory valuation ",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(InventoryMain.this,InventoryEvaluation.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"inventory Reports ",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(InventoryMain.this,InventoryActivity.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext()," inventory statistics ",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(InventoryMain.this,statisticsList.class);
                intent.putExtra("Date", dtinv);
                intent.putExtra("duration", duration);
                startActivity(intent);
            }
        });
    }

}
