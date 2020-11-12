package jp.ac.hcs.white.examreport;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ExamFormForStatus {

	@NotBlank(message = "{require_check}")
	private String exam_report_status;

	@NotBlank(message = "{require_check}")
	private String examreport_id;

}
