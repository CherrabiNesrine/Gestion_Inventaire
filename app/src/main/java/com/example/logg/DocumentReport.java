package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;

public class DocumentReport extends SidebarMenu {
    DataBaseM db = new DataBaseM(this);
    ArrayList<String> Magasin = new ArrayList<String>();
    DocAdap adapter;
    ListView list_stock;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    TextView DateRap;
    ArrayList<String>Header=new ArrayList<String>();
    ArrayList<String>Rows=new ArrayList<String>();
    StringBuilder data = new StringBuilder();
    TextView Dateinventaire;
    String duration ="1";
    String dtinv="01/01/2000";
    @SuppressLint("SdCardPath")
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
        View contentView = inflater.inflate(R.layout.activity_document_report, null, false);
        drawer.addView(contentView, 0);
        adapter = new DocAdap(this, Magasin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Expenses Report ");
        list_stock = (ListView) findViewById(R.id.list_stock_coast);
        list_stock.setAdapter(adapter);
        Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000'")};
        while (cursor[0].moveToNext()) {
            String sc1 = cursor[0].getString(16);
            if (!Magasin.contains(sc1)) {
                Magasin.add(cursor[0].getString(16));
                adapter.notifyDataSetChanged();
            }
        }
        DateRap = (TextView) findViewById(R.id.DateRapport);
        Date d = new Date();
        DateRap.setText(sdf.format(d));
        data.append(sdf.format(d)+"\n");



      try{

      }
      catch (Exception e){
         Toast.makeText(getApplicationContext(),"ups  can't open excel",Toast.LENGTH_SHORT).show();
         e.printStackTrace();
      }
    }


    public class DocAdap extends BaseAdapter {
        private final Context mContext;
        private final ArrayList<String> Magasin;


        public DocAdap(Context context, ArrayList<String> Magasin) {
            mContext = context;
            this.Magasin = Magasin;
        }

        @Override
        public int getCount() {
            return Magasin.size();
        }

        @Override
        public Object getItem(int i) {
            return Magasin.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.stockadap, null);
            }
            String code;
            String TYPE = "onHand";
            DataBaseM db = new DataBaseM(convertView.getContext());
            final TableLayout stk = (TableLayout) convertView.findViewById(R.id.tableItem);
            final TextView RapNom = (TextView) convertView.findViewById(R.id.RapNom);
            String sc = Magasin.get(i);
            RapNom.setText("Warehouse: " + Magasin.get(i));
            RapNom. setTypeface(null, Typeface.BOLD );
            stk.removeAllViews();


            TableRow tbrow0 = new TableRow(convertView.getContext());
            TextView tv0 = new TextView(convertView.getContext());


            tv0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv0.setText("Date");
            tv0.setTextColor(Color.WHITE);
            tv0.setBackgroundColor(Color.parseColor("#ffffff"));
            tv0.setBackgroundResource(R.drawable.descrip);
            tv0.setPadding(20, 20, 20, 20);
            tv0.setGravity(Gravity.CENTER);
            Header.add(tv0.getText().toString());
            tbrow0.addView(tv0);

            TextView tv2 = new TextView(convertView.getContext());
            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv2.setText(" type ");
            tv2.setTextColor(Color.WHITE);
            tv2.setBackgroundResource(R.drawable.descrip);
            tv2.setGravity(Gravity.CENTER);
            Header.add(tv2.getText().toString());
            tv2.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv2);

            TextView tv5 = new TextView(convertView.getContext());
            tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv5.setText("barcode");
            Header.add(tv5.getText().toString());
            tv5.setTextColor(Color.WHITE);
            tv5.setPadding(20, 20, 20, 20);
            tv5.setBackgroundColor(Color.parseColor("#ffffff"));
            tv5.setBackgroundResource(R.drawable.descrip);

            tv5.setGravity(Gravity.CENTER);
            tbrow0.addView(tv5);

            TextView tv6 = new TextView(convertView.getContext());
            tv6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv6.setText("Name");
            Header.add(tv6.getText().toString());
            tv6.setTextColor(Color.WHITE);
            tv6.setPadding(20, 20, 20, 20);
            tv6.setBackgroundColor(Color.parseColor("#ffffff"));
            tv6.setBackgroundResource(R.drawable.descrip);

            tv6.setGravity(Gravity.CENTER);
            tbrow0.addView(tv6);


            TextView tv8 = new TextView(convertView.getContext());
            tv8.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv8.setText("Quantity");
            Header.add(tv8.getText().toString());
            tv8.setTextColor(Color.WHITE);
            tv8.setPadding(20, 20, 20, 20);
            tv8.setBackgroundColor(Color.parseColor("#ffffff"));
            tv8.setBackgroundResource(R.drawable.descrip);

            tv8.setGravity(Gravity.CENTER);
            tbrow0.addView(tv8);

            TextView tv9 = new TextView(convertView.getContext());
            tv9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv9.setText("COUT");
            Header.add(tv9.getText().toString());
            tv9.setTextColor(Color.WHITE);
            tv9.setPadding(20, 20, 20, 20);
            tv9.setBackgroundColor(Color.parseColor("#ffffff"));
            tv9.setBackgroundResource(R.drawable.descrip);
            tv9.setGravity(Gravity.CENTER);
            tbrow0.addView(tv9);


            TextView tv10 = new TextView(convertView.getContext());
            tv10.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv10.setText("solds");
            Header.add(tv10.getText().toString());
            tv10.setTextColor(Color.WHITE);
            tv10.setPadding(20, 20, 20, 20);
            tv10.setBackgroundColor(Color.parseColor("#ffffff"));
            tv10.setBackgroundResource(R.drawable.descrip);

            tv10.setGravity(Gravity.CENTER);
            tbrow0.addView(tv10);


            stk.addView(tbrow0);

            db.QueryData();
            for (int j =0;j<Header.size();j++){
                data.append(Header.get(j)+",");
            }
            data.append("\n");

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

                    Cursor[] cursor = {db.getData("SELECT * FROM prod ")};
                    while (cursor[0].moveToNext()) {
                        if (cursor[0].getString(16).equals(sc)) {
                            Date dbD = sdf.parse(cursor[0].getString(10));
                            if (dbD.getMonth() + 1 == k) {
                                Toast.makeText(convertView.getContext(), "2", Toast.LENGTH_SHORT).show();
                                TableRow tbrow = new TableRow(convertView.getContext());
                                TextView t1v = new TextView(convertView.getContext());

                                t1v.setText(cursor[0].getString(10));
                                t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                        TableRow.LayoutParams.MATCH_PARENT));
                                t1v.setTextColor(Color.parseColor("#d3d3d3"));
                                t1v.setBackgroundColor(Color.parseColor("#ffffff"));
                                t1v.setPadding(20, 20, 20, 20);
                                t1v.setBackgroundResource(R.drawable.descrip);
                                t1v.setGravity(Gravity.CENTER);
                                tbrow.addView(t1v);
                                data.append(t1v.getText().toString() + ",");
                                code = cursor[0].getString(0);
                                Cursor cursor2 = db.getData("SELECT * FROM prod where fournisseur <> " + null + " AND dateDel = '01/01/2000'");
                                while (cursor2.moveToNext()) {
                                    if (code.equals(cursor2.getString(0))) {
                                        TYPE = "Entree";
                                    }
                                }

                                Cursor cursor3 = db.getData("SELECT * FROM prod where client <> " + null + " AND dateDel = '01/01/2000'");
                                while (cursor3.moveToNext()) {
                                    if (code.equals(cursor2.getString(0))) {
                                        TYPE = "Sortie";
                                    }
                                }


                                Cursor cursor1 = db.getData("SELECT * FROM transfert ");
                                while (cursor1.moveToNext()) {
                                    if (code.equals(cursor1.getString(0))) {
                                        TYPE = "transferd";
                                    }
                                }

                                TextView t3v = new TextView(convertView.getContext());
                                t3v.setText(TYPE);
                                t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                t3v.setTextColor(Color.parseColor("#d3d3d3"));
                                t3v.setBackgroundColor(Color.parseColor("#ffffff"));
                                t3v.setBackgroundResource(R.drawable.descrip);
                                t3v.setGravity(Gravity.CENTER);
                                t3v.setPadding(20, 20, 20, 20);
                                data.append(t3v.getText().toString() + ",");
                                tbrow.addView(t3v);

                                TextView t6v = new TextView(convertView.getContext());
                                t6v.setText(cursor[0].getString(0));
                                t6v.setPadding(20, 20, 20, 20);
                                data.append(t6v.getText().toString() + ",");
                                t6v.setTextColor(Color.parseColor("#d3d3d3"));
                                t6v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                                t6v.setBackgroundResource(R.drawable.descrip);
                                t6v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                t6v.setGravity(Gravity.CENTER);
                                tbrow.addView(t6v);

                                TextView t7v = new TextView(convertView.getContext());
                                t7v.setText(cursor[0].getString(1));
                                t7v.setPadding(20, 20, 20, 20);
                                data.append(t7v.getText().toString() + ",");
                                t7v.setTextColor(Color.parseColor("#d3d3d3"));
                                t7v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                                t7v.setBackgroundResource(R.drawable.descrip);
                                t7v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                t7v.setGravity(Gravity.CENTER);
                                tbrow.addView(t7v);

                                TextView t8v = new TextView(convertView.getContext());
                                t8v.setText(cursor[0].getString(7));
                                t8v.setPadding(20, 20, 20, 20);
                                data.append(t8v.getText().toString() + ",");
                                t8v.setTextColor(Color.parseColor("#d3d3d3"));
                                t8v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                                t8v.setBackgroundResource(R.drawable.descrip);
                                t8v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                t8v.setGravity(Gravity.CENTER);
                                tbrow.addView(t8v);

                                TextView t9v = new TextView(convertView.getContext());
                                t9v.setText(cursor[0].getString(8));
                                t9v.setPadding(20, 20, 20, 20);
                                data.append(t9v.getText().toString() + ",");
                                t9v.setTextColor(Color.parseColor("#d3d3d3"));
                                t9v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                                t9v.setBackgroundResource(R.drawable.descrip);
                                t9v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                t9v.setGravity(Gravity.CENTER);
                                tbrow.addView(t9v);

                                TextView t10v = new TextView(convertView.getContext());
                                t10v.setText(cursor[0].getString(14));
                                t10v.setPadding(20, 20, 20, 20);
                                data.append(t10v.getText().toString() + "," + "\n");
                                t10v.setTextColor(Color.parseColor("#d3d3d3"));
                                t10v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                                t10v.setBackgroundResource(R.drawable.descrip);
                                t10v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                t10v.setGravity(Gravity.CENTER);
                                tbrow.addView(t10v);
                                stk.addView(tbrow);
                            }
                        }
                    }
                }
            }
            catch(ParseException e){

            }


            TableRow tbrow3 = new TableRow(convertView.getContext());

            TextView tvv1 = new TextView(convertView.getContext());
            tvv1.setText(" Total :");
            tvv1.setPadding(20, 20, 20, 20);
            data.append(tvv1.getText().toString()+",");
            tvv1.setTextColor(Color.parseColor("#d3d3d3"));
            tvv1.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tvv1.setBackgroundResource(R.drawable.descrip);
            tvv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tvv1.setGravity(Gravity.CENTER);
            tbrow3.addView(tvv1);

            TextView tvv2 = new TextView(convertView.getContext());
            tvv2.setText(" ");
            tvv2.setPadding(20, 20, 20, 20);
            data.append(tvv2.getText().toString()+",");
            tvv2.setTextColor(Color.parseColor("#d3d3d3"));
            tvv2.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tvv2.setBackgroundResource(R.drawable.descrip);
            tvv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tvv2.setGravity(Gravity.CENTER);
            tbrow3.addView(tvv2);


            TextView tvv4 = new TextView(convertView.getContext());
            tvv4.setText(" ");
            tvv4.setPadding(20, 20, 20, 20);
            data.append(tvv4.getText().toString()+",");
            tvv4.setTextColor(Color.parseColor("#d3d3d3"));
            tvv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tvv4.setBackgroundResource(R.drawable.descrip);
            tvv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tvv4.setGravity(Gravity.CENTER);
            tbrow3.addView(tvv4);


            TextView tvv5 = new TextView(convertView.getContext());
            tvv5.setText(" ");
            tvv5.setPadding(20, 20, 20, 20);
            data.append(tvv5.getText().toString()+",");
            tvv5.setTextColor(Color.parseColor("#d3d3d3"));
            tvv5.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tvv5.setBackgroundResource(R.drawable.descrip);
            tvv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tvv5.setGravity(Gravity.CENTER);
            tbrow3.addView(tvv5);


            Double TotalQntty = 0.00;
            Double TotalCout = 0.00;
            Double TotalSales = 0.00;
            Cursor[] cursor1 = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000'")};

            while (cursor1[0].moveToNext()) {
                if(cursor1[0].getString(16).equals(sc)) {
                    TotalQntty = TotalQntty + cursor1[0].getDouble(7);
                    TotalSales = TotalSales + cursor1[0].getDouble(14);
                    TotalCout = TotalCout + cursor1[0].getDouble(8);
                }
            }


            TextView tvv3 = new TextView(convertView.getContext());
            tvv3.setText(TotalQntty + "");
            tvv3.setPadding(20, 20, 20, 20);
            tvv3.setTextColor(Color.parseColor("#d3d3d3"));
            tvv3.setBackgroundColor(Color.parseColor("#f5f5f5"));
            data.append(tvv3.getText().toString()+",");
            tvv3.setBackgroundResource(R.drawable.descrip);
            tvv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tvv3.setGravity(Gravity.CENTER);
            tbrow3.addView(tvv3);

            TextView tvv8 = new TextView(convertView.getContext());
            tvv8.setText(TotalCout + "");
            data.append(tvv8.getText().toString()+",");
            tvv8.setPadding(20, 20, 20, 20);
            tvv8.setTextColor(Color.parseColor("#d3d3d3"));
            tvv8.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tvv8.setBackgroundResource(R.drawable.descrip);
            tvv8.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tvv8.setGravity(Gravity.CENTER);
            tbrow3.addView(tvv8);

            TextView tvv9 = new TextView(convertView.getContext());
            tvv9.setText(TotalSales + "");
            data.append(tvv9.getText().toString());
            tvv9.setPadding(20, 20, 20, 20);
            tvv9.setTextColor(Color.parseColor("#d3d3d3"));
            tvv9.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tvv9.setBackgroundResource(R.drawable.descrip);
            tvv9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tvv9.setGravity(Gravity.CENTER);
            tbrow3.addView(tvv9);

            stk.addView(tbrow3);

            return convertView;

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
            FileOutputStream out = openFileOutput("Document_Report.csv",Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"Document_Report.csv");
            Uri paths=FileProvider.getUriForFile(context,"com.example.logg.FileProvider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Document_Report");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,paths);
            startActivity(Intent.createChooser(fileIntent,"send email"));

        }
        catch (Exception e){
             e.printStackTrace();
        }
    }
    public  String Tranform(String Coin){
        if (Coin.equals("Algerian dinar")) {
            Coin = "DA";
            return Coin;
        } else if (Coin.equals("dollar")) {
            Coin = "$";
            return Coin;
        } else {
            Coin = "€";
            return Coin;
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(DocumentReport.this,InventoryActivity.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
