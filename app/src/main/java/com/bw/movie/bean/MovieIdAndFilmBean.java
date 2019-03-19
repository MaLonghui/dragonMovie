package com.bw.movie.bean;

import java.util.List;

public class MovieIdAndFilmBean {

    /**
     * result : [{"beginTime":"20:00","duration":"130分钟","endTime":"21:48","id":596,"price":0.15,"screeningHall":"7号厅","seatsTotal":90,"seatsUseCount":30,"status":1},{"beginTime":"17:00","duration":"130分钟","endTime":"04:56","id":675,"price":0.15,"screeningHall":"4号厅","seatsTotal":46,"seatsUseCount":28,"status":1},{"beginTime":"22:00","duration":"130分钟","endTime":"23:55","id":687,"price":0.15,"screeningHall":"2号厅","seatsTotal":40,"seatsUseCount":28,"status":1},{"beginTime":"19:00","duration":"130分钟","endTime":"20:53","id":691,"price":0.15,"screeningHall":"3号厅","seatsTotal":40,"seatsUseCount":28,"status":1},{"beginTime":"20:00","duration":"130分钟","endTime":"21:48","id":695,"price":0.15,"screeningHall":"2号厅","seatsTotal":40,"seatsUseCount":28,"status":1},{"beginTime":"20:00","duration":"130分钟","endTime":"21:48","id":699,"price":0.15,"screeningHall":"8号厅","seatsTotal":78,"seatsUseCount":30,"status":1}]
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
         * beginTime : 20:00
         * duration : 130分钟
         * endTime : 21:48
         * id : 596
         * price : 0.15
         * screeningHall : 7号厅
         * seatsTotal : 90
         * seatsUseCount : 30
         * status : 1
         */

        private String beginTime;
        private String duration;
        private String endTime;
        private String id;
        private String price;
        private String screeningHall;
        private String seatsTotal;
        private String seatsUseCount;
        private String status;

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public String getSeatsTotal() {
            return seatsTotal;
        }

        public void setSeatsTotal(String seatsTotal) {
            this.seatsTotal = seatsTotal;
        }

        public String getSeatsUseCount() {
            return seatsUseCount;
        }

        public void setSeatsUseCount(String seatsUseCount) {
            this.seatsUseCount = seatsUseCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
