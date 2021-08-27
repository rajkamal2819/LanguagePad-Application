package com.learn.Language_pad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.learn.Language_pad.R;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int resourceColorId;
    public WordAdapter( Context context,ArrayList<Word> resource,int resourceId) {
        /* Here, we initialize the ArrayAdapter's internal storage for the context and the list.
         the second argument is used when the ArrayAdapter is populating a single TextView.
         Because this is a custom adapter for two TextViews, the adapter is not
         going to use this second argument, so it can be any value. Here, we used 0.*/
        super(context,0,resource);   //initializing the second arg to 0 since we are not going to use it
        this.resourceColorId = resourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        //lets create and edit that view that we are going to return to getView so that the list view adapt it and show
        View listItemView = convertView;
        // Check if the existing view is being reused, otherwise inflate the view
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        TextView english = (TextView) listItemView.findViewById(R.id.default_text_view);
        english.setText(currentWord.getDefaultTranslation());

        TextView miwok  = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwok.setText(currentWord.getMiwokTranslationTranslation());

        ImageView icon = (ImageView) listItemView.findViewById(R.id.icon_image_view);

        if(!currentWord.hasImage()){
            icon.setVisibility(View.GONE);
        }
        else
        icon.setImageResource(currentWord.getImageResourceId());

        View textContainer = listItemView.findViewById(R.id.text_vertical_linearLayout);
        int color = ContextCompat.getColor(getContext(),resourceColorId);
        textContainer.setBackgroundColor(color);                //we have converted the color resource id to a color value

        return listItemView;
    }
}
