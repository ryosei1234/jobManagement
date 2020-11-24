/* 開発用にデータ削除を追加 : リリース時は消す */
/*DELETE FROM m_user;
*/

/* ユーザマスタのテストデータ（TEACHER権限） PASS:pasword */
INSERT INTO "PUBLIC"."M_USER" VALUES
('t-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', STRINGDECODE('\u53d7\u5165\u5148\u751f'), 'ROLE_TEACHER', 'S3A3', '', TRUE, 1, TIMESTAMP '2019-05-02 12:48:35', 't-ukeire@hcs.ac.jp', TIMESTAMP '2020-11-24 13:44:39.876', 't-ukeire@hcs.ac.jp', 0),
('s-ukeire@hcs.ac.jp', '$2a$10$ZCHmqKLzAXq39kCc6BoBS.5M59sRkHNFWT4aSk.MM2QsRfWQFtqnK', STRINGDECODE('\u53d7\u5165\u5b66\u751f'), 'ROLE_STUDENT', 'S3A1', '01', FALSE, 1, TIMESTAMP '2020-11-24 13:44:53.327', 't-ukeire@hcs.ac.jp', TIMESTAMP '2020-11-24 13:44:53.327', 't-ukeire@hcs.ac.jp', 0);
