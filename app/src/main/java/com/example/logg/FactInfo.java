package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FactInfo extends SidebarMenu {
    EditText NIFC, RGcp, address, phnnum, SI, ema, SIZE, SCTR, faxx;
    String entreprise = "";
    String in = "";
    String Nom = "";
    String prenom = "";
    ImageView im;
    TextView edtO, gobak, done, cam;
    TextView nomJob;
    final int REQUEST_CODE_GALLERY = 999;
    Button btnFx, btnphn, btnST, btnEm;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        //reception of code from the pervius activity
        if (intent != null) {
            if (intent.hasExtra("in")) {
                in = intent.getStringExtra("in");
            }
            if (intent.hasExtra("nom")) {
                Nom = intent.getStringExtra("nom");
            }
            if (intent.hasExtra("prenom")) {
                prenom = intent.getStringExtra("prenom");
            }
            if (intent.hasExtra("entreprisee")) {
                entreprise = intent.getStringExtra("entreprisee");
            }
        }


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_fact_info, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Company INFORMATION ");
        db = new DataBaseM(this);

        im = (ImageView) findViewById(R.id.hello);

        nomJob = (TextView) findViewById(R.id.NOMENT);
        done = (TextView) findViewById(R.id.donne);
        cam = (TextView) findViewById(R.id.cam);
        edtO = (TextView) findViewById(R.id.edtO);
        gobak = (TextView) findViewById(R.id.goback);

        address = (EditText) findViewById(R.id.Address);
        phnnum = (EditText) findViewById(R.id.PhnNum);
        SCTR = (EditText) findViewById(R.id.SCTR);
        ema = (EditText) findViewById(R.id.ema);
        NIFC = (EditText) findViewById(R.id.NIFC);
        RGcp = (EditText) findViewById(R.id.RGcp);
        SIZE = (EditText) findViewById(R.id.SIZE);
        faxx = (EditText) findViewById(R.id.faxx);
        SI = (EditText) findViewById(R.id.si);

        btnST = (Button) findViewById(R.id.btnST);
        btnEm = (Button) findViewById(R.id.btnEm);
        btnphn = (Button) findViewById(R.id.btnPhn);

        db.QueryData();
        Cursor[] cursors = {db.getData("select * from Entreprise where Nom ='"+entreprise+"'")};
        while (cursors[0].moveToNext()) {
            byte[] bytess = cursors[0].getBlob(11);
            nomJob.setText(entreprise+ "\n" + cursors[0].getString(5));
            NIFC.setText(cursors[0].getString(0));
            RGcp.setText(cursors[0].getString(2));
            ema.setText(cursors[0].getString(6));
            SCTR.setText(cursors[0].getString(3));
            SI.setText(cursors[0].getString(9));
            SIZE.setText(cursors[0].getString(4));
            address.setText(cursors[0].getString(8));
            phnnum.setText(cursors[0].getString(7));
            faxx.setText(cursors[0].getString(10));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytess, 0, bytess.length);
            bitmap = ImageViewHalper.ImageFromDrawable(FactInfo.this, bitmap);

            im.setImageBitmap(bitmap);
        }

        cam.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(FactInfo.this);
                final View view = LayoutInflater.from(FactInfo.this).inflate(R.layout.ipic_ask, null);
                TextView title = (TextView) view.findViewById(R.id.ertitle);
                TextView message = (TextView) view.findViewById(R.id.messageer);
                Button acc = (Button) view.findViewById(R.id.btn_acc);
                Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                title.setText("OPEN");
                message.setText("do you want to open camera or gallery  ");
                dateAlt.setView(view);
                acc.setText("CAMERA");
                nacc.setText("GALLERY ");
                final AlertDialog dialog = dateAlt.create();
                acc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(FactInfo.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(FactInfo.this, new String[]{
                                    Manifest.permission.CAMERA}, 100);

                        }
                        dialog.dismiss();

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 100);

                    }
                });
                nacc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(FactInfo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                    }
                });
                dialog.show();

                return false;
            }
        });


        btnphn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = phnnum.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
               FactInfo.this.startActivity(callIntent);
            }
        });


        btnEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ema.getText().toString()});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");

                /* Send it off to the Activity-Chooser */
                FactInfo.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

        btnST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!SI.getText().toString().isEmpty()){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(SI.getText().toString()));
                FactInfo.this.startActivity(intent);}
                else {
                    Toast.makeText(getApplicationContext(),"No link is attributed",Toast.LENGTH_LONG).show();

                }
            }
        });

        gobak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(FactInfo.this, ContactP.class);
                intent1.putExtra("entreprisee", entreprise);
                intent1.putExtra("nom", Nom);
                intent1.putExtra("prenom", prenom);
                intent1.putExtra("in", in);

                startActivity(intent1);


            }
        });


        edtO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SI.setEnabled(true);
                phnnum.setEnabled(true);
                address.setEnabled(true);
                faxx.setEnabled(true);
                cam.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                edtO.setVisibility(View.GONE);

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phnnum.getText().toString().isEmpty()) {
                    phnnum.setError("this field can not be blank ");
                } else if (phnnum.getText().toString().length() != 10) {
                    phnnum.setError("your Number should have 10 numbers  ");
                }
                if (ema.getText().toString().isEmpty()) {
                    ema.setError("this field can not be blank ");
                } else if (isEmailValid(ema.getText().toString()) == false) {
                    ema.setError("email should be like : exp@exp.exp ");
                } else {



                       ContentValues values = new ContentValues();
                        values.put("email", ema.getText().toString());
                        values.put("address", address.getText().toString());
                        values.put("Fax", faxx.getText().toString());
                        values.put("Site", SI.getText().toString());
                        values.put("tlf", phnnum.getText().toString());
                        values.put("image",Imageviewtobyte(im));
                        db.Update("Entreprise", values, "Nom=?", new String[]{entreprise});



                    Toast.makeText(getApplicationContext(),"Edited",Toast.LENGTH_SHORT).show();
                    cam.setVisibility(View.INVISIBLE);
                    done.setVisibility(View.INVISIBLE);
                    edtO.setVisibility(View.VISIBLE);


                }
            }
        });


    }

    private byte[] Imageviewtobyte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "you don'thave permission", Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = ImageViewHalper.ImageFromDrawable(FactInfo.this, bitmap);
                im.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = ImageViewHalper.ImageFromDrawable(FactInfo.this, bitmap);
            im.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
