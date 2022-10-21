package com.lucene.service.impl;

import com.lucene.common.Result;
import com.lucene.entity.Chapter;
import com.lucene.service.ChapterService;
import com.lucene.service.SearchService;
import com.lucene.utils.LuceneUtils;
import com.lucene.vo.ItemVO;
import com.lucene.vo.ResultVO;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Scorer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    ChapterService chapterService;

    @Override
    public Result search(String question, Integer pageIndex, Integer pageSize) {
        ResultVO resultVO = new ResultVO();
        List<ItemVO> itemVOList = new ArrayList<>();
        try {
            MultiFieldQueryParser queryParser = new MultiFieldQueryParser(LuceneUtils.version, new String[]{"title", "content"}, LuceneUtils.analyzer);
            Query query = queryParser.parse(question);
            //高亮器
            Formatter htmlFormatter = new SimpleHTMLFormatter("<font color='#CC0000'>", "</font>");
            //高亮器进行关键词查询
            Scorer scorer = new QueryTermScorer(query);

            Highlighter highlighter = new Highlighter(htmlFormatter, scorer);

            IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
            TopDocs topDocs = indexSearcher.search(query, 500);
            resultVO.setPageTotal((topDocs.totalHits + pageSize - 1) / pageSize);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = (pageIndex - 1) * pageSize; i < pageIndex * pageSize && i < scoreDocs.length; i++) {
                ItemVO itemVO = new ItemVO();
                int doc = scoreDocs[i].doc;
                Document document = indexSearcher.doc(doc);
                String id = document.get("id");
                String title = document.get("title");
                String content = document.get("content");
                //会自动提取附近100个字符，有时会遇到开头是标点符号的问题
                String highlightTitle = highlighter.getBestFragment(LuceneUtils.analyzer, "title", document.get("title"));
                String highlightSummary = highlighter.getBestFragment(LuceneUtils.analyzer, "content", document.get("content"));
                if (highlightTitle == null) {
                    highlightTitle = title;
                }
                if (highlightSummary == null) {
                    if (content.length() > 100)
                        highlightSummary = content.substring(0, 100);
                    else
                        highlightSummary = content;
                }
                itemVO.setId(id);
                itemVO.setTitle(highlightTitle);
                itemVO.setSummary(highlightSummary);
                itemVOList.add(itemVO);
            }
        } catch (IOException | InvalidTokenOffsetsException | ParseException e) {
            e.printStackTrace();
        }
        resultVO.setItemVOList(itemVOList);
        return Result.success(resultVO);
    }



}
