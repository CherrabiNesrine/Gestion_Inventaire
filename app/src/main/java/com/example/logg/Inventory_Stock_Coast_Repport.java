package com.example.logg;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Inventory_Stock_Coast_Repport extends SidebarMenu {

    ListView list_stock;
    DataBaseM db = new DataBaseM(this);
    ArrayList<String> SousCat = new ArrayList<String>();
    StockAdap adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
TextView DateRap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_inventory__stock__coast__repport, null, false);
        drawer.addView(contentView, 0);
        adapter=new StockAdap(this,SousCat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Stock coast report");
        list_stock=(ListView)findViewById(R.id.list_stock_coast);
        list_stock.setAdapter(adapter);
        Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000' ")};
        while (cursor[0].moveToNext()){
            String sc=cursor[0].getString(5);
            if(!SousCat.contains(sc)) {
                SousCat.add(cursor[0].getString(5));
                adapter.notifyDataSetChanged();
            }
        }
        DateRap=(TextView)findViewById(R.id.DateRapport);
        Date d = new Date();
        DateRap.setText(sdf.format(d));




        //   init();


    }


}
