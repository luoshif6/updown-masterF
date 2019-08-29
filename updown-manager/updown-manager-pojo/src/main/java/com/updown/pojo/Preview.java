package com.updown.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 文件pojo
 */
@Table(name = "tb_preview")
public class Preview implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long preview_id;  //预览文件id

    private String pdf_file_url;   //预览文件路径

    private Long user_id;  //用户id

    private Long file_id;  //预览文件对应的原文件id

    public Long getPreview_id() {
        return preview_id;
    }

    public void setPreview_id(Long preview_id) {
        this.preview_id = preview_id;
    }

    public String getPdf_file_url() {
        return pdf_file_url;
    }

    public void setPdf_file_url(String pdf_file_url) {
        this.pdf_file_url = pdf_file_url;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getFile_id() {
        return file_id;
    }

    public void setFile_id(Long file_id) {
        this.file_id = file_id;
    }



}
