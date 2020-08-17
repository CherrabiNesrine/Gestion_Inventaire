/*package com.example.logg;

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
import java.util.List;

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
        return mApps.size();
    }

    @Override
    public Object getItem(int position) {
        return mApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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



        ViewlistP.CheckableLayout l;
        ImageView i;

        if (convertView == null) {
            i = new ImageView(MainActivity.this);
            i.setScaleType(ImageView.ScaleType.FIT_CENTER);
            i.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
            l = new CheckableLayout(MainActivity.this);
            l.setLayoutParams(new GridView.LayoutParams(
                    GridView.LayoutParams.WRAP_CONTENT,
                    GridView.LayoutParams.WRAP_CONTENT));
            l.addView(i);
        } else {
            l = (CheckableLayout) convertView;
            i = (ImageView) l.getChildAt(0);
        }

        ResolveInfo info = mApps.get(position);
        i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));


        return convertView;


    }
}
*/

