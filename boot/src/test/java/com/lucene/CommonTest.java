package com.lucene;


import com.lucene.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CommonTest  {

    /**
     * 对一篇文章建立索引
     */
    @Test
    public void createIndex() throws IOException {
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();
        //创建 Document 文档
        //注意，标准分词器对于 8 种基本类型+String 的 Field 不做分词处理；对 TextField 做《单字分词处理》
        Document document1 = new Document();
        //Store.YES 在元数据区存储数据
        //Store.NO 不在元数据区存储数据
        //如果想要某个域参与搜索，但是不会将搜索结果展示到页面，元数据区就没有必要存储
        document1.add(new TextField("content","我我我wowoowowo我我我我我我我我我我我哦我", Field.Store.NO));
        indexWriter.addDocument(document1);
        indexWriter.commit();
    }

    /**
     * 根据索引定位文章
     */
    @Test
    public void searchIndex() throws IOException {
        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
        TopDocs topDocs = indexSearcher.search(new TermQuery(new Term("content", "我")), 100);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (int i = 0; i < scoreDocs.length; i++) {
            int doc = scoreDocs[i].doc;//文章在索引库的ID
            Document document = indexSearcher.doc(doc);
            System.out.print(document);
            System.out.println(scoreDocs[i].score);
        }
    }

}
