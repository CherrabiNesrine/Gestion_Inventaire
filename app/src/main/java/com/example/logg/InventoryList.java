package com.example.logg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;

public class InventoryList extends SidebarMenu {
    DataBaseM db = new DataBaseM(this);
    ListView listView;
    static INVadap adapter = null;
    int FinREF = 0;
    String sFinREF = "";
    ArrayList<String> dates = new ArrayList<String>();
    ArrayList<String> StatusAr = new ArrayList<String>();
    ArrayList<String> Type = new ArrayList<String>();
    ArrayList<String> Reference = new ArrayList<String>();
    ArrayList<String> DURATION = new ArrayList<String>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sd = new SimpleDateFormat("YY");
    String duration = "";
    String type = "";
    ImageView imv ;
    TextView empt;
    Date nvDate = new Date();
    String SnvDate = sd.format(nvDate);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_inventory_list, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inventory");

        db.QueryData();
        listView = (ListView) findViewById(R.id.inventoryList);
        adapter = new INVadap(this, Reference, dates, Type, StatusAr);
        listView.setAdapter(adapter);
        Cursor[] cursor = {db.getData("SELECT * from inventaire")};
        if (cursor[0]==null || cursor[0].getCount()<=0){
            imv =(ImageView)findViewById(R.id.empty);
            empt =(TextView)findViewById(R.id.emptyTxt);
            imv.setVisibility(View.VISIBLE);
            empt.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else {
        while (cursor[0].moveToNext()) {

            String Ref = cursor[0].getString(0);
            Reference.add(Ref);
            String dat = cursor[0].getString(1);
            dates.add(dat);
            String type = cursor[0].getString(2);
            Type.add(type);
            String Status = cursor[0].getString(3);
            StatusAr.add(Status);
            String duratio = cursor[0].getString(4);
            DURATION.add(duratio);
            adapter.notifyDataSetChanged();

            FinREF++;


        }}


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(InventoryList.this, InventoryMain.class);
                intent.putExtra("Date", dates.get(i));
                intent.putExtra("duration", DURATION.get(i));
                startActivity(intent);
            }
        });


    }

    class INVadap extends BaseAdapter {
        ArrayList<String> ref;
        ArrayList<String> dates;
        ArrayList<String> types;
        ArrayList<String> status;
        Context mContext;

        INVadap(Context context, ArrayList<String> Ref, ArrayList<String> date, ArrayList<String> Type, ArrayList<String> Status) {
            this.mContext = context;
            this.ref = Ref;
            this.dates = date;
            this.types = Type;
            this.status = Status;

        }

        @Override
        public int getCount() {
            return ref.size();
        }

        @Override
        public Object getItem(int i) {
            return ref.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {


            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.activity_inv_adap, null);
            }

            final EditText Ref = (EditText) convertView.findViewById(R.id.INVreff);
            final EditText stat = (EditText) convertView.findViewById(R.id.Statuss);
            final EditText date = (EditText) convertView.findViewById(R.id.INVdate);
            final EditText typpe = (EditText) convertView.findViewById(R.id.INVtyp);
            final Button trash = (Button) convertView.findViewById(R.id.INVdrop);
            final TextView txt = (TextView) convertView.findViewById(R.id.Ma);
            trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.QueryData();
                    db.Delete("inventaire", "RefInv=?", new String[]{ref.get(i)});

                    db.QueryData("DROP TABLE IF EXISTS inventaire ");
                    db.QueryData();

                    ref.remove(i);
                    dates.remove(i);
                    types.remove(i);
                    status.remove(i);
                    DURATION.remove(i);
                    adapter.notifyDataSetChanged();

                   for (int k = 0; k < ref.size(); k++) {
                        if (types.get(k).equals("rolling")) {
                            FinREF = 0;
                            Cursor cuK = db.getData("SELECT * FROM inventaire where tYPiNV = 'rolling'");
                            while (cuK.moveToNext()) {
                                FinREF++;

                            }
                            if (DURATION.get(k).equals(1)) {
                                try {
                                    nvDate = sd.parse(SnvDate);
                                    int ko = Integer.parseInt(sd.format(nvDate));

                                    sFinREF = "RA" + String.valueOf(i) + String.format("%04d", FinREF);


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            } else if (DURATION.get(k).equals(12)) {
                                try {
                                    nvDate = sd.parse(SnvDate);
                                    int ko = Integer.parseInt(sd.format(nvDate));

                                    sFinREF = "RC" + String.valueOf(ko) + String.format("%04d", FinREF);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    nvDate = sd.parse(SnvDate);
                                    int ko = Integer.parseInt(sd.format(nvDate));

                                    sFinREF = "RB" + String.valueOf(ko) + String.format("%04d", FinREF);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            ref.set(k, sFinREF);
                            try {
                                db.InsertDataINV(ref.get(k), sdf.parse(dates.get(k)), types.get(k), status.get(k), DURATION.get(k));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else if (type.equals("Annual")) {
                            FinREF = 0;
                            Cursor cuK = db.getData("SELECT * FROM inventaire where tYPiNV = 'Annual'");
                            while (cuK.moveToNext()) {
                                FinREF++;

                            }
                            try {
                                nvDate = sd.parse(SnvDate);
                                int ko = Integer.parseInt(sd.format(nvDate));
                                sFinREF = "D" + String.valueOf(ko) + String.format("%04d", FinREF);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            ref.set(i, sFinREF);
                            try {
                                db.InsertDataINV(ref.get(k), sdf.parse(dates.get(k)), types.get(k), status.get(k), DURATION.get(k));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } else {

                            Cursor cursorP = db.getData("select * from inventaire where tYPiNV = 'permanent' ");
                            FinREF = 0;
                            while (cursorP.moveToNext()) {
                                FinREF++;

                            }
                            try {
                                nvDate = sd.parse(SnvDate);
                                int ko = Integer.parseInt(sd.format(nvDate));
                                sFinREF = "DP" + String.valueOf(ko) + String.format("%04d", FinREF);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            ref.set(i, sFinREF);
                            adapter.notifyDataSetChanged();
                            try {
                                db.InsertDataINV(ref.get(k), sdf.parse(dates.get(k)), types.get(k), status.get(k), DURATION.get(k));
                                adapter.notifyDataSetChanged();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }



                }
            });

            Ref.setText(ref.get(i));
            date.setText(dates.get(i));
            typpe.setText(types.get(i));
            stat.setText(status.get(i));
            final String NOOm = types.get(i);
            char harf = NOOm.charAt(0);
            harf = Character.toUpperCase(harf);
            txt.setText("" + harf + "");


            return convertView;


        }


    }

    private void SetReference() {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);

        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            final AlertDialog.Builder addINV = new AlertDialog.Builder(InventoryList.this);
            final View view3 = LayoutInflater.from(InventoryList.this).inflate(R.layout.ajt_inv, null);
            addINV.setView(view3);
            final AlertDialog dialog2 = addINV.create();
            TextView CLOSE = (TextView) view3.findViewById(R.id.invCL);
            final EditText REFFERENCE = (EditText) view3.findViewById(R.id.invRef);
            final EditText DATEinv = (EditText) view3.findViewById(R.id.DateINV);
            final Spinner dureINV = (Spinner) view3.findViewById(R.id.durINV);
            final Spinner typeINV = (Spinner) view3.findViewById(R.id.TypeINV);
            final Spinner stINV = (Spinner) view3.findViewById(R.id.staINV);
            Button kk = (Button) view3.findViewById(R.id.okINV);

            CLOSE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.dismiss();
                }
            });

            ArrayAdapter<CharSequence> dataAdapter4 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.TypeInventaire, simple_spinner_dropdown_item);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeINV.setAdapter(dataAdapter4);


            ArrayAdapter<CharSequence> dataAdaptr = ArrayAdapter.createFromResource(getApplicationContext(), R.array.StatusInventaire, simple_spinner_dropdown_item);
            dataAdaptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stINV.setAdapter(dataAdaptr);


            String stat = stINV.getSelectedItem().toString();


            dialog2.show();
            Date d = new Date();
            DATEinv.setText(sdf.format(d));


            db.QueryData();

            typeINV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    type = typeINV.getSelectedItem().toString();
                    if (type.equals("rolling")) {

                        dureINV.setVisibility(View.VISIBLE);
                        ArrayAdapter<CharSequence> dataAdapt = ArrayAdapter.createFromResource(getApplicationContext(), R.array.dureeInv, simple_spinner_dropdown_item);
                        dataAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dureINV.setAdapter(dataAdapt);


                        FinREF = 0;
                        Cursor cuK = db.getData("SELECT * FROM inventaire where tYPiNV = 'rolling'");
                        while (cuK.moveToNext()) {
                            FinREF++;

                        }

                        try {
                            nvDate = sd.parse(SnvDate);

                            int ko = Integer.parseInt(sd.format(nvDate));
                            sFinREF = "RC" + String.valueOf(ko) + String.format("%04d", FinREF);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        REFFERENCE.setText(sFinREF);

                    } else if (type.equals("Annual")) {
                        FinREF = 0;
                        Cursor cuK = db.getData("SELECT * FROM inventaire where tYPiNV = 'Annual'");
                        while (cuK.moveToNext()) {
                            FinREF++;

                        }
                        try {
                            nvDate = sd.parse(SnvDate);
                            int ko = Integer.parseInt(sd.format(nvDate));
                            sFinREF = "D" + String.valueOf(ko) + String.format("%04d", FinREF);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        REFFERENCE.setText(sFinREF);
                    } else {

                        Cursor cursorP = db.getData("select * from inventaire where tYPiNV = 'permanent' ");
                        FinREF = 0;
                        while (cursorP.moveToNext()) {
                            FinREF++;

                        }
                        try {
                            nvDate = sd.parse(SnvDate);
                            int ko = Integer.parseInt(sd.format(nvDate));
                            sFinREF = "DP" + String.valueOf(ko) + String.format("%04d", FinREF);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        REFFERENCE.setText(sFinREF);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            dureINV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    duration = dureINV.getSelectedItem().toString();

                    FinREF = 0;
                    Cursor cuK = db.getData("SELECT * FROM inventaire where tYPiNV = 'rolling'");
                    while (cuK.moveToNext()) {
                        FinREF++;

                    }
                    if (dureINV.getSelectedItem().toString().equals(1)) {
                        try {
                            nvDate = sd.parse(SnvDate);
                            int ko = Integer.parseInt(sd.format(nvDate));

                            sFinREF = "RA" + String.valueOf(i) + String.format("%04d", FinREF);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        REFFERENCE.setText(sFinREF);
                    } else if (dureINV.getSelectedItem().toString().equals(12)) {
                        try {
                            nvDate = sd.parse(SnvDate);
                            int ko = Integer.parseInt(sd.format(nvDate));

                            sFinREF = "RC" + String.valueOf(ko) + String.format("%04d", FinREF);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            nvDate = sd.parse(SnvDate);
                            int ko = Integer.parseInt(sd.format(nvDate));

                            sFinREF = "RB" + String.valueOf(ko) + String.format("%04d", FinREF);
                            REFFERENCE.setText(sFinREF);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            kk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (type.equals("Annual")) {


                        Cursor c1 = db.getData("SELECT * FROM inventaire ");
                        if (c1.getCount() <= 0 || c1 == null) {
                            try {


                                db.InsertDataINV(REFFERENCE.getText().toString(), sdf.parse(DATEinv.getText().toString()), type, stat, "12");
                                Toast.makeText(getApplicationContext(), "successed!", Toast.LENGTH_LONG);
                                Reference.add(REFFERENCE.getText().toString());
                                dates.add(DATEinv.getText().toString());
                                Type.add(type);
                                StatusAr.add(stat);
                                DURATION.add("12");
                                adapter.notifyDataSetChanged();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {

                            while (c1.moveToNext()) {
                                String req = c1.getString(1);
                                try {
                                    Date dreq = sdf.parse(req);
                                    Date LC = new Date();

                                    if (dreq.getYear() != LC.getYear() + 1900) {
                                        Toast.makeText(getApplicationContext(), "inventory made this year ! ", Toast.LENGTH_LONG).show();
                                    } else {
                                        db.InsertDataINV(REFFERENCE.getText().toString(), sdf.parse(DATEinv.getText().toString()), type, stat, "3");
                                        Toast.makeText(getApplicationContext(), "successed!", Toast.LENGTH_LONG);
                                        Reference.add(REFFERENCE.getText().toString());
                                        dates.add(DATEinv.getText().toString());
                                        Type.add(type);
                                        StatusAr.add(stat);
                                        DURATION.add("3");

                                        adapter.notifyDataSetChanged();

                                    }

                                } catch (ParseException e) {
                                    Toast.makeText(getApplicationContext(), req + " sorry there is problem ", Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                    } else if (type.equals("rolling")) {
                        Toast.makeText(getApplicationContext(), "rolling", Toast.LENGTH_LONG).show();


                        Cursor cursor = db.getData("select * from inventaire where tYPiNV <> 'rolling' ");
                        if (cursor != null && cursor.getCount() > 0) {
                            Toast.makeText(getApplicationContext(), "you should use only one types of inventory ", Toast.LENGTH_LONG).show();
                        } else {

                            try {
                                db.InsertDataINV(REFFERENCE.getText().toString(), sdf.parse(DATEinv.getText().toString()), type, stat, dureINV.getSelectedItem().toString());
                                Reference.add(REFFERENCE.getText().toString());
                                dates.add(DATEinv.getText().toString());
                                Type.add(type);
                                StatusAr.add(stat);
                                DURATION.add(dureINV.getSelectedItem().toString());

                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "successed!", Toast.LENGTH_LONG);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            adapter.notifyDataSetChanged();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "permanent", Toast.LENGTH_LONG).show();
                        Cursor cursor = db.getData("select * from inventaire where tYPiNV <> 'permanent' ");
                        if (cursor != null && cursor.getCount() > 0) {
                            Toast.makeText(getApplicationContext(), "you should use onely one types of inventory ", Toast.LENGTH_LONG).show();
                        } else {

                            try {
                                db.InsertDataINV(REFFERENCE.getText().toString(), sdf.parse(DATEinv.getText().toString()), type, stat, "3");
                                Reference.add(REFFERENCE.getText().toString());
                                dates.add(DATEinv.getText().toString());
                                Type.add(type);
                                StatusAr.add(stat);
                                DURATION.add("3");
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "successed!", Toast.LENGTH_LONG);
                            } catch (ParseException e) {
                                Toast.makeText(getApplicationContext(), "wrong date ", Toast.LENGTH_LONG);

                            }
                            adapter.notifyDataSetChanged();


                        }

                    }


                    dialog2.dismiss();
                }
            });
        }
        return true;
    }

}



