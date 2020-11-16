package jp.ac.hcs.white.user;

/**
 * ユーザ状態を定義する.
 * 1 有効		←初期値
 * 2 ロック中
 * 3 無効
 */
public enum UserStatus {
	VALID(1), LOCKED(2), INVALID(3);

	private final int code;

	private UserStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
