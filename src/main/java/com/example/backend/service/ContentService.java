package com.example.backend.service;

import com.example.backend.Entity.Contents;
import com.example.backend.mapper.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

@Service
public class ContentService {

    @Autowired
    ContentMapper contentMapper ;
    /**
     * 知道book_id和章节索引，得到相应的String
     * @param book_id 书本id
     * @param chapter_id 其实是指第几章，书本的章节索引
     * @return 如果返回为空的话，就是没找到；如果不为空的话，就是找到了
     */
    public String GetChapterContent(int book_id, int chapter_id){
        String output = "";
        List<Contents>  all_chapters;
        all_chapters = contentMapper.getContentByBook_id(book_id);
        for (Contents contents : all_chapters) {
            if (contents.content_index_inBook==chapter_id){
                String url = contents.chapter;
                output = getTextFromUrl(url);
                break;
            }
        }
        return output;
    }

    public static String getTextFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                System.out.println("HTTP请求失败，状态码：" + responseCode);
                return null;
            }
        } catch (IOException e) {
            System.out.println("发生IO异常：" + e.getMessage());
            return null;
        }
    }
}
