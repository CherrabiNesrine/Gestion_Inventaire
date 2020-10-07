package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

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

public class ExpensesReport extends SidebarMenu {
    DataBaseM db = new DataBaseM(this);
    ArrayList<String> Magasin = new ArrayList<String>();
    StringBuilder data = new StringBuilder();
    DepAdap adapter;
    ListView list_stock;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    TextView DateRap;
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
        View contentView = inflater.inflate(R.layout.activity_expenses_report, null, false);
        drawer.addView(contentView, 0);
        adapter=new DepAdap(this,Magasin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Expenses Report ");
        data.append("Expenses Report \n ");
        list_stock=(ListView)findViewById(R.id.list_stock_coast);
        list_stock.setAdapter(adapter);
        Cursor[] cursor = {db.getData("SELECT * FROM depense ")};
        while (cursor[0].moveToNext()){
            String sc1=cursor[0].getString(2);
            if(!Magasin.contains(sc1)) {
                Magasin.add(cursor[0].getString(2));
                adapter.notifyDataSetChanged();
            }
        }
        DateRap=(TextView)findViewById(R.id.DateRapport);

        Date d = new Date();
        DateRap.setText(sdf.format(d));

        data.append(DateRap.getText().toString()+"\n");


    }



    public class DepAdap extends BaseAdapter {
        private final Context mContext;
        private final ArrayList<String> Magasin;


        public DepAdap(Context context, ArrayList<String> Magasin) {
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

            DataBaseM db = new DataBaseM(convertView.getContext());
            final TableLayout stk = (TableLayout) convertView.findViewById(R.id.tableItem);
            final TextView RapNom = (TextView) convertView.findViewById(R.id.RapNom);
            String sc = Magasin.get(i);
            RapNom.setText("Warehouse: " + Magasin.get(i));

            stk.removeAllViews();


            TableRow tbrow0 = new TableRow(convertView.getContext());
            TextView tv0 = new TextView(convertView.getContext());


            tv0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv0.setText("Date");
            data.append(tv0.getText().toString() + ",");
            tv0.setTextColor(Color.WHITE);
            tv0.setBackgroundColor(Color.parseColor("#ffffff"));
            tv0.setBackgroundResource(R.drawable.descrip);
            tv0.setPadding(20, 20, 20, 20);
            tv0.setGravity(Gravity.CENTER);
            tbrow0.addView(tv0);

            TextView tv2 = new TextView(convertView.getContext());
            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv2.setText(" Name ");
            data.append(tv2.getText().toString() + ",");
            tv2.setTextColor(Color.WHITE);
            tv2.setBackgroundResource(R.drawable.descrip);
            tv2.setGravity(Gravity.CENTER);
            tv2.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv2);

            TextView tv5 = new TextView(convertView.getContext());
            tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv5.setText("Montent");
            data.append(tv5.getText().toString() + "\n");

            tv5.setTextColor(Color.WHITE);
            tv5.setPadding(20, 20, 20, 20);
            tv5.setBackgroundColor(Color.parseColor("#ffffff"));
            tv5.setBackgroundResource(R.drawable.descrip);

            tv5.setGravity(Gravity.CENTER);
            tbrow0.addView(tv5);

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
                    Cursor[] cursor = {db.getData("SELECT * FROM depense where magD ='" + sc + "'")};
                    Toast.makeText(convertView.getContext(), RapNom.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (cursor != null) {
                        while (cursor[0].moveToNext()) {
                            Date dbD = sdf.parse(cursor[0].getString(10));
                            if (dbD.getMonth() + 1 == k) {
                                TableRow tbrow = new TableRow(convertView.getContext());
                                TextView t1v = new TextView(convertView.getContext());

                                t1v.setText(cursor[0].getString(5));
                                t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                        TableRow.LayoutParams.MATCH_PARENT));
                                t1v.setTextColor(Color.parseColor("#d3d3d3"));
                                data.append(t1v.getText().toString() + ",");
                                t1v.setBackgroundColor(Color.parseColor("#ffffff"));
                                t1v.setPadding(20, 20, 20, 20);
                                t1v.setBackgroundResource(R.drawable.descrip);
                                t1v.setGravity(Gravity.CENTER);
                                tbrow.addView(t1v);

                                TextView t3v = new TextView(convertView.getContext());
                                t3v.setText(cursor[0].getString(2));
                                data.append(t3v.getText().toString() + ",");
                                t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                t3v.setTextColor(Color.parseColor("#d3d3d3"));
                                t3v.setBackgroundColor(Color.parseColor("#ffffff"));
                                t3v.setBackgroundResource(R.drawable.descrip);
                                t3v.setGravity(Gravity.CENTER);
                                t3v.setPadding(20, 20, 20, 20);
                                tbrow.addView(t3v);

                                TextView t6v = new TextView(convertView.getContext());
                                t6v.setText(cursor[0].getString(3));
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


                        TableRow tbrow3 = new TableRow(convertView.getContext());

                        TextView tvv1 = new TextView(convertView.getContext());
                        tvv1.setText(" Total :");
                        tvv1.setPadding(20, 20, 20, 20);

                        tvv1.setTextColor(Color.parseColor("#ffffff"));
                        tvv1.setBackgroundColor(Color.parseColor("#f5f5f5"));
                        tvv1.setBackgroundResource(R.drawable.descrip);
                        tvv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                        tvv1.setGravity(Gravity.CENTER);
                        data.append(tvv1.getText().toString() + ",");

                        tbrow3.addView(tvv1);

                        TextView tvv2 = new TextView(convertView.getContext());
                        tvv2.setText(" ");
                        tvv2.setPadding(20, 20, 20, 20);
                        data.append(tvv2.getText().toString() + ",");

                        tvv2.setTextColor(Color.parseColor("#d3d3d3"));
                        tvv2.setBackgroundColor(Color.parseColor("#f5f5f5"));
                        tvv2.setBackgroundResource(R.drawable.descrip);
                        tvv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                        tvv2.setGravity(Gravity.CENTER);
                        tbrow3.addView(tvv2);
                        Double TotalDep = 0.00;
                        Cursor[] cursor1 = {db.getData("SELECT * FROM depense where magD ='" + sc + "'")};

                        while (cursor[0].moveToNext()) {
                            TotalDep = TotalDep + cursor1[0].getDouble(3);
                        }


                        TextView tvv3 = new TextView(convertView.getContext());
                        tvv3.setText(TotalDep + "");
                        tvv3.setPadding(20, 20, 20, 20);
                        tvv3.setTextColor(Color.parseColor("#d3d3d3"));
                        tvv3.setBackgroundColor(Color.parseColor("#f5f5f5"));
                        tvv3.setBackgroundResource(R.drawable.descrip);
                        data.append(tvv3.getText().toString());

                        tvv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                        tvv3.setGravity(Gravity.CENTER);
                        tbrow3.addView(tvv3);

                        stk.addView(tbrow3);
                    } else {
                        Toast.makeText(convertView.getContext(), "No expense found ", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            catch (Exception e0){

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
            FileOutputStream out = openFileOutput("Expenses_Report.csv",Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"Expenses_Report.csv");
            Uri paths= FileProvider.getUriForFile(context,"com.example.logg.FileProvider",fileLocation);

            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Expenses_Report");
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
            Intent intent=new Intent(ExpensesReport.this,InventoryActivity.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}
