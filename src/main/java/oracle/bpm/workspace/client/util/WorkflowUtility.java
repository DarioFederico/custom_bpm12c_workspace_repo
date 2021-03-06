package oracle.bpm.workspace.client.util;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.identity.client.User;
import oracle.bpel.services.workflow.IWorkflowConstants;
import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.common.impl.CommonUtil;
import oracle.bpel.services.workflow.common.model.Participant;
import oracle.bpel.services.workflow.query.ITaskQueryService;
import oracle.bpel.services.workflow.repos.Column;
import oracle.bpel.services.workflow.repos.Ordering;
import oracle.bpel.services.workflow.repos.Predicate;
import oracle.bpel.services.workflow.repos.TableConstants;
import oracle.bpel.services.workflow.task.model.IdentityType;
import oracle.bpel.services.workflow.task.model.SystemMessageAttributesType;
import oracle.bpel.services.workflow.task.model.Task;
import oracle.bpel.services.workflow.user.IUserMetadataService;
import oracle.bpel.services.workflow.user.model.UserPreference;
import oracle.bpel.services.workflow.user.model.UserPreferences;
import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.bpel.services.workflow.worklist.util.FilterUtil;
import oracle.bpm.services.instancemanagement.model.IIdentityType;
import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.constants.OracleConstants;
import oracle.tip.pc.services.identity.BPMGroup;
import oracle.tip.pc.services.identity.BPMUser;
import oracle.tip.pc.services.identity.RoleClassifier;

public class WorkflowUtility {
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	private static String bundleName = OracleConstants.APPLICATION.RESOURCE_BUNDLE;

	public static IWorkflowContext getContext(HttpServletRequest req) throws WorkflowException, Exception {
		IWorkflowContext workflowContext = null;

		try {
			SOAServiceClient soaClient = (SOAServiceClient) getBean("soaClient");
			workflowContext = soaClient.getWorkflowServiceClient().getTaskQueryService().createContext(req);
		} catch (Exception e) {
			throw e;
		}
		return workflowContext;
	}
	
	public static IWorkflowContext getContext(String account_id, String account_passwd) throws WorkflowException, Exception {
		IWorkflowContext workflowContext = null;

		try {
			SOAServiceClient soaClient = (SOAServiceClient) getBean("soaClient");
			workflowContext = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate(account_id, account_passwd.toCharArray(), soaClient.getDomainIdentity());
		
		} catch (Exception e) {
			throw e;
		}
		return workflowContext;
	}
	
	public static Object getBean(String beanName) {
		ApplicationContext appContext = ContextLoaderListener.getCurrentWebApplicationContext();
    	return appContext.getBean(beanName); 
	}
	
    public static Map<String, Object> getBPMUserInfo(IWorkflowContext wfCtx) throws Exception {
    	Map<String, Object> bpm_user_info = new HashMap<String, Object>(); 

    	SOAServiceClient soaClient = (SOAServiceClient) getBean("soaClient"); 
    	
    	BPMUser bpmUser = soaClient.getBPMIdentityService().lookupUser(wfCtx.getUser());
    	
    	bpm_user_info.put("attributes", bpmUser.getAttributes());
        bpm_user_info.put("groups", bpmUser.getGroups(true));
        bpm_user_info.put("roles", bpmUser.getGrantedRoles(false));
     
		List groups = (List) bpmUser.getGroups(true);
		List<String> groupNames = new ArrayList<String>();
        for(int i=0; i<groups.size() ;i++){
          	BPMGroup bpmGroup = (BPMGroup) groups.get(i);
          	groupNames.add(bpmGroup.getDisplayName());
        }
        bpm_user_info.put("groupNames", groupNames);
        
        return bpm_user_info;
    }
    
    //Loads the user-specific preferences into the SessionStore.
  	public static Map<String, Object> initUserPreferences(IWorkflowContext wfCtx) throws WorkflowException {
  		SOAServiceClient soaClient = (SOAServiceClient) getBean("soaClient");
  		
  		Participant user = CommonUtil.getFactory().createParticipant();
        user.setName(wfCtx.getUser());
		user.setRealm(wfCtx.getIdentityContext());
		user.setType(IWorkflowConstants.PARTICIPANT_TYPE_USER);
        
		IUserMetadataService ums = soaClient.getWorkflowServiceClient().getUserMetadataService();
		UserPreferences userPreferences = ums.getUserPreferences(wfCtx, user);
		
		Map<String, Object> userPrefMap = new HashMap<String, Object>();
		
		if ( userPreferences.getUserPreference().size()==0 ) {
  			//By default, users have no preferences stored. If this is the case,
  			//use the defaults...
  			userPrefMap.putAll(OracleConstants.PREFERENCE.USER_DEFAULTS);  
  	    } else {
  	    	//Otherwise, load user preferences from back-end
  	    	Iterator preferences = userPreferences.getUserPreference().iterator();
  	    	while ( preferences.hasNext() ) {
  	    		UserPreference pref = (UserPreference)preferences.next();
  	    		String prefName = pref.getName();
  	    		String prefValue = pref.getValue();
  	    		//Use default value if no value specified
  	    		if ( prefValue == null && prefValue.length() == 0 ) {
  	    			userPrefMap.put(prefName,OracleConstants.PREFERENCE.USER_DEFAULTS.get(prefName));
  	    		} else {
  	    			userPrefMap.put(prefName,prefValue);
  	    		}
  	    	}
  	    }
  		return userPrefMap;
  	}
  	
  	public static HashMap<String, String> getTaskAssignees(IBPMContext ctx, Task task) throws Exception {
		HashMap<String, String> assingees = new HashMap<String, String>();
		
		/*
		//Query Task
		soaClient.getWorkflowServiceClient().getTaskQueryService().q
		
		
		//Group
		if(task.getSystemAttributes().isHasSubTasks()) {
			//task.getSystemAttributes().getParentTaskId()
		}*/
		assingees.put("id", getAssigneeString(task));
		assingees.put("name", getAssigneeDisplayNameString(task));
		return assingees;
		
	}
  	
  	public static HashMap<String, String> getTaskAssigneesOnHistory(IBPMContext ctx, int tasknumber) throws Exception {
		HashMap<String, String> assingees = new HashMap<String, String>();
		
		SOAServiceClient soaClient = (SOAServiceClient) getBean("soaClient");
		
		List<String> inboxColumns = new ArrayList<String>();
		
		inboxColumns.add(TableConstants.WFTASKHISTORY_STATE_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASKHISTORY_ASSIGNEES_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASKHISTORY_ASSIGNEEUSERS_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASKHISTORY_ASSIGNEESDISPLAYNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASKHISTORY_ASSIGNEEUSERSDISPLAYNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASKHISTORY_ASSIGNEEGROUPS_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASKHISTORY_ASSIGNEEGROUPSDISPLAYNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASK_TASKNUMBER_COLUMN.getName());	// Task Number
		
		String taskId = soaClient.getWorkflowServiceClient().getTaskQueryService().getTaskDetailsByNumber(ctx, tasknumber).getSystemAttributes().getTaskId();
		List<Task> tasks = soaClient.getWorkflowServiceClient().getTaskQueryService().getTaskHistory(ctx, taskId, inboxColumns);
		
		StringBuffer buffer = null;
		
		for(Task task : tasks) {
			System.out.println("task.getSystemAttributes().getState() : " +  task.getSystemAttributes().getState());
			if(task.getSystemAttributes().getState().equals("ASSIGNED")) {
				List assignees = task.getSystemAttributes().getAssigneeUsers();
				
		        for(int i = 0 ; i < assignees.size() ; i++) {
		        	IdentityType type = (IdentityType)assignees.get(i);
		        	String name = type.getDisplayName();
		        	
		        	if(buffer == null) {
		        		buffer = new StringBuffer();
		        	}
		        	else {
		        		buffer.append(",");
		        	}
		        	
		        	//buffer.append(name).append("(U)");
		        	buffer.append(name);
		        }
		        
		        assignees = task.getSystemAttributes().getAssigneeGroups();
		        for(int i = 0 ; i < assignees.size() ; i++) {
		        	IdentityType type = (IdentityType)assignees.get(i);
		        	String name = type.getDisplayName();
		        	if(buffer == null) {
		        		buffer = new StringBuffer();
		        	}
		        	else {
		        		buffer.append(",");
		        	}
		        	
		        	//buffer.append(name).append("(G)");
		        	buffer.append(name);
		        	
		        }
			}
			
		}
		
		if(buffer == null) {
			assingees.put("name", "");
        }
        else {
        	assingees.put("name", buffer.toString());
        }
		
		return assingees;
	}
  	
  	public static HashMap<String, String> getTaskAssignees(IBPMContext ctx, int tasknumber) throws Exception {
		HashMap<String, String> assingees = new HashMap<String, String>();
		
		SOAServiceClient soaClient = (SOAServiceClient) getBean("soaClient");
		
		Task task = soaClient.getWorkflowServiceClient().getTaskQueryService().getTaskDetailsByNumber(ctx, tasknumber);
		
		/*
		//Query Task
		soaClient.getWorkflowServiceClient().getTaskQueryService().q
		
		
		//Group
		if(task.getSystemAttributes().isHasSubTasks()) {
			//task.getSystemAttributes().getParentTaskId()
		}*/
		assingees.put("id", getAssigneeString(task));
		assingees.put("name", getAssigneeDisplayNameString(task));
		return assingees;
		
	}
  	
	/*public static String getAssigneeString(String username) throws Exception {
		JpsContextFactory ctxf = JpsContextFactory.getContextFactory();
        JpsContext ctx = ctxf.getContext();
        IdentityStoreService storeService = ctx.getServiceInstance(IdentityStoreService.class);
        IdentityStore is = storeService.getIdmStore();
        
        User user = is.searchUser(username);
        
        return user.getDisplayName();
	}*/
	
	public static String getAssigneeString(Task task) throws Exception {
		List assignees = task.getSystemAttributes().getAssigneeUsers();
        StringBuffer buffer = null;
        for(int i = 0 ; i < assignees.size() ; i++) {
        	IdentityType type = (IdentityType)assignees.get(i);
        	String name = type.getId();
        	if(buffer == null) {
        		buffer = new StringBuffer();
        	}
        	else {
        		buffer.append(",");
        	}
        	buffer.append(name).append("(U)");
        }
        
        assignees = task.getSystemAttributes().getAssigneeGroups();
        
        for(int i = 0 ; i < assignees.size() ; i++) {
        	IdentityType type = (IdentityType)assignees.get(i);
        	String name = type.getId();
        	if(buffer == null) {
        		buffer = new StringBuffer();
        	}
        	else {
        		buffer.append(",");
        	}
        	
        	buffer.append(name).append("(G)");
        	
        }
        if(buffer == null) {
           return "";
        }
        else {
          return buffer.toString();
        }
	}
	
	public static String getAssigneeDisplayNameString(Task task) throws Exception {
		
		StringBuffer buffer = null;
		
		List assignees = task.getSystemAttributes().getAssigneeUsers();
        for(int i = 0 ; i < assignees.size() ; i++) {
        	IdentityType type = (IdentityType)assignees.get(i);
        	String name = type.getDisplayName();
        	if(buffer == null) {
        		buffer = new StringBuffer();
        	}
        	else {
        		buffer.append(",");
        	}
        	//buffer.append(name).append("(U)");
        	buffer.append(name);
        }
        
        assignees = task.getSystemAttributes().getAssigneeGroups();
        
        for(int i = 0 ; i < assignees.size() ; i++) {
        	IdentityType type = (IdentityType)assignees.get(i);
        	String name = type.getDisplayName();
        	if(buffer == null) {
        		buffer = new StringBuffer();
        	}
        	else {
        		buffer.append(",");
        	}
        	
        	//buffer.append(name).append("(G)");
        	buffer.append(name);
        	
        }
		
        if(buffer == null) {
           return "";
        }
        else {
          return buffer.toString();
        }
	}
	
	/*public static String getAssigneeStringFromIdentityType(List<IIdentityType> assignees) throws Exception {
        StringBuffer buffer = null;
        for(int i = 0 ; i < assignees.size() ; i++) {
        	//type : user, group, application_role, position
        	
        	IIdentityType type = (IIdentityType)assignees.get(i);
        	String name = type.getId();
        	String displayName = null;
        	
        	//현재 요청중인 thread local의 HttpServletRequest 객체 가져오기
        	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        	 
        	 //HttpSession 객체 가져오기
        	 HttpSession session = request.getSession();
        	 
        	 //ServletContext 객체 가져오기
        	 ServletContext conext = session.getServletContext();
        	 
        	 //Spring Context 가져오기
        	 WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(conext);
        	 
        	 //스프링 빈 가져오기 & casting
        	 IdentityStoreServiceClient identityStoreServiceClient = (IdentityStoreServiceClient)wContext.getBean("identityStoreServiceClient");
        	 
        	if(type.getType().equals("user")) {
            	if(buffer == null) {
            		buffer = new StringBuffer();
            	}
            	else {
            		buffer.append(",");
            	}
            	//buffer.append(displayName).append("(U)");
            	try {
            		displayName = identityStoreServiceClient.getUserDisplayName(name);
            	} catch (NullPointerException e) {
            		e.printStackTrace();
            	}
            	
            	buffer.append((displayName == null ? name : displayName));
        	} else if(type.getType().equals("group")) {
            	if(buffer == null) {
            		buffer = new StringBuffer();
            	}
            	else {
            		buffer.append(",");
            	}
            	
            	//buffer.append(displayName).append("(G)");
            	buffer.append(name);
        	} else if(type.getType().equals("application_role")) {
            	if(buffer == null) {
            		buffer = new StringBuffer();
            	}
            	else {
            		buffer.append(",");
            	}
            	
            	buffer.append(name);
        	}
        }
        
        if(buffer == null) {
           return "";
        }
        else {
          return buffer.toString();
        }
	}*/
	
  	public static Map<String, String> getFilterCodeList(String codeName, Boolean isAdmin, Locale userLocale) {
		ResourceBundle bundle = null;
        if(userLocale != null) {
			checkResourceBundle();
			bundle = ResourceBundle.getBundle(OracleConstants.APPLICATION.RESOURCE_BUNDLE, userLocale);
        }
        
		Map<String, String> codes = new TreeMap<String, String>();
		
		if(codeName.equals(FilterUtil.FILTER_TYPE_ASSIGNMENT_FILTER)) {
		    //if (isAdmin)
		    //	codes.put(ITaskQueryService.AssignmentFilter.ADMIN.name(), bundle.getString("DROPDOWN_CATEGORY_ADMIN"));
			
			// 없음. 정의 필요함.
	    	codes.put(ITaskQueryService.AssignmentFilter.MY.name(), bundle.getString("DROPDOWN_CATEGORY_MY"));
	    	codes.put(ITaskQueryService.AssignmentFilter.GROUP.name(), bundle.getString("DROPDOWN_CATEGORY_GROUP"));
	    	codes.put(ITaskQueryService.AssignmentFilter.MY_AND_GROUP.name(), bundle.getString("DROPDOWN_CATEGORY_MY_AND_GROUP"));
	    	codes.put(ITaskQueryService.AssignmentFilter.REPORTEES.name(), bundle.getString("DROPDOWN_CATEGORY_REPORTEES"));
	    	codes.put(ITaskQueryService.AssignmentFilter.OWNER.name(), bundle.getString("DROPDOWN_CATEGORY_OWNER"));
	    	codes.put(ITaskQueryService.AssignmentFilter.CREATOR.name(), bundle.getString("DROPDOWN_CATEGORY_CREATOR"));
	    	codes.put(ITaskQueryService.AssignmentFilter.PREVIOUS.name(), bundle.getString("DROPDOWN_CATEGORY_PREVIOUS"));
		} else if(codeName.equals(TableConstants.WFTASK_PRIORITY_COLUMN.getName())) {
			// 없음. 정의 필요함.
			codes.put(IWorkflowConstants.TASK_PRIORITY_ANY, bundle.getString("DROPDOWN_PRIORITY_ANY"));
	    	codes.put(IWorkflowConstants.TASK_PRIORITY_ONE, bundle.getString("DROPDOWN_PRIORITY_ONE"));
	    	codes.put(IWorkflowConstants.TASK_PRIORITY_TWO, bundle.getString("DROPDOWN_PRIORITY_TWO"));
	    	codes.put(IWorkflowConstants.TASK_PRIORITY_THREE, bundle.getString("DROPDOWN_PRIORITY_THREE"));
	    	codes.put(IWorkflowConstants.TASK_PRIORITY_FOUR, bundle.getString("DROPDOWN_PRIORITY_FOUR"));
	    	codes.put(IWorkflowConstants.TASK_PRIORITY_FIVE, bundle.getString("DROPDOWN_PRIORITY_FIVE"));
		} else if(codeName.equals(TableConstants.WFTASK_STATE_COLUMN.getName())) {
	    	codes.put(IWorkflowConstants.TASK_STATE_ASSIGNED, bundle.getString("LABEL_TASK_STATE_ASSIGNED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_COMPLETED, bundle.getString("LABEL_TASK_STATE_COMPLETED"));
	    	if (isAdmin)
	    		codes.put(IWorkflowConstants.TASK_STATE_ALERTED, bundle.getString("LABEL_TASK_STATE_ALERTED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_SUSPENDED, bundle.getString("LABEL_TASK_STATE_SUSPENDED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_WITHDRAWN, bundle.getString("LABEL_TASK_STATE_WITHDRAWN"));
	    	codes.put(IWorkflowConstants.TASK_STATE_EXPIRED, bundle.getString("LABEL_TASK_STATE_EXPIRED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_ERRORED, bundle.getString("LABEL_TASK_STATE_ERRORED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_INFO_REQUESTED, bundle.getString("LABEL_TASK_STATE_INFO_REQUESTED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_ALERTED, bundle.getString("LABEL_TASK_STATE_ALERTED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_STALE, bundle.getString("LABEL_TASK_STATE_STALE"));
	    	codes.put(IWorkflowConstants.TASK_STATE_OUTCOME_UPDATED, bundle.getString("LABEL_TASK_STATE_OUTCOME_UPDATED"));
	    	codes.put(IWorkflowConstants.TASK_STATE_OUTCOME_UPDATED, bundle.getString("LABEL_TASK_STATE_DELETED"));	
		} else if(codeName.equals("instance_status")) {
	    	codes.put("0", "Initiated");
	    	codes.put("1", "Open(Running)");
	    	codes.put("2", "Open(Suspended)");
	    	codes.put("3", "Open(Faulted)");
	    	codes.put("4", "Closed(Pending Cancel)");
	    	codes.put("5", "Closed(Completed)");
	    	codes.put("6", "Closed(Faulted)");
	    	codes.put("7", "Closed(Cancelled)");
	    	codes.put("8", "Closed(Aborted)");
	    	codes.put("9", "Closed(Stale)");
		} else if(codeName.equals("activity_status")) {
	    	codes.put("0", "Inactive");
	    	codes.put("1", "Open(Running)");
	    	codes.put("2", "Open(Suspended)");
	    	codes.put("3", "Open(Pending Completed)");
	    	codes.put("4", "Open(Faulted)");
	    	codes.put("5", "Closed(Completed)");
	    	codes.put("6", "Closed(Finalized)");
	    	codes.put("7", "Closed(Pending Cancel)");
	    	codes.put("8", "Closed(Cancelled)");
	    	codes.put("9", "Closed(Faulted)");
	    	codes.put("10", "Closed(Aborted)");
	    	codes.put("11", "Closed(Compensated)");
	    	codes.put("12", "Closed(Stale)");
		} else if(codeName.equals("identity_role_type")) {
	    	codes.put(RoleClassifier.ANY_ROLE_TYPE, "All");
	    	codes.put(RoleClassifier.APPLICATION_ROLE_TYPE, "Application");
	    	//codes.put(RoleClassifier.ENTEPRISE_ROLE_TYPE, "Enterprise");
		}
		
		return codes;
	}
  	/*public static PROCESSDOC getJaxbPayload(Element payload) throws Exception {
  		
  		ObjectFactory factory=new ObjectFactory();
  		PROCESSDOC jbProcessDoc = factory.createPROCESSDOC();
  		
		
		
  		NodeList processDoc = payload.getElementsByTagName("PROCESS_DOC");
  		
  		if(processDoc != null) {
	    	 NodeList processDocChilds = processDoc.item(0).getChildNodes();
	    	 
	    	 for(int a=0; a<processDocChilds.getLength(); a++) {
	    		 if(processDocChilds.item(a).getNodeType() == Node.ELEMENT_NODE) {
	    			 if(processDocChilds.item(a).getNodeName().equals("PROCESS_NAME")) 
	    				 jbProcessDoc.setPROCESSNAME(processDocChilds.item(a).getTextContent());
	    			 
	    			 else if(processDocChilds.item(a).getNodeName().equals("START_TIME")) {
	    				 System.out.println("START_TIME : " + processDocChilds.item(a).getTextContent());
	    				 jbProcessDoc.setSTARTTIME(processDocChilds.item(a).getTextContent());
	    			 }
	    			 else if(processDocChilds.item(a).getNodeName().equals("END_TIME")) {
	    				 System.out.println("END_TIME : "+processDocChilds.item(a).getTextContent());
	    			 }
	    			 else if(processDocChilds.item(a).getNodeName().equals("LEAD_TIME")) {
	    				 System.out.println("LEAD_TIME : " + processDocChilds.item(a).getTextContent());
	    			 }
	    			 else if(processDocChilds.item(a).getNodeName().equals("BUSINESS_DATA_DOC")) {
	    				 BUSINESSDATADOC jbBusinessDataDoc = factory.createBUSINESSDATADOC();
	    				 
	    				 Element businessDocEle = (Element) processDocChilds.item(a);
	    				 
	    				 NodeList datas = businessDocEle.getElementsByTagName("DATAS");
	    				 NodeList businessDataDocChilds = processDocChilds.item(a).getChildNodes();
	    				 
	    				 for(int b=0; b<businessDataDocChilds.getLength(); b++) {
	    					 
	    					 
	    					 
	    					 if(businessDataDocChilds.item(b).getNodeType() == Node.ELEMENT_NODE) {
	    						 if(businessDataDocChilds.item(b).getNodeName().equals("CORRELATION_ID"))
	    		    				 jbBusinessDataDoc.setCORRELATIONID(businessDataDocChilds.item(b).getTextContent());
	    		    			 else if(businessDataDocChilds.item(b).getNodeName().equals("EVENT_KEY"))
	    		    				 jbBusinessDataDoc.setEVENTKEY(businessDataDocChilds.item(b).getTextContent());
	    		    			 else if(businessDataDocChilds.item(b).getNodeName().equals("APPROVER"))
	    		    				 jbBusinessDataDoc.setAPPROVER(businessDataDocChilds.item(b).getTextContent());
	    		    			 else if(businessDataDocChilds.item(b).getNodeName().equals("NEXT_APPROVER"))
	    		    				 jbBusinessDataDoc.setNEXTAPPROVER(businessDataDocChilds.item(b).getTextContent());
	    					 }
	    					 
	    				 }
	    				 
	    				 if(datas.getLength() > 0) {
	    					 //System.out.println("===== START BUSINESS_DATA_DOC/DATAS ======");
	    					 
	    					 for(int c=0; c<datas.getLength(); c++) {
	    						 if(datas.item(c).getNodeType() == Node.ELEMENT_NODE) {
	    							 BUSINESSDATADOC.DATAS jbDatas = factory.createBUSINESSDATADOCDATAS();
		    						 
		    						 NodeList datasChilds = datas.item(c).getChildNodes();
		    						 
		    						 jbDatas.setName(datas.item(c).getAttributes().getNamedItem("name").getTextContent());
		    						 
		    						 for(int d=0; d<datasChilds.getLength(); d++) {
	    								 if(datasChilds.item(d).getNodeType() == Node.ELEMENT_NODE) {
	    									 //OUTCOME/HEADER/BODY
	    									 if(datasChilds.item(d).getNodeName().equals("OUTCOME"))
	    										 jbDatas.setOUTCOME(datasChilds.item(d).getTextContent());
	    									 else if(datasChilds.item(d).getNodeName().equals("HEADER")) {
	    										 BUSINESSDATADOC.DATAS.HEADER jbHeader = factory.createBUSINESSDATADOCDATASHEADER();
	    										 
	    										 Element headerEle = (Element) datasChilds.item(d);
	    										 NodeList h_datas = headerEle.getElementsByTagName("H_DATAS");
	    										 
	    										 if(h_datas.getLength() > 0) {
	    											 for(int e=0; e<h_datas.getLength(); e++) {
	    												 if(h_datas.item(e).getNodeType() == Node.ELEMENT_NODE) {
	    													 BUSINESSDATADOC.DATAS.HEADER.HDATAS jbHdatas = factory.createBUSINESSDATADOCDATASHEADERHDATAS();
		    												 // HDATAS 에 name 값 설정
		    												 jbHdatas.setName(h_datas.item(e).getAttributes().getNamedItem("name").getTextContent());
		    												 
		    												 NodeList hdatasChild = h_datas.item(e).getChildNodes();
		    												 
		    												 if(hdatasChild.getLength() > 0) {
		    													 for(int f=0; f<hdatasChild.getLength(); f++) {
		        													 if(hdatasChild.item(f).getNodeType() == Node.ELEMENT_NODE) {
		        														 BUSINESSDATADOC.DATAS.HEADER.HDATAS.HDATA jbHData = factory.createBUSINESSDATADOCDATASHEADERHDATASHDATA();
		        														 
		        														 jbHData.setName(hdatasChild.item(f).getAttributes().getNamedItem("name").getTextContent());
		        														 jbHData.setRequired(hdatasChild.item(f).getAttributes().getNamedItem("required").getTextContent());
		        														 jbHData.setType(hdatasChild.item(f).getAttributes().getNamedItem("type").getTextContent());
		        														 jbHData.setValue(hdatasChild.item(f).getTextContent());
		        														 
		        														 //System.out.println("#######jbHData["+f+"] : "+jbHData.getValue());
		        														// HDATAS 에 HDATA 객체 설정
		            													 jbHdatas.getHDATA().add(jbHData);
		        													 }
		        												 }
		    												 }
		    												 jbHeader.setHDATAS(jbHdatas);	// HEADER 에 HDATAS 설정
	    												 }
	    											 }
	    										 }
	    										 jbDatas.setHEADER(jbHeader);;	// DATAS 에 HEADER 설정
	    										 
	    									 } else if(datasChilds.item(d).getNodeName().equals("BODY")) {
	    										 BUSINESSDATADOC.DATAS.BODY jbBody = factory.createBUSINESSDATADOCDATASBODY();
	    										 
	    										//System.out.println("===== START BUSINESS_DATA_DOC/DATAS["+(c+1)+"]/BODY ======");
	    										 
	    										 //B_DETAIL_DATAS
	    										 Element bodyEle = (Element) datasChilds.item(d);
	    										 NodeList bdetailDatas = bodyEle.getElementsByTagName("B_DETAIL_DATAS");
	    										 
	    										 if(bdetailDatas.getLength() > 0) {
	    											 for(int g=0; g<bdetailDatas.getLength(); g++) {
	    												 if(bdetailDatas.item(g).getNodeType() == Node.ELEMENT_NODE) {
	    													 BUSINESSDATADOC.DATAS.BODY.BDETAILDATAS jbBdetailDatas= factory.createBUSINESSDATADOCDATASBODYBDETAILDATAS();
		    												 //B_DETAIL_DATA 의 name 속성에 값 설정
		    												 jbBdetailDatas.setName(bdetailDatas.item(g).getAttributes().getNamedItem("name").getTextContent());
		    												 
		    												 //B_DETAIL_DATA
		    												 NodeList bdetailDatasChilds = bdetailDatas.item(g).getChildNodes();
		    												 
		    												 if(bdetailDatasChilds.getLength() > 0) {
		    													 for(int h=0; h<bdetailDatasChilds.getLength(); h++) {
		        													 if(bdetailDatasChilds.item(h).getNodeType() == Node.ELEMENT_NODE) {
		        														 BUSINESSDATADOC.DATAS.BODY.BDETAILDATAS.BDETAILDATA jbBdetailData= factory.createBUSINESSDATADOCDATASBODYBDETAILDATASBDETAILDATA();
		        														 
		        														 jbBdetailData.setId(bdetailDatasChilds.item(h).getAttributes().getNamedItem("id").getTextContent());
		    	    													 jbBdetailData.setName(bdetailDatasChilds.item(h).getAttributes().getNamedItem("name").getTextContent());
		    	    													 jbBdetailData.setRequired(bdetailDatasChilds.item(h).getAttributes().getNamedItem("required").getTextContent());
		    	    													 jbBdetailData.setType(bdetailDatasChilds.item(h).getAttributes().getNamedItem("type").getTextContent());
		    	    													 jbBdetailData.setValue(bdetailDatasChilds.item(h).getTextContent());
		    	    												 
		    	    													 jbBdetailDatas.getBDETAILDATA().add(jbBdetailData);
		        													 }
		        												 }
		    												 }
		    												 jbBody.getBDETAILDATAS().add(jbBdetailDatas);
		    												 //System.out.println("===== END BUSINESS_DATA_DOC/DATAS["+(c+1)+"]/BODY/B_DETAIL_DATAS[@name='"+bdetailDatas.item(g).getAttributes().getNamedItem("name").getTextContent()+"'] ======");
	    												 }
	    											 }
	    										 }
	    										 
	    										 //B_ARRAY_DATA
	    										 NodeList barrayDatas = bodyEle.getElementsByTagName("B_ARRAY_DATA");
	    										 if(barrayDatas.getLength() > 0) {
	    											 
	    											 for(int i=0; i<barrayDatas.getLength(); i++) {
	    												 if(barrayDatas.item(i).getNodeType() == Node.ELEMENT_NODE) {
	    													 BUSINESSDATADOC.DATAS.BODY.BARRAYDATA jbBarrayData = factory.createBUSINESSDATADOCDATASBODYBARRAYDATA();
		    												 jbBarrayData.setName(barrayDatas.item(i).getAttributes().getNamedItem("name").getTextContent());
		    												 NodeList barrayDatasChilds = barrayDatas.item(i).getChildNodes();
		    												 
		    												 if(barrayDatasChilds.getLength() > 0) {
		    													 for(int j=0; j<barrayDatasChilds.getLength(); j++) {
		        													 if(barrayDatasChilds.item(j).getNodeType() == Node.ELEMENT_NODE) {
		        														 if(barrayDatasChilds.item(j).getNodeName().equals("THEAD")) {
		        															 BUSINESSDATADOC.DATAS.BODY.BARRAYDATA.THEAD jbThead = factory.createBUSINESSDATADOCDATASBODYBARRAYDATATHEAD();
		        															 
		        															 NodeList theadChild = barrayDatasChilds.item(j).getChildNodes();
		        															 
		        															 if(theadChild.getLength()>0) {
		        																 for(int k=0; k<theadChild.getLength(); k++) {
		        																	 if(theadChild.item(k).getNodeType() == Node.ELEMENT_NODE)
		        																		 jbThead.getCOLS().add(theadChild.item(k).getTextContent());	// THEAD/COLS 에 값을 설정.
		        																 }
		        															 }
		        															 
		        															 // B_ARRAY_DATA 에 THEAD 설정
		        															 jbBarrayData.setTHEAD(jbThead);
		        															 
		        														 } else  if(barrayDatasChilds.item(j).getNodeName().equals("TBODY")) {
		        															 BUSINESSDATADOC.DATAS.BODY.BARRAYDATA.TBODY jbTbody = factory.createBUSINESSDATADOCDATASBODYBARRAYDATATBODY();
		        															 
		        															 Element tbodyEle = (Element) barrayDatasChilds.item(j);
		        															 
		        															 NodeList rows = tbodyEle.getElementsByTagName("ROWS");
		        															 
		        															 if(rows.getLength()>0) {
		        																 for(int l=0; l<rows.getLength(); l++) {
		        																	 if(rows.item(l).getNodeType() == Node.ELEMENT_NODE) {
		        																		 BUSINESSDATADOC.DATAS.BODY.BARRAYDATA.TBODY.ROWS jbRows= factory.createBUSINESSDATADOCDATASBODYBARRAYDATATBODYROWS();
			        																	 
		        																		 jbRows.setId(rows.item(l).getAttributes().getNamedItem("id").getTextContent());
		        																		 NodeList rowsChilds = rows.item(l).getChildNodes();
			        																	 //R_DATA
			        																	 if(rowsChilds.getLength()>0) {
			        																		 for(int m=0; m<rowsChilds.getLength(); m++) {
			        																			 if(rowsChilds.item(m).getNodeType() == Node.ELEMENT_NODE) {
			        																				 BUSINESSDATADOC.DATAS.BODY.BARRAYDATA.TBODY.ROWS.RDATA jbRdata = factory.createBUSINESSDATADOCDATASBODYBARRAYDATATBODYROWSRDATA();
			        																				 jbRdata.setCol(rowsChilds.item(m).getAttributes().getNamedItem("col").getTextContent());
			        																				 jbRdata.setValue(rowsChilds.item(m).getTextContent());
			        																				 
			        																				 //ROWS 에 R_DATA 설정
			        																				 jbRows.getRDATA().add(jbRdata);
			        																			 }
			        																		 }
			        																	 }
			        																	// TBODY 에 ROWS 설정
			            																 jbTbody.getROWS().add(jbRows);
		        																	 }
		        																 }
		        															 }
		        															 // B_ARRAY_DATA 에 TBODY 설정
		        															 jbBarrayData.setTBODY(jbTbody);
		        														 }
		        													 }
		        												 }
		    												 }
		    												 jbBody.getBARRAYDATA().add(jbBarrayData);
		    												 //System.out.println("===== END BUSINESS_DATA_DOC/DATAS["+(c+1)+"]/BODY/B_ARRAY_DATAS[@name='"+barrayDatas.item(i).getAttributes().getNamedItem("name").getTextContent()+"'] ======");
	    												 }
	    											 }
	    										 }
	    										 
	    										 // DATAS에 BODY 설정
	    										 jbDatas.setBODY(jbBody);
	    										 //System.out.println("===== END BUSINESS_DATA_DOC/DATAS["+(c+1)+"]/BODY ======");
	    									 }
	    								 }
		    						 }
		    						 
		    						 //BUSINESSDATADOC 에 DATAS 객체를 추가한다. (여러개 일 수 있음)
		    						 jbBusinessDataDoc.getDATAS().add(jbDatas);
	    						 }
	    					 }
	    				 }
	    				 jbProcessDoc.setBUSINESSDATADOC(jbBusinessDataDoc);
	    			 }
	    		 }
	    	 }
  		}
  		
  		return jbProcessDoc;
  	}
  	
  	public static Element getPayloadFromJaxb( Map<String, String> params) {
  		Element businessDataDoc = null;
  		
  		ObjectFactory factory=new ObjectFactory();
  		
  		BUSINESSDATADOC businessdataDoc = factory.createBUSINESSDATADOC();
  		BUSINESSDATADOC.DATAS datas = factory.createBUSINESSDATADOCDATAS();
  		
  		BUSINESSDATADOC.DATAS.BODY body = factory.createBUSINESSDATADOCDATASBODY();
  		BUSINESSDATADOC.DATAS.BODY.BDETAILDATAS bdetaildatas = factory.createBUSINESSDATADOCDATASBODYBDETAILDATAS();
  		
  		BUSINESSDATADOC.DATAS.BODY.BARRAYDATA barraydata = factory.createBUSINESSDATADOCDATASBODYBARRAYDATA();
  		
  		//businessdataDoc.setAPPROVER("approver1");
  		//businessdataDoc.setEVENTKEY("eventkey1");
  		
  		bdetaildatas.setName("");
  		
  		Iterator i = params.keySet().iterator();
		while(i.hasNext()) {
			
			String key = (String) i.next();
			String value = (String) params.get(key);
			
			//Body Detail Data
			if(key.startsWith("bd") && !value.equals("")) {
				BUSINESSDATADOC.DATAS.BODY.BDETAILDATAS.BDETAILDATA bdetaildata = factory.createBUSINESSDATADOCDATASBODYBDETAILDATASBDETAILDATA();
				
				bdetaildata.setId("");
				bdetaildata.setRequired("false");
				bdetaildata.setType("input");
				bdetaildata.setName(key);
		  		bdetaildata.setValue(value);
		  		bdetaildatas.getBDETAILDATA().add(bdetaildata);
			}
		}
		
  		body.getBDETAILDATAS().add(bdetaildatas);
  		body.getBARRAYDATA().add(barraydata);
  		datas.setName("");
  		datas.setBODY(body);
  		
  		
  		businessdataDoc.getDATAS().add(datas);
  		
  		try {
  			JAXBContext jaxbContext = JAXBContext.newInstance("egovframework.oracle.bpm.ps6.client.vo.payload");
  			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
  			
  			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
  	        Document document = docBuilder.newDocument();
  	        
  			// output pretty printed
  			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
  	 
  			jaxbMarshaller.marshal(businessdataDoc, document);
  			
  			businessDataDoc = document.getDocumentElement();
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		
  		return businessDataDoc;
  		
  	}*/
  	
  	public static void setPayloadValue(Element payload,String xpath,Map namespacemap,String value) {
        try {
            XPathUtility.setNodeValue(payload, namespacemap, xpath,value);
        } catch (Exception e) {
        }
    }
  	
  	public static void setPayload(Element payload,String xpath,Map<String, String> namespacemap, Node appendNode) {
        try {
        	
            XPathUtility.setNode(payload, namespacemap, xpath, appendNode);
        } catch (Exception e) {
        }
    }
  	
  	public static void updatePayloadContent(Element rsElement, String nodeName, String nodeText) {
        Document document = rsElement.getOwnerDocument();
        NodeList inputMessageNodeList = rsElement.getElementsByTagName(nodeName);
        Element messageNode = document.createElementNS("http://koemsoa.koem.or.kr/bpm/MasterTypes", "ns2:"+nodeName);
        messageNode.appendChild(document.createTextNode(nodeText.trim()));
        for(int nodeIdx = 0; nodeIdx < inputMessageNodeList.getLength(); nodeIdx++) {
            Node inputMessageNode = inputMessageNodeList.item(nodeIdx);
            Node parentNode = inputMessageNode.getParentNode();
            parentNode.removeChild(inputMessageNode);
            parentNode.appendChild(messageNode);
        }
	}
	
	public static void updateMessageAttributes(IWorkflowContext wfCtx, Task task, String attributeName, String attributeValue) throws Exception {
		Object[] args = new Object[] {attributeValue};
		//Class[] cls = new Class[] {String.class};
		//TableConstants.WFTASK_TEXTATTRIBUTE1_COLUMN.getName();
		
		SystemMessageAttributesType messageAttributesType= task.getSystemMessageAttributes();
		
		String methodName = "set"+attributeName.substring(0,1).toUpperCase() + attributeName.substring(1, attributeName.length());
		
		Method method = messageAttributesType.getClass().getMethod(methodName, new Class [] {String.class});
		
		method.invoke(messageAttributesType, args);
		
		//OracleConstants.BPEL.WORKFLOW_SERVICE_CLIENT.getTaskService().updateTask(wfCtx, task);
	}
	
  	public static void checkResourceBundle() {
		String prefBundle  = (String) OracleConstants.PREFERENCE.APPLICATION_DEFAULTS.get(OracleConstants.PARAM.APP_PREF.RESOURCE_BUNDLE);
		if (!bundleName.equals(prefBundle)) {
			//The application resource bundle has changed
	      
			//Check that we have a valid bundle
			try {
				ResourceBundle.getBundle(prefBundle);
				//prefBundle is valid, set the new resource bundle
				bundleName = prefBundle;
			} catch (Exception e) {
				//Failed to load the new bundle - continue to use the current bundle
				System.out.println("Failed to load new resource bundle: "+prefBundle+", reverting to previous bundle: "+bundleName);
				//Revert the preference value
				
				//ApplicationPrefsUtil.getPreferences().put(OracleConstants.PARAM.APP_PREF.RESOURCE_BUNDLE ,bundleName);
			}
		}
	}
  	
  	public static Predicate setPredicate(Predicate predicate, Column column, int operation, Object object) throws WorkflowException {
		return setPredicate(predicate, Predicate.AND, column, operation, object, false);
	}
	public static Predicate setPredicate(Predicate predicate, Column column, int operation, Object object, boolean ignoreCase) throws WorkflowException {
		return setPredicate(predicate, Predicate.AND, column, operation, object, ignoreCase);
	}
	public static Predicate setPredicate(Predicate predicate, int logical, Column column, int operation, Object object) throws WorkflowException {
		return setPredicate(predicate, logical, column, operation, object, false);
	}
	public static Predicate setPredicate(Predicate predicate, int logical, Column column, int operation, Object object, boolean ignoreCase) throws WorkflowException {
		if(predicate == null)
			predicate = (object instanceof Column) ? new Predicate(column, operation, (Column) object) : new Predicate(column, operation, object, ignoreCase);
		else
			if(object instanceof Column)
				predicate.addClause(logical, column, operation, (Column) object);
			else
				predicate.addClause(logical, column, operation, object, ignoreCase);
		
		return predicate;
	}
	
	public static String getTaskOutcome(IBPMContext ctx, int tasknumber) throws Exception {
		SOAServiceClient soaClient = (SOAServiceClient) getBean("soaClient");
		
		Task task = soaClient.getWorkflowServiceClient().getTaskQueryService().getTaskDetailsByNumber(ctx, tasknumber);
		
		return getTaskOutcomeKo((task.getSystemAttributes().getOutcome() == null ? "" : task.getSystemAttributes().getOutcome()));
	}
	
	public static String getOnlyDateString(Calendar calDate, Locale userLocale, TimeZone timeZone) {
		if (calDate == null)
			return "";
		else {
			getDateFormatInstance(userLocale);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return getTimeZoneBasedDateString(df, calDate, timeZone);
		}
	}	
	
	public static String getDateString(Date date, Locale userLocale, TimeZone timeZone) {
		if (date == null)
			return "";
		else {
			DateFormat df = getDateFormatInstance(userLocale);
			return getTimeZoneBasedDateString(df, date, timeZone);
		}
	}
	
	public static Calendar parseDateString(String dateString, Locale userLocale, TimeZone timeZone) {
		Calendar calDate = null;
		if ( dateString != null && !dateString.equals("")) {
			DateFormat df = getDateFormatInstance(userLocale);
			calDate = getTimeZoneBasedCalendar(df,dateString,userLocale,timeZone);
		}
		return calDate;
	}
	
	private static DateFormat getDateTimeFormatInstance(Locale userLocale) {
		String localeLanguage = userLocale.getLanguage();
		if (localeLanguage.equals(""))
			localeLanguage = "DEFAULT";

		return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, userLocale);
	}

	private static DateFormat getDateFormatInstance(Locale userLocale) {
		String localeLanguage = userLocale.getLanguage();
		if(localeLanguage.equals(""))
			localeLanguage = "DEFAULT";
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm"); //DateFormat.getDateInstance(DateFormat.MEDIUM, userLocale);
	}

	public static String getTimeZoneBasedDateString(DateFormat df, Calendar calDate, TimeZone timeZone) {
		String formattedDate = "";
		if(calDate != null) {
			// get existing timezone
		    TimeZone defaultTimeZone = df.getTimeZone();
		    if(timeZone != null)
		    	df.setTimeZone(timeZone);

		    formattedDate = df.format(calDate.getTime());
		    
		    // reset to existing timezone
		    df.setTimeZone(defaultTimeZone);
		} else {
			formattedDate = "";
		}
	    
	    return formattedDate;
	}    

	public static String getTimeZoneBasedDateString(DateFormat df, Date date, TimeZone timeZone) {
	    // get existing timezone
	    TimeZone defaultTimeZone = df.getTimeZone();
	    if(timeZone != null)
	    	df.setTimeZone(timeZone);

	    String formattedDate = df.format(date);
	    
	    // reset to existing timezone
	    df.setTimeZone(defaultTimeZone);

	    return formattedDate;
	}    
	private static Calendar getTimeZoneBasedCalendar(DateFormat df, String dateString, Locale userLocale, TimeZone timeZone) {
		// get existing timezone
		TimeZone defaultTimeZone = df.getTimeZone();
		if (timeZone == null)
			timeZone = defaultTimeZone;

		df.setTimeZone(timeZone);

		Calendar calendar = Calendar.getInstance(timeZone,userLocale);
		try {
			calendar.setTime(df.parse(dateString));
		} catch (ParseException e) {
			//Throw appropriate Exception
			//Error message includes sample date in correct format
			String sample = df.format(calendar.getTime());
			throw new IllegalArgumentException("\"" + dateString + "\" " + getMessage("MESSAGE_INVALID_DATE_FORMAT",userLocale)+sample);
		}

		// reset to existing timezone
		df.setTimeZone(defaultTimeZone);
		return calendar;
	}    

	public static String getMessage(String key) {
		return getMessage(key, null);
	}

	public static String getMessage(String key, Locale locale) {
		checkResourceBundle();
		ResourceBundle bundle = (locale == null) ? ResourceBundle.getBundle(OracleConstants.APPLICATION.RESOURCE_BUNDLE) : ResourceBundle.getBundle(OracleConstants.APPLICATION.RESOURCE_BUNDLE, locale);
		return bundle.getString(key);
	}
	
	public static Ordering createTaskOrdering(String sortField, String sortOrder) throws Exception {
		//Create the column
		Column column = Column.getColumn(sortField);
		//Default to ascending
		boolean isAscending = !(OracleConstants.CODE.SORT_ORDER.DESCENDING.equals(sortOrder));
		//Nulls last...
		boolean isNullFirst = false;
		return new Ordering(column,isAscending,isNullFirst);
	}
	
	// for BGF PoC
	public static String getTaskOutcomeKo(String outcome) {
		String outcomeko = "";
		
		if(outcome.equals("APPROVE"))
			outcomeko = "승인";
		else if(outcome.equals("REJECT"))
			outcomeko = "반려";
		else if(outcome.equals("SUBMIT"))
			outcomeko = "제출";
		else if(outcome.equals("OK"))
			outcomeko = "확인";
		else if(outcome.equals("CONFIRM"))
			outcomeko = "검증";
		else if(outcome.equals("REQUEST"))
			outcomeko = "요청";
		else if(outcome.equals("YES"))
			outcomeko = "적합";
		else if(outcome.equals("NO"))
			outcomeko = "비적합";
		else
			outcomeko = outcome;
		
		return outcomeko;
			
	}
	
	public static String DelayCalc(String dueDate){
		String result = "";
		String currDate = "";
		Calendar cal = new GregorianCalendar();
		Date date = cal.getTime();	
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmm");
		currDate = simple.format(date);

	    dueDate = dueDate.replaceAll("-", "").replaceAll(" ","").replaceAll(":", "");
	    
		SimpleDateFormat simple2 = new SimpleDateFormat("yyyyMMddHHmm");

		try {
			Date dueDateFormat = simple2.parse(dueDate);
			dueDate = simple2.format(dueDateFormat);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
	    System.out.println("현재시각:"+currDate);
	    System.out.println("duedate:"+dueDate);
	    
	   try {
	        SimpleDateFormat formatterDay = new SimpleDateFormat("yyyyMMddHHmm");
	        Date begin = formatterDay.parse(currDate);
	        Date end = formatterDay.parse(dueDate);
	         
	        long diff = end.getTime() - begin.getTime();
	        
	        long _day = 24*60*60*1000;
	        long _hour = 60*60*1000;
	        long _minute = 60*1000;
	        
	        long dayResult = diff/_day;
	        long DiffData = diff %_day;
	        long hourResult = DiffData/_hour;
	        DiffData = DiffData%_hour;
	        long minuteResult = DiffData/_minute;


	        if(dayResult<0)
	        	result += Long.toString(Math.abs(dayResult))+"일";
	        if(hourResult<0)
	        	result += Long.toString(Math.abs(hourResult))+"시간";
	        if(minuteResult<0)
	        	result += Long.toString(Math.abs(minuteResult))+"분";


	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	   
	   return result;
	}
}
