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

public class InvMovHis extends SidebarMenu{
    TextView DateRap,Dateinventaire;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    StringBuilder data = new StringBuilder();
    ListView list_stock;
    DataBaseM db = new DataBaseM(this);
    ArrayList<String> Nom = new ArrayList<String>();
    ArrayList<String> code = new ArrayList<String>();
    MOVAdap adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View contentView = inflater.inflate(R.layout.activity_inv_mov_his2,null,false);
       drawer.addView(contentView,0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Inventory movement history ");




        adapter=new MOVAdap(this,Nom,code);
        list_stock=(ListView)findViewById(R.id.list_Mov_Rep);
        list_stock.setAdapter(adapter);
        Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000' and typePr ='Goods'")};
        while (cursor[0].moveToNext()){
            String cd = cursor[0].getString(0);
            Cursor cursor1 = db.getData("SELECT * FROM transfert where code ='"+cd+"'");

           while (cursor1.moveToNext()) {
               String sc = cursor[0].getString(0);
               if (!Nom.contains(sc)) {
                   code.add(cursor[0].getString(0));
                   Nom.add(cursor[0].getString(1));
                   adapter.notifyDataSetChanged();
                   Toast.makeText(getApplicationContext(),"get them  ",Toast.LENGTH_SHORT).show();

               }
           }

        }

        DateRap=(TextView)findViewById(R.id.DateRapport);
        Date d = new Date();
        DateRap.setText(sdf.format(d));


        data.append(sdf.format(d)+"\n");


    }
    public class MOVAdap extends BaseAdapter {
        private final Context mContext;
        private final ArrayList<String> Nom;
        private final ArrayList<String> CODE;


        public MOVAdap(Context context, ArrayList<String> Nom,ArrayList<String> CODE) {
            mContext = context;
            this.Nom = Nom;
            this.CODE=CODE;
        }

        @Override
        public int getCount() {
            return Nom.size();
        }

        @Override
        public Object getItem(int i) {
            return Nom.get(i);
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
            TableLayout stk = (TableLayout) convertView.findViewById(R.id.tableItem);
            final TextView RapNom = (TextView) convertView.findViewById(R.id.RapNom);
            String sc=Nom.get(i);
            RapNom.setText("Item : "+Nom.get(i)+" , "+ CODE.get(i));

            stk.removeAllViews();


            TableRow tbrow0 = new TableRow(convertView.getContext());
            TextView tv0 = new TextView(convertView.getContext());


            tv0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv0.setText("Date");
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
            tv2.setText(" Source ");
            data.append(tv2.getText().toString()+",");

            tv2.setTextColor(Color.WHITE);
            tv2.setBackgroundResource(R.drawable.descrip);
            tv2.setGravity(Gravity.CENTER);
            tv2.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv2);

            TextView tv5 = new TextView(convertView.getContext());
            tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv5.setText("Bucket");
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
            tv3.setText(" Destination ");
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
            tv4.setText("Bucket");
            data.append(tv4.getText().toString()+",");

            tv4.setTextColor(Color.WHITE);
            tv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tv4.setBackgroundResource(R.drawable.descrip);

            tv4.setGravity(Gravity.CENTER);
            tv4.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv4);



            TextView tv8 = new TextView(convertView.getContext());
            tv8.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv8.setText("Action code");
            data.append(tv8.getText().toString()+",");

            tv8.setTextColor(Color.WHITE);
            tv8.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tv8.setBackgroundResource(R.drawable.descrip);

            tv8.setGravity(Gravity.CENTER);
            tv8.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv8);


            TextView tv9 = new TextView(convertView.getContext());
            tv9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tv9.setText("Quantity");
            data.append(tv9.getText().toString()+",");

            tv9.setTextColor(Color.WHITE);
            tv9.setBackgroundColor(Color.parseColor("#f5f5f5"));
            tv9.setBackgroundResource(R.drawable.descrip);

            tv9.setGravity(Gravity.CENTER);
            tv9.setPadding(20, 20, 20, 20);
            tbrow0.addView(tv9);

            stk.addView(tbrow0);
            db.QueryData();
               int k = 1;
            Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000' and Nom ='"+sc+"'")};
            Toast.makeText(convertView.getContext(),RapNom.getText().toString(),Toast.LENGTH_SHORT).show();
            Cursor cursor1 = db.getData("SELECT * FROM transfert where code ='"+CODE.get(i)+"'");
            while (cursor[0].moveToNext()) {

                TableRow tbrow = new TableRow(convertView.getContext());
                TextView t1v = new TextView(convertView.getContext());
                t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t1v.setTextColor(Color.parseColor("#d3d3d3"));
                t1v.setBackgroundColor(Color.parseColor("#ffffff"));
                t1v.setPadding(20, 20, 20, 20);
                t1v.setBackgroundResource(R.drawable.descrip);
                t1v.setGravity(Gravity.CENTER);




                //source:
                TextView t3v = new TextView(convertView.getContext());
                t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                t3v.setTextColor(Color.parseColor("#d3d3d3"));
                t3v.setBackgroundColor(Color.parseColor("#ffffff"));
                t3v.setBackgroundResource(R.drawable.descrip);
                t3v.setGravity(Gravity.CENTER);
                data.append(t3v.getText().toString()+",");

                t3v.setPadding(20, 20, 20, 20);
//Bucket
                TextView t6v = new TextView(convertView.getContext());

                t6v.setPadding(20, 20, 20, 20);


                t6v.setTextColor(Color.parseColor("#d3d3d3"));
                t6v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                t6v.setBackgroundResource(R.drawable.descrip);
                t6v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                t6v.setGravity(Gravity.CENTER);

                //destination
                TextView t4v = new TextView(convertView.getContext());
                t4v.setTextColor(Color.parseColor("#d3d3d3"));
                t4v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                t4v.setBackgroundResource(R.drawable.descrip);


                t4v.setPadding(20, 20, 20, 20);

                t4v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t4v.setGravity(Gravity.CENTER);

                //Backet

                TextView t5v = new TextView(convertView.getContext());
                t5v.setBackgroundColor(Color.parseColor("#ffffff"));
                t5v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t5v.setTextColor(Color.parseColor("#d3d3d3"));
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.descrip);
                t5v.setPadding(20, 20, 20, 20);

                TextView t7v = new TextView(convertView.getContext());
                t7v.setBackgroundColor(Color.parseColor("#ffffff"));
                t7v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t7v.setTextColor(Color.parseColor("#d3d3d3"));
                t7v.setGravity(Gravity.CENTER);
                t7v.setBackgroundResource(R.drawable.descrip);
                t7v.setPadding(20, 20, 20, 20);

                TextView t8v = new TextView(convertView.getContext());
                t8v.setBackgroundColor(Color.parseColor("#ffffff"));
                t8v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t8v.setTextColor(Color.parseColor("#d3d3d3"));
                t8v.setGravity(Gravity.CENTER);
                t8v.setBackgroundResource(R.drawable.descrip);
                t8v.setPadding(20, 20, 20, 20);

while(cursor1.moveToNext()) {
    if (k == 1) {
        t1v.setText(cursor[0].getString(10));
        tbrow.addView(t1v); //date
        t3v.setText(cursor[0].getString(16));
        tbrow.addView(t3v);//source
        t6v.setText("onHand");//bucket
        tbrow.addView(t6v);
        t4v.setText(cursor1.getString(5));
        tbrow.addView(t4v);//destination
        t5v.setText("");//bucket
        tbrow.addView(t5v);
        t7v.setText("");//action code
        tbrow.addView(t7v);
        t8v.setText(cursor1.getLong(2) + "");
        tbrow.addView(t8v);// quantity
    } else {
        t1v.setText(cursor1.getString(3));
        tbrow.addView(t1v);
        t3v.setText(cursor1.getString(4));
        tbrow.addView(t3v);
        t6v.setText("Onhand");
        tbrow.addView(t6v);
        t4v.setText(cursor1.getString(5));
        tbrow.addView(t4v);
        t5v.setText(cursor1.getString(6));
        tbrow.addView(t5v);
        t7v.setText(cursor1.getString(5));
        tbrow.addView(t7v);
        t8v.setText(cursor1.getString(2));
        tbrow.addView(t8v);

    }
}


                stk.addView(tbrow);
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
            FileOutputStream out = openFileOutput("Inventory_Movement_History.csv",Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"Inventory_movement_report.csv");
            Uri paths= FileProvider.getUriForFile(context,"com.example.logg.FileProvider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Invemtory_movement_history_Report");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM,paths);
            startActivity(Intent.createChooser(fileIntent,"send email"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
