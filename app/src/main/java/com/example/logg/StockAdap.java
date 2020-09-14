package com.example.logg;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

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
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(R.drawable.descrip);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv2);

        TextView tv5 = new TextView(convertView.getContext());
        tv5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        tv5.setText("OnHand");
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
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundColor(Color.parseColor("#f5f5f5"));
        tv4.setBackgroundResource(R.drawable.descrip);

        tv4.setGravity(Gravity.CENTER);
        tv4.setPadding(20, 20, 20, 20);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);
        db.QueryData();

        Cursor[] cursor = {db.getData("SELECT * FROM prod where dateDel = '01/01/2000' and sousCategorie ='"+sc+"'")};
        Toast.makeText(convertView.getContext(),RapNom.getText().toString(),Toast.LENGTH_SHORT).show();
        while (cursor[0].moveToNext()) {
            Toast.makeText(convertView.getContext(),"here i am ",Toast.LENGTH_SHORT).show();
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
            t3v.setPadding(20, 20, 20, 20);
            tbrow.addView(t3v);

            TextView t6v = new TextView(convertView.getContext());
            t6v.setText(cursor[0].getString(7));
            t6v.setPadding(20, 20, 20, 20);

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
            t4v.setPadding(20, 20, 20, 20);

            t4v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            TextView t5v = new TextView(convertView.getContext());
            t5v.setBackgroundColor(Color.parseColor("#ffffff"));
            double total_prix = 0;
            total_prix = cursor[0].getLong(7) * cursor[0].getDouble(8);
            t5v.setText(Coin + String.valueOf(total_prix));
            t5v.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            t5v.setTextColor(Color.parseColor("#d3d3d3"));
            t5v.setGravity(Gravity.CENTER);
            t5v.setBackgroundResource(R.drawable.descrip);
            t5v.setPadding(20, 20, 20, 20);
            tbrow.addView(t5v);
            stk.addView(tbrow);
        }


        return convertView;

    }

}
