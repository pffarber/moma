package com.pff.momafan.model.pojo;

import com.google.firebase.database.PropertyName;

public class Artista {

    private String name;
    @PropertyName("artistId")
    private String artist_id;
    @PropertyName("Influenced_by")
    private String influenced_by;
    private String nationality;

    public Artista(String name, String artist_id, String influenced_by, String nationality){
        this.name = name;
        this.artist_id = artist_id;
        this.influenced_by = influenced_by;
        this.nationality = nationality;
    }

    public Artista(){

    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @PropertyName("artistId")
    public String getArtist_id() {
        return artist_id;
    }
    @PropertyName("artistId")
    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }
    @PropertyName("Influenced_by")
    public String getInfluenced_by() {
        return influenced_by;
    }
    @PropertyName("Influenced_by")
    public void setInfluenced_by(String influenced_by) {
        this.influenced_by = influenced_by;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
