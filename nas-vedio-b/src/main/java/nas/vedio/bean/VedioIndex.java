package nas.vedio.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("vedio_index")
@Data
public class VedioIndex {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String filepath;
    private String filename;
    private String parentpath;
    private String classify;
    private String content;
    private String imagepath;
    private int status;
    private long createtime;
}
