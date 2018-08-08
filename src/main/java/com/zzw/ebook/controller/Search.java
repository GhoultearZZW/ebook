package com.zzw.ebook.controller;

import com.zzw.ebook.model.Keywords;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

@Controller
public class Search {

    @GetMapping("/getSearch")
    public String getSearch(Model model){
        model.addAttribute("keyWords",new Keywords());
        model.addAttribute("resultList",new ArrayList());
        return "search";
    }


    private static ArrayList<String> search(String indexDir, String q) throws Exception {
        Directory dir = FSDirectory.open(Paths.get(indexDir));// 打开目录
        IndexReader reader = DirectoryReader.open(dir);// 进行读取
        IndexSearcher is = new IndexSearcher(reader);// 索引查询器
        Analyzer analyzer = new StandardAnalyzer(); // 标准分词器
        QueryParser parser = new QueryParser("contents", analyzer);// 在哪查询，第一个参数为查询的Document，在Indexer中创建了
        Query query = parser.parse(q);// 对字段进行解析后返回给查询
        long start = System.currentTimeMillis();
        TopDocs hits = is.search(query, 10);// 开始查询，10代表前10条数据；返回一个文档
        long end = System.currentTimeMillis();
        System.out.println("匹配 " + q + " ，总共花费" + (end - start) + "毫秒" + "查询到"
                + hits.totalHits + "个记录");
        ArrayList<String> list = new ArrayList<>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);// 根据文档的标识获取文档
            System.out.println(doc.get("fullPath"));
            String str = doc.get("fullPath");
            list.add(str.substring(57,str.length()-4));
        }
        reader.close();
        return list;
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String get(@ModelAttribute Keywords keyWords,Model model)throws IOException {
        String indexDir = "indexDir";
        String q = keyWords.getKeyword();
        ArrayList<String> list = new ArrayList<>();
        try{
            list = search(indexDir,q);
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("keyWords",new Keywords());
        model.addAttribute("resultList",list);
        return "search";
    }

}
