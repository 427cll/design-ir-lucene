package com.lucene.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lucene.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ChapterMapper extends BaseMapper<Chapter> {
    @Select("SELECT * FROM chapter LIMIT #{limit} OFFSET #{offset}")
    public List<Chapter> selectPage(Integer limit, Integer offset);
}
