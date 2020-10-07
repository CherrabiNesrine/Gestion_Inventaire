package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileOutputStream;
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
        View contentView = inflater.inflate(R.layout.activity_inventory__stock__coast__repport, null, false);
        drawer.addView(contentView, 0);
        adapter=new StockAdap(this,SousCat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Stock coast report");
        data.append("Stock coast report \n");
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
        data.append(sdf.format(d)+"\n");


        //   init();


    }
    public class StockAdap extends BaseAdapter {
        private final Context mContext;
        private final ArrayList<String> SousCat;


        public StockAdap(Context context, ArrayList<String> SousCat) {
            mContext = context;
            this.SousCat = SousCat;
        }

        @Override
        public int getCount() {
            return SousCat.size();
        }

        @Override
        public Object getItem(int i) {
            return SousCat.get(i);
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

            DataBaseM db = new DataBaseM(convertView.getContext());
            final TableLayout stk = (TableLayout) convertView.findViewById(R.id.tableItem);
            final TextView RapNom = (TextView) convertView.findViewById(R.id.RapNom);
            String sc=SousCat.get(i);
            RapNom.setText("SubCategory"+SousCat.get(i));

            stk.removeAllViews();


            TableRow tbrow0 = new TableRow(convertView.getContext());
            TextView tv0 = new TextView(convertView.getContext());


            tv0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv0.setText("code");
            data.append(tv0.getText().toString()+",");
            tv0.setTextColor(Color.WHITE);
            tv0.setBackgroundColor(Color.parseColor("#ffffff"));
            tv0.setBackgroundResource(R.drawable.descrip);
            tv0.setPadding(20, 20, 20, 20);
            tv0.setGravity(Gravity.CENTER);
            tbrow0.addView(tv0);

            TextView tv2 = new TextView(convertView.getContext());
            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv2.setText(" description ");
            data.append(tv2.getText().toString()+",");

            tv2.setTextColor(Color.WHITE);
            tv2.setBackgroundResource(R.drawable.descrip);
            tv2.setGravity(Gravity.CENTER);
            tv2.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv2);

            TextView tv5 = new TextView(convertView.getContext());
            tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv5.setText("OnHand");
            data.append(tv5.getText().toString()+",");

            tv5.setTextColor(Color.WHITE);
            tv5.setPadding(20, 20, 20, 20);
            tv5.setBackgroundColor(Color.parseColor("#ffffff"));
            tv5.setBackgroundResource(R.drawable.descrip);

            tv5.setGravity(Gravity.CENTER);
            tbrow0.addView(tv5);

            TextView tv3 = new TextView(convertView.getContext());
            tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv3.setText(" Unit_Coast ");
            data.append(tv3.getText().toString()+",");

            tv3.setTextColor(Color.WHITE);
            tv3.setGravity(Gravity.CENTER);
            tv3.setBackgroundColor(Color.parseColor("#333333"));
            tv3.setBackgroundResource(R.drawable.descrip);
            tv3.setPadding(20, 20, 20, 20);

            tbrow0.addView(tv3);
            TextView tv4 = new TextView(convertView.getContext());
            tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv4.setText("Total_Coast");
            data.append(tv4.getText().toString()+",");

            tv4.setTextColor(Color.WHITE);
            tv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tv4.setBackgroundResource(R.drawable.descrip);

            tv4.setGravity(Gravity.CENTER);
            tv4.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv4);
            stk.addView(tbrow0);
            db.QueryData();

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

        Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000' and sousCategorie ='" + sc + "'")};
        while (cursor[0].moveToNext()) {
            Date dbD = sdf.parse(cursor[0].getString(10));
            if (dbD.getMonth() + 1 == k) {
                TableRow tbrow = new TableRow(convertView.getContext());
                TextView t1v = new TextView(convertView.getContext());
                t1v.setText(cursor[0].getString(0));
                t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t1v.setTextColor(Color.parseColor("#d3d3d3"));
                t1v.setBackgroundColor(Color.parseColor("#ffffff"));
                t1v.setPadding(20, 20, 20, 20);
                t1v.setBackgroundResource(R.drawable.descrip);
                t1v.setGravity(Gravity.CENTER);
                data.append(t1v.getText().toString() + ",");

                tbrow.addView(t1v);
                String Coin = cursor[0].getString(11);
                if (Coin.equals("Algerian dinar ")) {
                    Coin = "DA";
                } else if (Coin.equals("dollar")) {
                    Coin = "$";
                } else if (Coin.equals("euro")) {
                    Coin = "€";
                }
                TextView t3v = new TextView(convertView.getContext());
                t3v.setText(cursor[0].getString(15));
                t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                t3v.setTextColor(Color.parseColor("#d3d3d3"));
                t3v.setBackgroundColor(Color.parseColor("#ffffff"));
                t3v.setBackgroundResource(R.drawable.descrip);
                t3v.setGravity(Gravity.CENTER);
                data.append(t3v.getText().toString() + ",");

                t3v.setPadding(20, 20, 20, 20);
                tbrow.addView(t3v);

                TextView t6v = new TextView(convertView.getContext());
                t6v.setText(cursor[0].getString(7));
                t6v.setPadding(20, 20, 20, 20);
                data.append(t6v.getText().toString() + ",");

                t6v.setTextColor(Color.parseColor("#d3d3d3"));
                t6v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                t6v.setBackgroundResource(R.drawable.descrip);
                t6v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                t6v.setGravity(Gravity.CENTER);
                tbrow.addView(t6v);

                TextView t4v = new TextView(convertView.getContext());
                t4v.setText(String.valueOf(cursor[0].getDouble(8)));
                t4v.setTextColor(Color.parseColor("#d3d3d3"));
                t4v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                t4v.setBackgroundResource(R.drawable.descrip);
                data.append(t4v.getText().toString() + ",");

                t4v.setPadding(20, 20, 20, 20);

                t4v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);
                TextView t5v = new TextView(convertView.getContext());
                t5v.setBackgroundColor(Color.parseColor("#ffffff"));
                double total_prix = 0;
                total_prix = cursor[0].getLong(7) * cursor[0].getDouble(8);
                Coin = Tranform(Coin);
                t5v.setText(Coin + String.valueOf(total_prix));
                data.append(t5v.getText().toString() + "\n");

                t5v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t5v.setTextColor(Color.parseColor("#d3d3d3"));
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.descrip);
                t5v.setPadding(20, 20, 20, 20);
                tbrow.addView(t5v);
                stk.addView(tbrow);
            }
        }

    }
}
            catch(Exception e){

            }
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
            FileOutputStream out = openFileOutput("Stock_coast_Report.csv",Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"Stock_coast_Report.csv");
            Uri paths= FileProvider.getUriForFile(context,"com.example.logg.FileProvider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Stock_coast_Report");
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
            Intent intent=new Intent(Inventory_Stock_Coast_Repport.this,InventoryActivity.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
