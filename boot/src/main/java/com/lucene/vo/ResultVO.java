package com.lucene.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResultVO {
    Integer pageTotal;
    List<ItemVO> itemVOList;
}
