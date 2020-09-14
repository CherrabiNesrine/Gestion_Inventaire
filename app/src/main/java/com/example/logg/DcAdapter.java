package com.example.logg;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class DcAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> Itnom;
    private final ArrayList<String> Itcode;
    private final ArrayList<byte[]> imageId;

    public DcAdapter(Context c,  ArrayList<String> Itnom , ArrayList<String> Itcode ,ArrayList<byte[]> imageId) {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.docadp, null);
        }

        final TextView code= (TextView) convertView.findViewById(R.id.codeDoc);
        final TextView nom= (TextView) convertView.findViewById(R.id.NomDoc);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageDoc);

        final String CODE = code.getText().toString();

        nom.setText(Itnom.get(position));
        code.setText(Itcode.get(position));
        Bitmap bmp= BitmapFactory.decodeByteArray(imageId.get(position),0,imageId.get(position).length);
        imageView .setImageBitmap(bmp);
        return convertView;


    }
}
