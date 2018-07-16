package com.pff.momafan.controller;

import com.pff.momafan.model.DAO.ObraRetrofitDAO;
import com.pff.momafan.model.pojo.Obra;
import com.pff.momafan.utils.ResultListener;

import java.util.List;

public class ObraController {

    public void obtenerObras(final ResultListener<List<Obra>> resultListenerDelView) {
        if (hayInternet()) {
            ObraRetrofitDAO obraRetrofitDAO = new ObraRetrofitDAO();
            obraRetrofitDAO.obtenerObras(new ResultListener<List<Obra>>() {
                @Override
                public void finish(List<Obra> obras) {
                    resultListenerDelView.finish(obras);

                }
            });
        } else {
            /*ObraArchivoDAO obraArchivoDAO = new ObraArchivoDAO();
            obraArchivoDAO.obtenerObra(new ResultListener<List<Obra>>() {
                @Override
                public void finish(List<Obra> obras) {

                }
            });

        }*/
        }
    }
    private boolean hayInternet() {
        return true;
    }
}

