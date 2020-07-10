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
	  private String user_id;
	  private String user_class;
	  private int user_student_no;
	  private String user_name;
	  private String role;
	  private String examreport_id ;
	  private String department ;
	  private String company_name_top;
	  private Date report_day ;
	  private int recruitment_number;
	  private String company_name;
	  private String application_route ;
	  private Date exam_date_time;
	  private String examination_location;
	  private String contens_test;
	  private String remarks;
	  private String exam_report_status;
}
