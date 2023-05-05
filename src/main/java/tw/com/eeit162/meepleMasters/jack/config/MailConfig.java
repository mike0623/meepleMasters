package tw.com.eeit162.meepleMasters.jack.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
	@Value("${mail.host}")
	private String host;
	@Value("${mail.port}")
	private int port;
	@Value("${mail.username}")
	private String username;
	@Value("${mail.password}")
	private String password;
	@Value("${mail.protocol}")
	private String protocol;
	@Value("${mail.smtp.auth}")
	private boolean authEnabled;
	@Value("${mail.smtp.starttls.enable}")
	private boolean starttlsEnabled;
	
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getProtocol() {
		return protocol;
	}
	public boolean isAuthEnabled() {
		return authEnabled;
	}
	public boolean isStarttlsEnabled() {
		return starttlsEnabled;
	}

	
}
