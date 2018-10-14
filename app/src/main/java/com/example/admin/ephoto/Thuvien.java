package com.example.admin.ephoto;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Thuvien  implements Parcelable{
    private int id;
    private int date;
    private String Path;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Thuvien() {
    }

    public Thuvien(int id, int date, String path, String name) {
        this.id = id;
        this.date = date;
        Path = path;
        this.name = name;
    }

    public Thuvien(Parcel in) {
        id = in.readInt();
        date = in.readInt();
        Path = in.readString();
        name = in.readString();
    }

    public static final Creator<Thuvien> CREATOR = new Creator<Thuvien>() {
        @Override
        public Thuvien createFromParcel(Parcel in) {
            return new Thuvien(in);
        }

        @Override
        public Thuvien[] newArray(int size) {
            return new Thuvien[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(date);
        parcel.writeString(Path);
        parcel.writeString(name);
    }
}
