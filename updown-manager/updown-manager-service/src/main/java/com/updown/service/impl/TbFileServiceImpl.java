package com.updown.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.mapper.FileMapper;
import com.updown.pojo.File;
import com.updown.service.TbFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbFileServiceImpl implements TbFileService {

    @Autowired
    private FileMapper fileMapper;


    @Override
    public UpdownResult insertTbFile(File file) {

        if(file == null){
            return UpdownResult.build(404,"无file");
        }
        this.fileMapper.insert(file);
        return UpdownResult.ok();
    }
}
