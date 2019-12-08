package com.jack.wisjoker;

import android.os.Parcel;
import android.os.Parcelable;

public class DataWisata implements Parcelable {
    public String namaTempat, lokasi, deskripsi, imgUrl, key;
    public DataWisata() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataWisata(String namaTempat, String lokasi, String deskripsi, String imgUrl) {
        this.namaTempat = namaTempat;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.imgUrl = imgUrl;
    }

    protected DataWisata(Parcel in) {
        namaTempat = in.readString();
        lokasi = in.readString();
        deskripsi = in.readString();
        imgUrl = in.readString();
    }

    public static final Creator<DataWisata> CREATOR = new Creator<DataWisata>() {
        @Override
        public DataWisata createFromParcel(Parcel in) {
            return new DataWisata(in);
        }

        @Override
        public DataWisata[] newArray(int size) {
            return new DataWisata[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namaTempat);
        dest.writeString(lokasi);
        dest.writeString(deskripsi);
        dest.writeString(imgUrl);
    }
}
