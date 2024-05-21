package com.example.backend.service;

import com.example.backend.Entity.Contents;
import com.example.backend.mapper.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.net.*;
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
                return url;
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


    /**
     * 把text转成url
     * @param textContent 文本内容
     * @return
     */
    public String textToUrl(String textContent) throws IOException {
        String apiDevKey = "_PsQkRIdEBbBlp83SVtv7iLB46AAOoeR"; // 你的Pastebin API开发者密钥
        String apiOption = "paste";
        String apiPasteCode = textContent; // 要粘贴的文本内容
        String apiPasteName = "Optional Paste Name";
        String apiPasteExpireDate = "N"; // 永不过期
        String apiPasteFormat = "text"; // 粘贴内容的格式

        // 构建POST请求参数
        String postData = "api_dev_key=" + URLEncoder.encode(apiDevKey, "UTF-8") +
                "&api_option=" + URLEncoder.encode(apiOption, "UTF-8") +
                "&api_paste_code=" + URLEncoder.encode(apiPasteCode, "UTF-8") +
                "&api_paste_name=" + URLEncoder.encode(apiPasteName, "UTF-8") +
                "&api_paste_expire_date=" + URLEncoder.encode(apiPasteExpireDate, "UTF-8") +
                "&api_paste_format=" + URLEncoder.encode(apiPasteFormat, "UTF-8");

        // 发送POST请求
        URL url = new URL("https://pastebin.com/api/api_post.php");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(postData);
        outputStream.flush();
        outputStream.close();

        // 获取Pastebin的响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 输出响应结果，应该是包含URL的字符串
        return response.toString();
    }


}
