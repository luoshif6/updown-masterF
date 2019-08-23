package com.updown.service;

import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.File;

public interface TbFileService {
    //上传文件
    UpdownResult insertTbFile(File file);
    //删除文件
    UpdownResult deleteTbFileById(Long file_id);
}
