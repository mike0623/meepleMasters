package tw.com.eeit162.meepleMasters.jack.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tw.com.eeit162.meepleMasters.jack.filter.MemberLoginFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<MemberLoginFilter> memberLoginFilter(){
		FilterRegistrationBean<MemberLoginFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new MemberLoginFilter());
		
		registration.addUrlPatterns("/member/*","/admin");
		registration.setOrder(0);
		return registration;
	}
}
