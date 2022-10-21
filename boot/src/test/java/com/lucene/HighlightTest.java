package com.lucene;

import com.lucene.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * 高亮的原理：
 * 今天发生一件<font color="red">大事</font>
 */
public class HighlightTest {
    @Test
    public void test() throws IOException, InvalidTokenOffsetsException {

    }

}
