package jp.ac.hcs.white.jobhuntingreport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JobFormH {



@NotNull(message = "{require_check}")
private String action_day;

private String action_end_day;

@NotBlank(message = "{require_check}")
private String action_place;

@NotBlank(message = "{require_check}")
private String action_id;

@NotBlank(message = "{require_check}")
private String company_name;

@NotBlank(message = "{require_check}")
private String attendance_id;

@NotBlank(message = "{require_check}")
private String attendance_day;


private String attendance_end_day;

@NotBlank(message = "{require_check}")
private String schedule;

private String information;

@NotNull(message = "{require_check}")
private String action_status_id;

@NotNull(message = "{require_check}")
private String contents_report;

@NotNull(message = "{require_check}")
private String examination_report_id;



}
