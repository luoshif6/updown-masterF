package com.updown.service;

import com.updown.common.pojo.UpdownResult;
import com.updown.pojo.File;

public interface TbFileService {
    UpdownResult insertTbFile(File file);
}
