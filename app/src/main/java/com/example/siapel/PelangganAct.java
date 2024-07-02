package com.example.siapel;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.siapel.services.PelangganService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PelangganAct extends AppCompatActivity {
    private TextInputEditText inpId, inpNama;
    private AutoCompleteTextView inpJenis;
    private Button btnSimpan;
    private PelangganService db=new PelangganService(this);
    String kd,nm,jn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pelanggan_lay);

        inpId=findViewById(R.id.tie_Id);
        inpNama=findViewById(R.id.tie_Nama);
        inpJenis=findViewById(R.id.tie_Jenis);
        btnSimpan=findViewById(R.id.btn_Simpan);

        kd=getIntent().getStringExtra("id_pel");
        nm=getIntent().getStringExtra("nama_pel");
        jn=getIntent().getStringExtra("jenis");

        isiJenis();

        if (kd==null || kd.equals("")){
            setTitle("Tambah Pelanggan");
            inpId.requestFocus();
        }else{
            setTitle("Ubah Pelanggan");
            inpId.setText(kd);
            inpId.setText(nm);
            inpId.setText(jn);
        }
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kd==null || kd.equals("")){
                    simpan();
                }else{
                    ubah(kd);
                }
            }
        });
    }

    public void simpan(){
        if(inpId.getText().equals("")||inpNama.getText().equals("")||
                inpJenis.getText().equals("")){
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data.!",
                    Toast.LENGTH_SHORT).show();
        }else{
            if(!db.isExists(inpId.getText().toString())){
                db.insert(inpId.getText().toString(), inpNama.getText().toString(),
                        inpJenis.getText().toString());
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "ID Pelanggan sudah Terdaftar!", Toast.LENGTH_SHORT).show();
                inpId.selectAll();
                inpId.requestFocus();
            }
        }
    }

    public void ubah(String kode){
        if(inpId.getText().equals("")||inpNama.getText().equals("")||
                inpJenis.getText().equals("")){
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data",
                    Toast.LENGTH_SHORT).show();
        }else{
            if(db.isExists(kd)){
                db.update(inpId.getText().toString(),inpNama.getText().toString(),
                        inpJenis.getText().toString(),kode);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "ID pelanggan tidak terdaftar",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void isiJenis(){
        List<String> jenis=new ArrayList<>();

        jenis.add("PLN");
        jenis.add("BPJS");
        jenis.add("PULSA");
        jenis.add("PDAM");
        jenis.add("TELKOM");

        ArrayAdapter<String> combo= new ArrayAdapter<>(PelangganAct.this, android.R.layout.simple_spinner_dropdown_item,jenis);
        inpJenis.setAdapter(combo);
    }
}