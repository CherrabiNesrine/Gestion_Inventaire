/*
package com.example.logg;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class FactoryListenerAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> FctNom;
    private final ArrayList<String> FctAdress;
    private final ArrayList<String> FctTlf;
    private final ArrayList<String> Fcttype;
    private final ArrayList<byte[]> imageId;

    public FactoryListenerAdapter (Context c, ArrayList<String> FctNom ,ArrayList<String> FctAdress ,ArrayList<String> FctTlf, ArrayList<String> Fcttype,ArrayList<byte[]> imageId) {
        mContext = c;
        this.FctNom= FctNom;
        this.FctAdress=FctAdress;
        this.FctTlf=FctTlf;
        this.Fcttype=Fcttype;
        this.imageId = imageId;
    }

    @Override
    public int getCount() {
        return FctNom.size();
    }

    @Override
    public Object getItem(int position) {
        return FctNom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        return grid;//
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lst, null);
        }

        TextView nomFact= (TextView) convertView.findViewById(R.id.factname);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.factimg);
        TextView ADRESS= (TextView) convertView.findViewById(R.id.factTlf);
        TextView tlf = (TextView) convertView.findViewById(R.id.factTlf);
        TextView type = (TextView) convertView.findViewById(R.id.factType);
        nomFact.setText(FctNom.get(position));
        ADRESS.setText(FctAdress.get(position));
        tlf.setText(FctTlf.get(position));
        type.setText(Fcttype.get(position));

        Bitmap bmp= BitmapFactory.decodeByteArray(imageId.get(position),0,imageId.get(position).length);
        imageView .setImageBitmap(bmp);


        return convertView;


    }
}
*/