package com.example.superapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ItemPage extends AppCompatActivity implements View.OnClickListener {

    private int position;
    private Item[] items;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);
       create();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void create(){

        Button AddToCart = findViewById(R.id.AddToCart);
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView leftText = findViewById(R.id.textView);
        ChooseActivity(AddToCart,seekBar,leftText);
        SetParameters(AddToCart,seekBar,leftText);
        SeekBarAction(AddToCart,seekBar,leftText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddToCart:
                AddItem();
                create();
                break;
            default:
                break;
        }
    }

    private void ChooseActivity(Button AddToCart, SeekBar seekBar, TextView leftText){
        Intent intent = getIntent();
        position = intent.getExtras().getInt("id");
        String c = intent.getExtras().getString("class");
        assert c != null;
        switch (c) {
            case "main":
                items = MainActivity.items;
                break;
            case "cart":

                AddToCart.setVisibility(View.GONE);
                seekBar.setVisibility(View.GONE);
                leftText.setVisibility(View.GONE);
                items = new Item[MainActivity.itemsList.size()];
                MainActivity.itemsList.toArray(items);
                break;
            case "search":
                items = new Item[MainActivity.SearchResult.size()];
                MainActivity.SearchResult.toArray(items);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SetParameters(Button AddToCart, SeekBar seekBar, TextView leftText){
        ImageView imageView = findViewById(R.id.imagepart);
        imageView.setImageResource(items[position].Foto);
        TextView textView = findViewById(R.id.Namepart);
        textView.setText(String.valueOf(items[position].Name));
        TextView descriptionView = findViewById(R.id.Descriptionpart);
        descriptionView.setText(String.valueOf(items[position].Description));
        TextView CountView = findViewById(R.id.Countpart);
        CountView.setText("In stock " + String.valueOf(items[position].Count) + " pieces");
        AddToCart.setOnClickListener(this);
        leftText.setText("1");
        seekBar.setMin(1);
        seekBar.setMax(items[position].LeftCount);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SeekBarAction(Button AddToCart, SeekBar seekBar, TextView leftText){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView leftText = findViewById(R.id.textView);
                leftText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if (items[position].LeftCount == 0) {
            AddToCart.setBackgroundColor(Color.parseColor("#808080"));
            leftText.setText("0");
            seekBar.setMin(0);
            seekBar.setMax(0);
        }
    }

    private void AddItem() {
        SeekBar seekBar = findViewById(R.id.seekBar);
        if (seekBar.getProgress() != 0) {
            for (int i = 0; i < seekBar.getProgress(); i++) {
                MainActivity.itemsList.add(items[position]);
                MainActivity.items[position].LeftCount--;
            }
        }
    }
}

