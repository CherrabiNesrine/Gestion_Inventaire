package com.example.logg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;

public class TrnsfAdp extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> ItWare1;
    private final ArrayList<String> ItWare2;
    private final ArrayList<String> Itcode;
    private final ArrayList<byte[]> imageId;
    private final ArrayList<String> dates;


    public TrnsfAdp(Context c, ArrayList<String> ItWare1 , ArrayList<String> ItWare2, ArrayList<String> Itcode , ArrayList<byte[]> imageId, ArrayList<String> dates) {
        mContext = c;
        this.ItWare1 = ItWare1;
        this.ItWare2 = ItWare2;
        this.Itcode=Itcode;
        this.imageId = imageId;
        this.dates=dates;

    }


   public int getCount() {
        return Itcode.size();
    }


    public Object getItem(int position) {
        return Itcode.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.trnadap, null);
        }

        final TextView code= (TextView) convertView.findViewById(R.id.codeDoc);
        final TextView Date= (TextView) convertView.findViewById(R.id.datetrn);
        final TextView warehouse1= (TextView) convertView.findViewById(R.id.wareh);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageDoc);

        final String CODE = code.getText().toString();

        warehouse1.setText(ItWare1.get(position)+","+ItWare2.get(position));

        code.setText(Itcode.get(position));
        if (imageId.size()!=0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageId.get(position), 0, imageId.get(position).length);
            imageView.setImageBitmap(bmp);
        }
        return convertView;


    }
}
