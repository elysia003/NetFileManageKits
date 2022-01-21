package nas.vedio.controller;

import nas.vedio.api.ApiFile;
import nas.vedio.api.ApiPage;
import nas.vedio.bean.Config;
import nas.vedio.bean.VedioIndex;
import nas.vedio.dao.VedioDao;
import nas.vedio.service.Init;
import nas.vedio.service.VedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class TestC {
    @Autowired
    private VedioDao vedioDao;
    @Autowired
    private VedioService vedioService;
    @Autowired
    private Init init;
    @Autowired
    private Config config;

    @GetMapping("/run")
    public void run() {
        int t = init.initAll("E:/vedio", true);
    }
    @GetMapping("initOne")
    public VedioIndex initOne(String path) {
        return init.initOne(path);
    }
    @GetMapping("/getSimpleFile")
    public List<ApiFile> getSimpleFile(String basePath){
        return quickGetFiles(basePath).getFiles();
    }
    @GetMapping("/quickGetFiles")
    public ApiPage quickGetFiles(String basePath) {
        try {
            Thread.sleep(00);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ApiFile> list = new ArrayList<>();
        File[] files = new File(basePath).listFiles();
        ApiFile apiFile = null;
        for (File file : files) {
            apiFile = new ApiFile();
            apiFile.setFilename(file.getName());
            apiFile.setFilepath(file.getAbsolutePath());
            apiFile.setFileurl(getUrl(apiFile.getFilepath()));
            if (file.isDirectory()) {
                apiFile.setDir(true);
                apiFile.setTitle(file.getName());
            } else {
                apiFile.setDir(false);
                apiFile.setCreatetime(file.lastModified());
                apiFile.setSize(file.length());
                if (Init.isVedio(file)) {
                    String id = init.getIdForName(file.getName());
                    if (id != null) {
                        apiFile.setId(id);
                        apiFile.setTitle(init.getNameWithoutID(file.getName()));
                        apiFile.setImagepath(id);
                        apiFile.setStatus(100);
                    } else {
                        apiFile.setTitle(file.getName());
                        apiFile.setImagepath("未处理");
                        apiFile.setStatus(102);
                    }
                } else {
                    apiFile.setTitle(file.getName());
                    apiFile.setStatus(200);
                }
            }
            list.add(apiFile);
        }
        list.sort((a, b) -> {
                    if (a.isDir() == b.isDir()) {
                        if (a.getCreatetime() == b.getCreatetime()) {
                            return a.getFilename().compareTo(b.getFilename());
                        }
                        return a.getCreatetime() > b.getCreatetime() ? -1 : 1;
                    } else {
                        if (a.isDir()) {
                            return -1;
                        }
                        return 1;
                    }
                }
        );
        ApiPage apiPage = new ApiPage();
        apiPage.setFiles(list);
        apiPage.setTotal(list.size());
        apiPage.setBasePath(basePath);
        return apiPage;
    }

    @GetMapping("/getFiles")
    public ApiPage getFiles(String basePath) {
        File[] files = new File(basePath).listFiles();
        List<VedioIndex> index = vedioService.getChilden(new File(basePath).getAbsolutePath());
        System.out.println(index);
        ApiFile apiFile = null;
        List<ApiFile> apiFiles = new ArrayList<>();
        ApiPage res = new ApiPage();
        res.setTotal(files.length);
        //res.setPageNum(pageNum);
        //res.setPageSize(pageSize);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            apiFile = new ApiFile();
            if (file.isDirectory()) {
                apiFile.setDir(true);
                apiFile.setFilename(file.getName());
                apiFile.setFilepath(file.getAbsolutePath());
            } else {
                apiFile.setDir(false);
                apiFile.setFilename(file.getName());
                apiFile.setFilepath(file.getAbsolutePath());
                VedioIndex ind = findByName(index, file.getName());
                if (ind == null) {
                    String oldname = file.getName();
                    String newname = oldname.replaceAll("^##.*##$", "");
                    VedioIndex temp = new VedioIndex();
                    temp.setStatus(0);
                    temp.setImagepath("*");
                    temp.setParentpath(file.getParent());
                    temp.setFilename(file.getName());
                    temp.setFilepath(file.getAbsolutePath());
                    vedioDao.insert(temp);
                    apiFile.setTitle(file.getName());
                    apiFile.setStatus(0);
                } else {
                    apiFile.setTitle(ind.getContent());
                    apiFile.setClassify(ind.getClassify());
                    apiFile.setImagepath(ind.getImagepath());
                    apiFile.setStatus(ind.getStatus());
                }
            }
            apiFiles.add(apiFile);
        }
        res.setFiles(apiFiles);
        return res;
    }

    private VedioIndex findByName(List<VedioIndex> list, String filename) {
        for (VedioIndex file : list) {
            //System.out.println(file.getFilename()+"   "+filename);
            if (filename.equals(file.getFilename())) {
                return file;
            }
        }
        return null;
    }

    private String getUrl(String path) {
        return path.replace("\\", "/");
    }
}