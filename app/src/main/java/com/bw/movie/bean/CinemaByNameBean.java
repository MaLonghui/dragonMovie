package com.bw.movie.bean;

import java.io.Serializable;
import java.util.List;

public class CinemaByNameBean implements Serializable {

    /**
     * result : [{"address":"朝阳区广顺北大街16号望京华彩商业中心B1","commentTotal":0,"distance":0,"followCinema":0,"id":10,"logo":"http://172.17.8.100/images/movie/logo/hyxd.jpg","name":"华谊兄弟影院"}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * address : 朝阳区广顺北大街16号望京华彩商业中心B1
         * commentTotal : 0
         * distance : 0
         * followCinema : 0
         * id : 10
         * logo : http://172.17.8.100/images/movie/logo/hyxd.jpg
         * name : 华谊兄弟影院
         */

        private String address;
        private String commentTotal;
        private String distance;
        private String followCinema;
        private String id;
        private String logo;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(String commentTotal) {
            this.commentTotal = commentTotal;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(String followCinema) {
            this.followCinema = followCinema;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
