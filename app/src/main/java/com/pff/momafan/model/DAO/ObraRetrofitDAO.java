package com.pff.momafan.model.DAO;

import com.pff.momafan.model.pojo.ContenedorObras;
import com.pff.momafan.model.pojo.Obra;
import com.pff.momafan.model.pojo.ServiceObras;
import com.pff.momafan.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ObraRetrofitDAO {

    private static final String BASE_URL = "https://api.myjson.com/bins/";
    private Retrofit retrofit;
    private ServiceObras serviceObras;

    public ObraRetrofitDAO(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl (BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder.build();
        serviceObras = retrofit.create(ServiceObras.class);
    }

    public void obtenerObras (final ResultListener<List<Obra>> resultListenerDelController){
        serviceObras.obtenerObras().enqueue(new Callback<ContenedorObras>(){
            @Override
            public void onResponse(Call<ContenedorObras> call, Response<ContenedorObras> response){
                ContenedorObras contenedorObrasObtenido = response.body();
                if(contenedorObrasObtenido != null && contenedorObrasObtenido.getResults() != null){
                    List<Obra> listaObras = contenedorObrasObtenido.getResults();
                    resultListenerDelController.finish(listaObras);
                }else{
                    resultListenerDelController.finish(new ArrayList<Obra>());
                }
            }

            @Override
            public void onFailure (Call <ContenedorObras> call, Throwable t ){
                resultListenerDelController.finish(new ArrayList<Obra>());

            }
        });
    }
}

