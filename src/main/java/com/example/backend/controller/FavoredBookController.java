package com.example.backend.controller;

import com.example.backend.Entity.Favored_book;
import com.example.backend.service.FavoredBookService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class FavoredBookController {
    @Autowired
    private FavoredBookService favoredBookService;

    /**
     *得到读者id的人的收藏的书
     * @param reader_id 读者的open_id
     * @return Favored_book的List
     * @throws Exception
     */
    @GetMapping("/GetFavoredBooks/Reader")
    public String GetFavoredBooksByReader(@RequestParam int reader_id) throws Exception{
        List<Favored_book> favored_books = favoredBookService.findFavoredBooksByReader(reader_id);
        if(favored_books.isEmpty()){ return "no favored books"; }
        Gson gson = new Gson();
        return gson.toJson(favored_books);
    }

    // 查看这本书被多少读者加入书架
    @GetMapping("/GetFavoredBook/Book")
    public String GetFavoredBooksByBook(@RequestParam int book_id) throws Exception{
        List<Favored_book> favored_books = favoredBookService.findFavoredBooksByBook(book_id);
        if(!favored_books.isEmpty()){ return "no favored books";}
        Gson gson = new Gson();
        return gson.toJson(favored_books);
    }


    @PostMapping("/AddFavoredBook")
    public String AddFavoredBook(@RequestBody Map<String, Object> FavoredData) throws Exception{
        int reader_id = (Integer) FavoredData.get("reader_id");
        int book_id = (Integer) FavoredData.get("book_id");
        // Date begin_time = (Date) FavoredData.get("begin_time");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        Date begin_time = sdf.parse(String.valueOf(FavoredData.get("begin_time")));

        Favored_book favored_book = new Favored_book();
        favored_book.setReader_id(reader_id);
        favored_book.setBook_id(book_id);
        favored_book.setReading_progress(1);
        // 初始加入书架时，开始阅读时间和结束阅读时间均设为加入书架的时间
        favored_book.setBegin_time(begin_time);
        favored_book.setEnd_time(begin_time);

        boolean success = favoredBookService.insertFavoredBook(favored_book);
        if(success) return "add succeed";
        else return "add false";
    }

    @GetMapping("/DeleteFavoredBook")
    public String DeleteFavoredBook(@RequestParam int favored_id) throws Exception{
        if(favoredBookService.deleteFavoredBook(favored_id)){
            return "delete succeed";
        } else {
            return "delete false";
        }
    }

    @PostMapping("/UpdateFavoredBook/begin")
    public String UpdateFavoredBookBegin(@RequestBody Map<String, Object> FavoredData) throws Exception{
        int shelf_id = (Integer) FavoredData.get("shelf_id");
        // Date begin_time = (Date) FavoredData.get("begin_time");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        Date begin_time = sdf.parse(String.valueOf(FavoredData.get("begin_time")));

        Favored_book favored_book = favoredBookService.findById(shelf_id);
        // 判断书架是否存在，并且上次退出时间应该在这次进入之前
        if(favored_book == null || favored_book.getEnd_time().after(begin_time)){
            return "update false";
        }
        favored_book.setBegin_time(begin_time);
        favored_book.setEnd_time(begin_time);

        favoredBookService.updateFavored_book(favored_book);
        return "update succeed";
    }

    @PostMapping("/UpdateFavoredBook/end")
    public String UpdateFavoredBookEnd(@RequestBody Map<String, Object> FavoredData) throws Exception{
        int shelf_id = (Integer) FavoredData.get("shelf_id");
        // Date end_time = (Date) FavoredData.get("end_time");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        Date end_time = sdf.parse(String.valueOf(FavoredData.get("end_time")));

        int reading_progress = (Integer) FavoredData.get("reading_progress");

        Favored_book favored_book = favoredBookService.findById(shelf_id);
        // 判断书架是否存在，并且进入时间应该在结束之前
        if(favored_book == null || favored_book.getBegin_time().after(end_time)){
            return "update false";
        }
        Date begin_time = favored_book.getBegin_time();
        int reading_time_last = favored_book.getReading_time();
        int reading_time_current = (int) (Duration.between(begin_time.toInstant(), end_time.toInstant()).toMinutes() + reading_time_last);

        favored_book.setEnd_time(end_time);
        favored_book.setReading_time(reading_time_current);
        favored_book.setReading_progress(reading_progress);

        favoredBookService.updateFavored_book(favored_book);
        return "update succeed";
    }

    @GetMapping("/GetFavoredBook/shelf")
    public String GetFavoredBook(@RequestParam int shelf_id) throws Exception{
        Favored_book favoredBook = favoredBookService.findById(shelf_id);
        Gson gson = new Gson();
        return gson.toJson(favoredBook);
    }

}
