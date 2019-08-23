package com.uodown.search.service;

import com.updown.search.service.pojo.SearchData;

import java.util.List;

/**
 * @auther: 闫昊
 * @date: 2019/8/23
 */
public interface SearchService {

    /**
     * 新增索引库
     */
    void insertNewSearchData(Long file_id);

    /**
     * 搜索功能
     */
    List<SearchData> searchByKey(String key);

    /**
     * 删除功能
     */
    void deleteSearchData(Long file_id);
}
