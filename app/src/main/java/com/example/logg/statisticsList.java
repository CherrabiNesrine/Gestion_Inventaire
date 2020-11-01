package com.example.logg;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class statisticsList extends SidebarMenu {
    TextView Cqtegory,sold,purshace;
    String dtinv="01/01/2000";
    String duration = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {

            if (intent.hasExtra("Date")){
                dtinv=intent.getStringExtra("Date");
            }
            if (intent.hasExtra("duration")) {
                duration = intent.getStringExtra("duration");
            }
        }
        //inflate your activity layout here!
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_statistics_list, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Statistics");
        Cqtegory = (TextView)findViewById(R.id.CategoryStat);
        Cqtegory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(statisticsList.this,sub_cat_statistics.class);
                intent.putExtra("duration",duration);
                intent.putExtra("Date",dtinv);
                startActivity(intent);
            }
        });
        sold=(TextView)findViewById(R.id.soldsindur);
        sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statisticsList.this,soldInMonth.class);
                intent.putExtra("duration",duration);
                intent.putExtra("Date",dtinv);
                startActivity(intent);
            }
        });


        purshace=(TextView)findViewById(R.id.SOLDVSPUR);
        purshace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statisticsList.this,Sold_Purchase_statistics.class);
                intent.putExtra("duration",duration);
                intent.putExtra("Date",dtinv);
                startActivity(intent);
            }
        });


    }

}
