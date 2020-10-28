package jobhuntingreport;


public class JobHuntingData {

	/*
	 * 就職活動申請・報告ID
	 * 必須入力
	 */
	private int examination_report_id;
	/*
	 * ユーザID
	 */
	private String user_id;
	/*
	 * 就職活動申請・報告状態ID
	 */
	private int examination_status_id;
	/*
	 * 活動内容ID
	 */
	private int action_id;
	/*
	 * 活動場所
	 */
	private String action_place;
	/*
	 * 活動開始日時
	 */
	private String action_day;
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
	private int action_status_id;
	/*
	 * 出欠ID
	 */
	private int attendance_id;
	/*
	 * 出欠日時
	 */
	private String attendance_day;
	/*
	 * 宿泊ID
	 */
	private int lodging_day_id;
	/*
	 * 連絡事項
	 */
	private String information;
}
