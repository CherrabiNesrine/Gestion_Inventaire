package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.Button;

import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import static java.lang.Integer.parseInt;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AjtProduit extends SidebarMenu {
    private static final int Camera_Request = 123;
    SurfaceView surfaceView;
    CameraSource cameraSource;
    EditText acc;
    BottomNavigationView navigationView;
    TextView textView;
    Button Next;
    BarcodeDetector barcodeDetector;
    DataBaseHalperP db;
    String code;
    Cursor cursor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_ajt_produit, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("add product");

        surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
        textView = (TextView) findViewById(R.id.textView);
        Next = (Button) findViewById(R.id.Nextbtn);
        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_add);
        db = new DataBaseHalperP(this);
        ActivityCompat.requestPermissions(AjtProduit.this, new String[]{Manifest.permission.CAMERA}, Camera_Request);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).setAutoFocusEnabled(true).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(holder);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCode = detections.getDetectedItems();
                if (qrCode.size() != 0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            textView.setText(qrCode.valueAt(0).displayValue);
                             code = textView.getText().toString();


                            Next.setVisibility(View.VISIBLE);
                            Next.setActivated(true);
                            Next.setClickable(true);
                            Next.setCursorVisible(true);
                            Next.setFocusable(true);
                            Next.setFocusableInTouchMode(true);


                        }
                    });
                }

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_view: {
                        startActivity(new Intent(AjtProduit.this, ViewlistP.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.nav_trash: {
                        startActivity(new Intent(AjtProduit.this, Trash.class));
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.QueryData();
                try{
                cursor = db.getData("SELECT * FROM prod WHERE ID ="+code);}
                catch (Exception e){
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(AjtProduit.this);
                    final View view= LayoutInflater.from(AjtProduit.this).inflate(R.layout.ipic_ask,null);
                    TextView title = (TextView)view.findViewById(R.id.ertitle);
                    TextView message=(TextView)view.findViewById(R.id.messageer);
                    Button acc=(Button)view.findViewById(R.id.btn_acc);

                    ImageView img = (ImageView)view.findViewById(R.id.help);
                    title.setText("Error");
                    message.setText("BARCODE OR QR CODE NOT OR RECOGNISED ");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    dialog.show();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });



                }
                ///////////////////////////////////////////////////////
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        final AlertDialog.Builder dateAlt = new AlertDialog.Builder(AjtProduit.this);
                        final View view= LayoutInflater.from(AjtProduit.this).inflate(R.layout.camera,null);
                        TextView message=(TextView)view.findViewById(R.id.qntttm);
                        acc=(EditText) view.findViewById(R.id.btn_accqun);
                        Button nacc=(Button)view.findViewById(R.id.btn_okk);
                        message.setText("products exists  ");
                        dateAlt.setView(view);
                        final AlertDialog dialog = dateAlt.create();
                        dialog.show();
                        acc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        nacc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!acc.getText().toString().isEmpty()) {
                                    int nv = parseInt(acc.getText().toString());
                                    int Qntt = cursor.getInt(8);
                                    nv = nv + Qntt;
                                    ContentValues values = new ContentValues();
                                    values.put("quantite", nv);
                                    db.Update(values, "ID=?", new String[]{code});
                                    dialog.dismiss();
                                    Intent intent = new Intent(AjtProduit.this,ViewlistP.class);
                                    intent.putExtra("hello", textView.getText().toString());
                                    startActivity(intent);
                                }
                                else {
                                    int nv = 0;
                                    int Qntt = cursor.getInt(8);
                                    nv = nv + Qntt;
                                    ContentValues values = new ContentValues();
                                    values.put("quantite", nv);
                                    db.Update(values, "ID=?", new String[]{code});
                                    dialog.dismiss();
                                    Intent intent = new Intent(AjtProduit.this,ViewlistP.class);
                                    intent.putExtra("hello", textView.getText().toString());
                                    startActivity(intent);
                                }


                            }
                        });

                    }
                    else{
                        Intent intent = new Intent(AjtProduit.this, ajtproduit2.class);
                        intent.putExtra("hello", textView.getText().toString());
                        startActivity(intent);
                    }


                }

                else {
                    Intent intent = new Intent(AjtProduit.this, ajtproduit2.class);
                    intent.putExtra("hello", textView.getText().toString());
                    startActivity(intent);
                }


            }
        });


    }

}
/*      final android.app.AlertDialog.Builder dateAlt = new android.app.AlertDialog.Builder(AjtProduit.this);
                                    final View view = LayoutInflater.from(AjtProduit.this).inflate(R.layout.camera, null);
                                    TextView title = (TextView) view.findViewById(R.id.qnttt);
                                    TextView message = (TextView) view.findViewById(R.id.qntttm);
                                   acc = (EditText) view.findViewById(R.id.btn_accqun);
                                   Button ok=(Button)view.findViewById(R.id.btn_okk);
                                   ImageView img = (ImageView)view.findViewById(R.id.qntt);
                                    img.setImageResource(R.drawable.ic_action_cameraa);
                                    message.setText("This product's already exists please enter the new quantity  ");
final AlertDialog dialog = dateAlt.create();
        dateAlt.setView(view);
        dateAlt.show();
        ok.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if (!acc.getText().toString().isEmpty()) {
        int nv = parseInt(acc.getText().toString());
        int Qntt = cursor.getInt(8);
        nv = nv + Qntt;
        ContentValues values = new ContentValues();
        values.put("quantite", nv);
        db.Update(values, "ID=?", new String[]{code});

        }
        dialog.dismiss();
        }
        });
*/