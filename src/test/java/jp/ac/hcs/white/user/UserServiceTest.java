package jp.ac.hcs.white.user;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;

	@Test
	public void testSelectAll() {
		// 1.Ready
		// 2.Do
		UserEntity userEntity = userService.selectAll();
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testSelectAll]userEntity:" + userEntity.toString());
	}

	@Test
	public void testSelectOne() {
		// 1.Ready
		// 2.Do
		UserData userEntity = userService.selectOne("yamada@xxx.co.jp");
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testSelectOne]userEntity:" + userEntity.toString());
	}

	@Test
	public void testUpdateOneWithPassword() {
		// 1.Ready
		UserData data = new UserData();
		data.setUser_id("aaa@xxx.co.jp");
		data.setUser_name("山田");
		data.setRole("ROLE_TEACHER");
		data.setUser_class("S3A1");
		data.setUser_student_no("00");
		data.setUpdate_user_id("aaa@xxx.co.jp");

		// 2.Do
		data.setPassword("password");
		boolean result = userService.updateOneWithPassword(data);
		// 3.Assert
		//assertEquals(true, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testUpdateOne]userEntity:" + userEntity.toString());
	}

	@Test
	public void testInsertOne() {
		// 1.Ready
		UserData data = new UserData();
		data.setUser_id("aaa@xxx.co.jp");
		data.setPassword("asdfg");
		data.setUser_name("田中");
		data.setUser_darkmode(false);
		data.setRole("学生");
		data.setUser_class("S3A1");
		data.setUser_student_no("11");
		// 2.Do
		boolean result = userService.insertOne(data, "aaa@xxx.co.jp");
		// 3.Assert
		assertEquals(true, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testInsertOne]userEntity:" + userEntity.toString());
	}

	@Test
	public void testDeleteOne() {
		// 1.Ready
		// 2.Do
		boolean result = userService.deleteOne("yamada@xxx.co.jp");
		// 3.Assert
		assertEquals(true, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testDeleteOne]userEntity:" + userEntity.toString());
	}

	@Test
	public void testSearch() {
		// 1.Ready
		// 2.Do
		UserEntity userEntity = userService.search("aaa@xxx.co.jp", "");
		// 3.Assert
		//assertNotNull(examEntity);
		// 4.logs
		log.warn("[testSearch]userEntity:" + userEntity.toString());
	}

}
