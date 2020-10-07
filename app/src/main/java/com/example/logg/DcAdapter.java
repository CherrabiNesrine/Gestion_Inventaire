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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DcAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<String> Itnom;
    private final ArrayList<String> Itcode;
    private final ArrayList<byte[]> imageId;
    private final ArrayList<String> DateE;
    private final int k ;

    public DcAdapter(Context c,  ArrayList<String> Itnom , ArrayList<String> Itcode ,ArrayList<byte[]> imageId,int k,ArrayList<String> dateE) {
        mContext = c;
        this.Itnom = Itnom;
        this.Itcode=Itcode;
        this.imageId = imageId;
        this.k=k;
        this.DateE=dateE;
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
            convertView = inflater.inflate(R.layout.grid, null);
        }

        final TextView code= (TextView) convertView.findViewById(R.id.tcode);
        final TextView nom= (TextView) convertView.findViewById(R.id.tnom);
        TextView dtentr= (TextView) convertView.findViewById(R.id.dtentr);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imv);
TextView del = (TextView)convertView.findViewById(R.id.delIT);
del.setVisibility(View.VISIBLE);
        View finalConvertView = convertView;


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        del.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        DataBaseM db = new DataBaseM(finalConvertView.getContext());


        if(k==0) {
    db.Delete("sold", "Id=?", new String[]{Itcode.get(position)});
    Itcode.remove(position);
    Itnom.remove(position);
    imageId.remove(position);
    DateE.remove(position);
    notifyDataSetChanged();
}
else if(k==1){
    db.Delete("purchase", "Id=?", new String[]{Itcode.get(position)});
    Itcode.remove(position);
    Itnom.remove(position);
    imageId.remove(position);
    notifyDataSetChanged();
}
else {

}
    }
});
        final String CODE = code.getText().toString();
        if(Itcode.size()!=0){code.setText(Itcode.get(position));}
        if(Itnom.size()!=0){  nom.setText(Itnom.get(position)); }
        if(DateE.size()!=0){
            try {
                Date d = sdf.parse(DateE.get(position));
                dtentr.setText(d.getDate()+","+conv(d.getMonth()+1));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(imageId.size()!=0){
            Bitmap bmp= BitmapFactory.decodeByteArray(imageId.get(position),0,imageId.get(position).length);
            imageView .setImageBitmap(bmp);}
        return convertView;

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
