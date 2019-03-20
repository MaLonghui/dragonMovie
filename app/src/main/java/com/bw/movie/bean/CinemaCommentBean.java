package com.bw.movie.bean;

import java.util.List;

public class CinemaCommentBean {

    /**
     * result : [{"commentContent":"6666","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg","commentId":15,"commentTime":1552967150000,"commentUserId":451,"commentUserName":"你的益达666","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"6666","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg","commentId":14,"commentTime":1552873447000,"commentUserId":451,"commentUserName":"你的益达666","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"很棒","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-03-12/20190312110713.unknown","commentId":13,"commentTime":1552379352000,"commentUserId":454,"commentUserName":"李跃坤","greatNum":1,"hotComment":0,"isGreat":0},{"commentContent":"6666","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg","commentId":12,"commentTime":1552361931000,"commentUserId":451,"commentUserName":"你的益达666","greatNum":1,"hotComment":0,"isGreat":0},{"commentContent":"没有见我比我好看的片子","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-03-12/20190312105955.jpeg","commentId":11,"commentTime":1552361674000,"commentUserId":453,"commentUserName":"胡圆圆","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"很烂","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg","commentId":9,"commentTime":1552360860000,"commentUserId":456,"commentUserName":"浪里小白龙","greatNum":0,"hotComment":0,"isGreat":0},{"commentContent":"很棒","commentHeadPic":"http://mobile.bwstudent.com/images/movie/head_pic/2019-03-19/20190319173926.jpg","commentId":8,"commentTime":1552360484000,"commentUserId":450,"commentUserName":"我的益达","greatNum":0,"hotComment":0,"isGreat":0}]
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
         * commentContent : 6666
         * commentHeadPic : http://mobile.bwstudent.com/images/movie/head_pic/bwjy.jpg
         * commentId : 15
         * commentTime : 1552967150000
         * commentUserId : 451
         * commentUserName : 你的益达666
         * greatNum : 0
         * hotComment : 0
         * isGreat : 0
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
    }
}
