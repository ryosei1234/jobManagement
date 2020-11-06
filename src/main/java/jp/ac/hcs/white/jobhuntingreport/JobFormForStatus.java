package jp.ac.hcs.white.jobhuntingreport;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JobFormForStatus {

	@NotNull(message = "{require_check}")
	private String examination_report_id;

	@NotNull(message = "{require_check}")
	private String examination_status_id;

}
