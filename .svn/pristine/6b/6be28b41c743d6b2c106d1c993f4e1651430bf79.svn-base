package oracle.bpm.workspace.client.auth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.bpm.services.common.exception.BPMException;
import oracle.bpm.workspace.client.config.SOAServiceClient;


public class CustomAuthenticationManager implements AuthenticationProvider {
	
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	@SuppressWarnings("static-access")
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		String username = auth.getName();
		String password = (String) auth.getCredentials();
		
		logger.debug("username:" + username);
		logger.debug("password:" + password);
		
		try {
			IWorkflowContext wfCtx = soaClient.getTaskQueryService().authenticate(username, password.toCharArray(), null);
			IWorkflowContext adminCtx = soaClient.getTaskQueryService().authenticate("weblogic", "welcome1".toCharArray(), null);
			IBPMContext bpmCtx = soaClient.getBPMUserAuthenticationService().getBPMContextForAuthenticatedUser();
			
			logger.debug("CustomAuthenticationManager.wfCtx.getUser():" + wfCtx.getUser());
			
			ContextCache.getContextCache().put(username, wfCtx);
			ContextCache.getContextCache().put("adminCtx", adminCtx);
			ContextCache.getContextCache().put("bpm_"+username, bpmCtx);
			
			return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), getGrantedAuthority());
		} catch (WorkflowException e) {
			e.printStackTrace();
			throw new BadCredentialsException("Username/Password does not match for "
					+ auth.getPrincipal());
		} catch (BPMException e) {
			e.printStackTrace();
			throw new BadCredentialsException("Username/Password does not match for "
					+ auth.getPrincipal());
		}
	}

	public boolean supports(Class<? extends Object> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private List<GrantedAuthority> getGrantedAuthority() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		// Login User가 가지고 있는 Role 정보를 따로 가지고 있다면, 해당 Role을 가지고 오는 로직을 구현하여 사용하면 된다.
		// Oracle BPM 에서 관리하는 Role을 가지고 오는 로직을 통해 
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return authorities;
	}

}
