package com.example.logg;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AlertDialog;

import java.sql.Date;
import java.util.ArrayList;

public class magAdap extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> Mnom;
    private final ArrayList<String> MTYPE;
    private final ArrayList<String> MUNIT;

    public magAdap(Context c, ArrayList<String> Mnom, ArrayList<String> Mtype, ArrayList<String> MUNIT) {
        mContext = c;
        this.Mnom = Mnom;
        this.MTYPE = Mtype;
        this.MUNIT = MUNIT;

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




        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.maglistview, null);
        }


        final EditText nom = (EditText) convertView.findViewById(R.id.Mname);
        final EditText type = (EditText) convertView.findViewById(R.id.Mtype);
        final EditText unit = (EditText) convertView.findViewById(R.id.MUnit);
        final TextView txt = (TextView) convertView.findViewById(R.id.Ma);
        nom.setText(Mnom.get(position));
        type.setText(MTYPE.get(position));
        unit.setText(MUNIT.get(position));
        final String NOOm = Mnom.get(position);
        char harf = NOOm.charAt(0);
        harf = Character.toUpperCase(harf);
        txt.setText("" + harf + "");

        final Button magdel = (Button) convertView.findViewById(R.id.magdel);
        final Button magedi = (Button) convertView.findViewById(R.id.magedi);

        /*Bitmap bmp= BitmapFactory.decodeByteArray(imageId.get(position),0,imageId.get(position).length);
        imageView .setImageBitmap(bmp);*/
        magdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magdel.setFocusableInTouchMode(true);magdel.setFocusable(true);

                DataBaseM db = new DataBaseM(mContext);
                db.QueryData();
                Cursor cursor = db.getData("SELECT * FROM prod where idmag ='" +Mnom.get(position)+"'");

                if (((cursor == null) && (cursor.getCount() <= 0))) {


                    final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("Alert Dialog");
                    alertDialog.setMessage("do you really want to delete this warehouse 0_0 ");
                    //alertDialog.setIcon(R.drawable.welcome);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, "You clicked on OK", Toast.LENGTH_SHORT).show();

                            DataBaseM db =new DataBaseM(mContext);
                            db.Delete("mag","nomMAg=?",new String[]{Mnom.get(position)});
                            Mnom.remove(position);
                            MTYPE.remove(position);
                            MUNIT.remove(position);
                            notifyDataSetChanged();
                            magdel.setFocusableInTouchMode(false);magdel.setFocusable(false);

                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            alertDialog.dismiss();

                        }

                    });
                    alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.required);
                        }
                    });


                    alertDialog.show();







                }
                else{



                    Toast.makeText(mContext, "No sorry we can't their is products in this warehouse ", Toast.LENGTH_LONG).show();
                    magdel.setFocusableInTouchMode(false);
                    magdel.setFocusable(false);






                }


            }
        });

        magedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magedi.setFocusable(true);
                magedi.setFocusableInTouchMode(true);
                Toast.makeText(mContext, "this2", Toast.LENGTH_LONG).show();

                if (magedi.getText().toString().equals("OK")) {
                    Toast.makeText(mContext, "this2" + magedi.getText().toString(), Toast.LENGTH_SHORT).show();
                    DataBaseM db = new DataBaseM(mContext);
                    db.QueryData();
                    String NOOm2 = nom.getText().toString();
                    char harf = NOOm2.charAt(0);
                    harf = Character.toUpperCase(harf);
                    txt.setText("" + harf + "");
                    ContentValues values = new ContentValues();
                    values.put("nomMAg", nom.getText().toString());
                    values.put("typemag", type.getText().toString());
                    values.put("mesuremag", unit.getText().toString());

                    db.Update("mag", values, "nomMAg=?", new String[]{nom.getText().toString()});
                    Toast.makeText(mContext, "Edited", Toast.LENGTH_SHORT).show();

                    magedi.setText("Edit");
                    magedi.setFocusable(false);
                    nom.setFocusable(false);
                    type.setFocusable(false);
                    unit.setFocusable(false);

                }
                else if  (magedi.getText().toString().equals("Edit")) {
                    Toast.makeText(mContext, "this", Toast.LENGTH_SHORT).show();
                    nom.setFocusable(true);
                    nom.setFocusableInTouchMode(true);
                    nom.setCursorVisible(true);
                    magedi.setText("OK");
                    nom.setEnabled(true);
                    nom.setCursorVisible(true);

                     magedi.setFocusable(true);
                    magedi.setFocusableInTouchMode(true);


                    type.setCursorVisible(true);
                    unit.setCursorVisible(true);

                    type.setFocusable(true);
                    type.setFocusableInTouchMode(true);
                    unit.setFocusableInTouchMode(true);
                    unit.setFocusable(true);
                    Toast.makeText(mContext, "will be edited ", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(mContext, "lollo ", Toast.LENGTH_LONG).show();

                }

            }
        });


        return convertView;


    }
}
