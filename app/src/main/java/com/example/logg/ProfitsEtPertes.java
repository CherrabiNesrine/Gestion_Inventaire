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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfitsEtPertes extends SidebarMenu {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    double Achats = 0.00;
    double Ventes = 0.00;
    double frais = 0.00;
    double benifnet = 0.00;
    double TotalAchat = 0.00;
    double TotalVentes = 0.00;
    String coin = "Algerian dinar ";
    double TotalFrais = 0.00;
    double Totalbenifnet = 0.00;
    TextView DateReport;
    ArrayList<Double> All = new ArrayList<Double>();
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
        View contentView = inflater.inflate(R.layout.activity_profits_et_pertes, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profits and Losses ");
        data.append("Profits and Losses"+"\n");
        Date datte=new Date();


        DateReport=(TextView)findViewById(R.id.DateRapport);
        String Sdatte=sdf.format(datte);
        data.append(Sdatte+"\n");
        DateReport.setText(Sdatte);
coin=Tranform(coin);
        init();
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(ProfitsEtPertes.this,InventoryActivity.class);
        intent.putExtra("Date", dtinv);
        intent.putExtra("duration", duration);
        startActivity(intent);
    }

    public void init() {
        db.QueryData();
        TableLayout stk = (TableLayout) findViewById(R.id.tableItemProfis);
        stk.removeAllViews();

        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv0.setText("Mois");
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
        tv1.setText("Total purchase ");
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
        tv2.setText(" Total sale ");
        data.append(tv2.getText().toString()+",");
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(R.drawable.descrip);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv3.setText("expenses");
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tv3.setBackgroundColor(Color.parseColor("#333333"));
        tv3.setBackgroundResource(R.drawable.descrip);
        tv3.setPadding(20, 20, 20, 20);
        data.append(tv3.getText().toString()+",");
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv4.setText("Net profit");
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tv4.setBackgroundResource(R.drawable.descrip);

        tv4.setGravity(Gravity.CENTER);
        tv4.setPadding(20, 20, 20, 20);
        data.append(tv4.getText().toString()+"\n");
        tbrow0.addView(tv4);

        stk.addView(tbrow0);


        // DYNAMIQUE ROwS


        try {
            Date dateInv = sdf.parse(dtinv);
            int mois= dateInv.getMonth()+1;
            int dur = Integer.parseInt(duration);
            int debut;

            if(mois-dur<=0){
                debut=1;
            }
            else{
                debut=mois-dur;
            }


            for (int i=debut;i<=mois;i++){


                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(String.valueOf(i));

                data.append(t1v.getText().toString()+",");
                t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t1v.setTextColor(Color.parseColor("#d3d3d3"));
                t1v.setBackgroundColor(Color.parseColor("#ffffff"));
                t1v.setPadding(20, 20, 20, 20);
                t1v.setBackgroundResource(R.drawable.descrip);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);

                Cursor[] cursor={db.getData("SELECT * FROM purchase where qntt <> 0 ")};
                Achats=0;
                if (cursor[0].getCount()<=0) {

                }
                while(cursor[0].moveToNext()){
                    Date dbD= sdf.parse(cursor[0].getString(1));

                    if(dbD.getMonth()+1==i){
                        double achat= cursor[0].getDouble(3);

                        Achats=Achats+achat*cursor[0].getLong(2);
                        TotalAchat=TotalAchat+achat;
                    }


                }

                coin=Tranform(coin);
                TextView t2v = new TextView(this);
                t2v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t2v.setText(coin + String.valueOf(Achats));
                Toast.makeText(getApplicationContext(),"i did it 2  ",Toast.LENGTH_LONG).show();

                data.append(t2v.getText().toString()+",");
                t2v.setBackgroundResource(R.drawable.descrip);

                t2v.setTextColor(Color.parseColor("#d3d3d3"));
                t2v.setGravity(Gravity.CENTER);
                t2v.setPadding(20, 20, 20, 20);
                tbrow.addView(t2v);



                Cursor[] cursor2={db.getData("select * from sold  ")};
                Ventes=0;
                while(cursor2[0].moveToNext()){
                    Date dbD= sdf.parse(cursor2[0].getString(1));
                    if(dbD.getMonth()+1==i){
                        double vente= cursor2[0].getDouble(3);
                        Toast.makeText(getApplicationContext(),vente+"VENTE",Toast.LENGTH_SHORT).show();
                        Ventes=Ventes+vente*cursor2[0].getLong(2);
                        TotalVentes=TotalVentes+Ventes;
                    }
                }
                TextView t3v = new TextView(this);
                t3v.setText(coin+ String.valueOf(Ventes));
                t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                data.append(t3v.getText().toString()+",");
                t3v.setTextColor(Color.parseColor("#d3d3d3"));
                t3v.setBackgroundColor(Color.parseColor("#ffffff"));
                t3v.setBackgroundResource(R.drawable.descrip);
                t3v.setGravity(Gravity.CENTER);
                t3v.setPadding(20, 20, 20, 20);
                tbrow.addView(t3v);


                Cursor[] cursor4 = {db.getData("SELECT * FROM depense  ")};
                while (cursor4[0].moveToNext()) {
                    try {
                        String Dattte=cursor4[0].getString(5);
                        if(Dattte != null){
                        Date datee = sdf.parse(Dattte);
                        if (datee.getMonth()+1 == i) {

                            double frai = cursor4[0].getDouble(3);
                            frais += frai;
                        }
                        }
                        else{

                        }
                    }


                    catch(Exception e){
                      Toast.makeText(getApplicationContext(),"Erreur",Toast.LENGTH_SHORT).show();
                    }

                }

                TextView t4v = new TextView(this);
                t4v.setText(coin+String.valueOf(frais));
                t4v.setTextColor(Color.parseColor("#d3d3d3"));
                t4v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                t4v.setBackgroundResource(R.drawable.descrip);
                t4v.setPadding(20, 20, 20, 20);
                data.append(t4v.getText().toString()+",");
                t4v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);

                benifnet = Ventes - (Achats + frais);
                All.add(benifnet);
                TextView t5v = new TextView(this);
                t5v.setBackgroundColor(Color.parseColor("#ffffff"));
                t5v.setText(coin+ String.valueOf(benifnet));
                t5v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t5v.setTextColor(Color.parseColor("#d3d3d3"));
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.descrip);
                t5v.setPadding(20, 20, 20, 20);
                data.append(t5v.getText().toString()+"\n");
                tbrow.addView(t5v);
                stk.addView(tbrow);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Final Row



        TableRow tbrow3 = new TableRow(this);

        TextView tvv1 = new TextView(this);
        tvv1.setText(" Total :");
        tvv1.setPadding(20, 20, 20, 20);
        data.append(tvv1.getText().toString()+",");
        tvv1.setTextColor(Color.parseColor("#d3d3d3"));
        tvv1.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tvv1.setBackgroundResource(R.drawable.descrip);
        tvv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        tvv1.setGravity(Gravity.CENTER);
        tbrow3.addView(tvv1);
        double prix=0.0;
       /* Cursor[] cursor4 = {db.getData("SELECT * FROM purchase where qntt <> 0 ")};
        TotalAchat=0.00;
        while (cursor4[0].moveToNext()) {
             prix = cursor4[0].getDouble(3);
            TotalAchat = TotalAchat+prix;
        }*/
        TextView tvv2 = new TextView(this);
        tvv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tvv2.setText(TotalAchat + coin);
        tvv2.setTextColor(Color.WHITE);
        tvv2.setGravity(Gravity.CENTER);
        data.append(tvv2.getText().toString()+",");

        tvv2.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tvv2.setBackgroundResource(R.drawable.descrip);
        tvv2.setPadding(20, 20, 20, 20);
        tbrow3.addView(tvv2);
        prix=0.0;
       /* Cursor[] cursor5 = {db.getData("SELECT * FROM sold")};
        while (cursor5[0].moveToNext()) {
             prix= cursor5[0].getDouble(3);
            TotalVentes = TotalVentes+prix;
        }*/
        TextView tvv3 = new TextView(this);
        tvv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tvv3.setText(String.valueOf(TotalVentes) + coin);
        tvv3.setTextColor(Color.WHITE);
        tvv3.setGravity(Gravity.CENTER);
        data.append(tvv3.getText().toString()+",");

        tvv3.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tvv3.setBackgroundResource(R.drawable.descrip);
        tvv3.setPadding(20, 20, 20, 20);
        tbrow3.addView(tvv3);
        prix=0.0;
        Cursor[] cursor6 = {db.getData("SELECT * FROM depense  ")};
        while (cursor6[0].moveToNext()) {
             prix = cursor6[0].getDouble(3);
            TotalFrais += prix;
        }
        TextView tvv4 = new TextView(this);
        tvv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tvv4.setText(String.valueOf(TotalFrais) + coin);
        tvv4.setTextColor(Color.WHITE);
        tvv4.setGravity(Gravity.CENTER);
        data.append(tvv4.getText().toString()+",");

        tvv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tvv4.setBackgroundResource(R.drawable.descrip);
        tvv4.setPadding(20, 20, 20, 20);
        tbrow3.addView(tvv4);

        for (int i = 0; i < All.size(); i++) {
            Totalbenifnet += All.get(i);
        }
        TextView tvv5 = new TextView(this);
        tvv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tvv5.setText(String.valueOf(Totalbenifnet) + coin);
        tvv5.setTextColor(Color.WHITE);
        tvv5.setGravity(Gravity.CENTER);
        tvv5.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tvv5.setBackgroundResource(R.drawable.descrip);
        data.append(tvv5.getText().toString());

        tvv5.setPadding(20, 20, 20, 20);
        tbrow3.addView(tvv5);

        stk.addView(tbrow3);

    }
    public  String Tranform(String Coin){
        if (Coin.equals("Algerian dinar ")) {
            Coin = "DA";
            return Coin;
        } else if (Coin.equals("dollar")) {
            Coin = "$";
            return Coin;
        } else {
            Coin = "DA";
            return Coin;
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
            FileOutputStream out = openFileOutput("Profits_Losses_Report.csv",Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(),"Profits_Losses_Report.csv");
            Uri paths= FileProvider.getUriForFile(context,"com.example.logg.FileProvider",fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Profits_Losses_Report");
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
            Intent intent=new Intent(ProfitsEtPertes.this,InventoryActivity.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
