package com.bw.movie.bean;

import java.util.List;

public class SysMsgBean {

    /**
     * result : [{"content":"发现您刚刚修改过登陆密码,请您妥善保管好新的密码","id":5350,"pushTime":1553154920000,"status":0,"title":"系统通知","userId":589},{"content":"发现您刚刚修改过登陆密码,请您妥善保管好新的密码","id":5348,"pushTime":1553154619000,"status":0,"title":"系统通知","userId":589},{"content":"发现您刚刚修改过登陆密码,请您妥善保管好新的密码","id":5347,"pushTime":1553154610000,"status":0,"title":"系统通知","userId":589},{"content":"感谢您注册维度账号,小维希望您使用我们的产品能够获得快乐~","id":5182,"pushTime":1552701264000,"status":0,"title":"系统通知","userId":589}]
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
         * content : 发现您刚刚修改过登陆密码,请您妥善保管好新的密码
         * id : 5350
         * pushTime : 1553154920000
         * status : 0
         * title : 系统通知
         * userId : 589
         */

        private String content;
        private String id;
        private long pushTime;
        private String status;
        private String title;
        private String userId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getPushTime() {
            return pushTime;
        }

        public void setPushTime(long pushTime) {
            this.pushTime = pushTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
