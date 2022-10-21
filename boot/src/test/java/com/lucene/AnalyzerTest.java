package com.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class AnalyzerTest {
    @Test
    public void test() throws IOException {
        Analyzer analyzer;
//        标准分词器
//        analyzer = new StandardAnalyzer(Version.LUCENE_44);
//        System.out.println("停用词集合 --> " + StandardAnalyzer.STOP_WORDS_SET);
//        中日韩分词器
//        analyzer = new CJKAnalyzer(Version.LUCENE_44);
//        System.out.println("停用词集合 --> " + CJKAnalyzer.getDefaultStopSet());
        analyzer = new IKAnalyzer();
        test(analyzer, "今天");
    }

    public static void test(Analyzer analyzer, String text) throws IOException {
        System.out.println("当前分词器 --> " + analyzer.getClass().getName());
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
        tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(attribute.toString());
        }
        tokenStream.end();
    }
}
