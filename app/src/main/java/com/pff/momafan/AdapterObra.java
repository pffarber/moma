package com.pff.momafan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pff.momafan.model.pojo.Obra;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterObra extends RecyclerView.Adapter {

    private List<Obra> listadoDeObras;
    private NotificadorObraCelda notificadorObraCelda;

    public AdapterObra(List<Obra> obras) {
        listadoDeObras = obras;
        if (obras == null) {
            listadoDeObras = new ArrayList<>();
        }
    }

    public AdapterObra() {
        listadoDeObras = new ArrayList<>();

    }

    public AdapterObra(NotificadorObraCelda notificadorObraCelda) {
        this.listadoDeObras = new ArrayList<>();
        this.notificadorObraCelda = notificadorObraCelda;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_cell, parent, false);
        ObrasViewHolder viewHolder = new ObrasViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Obra obra = listadoDeObras.get(position);

        //??
        ObrasViewHolder obrasViewHolder = (ObrasViewHolder) holder;
        obrasViewHolder.cargarObra(obra);


    }

    @Override
    public int getItemCount() {
        return listadoDeObras.size();
    }

    public void agregarObras(List<Obra> obras) {
        for (Obra obraAAgregar : obras) {
            if (!listadoDeObras.contains(obraAAgregar)) {
                listadoDeObras.add(obraAAgregar);
            }
        }
        notifyDataSetChanged();
    }

    public class ObrasViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private ImageView imageViewObra;

        public ObrasViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textviewCell_id);
            imageViewObra = itemView.findViewById(R.id.imageviewObra_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicionObraClickeada = getAdapterPosition();
                    Obra obra = listadoDeObras.get(posicionObraClickeada);
                    notificadorObraCelda.notificarObraClickeada(obra);
                }
            });

        }

        public void cargarObra(Obra obra) {
            textViewNombre.setText(obra.getNombre());
            if (TextUtils.isEmpty(obra.getImagenUrl())) {
                return;
            }
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference reference = storage.getReference();
            reference = reference.child(obra.getImagenUrl());
            try {
                final File archivo = File.createTempFile("imagenandroid", "jpg");
                final StorageReference finalReference = reference;
                reference.getFile(archivo).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Glide.with(itemView.getContext())
                                .using(new FirebaseImageLoader())
                                .load(finalReference)
                                .override(500, 500)
                                .fitCenter()
                                .into(imageViewObra);
                    }
                });
            } catch (Exception e) {

            }
        }

    }
    public interface NotificadorObraCelda {
        public void notificarObraClickeada(Obra obra);
    }

}
