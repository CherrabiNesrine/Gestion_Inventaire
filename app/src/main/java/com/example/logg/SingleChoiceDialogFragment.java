package com.example.logg;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;


public class SingleChoiceDialogFragment extends DialogFragment {
    int position = 0;
    ArrayList<Magasin> array;
    DataBaseM database_helper;
    String[] list;int i;Context context;

    public interface SingleChoiceListener {
        void onPositiveButtonClicked(String[] list, int position);
        void onNegativeButtonClicked();
        void onNewOne();
    }
    SingleChoiceListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SingleChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " SingleChoiceListener must implemented");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        database_helper = new DataBaseM(getContext());
        array = new ArrayList<>(database_helper.getMag());
        List<String> where = new ArrayList<String>();
        for(i=0;i<array.size();i++){
            where.add(array.get(i).getNomMag()) ;}
        final String[] l = new String[ where.size() ];
        list=where.toArray( l );
        builder.setTitle("Select your store name")
                .setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveButtonClicked(list, position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onNegativeButtonClicked();
                    }
                })
                .setNegativeButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onNewOne();
            }
        });
        return builder.create();
    }
}
