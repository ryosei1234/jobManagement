package jp.ac.hcs.white.user;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * ユーザ情報をまとめて管理する為のエンティティクラス.
 */
@Data
public class UserEntity {

	/** ユーザ情報のリスト */
	private List<UserData> userlist = new ArrayList<UserData>();

}
