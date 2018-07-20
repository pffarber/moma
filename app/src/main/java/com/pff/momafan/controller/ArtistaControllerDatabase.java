package com.pff.momafan.controller;

import com.pff.momafan.model.DAO.ArtistaDatabaseDAO;
import com.pff.momafan.model.DAO.ObraRetrofitDAO;
import com.pff.momafan.model.pojo.Artista;
import com.pff.momafan.model.pojo.Obra;
import com.pff.momafan.utils.ResultListener;

import java.util.List;

public class ArtistaControllerDatabase {


    public void obtenerDatosArtistaDatabase
            (String id, final ResultListener<Artista> resultListenerDelView){

            if (hayInternet()) {
                ArtistaDatabaseDAO artistaDatabaseDAO = new ArtistaDatabaseDAO();
                artistaDatabaseDAO.obtenerDatosArtista(id, new ResultListener<Artista>() {
                    @Override
                    public void finish(Artista artista) {
                        resultListenerDelView.finish(artista);

                    }
                });
            } else {

            }
    }

    private boolean hayInternet() {
        return true;
    }
}
