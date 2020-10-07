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

public class Sold_Purchase_statistics extends SidebarMenu {

    AnyChartView anyChartView;
    String[] Subcat =  {"purchase","sold"};

    String duration ="1";
    String dtinv="01/01/2000";
    DataBaseM db = new DataBaseM(this);
    int nbpur=0;
    int nbsol=0;
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
        View contentView = inflater.inflate(R.layout.activity_sold__purchase_statistics, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Purchase and sales ");

        db.QueryData();

        try {
            Date dateInv = sdf.parse(dtinv);
            int mois= dateInv.getMonth();
            int dur = Integer.parseInt(duration);
            int debut;

            if(mois-dur<=0){
                debut=0;
            }
            else{
                debut=mois-dur;
            }
            for(int i=debut+1;i<=mois+1;i++){
                Cursor cs = db.getData("select * from purchase");
                while(cs.moveToNext()){
                    Date ko = sdf.parse(cs.getString(1));
                    int kom = ko.getMonth()+1;


                    if(kom==i){
                        Cursor cursor =db.getData("SELECT * FROM sold where Id ='"+cs.getString(0)+"'");
                        if( cursor.getCount()<=0 ){
                            nbpur++;
                            Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_LONG).show();

                        }
                        else{
                            if(cs.getLong(2)==0){
                                nbsol++;
                            }
                            else{
                                nbpur++;
                                nbsol++;
                            }


                        }


                    }


                }
            }
            int[] earning={nbpur,nbsol};
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
            Intent intent=new Intent(Sold_Purchase_statistics.this,statisticsList.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
