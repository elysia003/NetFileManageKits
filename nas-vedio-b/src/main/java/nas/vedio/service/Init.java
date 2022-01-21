package nas.vedio.service;

import nas.vedio.bean.Config;
import nas.vedio.bean.VedioIndex;
import nas.vedio.dao.VedioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Init {
    @Autowired
    private VedioDao vedioDao;
    @Autowired
    private VedioService vedioService;
    @Autowired
    private GenService genService;
    @Autowired
    private Config config;
    public static final Pattern pattern = Pattern.compile("(" + "__" + "+\\d*" + "__" + ")(.*)");
    public static final String[] SET_VALUES = new String[]{"mp4", "avi", "mov", "rmvb", "rm", "flv", "3gp", "wmv", "ts", "mpeg", "mpg", "mkv", "flc", "m4v", "mod", "webm", "mst", "mpe", "flc", "swf", "cfg", "mts", "vob"};
    public static final HashSet<String> VEDIO_SUFFIX = new HashSet<String>(Arrays.asList(SET_VALUES));

    public String getIdForName(String filename) {
        Matcher m = pattern.matcher(filename);
        List<String> res = new ArrayList<>();
        if (m.find()) {
            return m.group(1).replace("_", "");
        } else {
            return null;
        }
    }

    public String getNameWithoutID(String filename) {
        Matcher m = pattern.matcher(filename);
        List<String> res = new ArrayList<>();
        if (m.find()) {
            return m.group(2);
        } else {
            return filename;
        }
    }

    public List<File> getFiles(String basePath) {
        File basefile = new File(basePath);
        if (basefile.isDirectory()) {
            File[] files = basefile.listFiles();
            List<File> res = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                res.add(files[i]);
            }
        }
        return null;
    }

    public static boolean isVedio(File file) {
        if (VEDIO_SUFFIX.contains(file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase())) {
            return true;
        }
        return false;
    }

    public List<File> getAllFiles(String filePath, List<File> filelist) {
        File root = new File(filePath);
        if (!root.exists()) {
            System.out.println("文件夹是空的!");
        } else {
            File[] files = root.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllFiles(file.getAbsolutePath(), filelist);
                } else {
                    if (isVedio(file)) {
                        filelist.add(file);
                    }
                }
            }
        }
        return filelist;
    }

    public VedioIndex initOne(String path) {
        File file = new File(path);
        try {
            VedioIndex vedioIndex = new VedioIndex();
            vedioIndex.setFilepath(file.getAbsolutePath());
            vedioIndex.setFilename(file.getName());
            vedioIndex.setParentpath(file.getParent());
            vedioIndex.setCreatetime(System.currentTimeMillis());
            vedioIndex.setClassify("*");
            vedioIndex.setStatus(0);
            vedioIndex.setImagepath("null");
            vedioDao.insert(vedioIndex);
            String nfp = rename(file, vedioIndex.getId() + "");
            vedioIndex.setFilepath(nfp);
            vedioIndex.setFilename(new File(nfp).getName());
            vedioDao.updateById(vedioIndex);
            genService.gen(vedioIndex);
            return vedioIndex;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String rename(File file, String id) {
        String newpath = file.getParent() + "/" + config.idbeg + id + config.idend + getNameWithoutID(file.getName());
        File nf = new File(newpath);
        file.renameTo(nf);
        return nf.getAbsolutePath();
    }

    public int initAll(String basePath, boolean flg) {
        if (basePath == null) {
            basePath = config.baseVedioPath;
        }
        List<File> files = getAllFiles(basePath, new ArrayList<>());
        int total = files.size();
        int now = 0;
        int rk = 0;
        int gen = 0;
        int err = 0;
        for (File file : files) {
            now++;
            try {
                VedioIndex vedioIndex = vedioService.getByPath(file.getAbsolutePath());
                if (vedioIndex == null) {
                    vedioIndex = new VedioIndex();
                    vedioIndex.setFilepath(file.getAbsolutePath());
                    vedioIndex.setFilename(file.getName());
                    vedioIndex.setParentpath(file.getParent());
                    vedioIndex.setCreatetime(System.currentTimeMillis());
                    vedioIndex.setClassify("*");
                    vedioIndex.setStatus(0);
                    vedioIndex.setImagepath("null");
                    vedioDao.insert(vedioIndex);
                    String nfp = rename(file, vedioIndex.getId() + "");
                    vedioIndex.setFilepath(nfp);
                    vedioIndex.setFilename(new File(nfp).getName());
                    vedioDao.updateById(vedioIndex);
                    rk++;
                }
                //if (vedioIndex.getStatus() != 1) {
                //FIXME###############################################
                //FIXME###############################################
                //FIXME###############################################
                //if (flg) {
                genService.gen(vedioIndex);
                //}
                //FIXME###############################################
                //FIXME###############################################
                //FIXME###############################################
                gen++;
                //}
                System.out.println("[" + now + "/" + total + "] rk:" + rk + " gen:" + gen + " err:" + err);
            } catch (Exception e) {
                e.printStackTrace();
                err++;
            }
        }
        return rk + gen;
    }
}