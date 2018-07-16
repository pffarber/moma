
package com.pff.momafan.model.pojo;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Obra implements Serializable {
        @SerializedName("name")
        private String nombre;

        private String imagenUrl;

        @SerializedName("artistId")
        private String idArtista;

        public Obra(String nombre, String imagenUrl, String  idArtista) {
            this.nombre = nombre;
            this.imagenUrl = imagenUrl;
            this.idArtista = idArtista;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getImagenUrl() {
            return imagenUrl;
        }

        public void setImagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
        }

        public String getIdArtista() {
            return idArtista;
        }

        public void setIdArtista(String idArtista) {
            this.idArtista = idArtista;
        }

        @Override
        public String toString() {
            return "Titulo:" + nombre;
        }

        @Override
        public boolean equals (Object obj){
            if(!(obj instanceof Obra)){
                return false;
            }
            Obra obraAComparar = (Obra) obj;
            return (obraAComparar.getNombre().equals(this.nombre));
        }

    }


