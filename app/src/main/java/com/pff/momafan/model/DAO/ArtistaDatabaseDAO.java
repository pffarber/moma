package com.pff.momafan.model.DAO;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pff.momafan.model.pojo.Artista;
import com.pff.momafan.model.pojo.ContenedorObras;
import com.pff.momafan.model.pojo.Obra;
import com.pff.momafan.utils.ResultListener;

import java.util.List;

import retrofit2.Callback;

public class ArtistaDatabaseDAO {

    FirebaseDatabase database;
    public static final String ARTISTAS = "artists";

    public ArtistaDatabaseDAO() {
        database = FirebaseDatabase.getInstance();

    }


    public void obtenerDatosArtista(final String id, final ResultListener<Artista> resultListenerDelController) {
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
                            resultListenerDelController.finish(artista);
                            break;
                        }
                    }
                } else {
                    resultListenerDelController.finish(new Artista());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                resultListenerDelController.finish(new Artista());
            }
        });
    }
}
