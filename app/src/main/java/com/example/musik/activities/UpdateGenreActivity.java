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

public class UpdateGenreActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaGenre)
    EditText edtNamaGenre;
    @BindView(R.id.edtNamaNegara)
    EditText edtNamaNegara;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    Bundle bundle;
    LaguDatabase laguDatabase;
    int id_genre;
    String nama_genre, nama_negara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_genre);
        ButterKnife.bind(this);

        Toolbar tbDetailPenyanyi = findViewById(R.id.toolbar);
        tbDetailPenyanyi.setTitle("Update Data");
        setSupportActionBar(tbDetailPenyanyi);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Menangkap data dari activity sebelumnya
        bundle = getIntent().getExtras();

        // Buat object database
        laguDatabase = LaguDatabase.createDatabase(this);

        // Menampilkan data sebelumnya ke layar
        showData();
    }

    private void showData() {
        // mengambil data di dalam bundle
        id_genre = bundle.getInt(Constant.KEY_ID_GENRE);
        nama_genre = bundle.getString(Constant.KEY_NAMA_GENRE);
        nama_negara = bundle.getString(Constant.KEY_NAMA_NEGARA);

        // Menampilkan ke layar
        edtNamaGenre.setText(nama_genre);
        edtNamaNegara.setText(nama_negara);
    }

    @OnClick(R.id.btnSimpan)
    public void onViewClicked() {

        // Mengambil data
        getData();

        // Mengirim data ke sqlite
        saveData();

        Toast.makeText(this, "Berhasil di update", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void getData() {
        // Mengambil inputan user dan dimasukkan ke dalam variable
        nama_genre = edtNamaGenre.getText().toString();
        nama_negara = edtNamaNegara.getText().toString();
    }

    private void saveData() {

        // Membuat object kelasmodel
        GenreModel kelasModel = new GenreModel();

        // Memasukkan data ke kelasmodel
        GenreModel.setId_genre(id_genre);
        GenreModel.setNama_genre(nama_genre);
        GenreModel.setNama_negara(nama_negara);

        // Melakukan operasi update untuk mengupdate data ke sqlite
        laguDatabase.genreDao().update(genreModel);
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
