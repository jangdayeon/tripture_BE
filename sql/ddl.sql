use tripture;

CREATE TABLE `bookmark` (
  `bookmark_id` int unsigned NOT NULL AUTO_INCREMENT,
  `bookmark_time` datetime(6) DEFAULT NULL,
  `post_id` bigint DEFAULT NULL,
  `profile_id` int unsigned DEFAULT NULL,
  `bookmark_type` varchar(31) NOT NULL,
  `content_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bookmark_id`),
  KEY `FKbrwfrudp6lu69r0ah11u0taqn` (`profile_id`),
  CONSTRAINT `FKbrwfrudp6lu69r0ah11u0taqn` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`)
);

CREATE TABLE `challenge` (
  `challenge_date` date NOT NULL,
  `challenge_point` int NOT NULL,
  `challenge_id` int unsigned NOT NULL AUTO_INCREMENT,
  `challenge_content` varchar(255) DEFAULT NULL,
  `challenge_img_name` varchar(255) DEFAULT NULL,
  `challenge_name` varchar(255) NOT NULL,
  `content_id` varchar(255) NOT NULL,
  `challenge_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`challenge_id`)
);

CREATE TABLE `comment` (
  `nested` tinyint(1) NOT NULL,
  `comment_date` datetime(6) NOT NULL,
  `comment_group_id` bigint DEFAULT NULL,
  `comment_id` int unsigned NOT NULL AUTO_INCREMENT,
  `post_id` int unsigned DEFAULT NULL,
  `profile_id` int unsigned DEFAULT NULL,
  `comment_content` longtext,
  PRIMARY KEY (`comment_id`),
  KEY `FKs1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
  CONSTRAINT `FKs1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
);

CREATE TABLE `item` (
  `item_price` int NOT NULL,
  `item_stock` int DEFAULT NULL,
  `item_date` datetime(6) NOT NULL,
  `item_id` int unsigned NOT NULL AUTO_INCREMENT,
  `item_view_count` int DEFAULT NULL,
  `item_description` longtext,
  `item_img_name` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) NOT NULL,
  `item_position` varchar(255) DEFAULT NULL,
  `item_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
);

CREATE TABLE `login` (
  `login_id` int unsigned NOT NULL AUTO_INCREMENT,
  `profile_id` int unsigned DEFAULT NULL,
  `session_limit` datetime(6) DEFAULT NULL,
  `login_pw` varchar(30) DEFAULT NULL,
  `login_email` varchar(320) NOT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `login_type` varchar(10) NOT NULL,
  PRIMARY KEY (`login_id`),
  KEY `FKe0ght7n5cjx8tnheha9mpn8l3` (`profile_id`),
  CONSTRAINT `FKe0ght7n5cjx8tnheha9mpn8l3` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`)
);

CREATE TABLE `point` (
  `point_date` date NOT NULL,
  `point_id` int unsigned NOT NULL AUTO_INCREMENT,
  `profile_id` int unsigned DEFAULT NULL,
  `point_change` varchar(255) NOT NULL,
  `point_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`point_id`),
  KEY `FKryeufdt9yojec75g7i5gkilh4` (`profile_id`),
  CONSTRAINT `FKryeufdt9yojec75g7i5gkilh4` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`)
);

CREATE TABLE `post` (
  `post_date` date NOT NULL,
  `post_like_count` int NOT NULL,
  `post_id` int unsigned NOT NULL AUTO_INCREMENT,
  `post_view_count` bigint NOT NULL,
  `profile_id` int unsigned DEFAULT NULL,
  `content_id` varchar(255) DEFAULT NULL,
  `post_challenge_name` varchar(255) DEFAULT NULL,
  `post_content` longtext,
  `post_img_name` varchar(255) DEFAULT NULL,
  `post_challenge_region` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FKk5e5q6qsbobb7sst3h99kjr50` (`profile_id`),
  CONSTRAINT `FKk5e5q6qsbobb7sst3h99kjr50` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`)
);

CREATE TABLE `post_cnt` (
  `chung` int DEFAULT NULL,
  `gang` int DEFAULT NULL,
  `gyeong` int DEFAULT NULL,
  `inc` int DEFAULT NULL,
  `je` int DEFAULT NULL,
  `jeon` int DEFAULT NULL,
  `seo` int DEFAULT NULL,
  `post_cnt_id` int unsigned NOT NULL AUTO_INCREMENT,
  `profile_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`post_cnt_id`),
  UNIQUE KEY `UK7hta1eqac2p5afm6bygnmpu3n` (`profile_id`),
  CONSTRAINT `FKf1ohfxqt39ffsput6ubl4525y` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`)
);

CREATE TABLE `post_like` (
  `post_id` int unsigned DEFAULT NULL,
  `post_like_id` int unsigned NOT NULL AUTO_INCREMENT,
  `profile_id` int unsigned NOT NULL,
  PRIMARY KEY (`post_like_id`),
  KEY `FKj7iy0k7n3d0vkh8o7ibjna884` (`post_id`),
  CONSTRAINT `FKj7iy0k7n3d0vkh8o7ibjna884` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
);

CREATE TABLE `profile` (
  `profile_total_point` int DEFAULT NULL,
  `profile_id` int unsigned NOT NULL AUTO_INCREMENT,
  `profile_img_name` varchar(255) DEFAULT NULL,
  `profile_nickname` varchar(255) DEFAULT NULL,
  `profile_auth` varchar(10) DEFAULT NULL,
  `profile_level` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`profile_id`)
);

CREATE TABLE `purchase` (
  `purchase_check` tinyint(1) NOT NULL,
  `purchase_count` int DEFAULT NULL,
  `purchase_price` int DEFAULT NULL,
  `item_id` int unsigned DEFAULT NULL,
  `profile_id` int unsigned DEFAULT NULL,
  `purchase_id` int unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(255) NOT NULL,
  PRIMARY KEY (`purchase_id`),
  KEY `FKemduimgakuywg7g70dd54sshw` (`item_id`),
  KEY `FK3hj2yuries0gsqlv5wm8bwf94` (`profile_id`),
  CONSTRAINT `FK3hj2yuries0gsqlv5wm8bwf94` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`profile_id`),
  CONSTRAINT `FKemduimgakuywg7g70dd54sshw` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
);

CREATE TABLE `report` (
  `report_block_chk` tinyint(1) NOT NULL,
  `post_or_comment_id` int unsigned DEFAULT NULL,
  `report_block_id` int unsigned NOT NULL,
  `report_id` int unsigned NOT NULL AUTO_INCREMENT,
  `reporter_id` int unsigned NOT NULL,
  `report_category` varchar(50) NOT NULL,
  `report_content` longtext,
  `report_type` varchar(10) NOT NULL,
  PRIMARY KEY (`report_id`)
);

use tripture;

INSERT INTO profile (profile_total_point, profile_img_name, profile_nickname, profile_auth, profile_level)
VALUES
    (1000, 'file/be_profile.jpg', 'user1', 'USER', 'LEVEL1'),
    (2000, 'file/be_profile.jpg', 'user2', 'USER', 'LEVEL2'),
    (3000, 'file/be_profile.jpg', 'user3', 'USER', 'LEVEL3');

INSERT INTO challenge (challenge_date, challenge_point, challenge_content, challenge_img_name, challenge_name, content_id, challenge_type)
VALUES
    ('2024-08-09', 50, 'Challenge Content 1', 'file/be_challenge.jpg', 'Challenge 1', 'content1', 'tourist'),
    ('2024-08-10', 100, 'Challenge Content 2', 'file/be_challenge.jpg', 'Challenge 2', 'content2', 'restaurant'),
    ('2024-08-15', 150, 'Challenge Content 3', 'file/be_challenge.jpg', 'Challenge 3', 'content3', 'tourist');

INSERT INTO post (post_date, post_like_count, post_view_count, profile_id, content_id, post_challenge_name, post_content, post_img_name, post_challenge_region)
VALUES
    ('2024-08-09', 10, 10, 1, '2757454', '애니멀원더스테이지에버랜드', '에버랜드 푸바오 정말 귀여웠어요..!!! 사람들 다 오픈런해서 빨리 가셔야 함,,', 'http://tong.visitkorea.or.kr/cms/resource/09/2757509_image2_1.jpg', 'inc'),
    ('2024-08-10', 20, 20, 2, '1030763', '뚝섬한강공원', '노을 지는 풍경이 너무 예뻤어요!', 'file/be_challenge.jpg', 'seo');

INSERT INTO comment (nested, comment_date, comment_group_id, post_id, profile_id, comment_content)
VALUES
    (0, '2024-08-09 12:00:00.000000', 0, 1, 1, 'This is a comment 1 on Post 1'),
    (0, '2024-08-01 12:00:00.000000', 0, 1, 2, 'This is a comment 2 on Post 1'),
    (1, '2024-08-02 13:00:00.000000', 1, 1, 2, 'This is a nested comment 1 on Post 1'),
    (1, '2024-08-03 13:00:00.000000', 1, 1, 2, 'This is a nested comment 2 on Post 1');

INSERT INTO bookmark (bookmark_time, post_id, profile_id, bookmark_type, content_id)
VALUES
    ('2024-08-09 14:00:00.000000', 1, 1, 'PhotoChallenge', 'content1'),
    ('2024-08-10 15:00:00.000000', 2, 2, 'PhotoChallenge', 'content2');

INSERT INTO login (profile_id, session_limit, login_pw, login_email, session_id, login_type)
VALUES
    (1, '2024-08-09 23:59:59.000000', 'password1', 'user1@example.com', NULL, 'SELF'),
    (2, '2024-08-10 23:59:59.000000', 'password2', 'user2@example.com', NULL, 'SELF');

INSERT INTO point (point_date, profile_id, point_change, point_title)
VALUES
    ('2024-08-09', 1, '+100', 'Earned points'),
    ('2024-08-10', 2, '-50', 'Used points');

INSERT INTO post_cnt (chung, gang, gyeong, inc, je, jeon, seo, profile_id)
VALUES
    (0, 0, 0, 1, 0, 0, 0, 1),
    (0, 0, 0, 1, 0, 0, 0, 2);

INSERT INTO post_like (post_id, profile_id)
VALUES
    (1, 1),
    (2, 1),
    (1, 2),
    (2, 2);

INSERT INTO item (item_price, item_stock, item_date, item_view_count, item_description, item_img_name, item_name, item_position, item_type)
VALUES
    (1000, 10, '2024-08-09 12:00:00.000000', 100, 'Description of item 1', 'file/be_item.png', 'Item 1', 'A1', 'TICKET'),
    (2000, 20, '2024-08-10 12:00:00.000000', 200, 'Description of item 2', 'file/be_item.png', 'Item 2', 'B1', 'COUPON');

INSERT INTO purchase (purchase_check, purchase_count, purchase_price, item_id, profile_id, tid)
VALUES
    (1, 1, 1000, 1, 1, 'tid1'),
    (0, 2, 4000, 2, 2, 'tid2');

INSERT INTO report (report_block_chk, report_block_id, reporter_id, report_category, report_content, report_type, post_or_comment_id)
VALUES
    (1, 1, 2, '스팸 또는 광고', 'User is sending unsolicited messages.', 'profile', 2),
    (0, 2, 1, '스팸 또는 광고', 'User is making offensive remarks.', 'post', 1);
