package jp.ac.hcs.white.jobhuntingreport;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobHuntingData {

	/*
	 * 就職活動申請・報告ID
	 * 必須入力
	 */
	private String examination_report_id;{
	}

	/*
	 * ユーザID
	 */
	private String user_id;{
	}

	/*
	 * 就職活動申請・報告状態ID
	 */
	private String examination_status_id;{
	}

	/*
	 * 活動内容ID
	 */
	private String action_id;{
	}

	/*
	 * 活動場所
	 */
	private String action_place;{
	}

	/*
	 * 活動開始日時
	 */
	private String action_day;{
	}

	/*
	 * 活動終了日時
	 */
	private String action_end_day;{
	}

	/*
	 * 企業名
	 */
	private String company_name ;{
	}

	/*
	 * 就職活動続行ID
	 */
	private String action_status_id;{
	}

	/*
	 * 出欠ID
	 */
	private String attendance_id;{
	}

	/*
	 * 出欠日時
	 */
	private String attendance_day;{
	}

	/*
	 * 出欠終了日時
	 */
	private String attendance_end_day;{
	}

	/*
	 * 宿泊ID
	 */
	private String lodging_day_id;{
	}

	/*
	 * 連絡事項
	 */
	private String information;{
	}

	/*
	 * スケジュール
	 */
	private String schedule;{
	}

	/*
	 * 報告内容
	 */
	private String contents_report;{
	}

	/*
	 * ユーザ名
	 */
	private String user_name;{
	}


	/*
	 * ユーザのクラス
	 */
	private String user_class;{
	}

	/*
	 * ユーザの出席番号
	 */
	private String user_student_no;{
	}
}
