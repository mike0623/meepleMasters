package tw.com.eeit162.meepleMasters.jack.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;

public class AuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();
		Member attribute = (Member)session.getAttribute("member");
		System.out.println(attribute.getMemberActive());
		if((attribute.getMemberActive())==0) {
			response.sendRedirect(contextPath + "/login");
		}else {
			 filterChain.doFilter(request, response);
		}
		
	}

}
