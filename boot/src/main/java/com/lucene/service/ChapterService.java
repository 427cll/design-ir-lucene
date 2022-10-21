package com.lucene.service;

import com.lucene.common.Result;
import com.lucene.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ChapterService extends IService<Chapter> {

    Result listChapters(Integer limit, Integer offset);
}
