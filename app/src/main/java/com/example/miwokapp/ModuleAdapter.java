package com.example.miwokapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class ModuleAdapter extends ArrayAdapter<ManageModule> {


    private int colorId;
    public ModuleAdapter(@NonNull Context context,ArrayList<ManageModule> objects,int colorId) {
        super(context, 0, objects);
        this.colorId = colorId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        // Check if the existing view is being reused, otherwise inflate the view
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.manage_translation_list_view, parent, false);
        }
        ManageModule currentMoudule = getItem(position);

         TextView txt = (TextView) listItemView.findViewById(R.id.language_txt);
         txt.setText(currentMoudule.getLanguageName());

        TextView download = (TextView) listItemView.findViewById(R.id.download_txt);
        TextView delete = (TextView) listItemView.findViewById(R.id.delete_txt);

        if(!currentMoudule.isDownloaded()){
            download.setVisibility(View.GONE);
            delete.setText("Delete");
        }
        else {
            delete.setVisibility(View.GONE);
            download.setText("Download");
        }

        View textContainer = listItemView.findViewById(R.id.language_list_item);
        int color = ContextCompat.getColor(getContext(),colorId);
        textContainer.setBackgroundColor(color);                //we have converted the color resource id to a color value

        return listItemView;
    }

}
