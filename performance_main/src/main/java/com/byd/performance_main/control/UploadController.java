package com.byd.performance_main.control;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_utils.bean.StaticResourceBean;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/upload")
public class UploadController extends BaseController {

    //获取上传的文件夹，具体路径参考application.properties中的配置
    @Value("${web.relative-path}")
    private String relativePath;
    @Value("${web.file}")
    private String file;
    @Value("${web.image}")
    private String image;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String max_file_size;


    /**
     * GET请求
     * 上传页面，也将显示已经存在的文件
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/index")
    public String index(Model model) {
        urlLogInfo("/upload/index");
        //获取已存在的文件
        try {
            HashMap<String, File> filesMap = CommonMethod.initUploadFile(relativePath, image, file);
            File[] files = filesMap.get(file).listFiles();
            File[] images = filesMap.get(image).listFiles();

            ArrayList<StaticResourceBean> fileList = getStaticResourceBeanList(files, file);
            ArrayList<StaticResourceBean> imageList = getStaticResourceBeanList(images, image);

            model.addAttribute("files", fileList);
            model.addAttribute("images", imageList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "/upload";
    }

    private ArrayList<StaticResourceBean> getStaticResourceBeanList(File[] files, String path) {
        ArrayList<StaticResourceBean> list = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getName();
                String absolutePath = files[i].getAbsolutePath();
//                logger.info(name);
//                logger.info(absolutePath);
//                logger.info(path + name);
                StaticResourceBean staticResourceBean = new StaticResourceBean();
                staticResourceBean.setName(name);
                staticResourceBean.setAbsolutePath(absolutePath);
                staticResourceBean.setRelativePath(path + name);
                list.add(staticResourceBean);
            }

        }
        return list;
    }

    /**
     * POST请求
     *
     * @param request
     * @param files
     * @return
     */
    @PostMapping(value = "/index")
    public String index(HttpServletRequest request, @RequestParam("headimg") MultipartFile[] files) {
        //可以从页面传参数过来
        logger.info("name=====" + request.getParameter("name"));
        logger.info("files size=====" + files.length);
        boolean success = CommonMethod.uploadFiles(files, relativePath, image, file, max_file_size);
        logInfo(success);
        return "redirect:/upload/index";
    }


}


