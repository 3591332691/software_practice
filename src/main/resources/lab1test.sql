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
                        `book_id` int NOT NULL,
                        `book_name` varchar(255) NOT NULL COMMENT '书名',
                        `brief_introduction` varchar(255) DEFAULT NULL COMMENT '简介',
                        `tag` varchar(255) DEFAULT NULL COMMENT '标签',
                        `author_id` int  DEFAULT NULL COMMENT '作者id',
                        PRIMARY KEY (`book_id`),
                        FOREIGN KEY (`author_id`) REFERENCES `user` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `favored_book`;
CREATE TABLE `favored_book` (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '书架的书id，自增',
                                `favored_book_id` int NOT NULL COMMENT '收藏的书的id',
                                `reading_time` int NOT NULL DEFAULT '0' COMMENT '阅读时间，默认为0',
                                `reading_progress` int NOT NULL DEFAULT '1' COMMENT '阅读进度，默认为第一章',
                                `begin_time` datetime DEFAULT NULL COMMENT '开始阅读时间',
                                `end_time` datetime DEFAULT NULL COMMENT '结束阅读时间',
                                `reader_id`  int NOT NULL COMMENT '收藏的人的id',
                                PRIMARY KEY (`id`),
                                FOREIGN KEY (`favored_book_id`) REFERENCES `book` (`book_id`),
                                FOREIGN KEY (`reader_id`) REFERENCES `user` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `contents`;
CREATE TABLE `contents` (
                            `content_id` int NOT NULL AUTO_INCREMENT COMMENT '章节id，自增',
                            `chapter` varchar(255) NOT NULL COMMENT 'url',
                            PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
