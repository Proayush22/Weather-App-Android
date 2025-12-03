package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Weather> {

    List<Weather> list;
    Context context;
    int xmlResource;

    public CustomAdapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        xmlResource = resource;
        list = objects;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View adapterLayout = layoutInflater.inflate(xmlResource, null);

        TextView textViewDate = adapterLayout.findViewById(R.id.textViewDa);
        TextView textViewTemp = adapterLayout.findViewById(R.id.textViewT);
        TextView textViewHigh = adapterLayout.findViewById(R.id.textViewHi);
        TextView textViewLow = adapterLayout.findViewById(R.id.textViewL);
        TextView textViewWind = adapterLayout.findViewById(R.id.textViewW);
        TextView textViewHumidity = adapterLayout.findViewById(R.id.textViewH);
        TextView textViewDescription = adapterLayout.findViewById(R.id.textViewD);
        ImageView imageView = adapterLayout.findViewById(R.id.imageViewDI);

        textViewDate.setText(list.get(position).getDate());
        textViewTemp.setText(list.get(position).getTemperature() + "°");
        //textViewHigh.setText(list.get(position).getHightemp() + "°");
        textViewLow.setText(list.get(position).getFeelslike() + "°");
        textViewWind.setText(list.get(position).getWind() + " mph");
        textViewHumidity.setText(list.get(position).getHumidity() + "%");
        textViewDescription.setText(list.get(position).getDescription());
        imageView.setImageResource(list.get(position).getDescriptopnImage());



        return adapterLayout;
    }
}
