package com.updown.search.service.impl;

import com.updown.search.service.SearchService;

/**
 * @auther: 闫昊
 * @date: 2019/8/23
 */
//@Service
public class SearchServiceImpl implements SearchService {
/*

    @Autowired
    private SelectFileService selectFileService;
    @Autowired
    private UserHandleService userHandleService;
    @Autowired
    private SearchDataRepository searchDataRepository;
    @Autowired
    private ElasticsearchTemplate template;
*/


    /**
     * 删除索引库数据
     *
     * @param file_id
     */
/*    @Override
    public void deleteSearchData(Long file_id) {
//        根据id删除索引库数据
        searchDataRepository.deleteById(file_id);
    }*/

    /**
     * 新增索引
     *
     * @param file_id
     */
/*
    @Override
    public void insertNewSearchData(Long file_id) {
//        根据文件id查询file
        File file = selectFileService.selectFileByFileId(file_id);
//        构建索引库所需要的数据
        SearchData searchData = buildSearch(file);
//        保存
        searchDataRepository.save(searchData);
    }
*/

    /**
     * 构建搜索数据，用来存入es索引库的方法
     *
     * @return
     */
/*    private SearchData buildSearch(File file) {
        *//**
         * 查询所有信息
         *//*
        SearchData searchData = new SearchData();
        searchData.setFile_id(file.getFile_id());
        searchData.setFile_name(file.getFile_name());
        searchData.setUser_name((userHandleService.findUserByUserId(file.getUser_id())).getUser_name());
        searchData.getFile_url(file.getFile_url());

        return searchData;
    }*/

    /**
     * 根据搜索条件搜索
     *
     * @param key
     * @return
     */
/*
    @Override
    public List<SearchData> searchByKey(String key) {
//        搜索数据
        List<SearchData> byFileName = searchDataRepository.findByFile_name(key);
        List<SearchData> byUserName = searchDataRepository.findByUser_name(key);
//        去重
        byFileName.removeAll(byUserName);
        byFileName.addAll(byUserName);
//        返回
        return byFileName;
    }
*/


}
