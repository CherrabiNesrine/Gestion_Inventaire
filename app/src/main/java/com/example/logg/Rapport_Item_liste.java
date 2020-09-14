package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Rapport_Item_liste extends SidebarMenu {

    TextView DateRap;
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_rapport__item_liste, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product's list  ");
        DateRap=(TextView)findViewById(R.id.DateRapport);
        Date d = new Date();
        DateRap.setText("Date:"+ sdf.format(d));
        init();
       // startLoadData();
    }

    public void init() {
        db.QueryData();
        TableLayout stk = (TableLayout) findViewById(R.id.tableItem);
        stk.removeAllViews();
        TableRow tbrow0 = new TableRow(this);
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        TextView tv0 = new TextView(this);
        tv0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv0.setText("code");
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundColor(Color.parseColor("#ffffff"));
        tv0.setBackgroundResource(R.drawable.descrip);
        tv0.setPadding(20, 20, 20, 20);
        tv0.setGravity(Gravity.CENTER);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv1.setText(" Name ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tv1.setBackgroundResource(R.drawable.descrip);

        tv1.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv2.setText(" description ");
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(R.drawable.descrip);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv3.setText(" Location ");
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tv3.setBackgroundColor(Color.parseColor("#333333"));
        tv3.setBackgroundResource(R.drawable.descrip);
        tv3.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv4.setText(" entry ");
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tv4.setBackgroundResource(R.drawable.descrip);

        tv4.setGravity(Gravity.CENTER);
        tv4.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv5.setText("Quantity ");
        tv5.setTextColor(Color.WHITE);
        tv5.setPadding(20, 20, 20, 20);
        tv5.setBackgroundColor(Color.parseColor("#ffffff"));
        tv5.setBackgroundResource(R.drawable.descrip);

        tv5.setGravity(Gravity.CENTER);
        tbrow0.addView(tv5);

        stk.addView(tbrow0);
        Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000'")};
        while (cursor[0].moveToNext()) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);

            t1v.setText(cursor[0].getString(0));
            t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            t1v.setTextColor(Color.parseColor("#d3d3d3"));
            t1v.setBackgroundColor(Color.parseColor("#ffffff"));
            t1v.setPadding(20, 20, 20, 20);
            t1v.setBackgroundResource(R.drawable.descrip);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            t2v.setText(cursor[0].getString(1));
            t2v.setBackgroundResource(R.drawable.descrip);

            t2v.setTextColor(Color.parseColor("#d3d3d3"));
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(20, 20, 20, 20);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(cursor[0].getString(15));
            t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            t3v.setTextColor(Color.parseColor("#d3d3d3"));
            t3v.setBackgroundColor(Color.parseColor("#ffffff"));
            t3v.setBackgroundResource(R.drawable.descrip);
            t3v.setGravity(Gravity.CENTER);
            t3v.setPadding(20, 20, 20, 20);

            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(cursor[0].getString(16));
            t4v.setTextColor(Color.parseColor("#d3d3d3"));
            t4v.setBackgroundColor(Color.parseColor("#f5f5f5"));
            t4v.setBackgroundResource(R.drawable.descrip);
            t4v.setPadding(20, 20, 20, 20);

            t4v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setBackgroundColor(Color.parseColor("#ffffff"));
            t5v.setText(cursor[0].getString(10));
            t5v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            t5v.setTextColor(Color.parseColor("#d3d3d3"));
            t5v.setGravity(Gravity.CENTER);
            t5v.setBackgroundResource(R.drawable.descrip);
            t5v.setPadding(20, 20, 20, 20);

            tbrow.addView(t5v);
            TextView t6v = new TextView(this);
            t6v.setText(cursor[0].getString(7));
            t6v.setPadding(20, 20, 20, 20);

            t6v.setTextColor(Color.parseColor("#d3d3d3"));
            t6v.setBackgroundColor(Color.parseColor("#f5f5f5"));
            t6v.setBackgroundResource(R.drawable.descrip);
            t6v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);
            stk.addView(tbrow);
        }
    }


}
