package com.example.logg;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.valueOf;

public class DepenseMain extends SidebarMenu implements SingleChoiceDialogFragment.SingleChoiceListener {

    ArrayList<DepenseData> arrayList;
    DatePickerDialog picker;
    RecyclerView recyclerView;
    int i;
    Context context;String maag;TextView mag;
    FloatingActionButton actionButton;
    DataBaseM database_helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main_depenses, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Expenses");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        actionButton = (FloatingActionButton) findViewById(R.id.add);
        database_helper = new DataBaseM(this);
        displayDepense();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
    }


    public void displayDepense() {
        FragmentManager fm = getSupportFragmentManager();
        arrayList = new ArrayList<>(database_helper.getDepense());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DepenseAdapter adapter = new DepenseAdapter(getApplicationContext(), this, arrayList,fm);
        recyclerView.setAdapter(adapter);

    }

    public void showDialog() {
        final TextView nom,details, amount;
        final TextView DateCrea;
        final Spinner ar;
        String maint;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        maint = sdf.format(cal.getTime());
        Button submit,cancel;
        ImageButton before, after;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.addex);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ar = (Spinner) dialog.findViewById(R.id.type);
        nom = (TextView) dialog.findViewById(R.id.nom);
        mag = (TextView) dialog.findViewById(R.id.ware);
        details = (TextView) dialog.findViewById(R.id.description);
        amount = (TextView) dialog.findViewById(R.id.amount);
        DateCrea = (TextView) dialog.findViewById(R.id.dateView);
        before = (ImageButton) dialog.findViewById(R.id.imageButton3);
        after = (ImageButton) dialog.findViewById(R.id.imageButton4);
        cancel = (Button) dialog.findViewById(R.id.cancel);
        DateCrea.setText(maint);
        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                DialogFragment single = new SingleChoiceDialogFragment();
                single.setCancelable(false);
                single.show(getSupportFragmentManager(), "Select your store name");

            }
        });

        DateCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(DepenseMain.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DateCrea.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();

            }
        });


        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String date;
                    Date updateLast = format.parse(DateCrea.getText().toString());
                    Calendar c = Calendar.getInstance();
                    c.setTime(updateLast);
                    c.add(Calendar.DATE, -1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.format(c.getTime());
                    DateCrea.setText(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });


        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String date;
                    Date updateLast = format.parse(DateCrea.getText().toString());
                    Calendar c = Calendar.getInstance();
                    c.setTime(updateLast);
                    c.add(Calendar.DATE, 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.format(c.getTime());
                    DateCrea.setText(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });

        submit = (Button) dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            ;

            @Override
            public void onClick(View v) {
                String s = valueOf(nom.getText().toString());
                if (amount.getText().toString().isEmpty()) {
                    amount.setError("Please Enter the amount");}
                 else  if (s.isEmpty()) {
                    nom.setError("Please Enter the name of your expense");
                } else if (mag.getText().toString().isEmpty()) {
                    mag.setError("Please Enter the name of the store");}
                 else {
                    database_helper.addDepense(nom.getText().toString(), maag, Double.parseDouble(amount.getText().toString()), ar.getSelectedItem().toString(), DateCrea.getText().toString(), details.getText().toString());
                    dialog.cancel();
                    displayDepense();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            ;

            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list,int position) {
        Toast.makeText(getApplicationContext(),"Selected Item = " + list[position],Toast.LENGTH_LONG).show();
        maag=list[position];
        mag.setText(maag);

    }

    @Override
    public void onNegativeButtonClicked() {
        Toast.makeText(DepenseMain.this,"dialog canceled !",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNewOne() {
        Toast.makeText(getApplicationContext(), "Magazins", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MagazinActivity.class);
        startActivity(intent);
    }
}





