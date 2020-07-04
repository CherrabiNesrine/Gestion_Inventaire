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

public class ProductsListenerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Produit> products;

    ProductsListenerAdapter(Context context, int layout, ArrayList<Produit> products) {
        this.context = context;
        this.layout = layout;
        this.products = products;
    }

    private class ViewHolder {
        ImageView imv;
        TextView tnom, tcode;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
       View row = view;
      ViewHolder viewHolder = new ViewHolder();
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //row = layoutInflater.inflate(layout, null);
            row = layoutInflater.inflate(R.layout.grid, parent, false);
            viewHolder.tnom = (TextView) row.findViewById(R.id.tnom);
            viewHolder.tcode = (TextView) row.findViewById(R.id.tcode);
            viewHolder.imv = (ImageView) row.findViewById(R.id.imv);
            row.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) row.getTag();
        }
        Produit pro = products.get(position);
        viewHolder.tnom.setText(pro.getName());
        viewHolder.tcode.setText(pro.getCode());
        byte[] proding = pro.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(proding, 0, proding.length);
        viewHolder.imv.setImageBitmap(bitmap);

       return row;
    }
}
