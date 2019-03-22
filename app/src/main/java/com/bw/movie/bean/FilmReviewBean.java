package com.bw.movie.bean;

import java.io.Serializable;
import java.util.List;

public class FilmReviewBean implements Serializable {

    /**
     * result : [{"commentContent":"123456","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","commentId":168,"commentTime":1552957781000,"commentUserId":12086,"commentUserName":"深海霸主皮皮虾丶","greatNum":0,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"1234","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","commentId":156,"commentTime":1552917511000,"commentUserId":12086,"commentUserName":"深海霸主皮皮虾丶","greatNum":1,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"小老弟","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-03-18/20190318110434.jpg","commentId":149,"commentTime":1552897676000,"commentUserId":12093,"commentUserName":"寂然","greatNum":0,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"123","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-03-18/20190318110434.jpg","commentId":148,"commentTime":1552897494000,"commentUserId":12093,"commentUserName":"寂然","greatNum":0,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"123123","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-03-18/20190318110434.jpg","commentId":147,"commentTime":1552897472000,"commentUserId":12093,"commentUserName":"寂然","greatNum":0,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"电影好看","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-03-12/20190312105053.thumb.700_0.jpeg","commentId":133,"commentTime":1552360606000,"commentUserId":12089,"commentUserName":"碧螺春","greatNum":0,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"电影好看","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-03-12/20190312105053.thumb.700_0.jpeg","commentId":132,"commentTime":1552360515000,"commentUserId":12089,"commentUserName":"碧螺春","greatNum":0,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"11111111111111111111111111111111111","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-02-19/20190219205242.jpg","commentId":77,"commentTime":1550580866000,"commentUserId":1,"commentUserName":"哟","greatNum":1,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"凄凄切切凄凄切切凄凄切切群去群群群群群群群群群群群群群群群群群群群群群群群群","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2019-02-19/20190219205242.jpg","commentId":76,"commentTime":1550580827000,"commentUserId":1,"commentUserName":"哟","greatNum":1,"hotComment":0,"isGreat":0,"replyNum":0},{"commentContent":"666","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","commentId":3,"commentTime":1550298205000,"commentUserId":10441,"commentUserName":"斑马发送到","greatNum":1,"hotComment":0,"isGreat":1,"replyNum":2},{"commentContent":"hhhhh","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","commentId":1,"commentTime":1550133672000,"commentUserId":3777,"commentUserName":"95799151817","greatNum":15,"hotComment":0,"isGreat":0,"replyNum":99}]
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
         * commentContent : 123456
         * commentHeadPic : http://172.17.8.100/images/movie/head_pic/bwjy.jpg
         * commentId : 168
         * commentTime : 1552957781000
         * commentUserId : 12086
         * commentUserName : 深海霸主皮皮虾丶
         * greatNum : 0
         * hotComment : 0
         * isGreat : 0
         * replyNum : 0
         */

        private String commentContent;
        private String commentHeadPic;
        private String commentId;
        private long commentTime;
        private String commentUserId;
        private String commentUserName;
        private String greatNum;
        private String hotComment;
        private String isGreat;
        private String replyNum;

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public String getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(String commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public String getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(String greatNum) {
            this.greatNum = greatNum;
        }

        public String getHotComment() {
            return hotComment;
        }

        public void setHotComment(String hotComment) {
            this.hotComment = hotComment;
        }

        public String getIsGreat() {
            return isGreat;
        }

        public void setIsGreat(String isGreat) {
            this.isGreat = isGreat;
        }

        public String getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(String replyNum) {
            this.replyNum = replyNum;
        }
    }
}
