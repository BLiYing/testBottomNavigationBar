package com.example.wangchang.testbottomnavigationbar.enity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liukun on 16/3/5.
 */
@SuppressLint("ParcelCreator")
public class Subject implements Parcelable{

    private String id;
    private String alt;
    private String year;
    private String title;
    private String original_title;
    private List<String> genres;
    private List<Cast> casts = new ArrayList<>();
    private List<Cast> directors = new ArrayList<>();
    private Avatars images;


    @Override
    public String toString() {
        return "Subject.id=" + id
                + " Subject.title=" + title
                + " Subject.year=" + year
                + " Subject.originalTitle=" + original_title + casts.toString() + directors.toString() + " | ";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Cast> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Cast> directors) {
        this.directors = directors;
    }

    public Avatars getImages() {
        return images;
    }

    public void setImages(Avatars images) {
        this.images = images;
    }

    protected Subject(Parcel in){
         id = in.readString();
        alt = in.readString();
        year = in.readString();
        title = in.readString();
        original_title = in.readString();
        genres = in.createStringArrayList();
//        in.readList(casts,casts.getClass().getClassLoader());
//        in.readList(directors,directors.getClass().getClassLoader());
        in.readTypedList(casts,Cast.CREATOR);
        in.readTypedList(directors,Cast.CREATOR);
        images = in.readParcelable(images.getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /* private String id;
    private String alt;
    private String year;
    private String title;
    private String original_title;
    private List<String> genres;
    private List<Cast> casts;
    private List<Cast> directors;
    private Avatars images;*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.alt);
        dest.writeString(this.year);
        dest.writeString(this.title);
        dest.writeString(this.original_title);
        dest.writeStringList(this.genres);
//        dest.writeList(this.casts);
//        dest.writeList(this.directors);
        dest.writeTypedList(casts);
        dest.writeTypedList(directors);
        dest.writeParcelable(this.images,flags);
    }

    public static final Parcelable.Creator<Subject> CREATOR = new Parcelable.Creator<Subject>(){

        @Override
        public Subject createFromParcel(Parcel source) {
            return new Subject(source);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };






}