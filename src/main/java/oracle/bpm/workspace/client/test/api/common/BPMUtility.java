package oracle.bpm.workspace.client.test.api.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import oracle.bpel.services.workflow.IWorkflowConstants;
import oracle.bpel.services.workflow.WorkflowException;
import oracle.bpel.services.workflow.common.impl.CommonUtil;
import oracle.bpel.services.workflow.common.model.Participant;
import oracle.bpel.services.workflow.repos.Column;
import oracle.bpel.services.workflow.repos.Predicate;
import oracle.bpel.services.workflow.user.IUserMetadataService;
import oracle.bpel.services.workflow.user.model.UserPreference;
import oracle.bpel.services.workflow.user.model.UserPreferences;
import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.tip.pc.services.identity.BPMGroup;
import oracle.tip.pc.services.identity.BPMUser;

public class BPMUtility {

	public BPMUtility() {
		// TODO Auto-generated constructor stub
	}
	
	public static IWorkflowContext getContext(HttpServletRequest req) throws WorkflowException, Exception {
		IWorkflowContext workflowContext = null;

		try {
			SOAServiceClient soaClient = new SOAServiceClient();
			workflowContext = soaClient.getWorkflowServiceClient().getTaskQueryService().createContext(req);
		} catch (Exception e) {
			throw e;
		}
		return workflowContext;
	}
	
	public static IWorkflowContext getContext(String account_id, String account_passwd) throws WorkflowException, Exception {
		IWorkflowContext workflowContext = null;

		try {
			SOAServiceClient soaClient = new SOAServiceClient();
			workflowContext = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate(account_id, account_passwd.toCharArray(), SOAServiceClient.domainIdentity);
		
		} catch (Exception e) {
			throw e;
		}
		return workflowContext;
	}
	
    public static Map<String, Object> getBPMUserInfo(IWorkflowContext wfCtx) throws Exception {
    	Map<String, Object> bpm_user_info = new HashMap<String, Object>(); 

    	SOAServiceClient soaClient = new SOAServiceClient();
    	
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
  		SOAServiceClient soaClient = new SOAServiceClient();
  		
  		Participant user = CommonUtil.getFactory().createParticipant();
        user.setName(wfCtx.getUser());
		user.setRealm(wfCtx.getIdentityContext());
		user.setType(IWorkflowConstants.PARTICIPANT_TYPE_USER);
        
		IUserMetadataService ums = soaClient.getWorkflowServiceClient().getUserMetadataService();
		UserPreferences userPreferences = ums.getUserPreferences(wfCtx, user);
		
		Map<String, Object> userPrefMap = new HashMap<String, Object>();
		
		//Otherwise, load user preferences from back-end
    	Iterator preferences = userPreferences.getUserPreference().iterator();
    	while ( preferences.hasNext() ) {
    		UserPreference pref = (UserPreference)preferences.next();
    		String prefName = pref.getName();
    		String prefValue = pref.getValue();
    		//Use default value if no value specified
    		userPrefMap.put(prefName,prefValue);
    	}
	    	
  		return userPrefMap;
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
			throw new IllegalArgumentException("\"" + dateString + "\" " + sample);
		}

		// reset to existing timezone
		df.setTimeZone(defaultTimeZone);
		return calendar;
	}    

}
