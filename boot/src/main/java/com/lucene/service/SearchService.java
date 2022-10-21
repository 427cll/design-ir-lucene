package com.lucene.service;

import com.lucene.common.Result;

public interface SearchService {
    Result search(String question, Integer pageIndex, Integer pageSize);

}
