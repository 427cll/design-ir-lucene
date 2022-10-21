package com.lucene;

import com.lucene.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class QueryTest {
    /*
     * 1. 创建资源索引
     * 2. 搜索索引库
     * */
    @Test
    public void createIndex() throws IOException {
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();
        Document document = new Document();
        document.add(new StringField("id", "21", Field.Store.YES));
        document.add(new TextField("title", "朝鲜日报", Field.Store.YES));
        document.add(new TextField("content", "今天美国发生了一件大事", Field.Store.YES));
        indexWriter.addDocument(document);
        LuceneUtils.commit();
    }

    public void searchIndex(Query query) throws IOException {
        IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
        TopDocs topDocs = indexSearcher.search(query, 100);
        System.out.println("记录数" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println(scoreDoc.score);
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            System.out.println(document.get("id"));
            System.out.println(document.get("title"));
            System.out.println(document.get("content"));
        }
    }

    @Test
    public void testQuery() throws IOException, ParseException {
        // 根据Term查询
//        searchIndex(new TermQuery(new Term("content", "今")));

        // 查询所有
//        searchIndex(new MatchAllDocsQuery());

        // 模糊查询，和 SQL 的模糊查询不同。限制字数相同的前提下：有0-2个字可以不同
//        searchIndex(new FuzzyQuery(new Term("title","国x报")));

        // 扩展查询 QueryParser ，会根据指定的分词器对搜索条件进行分词 之后再去索引库查询
//        QueryParser queryParser = new QueryParser(LuceneUtils.version, "content", LuceneUtils.analyzer);
//        Query query = queryParser.parse("今天");
//        searchIndex(query);

        // 多条件查询 ★ most useful ★

        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(LuceneUtils.version, new String[]{"title", "content"}, LuceneUtils.analyzer);
        Query query = multiFieldQueryParser.parse("今");
        searchIndex(query);

        // 布尔查询
//        Query query = new BooleanQuery();
//        searchIndex(query);

    }
}
