package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.lowestOneBit;
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
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DataBaseM db;
    String code;
    Cursor cursor = null;
    String nomm = "";
    String typee = "";
    String unitt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
//recupriate the pervious page attribute
        if (intent != null) {

            if (intent.hasExtra("Nom")) {
                nomm = intent.getStringExtra("Nom");

            }
            if (intent.hasExtra("type")) {
                typee = intent.getStringExtra("type");

            }
            if (intent.hasExtra("unit")) {
                unitt = intent.getStringExtra("unit");

            }
        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_ajt_produit, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("add product");


        Toast.makeText(getApplicationContext(), "you are in " + nomm, Toast.LENGTH_LONG).show();


        surfaceView = (SurfaceView) findViewById(R.id.camerapreview);
        textView = (TextView) findViewById(R.id.textView);
        Next = (Button) findViewById(R.id.Nextbtn);
        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_add);
        db = new DataBaseM(this);
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
                        Intent intent = new Intent(AjtProduit.this, ViewlistP.class);
                        intent.putExtra("Nom", nomm);
                        intent.putExtra("type", typee);
                        intent.putExtra("unit", unitt);
                        startActivity(intent);
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
                try {
                    cursor = db.getData("SELECT * FROM prod WHERE ID ='" + code + "'");
                } catch (Exception e) {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(AjtProduit.this);
                    final View view = LayoutInflater.from(AjtProduit.this).inflate(R.layout.ipic_ask, null);
                    TextView title = (TextView) view.findViewById(R.id.ertitle);
                    TextView message = (TextView) view.findViewById(R.id.messageer);
                    Button acc = (Button) view.findViewById(R.id.btn_acc);

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

                if (cursor == null || cursor.getCount() <= 0) {
                    Intent intent = new Intent(AjtProduit.this, ajtproduit2.class);
                    intent.putExtra("hello", textView.getText().toString());
                    intent.putExtra("Nom", nomm);
                    intent.putExtra("type", typee);
                    intent.putExtra("unit", unitt);


                    startActivity(intent);
                } else {
                    if (cursor.moveToNext()) {
                        final AlertDialog.Builder dateAlt = new AlertDialog.Builder(AjtProduit.this);
                        final View view = LayoutInflater.from(AjtProduit.this).inflate(R.layout.camera, null);
                        TextView message = (TextView) view.findViewById(R.id.qntttm);
                        acc = (EditText) view.findViewById(R.id.btn_accqun);
                        EditText prix = (EditText) view.findViewById(R.id.btn_accPrix);
                        TextView close = (TextView) view.findViewById(R.id.btn_close_alr);
                        Button nacc = (Button) view.findViewById(R.id.btn_okk);
                        TextInputLayout p = (TextInputLayout) view.findViewById(R.id.p);
                        TextInputLayout q = (TextInputLayout) view.findViewById(R.id.q);
                        message.setText("products exists  ");
                        dateAlt.setView(view);
                        final AlertDialog dialog = dateAlt.create();
                        dialog.show();
                        acc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                            }
                        });
                        prix.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (prix.getText().toString().isEmpty()) {
                                    p.setError("this field can not be blank ");
                                }
                            }
                        });
                        acc.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (acc.getText().toString().isEmpty()) {
                                    q.setError("this field can not be blank ");
                                }
                            }
                        });
                        nacc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!acc.getText().toString().isEmpty()) {
                                    int nv = parseInt(acc.getText().toString());
                                    Date f = new Date();

                                    try {
                                       String ff  = sdf.format(f);
                                        long ik=0;
                                        Cursor cs = db.getData("select * from prod where ID='"+code+"'");
                                        while (cs.moveToNext()){
                                            ik=cs.getLong(7);
                                        }
                                        Cursor cs1 = db.getData("select * from purchase where Id='"+code+"' and DateE='"+ff+"'");
                                       if(cs1==null || cs1.getCount()<=0){
                                           db.InsertDataPurshase(code, f, nv, Double.parseDouble(prix.getText().toString()));
                                           ik=ik+nv;
                                           ContentValues vls1 = new ContentValues();
                                           vls1.put("quantite",ik);
                                           db.Update("prod",vls1,"ID=?",new String[]{code});

                                       }
                                       else{
                                           long kl=0;
                                           while (cs1.moveToNext()){
                                               kl=cs1.getLong(2);
                                           }
                                           kl=kl+nv;
                                           ContentValues vls = new ContentValues();
                                           vls.put("QNTT",kl);
                                           db.Update("purchase",vls,"Id=?",new String[]{code});
                                       }

                                        db.InsertDataPurshase(code, f, nv, Double.parseDouble(prix.getText().toString()));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    db.QueryData();

                                    int Qntt = cursor.getInt(7);
                                    nv = nv + Qntt;
                                    ContentValues values = new ContentValues();
                                    values.put("quantite", nv);
                                    db.Update(DataBaseM.Table_name, values, "ID=?", new String[]{code});
                                    dialog.dismiss();
                                    Intent intent = new Intent(AjtProduit.this, ViewlistP.class);
                                    intent.putExtra("hello", textView.getText().toString());
                                    intent.putExtra("Nom", nomm);
                                    intent.putExtra("type", typee);
                                    intent.putExtra("unit", unitt);
                                    startActivity(intent);
                                } else {
                                    int nv = 0;
                                    int Qntt = cursor.getInt(8);
                                    nv = nv + Qntt;
                                    ContentValues values = new ContentValues();
                                    values.put("quantite", nv);
                                    db.Update(DataBaseM.Table_name, values, "ID=?", new String[]{code});
                                    dialog.dismiss();
                                    Intent intent = new Intent(AjtProduit.this, ViewlistP.class);
                                    intent.putExtra("hello", textView.getText().toString());
                                    intent.putExtra("Nom", nomm);
                                    intent.putExtra("type", typee);
                                    intent.putExtra("unit", unitt);

                                    startActivity(intent);
                                }


                            }
                        });

                    } else {
                        Intent intent = new Intent(AjtProduit.this, ajtproduit2.class);
                        intent.putExtra("hello", textView.getText().toString());
                        intent.putExtra("Nom", nomm);
                        intent.putExtra("type", typee);
                        intent.putExtra("unit", unitt);

                        startActivity(intent);
                    }


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