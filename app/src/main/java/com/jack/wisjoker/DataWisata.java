package com.jack.wisjoker;

public class DataWisata {
    public String namaTempat, lokasi, deskripsi, imgUrl;
    public DataWisata() {
    }

    public DataWisata(String namaTempat, String lokasi, String deskripsi, String imgUrl) {
        this.namaTempat = namaTempat;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.imgUrl = imgUrl;
    }

    public String getNamaTempat() {
        return namaTempat;
    }

    public void setNamaTempat(String namaTempat) {
        this.namaTempat = namaTempat;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
