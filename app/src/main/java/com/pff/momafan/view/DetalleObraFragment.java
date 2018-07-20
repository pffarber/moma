package com.pff.momafan.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pff.momafan.R;
import com.pff.momafan.controller.ArtistaControllerDatabase;
import com.pff.momafan.model.pojo.Artista;
import com.pff.momafan.model.pojo.Obra;
import com.pff.momafan.utils.ResultListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleObraFragment extends Fragment {

    public static final String OBRA_CLAVE = "Obra_clave";
    public static final String ARTISTAS = "artists";
    private ArtistaControllerDatabase artistaControllerDatabase;



    TextView tv_artistName;
    TextView tv_artistNationality;
    TextView tv_influenced;
    ImageView iv_obra;


    public DetalleObraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_obra, container, false);
        TextView tv_nombre = view.findViewById(R.id.tv_nombre_id);
        tv_artistName = view.findViewById(R.id.tv_artist_name);
        tv_artistNationality = view.findViewById(R.id.tv_artist_nationality);
        tv_influenced = view.findViewById(R.id.tv_influenced_by);
        iv_obra = view.findViewById(R.id.iv_imagen_id);
        Bundle bundle = getArguments();
        Obra obra = (Obra) bundle.getSerializable(OBRA_CLAVE);

        artistaControllerDatabase = new ArtistaControllerDatabase();
        obtenerDatosArtistaDatabase(obra.getIdArtista());

        traerImagenObraStorage(obra.getImagenUrl());
        tv_nombre.setText(obra.getNombre());

        return view;
    }


    //pedimos al controller por los datos del artista, le pasamos el id y el listener,
    // cuando vuelva agregamos los datos del artista que devolvió
    private void obtenerDatosArtistaDatabase(String id) {
        artistaControllerDatabase.obtenerDatosArtistaDatabase
                (id, new ResultListener<Artista>() {
            @Override
            public void finish(Artista artista) {
                agregarDatos(artista);
            }
        });
    }

    public void agregarDatos(Artista artista){

        tv_artistName.setText(artista.getName());
        tv_artistNationality.setText(artista.getNationality());
        tv_influenced.setText("Influenced by: " + artista.getInfluenced_by());
        }


    public void traerImagenObraStorage(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        reference = reference.child(imagePath);
        try {
            Glide.with(this)
                            .using(new FirebaseImageLoader())
                            .load(reference)
                            .into(iv_obra);

        } catch (Exception e) {

        }
    }
}





