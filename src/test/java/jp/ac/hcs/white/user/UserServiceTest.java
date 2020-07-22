package jp.ac.hcs.white.user;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;

	@SpyBean
	UserRepository userRepository;

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
	public void testUpdateOne() {
		// 1.Ready
		UserData data = new UserData();
		data.setUser_id("yamada@xxx.co.jp");
		data.setUser_name("山田鈴木");
		data.setRole("ROLE_TEACHER");
		data.setUser_class("S3A1");
		data.setUser_student_no("00");
		data.setUpdate_user_id("yamada@xxx.co.jp");
		// 2.Do
		boolean result = userService.updateOne(data);
		log.warn(""+result);
		// 3.Assert
		assertEquals(true, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testUpdateOne]userEntity:" + userEntity.toString());
	}

	@Test
	public void testUpdateOne_失敗() {
		// 1.Ready
		UserData data = new UserData();
		data.setUser_name("山田");
		data.setRole("ROLE_TEACHER");
		data.setUser_class("S3A1");
		data.setUser_student_no("00");
		data.setUpdate_user_id("aaa@xxx.co.jp");
		data.setPassword("password");

		// 2.Do
		boolean result = userService.updateOne(data);
		log.warn(""+result);
		// 3.Assert
		assertEquals(false, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testUpdateOne_失敗]userEntity:" + userEntity.toString());
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
		data.setPassword("password");

		// 2.Do
		boolean result = userService.updateOneWithPassword(data);
		// 3.Assert
		//assertEquals(true, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testUpdateOne]userEntity:" + userEntity.toString());
	}

	@Test
	public void testUpdateOneWithPassword_失敗() {
		// 1.Ready
		UserData data = new UserData();
		data.setUser_name("山田");
		data.setRole(null);
		data.setUser_class("S3A1");
		data.setUser_student_no("00");
		data.setUpdate_user_id("aaa@xxx.co.jp");
		data.setPassword("password");
		// 2.Do

		boolean result = userService.updateOneWithPassword(data);
		// 3.Assert
		assertEquals(false, result);
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
	public void testInsertOne_失敗() {
		// 1.Ready
		UserData data = new UserData();
		data.setUser_id(null);
		data.setPassword("");
		data.setUser_name("");
		data.setUser_darkmode(false);
		data.setRole("");
		data.setUser_class("");
		data.setUser_student_no("");
		// Mock
		doReturn(0).when(userRepository).insertOne(any(),anyString());
		// 2.Do
		boolean result = userService.insertOne(data, "aaa@xxx.co.jp");
		// 3.Assert
		assertEquals(false, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testInsertOne_失敗]userEntity:" + userEntity.toString());
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
	public void testDeleteOne_失敗() {
		// 1.Ready
		// 2.Do
		boolean result = userService.deleteOne("");
		// 3.Assert
		assertEquals(false, result);
		// 4.logs
		UserEntity userEntity = userService.selectAll();
		log.warn("[testDeleteOne_失敗]userEntity:" + userEntity.toString());
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

	@Test
	public void testprofileUpdateOne() {
		// 1.Ready
				UserData data = new UserData();
				data.setUser_id("yamada@xxx.co.jp");
				data.setUser_name("山田鈴木");
				data.setRole("ROLE_TEACHER");
				data.setUser_class("S3A1");
				data.setUser_student_no("00");
				data.setUpdate_user_id("yamada@xxx.co.jp");
				// 2.Do
				boolean result = userService.updateOne(data);
				log.warn(""+result);
				// 3.Assert
				assertEquals(true, result);
				// 4.logs
				UserEntity userEntity = userService.selectAll();
				log.warn("[testUpdateOne]userEntity:" + userEntity.toString());
	}

	@Test
	public void testprofileUpdateOne_失敗() {
		// 1.Ready
				UserData data = new UserData();
				data.setUser_name("山田");
				data.setRole(null);
				data.setUser_class("S3A1");
				data.setUser_student_no("00");
				data.setUpdate_user_id("aaa@xxx.co.jp");
				data.setPassword("password");
				// 2.Do

				boolean result = userService.updateOneWithPassword(data);
				// 3.Assert
				assertEquals(false, result);
				// 4.logs
				UserEntity userEntity = userService.selectAll();
				log.warn("[testUpdateOne]userEntity:" + userEntity.toString());
	}

}
