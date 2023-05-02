package tw.com.eeit162.meepleMasters.init;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class Initialize implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ServletContext servletContext = ((WebApplicationContext) event.getApplicationContext()).getServletContext();
		servletContext.setAttribute("root", servletContext.getContextPath());
		servletContext.setAttribute("webName", "米寶大師");
		servletContext.setAttribute("adminWebName", "米寶大師後台管理系統");
		
//		insertDataIntoDB();
	}
	
	
	//給初始化資料用
//	private void insertDataIntoDB() {
	


	
	
	
}
