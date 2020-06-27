package com.example.musik.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaguAdapter extends RecyclerView.Adapter<LaguAdapter.ViewHolder> {

    private final Context context;
    private final List<LaguModel> laguModelList;
    private Bundle bundle;
    private String firstName;
    private LaguDatabase laguDatabase;

    public LaguAdapter(Context context, List<laguModel> laguModelList) {
        this.context = context;
        this.laguModelList = laguModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lagu, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // memindahkan data yang dipilih ke dalam list
        final LaguModel laguModel = laguModelList.get(position);

        // Menampilkan data ke layar
        holder.txtjudullagu.setText(laguModel.getNama_siswa());

        // Mengambil huruf pertama
        String nama = laguModel.getjudullagu();
        if (!nama.isEmpty()) {
            firstName = nama.substring(0, 1);
        } else {
            firstName = " ";
        }

        ColorGenerator generator = ColorGenerator.MATERIAL;
        // generate random color
        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder().buildRound(firstName, color);
        holder.imgView.setImageDrawable(drawable);

        // Membuat onclick icon overflow
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Buat object database
                laguDatabase = laguDatabase.createDatabase(context);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure delete " + laguModel.getJudulLagu() + " ?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Melakukan operasi delete data
                        laguDatabase.genreDao().deleteLagu(laguModel);

                        // Menghapus data yang telash di hapus pada List
                        laguModelList.remove(position);

                        // Memberitahu bahwa data sudah hilang
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, laguModelList.size());

                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return laguModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_JudulLagu)
        TextView txtNameSiswa;
        @BindView(R.id.btnDelete)
        ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
