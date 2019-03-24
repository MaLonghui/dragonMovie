package com.bw.movie.bean;

import java.util.List;

public class MovieAttentionBean {

    /**
     * result : [{"address":"海淀区中关村广场购物中心津乐汇三层（鼎好一期西侧）","commentTotal":0,"distance":0,"followCinema":0,"id":12,"logo":"http://mobile.bwstudent.com/images/movie/logo/mjhlyc.jpg","name":"美嘉欢乐影城中关村店"},{"address":"朝阳区建国路93号万达广场三层","commentTotal":0,"distance":0,"followCinema":0,"id":6,"logo":"http://mobile.bwstudent.com/images/movie/logo/wd.jpg","name":"北京CBD万达广场店"},{"address":"北京市朝阳区三丰北里2号楼悠唐广场B1层","commentTotal":0,"distance":0,"followCinema":0,"id":8,"logo":"http://mobile.bwstudent.com/images/movie/logo/bn.jpg","name":"北京博纳影城朝阳门旗舰店"},{"address":"海淀区远大路1号金源时代购物中心5层东首","commentTotal":0,"distance":0,"followCinema":0,"id":11,"logo":"http://mobile.bwstudent.com/images/movie/logo/xmgj.jpg","name":"星美国际影城"},{"address":"北京市海淀区中关村大街28号","commentTotal":0,"distance":0,"followCinema":0,"id":16,"logo":"http://mobile.bwstudent.com/images/movie/logo/hdjy.jpg","name":"海淀剧院"},{"address":"海淀区复兴路69号五棵松卓展时代百货五层东侧","commentTotal":0,"distance":0,"followCinema":0,"id":13,"logo":"http://mobile.bwstudent.com/images/movie/logo/bjalclgj.jpg","name":"北京耀莱成龙国际影城（五棵松店）"},{"address":"北京市海淀区中关村大街19号新中关购物中心B1层","commentTotal":0,"distance":0,"followCinema":0,"id":15,"logo":"http://mobile.bwstudent.com/images/movie/logo/jy.jpg","name":"金逸北京中关村店"},{"address":"朝阳区湖景东路11号新奥购物中心B1(东A口)","commentTotal":0,"distance":0,"followCinema":0,"id":5,"logo":"http://mobile.bwstudent.com/images/movie/logo/CGVxx.jpg","name":"CGV星星影城"}]
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

    public static class ResultBean {
        /**
         * address : 海淀区中关村广场购物中心津乐汇三层（鼎好一期西侧）
         * commentTotal : 0
         * distance : 0
         * followCinema : 0
         * id : 12
         * logo : http://mobile.bwstudent.com/images/movie/logo/mjhlyc.jpg
         * name : 美嘉欢乐影城中关村店
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
