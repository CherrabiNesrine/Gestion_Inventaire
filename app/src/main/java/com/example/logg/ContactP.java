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
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
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
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ContactP extends SidebarMenu {
    ImageView im;
    LinearLayout luser;
    EditText typeuser, red, address, phnnum, face, ema, link, twit;
    TextView nomJob;
    DataBaseM db;
    String in = "";
    Button btnTwi,btnphn,btnvb,btnwa,btnLk,btnFb,btnEm;
    String Nom = "";
    String prenom = "";
    FactoryE Fact = new FactoryE();
    String entreprise = "";
    TextView edtO, gobak, done, cam;
    EditText compINFO;
    final int REQUEST_CODE_GALLERY = 999;

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
        View contentView = inflater.inflate(R.layout.activity_contact_p, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("OPERATOR INFORMATION ");

        db = new DataBaseM(this);
        im = (ImageView) findViewById(R.id.hello);
        luser = (LinearLayout) findViewById(R.id.luser);
        typeuser = (EditText) findViewById(R.id.typUsr);
        address = (EditText) findViewById(R.id.Address);
        phnnum = (EditText) findViewById(R.id.PhnNum);
        face = (EditText) findViewById(R.id.face);
        ema = (EditText) findViewById(R.id.ema);
        link = (EditText) findViewById(R.id.link);
        twit = (EditText) findViewById(R.id.twit);
        nomJob = (TextView) findViewById(R.id.nomJob);
        done = (TextView) findViewById(R.id.donne);
        cam = (TextView) findViewById(R.id.cam);
        edtO = (TextView) findViewById(R.id.edtO);
        gobak = (TextView) findViewById(R.id.gobac);
        compINFO = (EditText) findViewById(R.id.compINFO);

        btnEm=(Button)findViewById(R.id.btnEm);
        btnFb=(Button)findViewById(R.id.btnFb);
        btnTwi=(Button)findViewById(R.id.btntwi);
        btnLk=(Button)findViewById(R.id.btnLK);
        btnwa=(Button)findViewById(R.id.btnWA);
        btnvb=(Button)findViewById(R.id.btnVB);
        btnphn=(Button)findViewById(R.id.btnPhn);


        db.QueryData();
        Cursor[] cursors = {db.getData("select * from client where Nom ='"+ Nom +"'and Prenom='" + prenom +"'")};
        while (cursors[0].moveToNext()) {

            luser.setVisibility(View.VISIBLE);
            typeuser.setText(cursors[0].getString(3));
        }
        Cursor[] cursors1 = {db.getData("select * from OPERATEUR where  nomOp ='" + Nom + "' and prenomOp= '" + prenom + "'")};

        while (cursors1[0].moveToNext()) {
            byte[] bytess = cursors1[0].getBlob(11);
            nomJob.setText(Nom + " " + prenom + "\n"+ cursors1[0].getString(4));
            link.setText(cursors1[0].getString(8));
            twit.setText(cursors1[0].getString(10));
            ema.setText(cursors1[0].getString(6));
            face.setText(cursors1[0].getString(9));
            address.setText(cursors1[0].getString(7));
            phnnum.setText(cursors1[0].getString(5));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytess, 0, bytess.length);
            bitmap = ImageViewHalper.ImageFromDrawable(ContactP.this, bitmap);

            im.setImageBitmap(bitmap);

        }
        btnphn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number=phnnum.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+number));
                ContactP.this.startActivity(callIntent);
            }
        });
        btnvb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=phnnum.getText().toString();
                Uri uri = Uri.parse("tel:" + Uri.encode(number));
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setClassName("com.viber.voip", "com.viber.voip.WelcomeActivity");
                intent.setData(uri);
                ContactP.this.startActivity(intent);
            }
        });
        btnwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.voip.call";
                String displayName = null;
                String name=Nom;// here you can give static name.
                Long _id;

                ContentResolver resolver = getApplicationContext().getContentResolver();
               Cursor cursor = resolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
                while (cursor.moveToNext()) {
                    _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                    String mimeType = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));
                    if (displayName.equals(name)) {
                        if (mimeType.equals(mimeString)) {
                            String data = "content://com.android.contacts/data/" + _id;
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_VIEW);
                            sendIntent.setDataAndType(Uri.parse(data), mimeString);
                            sendIntent.setPackage("com.whatsapp");
                            ContactP.this.startActivity(sendIntent);
                        }
                    }
                }

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
               ContactP.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
        btnLk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!link.getText().toString().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link.getText().toString()));
                    ContactP.this.startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"No link is attributed",Toast.LENGTH_LONG).show();

                }
            }
        });
        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!face.getText().toString().isEmpty()){
                Intent facebookAppIntent;
                try {
                    facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(face.getText().toString()));
                   ContactP.this.startActivity(facebookAppIntent);
                } catch (ActivityNotFoundException e) {
                    facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(face.getText().toString()));
                    ContactP.this.startActivity(facebookAppIntent);
                }}
                else{
                    Toast.makeText(getApplicationContext(),"No link is attributed",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnTwi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!twit.getText().toString().isEmpty()){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(twit.getText().toString()));
                ContactP.this.startActivity(intent);}
                else{
                    Toast.makeText(getApplicationContext(),"No link is attributed",Toast.LENGTH_LONG).show();

                }
            }
        });
        gobak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (in.equals("fournisseur")) {
                    Intent intent1 = new Intent(ContactP.this, factoriesList.class);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(ContactP.this, clientsliste.class);
                    startActivity(intent1);
                }

            }
        });
        edtO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link.setEnabled(true);
                phnnum.setEnabled(true);
                address.setEnabled(true);
                face.setEnabled(true);
                twit.setEnabled(true);
                link.setEnabled(true);
                ema.setEnabled(true);
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



                    if (in.equals("client")) {
                        ContentValues values = new ContentValues();
                        values.put("email", ema.getText().toString());
                        values.put("address", address.getText().toString());
                        values.put("Linkedin", link.getText().toString());
                        values.put("facebook", face.getText().toString());
                        values.put("twitter", twit.getText().toString());
                        values.put("TelOperateur", phnnum.getText().toString());
                        db.Update("OPERATEUR", values, "nomOp=? and prenomOp=? ", new String[]{Nom, prenom});
                        ContentValues values1 = new ContentValues();
                        values.put("type", typeuser.getText().toString());
                        values.put("reduction", red.getText().toString());
                        db.Update("client", values1, "nomOp=? and prenomOp=? ", new String[]{Nom, prenom});

                    } else {
                        ContentValues values = new ContentValues();
                        values.put("email", ema.getText().toString());
                        values.put("address", address.getText().toString());
                        values.put("Linkedin", link.getText().toString());
                        values.put("facebook", face.getText().toString());
                        values.put("twitter", twit.getText().toString());
                        values.put("TelOperateur", phnnum.getText().toString());
                        values.put("logoImg",Imageviewtobyte(im));
                        db.Update("OPERATEUR", values, "nomOp=? and prenomOp=? ", new String[]{Nom, prenom});
                    }
                    Toast.makeText(getApplicationContext(),"Edited",Toast.LENGTH_SHORT).show();
                    cam.setVisibility(View.INVISIBLE);
                    done.setVisibility(View.INVISIBLE);
                    edtO.setVisibility(View.VISIBLE);
                    link.setEnabled(false);
                    phnnum.setEnabled(false);
                    address.setEnabled(false);
                    face.setEnabled(false);
                    twit.setEnabled(false);
                    link.setEnabled(false);
                    ema.setEnabled(false);


                }
            }
        });

        compINFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ContactP.this, FactInfo.class);
                intent1.putExtra("entreprisee", entreprise);
                intent1.putExtra("nom",Nom);
                intent1.putExtra("prenom",prenom);
                intent1.putExtra("in",in);
                startActivity(intent1);

            }
        });

        cam.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ContactP.this);
                final View view = LayoutInflater.from(ContactP.this).inflate(R.layout.ipic_ask, null);
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
                        if (ContextCompat.checkSelfPermission(ContactP.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ContactP.this, new String[]{
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
                        ActivityCompat.requestPermissions(ContactP.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                    }
                });
                dialog.show();

                return false;
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
                bitmap = ImageViewHalper.ImageFromDrawable(ContactP.this, bitmap);
                im.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = ImageViewHalper.ImageFromDrawable(ContactP.this, bitmap);
            im.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

