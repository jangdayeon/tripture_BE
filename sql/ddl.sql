use tripture;

INSERT INTO profile (profile_total_point, profile_img_name, profile_nickname, profile_auth, profile_level)
VALUES
    (1000, 'file/be_profile.jpg', 'user1', 'USER', 'LEVEL1'),
    (2000, 'file/be_profile.jpg', 'user2', 'USER', 'LEVEL2'),
    (3000, 'file/be_profile.jpg', 'user3', 'USER', 'LEVEL3');

INSERT INTO challenge (challenge_date, challenge_latitude, challenge_longitude, challenge_point, challenge_content, challenge_img_name, challenge_name, content_id, challenge_region)
VALUES
    ('2024-08-09', 37.5665, 126.9780, 50, 'Challenge Content 1', 'file/be_challenge.jpg', 'Challenge 1', 'content1', 'inc'),
    ('2024-08-10', 35.1796, 129.0756, 100, 'Challenge Content 2', 'file/be_challenge.jpg', 'Challenge 2', 'content2', 'inc');

INSERT INTO post (post_date, post_like_count, challenge_id, post_view_count, profile_id, content_id, post_content, post_img_name)
VALUES
    ('2024-08-09', 10, 1, 100, 1, 'content1', 'Post Content 1', 'file/be_challenge.jpg'),
    ('2024-08-10', 20, 2, 200, 2, 'content2', 'Post Content 2', 'file/be_challenge.jpg');

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

INSERT INTO report (post_id, profile_id, report_content)
VALUES
    (1, 2, 'This is a report for Post 1'),
    (2, 1, 'This is a report for Post 2');
