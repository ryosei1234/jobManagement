package jp.ac.hcs.white.examreport;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1件分のユーザ情報.
 * 各項目のデータ仕様も合わせて管理する.
 */
@Data
@NoArgsConstructor
public class ExamReportData {

	/*
	 * 受験番号ID
	 * 必須入力
	 */
	private String examreport_id ;
	/*
	 * ユーザID
	 */
	private String user_id;
	/*
	 * 学科コード
	 */
	private String department ;
	/*
	 * 会社名カナ3文字
	 */
	private String company_name_top;
	/*
	 * 報告日
	 */
	private Date report_day;
	/*
	 * 求人番号
	 */
	private int recruitment_number;
	/*
	 * 会社名
	 */
	private String company_name;
	/*
	 * 受験申込場所
	 */
	private String application_route ;
	/*
	 * 受験日時
	 */
	private String exam_date_time;
	/*
	 * 受験場所
	 */
	private String examination_location;
	/*
	 * テスト内容
	 */
	private String contens_test;
	/*
	 * 備考
	 */
	private String remarks;
	/*
	 * 受験報告状態
	 */
	private String exam_report_status;
	/*
	 * ユーザのクラス
	 */
	private String user_class;
	/*
	 * ユーザの出席番号
	 */
	private String user_student_no;
	/*
	 * ユーザ名
	 */
	private String user_name;
}
