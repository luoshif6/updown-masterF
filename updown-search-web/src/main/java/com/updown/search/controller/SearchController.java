package com.updown.search.controller;

import com.updown.search.service.SearchService;
import com.updown.common.pojo.SearchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @auther: 闫昊
 * @date: 2019/8/23
 */
@Controller
@RequestMapping("search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("byKey")
    public ResponseEntity<List<SearchData>> searchBykey(String key) {
        return ResponseEntity.ok(searchService.searchByKey(key));
    }
}
