package oracle.bpm.workspace.client.test.api.common;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.bpel.services.workflow.client.IWorkflowServiceClient;
import oracle.bpel.services.workflow.client.IWorkflowServiceClientConstants;
import oracle.bpel.services.workflow.client.WorkflowServiceClientFactory;
import oracle.bpel.services.workflow.metadata.ITaskMetadataService;
import oracle.bpel.services.workflow.query.ITaskQueryService;
import oracle.bpel.services.workflow.task.ITaskService;
import oracle.bpm.client.BPMServiceClientFactory;
import oracle.bpm.services.authentication.IBPMUserAuthenticationService;
import oracle.bpm.services.client.IBPMServiceClient;
import oracle.soa.management.facade.Locator;
import oracle.soa.management.facade.LocatorFactory;
import oracle.soa.management.facade.ServerManager;
import oracle.soa.management.facade.ServerManagerFactory;
import oracle.tip.pc.services.identity.BPMIdentityService;

public class SOAServiceClient {
	public static final String domainIdentity = "jazn.com";
	public static final String serverURL = "t3://oraclebpm:7003";
	public static final String wsURL = "http://oraclebpm:7003";
	public static final String userName = "weblogic";
	public static final String password = "welcome1";
	public static final String initialContextFactory = "weblogic.jndi.WLInitialContextFactory";
	public static final String participateInClientTransaction = "false";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SOAServiceClient() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Locator getLocator() {
		Hashtable<String, String> soaserver = new Hashtable<String, String>();
		soaserver.put(Context.PROVIDER_URL, serverURL+"/soa-infra");
		soaserver.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
		soaserver.put(Context.SECURITY_PRINCIPAL, userName);
		soaserver.put(Context.SECURITY_CREDENTIALS, password);
		soaserver.put("dedicated.connection", "true");
		Locator locator = null;
		
		//here we connect to server
		try {
			locator = LocatorFactory.createLocator(soaserver);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return locator;
	}
	
	public ServerManager getServerManager() {
		Hashtable<String, String> soaserver = new Hashtable<String, String>();
		soaserver.put(Context.PROVIDER_URL, domainIdentity+"/soa-infra");
		soaserver.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
		soaserver.put(Context.SECURITY_PRINCIPAL, userName);
		soaserver.put(Context.SECURITY_CREDENTIALS, password);
		soaserver.put("dedicated.connection", "true");
		
		ServerManager sm = null;
		ServerManagerFactory smf = ServerManagerFactory.getInstance();
		
		try {
			sm = smf.createServerManager(soaserver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sm;
	}
	
	private BPMServiceClientFactory getBPMServiceClientFactory() {
		Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> properties =
	            new HashMap<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String>();
	     
		//SOA ?�버??배치?��? ?�을 경우 ?�요??
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.CLIENT_TYPE, "REMOTE");
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_PROVIDER_URL, serverURL);
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_PRINCIPAL, userName);
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_CREDENTIALS, password);
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_INITIAL_CONTEXT_FACTORY, initialContextFactory);
        
        return BPMServiceClientFactory.getInstance(properties, null, null);
        
	}
	
	public IBPMUserAuthenticationService getBPMUserAuthenticationService() {
		return getBPMServiceClientFactory().getBPMUserAuthenticationService();
		
		
	}
	
	public IBPMServiceClient getBPMServiceClient() {
		return getBPMServiceClientFactory().getBPMServiceClient();
	}
	
	public IWorkflowServiceClient getWorkflowServiceClient() {
		Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> properties =
	            new HashMap<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String>();
		
		//SOA ?�버??배치?��? ?�을 경우 ?�요??
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.CLIENT_TYPE, "REMOTE");
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_PROVIDER_URL, serverURL);
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_PRINCIPAL, userName);
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_SECURITY_CREDENTIALS, password);
        properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.EJB_INITIAL_CONTEXT_FACTORY, initialContextFactory);
		
		return WorkflowServiceClientFactory.getWorkflowServiceClient(WorkflowServiceClientFactory.REMOTE_CLIENT, properties, null);
	}
	
	public BPMIdentityService getBPMIdentityService() {
		Map<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String> properties =
	            new HashMap<IWorkflowServiceClientConstants.CONNECTION_PROPERTY, String>();
		
		//SOA ?�버??배치?��? ?�을 경우 ?�요??
		properties.put(IWorkflowServiceClientConstants.CONNECTION_PROPERTY.SOAP_END_POINT_ROOT, wsURL);
		
		return WorkflowServiceClientFactory.getSOAPIdentityServiceClient(domainIdentity, properties, null);
	}
	
	public ITaskQueryService getTaskQueryService() {
		return getWorkflowServiceClient().getTaskQueryService();
	}
	
	public ITaskService getTaskService() {
		return getWorkflowServiceClient().getTaskService();
	}
	
	public ITaskMetadataService getTaskMetadataService() {
		return getWorkflowServiceClient().getTaskMetadataService();
	}
}
