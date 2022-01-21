package nas.vedio.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import nas.vedio.bean.VedioIndex;
import nas.vedio.dao.VedioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VedioService {
    @Autowired
    private VedioDao vedioDao;
    public VedioIndex getByPath(String path){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("filepath",path);
        return vedioDao.selectOne(queryWrapper);
    }
    public List<VedioIndex> getChilden(String basepath){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("parentpath",basepath);
        return vedioDao.selectList(queryWrapper);
    }
}
