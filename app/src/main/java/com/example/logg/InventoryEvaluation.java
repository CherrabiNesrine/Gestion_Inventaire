package com.example.logg;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InventoryEvaluation extends SidebarMenu {
    TextView DateRap;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    StringBuilder data = new StringBuilder();
    String first = "";
    String duration = "12";
    String dtinv = "01/01/2000";
    ArrayList<String> ITEMS = new ArrayList<>();
    Date y = new Date();
    double total_coast_in_month = 0.00;
    ArrayList<Long> array_total_in_duration = new ArrayList<>();
    ArrayList<Double> array_total_item_coast_duration = new ArrayList<>();
    ArrayList<Double> array_total_item_coast_month = new ArrayList<>();
    ArrayList<Long> array_total_insold_duration = new ArrayList<>();
    long total_in_month = 0;
    double total_coast = 0;
    int ye;
    int k = 0;
    long total_sold = 0;
    int mois=1;
    String code = "";

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
        View contentView = inflater.inflate(R.layout.activity_inventory_evaluation, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Evaluation");

        final AlertDialog.Builder dateAlt = new AlertDialog.Builder(InventoryEvaluation.this);
        final View view2 = LayoutInflater.from(InventoryEvaluation.this).inflate(R.layout.itempicker, null);
        TextView itclose = (TextView) view2.findViewById(R.id.Itclo);
        Spinner spitm = (Spinner) view2.findViewById(R.id.itspi);
        Button itOk = (Button) view2.findViewById(R.id.itOk);
        dateAlt.setView(view2);
        final AlertDialog dialog2 = dateAlt.create();


        Cursor curs = db.getData("SELECT * FROM prod where dateDel='01/01/2000'");
        while (curs.moveToNext()) {
            String cd= curs.getString(0);
               ITEMS.add(cd);


        }
        dialog2.show();
        final ArrayAdapter<String> dataAdaC = new ArrayAdapter<String>(InventoryEvaluation.this, R.layout.support_simple_spinner_dropdown_item, ITEMS);
        //ArrayAdapter<String> dataAda = ArrayAdapter.createFromResource(getApplicationContext(),llist,simple_list_item_1 );
        dataAdaC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spitm.setAdapter(dataAdaC);

        itclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        spitm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                code =  spitm.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "please select from the list ", Toast.LENGTH_SHORT).show();
                code = spitm.getItemAtPosition(0).toString();
            }
        });
        itOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor[] curs11 = {db.getData("SELECT * FROM prod ")};

                while (curs11[0].moveToNext()) {

                    if (code.equals(curs11[0].getString(0))) {

                        first = curs11[0].getString(1);

                    }
                }
                if (!first.equals("")) {
                    try {
                        Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();

                        Cursor cur2 = db.getData("SELECT * from sold where Id='"+code+"'");
                        while(cur2.moveToNext()){
                            Toast.makeText(getApplicationContext(),cur2.getLong(2)+"cur2",Toast.LENGTH_LONG).show();
                        }

                        init();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                dialog2.dismiss();
            }
        });


    }

    public void init() throws ParseException {
        db.QueryData();
        TableLayout stk = (TableLayout) findViewById(R.id.tableVal);
        stk.removeAllViews();
        TableRow tbrow0 = new TableRow(this);
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setText(first);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.parseColor("#ffffff"));
        tv.setBackgroundResource(R.drawable.descrip);
        tv.setPadding(20, 20, 20, 20);
        tv.setGravity(Gravity.CENTER);
        data.append(tv.getText().toString() + ",");
        tbrow0.addView(tv);
        TextView tv0 = new TextView(this);
        tv0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv0.setText("Purchase");
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundColor(Color.parseColor("#ffffff"));
        tv0.setBackgroundResource(R.drawable.descrip);
        tv0.setPadding(20, 20, 20, 20);
        tv0.setGravity(Gravity.CENTER);
        data.append(tv0.getText().toString() + ",");
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv1.setText(" Rate (each pair) ");
        data.append(tv1.getText().toString() + ",");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tv1.setBackgroundResource(R.drawable.descrip);
        tv1.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv2.setText(" Total coast ");
        tv2.setTextColor(Color.WHITE);
        data.append(tv2.getText().toString() + ",");

        tv2.setBackgroundResource(R.drawable.descrip);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv3.setText("FIFO");
        data.append(tv3.getText().toString() + ",");

        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tv3.setBackgroundColor(Color.parseColor("#333333"));
        tv3.setBackgroundResource(R.drawable.descrip);
        tv3.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv4.setText(" LIFO ");
        data.append(tv4.getText().toString() + ",");

        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tv4.setBackgroundResource(R.drawable.descrip);

        tv4.setGravity(Gravity.CENTER);
        tv4.setPadding(20, 20, 20, 20);

        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv5.setText("WAC");
        data.append(tv5.getText().toString() + ",");
        tv5.setTextColor(Color.WHITE);
        tv5.setPadding(20, 20, 20, 20);
        tv5.setBackgroundColor(Color.parseColor("#ffffff"));
        tv5.setBackgroundResource(R.drawable.descrip);
        tv5.setGravity(Gravity.CENTER);
        tbrow0.addView(tv5);
        stk.addView(tbrow0);

// LOOPP ROW

        Date dateInv = sdf.parse(dtinv);
        int mois= dateInv.getMonth()+1;
        int dur = Integer.parseInt(duration);
        int debut;

        if(mois-dur<=0){
            debut=0;
        }
        else{
            debut=mois-dur;
        }



        for (int i =debut+1; i <= mois; i++) {
            total_in_month=0;
            total_coast=0;
            double coa = 0.00;
          /*  Cursor[] cursor = {db.getData("SELECT * FROM purchase ")};
          while (cursor[0].moveToNext()) {
            if (code.equals(cursor[0].getString(0))) {
            */
                     if (i <= 0) {
                       Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_SHORT).show();
                     } else {
                         TableRow tbrow = new TableRow(this);
                        TextView t1v = new TextView(this);
                        t1v.setText(conv(i));
                        data.append(conv(i) + ",");
                        t1v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        t1v.setTextColor(Color.parseColor("#d3d3d3"));
                        t1v.setBackgroundColor(Color.parseColor("#ffffff"));
                        t1v.setPadding(20, 20, 20, 20);
                        t1v.setBackgroundResource(R.drawable.descrip);
                        t1v.setGravity(Gravity.CENTER);
                        tbrow.addView(t1v);
                        Cursor[] cuor = {db.getData("SELECT * FROM purchase ")};
                        while (cuor[0].moveToNext()) {
                            if (code.equals(cuor[0].getString(0))) {
                                String fg2 = cuor[0].getString(1);//mo
                                Date fgd2 = sdf.parse(fg2);

                                if (fgd2.getMonth() + 1 == i) {
                                    total_in_month = total_in_month + cuor[0].getLong(2);
                                }

                            }
                        }
                        array_total_in_duration.add(total_in_month);
                        TextView t2v = new TextView(this);
                        t2v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        t2v.setText(total_in_month + "");
                        t2v.setBackgroundResource(R.drawable.descrip);
                        data.append(total_in_month + ",");

                        t2v.setTextColor(Color.parseColor("#d3d3d3"));
                        t2v.setGravity(Gravity.CENTER);
                        t2v.setPadding(20, 20, 20, 20);
                        tbrow.addView(t2v);

                        Cursor[] cuor1 = {db.getData("SELECT * FROM purchase")};

                        while (cuor1[0].moveToNext()) {
                            if (code.equals(cuor1[0].getString(0))) {
                                String fg2 = cuor1[0].getString(1);
                                Date fgd2 = sdf.parse(fg2);

                                if (fgd2.getMonth() + 1 == i) {
                                    coa = cuor1[0].getDouble(3);
                                    array_total_item_coast_month.add(cuor1[0].getDouble(3));
                                }

                            }
                        }

                        TextView t3v = new TextView(this);
                        t3v.setText(String.valueOf(coa));
                        data.append(t3v.getText().toString() + ",");

                        t3v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        t3v.setTextColor(Color.parseColor("#d3d3d3"));
                        t3v.setBackgroundColor(Color.parseColor("#ffffff"));
                        t3v.setBackgroundResource(R.drawable.descrip);
                        t3v.setGravity(Gravity.CENTER);
                        t3v.setPadding(20, 20, 20, 20);

                        tbrow.addView(t3v);


                        Cursor[] cuor2 = {db.getData("SELECT * FROM purchase ")};
                        while (cuor2[0].moveToNext()) {
                            if (code.equals(cuor2[0].getString(0))) {
                                String fg2 = cuor2[0].getString(1);
                                Date fgd2 = sdf.parse(fg2);

                                if (fgd2.getMonth() + 1 == i) {
                                    total_coast = total_coast + cuor2[0].getLong(2) * cuor2[0].getDouble(3);
                                     array_total_item_coast_duration.add(total_coast);
                                }
                            }


                        }


                        TextView t4v = new TextView(this);
                        t4v.setText(total_coast + "");
                        data.append(total_coast + ",");

                        t4v.setTextColor(Color.parseColor("#d3d3d3"));
                        t4v.setBackgroundColor(Color.parseColor("#f5f5f5"));
                        t4v.setBackgroundResource(R.drawable.descrip);
                        t4v.setPadding(20, 20, 20, 20);

                        t4v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        t4v.setGravity(Gravity.CENTER);
                        tbrow.addView(t4v);
                        TextView t5v = new TextView(this);
                        t5v.setBackgroundColor(Color.parseColor("#ffffff"));
                        t5v.setText("");
                        data.append("" + ",");

                        t5v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        t5v.setTextColor(Color.parseColor("#d3d3d3"));
                        t5v.setGravity(Gravity.CENTER);
                        t5v.setBackgroundResource(R.drawable.descrip);
                        t5v.setPadding(20, 20, 20, 20);

                        tbrow.addView(t5v);

                        TextView t6v = new TextView(this);
                        t6v.setBackgroundColor(Color.parseColor("#ffffff"));
                        t6v.setText("");
                        data.append("" + ",");

                        t6v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        t6v.setTextColor(Color.parseColor("#d3d3d3"));
                        t6v.setGravity(Gravity.CENTER);
                        t6v.setBackgroundResource(R.drawable.descrip);
                        t6v.setPadding(20, 20, 20, 20);

                        tbrow.addView(t6v);


                        TextView t7v = new TextView(this);
                        t7v.setBackgroundColor(Color.parseColor("#ffffff"));
                        t7v.setText("");
                        data.append("" + "\n");

                        t7v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT));
                        t7v.setTextColor(Color.parseColor("#d3d3d3"));
                        t7v.setGravity(Gravity.CENTER);
                        t7v.setBackgroundResource(R.drawable.descrip);
                        t7v.setPadding(20, 20, 20, 20);

                        tbrow.addView(t7v);

                        stk.addView(tbrow);
                    }
              //  }
            //}
        }

        TableRow tbrow2 = new TableRow(this);
        TextView t1 = new TextView(this);
        t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        t1.setText("item Purchased");
        t1.setTextColor(Color.WHITE);
        t1.setBackgroundColor(Color.parseColor("#ffffff"));
        t1.setBackgroundResource(R.drawable.descrip);
        t1.setPadding(20, 20, 20, 20);
        t1.setGravity(Gravity.CENTER);
        data.append(t1.getText().toString() + ",");
        tbrow2.addView(t1);
        TextView t2 = new TextView(this);

        t2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
      /*  Cursor[] cuor1 = {db.getData("SELECT * FROM prod where  typePr='Goods' and prix <> " + null)};
        while (cuor1[0].moveToNext()){
            String fg2 = cursor[0].getString(20);
            Date fgd2 = sdf.parse(fg2);

            if (fgd2.getMonth()+1==i) {
                total_coast_in_month = total_coast_in_month + cuor[0].getLong(7);
            }



        }*/
        long u = 0;
        for (int j = 0; j < array_total_in_duration.size(); j++) {
            u = u + array_total_in_duration.get(j);
        }

        t2.setText(String.valueOf(u));
        t2.setTextColor(Color.WHITE);
        t2.setBackgroundColor(Color.parseColor("#ffffff"));
        t2.setBackgroundResource(R.drawable.descrip);
        t2.setPadding(20, 20, 20, 20);
        t2.setGravity(Gravity.CENTER);
        data.append(t2.getText().toString() + ",");
        tbrow2.addView(t2);
        TextView t3 = new TextView(this);
        t3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t3.setText("");
        data.append(t3.getText().toString() + ",");
        t3.setTextColor(Color.WHITE);
        t3.setGravity(Gravity.CENTER);
        t3.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t3.setBackgroundResource(R.drawable.descrip);
        t3.setPadding(20, 20, 20, 20);
        tbrow2.addView(t3);
        TextView t4 = new TextView(this);
        t4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t4.setText(" ");
        t4.setTextColor(Color.WHITE);
        data.append(t4.getText().toString() + ",");

        t4.setBackgroundResource(R.drawable.descrip);
        t4.setGravity(Gravity.CENTER);
        t4.setPadding(20, 20, 20, 20);

        tbrow2.addView(t4);
        TextView t5 = new TextView(this);
        t5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t5.setText("");
        data.append(t5.getText().toString() + ",");

        t5.setTextColor(Color.WHITE);
        t5.setGravity(Gravity.CENTER);
        t5.setBackgroundColor(Color.parseColor("#333333"));
        t5.setBackgroundResource(R.drawable.descrip);
        t5.setPadding(20, 20, 20, 20);

        tbrow2.addView(t5);
        TextView t6 = new TextView(this);
        t6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t6.setText(" ");
        data.append(t6.getText().toString() + ",");

        t6.setTextColor(Color.WHITE);
        t6.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t6.setBackgroundResource(R.drawable.descrip);

        t6.setGravity(Gravity.CENTER);
        t6.setPadding(20, 20, 20, 20);

        tbrow2.addView(t6);
        TextView t9 = new TextView(this);
        t9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t9.setText("");
        data.append(t9.getText().toString() + ",");
        t9.setTextColor(Color.WHITE);
        t9.setPadding(20, 20, 20, 20);
        t9.setBackgroundColor(Color.parseColor("#ffffff"));
        t9.setBackgroundResource(R.drawable.descrip);
        t9.setGravity(Gravity.CENTER);
        tbrow2.addView(t9);

      /*  TextView t7 = new TextView(this);
        t7.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t7.setText("");
        data.append(t7.getText().toString() + "\n");
        t7.setTextColor(Color.WHITE);
        t7.setPadding(20, 20, 20, 20);
        t7.setBackgroundColor(Color.parseColor("#ffffff"));
        t7.setBackgroundResource(R.drawable.descrip);
        t7.setGravity(Gravity.CENTER);
        tbrow2.addView(t7);*/

        stk.addView(tbrow2);


        TableRow tbrow4 = new TableRow(this);
        TextView tt1 = new TextView(this);
        tt1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tt1.setText("Item sold");
        tt1.setTextColor(Color.WHITE);
        tt1.setBackgroundColor(Color.parseColor("#ffffff"));
        tt1.setBackgroundResource(R.drawable.descrip);
        tt1.setPadding(20, 20, 20, 20);
        tt1.setGravity(Gravity.CENTER);
        data.append(tt1.getText().toString() + ",");
        tbrow4.addView(tt1);
        TextView tt2 = new TextView(this);
        tt2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        total_sold=0;



        if(mois-dur<=0){
            debut=0;
        }
        else{
            debut=mois-dur;
        }

        Cursor[] csol = {db.getData("SELECT * from sold ")};
            while (csol[0].moveToNext()) {
                if (code.equals(csol[0].getString(0))) {
                for (int i=debut+1; i <= mois; i++) {

                    String fg2 = csol[0].getString(1);
                    Date fgd2 = sdf.parse(fg2);
                    if (fgd2.getMonth() + 1 == i) {
                        Toast.makeText(getApplicationContext(), total_sold + "   totalsold", Toast.LENGTH_LONG).show();

                        total_sold = total_sold + csol[0].getLong(2);
                        Toast.makeText(getApplicationContext(), total_sold + "   totalsold2", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
        tt2.setText(String.valueOf(total_sold));
        tt2.setTextColor(Color.WHITE);
        tt2.setBackgroundColor(Color.parseColor("#ffffff"));
        tt2.setBackgroundResource(R.drawable.descrip);
        tt2.setPadding(20, 20, 20, 20);
        tt2.setGravity(Gravity.CENTER);
        data.append(tt2.getText().toString() + ",");
        tbrow4.addView(tt2);
        TextView tt3 = new TextView(this);


        tt3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tt3.setText(" ");
        data.append(tt3.getText().toString() + ",");
        tt3.setTextColor(Color.WHITE);
        tt3.setGravity(Gravity.CENTER);
        tt3.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tt3.setBackgroundResource(R.drawable.descrip);
        tt3.setPadding(20, 20, 20, 20);
        tbrow4.addView(tt3);
        TextView tt4 = new TextView(this);
        tt4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tt4.setText("  ");
        tt4.setTextColor(Color.WHITE);
        data.append(t4.getText().toString() + ",");

        tt4.setBackgroundResource(R.drawable.descrip);
        tt4.setGravity(Gravity.CENTER);
        tt4.setPadding(20, 20, 20, 20);

        tbrow4.addView(tt4);
        TextView tt5 = new TextView(this);
        tt5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tt5.setText("");
        data.append(tt5.getText().toString() + ",");

        tt5.setTextColor(Color.WHITE);
        tt5.setGravity(Gravity.CENTER);
        tt5.setBackgroundColor(Color.parseColor("#333333"));
        tt5.setBackgroundResource(R.drawable.descrip);
        tt5.setPadding(20, 20, 20, 20);

        tbrow4.addView(tt5);
        TextView tt6 = new TextView(this);
        tt6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tt6.setText("");
        data.append(tt6.getText().toString() + ",");

        tt6.setTextColor(Color.WHITE);
        tt6.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tt6.setBackgroundResource(R.drawable.descrip);

        tt6.setGravity(Gravity.CENTER);
        tt6.setPadding(20, 20, 20, 20);

        tbrow4.addView(tt6);
        TextView tt9 = new TextView(this);
        tt9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tt9.setText("");
        data.append(tt9.getText().toString() + "\n");
        tt9.setTextColor(Color.WHITE);
        tt9.setPadding(20, 20, 20, 20);
        tt9.setBackgroundColor(Color.parseColor("#ffffff"));
        tt9.setBackgroundResource(R.drawable.descrip);
        tt9.setGravity(Gravity.CENTER);
        tbrow4.addView(tt9);


       /* TextView tt10 = new TextView(this);
        tt10.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tt10.setText("");
        data.append(tt10.getText().toString() + "\n");
        tt10.setTextColor(Color.WHITE);
        tt10.setPadding(20, 20, 20, 20);
        tt10.setBackgroundColor(Color.parseColor("#ffffff"));
        tt10.setBackgroundResource(R.drawable.descrip);
        tt10.setGravity(Gravity.CENTER);
        tbrow4.addView(tt10);
*/
        stk.addView(tbrow4);


        TableRow tbrow5 = new TableRow(this);
        TextView t1tt = new TextView(this);
        t1tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        t1tt.setText("Item insold");
        t1tt.setTextColor(Color.WHITE);
        t1tt.setBackgroundColor(Color.parseColor("#ffffff"));
        t1tt.setBackgroundResource(R.drawable.descrip);
        t1tt.setPadding(20, 20, 20, 20);
        t1tt.setGravity(Gravity.CENTER);
        data.append(t1tt.getText().toString() + ",");
        tbrow5.addView(t1tt);


        long insold = u - total_sold;


        TextView t2tt = new TextView(this);
        t2tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        t2tt.setText(insold + "");
        t2tt.setTextColor(Color.WHITE);
        t2tt.setBackgroundColor(Color.parseColor("#ffffff"));
        t2tt.setBackgroundResource(R.drawable.descrip);
        t2tt.setPadding(20, 20, 20, 20);
        t2tt.setGravity(Gravity.CENTER);
        data.append(t2tt.getText().toString() + ",");
        tbrow5.addView(t2tt);
        TextView t3tt = new TextView(this);
        t3tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t3tt.setText(" ");
        data.append(t3tt.getText().toString() + ",");
        t3tt.setTextColor(Color.WHITE);
        t3tt.setGravity(Gravity.CENTER);
        t3tt.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t3tt.setBackgroundResource(R.drawable.descrip);
        t3tt.setPadding(20, 20, 20, 20);
        tbrow5.addView(t3tt);
        TextView t4tt = new TextView(this);
        t4tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t4tt.setText(" ");
        t4tt.setTextColor(Color.WHITE);
        data.append(t4tt.getText().toString() + ",");

        t4tt.setBackgroundResource(R.drawable.descrip);
        t4tt.setGravity(Gravity.CENTER);
        t4tt.setPadding(20, 20, 20, 20);

        tbrow5.addView(t4tt);
        TextView t5tt = new TextView(this);
        t5tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t5tt.setText("");
        data.append(t5tt.getText().toString() + ",");

        t5tt.setTextColor(Color.WHITE);
        t5tt.setGravity(Gravity.CENTER);
        t5tt.setBackgroundColor(Color.parseColor("#333333"));
        t5tt.setBackgroundResource(R.drawable.descrip);
        t5tt.setPadding(20, 20, 20, 20);

        tbrow5.addView(t5tt);
        TextView t6tt = new TextView(this);
        t6tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t6tt.setText("");
        data.append(t6tt.getText().toString() + ",");

        t6tt.setTextColor(Color.WHITE);
        t6tt.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t6tt.setBackgroundResource(R.drawable.descrip);

        t6tt.setGravity(Gravity.CENTER);
        t6tt.setPadding(20, 20, 20, 20);

        tbrow5.addView(t6tt);
        TextView t9tt = new TextView(this);
        t9tt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t9tt.setText("");
        data.append(t9tt.getText().toString() + ",");
        t9tt.setTextColor(Color.WHITE);
        t9tt.setPadding(20, 20, 20, 20);
        t9tt.setBackgroundColor(Color.parseColor("#ffffff"));
        t9tt.setBackgroundResource(R.drawable.descrip);
        t9tt.setGravity(Gravity.CENTER);
        tbrow5.addView(t9tt);
        stk.addView(tbrow5);


        TableRow tbrow6 = new TableRow(this);
        TextView ttt1 = new TextView(this);
        ttt1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        ttt1.setText("Total");
        ttt1.setTextColor(Color.WHITE);
        ttt1.setBackgroundColor(Color.parseColor("#ffffff"));
        ttt1.setBackgroundResource(R.drawable.descrip);
        ttt1.setPadding(20, 20, 20, 20);
        ttt1.setGravity(Gravity.CENTER);
        data.append(ttt1.getText().toString() + ",");
        tbrow6.addView(ttt1);
        TextView ttt2 = new TextView(this);

        ttt2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        ttt2.setText("");
        ttt2.setTextColor(Color.WHITE);
        ttt2.setBackgroundColor(Color.parseColor("#ffffff"));
        ttt2.setBackgroundResource(R.drawable.descrip);
        ttt2.setPadding(20, 20, 20, 20);
        ttt2.setGravity(Gravity.CENTER);
        data.append(ttt2.getText().toString() + ",");
        tbrow6.addView(ttt2);
        TextView ttt3 = new TextView(this);
        ttt3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        ttt3.setText("");
        data.append(ttt3.getText().toString() + ",");
        ttt3.setTextColor(Color.WHITE);
        ttt3.setGravity(Gravity.CENTER);
        ttt3.setBackgroundColor(Color.parseColor("#f5f5f5"));
        ttt3.setBackgroundResource(R.drawable.descrip);
        ttt3.setPadding(20, 20, 20, 20);
        tbrow6.addView(ttt3);
        TextView ttt4 = new TextView(this);
        ttt4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        double v = 0.000;
        for (int i = 0; i < array_total_item_coast_duration.size(); i++) {
            v = v + array_total_item_coast_duration.get(i);
        }
        ttt4.setText(v + "");
        ttt4.setTextColor(Color.WHITE);
        data.append(ttt4.getText().toString() + ",");

        ttt4.setBackgroundResource(R.drawable.descrip);
        ttt4.setGravity(Gravity.CENTER);
        ttt4.setPadding(20, 20, 20, 20);

        tbrow6.addView(ttt4);
        TextView ttt5 = new TextView(this);
        ttt5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        ttt5.setText("");
        data.append(ttt5.getText().toString() + ",");

        ttt5.setTextColor(Color.WHITE);
        ttt5.setGravity(Gravity.CENTER);
        ttt5.setBackgroundColor(Color.parseColor("#333333"));
        ttt5.setBackgroundResource(R.drawable.descrip);
        ttt5.setPadding(20, 20, 20, 20);

        tbrow6.addView(ttt5);
        TextView ttt6 = new TextView(this);
        ttt6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        ttt6.setText("");
        data.append(ttt6.getText().toString() + ",");

        ttt6.setTextColor(Color.WHITE);
        ttt6.setBackgroundColor(Color.parseColor("#f5f5f5"));
        ttt6.setBackgroundResource(R.drawable.descrip);

        ttt6.setGravity(Gravity.CENTER);
        ttt6.setPadding(20, 20, 20, 20);

        tbrow6.addView(ttt6);
        TextView ttt9 = new TextView(this);
        ttt9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        ttt9.setText("");
        data.append(ttt9.getText().toString() + ",");
        ttt9.setTextColor(Color.WHITE);
        ttt9.setPadding(20, 20, 20, 20);
        ttt9.setBackgroundColor(Color.parseColor("#ffffff"));
        ttt9.setBackgroundResource(R.drawable.descrip);
        ttt9.setGravity(Gravity.CENTER);
        tbrow6.addView(ttt9);


       /* TextView ttt10 = new TextView(this);
        ttt10.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        ttt10.setText("");
        data.append(ttt10.getText().toString() + ",");
        ttt10.setTextColor(Color.WHITE);
        ttt10.setPadding(20, 20, 20, 20);
        ttt10.setBackgroundColor(Color.parseColor("#ffffff"));
        ttt10.setBackgroundResource(R.drawable.descrip);
        ttt10.setGravity(Gravity.CENTER);
        tbrow6.addView(ttt10);*/
        stk.addView(tbrow6);


        TableRow tbrow7 = new TableRow(this);
        TextView t1t1 = new TextView(this);
        t1t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        t1t1.setText("avrige price/unit");
        t1t1.setTextColor(Color.WHITE);
        t1t1.setBackgroundColor(Color.parseColor("#ffffff"));
        t1t1.setBackgroundResource(R.drawable.descrip);
        t1t1.setPadding(20, 20, 20, 20);
        t1t1.setGravity(Gravity.CENTER);
        data.append(t1t1.getText().toString() + ",");
        tbrow7.addView(t1t1);
        TextView t2t2 = new TextView(this);
        t2t2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        t2t2.setText("");
        t2t2.setTextColor(Color.WHITE);
        t2t2.setBackgroundColor(Color.parseColor("#ffffff"));
        t2t2.setBackgroundResource(R.drawable.descrip);
        t2t2.setPadding(20, 20, 20, 20);
        t2t2.setGravity(Gravity.CENTER);
        data.append(t2t2.getText().toString() + ",");
        tbrow7.addView(t2t2);
        TextView t3t3 = new TextView(this);
        t3t3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t3t3.setText("");
        data.append(t3t3.getText().toString() + ",");
        t3t3.setTextColor(Color.WHITE);
        t3t3.setGravity(Gravity.CENTER);
        t3t3.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t3t3.setBackgroundResource(R.drawable.descrip);
        t3t3.setPadding(20, 20, 20, 20);
        tbrow7.addView(t3t3);
        TextView t4t4 = new TextView(this);
        t4t4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t4t4.setText("");
        t4t4.setTextColor(Color.WHITE);
        data.append(t4t4.getText().toString() + ",");

        t4t4.setBackgroundResource(R.drawable.descrip);
        t4t4.setGravity(Gravity.CENTER);
        t4t4.setPadding(20, 20, 20, 20);

        tbrow7.addView(t4t4);
        TextView t5t5 = new TextView(this);
        t5t5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        t5t5.setText("");
        data.append(t5t5.getText().toString() + ",");

        t5t5.setTextColor(Color.WHITE);
        t5t5.setGravity(Gravity.CENTER);
        t5t5.setBackgroundColor(Color.parseColor("#333333"));
        t5t5.setBackgroundResource(R.drawable.descrip);
        t5t5.setPadding(20, 20, 20, 20);

        tbrow7.addView(t5t5);
        TextView t6t6 = new TextView(this);
        t6t6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t6t6.setText("  ");
        data.append(t6t6.getText().toString() + ",");

        t6t6.setTextColor(Color.WHITE);
        t6t6.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t6t6.setBackgroundResource(R.drawable.descrip);

        t6t6.setGravity(Gravity.CENTER);
        t6t6.setPadding(20, 20, 20, 20);

        tbrow7.addView(t6t6);
        TextView t9t9 = new TextView(this);
        t9t9.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        double t=0.00 ;
if(u!=0) {t= v / u;}
        t9t9.setText(t + "");
        data.append(t9t9.getText().toString() + "\n");
        t9t9.setTextColor(Color.WHITE);
        t9t9.setPadding(20, 20, 20, 20);
        t9t9.setBackgroundColor(Color.parseColor("#ffffff"));
        t9t9.setBackgroundResource(R.drawable.descrip);
        t9t9.setGravity(Gravity.CENTER);
        tbrow7.addView(t9t9);
        stk.addView(tbrow7);

        TableRow tbrow8 = new TableRow(this);
        TextView t1t1t = new TextView(this);
        t1t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        t1t1t.setText(" Inventory value ");
        t1t1t.setTextColor(Color.WHITE);
        t1t1t.setBackgroundColor(Color.parseColor("#ffffff"));
        t1t1t.setBackgroundResource(R.drawable.descrip);
        t1t1t.setPadding(20, 20, 20, 20);
        t1t1t.setGravity(Gravity.CENTER);
        data.append(t1t1t.getText().toString() + ",");
        tbrow8.addView(t1t1t);
        TextView t2t2t = new TextView(this);
        t2t2t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        t2t2t.setText("");
        t2t2t.setTextColor(Color.WHITE);
        t2t2t.setBackgroundColor(Color.parseColor("#ffffff"));
        t2t2t.setBackgroundResource(R.drawable.descrip);
        t2t2t.setPadding(20, 20, 20, 20);
        t2t2t.setGravity(Gravity.CENTER);
        data.append(t2t2t.getText().toString() + ",");
        tbrow8.addView(t2t2t);
        TextView t3t3t = new TextView(this);
        t3t3t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t3t3t.setText("  ");
        data.append(t3t3t.getText().toString() + ",");
        t3t3t.setTextColor(Color.WHITE);
        t3t3t.setGravity(Gravity.CENTER);
        t3t3t.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t3t3t.setBackgroundResource(R.drawable.descrip);
        t3t3t.setPadding(20, 20, 20, 20);
        tbrow8.addView(t3t3t);
        TextView t4t4t = new TextView(this);
        t4t4t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        t4t4t.setText(" ");
        t4t4t.setTextColor(Color.WHITE);
        data.append(t4t4t.getText().toString() + ",");

        t4t4t.setBackgroundResource(R.drawable.descrip);
        t4t4t.setGravity(Gravity.CENTER);
        t4t4t.setPadding(20, 20, 20, 20);

        tbrow8.addView(t4t4t);
        TextView t5t5t = new TextView(this);

        t5t5t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        if (array_total_item_coast_month.size()>0) {
            double fifo = insold * array_total_item_coast_month.get(array_total_item_coast_month.size()-1);

            t5t5t.setText(String.valueOf(fifo));
        } else {
            double fifo = 0.000;
            t5t5t.setText(String.valueOf(fifo));
        }
        data.append(t5t5t.getText().toString() + ",");

        t5t5t.setTextColor(Color.WHITE);
        t5t5t.setGravity(Gravity.CENTER);
        t5t5t.setBackgroundColor(Color.parseColor("#333333"));
        t5t5t.setBackgroundResource(R.drawable.descrip);
        t5t5t.setPadding(20, 20, 20, 20);

        tbrow8.addView(t5t5t);
        TextView t6t6t = new TextView(this);
        if (array_total_item_coast_month.size()>0) {
            double lifo = insold * array_total_item_coast_month.get(0);
            t6t6t.setText(lifo + "");
        } else {
            double lifo = 0.000;
            t6t6t.setText(lifo + "");
        }

        t6t6t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));

        data.append(t6t6t.getText().toString() + ",");

        t6t6t.setTextColor(Color.WHITE);
        t6t6t.setBackgroundColor(Color.parseColor("#f5f5f5"));
        t6t6t.setBackgroundResource(R.drawable.descrip);

        t6t6t.setGravity(Gravity.CENTER);
        t6t6t.setPadding(20, 20, 20, 20);

        tbrow8.addView(t6t6t);
        TextView t9t9t = new TextView(this);
        t9t9t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        double wac = insold * t;
        t9t9t.setText("" + wac);
        data.append(t9t9t.getText().toString() + "\n");
        t9t9t.setTextColor(Color.WHITE);
        t9t9t.setPadding(20, 20, 20, 20);
        t9t9t.setBackgroundColor(Color.parseColor("#ffffff"));
        t9t9t.setBackgroundResource(R.drawable.descrip);
        t9t9t.setGravity(Gravity.CENTER);
        tbrow8.addView(t9t9t);
        stk.addView(tbrow8);

// you have donne the final rows you still have loop rows

    }

    public String conv(int i) {
        switch (i) {
            case 1:
                return "january";
            case 2:
                return "february";
            case 3:
                return "marsh";
            case 4:
                return "april";
            case 5:
                return "mai";
            case 6:
                return "june";
            case 7:
                return "july";
            case 8:
                return "august";
            case 9:
                return "september";
            case 10:
                return "october";
            case 11:
                return "november";
            case 12:
                return "december";
            default:
                return "wrong the year have 12 months ! ";
        }
    }
}
