/* 開発用にデータ削除を追加 : リリース時は消す
DROP TABLE m_user;
DROP TABLE examreport;
 */

/* ユーザマスタ */
CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(254) PRIMARY KEY,
    encrypted_password VARCHAR(100) NOT NULL,
    user_name VARCHAR(60) NOT NULL,
    user_role VARCHAR(20) NOT NULL,
    user_class CHAR(4),
    user_student_no VARCHAR(2),
    user_darkmode BOOLEAN NOT NULL,
    user_status VARCHAR(7) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    created_user_id VARCHAR(254) NOT NULL ,
    update_at TIMESTAMP,
    update_user_id VARCHAR(254),
    CONSTRAINT user_id FOREIGN KEY (created_user_id) REFERENCES m_user(user_id)
);



/*受験報告テーブル*/
CREATE TABLE IF NOT EXISTS examreport (
  examreport_id VARCHAR(10) PRIMARY KEY,
  user_id VARCHAR(254) NOT NULL,
  department CHAR(1),
  company_name_top CHAR(3),
  report_day TIMESTAMP,
  recruitment_number INT,
  company_name VARCHAR(50),
  application_route VARCHAR(10),
  exam_date_time DATETIME,
  examination_location VARCHAR(50),
  contens_test VARCHAR(10),
  remarks VARCHAR(600),
  exam_report_status VARCHAR(10)
);



/*就職活動申請・報告テーブル*/
CREATE TABLE IF NOT EXISTS application_and_report (
  examination_report_id VARCHAR(10) PRIMARY KEY, /*就職活動申請・報告ID*/
  examination_status_id CHAR(100) NOT NULL, /* 就職活動申請・報告状態ID */
  action_id CHAR(10) NOT NULL, /* 活動内容ID */
  action_place VARCHAR(50) NOT NULL, /* 活動場所 */
  action_day DATETIME NOT NULL, /* 活動開始日時 */
  action_end_day DATETIME, /* 活動終了日時 */
  company_name VARCHAR(50) NOT NULL, /* 企業名 */
  action_status_id CHAR(1), /* 就職活動続行ID */
  attendance_id VARCHAR(10), /* 出欠ID */
  attendance_day DATETIME, /* 出欠日時 */
  lodging_day_id VARCHAR(10), /* 宿泊ID */
  information VARCHAR(300) /* 連絡事項 */
);
