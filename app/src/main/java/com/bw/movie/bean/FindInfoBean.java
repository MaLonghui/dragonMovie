package com.bw.movie.bean;

public class FindInfoBean {

    /**
     * result : {"birthday":1552579200000,"email":"1971658757@qq.com","headPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-03-16/20190316095508.png","id":589,"lastLogStringime":1552821106000,"nickName":"dura","phone":"18813175607","sex":1}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * birthday : 1552579200000
         * email : 1971658757@qq.com
         * headPic : http://mobile.bwstudent.com/images/movie/head_pic/2019-03-16/20190316095508.png
         * id : 589
         * lastLogStringime : 1552821106000
         * nickName : dura
         * phone : 18813175607
         * sex : 1
         */

        private long birthday;
        private String email;
        private String headPic;
        private String id;
        private long lastLogStringime;
        private String nickName;
        private String phone;
        private String sex;

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getLastLogStringime() {
            return lastLogStringime;
        }

        public void setLastLogStringime(long lastLogStringime) {
            this.lastLogStringime = lastLogStringime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
