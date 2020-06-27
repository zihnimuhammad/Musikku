package com.example.musik.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    private final Context context;
    private final List<GenreModel> genreModelList;
    private LaguDatabase laguDatabase;
    private Bundle bundle;

    public GenreAdapter(Context context, List<GenreModel> genreModelList) {
        this.context = context;
        this.genreModelList = genreModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Memindahkan data di dalam list dengan index position ke dalam KelasModel
        final genreModel genreModel = genreModelList.get(position);

        // Menampilkan data ke layar
        holder.tvNamaNegara.setText(genreModel.getNama_negara());
        holder.tvNamaGenre.setText(genreModel.getNama_genre());

        ColorGenerator generator = ColorGenerator.MATERIAL;

        // generate random color
        int color = generator.getRandomColor();
        holder.cvgenre.setCardBackgroundColor(color);

        // Onlick pada itemview
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();

                bundle.putInt(Constant.KEY_ID_GENRE, genreModel.getId_genre());
                context.startActivity(new Intent(context, ListLaguActivity.class).putExtras(bundle));
            }
        });

        // Membuat onclick icon overflow
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Buat object database
                laguDatabase = laguDatabase.createDatabase(context);

                // Membuat object popumemu
                PopupMenu popupMenu = new PopupMenu(context, view);

                // Inflate menu ke dalam popupmenu
                popupMenu.inflate(R.menu.popup_menu);

                // Menampilkan menu
                popupMenu.show();

                // Onclick pada salah satu menu pada popupmenu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setMessage("Are you sure delete " + genreModel.getNama_genre() + " ?");
                                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        // Melakukan operasi delete data
                                        laguDatabase.genreDao().delete(genreModel);

                                        // Menghapus data yang telash di hapus pada List
                                        genreModelList.remove(position);

                                        // Memberitahu bahwa data sudah hilang
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(0, genreModelList.size());

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

                                break;

                            case R.id.edit:

                                // Membuat object bundle
                                bundle = new Bundle();

                                // Mengisi bundle dengan data
                                bundle.putInt(Constant.KEY_ID_GENRE, genreModel.getId_genre());
                                bundle.putString(Constant.KEY_NAMA_GENRE, genreModel.getNama_genre());
                                bundle.putString(Constant.KEY_NAMA_NEGARA, genreModel.getNama_negara());

                                // Berpindah halaman dengan membawa data
                                context.startActivity(new Intent(context, UpdateGenreActivity.class).putExtras(bundle));
                                break;
                        }
                        return true;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return genreModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNamagenre)
        TextView tvNamagenre;
        @BindView(R.id.tvNamanegara)
        TextView tvNamanegara;
        @BindView(R.id.cvgenre)
        CardView cvKelas;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
