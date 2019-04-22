package com.example.superapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageTextAdapter extends BaseAdapter {
    private Context mContext;
    private Item[] items;

    ImageTextAdapter(Context c, String activityName) {
        mContext = c;
        switch (activityName) {
            case "main":
                items = MainActivity.items;
                break;
            case "search":
                items = new Item[MainActivity.SearchResult.size()];
                MainActivity.SearchResult.toArray(items);
                break;
            case "cart":
                items = new Item[MainActivity.itemsList.size()];
                MainActivity.itemsList.toArray(items);
                break;
            default:
                break;
        }
    }


    public int getCount() {
        return items.length;
    }

    public Object getItem(int position) {
        return items[position].Foto;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.cellgrid, parent, false);
        } else {
            grid = convertView;
        }
        SetParametrs(grid,position);
        return grid;
    }

    @SuppressLint("SetTextI18n")
    private void SetParametrs(View grid, int position){
        ImageView imageView = grid.findViewById(R.id.imagepart);
        TextView textView = grid.findViewById(R.id.textpart);
        TextView textCountView = grid.findViewById(R.id.countpart);
        imageView.setImageResource(items[position].Foto);
        textView.setText(String.valueOf(items[position].Name));
        textCountView.setText("In stock " + String.valueOf(items[position].Count) + " pieces");
    }

}