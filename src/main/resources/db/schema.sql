/* 開発用にデータ削除を追加 : リリース時は消す
DROP TABLE m_user;
DROP TABLE task;
 */

/* ユーザマスタ */
CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(254) PRIMARY KEY,
    encrypted_password VARCHAR(100) NOT NULL,
    user_name VARCHAR(60) NOT NULL,
    user_role VARCHAR(20) NOT NULL,
    user_class CHAR(4),
    user_student_no INT,
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
  user_id VARCHAR(254),
  department CHAR(1),
  company_name_top CHAR(3),
  report_day DATE,
  recruitment_number INT,/*文字型にする*/
  company_name VARCHAR(50),
  application_route VARCHAR(10),
  exam_date_time DATE,
  examination_location VARCHAR(50),
  contens_test VARCHAR(10),
  remarks VARCHAR(600),
  exam_report_status VARCHAR(10)
);
