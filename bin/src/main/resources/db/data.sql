/* 開発用にデータ削除を追加 : リリース時は消す */
/*DELETE FROM m_user;
*/
/* ユーザマスタのデータ（ADMIN権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'TEACHER', 'S3A1', '00',false, 'VALID', '2019-05-02 12:48:35', 'yamada@xxx.co.jp', '2019-05-02 12:48:35', 'yamada@xxx.co.jp' );
/* ユーザマスタのデータ（一般権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('suzuki@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'STUDENT','S3A1','00',false,'VALID','2019-05-02 12:48:35','suzuki@xxx.co.jp','2019-05-02 12:48:35','suzuki@xxx.co.jp' );

/* タスクテーブルのデータ*/
INSERT INTO  examreport (examreport_id,department,company_name_top,report_day,recruitment_number,company_name,exam_application_place,exam_date_time,examination_location ,remarks,Exam_report_status)
VALUES('0000000001','S','HCS','2020-07-09','0000000002','株式会社HCS','札幌','2020-07-17','東京','あああああああああ','新規作成');