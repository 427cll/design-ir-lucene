package com.lucene.controller;

import com.lucene.common.Result;
import com.lucene.entity.Chapter;
import com.lucene.service.ChapterService;
import com.lucene.service.IndexService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Resource
    ChapterService chapterService;
    @Resource
    IndexService indexService;
    @GetMapping
    public Result listChapters(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        Integer limit = pageSize;
        Integer offset = (pageIndex - 1) * pageSize;
        return chapterService.listChapters(limit, offset);
    }

    @GetMapping("{id}")
    public Result getChapter(@PathVariable Integer id) {
        return Result.success(chapterService.getById(id));
    }

    @PostMapping
    public Result saveOrUpdateChapter(@RequestBody Chapter chapter) {
        chapterService.saveOrUpdate(chapter);
        //增加或修改索引
        indexService.saveOrUpdateIndex(chapter);
        return Result.success();
    }

    @DeleteMapping("{id}")
    public Result deleteChapter(@PathVariable Integer id) {
        //删除索引
        indexService.deleteIndex(id);
        chapterService.removeById(id);
        return Result.success();
    }
}