package com.example.nasmanage.bean;

public class ApiFile {
    private String id;
    private boolean dir;
    private String filename;
    private String filepath;
    private String imagepath;
    private String title;
    private String classify;
    private String fileurl;
    private long createtime;
    private long size;
    private int status;

    @Override
    public String toString() {
        return "ApiFile{" +
                "id='" + id + '\'' +
                ", dir=" + dir +
                ", filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", title='" + title + '\'' +
                ", classify='" + classify + '\'' +
                ", fileurl='" + fileurl + '\'' +
                ", createtime=" + createtime +
                ", size=" + size +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDir() {
        return dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
