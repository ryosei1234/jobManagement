package jobhuntingreport;


public class JobHuntingData {

	/*
	 * 就職活動申請・報告ID
	 * 必須入力
	 */
	private int examination_report_id;
	public int getExamination_report_id(){
		return examination_report_id;
	}
	public void setExamination_report_id(int examination_report_id) {
		this.examination_report_id = examination_report_id;
	}
	/*
	 * ユーザID
	 */
	private String user_id;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/*
	 * 就職活動申請・報告状態ID
	 */
	private int examination_status_id;
	public int getExamination_status_id() {
		return examination_status_id;
	}
	public void setExamination_status_id(int examination_status_id) {
		this.examination_status_id = examination_status_id;
	}
	/*
	 * 活動内容ID
	 */
	private int action_id;
	public int getAction_id() {
		return action_id;
	}
	public void setAction_id(int action_id) {
		this.action_id = action_id;
	}
	/*
	 * 活動場所
	 */
	private String action_place;
	public String getAction_place() {
		return action_place;
	}
	public void setAction_place(String action_place) {
		this.action_place = action_place;
	}
	/*
	 * 活動開始日時
	 */
	private String action_day;
	public String getAction_day() {
		return action_day;
	}
	public void setAction_day(String action_day) {
		this.action_day = action_day;
	}
	/*
	 * 活動終了日時
	 */
	private String action_end_day;
	public String getAction_end_day() {
		return action_end_day;
	}
	public void setAction_end_day(String action_end_day) {
		this.action_end_day = action_end_day;
	}
	/*
	 * 企業名
	 */
	private String company_name ;
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	/*
	 * 就職活動続行ID
	 */
	private int action_status_id;
	public int getAction_status_id() {
		return action_status_id;
	}
	public void setAction_status_id(int action_status_id) {
		this.action_status_id = action_status_id;
	}
	/*
	 * 出欠ID
	 */
	private int attendance_id;
	public int getAttendance_id() {
		return attendance_id;
	}
	public void setAttendance_id(int attendance_id) {
		this.attendance_id = attendance_id;
	}
	/*
	 * 出欠日時
	 */
	private String attendance_day;
	public String getAttendance_day() {
		return attendance_day;
	}
	public void setAttendance_day(String attendance_day) {
		this.attendance_day = attendance_day;
	}
	/*
	 * 宿泊ID
	 */
	private int lodging_day_id;
	public int getLodging_day_id() {
		return lodging_day_id;
	}
	public void setLodging_day_id(int lodging_day_id) {
		this.lodging_day_id = lodging_day_id;
	}
	/*
	 * 連絡事項
	 */
	private String information;
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	/*
	 * スケジュール
	 */
	private String schedule;
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	/*
	 * 報告内容
	 */
	private String contents_report;
	public String getContents_report() {
		return contents_report;
	}
	public void setContents_report(String contents_report) {
		this.contents_report = contents_report;
	}
	/*
	 * ユーザ名
	 */
	private String user_name;
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/*
	 * ユーザのクラス
	 */
	private String user_class;
	public String getUser_class() {
		return user_class;
	}
	public void setUser_class(String user_class) {
		this.user_class = user_class;
	}

	/*
	 * ユーザの出席番号
	 */
	private String user_student_no;
	public String getUser_student_no() {
		return user_student_no;
	}
	public void setUser_student_no(String user_student_no) {
		this.user_student_no = user_student_no;
	}
}
