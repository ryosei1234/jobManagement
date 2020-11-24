/* 開発用にデータ削除を追加 : リリース時は消す */
/*DELETE FROM m_user;
*/

/* ユーザマスタのテストデータ（TEACHER権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode,created_at ,created_user_id ,update_at,update_user_id)
VALUES('t-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入先生', 'ROLE_TEACHER', 'S3A3', '',true,  '2019-05-02 12:48:35', 't-ukeire@hcs.ac.jp', '2019-05-02 12:48:35', 't-ukeire@hcs.ac.jp' );
/* ユーザマスタのテストデータ（TEACHER権限） PASS:pasword */
VALUES('s-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入先生', 'ROLE_TEACHER', 'S3A3', '',true,  '2019-05-02 12:48:35', 't-ukeire@hcs.ac.jp', '2019-05-02 12:48:35', 't-ukeire@hcs.ac.jp' );
