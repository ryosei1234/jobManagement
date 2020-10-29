package jobhuntingreport;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class JobForm {

@NotBlank(message = "{require_check}")
private String action_day;

@NotBlank(message = "{require_check}")
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
private String schedule;

@NotBlank(message = "{require_check}")
private String information;

}
