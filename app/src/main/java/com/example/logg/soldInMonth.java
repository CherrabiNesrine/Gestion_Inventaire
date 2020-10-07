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
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class soldInMonth extends SidebarMenu {

    AnyChartView anyChartView;
    ArrayList<String> Items = new ArrayList<>();
    ArrayList<String> Codes = new ArrayList<>();
    ArrayList<Long> sold = new ArrayList<>();


    String duration = "1";
    String dtinv = "01/01/2000";
    DataBaseM db = new DataBaseM(this);
    int nbpur = 0;
    int nbsol = 0;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("duration")) {
                duration = intent.getStringExtra("duration");
            }
            if (intent.hasExtra("Date")) {
                dtinv = intent.getStringExtra("Date");
            }
        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_sold_in_month, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sold in month ");

        db.QueryData();

        try {
            Date dateInv = sdf.parse(dtinv);
            int mois = dateInv.getMonth();
            int dur = Integer.parseInt(duration);
            int debut;

            if (mois - dur <= 0) {
                debut = 0;
            } else {
                debut = mois - dur;
            }

            Cursor c1 = db.getData("select * from sold ");
            while (c1.moveToNext()) {
                String code = c1.getString(0);
                if (!Codes.contains(c1.getString(0))) {
                    Codes.add(code);
                    Cursor c = db.getData("select * from prod where ID='" + code + "'");
                    while (c.moveToNext()) {
                        if (!Items.contains(c.getString(1))) {
                            Items.add(c.getString(1));
                        }

                    }
                }


            }

            for (int k = 0; k < Items.size(); k++) {
                long someQntt = 0;
                for (int i = debut + 1; i <= mois + 1; i++) {
                    Cursor c2 = db.getData("select * from sold ");
                    while (c2.moveToNext()) {
                        if (Codes.get(k).equals(c2.getString(0))) {
                            Date d = sdf.parse(c2.getString(1));
                            if (d.getMonth() + 1 == i) {
                                someQntt = someQntt + c2.getLong(2);

                            }
                        }
                    }


                }
                sold.add(someQntt);


            }

for(int i=0;i<sold.size();i++){
    Toast.makeText(getApplicationContext(),sold.get(i)+"",Toast.LENGTH_LONG).show();
}

            anyChartView = (AnyChartView) findViewById(R.id.any_char_view);

            setupPieChart();

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void setupPieChart() {

         Cartesian cartesian = AnyChart.column();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < Items.size(); i++) {
            dataEntries.add(new ValueDataEntry(Items.get(i), sold.get(i)));

        }
        //cartesian.data(dataEntries);
        Column column = cartesian.column(dataEntries);
        column.tooltip()
                .titleFormat("{X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
             .format("{%Value}{groupsSeparator:}");

        cartesian.animation(true);
        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);
        cartesian.title("Items sold in this inventory period ");
        cartesian.xAxis(0).title("Product");
        cartesian.yAxis(0).title("Quantity");
        anyChartView.setChart(cartesian);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(soldInMonth.this, statisticsList.class);
            intent.putExtra("Date", dtinv);
            intent.putExtra("duration", duration);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}


