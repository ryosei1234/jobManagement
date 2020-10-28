package jobhuntingreport;

import java.sql.Timestamp;

/**
 * 1件分のユーザ情報.
 * 各項目のデータ仕様も合わせて管理する.
 */

public class JobHuntingData {
	/*
	 * 就職活動申請・報告ID
	 */
	private String examination_report_id ;
	/*
	 * ユーザID
	 */
	private String user_id;
	/*
	 * 就職活動申請・報告状態ID
	 */
	private String examination_status_id ;
	/*
	 * 活動内容ID
	 */
	private String action_id;
	/*
	 * 活動場所日
	 */
	private Timestamp action_place;
	/*
	 * 活動開始日時
	 */
	private int action_day;
	/*
	 * 活動終了日時
	 */
	private String action_end_day;
	/*
	 * 企業名
	 */
	private String company_name ;
	/*
	 * 就職活動続行ID
	 */
	private String action_status_id;
	/*
	 * 出欠ID
	 */
	private String attendance_id;
	/*
	 * 出欠日時
	 */
	private String attendance_day;
	/*
	 * 宿泊ID
	 */
	private String lodging_day_id;
	/*
	 * 連絡事項
	 */
	private String information;
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
