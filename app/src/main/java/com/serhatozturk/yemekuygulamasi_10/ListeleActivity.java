package com.serhatozturk.yemekuygulamasi_10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.serhatozturk.yemekuygulamasi_10.databinding.ActivityDateBinding;
import com.serhatozturk.yemekuygulamasi_10.databinding.ActivityListeleBinding;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

public class ListeleActivity extends AppCompatActivity {

    private ActivityListeleBinding binding;
    String sicilAl2;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<ModelYemek> modelYemekArrayList;
    AdapterYemek adapterYemek;
    String idGonder;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListeleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        actionBar=getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(203, 67, 53 )));
        actionBar.setTitle("Odemelerim");

        firebaseFirestore = FirebaseFirestore.getInstance();
        modelYemekArrayList=new ArrayList<>();

        //diğer taraftan sicili aldım. sorguda lazım olacak...**************************
        Intent intent=getIntent();
        sicilAl2= intent.getStringExtra("sicilGonder");
        //*******************************************************************************



        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterYemek = new AdapterYemek (modelYemekArrayList);
        binding.RecyclerView.setAdapter(adapterYemek);
        
        
        
        getData();



        Intent intent2 = getIntent();
        idGonder = intent2.getStringExtra("idGonder");

        if(idGonder!=null){
            AlertDialog.Builder alert = new AlertDialog.Builder(ListeleActivity.this);
           // alert.setTitle("Uyarı");
            alert.setMessage("Kayıt Silinecek?");
            alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    firebaseFirestore.collection("yemekListesi").document(idGonder)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(ListeleActivity.this, "Kayıt Silindi", Toast.LENGTH_LONG).show();


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ListeleActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
            alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alert.show();
        }

    }


    public void getData(){



        firebaseFirestore.collection("yemekListesi").whereEqualTo("sicil",sicilAl2).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(ListeleActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if (value != null) {

                    modelYemekArrayList.clear();

                    for (DocumentSnapshot snapshot : value.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();

                        String sicil = (String) data.get("sicil");
                        String yil = (String) data.get("yil");
                        String ay = (String) data.get("ay");

                        String gunSayisi = (String) data.get("gunSayisi");
                        String toplamUcret = (String) data.get("ucret");

                       String id = snapshot.getId();


                        ModelYemek modelYemek = new ModelYemek(id,"" + sicil, "", "", "", "" + yil, ay+".ay","",""+gunSayisi+" gun","",""+toplamUcret+" TL");
                        modelYemekArrayList.add(modelYemek);
                    }
                        adapterYemek.notifyDataSetChanged();  // değişikleri göstermek.
                }

            }
        });


    }
}