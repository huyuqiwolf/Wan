package com.hlox.app.wan.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Banner
 */
public class Banner {

    @SerializedName("data")
    private List<Data> data;
    @SerializedName("errorCode")
    private Integer errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class Data {
        @SerializedName("desc")
        private String desc;
        @SerializedName("id")
        private Integer id;
        @SerializedName("imagePath")
        private String imagePath;
        @SerializedName("isVisible")
        private Integer isVisible;
        @SerializedName("order")
        private Integer order;
        @SerializedName("title")
        private String title;
        @SerializedName("type")
        private Integer type;
        @SerializedName("url")
        private String url;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public Integer getIsVisible() {
            return isVisible;
        }

        public void setIsVisible(Integer isVisible) {
            this.isVisible = isVisible;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
