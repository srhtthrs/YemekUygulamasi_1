package com.serhatozturk.yemekuygulamasi_10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.serhatozturk.yemekuygulamasi_10.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    String mudurluk [];
    String sicil[];
    private ActivityMainBinding binding;
    String mudurlukString, sicilString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53 )));
        binding.buttonDevam.setBackgroundColor(Color.rgb(203, 67, 53 ));
        actionBar.setTitle("Yemek Uygulamasi");

        mudurluk= new String [] {"Insan_Kaynaklari", "Destek_Hizmetleri","Muhasebe","Satinalma","PTTBank"};
        ArrayAdapter<String>adapterMudurluk=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,mudurluk);
        binding.spinnerMudurluk.setAdapter(adapterMudurluk);


        sicil= new String [] {"Sicil","246811","000000","000001","000002","000003"};
        ArrayAdapter<String>adapterSicil=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,sicil);
        binding.spinnerSicil.setAdapter(adapterSicil);





    }








    public void nextClick(View view){
        sicilString=binding.spinnerSicil.getSelectedItem().toString();
        mudurlukString=binding.spinnerMudurluk.getSelectedItem().toString();

        if(binding.spinnerSicil.getSelectedItem().toString().equals("Sicil"))  {

            Toast.makeText(MainActivity.this,"Sicil Secilmedi",Toast.LENGTH_LONG).show();

        }
        else if (binding.spinnerMudurluk.getSelectedItem().toString().equals("Mudurluk"))  {

            Toast.makeText(MainActivity.this,"Mudurluk Secilmedi",Toast.LENGTH_LONG).show();

        }

        else{

            Intent gotoDateIntent = new Intent(MainActivity.this, DateActivity.class);
            gotoDateIntent.putExtra("sicilGonder",sicilString);
            gotoDateIntent.putExtra("mudurlukGonder",mudurlukString);

            startActivity(gotoDateIntent);
        }


    }


}