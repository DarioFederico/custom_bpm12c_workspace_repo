package oracle.bpm.workspace.client.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import oracle.bpel.services.workflow.verification.IWorkflowContext;

public class CustomBPMUser implements UserDetails {
	private String username;
	private String password;
	
	private IWorkflowContext wfCtx;
	
	public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();    
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        return authorities;
    }
	
	public IWorkflowContext getWfCtx() {
		return wfCtx;
	}

	public void setWfCtx(IWorkflowContext wfCtx) {
		this.wfCtx = wfCtx;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
