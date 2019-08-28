package com.updown.controller;

import com.updown.common.pojo.UpdownResult;
import com.updown.service.FileService;
import com.updown.service.SelectFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件与服务器之间的操作
 *
 * @param
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private SelectFileService selectFileService;

    /**
     * 文件上传
     *
     * @param
     * @param
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpdownResult> createfile(@RequestParam("file") MultipartFile uploadFile) {
        try {
            if (uploadFile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(400, "无文件"));
            }
            System.out.println(uploadFile);
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            UpdownResult result = this.fileService.createFile(uploadFile.getBytes(), extName);
            String url = result.getData().toString();
            //响应上传图片的url
            if (url.equals("error")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件上传失败", null));
            } else {
                return ResponseEntity.ok(UpdownResult.ok(url));
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
     * @param filePath:文件在服务器内的地址
     * @param fileName：文件要保存的名称
     * @param fileUrl：文件要保存的路径
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UpdownResult> fileDownload(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName, @RequestParam("fileUrl") String fileUrl) {
        if (filePath == null || fileName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件下载失败", null));

        }

        this.fileService.getFile(filePath, fileName, fileUrl);
        return ResponseEntity.ok(UpdownResult.ok());

    }


    /**
     * 文件删除
     *
     * @param filePath
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UpdownResult> fileDelete(@RequestParam("filePath") String filePath) {
        if (filePath == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UpdownResult.build(404, "文件删除失败", null));

        }
        this.fileService.deleteFile(filePath);
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
    public ResponseEntity<String> filePreview(@RequestParam("file_id") Long file_id) {
//        根据id获取文件fastDfs的url
        String file_url = selectFileService.selectFileByFileId(file_id).getFile_url();
        String url = null;
//        获取文件类型
        String type = StringUtils.substringAfterLast(file_url, ".");
//        如果是doc或者docx文件转成pdf并下载到本地，返回本地url
        if (type.equals("doc") || type.equals("docx")) {
            System.out.println("进入doc预览方法处理");
            String previewPath = fileService.filePreview(file_url, type);
            url = previewPath;
        } else {
//            如果不是doc文件类型直接返回
            url = file_url;
        }
        return ResponseEntity.ok(url);
    }
}
