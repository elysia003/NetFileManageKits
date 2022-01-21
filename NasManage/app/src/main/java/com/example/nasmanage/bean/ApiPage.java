package com.example.nasmanage.bean;

import java.util.List;

public class ApiPage {
    private String basePath;
    private int total;
    private int pageSize;
    private int pageNum;
    List<ApiFile> files;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<ApiFile> getFiles() {
        return files;
    }

    public void setFiles(List<ApiFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ApiPage{" +
                "basePath='" + basePath + '\'' +
                ", total=" + total +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", files=" + files +
                '}';
    }
}
