package nas.vedio.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import nas.vedio.bean.VedioIndex;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VedioDao extends BaseMapper<VedioIndex> {
}
