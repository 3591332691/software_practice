USE software_practice_phase1;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `open_id` int NOT NULL COMMENT '微信传过来的id，唯一',
                        `name` varchar(255) DEFAULT NULL COMMENT '昵称',
                        `image` varchar(255) DEFAULT NULL COMMENT '存url',
                        `news` varchar(255) DEFAULT NULL COMMENT '消息列表，作者更新书后设置一触发器更新',
                        PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
                        `book_id` int NOT NULL AUTO_INCREMENT,
                        `book_name` varchar(255) NOT NULL COMMENT '书名',
                        `brief_introduction` varchar(255) DEFAULT NULL COMMENT '简介',
                        `tag` varchar(255) DEFAULT NULL COMMENT '标签',
                        `image` varchar(255) DEFAULT NULL COMMENT '存url',
                        `author_id` int  DEFAULT NULL COMMENT '作者id',
                        `publish` bool DEFAULT FALSE COMMENT '是否上架',
                        PRIMARY KEY (`book_id`),
                        FOREIGN KEY (`author_id`) REFERENCES `user` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `favored_book`;
CREATE TABLE `favored_book` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '书架的书id，自增',
                                `book_id` int NOT NULL COMMENT '收藏的书的id',
                                `reading_time` int NOT NULL DEFAULT 0 COMMENT '阅读时间，默认为0，单位为分钟',
                                `reading_progress` int NOT NULL DEFAULT 1 COMMENT '阅读进度，默认为第一章',
                                `begin_time` datetime DEFAULT NULL COMMENT '开始阅读时间',
                                `end_time` datetime DEFAULT NULL COMMENT '结束阅读时间',
                                `reader_id`  int NOT NULL COMMENT '收藏的人的id',
                                PRIMARY KEY (`id`),
                                FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`),
                                FOREIGN KEY (`reader_id`) REFERENCES `user` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `contents`;
CREATE TABLE `contents` (
                            `content_id` int NOT NULL AUTO_INCREMENT COMMENT '章节id，自增',
                            `chapter` varchar(2000) NOT NULL COMMENT '章节内容',
                            `title` varchar(255) NOT NULL COMMENT '标题',
                            `book_id` int  COMMENT '属于的书的id',
                            `content_index_inBook` int COMMENT '在书中的第几章节',
                            PRIMARY KEY (`content_id`),
                            FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 测试数据
-- 这是测试里的作者
INSERT INTO  `user` values (1, 'Anna', 'null', null);
-- 这是她创建的书
INSERT INTO  `book` (book_id, book_name, brief_introduction, tag, author_id, publish) values (1, 'test', 'test', 'test', 1,TRUE);
-- 这是读者
INSERT INTO  `user` values (2, 'Mary', 'null', null);
-- 这是她收藏了这本书,并且设定读书的统计时间为0
INSERT INTO `favored_book`  (id,book_id,reading_time,reading_progress,begin_time,end_time,reader_id) values(1,'1',0,1,'2024-06-11 08:00:00','2024-06-11 08:00:00',2)


