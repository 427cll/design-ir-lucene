package com.lucene.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Chapter {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    Integer id;
    String title;
    String content;
}
