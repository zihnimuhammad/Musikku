package com.example.musik.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahLaguActivity extends AppCompatActivity {

    @BindView(R.id.edtJudulLagu)
    EditText edtJudulLagu;
    @BindView(R.id.edtPenyanyi)
    EditText edtPenyanyi;
    @BindView(R.id.edtasal)
    EditText edtAsallabel;
    @BindView(R.id.edttahunrilis)
    EditText edttahunrilis;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;


    SiswaDatabase siswaDatabase;
    int id_genre;
    String JudulLagu, penyanyi, asal, tahunrilis;
    boolean empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_lagu);
        ButterKnife.bind(this);

        Toolbar tbDetailPenyanyi = findViewById(R.id.toolbar);
        tbDetailPenyanyi.setTitle("Tambah lagu");
        setSupportActionBar(tbDetailPenyanyi);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_genre = getIntent().getIntExtra(Constant.KEY_ID_Genre, 0);

        laguDatabase = LaguDatabase.createDatabase(this);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {

        // Memastikan semuanya terisi
        cekData();

        if (!empty) {
            saveData();
            clearData();
            Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Masih ada data yang kosong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearData() {
        edtJudulLagu.setText("");
        edtPenyanyi.setText("");
        edtAsallabel.setText("");
        edttahunrilis.setText("");
    }

    private void saveData() {

        // Membuat penampung dengan membaut object SiswaModel
        LaguModel laguModel = new LaguModel();

        // kita masukkan data ke dalam siswaModel
        laguModel.setId_genre(id_genre);
        laguModel.setJudulLagu(JudulLagu);
        laguModel.setAsallable(asal);
        laguModel.setpenyanyi(penyanyi);
        laguModel.settahun(tahunrilis);

        // Kita lakukan operasi insert
        laguDatabase.genreDao().insertLagu(laguModel);
    }

    private void cekData() {
        JudulLagu = edtJudulLagu.getText().toString();
        asal = edtAsallabel.getText().toString();
        penyanyi = penyanyi.getText().toString();
        tahunrilis = edttahunrilis.getText().toString();

        empty = JudulLagu.isEmpty() || asal.isEmpty() || penyanyi.isEmpty() || tahunrilis.isEmpty();
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
