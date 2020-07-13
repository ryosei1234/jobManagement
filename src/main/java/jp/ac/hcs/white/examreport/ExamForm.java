package jp.ac.hcs.white.examreport;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ExamForm {

@NotBlank(message = "{require_check}")
private String department;

@NotBlank(message = "{require_check}")
private String company_name_top;


private int recruitment_number;

@NotBlank(message = "{require_check}")
private String company_name;

@NotBlank(message = "{require_check}")
private String application_route;

@NotBlank(message = "{require_check}")
private String exam_date_time;

@NotBlank(message = "{require_check}")
private String examination_location;

@NotBlank(message = "{require_check}")
private String contens_test;

@NotBlank(message = "{require_check}")
private String remarks;

}
