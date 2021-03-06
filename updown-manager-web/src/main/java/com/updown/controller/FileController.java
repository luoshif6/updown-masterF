package com.updown.controller;

import com.updown.common.pojo.RequestFile;
import com.updown.common.pojo.UpdownResult;
import com.updown.common.utils.CookieUtils;
import com.updown.pojo.File;
import com.updown.pojo.Preview;
import com.updown.pojo.User;
import com.updown.service.FileService;
import com.updown.service.SelectFileService;
import com.updown.service.TbPreviewService;
import com.updown.sso.service.UserLoginService;
import com.updown.user.service.ManagerHandleService;
import com.updown.user.service.UserHandleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 文件与服务器之间的操作
 *
 * @param
 */
@Controller
@RequestMapping("file")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private SelectFileService selectFileService;

    @Autowired
    private TbPreviewService tbPreviewService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserHandleService userHandleService;
    @Autowired
    private ManagerHandleService managerHandleService;
    @Value("${UP_TOKEN_KEY}")
    private String UP_TOKEN_KEY;

    /**
     * 文件上传
     *
     * @param
     * @param
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpdownResult> createfile(@ModelAttribute RequestFile requestFile) {
        try {
            MultipartFile uploadFile =  requestFile.getUploadFile();
            if (uploadFile == null) {http://loaclhost:8089/file/upload
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(400, "无文件"));
            }
            String originalFilename = uploadFile.getOriginalFilename();
            int index = originalFilename.indexOf(".");
//          filename：文件不包括后缀的名字
            String filename = originalFilename.substring(0,index);
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            UpdownResult result = this.fileService.createFile(uploadFile.getBytes(), extName);
            String url = result.getData().toString();
            //响应上传图片的url
            String v[]={url,filename};
            if (url.equals("error")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件上传失败", null));
            } else {
                return ResponseEntity.ok(UpdownResult.ok(v));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件上传失败1", null));
        }
    }


    /**
     * 文件下载
     *
     * @param
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UpdownResult> fileDownload(@RequestParam("file_id") Long file_id) {
        if (file_id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件下载失败", null));
        }
//        根据file_id查询文件信息
        File file = selectFileService.selectFileByFileId(file_id);
        String filePath = file.getFile_url();
        String taskName = managerHandleService.selectTaskByTaskId(file.getTask_id()).getTask_name();
        String userName = userHandleService.findUserByUserId(file.getUser_id()).getUser_name();
        if(taskName == null){
            taskName = "无";
        }
        System.out.println(taskName);
        System.out.println(userName);
        System.out.println(file.getFile_name());
        System.out.println("------------------------");
        String fileName = "【任务 "+taskName+"】"+" · "+userName+" · "+file.getFile_name();
        String fileUrl = "C:/updown/data";
        this.fileService.getFile(filePath, fileName, fileUrl);
        return ResponseEntity.ok(UpdownResult.ok());

    }


    /**
     * 文件预览
     *
     * @param file_id
     * @return
     */
    @RequestMapping(value = "preview", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UpdownResult> filePreview(@RequestParam("file_id") Long file_id,
                                                    HttpServletRequest request) {
        if (file_id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(400, "file_id为空", null));

        }
//        根据id查询预览对象
        Preview preview = tbPreviewService.selectByFileId(file_id);
        if (preview != null) {
            String pdf_file_url = preview.getPdf_file_url();
            return ResponseEntity.status(HttpStatus.OK).body(UpdownResult.ok(pdf_file_url));
        }
//        根据id获取文件fastDfs的url
        String file_url = selectFileService.selectFileByFileId(file_id).getFile_url();
        String url = null;
//        获取文件类型
        String type = StringUtils.substringAfterLast(file_url, ".");
//        如果是doc或者docx文件转成pdf并下载到本地，返回本地url
        if (type.equals("doc") || type.equals("docx")) {
//            进入doc预览处理方法
            UpdownResult previewPath = fileService.filePreview(file_url, type);
            /**
             * 获取pdf信息，存到预览表中
             */
//           获取token
            String token = CookieUtils.getCookieValue(request, UP_TOKEN_KEY);
//          通过sso的服务获取用户信息
            UpdownResult result = this.userLoginService.findUserByToken(token);
            User user = (User) result.getData();
            Preview preview1 = new Preview();
            preview1.setFile_id(file_id);
//            preview1.setUser_id(user.getUser_id());
            preview1.setUser_id(4l);
            preview1.setPdf_file_url(previewPath.getData().toString());
//            存入预览表中
            tbPreviewService.insertTbPreview(preview1);
//            判断返回的url是否相同
            if (previewPath.getStatus() == 200) {
                return ResponseEntity.ok(previewPath);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(previewPath);
        } else {
//            如果不是doc文件类型直接返回路径
            return ResponseEntity.status(HttpStatus.OK).body(UpdownResult.ok(file_url));
        }

    }

}
