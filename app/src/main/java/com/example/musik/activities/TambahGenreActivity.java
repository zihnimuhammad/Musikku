package com.example.musik.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahGenreActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaGenre)
    EditText edtNamaGenre;
    @BindView(R.id.edtNamaNegara)
    EditText edtNamaNegara;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    LaguDatabase laguDatabase;
    String namaGenre, namaNegara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_genre);
        ButterKnife.bind(this);

        Toolbar tbDetailPenyanyi = findViewById(R.id.toolbar);
        tbDetailPenyanyi.setTitle("Tambah Genre");
        setSupportActionBar(tbDetailPenyanyi);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        laguDatabase = LaguDatabase.createDatabase(this);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {

        getData();

        saveData();

        clearData();

        Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void clearData() {
        edtNamaGenre.setText("");
        edtNamaNegara.setText("");
    }

    private void saveData() {

        // Membuat object KelasModel untuk menampung data
        GenreModel kelasModel = new genreModel();

        // Memasukkan data ke dalam KelasModel
        kelasModel.setNama_genre(namaGenre);
        kelasModel.setNama_negara(namaNegara);

        // Perintah untuk melakukan operasi Insert menggunakan siswaDatabase
        laguDatabase.genreDao().insert(genreModel);
    }

    private void getData() {
        namaGenre = edtNamaGenre.getText().toString();
        namaNegara = edtNamaNegara.getText().toString();
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
