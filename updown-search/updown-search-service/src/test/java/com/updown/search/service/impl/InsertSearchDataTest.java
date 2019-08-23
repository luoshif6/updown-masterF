package com.updown.search.service.impl;

import com.updown.search.service.pojo.SearchData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * @auther: 闫昊
 * @date: 2019/8/23
 */

public class InsertSearchDataTest {

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 创建索引
     */
    @Test
    public void createIndex() {
        template.createIndex(SearchData.class);
        template.putMapping(SearchData.class);
    }


}
