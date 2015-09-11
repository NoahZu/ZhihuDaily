package com.zu.jinhao.zhihuribao.model;

/**
 * Created by zujinhao on 15/8/24.
 */

/**
 *   10.主题日报内容查看
 *   URL: http://news-at.zhihu.com/api/4/theme/11
 *   使用在 主题日报列表查看 中获得需要查看的主题日报的 id，
 *   拼接在 http://news-at.zhihu.com/api/4/theme/ 后，得到对应主题日报 JSON 格式的内容
 */
/**
 分析：
     stories : 该主题日报中的文章列表
     images : 图像地址（其类型为数组。请留意在代码中处理无该属性与数组长度为 0 的情况）
     type : 类型，作用未知
     title : 消息的标题
     description : 该主题日报的介绍
     background : 该主题日报的背景图片（大图）
     color : 颜色，作用未知
     name : 该主题日报的名称
     image : 背景图片的小图版本
     editors : 该主题日报的编辑（『用户推荐日报』中此项的指是一个空数组，在 App 中的主编栏显示为『许多人』，点击后访问该主题日报的介绍页面，请留意）
     url : 主编的知乎用户主页
     bio : 主编的个人简介
     id : 数据库中的唯一表示符
     avatar : 主编的头像
     name : 主编的姓名
     image_source : 图像的版权信息
 */
import com.google.gson.annotations.SerializedName;

import junit.framework.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubjectDailyContentJson implements Serializable{
    public List<Story> stories = new ArrayList<>();
    public static class Story implements Serializable{
        List<String> images;
        int type;
        int id;
        String title;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    public String description;
    public String background;
    public int color;
    public String name;
    public String image;
    public List<Editor> editors;
    public static class Editor implements Serializable
    {
        String url;
        String bio;
        int id;
        String avator;
        String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvator() {
            return avator;
        }


        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    @SerializedName("image_source")
    public String imageSource;


    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
