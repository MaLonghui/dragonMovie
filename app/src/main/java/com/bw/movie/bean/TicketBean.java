package com.bw.movie.bean;

import java.util.List;

public class TicketBean {

    /**
     * result : [{"amount":1,"begStringime":"17:00:00","cinemaName":"大地影院-北京海淀西三旗物美","createTime":1553603698000,"endTime":"04:56:00","id":4618,"movieName":"江湖儿女","orderId":"20190326203458960","price":0.28,"screeningHall":"4号厅","status":1,"userId":462}]
     * message : 请求成功
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
         * amount : 1
         * begStringime : 17:00:00
         * cinemaName : 大地影院-北京海淀西三旗物美
         * createTime : 1553603698000
         * endTime : 04:56:00
         * id : 4618
         * movieName : 江湖儿女
         * orderId : 20190326203458960
         * price : 0.28
         * screeningHall : 4号厅
         * status : 1
         * userId : 462
         */

        private String amount;
        private String begintime;
        private String cinemaName;
        private long createTime;
        private String endTime;
        private String id;
        private String movieName;
        private String orderId;
        private double price;
        private String screeningHall;
        private String status;
        private String userId;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begStringime) {
            this.begintime = begStringime;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
