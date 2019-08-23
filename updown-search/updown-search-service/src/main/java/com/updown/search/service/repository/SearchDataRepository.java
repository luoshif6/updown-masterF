package com.updown.search.service.repository;

import com.updown.search.service.pojo.SearchData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Elasticsearch服务
 *
 * @auther: 闫昊
 * @date: 2019/8/23
 */
public interface SearchDataRepository extends ElasticsearchRepository<SearchData, Long> {
    /**
     * 根据文件名查询
     *
     * @return
     */
    List<SearchData> findByFile_name(String key);

    /**
     * 根据用户名查询
     *
     * @return
     */
    List<SearchData> findByUser_name(String key);
}
