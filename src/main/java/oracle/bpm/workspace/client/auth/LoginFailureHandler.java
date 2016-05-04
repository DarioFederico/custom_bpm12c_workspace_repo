package oracle.bpm.workspace.client.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailureHandler implements AuthenticationFailureHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException auth)
			throws IOException, ServletException {
		
		// 안중 성공 시 처리 : 일반적으로 계정에 대한 초기화 또는 로그 기록 작업을 수행한다.
		//response.sendRedirect(request.getContextPath() + "/login");
		logger.debug("===========  onAuthenticationFailure ===============");
	}

}
