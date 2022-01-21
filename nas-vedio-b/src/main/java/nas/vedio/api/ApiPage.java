package nas.vedio.api;

import lombok.Data;

import java.util.List;

@Data
public class ApiPage {
    private String basePath;
    private int total;
    private int pageSize;
    private int pageNum;
    List<ApiFile> files;
}
