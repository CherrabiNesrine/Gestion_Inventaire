package com.example.logg;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import java.io.ByteArrayOutputStream;

import static com.example.logg.LoginActivity.Id;


public class DashboardActivity extends SidebarMenu{


    static String UserHolder,PrenomHolder,NameHolder,IdHolder,JobHolder,Passholder;
    TextView user,nom,prenom,id,job;
    Button LogOUT,edit ;
ImageView image;SQLiteHelper sqLiteHelper;   SQLiteDatabase sqLiteDatabaseObj;


    Button btnScanBarcode;String TempPassword = "", ID = "", Usr = "", Nom = "", Prenom = "", Job = "", password = "";byte[] profileimg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_dashboard, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        user = (TextView)findViewById(R.id.textView1);
        nom = (TextView)findViewById(R.id.textView2);
        prenom = (TextView)findViewById(R.id.textView3);
        image = (ImageView)findViewById(R.id.img);
        id = (TextView)findViewById(R.id.textView4);
        job= (TextView)findViewById(R.id.textView5);
        LogOUT = (Button)findViewById(R.id.logout);
        edit = (Button)findViewById(R.id.editbutton);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        Intent intent = getIntent();
        Cursor cursor;
        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabaseObj = sqLiteHelper.getReadableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_ID + "=?", new String[]{Id}, null, null, null);

        while (cursor.moveToNext()) {


            if (cursor.isFirst()) {

                cursor.moveToFirst();


                TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));

                Usr = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_username));

                ID = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID));

                Nom = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

                Prenom = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Prenom));

                Job = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_job));

                password = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));

                profileimg = cursor.getBlob(cursor.getColumnIndex(SQLiteHelper.KEY_IMG));

                cursor.close();


            }
        }
        if(profileimg !=null || intent.hasExtra("image"))
        {image.setImageBitmap(getImage(profileimg));}
        User us = new User(ID, Nom, Prenom, password, Usr, Job);
        user.setText(String.valueOf(us.getUserName()));
        nom.setText(String.valueOf(us.getName())+" "+String.valueOf(us.getPrenom()));
        id.setText(String.valueOf(us.getId()));
        job.setText(String.valueOf(us.getJob()));


        LogOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Toast.makeText(DashboardActivity.this,"See you soon !", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(DashboardActivity.this, EditProfile.class);
                startActivity(i);
            }
        });
        btnScanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AjtProduit.class);
                startActivity(i);
            }
        });

    }


    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}