package com.example.superapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class Cart extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button BuyButton = findViewById(R.id.Buy);
        BuyButton.setOnClickListener(this);
        Button ClearButton = findViewById(R.id.Clear);
        ClearButton.setOnClickListener(this);

        gridView=  findViewById(R.id.GridCartTable);
        gridView.setNumColumns(3);

        gridView.setAdapter(new ImageTextAdapter(this, "cart"));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(getApplicationContext(),
                        ItemPage.class);
                i.putExtra("id", position);
                i.putExtra("class","cart");
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Buy:
                Buy();
                break;
            case R.id.Clear:
                ClearCountsAndView();
                break;
            default:
                break;
        }
    }

    private void Buy(){
      for (int i = 0; i< MainActivity.items.length; i++){
      MainActivity.items[i].Count= MainActivity.items[i].LeftCount;
      }
      Clear();
    }

    private void ClearCountsAndView(){
        for (int i = 0; i< MainActivity.items.length; i++){
            MainActivity.items[i].LeftCount= MainActivity.items[i].Count;
        }
        Clear();
    }

    private void Clear(){
        MainActivity.itemsList.clear();
        gridView.setAdapter(new ImageTextAdapter(this, "cart"));
    }

}
