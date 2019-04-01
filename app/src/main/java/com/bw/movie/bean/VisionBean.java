package com.bw.movie.bean;

public class VisionBean {

    /**
     * flag : 1
     * downloadUrl : http://mobile.bwstudent.com/media/2019215/movie_prod_xqc.apk
     * message : 查询成功
     * status : 0000
     */

    private String flag;
    private String downloadUrl;
    private String message;
    private String status;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
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
}
