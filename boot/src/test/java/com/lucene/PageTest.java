package com.lucene;

import com.lucene.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.junit.jupiter.api.Test;

import java.io.IOException;
/**
 * 每页显示两条
 * pageIndex pageSize*/
public class PageTest {

    @Test
    public void searchIndex() throws IOException {
        Integer pageIndex = 2;
        Integer pageSize = 2;
        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
        TopDocs topDocs = indexSearcher.search(new TermQuery(new Term("content", "天")), 100);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        for (int i = (pageIndex - 1) * pageSize; i < scoreDocs.length && i < topDocs.totalHits; i++) {
            System.out.println(scoreDocs[i].score);
            int doc = scoreDocs[i].doc;
            Document document = indexSearcher.doc(doc);
            System.out.println(document.get("id"));
            System.out.println(document.get("title"));
            System.out.println(document.get("content"));
            System.out.println("<-------------->");
        }
    }
}
