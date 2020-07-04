package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.R.layout.simple_spinner_item;

public class ajtproduit2 extends SidebarMenu {
EditText nom,qntete,prix;
Button add;


BottomNavigationView navigationView;
private boolean nomIsEmpty=true;
private boolean qnttIsEmpty=true;
private boolean prixIsEmpty=true;
private String Code;
String mesure,coins;
ImageView image;
final int REQUEST_CODE_GALLERY=999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        //reception of code from the pervius activity
        if (intent != null) {
            String codee = "";
            if (intent.hasExtra("hello")) {
                codee = intent.getStringExtra("hello");
            }
            Code=codee;

        }

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_ajtproduit2, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("About");

       nom=(EditText) findViewById(R.id.nom);
       qntete=(EditText)findViewById(R.id.qnt);
       prix=(EditText)findViewById(R.id.prix);
       add=(Button) findViewById(R.id.btajt);
       image=(ImageView) findViewById(R.id.imgv);
        image.setImageResource(R.mipmap.vv);
       image.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
               final View view= LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask,null);
               TextView title = (TextView)view.findViewById(R.id.ertitle);
               TextView message=(TextView)view.findViewById(R.id.messageer);
               Button acc=(Button)view.findViewById(R.id.btn_acc);
               Button nacc=(Button)view.findViewById(R.id.btn_nacc);
               ImageView img = (ImageView)view.findViewById(R.id.help);
               title.setText("OPEN");
               img.setImageResource(R.drawable.ic_action_cameraa);
               message.setText("do you want to open camera or gallery  ");
               dateAlt.setView(view);
               acc.setText("CAMERA");
               nacc.setText("GALLERY ");
               final AlertDialog dialog = dateAlt.create();
               acc.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if (ContextCompat.checkSelfPermission(ajtproduit2.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                         ActivityCompat.requestPermissions(ajtproduit2.this,new String[]{
                                 Manifest.permission.CAMERA},100);

                       }
                       dialog.dismiss();

                       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       startActivityForResult(intent,100);

                   }
               });
               nacc.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                       ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);

                   }
               });
               dialog.show();

               return false;
           }
       });

        navigationView=(BottomNavigationView)findViewById(R.id.Bottom_nav) ;
        navigationView.setSelectedItemId(R.id.nav_add);
        if (nom.getText().toString().trim().equalsIgnoreCase("")) {
            nom.setError("This field can not be blank");
        }

        //programming the spinners
        // quantity spinner
              /*  ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Mesure, simple_spinner_item);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mesur.setAdapter(dataAdapter);
                mesur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String text = parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
                        mesure = text;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
// programming coins spinner
        ArrayAdapter<CharSequence> dataAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.coins, simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
                coins = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
                //programming name edit text
        nom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (nom.getText().toString().trim().equalsIgnoreCase("")) {
                    nom.setError("This field can not be blank");

                }
                else {
                    nom.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nom.getText().toString().trim().equalsIgnoreCase("")) {
                    nom.setError("This field can not be blank");
                }
                else {
                    nom.setError(null);
                }

                }
        });

//    Quantity edit text
        qntete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (qntete.getText().toString().trim().equalsIgnoreCase("")) {
                    qntete.setError("This field can not be blank");
                }
                else {
                    qntete.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (qntete.getText().toString().trim().equalsIgnoreCase("")) {
                    qntete.setError("This field can not be blank");
                }
                else {
                    qntete.setError(null);
                }
            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_view :{ startActivity(new Intent(ajtproduit2.this,ViewlistP.class));
                        overridePendingTransition(0,0); return true;}

                    case R.id.nav_trash : {startActivity(new Intent(ajtproduit2.this,Trash.class)); overridePendingTransition(0,0); return true;}
                }
                return false;
            }
        });
        // price edit text
        prix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (prix.getText().toString().trim().equalsIgnoreCase("")) {
                    prix.setError("This field can not be blank");
                }
                else {
                    prix.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (prix.getText().toString().trim().equalsIgnoreCase("")) {
                    prix.setError("This field can not be blank");
                }
                else {
                    prix.setError(null);
                }
            }
        });


       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if((nom.getText().toString().isEmpty())||(qntete.getText().toString().isEmpty())||(prix.getText().toString().isEmpty())) {
                   final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                   final View view= LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_alrt_neg,null);
                   TextView title = (TextView)view.findViewById(R.id.ertitlen);
                   TextView message=(TextView)view.findViewById(R.id.messageern);
                   Button acc=(Button)view.findViewById(R.id.btn_acc);
                   Button nacc=(Button)view.findViewById(R.id.btn_nacc);
                   ImageView img = (ImageView)view.findViewById(R.id.help);
                   title.setText("Empty Fileds");
                   message.setText("Please  fill all fields ! ");
                   dateAlt.setView(view);
                   final AlertDialog dialog = dateAlt.create();
                   acc.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   dialog.show();


               }
               else {
                   Toast.makeText(getApplicationContext(),"befor",Toast.LENGTH_LONG).show();
                   byte[] bytes= Imageviewtobyte(image);

                   Intent intent = new Intent(ajtproduit2.this, Factory.class);
                   intent.putExtra("Nom", nom.getText().toString());
                   Toast.makeText(getApplicationContext(),nom.getText().toString(),Toast.LENGTH_LONG).show();
                   intent.putExtra("Quentite", Integer.parseInt(qntete.getText().toString()));
                   intent.putExtra("prix", Double.parseDouble(prix.getText().toString()));
                   Toast.makeText(getApplicationContext(),prix.getText().toString(),Toast.LENGTH_LONG).show();
                   intent.putExtra("code", Code);
                 /*  intent.putExtra("mesure", mesure);
                   intent.putExtra("coins", coins);
*/
                   intent.putExtra("img",bytes);
                   Toast.makeText(getApplicationContext(),"after",Toast.LENGTH_LONG).show();
                   startActivity(intent);

                   //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);


               }
           }
       });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(),"you don'thave permission",Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data!= null){
            Uri uri = data.getData();
            try{
                InputStream inputStream= getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               image.setImageBitmap(bitmap);
            }
         catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode==100){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private byte[] Imageviewtobyte(ImageView image){
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,50,stream);
        byte[] byteArray =stream.toByteArray();
        return byteArray;
    }
}
