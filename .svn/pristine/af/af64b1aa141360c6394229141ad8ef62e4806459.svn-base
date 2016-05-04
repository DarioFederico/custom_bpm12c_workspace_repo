package oracle.bpm.workspace.client.auth;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.bpm.services.common.exception.BPMException;
import oracle.bpm.workspace.client.config.SOAServiceClient;


public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	@Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
		
		if(soaClient == null) {
    		logger.debug("Logout Success Handler : soaClient is null");
    	} else {
    		logger.debug("Logout Success Handler : soaClient is not null");
    	}
		
        if (authentication != null) {
        	
        	IWorkflowContext wfCtx = (IWorkflowContext) ContextCache.get(authentication.getName());
        	IBPMContext bpmCtx = (IBPMContext) ContextCache.get(authentication.getName());
        	
    		logger.debug("########## Logout Handler Workflow Context User : "+(wfCtx.getUser()));
        	try {
				soaClient.getWorkflowServiceClient().getTaskQueryService().destroyWorkflowContext(wfCtx);
				soaClient.getBPMUserAuthenticationService().destroyBPMContext(bpmCtx);
				
				ContextCache.remove(authentication.getName());
				ContextCache.remove("bpm_"+authentication.getName());
			} catch (WorkflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BPMException e) {
				e.printStackTrace();
			}
        }

        setDefaultTargetUrl("/login");
        super.onLogoutSuccess(request, response, authentication);       
    }
}
