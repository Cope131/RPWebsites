package com.example.rpwebsites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spnCategory, spnSubcategory;
    Button btnGo;

    ArrayAdapter<String> aaCategory;
    ArrayList<String> alCategory;

    List<String> strListCatSOI;
    List<String> strListCatRP;

    String[][] websites = {
            {"https://www.rp.edu.sg/",
                    "https://www.rp.edu.sg/student-life"},
            {"https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                    "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnCategory = findViewById(R.id.spinnerCategory);
        spnSubcategory = findViewById(R.id.spinnerSubcategory);
        btnGo = findViewById(R.id.buttonGo);

        //Array
        alCategory = new ArrayList<>();

        //Array Adapter
        aaCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alCategory);
        spnSubcategory.setAdapter(aaCategory);

        //SOI values
        String[] strArrCatSOI = getResources().getStringArray(R.array.SOI_subcategories);
        strListCatSOI = Arrays.asList(strArrCatSOI);

        //RP values
        String[] strArrCatRP = getResources().getStringArray(R.array.RP_subcategories);
        strListCatRP = Arrays.asList(strArrCatRP);

        //Default values
        alCategory.addAll(strListCatRP);
        aaCategory.notifyDataSetChanged();

        //Select Category 
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                alCategory.clear();

                switch(pos) {
                    case 0:
                        alCategory.addAll(strListCatRP);
                        break;
                    case 1:
                        alCategory.addAll(strListCatSOI);
                }
                aaCategory.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Select Button Go
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int subItemPos = spnSubcategory.getSelectedItemPosition();
                int catItemPos = spnCategory.getSelectedItemPosition();
                String url = "";

                if (!alCategory.isEmpty()) {
                    url = websites[catItemPos][subItemPos];
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "No Sub-categories", Toast.LENGTH_SHORT).show();
                }
            }
        });

    } //onCreate

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int catPos = sp.getInt("Category", 0);
        int subCatPos = sp.getInt("Subcategory", 0);
        String subcategoryFirstElement = sp.getString("SubcategoryFirstElement", "Homepage");

        alCategory.clear();
        if (subcategoryFirstElement.equals("Homepage"))
            alCategory.addAll(strListCatRP);
        else
            alCategory.addAll(strListCatSOI);
        aaCategory.notifyDataSetChanged();

        //Log.d("Category pos", catPos + "");
        //Log.d("Subcategory pos", subCatPos + "");

        spnCategory.setSelection(catPos);
        spnSubcategory.setSelection(subCatPos);
    }

    //save
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = sp.edit();

        final int catPos = spnCategory.getSelectedItemPosition();
        final int subCatPos = spnCategory.getSelectedItemPosition();

        prefEdit.putInt("Category", catPos);
        prefEdit.putInt("Subcategory", subCatPos);
        prefEdit.putString("SubcategoryFirstElement", alCategory.get(0));

        //Log.d("Category pos", spnCategory.getSelectedItemPosition() + "");
        //Log.d("Subcategory pos", spnSubcategory.getSelectedItemPosition() + "");

        prefEdit.commit();
    }
}