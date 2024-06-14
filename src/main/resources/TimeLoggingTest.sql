
INSERT INTO  `user` values (1, 'Anna', 'null', null);
-- 这是她创建的书
INSERT INTO  `book` (book_id, book_name, brief_introduction, tag, author_id, publish) values (1, 'test', 'test', 'test', 1,false);
-- 这是读者
INSERT INTO  `user` values (2, 'Mary', 'null', null);
-- 这是她收藏了这本书,并且设定读书的统计时间为0
INSERT INTO `favored_book`  (id,book_id,reading_time,reading_progress,begin_time,end_time,reader_id) values(1,'1',0,1,'2024-06-11 08:00:00','2024-06-11 08:00:00',2)

