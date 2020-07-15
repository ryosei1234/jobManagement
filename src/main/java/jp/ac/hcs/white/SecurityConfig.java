package jp.ac.hcs.white;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/** ユーザーIDとパスワードを取得するSQL */
	private static final String USER_SQL = "SELECT user_id, encrypted_password as password, true FROM m_user WHERE user_id = ?";

	/** ユーザーの権限を取得するSQL */
	private static final String ROLE_SQL = "SELECT user_id, user_role FROM m_user WHERE user_id = ?";

	@Override
	public void configure(WebSecurity web) throws Exception {

		// 静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers("/css/∗∗", "/js/∗∗", "/img/∗∗", "/h2-console/∗∗");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// ログイン不要ページの設定
		http.authorizeRequests().antMatchers("/css/**").permitAll() // cssへアクセス許可
				.antMatchers("/js/**").permitAll() // jsへアクセス許可
				.antMatchers("/img/**").permitAll() // imgへアクセス許可
				.antMatchers("/login").permitAll() // ログインページは直リンクOK
				.antMatchers("/signup").permitAll() // 新規ユーザー登録画面は直リンクOK
				.antMatchers("/h2-console/**").permitAll() // XXX h2-console使用時は有効にする.
				.antMatchers("/exam/**").hasAuthority("ROLE_TEACHER") // 受験報告機能は管理権限ユーザに許可
				.anyRequest().authenticated(); // それ以外は直リンク禁止

		//ログイン処理
		http.formLogin().loginProcessingUrl("/login") // ログイン処理のパス
				.loginPage("/login") // ログインページの指定
				.failureUrl("/login") // ログイン失敗時の遷移先
				.usernameParameter("user_id") // ログインページのユーザID
				.passwordParameter("password") // ログインページのパスワード
				.defaultSuccessUrl("/", true); // ログイン成功後の遷移先

		//ログアウト処理
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout") //ログアウト処理のパス
				.logoutSuccessUrl("/login"); //ログアウト成功後の遷移先

		// (開発用)CSRF対策 無効設定
		// XXX h2-console使用時は有効にする.
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// ログイン処理時のユーザ情報をDBから取得する
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(USER_SQL)
				.authoritiesByUsernameQuery(ROLE_SQL)
				.passwordEncoder(passwordEncoder());
	}

}
