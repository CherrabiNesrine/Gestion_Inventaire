package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Rapport_Item_liste extends SidebarMenu {

    TextView DateRap;
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
StringBuilder data = new StringBuilder();
    String duration ="1";
    String dtinv="01/01/2000";


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
        View contentView = inflater.inflate(R.layout.activity_rapport__item_liste, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product's list  ");
        data.append("Product's List \n");
        DateRap=(TextView)findViewById(R.id.DateRapport);
        Date d = new Date();
        DateRap.setText("Date:"+ sdf.format(d));
        data.append(DateRap+"\n");


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
        data.append(tv0.getText().toString()+",");
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv1.setText(" Name ");
        data.append(tv1.getText().toString()+",");

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
        data.append(tv2.getText().toString()+",");

        tv2.setBackgroundResource(R.drawable.descrip);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv3.setText(" Location ");
        data.append(tv3.getText().toString()+",");

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
        data.append(tv4.getText().toString()+",");

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
        data.append(tv5.getText().toString()+",");

        tv5.setTextColor(Color.WHITE);
        tv5.setPadding(20, 20, 20, 20);
        tv5.setBackgroundColor(Color.parseColor("#ffffff"));
        tv5.setBackgroundResource(R.drawable.descrip);

        tv5.setGravity(Gravity.CENTER);
        tbrow0.addView(tv5);

        stk.addView(tbrow0);

try {
    Date dateInv = sdf.parse(dtinv);
    int mois = dateInv.getMonth() + 1;
    int dur = Integer.parseInt(duration);
    int debut;

    if (mois - dur <= 0) {
        debut = 0;
    } else {
        debut = mois - dur;
    }


    for (int k = debut + 1; k <= mois; k++) {
        Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000'")};
        while (cursor[0].moveToNext()) {
            Date dbD = sdf.parse(cursor[0].getString(10));
            if (dbD.getMonth() + 1 == k) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);

            t1v.setText(cursor[0].getString(0));
            data.append(t1v.getText().toString() + ",");

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
            data.append(t2v.getText().toString() + ",");

            t2v.setTextColor(Color.parseColor("#d3d3d3"));
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(20, 20, 20, 20);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(cursor[0].getString(15));
            data.append(t3v.getText().toString() + ",");

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
            data.append(t4v.getText().toString() + ",");

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
            data.append(t5v.getText().toString() + ",");

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
            data.append(t6v.getText().toString() + "\n");

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
}
        catch(Exception e){

        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.export, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_export){
            Export();
        }
        return true;
    }
    public void Export(){

        try{
            FileOutputStream out = openFileOutput("Item_List_Report.csv",Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"Item_List_Report.csv");
            Uri paths= FileProvider.getUriForFile(context,"com.example.logg.FileProvider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Item_List_Report");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,paths);
            startActivity(Intent.createChooser(fileIntent,"send email"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(Rapport_Item_liste.this,InventoryActivity.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
