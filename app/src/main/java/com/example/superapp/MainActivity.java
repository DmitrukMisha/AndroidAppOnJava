package com.example.superapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;

    public static Item[] items;
    public static ArrayList<Item> itemsList;
    public static ArrayList<Item> SearchResult;
    SharedPreferences myPreferences;
    @Override
    protected void onStart() {
        super.onStart();
        gridView.setAdapter(new ImageTextAdapter(this, "main"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SetParameters();
        GridViewAdapter();
        TextAction();
    }

    private void TextAction(){
        final EditText editText = findViewById(R.id.Search);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(editText.getText().length()>=3) {
                    Search(editText.getText().toString());
                } else Default();
            }
        });
    }

    private void GridViewAdapter(){
        gridView = findViewById(R.id.GridTable);
        gridView.setNumColumns(myPreferences.getInt("Number",3));
        gridView.setAdapter(new ImageTextAdapter(this, "main"));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(getApplicationContext(),
                        ItemPage.class);
                i.putExtra("id", position);
                if(SearchResult==null){
                    i.putExtra("class", "main");
                }else {i.putExtra("class", "search");}
                startActivity(i);
            }
        });

    }

    private void SetParameters(){
        items = CreateItems();
        itemsList = new ArrayList<>();
        Button CartButton = findViewById(R.id.Cart);
        CartButton.setOnClickListener(this);
        Button ExitButton = findViewById(R.id.Exit);
        ExitButton.setOnClickListener(this);
        Button LineButton = findViewById(R.id.LineByLine);
        LineButton.setOnClickListener(this);
        Button ColumnButton = findViewById(R.id.ByColumns);
        ColumnButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Cart:
                Intent intent = new Intent(this, Cart.class);
                startActivity(intent);
                break;
            case R.id.Exit:
                finish();
                break;
            case R.id.ByColumns:
                ChangeView(3);
                break;
            case R.id.LineByLine:
                ChangeView(1);
            default:
                break;
        }
    }

    private void ChangeView (Integer number){
        SharedPreferences.Editor myEditor= myPreferences.edit();
        myEditor.putInt("Number",number);
        myEditor.commit();
        gridView.setNumColumns(number);
    }

    public void Default(){
        SearchResult=null;
        gridView.setAdapter(new ImageTextAdapter(this, "main"));
    }

    public void Search(String text){
        SearchResult= new ArrayList<>();
        for (Item item:items) {
            if(item.Name.toLowerCase().contains(text.toLowerCase())||item.Description.toLowerCase().contains(text.toLowerCase())){
                SearchResult.add(item);
            }
        }
        gridView.setAdapter(new ImageTextAdapter(this, "search"));
    }

    public Item[] CreateItems() {
        Item[] items = new Item[10];
        Integer[] mThumbIds = {R.drawable.foto1, R.drawable.foto2,
                R.drawable.foto3, R.drawable.foto4, R.drawable.foto5,
                R.drawable.foto6, R.drawable.foto7, R.drawable.foto8,
                R.drawable.foto9, R.drawable.foto10};
        String[] Names = {"HP", "Apple", "Revolt", "ASUS", "HP", "Dell", "HP", "Revolt", "URAN 235", "Lenovo"};
        Integer[] Counts = {1, 0, 3, 5, 6, 7, 3, 5, 33, 4};
        String[] Descriptions = {"PC for games", "PC for office work", "PC for games", "PC for games", "Laptop", "PC for games", "TV-set", "PC for games", "PC for games", "PC for office work"};
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(i, mThumbIds[i], Names[i], Descriptions[i], Counts[i], Counts[i]);
        }
        return items;
    }
}
