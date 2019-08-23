package com.uodown.search.service;

import com.updown.search.service.pojo.SearchData;

/**
 * @auther: 闫昊
 * @date: 2019/8/23
 */
public interface SearchService {
    /**
     * 构建搜索数据，用来存入es索引库
     * @param searchData
     * @return
     */
    SearchData buildSearch(SearchData searchData);
}
