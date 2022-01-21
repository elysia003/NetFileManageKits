package nas.vedio.service;

import nas.vedio.bean.Config;
import nas.vedio.bean.VedioIndex;
import nas.vedio.dao.VedioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class GenService {
    @Autowired
    private VedioService vedioService;
    @Autowired
    public VedioDao vedioDao;
    @Autowired
    private Config config;
    public int gen(VedioIndex vedioIndex){
        String res=dogen(vedioIndex.getFilepath(),config.baseImagePath+vedioIndex.getId().toString());
        if(res.equals("success")) {
            vedioIndex.setImagepath(config.baseImagePath + vedioIndex.getId().toString());
            vedioIndex.setStatus(1);
        }
        else{
            vedioIndex.setImagepath(res);
            vedioIndex.setStatus(-1);
        }
        return vedioDao.updateById(vedioIndex);
    }
    public String dogen (String vedioPath, String imagePath) {
        System.out.println("[" + LocalDateTime.now().toString() + "]" + vedioPath + "开始");
        long startTime = System.currentTimeMillis();
        String res = "";
        double len = 0;
        Process process;
        String cmd = "";
        String[] cmds={};
        try {
            cmds = new String[]{"ffprobe", "-v", "error", "-show_entries", "format=duration", "-of", "default=noprint_wrappers=1:nokey=1", vedioPath, "-loglevel", "quiet"};
            process = Runtime.getRuntime().exec(cmds);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            res = reader.readLine();
            process.waitFor(60, TimeUnit.SECONDS);
            len = Double.valueOf(res);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

        try {
            File file = new File(imagePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            return "创建文件夹失败:"+e.toString();
        }

        try {
            for (int i = 1; i < 17; i++) {
                double t = (len) / 18 * i;
                cmd = "ffmpeg -ss " + t + " -i \"" + vedioPath + "\" -f image2 -vframes 1 -y " + imagePath + "/" + (i - 1) + ".jpg -loglevel quiet";
                cmds = new String[]{"ffmpeg","-ss",t+"","-i",vedioPath,"-f","image2","-vframes","1","-y",imagePath + "/" + (i - 1) + ".jpg","-loglevel","quiet"};
                process = Runtime.getRuntime().exec(cmds);
                Boolean wf = process.waitFor(3, TimeUnit.MINUTES);
                if (wf != true) {
                    //log.error(null, vedioPath, "提取关键帧超时", cmd);
                    return "提取关键帧超时"+cmd;
                }
                if (process.exitValue() != 0) {
                    return "提取关键帧超时"+cmd;
                }
            }
        } catch (Exception e) {
            return "提取关键帧失败"+e.toString();
        }
        try {
            int startX = 0;
            int startY = 0;
            BufferedImage[] image = new BufferedImage[16];
            for (int i = 0; i < 16; i++) {
                image[i] = ImageIO.read(new FileInputStream(imagePath + "/" + i + ".jpg"));
            }
            BufferedImage temp = new BufferedImage(image[0].getWidth() * 3, image[0].getHeight() * 3, BufferedImage.TYPE_INT_RGB);
            Graphics2D res99 = temp.createGraphics();
            for (int i = 0; i < 9; i++) {
                BufferedImage b = image[i];
                res99.drawImage(b, startX, startY, b.getWidth(), b.getHeight(), null);
                startX = startX + b.getWidth();
                if ((i + 1) % 3 == 0) {
                    startY = startY + b.getHeight();
                    startX = 0;
                }
            }
            File outputfile = new File(imagePath + "/99.jpg");
            ImageIO.write(temp, "jpg", outputfile);
            temp = new BufferedImage(image[0].getWidth() * 4, image[0].getHeight() * 4, BufferedImage.TYPE_INT_RGB);
            Graphics2D res1616 = temp.createGraphics();
            startX = 0;
            startY = 0;
            for (int i = 0; i < 16; i++) {
                BufferedImage b = image[i];
                res1616.drawImage(b, startX, startY, b.getWidth(), b.getHeight(), null);
                startX = startX + b.getWidth();
                if ((i + 1) % 4 == 0) {
                    startY = startY + b.getHeight();
                    startX = 0;
                }
            }
            outputfile = new File(imagePath + "/1616.jpg");
            ImageIO.write(temp, "jpg", outputfile);
        } catch (Exception e) {
            return "合成失败:"+e.toString();
        }
        try {
            cmd = "chmod -R 777 " + imagePath;
            cmds=new String[]{"chmod","-R","777",imagePath};
            process = Runtime.getRuntime().exec(cmds);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String str;
            while ((str = bufferedReader.readLine()) != null && (str = bufferedReader1.readLine()) != null) {
            }
            bufferedReader.close();
            bufferedReader1.close();
            int waitFor;
            waitFor = process.waitFor();
            if (waitFor != 0) {
                //log.error(null, vedioPath, "修改权限失败", cmd);
                return "修改权限失败:"+cmd;
            }
        } catch (Exception e) {
            //log.error(null, vedioPath, "修改权限失败", cmd, e.toString());
            return "修改权限失败:"+e.toString();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("[" + LocalDateTime.now().toString() + "]" + vedioPath + "结束 耗时" + (endTime - startTime) / 1000 + "s");
        return "success";
    }

}