package com.example.after.alittletwo.entity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by hongjian.chen on 2018/7/25.
 */

public class ResponseContent {


    JsonArray data;

    List<Content> list;

    public ResponseContent(List<Content> list) {
        list = new Gson().fromJson(data, ArrayList.class);
        this.list = list;
    }

    public JsonArray getData() {
        return data;
    }


    public static class Content implements Serializable {
        String name;
        String downloadPath;
        String lable;
        String iconPath;

        public String getDownloadPath() {
            return downloadPath;
        }

        public void setDownloadPath(String downloadPath) {
            this.downloadPath = Constant.UMS3_FILE.getBaseUrl() + downloadPath;
        }

        public String getIconPath() {
            return iconPath;
        }

        public void setIconPath(String iconPath) {
            this.iconPath = Constant.UMS3_FILE.getBaseUrl() + iconPath;
        }

        public String getLable() {
            return lable;
        }

        public void setLable(String lable) {
            this.lable = lable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public List<Content> getList() {
        return list;
    }

}
