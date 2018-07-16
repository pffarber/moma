package com.pff.momafan.model.pojo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceObras {
    @GET("x858r")
    Call<ContenedorObras> obtenerObras();

}
