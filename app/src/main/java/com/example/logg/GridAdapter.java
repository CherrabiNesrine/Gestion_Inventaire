package com.example.logg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> Itnom;
    private final ArrayList<String> Itcode;
    private final ArrayList<byte[]> imageId;

    public GridAdapter(Context c,  ArrayList<String> Itnom , ArrayList<String> Itcode ,ArrayList<byte[]> imageId) {
        mContext = c;
        this.Itnom = Itnom;
        this.Itcode=Itcode;
        this.imageId = imageId;
    }

    @Override
    public int getCount() {
        return Itnom.size();
    }

    @Override
    public Object getItem(int position) {
        return Itnom.get(position);
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
        return grid;*/
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid, null);
        }

        TextView nom= (TextView) convertView.findViewById(R.id.tnom);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imv);
        TextView code= (TextView) convertView.findViewById(R.id.tcode);
        nom.setText(Itnom.get(position));
        code.setText(Itcode.get(position));

        Bitmap bmp= BitmapFactory.decodeByteArray(imageId.get(position),0,imageId.get(position).length);
        imageView .setImageBitmap(bmp);


        return convertView;


    }
}
