package com.example.logg;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    String coin = "";
    String coins = "";
    double TotalFrais = 0.00;
    double Totalbenifnet = 0.00;
    TextView DateReport;
    ArrayList<Double> All = new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_profits_et_pertes, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profis et Pertes ");
        Date datte=new Date();
        DateReport=(TextView)findViewById(R.id.DateRapport);
        String Sdatte=sdf.format(datte);
        DateReport.setText(Sdatte);
        init();
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
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv1.setText("Total purchase ");
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

        tbrow0.addView(tv4);

        stk.addView(tbrow0);


        // DYNAMIQUE ROwS


        String dateAuj=sdf.format(new Date());
        try {
            Date Aujour=sdf.parse(dateAuj);
            int max= Aujour.getMonth();
            for (int i =1;i<=max+1;i++){
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(String.valueOf(i));
                t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t1v.setTextColor(Color.parseColor("#d3d3d3"));
                t1v.setBackgroundColor(Color.parseColor("#ffffff"));
                t1v.setPadding(20, 20, 20, 20);
                t1v.setBackgroundResource(R.drawable.descrip);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);

                Cursor[] cursor={db.getData("SELECT * FROM prod where dateDel = '01/01/2000' ")};
                while(cursor[0].moveToNext()){
                    Date dbD= sdf.parse(cursor[0].getString(10));


                    if(dbD.getMonth()+1==i){
                        Double achat= cursor[0].getDouble(8);
                        Achats=Achats+achat;
                    }
                    coin=cursor[0].getString(12);

                }
                Toast.makeText(getApplicationContext(),coin,Toast.LENGTH_SHORT).show();

                coin=Tranform(coin);
                TextView t2v = new TextView(this);
                t2v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t2v.setText(String.valueOf(Achats + coin));
                t2v.setBackgroundResource(R.drawable.descrip);

                t2v.setTextColor(Color.parseColor("#d3d3d3"));
                t2v.setGravity(Gravity.CENTER);
                t2v.setPadding(20, 20, 20, 20);
                tbrow.addView(t2v);



                Cursor[] cursor2={db.getData("SELECT * FROM prod where dateDel = '01/01/2000' ")};
                while(cursor2[0].moveToNext()){
                    Date dbD= sdf.parse(cursor2[0].getString(10));
                    if(dbD.getMonth()+1==i){
                        Double vente= cursor2[0].getDouble(14);
                        Ventes=Ventes+vente;
                    }
                }
                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(Ventes + coin));
                t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
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
                      Toast.makeText(getApplicationContext(),"hellooo world zmr ",Toast.LENGTH_SHORT).show();
                    }

                }

                TextView t4v = new TextView(this);
                t4v.setText(String.valueOf(frais) + coin);
                t4v.setTextColor(Color.parseColor("#d3d3d3"));
                t4v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                t4v.setBackgroundResource(R.drawable.descrip);
                t4v.setPadding(20, 20, 20, 20);

                t4v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t4v.setGravity(Gravity.CENTER);
                tbrow.addView(t4v);

                benifnet = Ventes - (Achats + frais);
                All.add(benifnet);
                TextView t5v = new TextView(this);
                t5v.setBackgroundColor(Color.parseColor("#ffffff"));
                t5v.setText(String.valueOf(benifnet) + coin);
                t5v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                t5v.setTextColor(Color.parseColor("#d3d3d3"));
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.descrip);
                t5v.setPadding(20, 20, 20, 20);

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

        tvv1.setTextColor(Color.parseColor("#d3d3d3"));
        tvv1.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tvv1.setBackgroundResource(R.drawable.descrip);
        tvv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        tvv1.setGravity(Gravity.CENTER);
        tbrow3.addView(tvv1);
        double prix=0.0;
        Cursor[] cursor4 = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000'")};
        TotalAchat=0.00;
        while (cursor4[0].moveToNext()) {
             prix = cursor4[0].getDouble(8);
            TotalAchat = TotalAchat+prix;
        }
        TextView tvv2 = new TextView(this);
        tvv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tvv2.setText(TotalAchat + coin);
        tvv2.setTextColor(Color.WHITE);
        tvv2.setGravity(Gravity.CENTER);
        tvv2.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tvv2.setBackgroundResource(R.drawable.descrip);
        tvv2.setPadding(20, 20, 20, 20);
        tbrow3.addView(tvv2);
        prix=0.0;
        Cursor[] cursor5 = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000'")};
        while (cursor5[0].moveToNext()) {
             prix= cursor5[0].getDouble(14);
            TotalVentes = TotalVentes+prix;
        }
        TextView tvv3 = new TextView(this);
        tvv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tvv3.setText(String.valueOf(TotalVentes) + coin);
        tvv3.setTextColor(Color.WHITE);
        tvv3.setGravity(Gravity.CENTER);
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
        tvv5.setPadding(20, 20, 20, 20);
        tbrow3.addView(tvv5);

        stk.addView(tbrow3);

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
}
