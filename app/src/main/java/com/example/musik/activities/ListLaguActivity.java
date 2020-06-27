package com.example.musik.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musik.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListLaguActivity extends AppCompatActivity {

    @BindView(R.id.rvLagu)
    RecyclerView rvLagu;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    LaguDatabase laguDatabase;
    List<LaguModel> laguModelList;
    int id_lagu;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lagu);

        ButterKnife.bind(this);

        Toolbar tbDetailPenyanyi = findViewById(R.id.toolbar);
        tbDetailPenyanyi.setTitle("Daftar Lagu");
        setSupportActionBar(tbDetailPenyanyi);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();

        if (bundle != null) {
            id_lagu = bundle.getInt(Constant.KEY_ID_LAGU);
        }

        laguDatabase = LaguDatabase.createDatabase(this);

        laguModelList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        laguModelList.clear();

        getData();

        rvLagu.setLayoutManager(new LinearLayoutManager(this));
        rvLagu.setAdapter(new LaguAdapter(this, laguModelList));
    }

    private void getData() {
        laguModelList = laguDatabase.laguDao().selectSiswa(id_lagu);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(this, TambahLaguActivity.class).putExtra(Constant.KEY_ID_LAGU, id_lagu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
