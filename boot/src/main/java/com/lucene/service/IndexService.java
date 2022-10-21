package com.lucene.service;

import com.lucene.common.Result;
import com.lucene.entity.Chapter;

public interface IndexService {
    Result createIndex();
    Result saveOrUpdateIndex(Chapter chapter);
    Result deleteIndex(Integer id);
}
