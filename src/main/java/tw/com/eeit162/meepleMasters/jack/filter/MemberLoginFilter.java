package tw.com.eeit162.meepleMasters.jack.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

public class MemberLoginFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			HttpSession session = request.getSession();
			String contextPath = request.getContextPath();
			boolean isServer = false;
			if(request.getHeader("isServer") != null) {
				isServer = ("yes").equals(request.getHeader("isServer"));
			}
			if(session.getAttribute("member") == null && !isServer) {
				response.sendRedirect(contextPath + "/login");
			}else {
				 filterChain.doFilter(request, response);
			}
						
	}
	
}
