package com.updown.service;
import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.Preview;
public interface TbPreviewService {

    //添加记录
    UpdownResult insertTbPreview(Preview preview);
    //删除记录
    UpdownResult deleteTbPreviewByFileId(Long file_id);
    //根据file_id查预览文件
    Preview selectByFileId(Long file_id);
}
