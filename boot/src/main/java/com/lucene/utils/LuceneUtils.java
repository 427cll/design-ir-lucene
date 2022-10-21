package com.lucene.utils;

import com.lucene.entity.Chapter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class LuceneUtils {
    private static FSDirectory fsDirectory;
    private static IndexWriterConfig indexWriterConfig;
    private static DirectoryReader directoryReader;
    public static Version version;
    public static Analyzer analyzer;
    private static final ThreadLocal<IndexWriter> t = new ThreadLocal<>();

    static {
        try {
            version = Version.LUCENE_44;
            fsDirectory = FSDirectory.open(new File("./index"));
            analyzer = new IKAnalyzer();
            indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IndexWriter getIndexWriter() {
        /*
          核心对象
          参数一：索引存放的目录
          参数二：索引的配置信息
         */
        IndexWriter indexWriter = t.get();
        if (indexWriter == null) {
            try {
                //跟Connection一样，每次要重新创建
                indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);
                t.set(indexWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return indexWriter;
    }

    public static IndexSearcher getIndexSearcher() {
        //对于搜索事务，不需要关心线程安全
        try {
            directoryReader = DirectoryReader.open(fsDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new IndexSearcher(directoryReader);
    }

    public static void commit() {
        try {
            IndexWriter indexWriter = getIndexWriter();
            indexWriter.commit();
            indexWriter.close();
            t.remove();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rollBack() {
        try {
            IndexWriter indexWriter = getIndexWriter();
            indexWriter.rollback();
            indexWriter.close();
            t.remove();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Document transToDocument(Chapter chapter) {
        Document document = new Document();
        document.add(new StringField("id", chapter.getId().toString(), Field.Store.YES));
        document.add(new TextField("title", chapter.getTitle(), Field.Store.YES));
        document.add(new TextField("content", chapter.getContent(), Field.Store.YES));
        return document;
    }

}
