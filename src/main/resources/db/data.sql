/* 開発用にデータ削除を追加 : リリース時は消す */
/*DELETE FROM m_user;
*/
/* ユーザマスタのデータ（ADMIN権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'TEACHER', 'S3A1', '00',false, 'VALID', '2019-05-02 12:48:35', 'yamada@xxx.co.jp', '2019-05-02 12:48:35', 'yamada@xxx.co.jp' );
/* ユーザマスタのデータ（一般権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('suzuki@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'STUDENT','S3A1','00',false,'VALID','2019-05-02 12:48:35','suzuki@xxx.co.jp','2019-05-02 12:48:35','suzuki@xxx.co.jp' );

/* タスクテーブルのデータ
INSERT INTO task (id, user_id, comment, limitday) VALUES (1, 'yamada@xxx.co.jp', 'これやる', '2020-03-23');
INSERT INTO task (id, user_id, comment, limitday) VALUES (2, 'yamada@xxx.co.jp', 'あれやる', '2020-03-24');
INSERT INTO task (id, user_id, comment, limitday) VALUES (3, 'tamura@xxx.co.jp', 'それやる', '2020-03-31');
INSERT INTO task (id, user_id, comment, limitday) VALUES (4, 'yamada@xxx.co.jp', 'どれやる', '2020-03-25');
INSERT INTO task (id, user_id, comment, limitday) VALUES (5, 'tamura@xxx.co.jp', 'もっとやる', '2020-04-20');
*/