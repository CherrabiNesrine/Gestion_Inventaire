package com.example.logg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.valueOf;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseAdapter.viewHolder> implements SingleChoiceDialogFragment.SingleChoiceListener{

    Context context;
    Activity activity;private FragmentManager fm;
    ArrayList<DepenseData> arrayList;
    DataBaseM database_helper;DatePickerDialog picker;
    String maag;




    public DepenseAdapter(Context context, Activity activity, ArrayList<DepenseData> arrayList, FragmentManager fm) {
        this.context = context;
        this.activity  = activity ;
        this.arrayList = arrayList;
        this.fm=fm;

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layout_depense, viewGroup, false);
        return new viewHolder(view);
    }



    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        double pos=arrayList.get(position).getTotal();
        holder.amount.setText(String.valueOf(pos) +" "+arrayList.get(position).getAr());
        holder.nom.setText(arrayList.get(position).getNOM());
        holder.ID.setText(arrayList.get(position).getID());

        database_helper = new DataBaseM(context);
        holder.edit.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });

        database_helper = new DataBaseM(context);
        holder.view.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                DisplayIt(position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                database_helper.deleteExp(arrayList.get(position).getID());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        final TextView nom,amount;
        ImageView delete, edit,view;
        Button ID;
        public viewHolder(View itemView) {
            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.nom);
            ID= (Button) itemView.findViewById(R.id.ID);
            amount = (TextView) itemView.findViewById(R.id.amount);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            view = (ImageView) itemView.findViewById(R.id.view);

        }
    }

    public void showDialog(final int pos) {
        final TextView nom, mag,details,amount;
        final TextView DateCrea;String maint;
        final Spinner ar;
        Button submit;ImageButton before,after;
        final Dialog dialog = new Dialog(activity);
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
        ar=(Spinner) dialog.findViewById(R.id.type);
        nom = (TextView) dialog.findViewById(R.id.nom);
        mag= (TextView) dialog.findViewById(R.id.ware);
        details = (TextView) dialog.findViewById(R.id.description);
        amount = (TextView) dialog.findViewById(R.id.amount);
        DateCrea = (TextView) dialog.findViewById(R.id.dateView);
        before = (ImageButton) dialog.findViewById(R.id.imageButton3);
        after = (ImageButton) dialog.findViewById(R.id.imageButton4);
        submit = (Button) dialog.findViewById(R.id.submit);
        mag.setText(maag);
        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment single = new SingleChoiceDialogFragment();
                single.show(fm, "Single Choice Dialog");
                mag.setText(maag);
            }
        });
        DateCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(activity,
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
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");String date;

                    Date updateLast = format.parse(DateCrea.getText().toString());
                    Calendar c = Calendar.getInstance();
                    c.setTime(updateLast);
                    c.add(Calendar.DATE, -1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date= sdf.format(c.getTime());
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
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");String date;

                    Date updateLast = format.parse(DateCrea.getText().toString());
                    Calendar c = Calendar.getInstance();
                    c.setTime(updateLast);
                    c.add(Calendar.DATE, 1);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date= sdf.format(c.getTime());
                    DateCrea.setText(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                if (nom.getText().toString().isEmpty()) {
                    nom.setError("Please Enter quantity");
                }
                else {

                    database_helper.updateDepense(nom.getText().toString(),mag.getText().toString(),Double.parseDouble(amount.getText().toString()),ar.getSelectedItem().toString(),DateCrea.getText().toString(),details.getText().toString(),arrayList.get(pos).getID());
                    arrayList.get(pos).setNOM(nom.getText().toString());
                    arrayList.get(pos).setMAGASIN(mag.getText().toString());
                    arrayList.get(pos).setTOTAL(Double.parseDouble(amount.getText().toString()));
                    arrayList.get(pos).setAR(ar.getSelectedItem().toString());
                    arrayList.get(pos).setDateCrea(DateCrea.getText().toString());
                    arrayList.get(pos).setDESCRIPTION(details.getText().toString());
                    dialog.cancel();
                    notifyDataSetChanged();
                }
            }
        });

        nom.setText(valueOf(arrayList.get(pos).getNOM()));
        mag.setText(arrayList.get(pos).getMAGASIN());
        amount.setText(valueOf(arrayList.get(pos).getTotal()));
        details.setText(arrayList.get(pos).getDESCRIPTION());
        DateCrea.setText(arrayList.get(pos).getDateCrea());

    }

    public void DisplayIt(final int pos) {
        final TextView nom, mag,details,amount,DateCrea,ID;
        Button OK;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.depense_view);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ID = (TextView) dialog.findViewById(R.id.ExpenseID);
        nom = (TextView) dialog.findViewById(R.id.nom);
        mag = (TextView) dialog.findViewById(R.id.ware);
        details=(TextView) dialog.findViewById(R.id.details);
        amount=(TextView) dialog.findViewById(R.id.amount);
        DateCrea=(TextView) dialog.findViewById(R.id.DateCrea);
        OK = (Button) dialog.findViewById(R.id.OK);
        ID.append(arrayList.get(pos).getID());
        amount.append(valueOf(arrayList.get(pos).getTotal())+" "+arrayList.get(pos).getAr());
        nom.append(arrayList.get(pos).getNOM());
        mag.append(arrayList.get(pos).getMAGASIN());
        DateCrea.append(arrayList.get(pos).getDateCrea());
        details.append(arrayList.get(pos).getDESCRIPTION());
        OK.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {


                dialog.cancel();


            }
        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list,int position) {
        Toast.makeText(context,"Selected Item = " + list[position],Toast.LENGTH_LONG).show();
        maag=list[position];
    }

    @Override
    public void onNegativeButtonClicked() {
        Toast.makeText(context,"dialog canceled !",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNewOne() {
        Toast.makeText(context, "Magazins", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, MagazinActivity.class);
        intent.putExtra("check","care");
        context.startActivity(intent);
    }

}