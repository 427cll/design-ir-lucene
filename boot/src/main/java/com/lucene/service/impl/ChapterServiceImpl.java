package com.lucene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lucene.common.Result;
import com.lucene.entity.Chapter;
import com.lucene.mapper.ChapterMapper;
import com.lucene.service.ChapterService;
import com.lucene.vo.ChaptersVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Resource
    ChapterMapper chapterMapper;

    @Override
    public Result listChapters(Integer limit, Integer offset) {
        ChaptersVO vo = new ChaptersVO();
        List<Chapter> allChapters = chapterMapper.selectList(null);
        List<Chapter> chapterList = chapterMapper.selectPage(limit, offset);
        vo.setChapterList(chapterList);
        vo.setPageTotal((allChapters.size() + limit - 1) / limit);
        return Result.success(vo);
    }

}
