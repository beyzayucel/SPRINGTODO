package com.haratres.todo.dto;

import java.util.Date;

public class ImageDto {
    int id;
    String imageUrl;
    Date date;

    public ImageDto() {
    }

    public ImageDto(Date date, String imageUrl) {
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
