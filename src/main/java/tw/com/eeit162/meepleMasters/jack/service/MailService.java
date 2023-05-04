package tw.com.eeit162.meepleMasters.jack.service;

import org.springframework.beans.factory.InitializingBean;

public class MailService implements InitializingBean {
	private final String from = "meeplemasters@gmail.com";
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}
