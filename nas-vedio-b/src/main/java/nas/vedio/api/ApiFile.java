package nas.vedio.api;

import lombok.Data;

@Data
public class ApiFile {
    private String id;
    private boolean isDir;
    private String filename;
    private String filepath;
    private String imagepath;
    private String title;
    private String classify;
    private String fileurl;
    private long createtime;
    private long size;
    private int status;
}