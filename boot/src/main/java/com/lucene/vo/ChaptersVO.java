package com.lucene.vo;

import com.lucene.entity.Chapter;
import lombok.Data;

import java.util.List;

@Data
public class ChaptersVO {
    Integer pageTotal;
    List<Chapter> chapterList;
}
