/* 開発用にデータ削除を追加 : リリース時は消す */
DELETE FROM m_user;
DELETE FROM task;

/* ユーザマスタのデータ（ADMIN権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, darkmode, role)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', false, 'ROLE_ADMIN');
/* ユーザマスタのデータ（一般権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, darkmode, role)
VALUES('tamura@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村', false, 'ROLE_GENERAL');
INSERT INTO m_user (user_id, encrypted_password, user_name, darkmode, role)
VALUES('suzuki@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '鈴木', true, 'ROLE_GENERAL');

/* タスクテーブルのデータ */
INSERT INTO task (id, user_id, comment, limitday) VALUES (1, 'yamada@xxx.co.jp', 'これやる', '2020-03-23');
INSERT INTO task (id, user_id, comment, limitday) VALUES (2, 'yamada@xxx.co.jp', 'あれやる', '2020-03-24');
INSERT INTO task (id, user_id, comment, limitday) VALUES (3, 'tamura@xxx.co.jp', 'それやる', '2020-03-31');
INSERT INTO task (id, user_id, comment, limitday) VALUES (4, 'yamada@xxx.co.jp', 'どれやる', '2020-03-25');
INSERT INTO task (id, user_id, comment, limitday) VALUES (5, 'tamura@xxx.co.jp', 'もっとやる', '2020-04-20');
