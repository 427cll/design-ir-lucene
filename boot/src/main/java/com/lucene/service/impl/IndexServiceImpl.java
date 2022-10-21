package com.lucene.service.impl;

import com.lucene.common.Result;
import com.lucene.entity.Chapter;
import com.lucene.service.ChapterService;
import com.lucene.service.IndexService;
import com.lucene.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    ChapterService chapterService;
    /**
     * 创建索引库
     */
    @Override
    public Result createIndex() {
        try {
            List<Chapter> list = chapterService.list();
            IndexWriter indexWriter = LuceneUtils.getIndexWriter();
            for (Chapter chapter : list) {
                Document document = LuceneUtils.transToDocument(chapter);
                indexWriter.addDocument(document);
            }
            LuceneUtils.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success("索引创建成功");
    }

    @Override
    public Result deleteIndex(Integer id) {
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();
        try {
            indexWriter.deleteDocuments(new Term("id", id + ""));
            LuceneUtils.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    @Override
    public Result saveOrUpdateIndex(Chapter chapter) {
        Document document = LuceneUtils.transToDocument(chapter);
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();
        try {
            indexWriter.updateDocument(new Term("id", chapter.getId().toString()), document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LuceneUtils.commit();
        }
        return null;
    }



}
