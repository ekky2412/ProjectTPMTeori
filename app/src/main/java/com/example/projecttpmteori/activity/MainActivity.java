package com.example.projecttpmteori.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.projecttpmteori.R;
import com.example.projecttpmteori.model.kota.ResultsItem;
import com.example.projecttpmteori.services.OngkirListener;
import com.example.projecttpmteori.services.OngkirService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_kotaAsal,spinner_kotaTujuan,spinner_kurir;
    EditText et_berat;
    AppCompatButton btn_submit;

    String selectedNamaKotaAsal = "", selectedNamaKotaTujuan = "";
    String selectedIdKotaAsal = "",selectedIdKotaTujuan = "",selectedKurir = "";
    List<String> listIdKota = new ArrayList<String>();
    List<String> listSpinnerKotaAsal = new ArrayList<String>();
    List<String> listSpinnerKotaTujuan = new ArrayList<String>();
    List<String> listKurir = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_submit = findViewById(R.id.btn_submit);
        et_berat = findViewById(R.id.et_berat);
        spinner_kotaAsal = findViewById(R.id.spinner_kotaAsal);
        spinner_kotaTujuan = findViewById(R.id.spinner_kotaTujuan);
        spinner_kurir = findViewById(R.id.spinner_kurir);

//        spinner_kotaAsal.setAdapter();

        new OngkirService().getKotaAPI(kotaListener);


        spinner_kotaAsal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNamaKotaAsal = parent.getItemAtPosition(position).toString();
                selectedIdKotaAsal = listIdKota.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_kotaTujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNamaKotaTujuan = parent.getItemAtPosition(position).toString();
                selectedIdKotaTujuan = listIdKota.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_kurir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedKurir = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_berat.getText().toString())){
                    et_berat.setError("Berat tidak boleh kosong!");
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),OngkirActivity.class);
                    intent.putExtra("NamaKotaAsal",selectedNamaKotaAsal);
                    intent.putExtra("NamaKotaTujuan",selectedNamaKotaTujuan);
                    intent.putExtra("idKotaAsal",selectedIdKotaAsal);
                    intent.putExtra("idKotaTujuan",selectedIdKotaTujuan);
                    intent.putExtra("berat",et_berat.getText().toString());
                    intent.putExtra("kurir",selectedKurir);
                    startActivity(intent);
//                    finish();
                }
            }

        });

    }

    OngkirListener<List<ResultsItem>> kotaListener = new OngkirListener<List<ResultsItem>>() {
        @Override
        public void onResponse(List<ResultsItem> items) {

            for(int i=0;i<items.size();i++){
                listSpinnerKotaAsal.add(items.get(i).getCityName());
                listSpinnerKotaTujuan.add(items.get(i).getCityName());
                listIdKota.add(items.get(i).getCityId());
            }

            listKurir.add("jne");
            listKurir.add("pos");
            listKurir.add("tiki");

            ArrayAdapter<String> adapterKotaAsal = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listSpinnerKotaAsal);
            adapterKotaAsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_kotaAsal.setAdapter(adapterKotaAsal);

            ArrayAdapter<String> adapterKotaTujuan = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listSpinnerKotaTujuan);
            adapterKotaTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_kotaTujuan.setAdapter(adapterKotaTujuan);

            ArrayAdapter<String> adapterKurir = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,listKurir);
            adapterKurir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_kurir.setAdapter(adapterKurir);

        }

        @Override
        public void onFailure(String message) {
            Log.d("isi Error : ", message);
        }
    };

}