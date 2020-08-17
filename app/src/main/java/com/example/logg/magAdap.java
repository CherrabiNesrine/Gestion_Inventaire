package com.example.logg;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;

public class magAdap extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> Mnom;
    private final ArrayList<String> MTYPE;
    private final ArrayList<String> MUNIT;

    public magAdap(Context c,  ArrayList<String> Mnom , ArrayList<String> Mtype ,ArrayList<String> MUNIT) {
        mContext = c;
        this.Mnom=Mnom;
        this.MTYPE=Mtype;
        this.MUNIT=MUNIT;

    }

    @Override
    public int getCount() {
        return Mnom.size();
    }

    @Override
    public Object getItem(int position) {
        return Mnom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*View grid;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = inflater.inflate(R.layout.grid, null);
            TextView nom = (TextView) grid.findViewById(R.id.tnom);
            TextView code = (TextView) grid.findViewById(R.id.tcode);
            ImageView imageView = (ImageView) grid.findViewById(R.id.imv);
            nom.setText(Itnom[position]);
            nom.setText(Itcode[position]);
            imageView.setImageResource(imageId[position]);
        } else {
            grid = convertView;
        }
        return grid;*/


        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.maglistview, null);
        }

        final EditText nom= (EditText) convertView.findViewById(R.id.Mname);
        final EditText type= (EditText) convertView.findViewById(R.id.Mtype);
        final EditText unit=  (EditText) convertView.findViewById(R.id.MUnit);
        final TextView txt = (TextView) convertView.findViewById(R.id.Ma);
        nom.setText(Mnom.get(position));
        type.setText(MTYPE.get(position));
        unit.setText(MUNIT.get(position));
        final String NOOm = Mnom.get(position);
        char harf= NOOm.charAt(0);
        harf=Character.toUpperCase(harf);
        txt.setText(""+harf+"");

        Button magdel=(Button)convertView.findViewById(R.id.magdel);
        final Button magedi=(Button)convertView.findViewById(R.id.magedi);

        /*Bitmap bmp= BitmapFactory.decodeByteArray(imageId.get(position),0,imageId.get(position).length);
        imageView .setImageBitmap(bmp);*/
        magdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DataBaseM db = new DataBaseM(mContext);
                Cursor cursor = db.getData("SELECT * FROM prod where idmag = '"+NOOm+"'");
                if (cursor.moveToNext()){
                    Toast.makeText(mContext,"No sorry we can't their is products in this warehouse ",Toast.LENGTH_LONG).show();
                }


            }
        });
        magedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b=true;
                if(magedi.getText().toString().equals("OK")&& b==true){
                    DataBaseM db = new DataBaseM(mContext);
                    String NOOm2 =nom.getText().toString();
                    char harf= NOOm2.charAt(0);
                    harf=Character.toUpperCase(harf);
                    txt.setText(""+harf+"");
                    ContentValues values = new ContentValues();
                    values.put("nomMAg",nom.getText().toString());
                    values.put("typemag",type.getText().toString());
                    values.put("mesuremag",unit.getText().toString());

                    db.Update("mag",values, "nomMAg=?", new String[]{nom.getText().toString()});

                    Toast.makeText(mContext,"Edited",Toast.LENGTH_SHORT).show();
                    magedi.setFocusable(true);
                    magedi.setText("Edit");
b=false;
                }
                if(magedi.getText().toString().equals("Edit")&& b==false) {
                    magedi.setFocusable(true);
                    magedi.setText("OK") ;
                    nom.setEnabled(true);
                    nom.setFocusableInTouchMode(true);

                    nom.setClickable(true);
                    type.setEnabled(true);
                    type.setFocusableInTouchMode(true);

                    type.setClickable(true);
                    unit.setEnabled(true);
                    unit.setClickable(true);
                    Toast.makeText(mContext,"will be edited ",Toast.LENGTH_SHORT).show();

                          b=false;

                }


            }
        });


        return convertView;


    }
}
