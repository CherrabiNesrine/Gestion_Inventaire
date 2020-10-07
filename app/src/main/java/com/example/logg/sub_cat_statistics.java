package com.example.logg;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class sub_cat_statistics extends SidebarMenu {
AnyChartView anyChartView;
String[] Subcat =  {"Consumable","notConsumable","rowMaterial","SpareParts"};

String duration ="1";
String dtinv="01/01/2000";
DataBaseM db = new DataBaseM(this);
int nbCons=0;
int nbNcons=0;
int nbrow=0;
int nbSpare=0;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
        View contentView = inflater.inflate(R.layout.activity_sub_cat_statistics, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Category statistics  ");
        db.QueryData();


        try {
            Date dateInv = sdf.parse(dtinv);
           int mois= dateInv.getMonth();
           int dur = Integer.parseInt(duration);
int debut;

            if(mois-dur<=0){
                debut=1;
            }
            else{
                debut=mois-dur;
            }
                for(int i=debut+1;i<=mois+1;i++){
                    Cursor cs = db.getData("select * from prod ");
                    while(cs.moveToNext()){
                    Date ko = sdf.parse(cs.getString(10));
                    int kom = ko.getMonth();
                    if(kom==i){
                        if(cs.getString(4).equals("Consumable")){
                            nbCons=nbCons+1;

                        }
                        else if (cs.getString(4).equals("Not consumable")){
                            nbNcons=nbNcons+1;

                        }
                        else if (cs.getString(4).equals("Raw materials")){
                            nbrow=nbrow+1;
                        }
                        else {
                            nbSpare=nbSpare+1;
                        }
                    }


                    }
                }
                if(nbSpare==0 && nbrow==0 && nbNcons==0 && nbCons==0){
                    Toast.makeText(getApplicationContext(),"NO PRODUCT FOUND ! ",Toast.LENGTH_LONG).show();
                }
            int[] earning={nbCons,nbNcons,nbrow,nbSpare};
            anyChartView=(AnyChartView)findViewById(R.id.any_char_view);
            setupPieChart(earning);

        } catch (ParseException e) {
            e.printStackTrace();
        }





    }
    public void setupPieChart(int[] t){

        Pie pie= AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for(int i =0 ;i<Subcat.length;i++ ){
            dataEntries.add(new ValueDataEntry(Subcat[i],t[i]));

        }
        pie.data(dataEntries);
        pie.animation(true);
        anyChartView.setChart(pie);
        pie.title("category in inventory duration ");
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent=new Intent(sub_cat_statistics.this,statisticsList.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
