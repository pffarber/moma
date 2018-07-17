package com.pff.momafan;


import android.database.DatabaseUtils;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pff.momafan.model.pojo.Artista;
import com.pff.momafan.model.pojo.Obra;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleObraFragment extends Fragment {

    public static final String OBRA_CLAVE = "Obra_clave";
    public static final String ARTISTAS = "artists";
    private static final String FOTOS_ROOT_STORAGE = "/artistpaints";
    public static final String IDARTISTA = "artistId";

    FirebaseDatabase database;
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
        database = FirebaseDatabase.getInstance();
        traerArtistaPorId(obra.getIdArtista());
        traerImagenObra(obra.getImagenUrl());
        tv_nombre.setText(obra.getNombre());

        return view;
    }

    private void traerArtistaPorId(final String id) {
        //DatabaseReference reference = database.getReference().child(ARTISTAS).child(id);
        DatabaseReference reference = database.getReference().child(ARTISTAS);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Artista artista = snapshot.getValue(Artista.class);
                        if (artista.getArtist_id().equals(id)) {
                            tv_artistName.setText(artista.getName());
                            tv_artistNationality.setText(artista.getNationality());
                            tv_influenced.setText("Influenced by: " + artista.getInfluenced_by());
                            break;
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Artista inexistente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), "Fallo", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void traerImagenObra(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        reference = reference.child(imagePath);
        try {
            final File archivo = File.createTempFile("imagenandroid", "jpg");
            final StorageReference finalReference = reference;
            reference.getFile(archivo).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Glide.with(getContext())
                            .using(new FirebaseImageLoader())
                            .load(finalReference)
                            .override(500,500)
                            .fitCenter()
                            .into(iv_obra);
                }
            });
        } catch (Exception e) {

        }
    }
}





