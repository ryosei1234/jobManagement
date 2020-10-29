package jobhuntingreport;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JobHuntingEntity {
	/** 就職活動申請情報のリスト */
	private List<JobHuntingData> joblist = new ArrayList<JobHuntingData>();


}
