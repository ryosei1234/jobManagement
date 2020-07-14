package jp.ac.hcs.white;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

	/** タスク情報のCSVファイル名 */
	public static final String FILENAME_CSV = "list.csv";


	@Bean
	public RestTemplate restTemplate() {

		return new RestTemplate();
	}

}
