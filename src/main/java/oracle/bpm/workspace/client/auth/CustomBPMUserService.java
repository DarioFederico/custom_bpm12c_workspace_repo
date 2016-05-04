package oracle.bpm.workspace.client.auth;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import oracle.bpm.workspace.client.config.SOAServiceClient;


public class CustomBPMUserService implements UserDetailsService {
	
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		
		//StandardPasswordEncoder encoder = new StandardPasswordEncoder();
        
        CustomBPMUser bpmUser = new CustomBPMUser();
        
        bpmUser.setUsername(user);
        bpmUser.setPassword("welcome1");
        //bpmUser.setPassword(encoder.encode("welcome1"));
        
        return bpmUser;
	}
}
