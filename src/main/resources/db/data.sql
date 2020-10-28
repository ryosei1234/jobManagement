/* 開発用にデータ削除を追加 : リリース時は消す */
/*DELETE FROM m_user;
*/
/* ユーザマスタのデータ（TEACHER権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '山田太郎', 'ROLE_TEACHER', 'S3A1', '00',false, 'VALID', '2019-05-02 12:48:35', 'yamada@xxx.co.jp', '2019-05-02 12:48:35', 'yamada@xxx.co.jp' );
/* ユーザマスタのデータ（STUDENT権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('suzuki@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '鈴木太郎', 'ROLE_STUDENT','S3A1','00',false,'VALID','2019-05-02 12:48:35','suzuki@xxx.co.jp','2019-05-02 12:48:35','suzuki@xxx.co.jp' );
/* ユーザマスタのデータ（STAFF権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('hashiya@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '橋谷太郎', 'ROLE_STAFF','S3A1','00',false,'VALID','2019-05-02 12:48:35','suzuki@xxx.co.jp','2019-05-02 12:48:35','suzuki@xxx.co.jp' );

/* ユーザマスタのテストデータ（TEACHER権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('t-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入先生', 'ROLE_TEACHER', 'S3A3', '',true, 'VALID', '2019-05-02 12:48:35', 'yamada@xxx.co.jp', '2019-05-02 12:48:35', 'yamada@xxx.co.jp' );
/* ユーザマスタのテストデータ（STUDENT権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('s-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入太郎', 'ROLE_STUDENT','S3A3','01',false,'VALID','2019-05-02 12:48:35','suzuki@xxx.co.jp','2019-05-02 12:48:35','suzuki@xxx.co.jp' );
/* ユーザマスタのテストデータ（STAFF権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_role, user_class, user_student_no,user_darkmode, user_status,created_at ,created_user_id ,update_at,update_user_id)
VALUES('j-ukeire@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '受入事務', 'ROLE_STAFF','','',true,'VALID','2019-05-02 12:48:35','suzuki@xxx.co.jp','2019-05-02 12:48:35','suzuki@xxx.co.jp' );

/* タスクテーブルのデータ*/
INSERT INTO  examreport (examreport_id,user_id,department,company_name_top,report_day,recruitment_number,company_name,application_route,exam_date_time,examination_location,contens_test ,remarks,Exam_report_status)
VALUES('0000000001','yamada@xxx.co.jp','S','エイチ','2020-07-09 12:48:35','0000000001','株式会社HCS','斡旋サイト','2020-07-17','東京','1次試験','あんぱんまん','新規作成');

INSERT INTO  examreport (examreport_id,user_id,department,company_name_top,report_day,recruitment_number,company_name,application_route,exam_date_time,examination_location,contens_test ,remarks,Exam_report_status)
VALUES('0000000002','suzuki@xxx.co.jp','S','エイチ','2020-07-09 12:48:35','0000000002','株式会社HCS','斡旋サイト','2020-07-17','札幌','1次試験','あんぱんまん','新規作成');


/* 就職活動申請・報告のデータ(休日に実施した場合) */
INSERT INTO application_and_report(examination_report_id,examination_status_id,action_id,action_place,action_day,action_end_day,company_name,action_status_id,attendance_id,attendance_day,lodging_day_id,information)
VALUES('0000000010','yamada@xxx.co.jp','2','1','北海道情報専門学校','2020-10-31 10:30','2020-10-31 12:30','株式会社HCS','0','0',null,'0');

/* 就職活動申請・報告のデータ(欠席する場合) */
INSERT INTO application_and_report(examination_report_id,examination_status_id,action_id,action_place,action_day,action_end_day,company_name,action_status_id,attendance_id,attendance_day,lodging_day_id,information)
VALUES('0000000011','suzuki@xxx.co.jp','2','1','北海道情報専門学校','2020-11-01 10:30','2020-11-01 12:30','株式会社HCS','0','1','2020-11-01','1','8:00発9:00着ANA128便を利用する');

/* 就職活動申請・報告のデータ(遅刻する場合) */
INSERT INTO application_and_report(examination_report_id,examination_status_id,action_id,action_place,action_day,action_end_day,company_name,action_status_id,attendance_id,attendance_day,lodging_day_id,information)
VALUES('0000000012','hashiya@xxx.co.jp','2','1','北海道情報専門学校','2020-11-01 10:30','2020-11-01 12:30','株式会社HCS','0','2','2020-11-01 13:00','0');