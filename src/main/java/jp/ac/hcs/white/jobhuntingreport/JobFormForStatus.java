package jp.ac.hcs.white.jobhuntingreport;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class JobFormForStatus {

	@NotBlank(message = "{require_check}")
	private String examination_report_id;

	@NotBlank(message = "{require_check}")
	private String examination_status_id;

}
