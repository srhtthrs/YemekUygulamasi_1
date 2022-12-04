package com.serhatozturk.yemekuygulamasi_10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.serhatozturk.yemekuygulamasi_10.databinding.ActivityDateBinding;
import com.serhatozturk.yemekuygulamasi_10.databinding.ActivityMainBinding;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class DateActivity extends AppCompatActivity {

    private ActivityDateBinding binding;

    ActionBar actionBar;

    String sicilAl, mudurlukAl, statu;
    String adSoyad;
    String eklenenGunler;

    int gunSay=0;
    int ucret=0;
    int birimUcret=0;

    String  curDate;
    String  year2;
    String  month2;

    private FirebaseFirestore firebaseFirestore;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();



        Toast.makeText(DateActivity.this,"Gunleri Tek Tek Secip, Gonder Butonuna Basiniz.",Toast.LENGTH_LONG).show();
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53 )));

        binding.buttonTemizle.setBackgroundColor(Color.rgb(203, 67, 53 ));
        binding.buttonGonder.setBackgroundColor(Color.rgb(203, 67, 53 ));




        Intent intent=getIntent();
       sicilAl= intent.getStringExtra("sicilGonder");
       mudurlukAl=intent.getStringExtra("mudurlukGonder");


//buraya kişileri ekleyebilirsin ////////////////////////////////////////////////////

       if(sicilAl.equals("246811"))
       {adSoyad="Serhat OZTURK"; statu="399.2"; birimUcret=15;}
       else{adSoyad="Bilinmiyor..."; statu="IHS"; birimUcret=23; }

       actionBar.setTitle(adSoyad);

//yukarıya kişileri ekleyebilirsin ///////////////////////////////////////////////////




        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                curDate = String.valueOf(dayOfMonth);
                year2 = String.valueOf(year);
                month2 = String.valueOf(month+1);

                if(curDate.equals("1")||curDate.equals("2")||curDate.equals("3")||curDate.equals("4")||
                    curDate.equals("5")||curDate.equals("6")||curDate.equals("7")||curDate.equals("8")||curDate.equals("9")){
                    eklenenGunler="0"+curDate+"/"+month2+"/"+year2;

                }
                else{
                eklenenGunler=curDate+"/"+month2+"/"+year2;
                }

              if(binding.textViewGunEklenmis.getText().toString().equals("")){
                    binding.textViewGunEklenmis.setText(eklenenGunler);
                    gunSay++;
                }
                else {
                    binding.textViewGunEklenmis.setText(binding.textViewGunEklenmis.getText().toString()  + " _ "+ eklenenGunler);
                    gunSay++;
                }


                     ucret=gunSay*birimUcret;
                     binding.textViewTopGun.setText(""+gunSay +" gun " + gunSay*birimUcret+" TL" );
            }
        });


    }

    public boolean onSupportNavigateUp() {


        Intent intentToMain = new Intent(DateActivity.this, MainActivity.class);
        intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentToMain);

        return super.onSupportNavigateUp();
    }

    public void temizleClick(View view){

        binding.textViewGunEklenmis.setText("");

        gunSay=0;
        binding.textViewTopGun.setText("");

    }

    public void temizle(){binding.textViewGunEklenmis.setText("");

        gunSay=0;
        binding.textViewTopGun.setText("");}

    public void gonderClick(View view){

        String birimUcretString=String.valueOf(birimUcret);
        String gunSayString=String.valueOf(gunSay);
        String ucretString=String.valueOf(ucret);


        HashMap<String, Object> yemekGir = new HashMap<>();

        yemekGir.put("sicil", sicilAl);
        yemekGir.put("adiSoyadi", adSoyad);
        yemekGir.put("statu", statu);
        yemekGir.put("mudurluk", mudurlukAl);

        yemekGir.put("yil", year2);
        yemekGir.put("ay", month2);
        yemekGir.put("gunler", binding.textViewGunEklenmis.getText().toString());


        yemekGir.put("gunSayisi", gunSayString);
        yemekGir.put("birimUcret", birimUcretString);
        yemekGir.put("ucret", ucretString);



        firebaseFirestore.collection("yemekListesi").add(yemekGir).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(DateActivity.this, "Kayıt Başarılı id: " + documentReference.getId(), Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DateActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        temizle();

        Intent gotoListele= new Intent(DateActivity.this, ListeleActivity.class);
        gotoListele.putExtra("sicilGonder",sicilAl);
        startActivity(gotoListele);

    }



}