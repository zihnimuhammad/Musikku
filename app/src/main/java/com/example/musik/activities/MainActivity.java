package com.example.musik.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musik.R;
import com.example.musik.database.LaguDatabase;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvLagu)
    RecyclerView rvKelas;

    private LaguDatabase laguDatabase;
    private List<LaguModel> laguModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Membuat object database
        laguDatabase = LaguDatabase.createDatabase(this);

        // Membuat membuat object List
        genreModelList = new ArrayList<>();

        ExtendedFloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahKelasActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Mengosongkan List agar dipastikan list dapat disi dengan data yg paling baru
        genreModelList.clear();

        // Mengambil data dari Sqlite
        getData();

        // Mensetting dan proses menampilkan data ke RecyclerView
        rvGenre.setLayoutManager(new GridLayoutManager(this, 2));
        rvGenre.setAdapter(new GenreAdapter(this, genreModelList));
    }

    private void getData() {

        // Operasi mengambil data dari database Sqlite
        genreModelList = laguDatabase.kelasDao().select();
    }

}
