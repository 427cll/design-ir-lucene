package com.lucene.controller;

import com.lucene.common.Result;
import com.lucene.service.IndexService;
import com.lucene.service.SearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("search")
public class SearchController {
    @Resource
    SearchService searchService;
    @Resource
    IndexService indexService;

    @GetMapping
    public Result search(@RequestParam String question,@RequestParam Integer pageIndex,@RequestParam Integer pageSize) {

        return searchService.search(question,pageIndex,pageSize);
    }

    @PostMapping
    public Result createIndex(){
        return indexService.createIndex();
    }
}
