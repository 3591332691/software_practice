USE software_practice_phase1;
insert into `user` (`open_id`, `name`, `image`,`news`) values (1,'user001',null,null);
insert into `book` (`book_id`,`book_name`,`brief_introduction`,`tag`,`author_id`)values
                    (1,'bookname001','introduction','tag001',1);
insert into `favored_book`(`book_id`,`reading_time`,`reading_progress`,`reader_id`)values (1,0,1,1);
insert into `contents` (`chapter`,`title`,`book_id`,`content_index_inBook`) values
                        ('https://pastebin.com/0nFf0AfH','chapter1title',1,1);
