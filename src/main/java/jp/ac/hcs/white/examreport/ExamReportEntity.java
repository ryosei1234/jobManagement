package jp.ac.hcs.white.examreport;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ExamReportEntity {

		/** 受験報告情報のリスト */
		private List<ExamReportData> examlist = new ArrayList<ExamReportData>();

}
