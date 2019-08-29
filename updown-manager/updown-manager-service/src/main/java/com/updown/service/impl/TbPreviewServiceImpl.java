package com.updown.service.impl;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.Preview;
import com.updown.service.FileService;
import com.updown.service.TbPreviewService;
import com.updown.mapper.PreviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbPreviewServiceImpl implements TbPreviewService {

    @Autowired
    private PreviewMapper previewMapper;
    @Autowired
    private FileService fileService;

    @Autowired
    private TbPreviewService tbPreviewService;
    /**
     * 插入PDF数据
     * @param preview
     * @return
     */
    @Override
    public UpdownResult insertTbPreview(Preview preview) {

        //补全信息
        preview.setPreview_id(null);
        preview.setUser_id(preview.getUser_id());
        preview.setFile_id(preview.getFile_id());
        preview.setPdf_file_url(preview.getPdf_file_url());

        //进行插入操作
        int count =previewMapper.insert(preview);
        //如果执行结果条数不为1，则抛出异常
        if (count != 1) {
            throw new UpException(ExceptionEnum.USER_INSERT_FAIL);
        }
        return UpdownResult.ok();
    }

    /**
     * 根据user_id删除pdf文件
     * @param user_id
     * @return
     */
    @Override
    public UpdownResult deleteTbPreviewByUserId(Long user_id) {
        Preview preview = new Preview();
        preview.setUser_id(user_id);
        //查询出符合条件的结果集合
        List<Preview> previews = previewMapper.select(preview);
        //逐条删除
        previews.forEach(pr -> {
            //删除服务器中的pdf文件
            fileService.deleteFile(pr.getPdf_file_url());
            //删除表中的记录
            previewMapper.delete(pr);
        });
        return UpdownResult.ok();
    }

    /**
     * 根据file_id查询预览文件
     * @param file_id
     * @return
     */
    @Override
    public Preview selectByFileId(Long file_id) {

        Preview preview = new Preview();
        preview.setFile_id(file_id);

        Preview preview1 = previewMapper.selectOne(preview);


        return preview1;
    }


}
