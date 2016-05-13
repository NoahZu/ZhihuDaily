package com.zu.jinhao.zhihuribao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zujinhao on 15/9/6.
 */
public class Story implements Serializable{
    String title;
    String ga_prefix;
    List<String> images;
    int type;
    int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
