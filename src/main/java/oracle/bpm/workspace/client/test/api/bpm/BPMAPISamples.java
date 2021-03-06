package oracle.bpm.workspace.client.test.api.bpm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.IWorkflowConstants;
import oracle.bpel.services.workflow.query.ITaskQueryService;
import oracle.bpel.services.workflow.query.ITaskQueryService.OptionalInfo;
import oracle.bpel.services.workflow.query.model.TaskCountType;
import oracle.bpel.services.workflow.repos.Column;
import oracle.bpel.services.workflow.repos.Ordering;
import oracle.bpel.services.workflow.repos.Predicate;
import oracle.bpel.services.workflow.repos.TableConstants;
import oracle.bpel.services.workflow.repos.table.WFTaskConstants;
import oracle.bpel.services.workflow.task.model.IdentityType;
import oracle.bpel.services.workflow.task.model.ShortHistoryTaskType;
import oracle.bpel.services.workflow.task.model.Task;
import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.bpm.services.instancemanagement.model.IActivityInfo;
import oracle.bpm.services.instancemanagement.model.IFlowChangeItem;
import oracle.bpm.services.instancemanagement.model.IGrabInstanceContext;
import oracle.bpm.services.instancemanagement.model.IGrabInstanceContextRequest;
import oracle.bpm.services.instancemanagement.model.IGrabInstanceContextResponse;
import oracle.bpm.services.instancemanagement.model.IGrabInstanceRequest;
import oracle.bpm.services.instancemanagement.model.IGrabInstanceResponse;
import oracle.bpm.services.instancemanagement.model.IInstanceContextConfiguration;
import oracle.bpm.services.instancemanagement.model.IInstanceSummary;
import oracle.bpm.services.instancemanagement.model.IOpenActivityInfo;
import oracle.bpm.services.instancemanagement.model.IProcessInstance;
import oracle.bpm.services.instancemanagement.model.IVariableItem;
import oracle.bpm.services.instancemanagement.model.impl.alterflow.GrabInstanceContextRequest;
import oracle.bpm.services.instancemanagement.model.impl.alterflow.GrabInstanceRequest;
import oracle.bpm.services.instancemanagement.model.impl.alterflow.InstanceContextConfiguration;
import oracle.bpm.services.instancemanagement.model.impl.alterflow.LocationInfo;
import oracle.bpm.services.instancemanagement.model.impl.alterflow.VariableItem;
import oracle.bpm.services.instancequery.IColumnConstants;
import oracle.bpm.services.instancequery.IInstanceQueryInput;
import oracle.bpm.services.instancequery.IInstanceQueryService;
import oracle.bpm.services.instancequery.impl.InstanceQueryInput;
import oracle.bpm.workspace.client.constants.OracleConstants;
import oracle.bpm.workspace.client.test.api.common.BPMUtility;
import oracle.bpm.workspace.client.test.api.common.SOAServiceClient;
import oracle.bpm.workspace.client.util.WorkflowUtility;

public class BPMAPISamples {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public BPMAPISamples() {
		// TODO Auto-generated constructor stub
	}
	
	public void getQueryTasks(String title, String state, String overdue) throws Exception {
		
		int startRow = 0;
	    int endRow = 10;
	    
	    
	  //조회할 컬럼을 정의한다.
		List<String> inboxColumns = new ArrayList<String>();
		
		inboxColumns.add(TableConstants.WFTASK_APPLICATIONNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASK_TASKNUMBER_COLUMN.getName());	// Task Number
		inboxColumns.add(TableConstants.WFTASK_TITLE_COLUMN.getName());			// Task Title
		inboxColumns.add(TableConstants.WFTASK_ACQUIREDBY_COLUMN.getName());		// Task AcquiredBy ( 획득자 )
		inboxColumns.add(TableConstants.WFTASK_ASSIGNEEUSERS_COLUMN.getName());		// Task Assigneeusers (할당자)
		inboxColumns.add(TableConstants.WFTASK_STATE_COLUMN.getName());				// Task Status (ASSIGNED, COMPLETED...)
		inboxColumns.add(TableConstants.WFTASK_OUTCOME_COLUMN.getName());			// Task outcome (APPROVE,REJECT)
		inboxColumns.add(TableConstants.WFTASK_CREATEDDATE_COLUMN.getName());		// 생성일자
		inboxColumns.add(TableConstants.WFTASK_ENDDATE_COLUMN.getName());			// 완료일자
		inboxColumns.add(TableConstants.WFTASK_FROMUSER_COLUMN.getName());			// FromUser (위임자)
		inboxColumns.add(TableConstants.WFTASK_ACTIVITYNAME_COLUMN.getName());		// 액티비티
		inboxColumns.add(TableConstants.WFTASK_TASKGROUPID_COLUMN.getName());		// Task 유형이 Group Vote 일 경우 
		inboxColumns.add(TableConstants.WFTASK_TASKDEFINITIONNAME_COLUMN.getName());	// Task Definition 명
		inboxColumns.add(TableConstants.WFTASK_PROCESSID_COLUMN.getName()); 			// 프로세스 아이디
		inboxColumns.add(TableConstants.WFTASK_PROCESSNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASK_TEXTATTRIBUTE1_COLUMN.getName());		// DocType
		inboxColumns.add(TableConstants.WFTASK_TEXTATTRIBUTE2_COLUMN.getName());		// DocNum
		inboxColumns.add(TableConstants.WFTASK_DATEATTRIBUTE1_COLUMN.getName());		// Drafting Date
		
		
		// 화면상에 테이블 컬럼명을 설정한다. (JSP에서 동적으로 컬럼을 설정하기 위함이며, 선택사항임.)
		List<String> inboxColumnLabels = new ArrayList<String>();
		inboxColumnLabels.add("Title");
		inboxColumnLabels.add("Assign Users");
		inboxColumnLabels.add("State");
		inboxColumnLabels.add("Start Date");
		inboxColumnLabels.add("End Date");
		inboxColumnLabels.add("Lead Time");
		
		// 화면에서 호출할 액션 리스트를 정의한다.
		List<OptionalInfo> optionalInfo = new ArrayList<OptionalInfo>();
		optionalInfo.add(ITaskQueryService.OptionalInfo.ALL_ACTIONS); // actions do not needed for listing page
		
		// 검색 조건을 저장한다.
		Predicate predicate = null;
		// 값 안나옴, 검토필요
		//Map<String, String> mapState = WorkflowUtility.getFilterCodeList(TableConstants.WFTASK_STATE_COLUMN.getName(), wfCtx.getIsAdmin(), wfCtx.getLocale());
		
		
		// 상태 조회 // ASSIGNED(진행중), COMPLETED (완료), state=ASSIGNED, state=COMPLETED (assignmentFilter = PREVIOUS)
		predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_STATE_COLUMN, Predicate.OP_EQ, state);
		
		if(title != null)
			predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_TITLE_COLUMN, Predicate.OP_LIKE, "%"+state+"%", true);
		
		if(overdue != null) {
			Calendar curDate = Calendar.getInstance();

	    	logger.debug("curDate.getTime() : " + curDate.getTime());
	    	predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_DATEATTRIBUTE2_COLUMN, Predicate.OP_GTE, curDate.getTime());
		}
		
        Ordering ordering = null;
        
        ordering = WorkflowUtility.createTaskOrdering(TableConstants.WFTASK_CREATEDDATE_COLUMN.getName(), OracleConstants.CODE.SORT_ORDER.DESCENDING);
        
        ITaskQueryService.AssignmentFilter defaultAssignmentFilter = null;
        
        if(state.equals("ASSIGNED"))
        	defaultAssignmentFilter = ITaskQueryService.AssignmentFilter.MY_AND_GROUP;//"My+Group";
        else
        	defaultAssignmentFilter = ITaskQueryService.AssignmentFilter.PREVIOUS;
        
        //logger.debug("Worklist where string : "+ predicate.getString());
        
        SOAServiceClient soaClient = new SOAServiceClient();
        IWorkflowContext wfCtx = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate("sapuserA", "welcome1".toCharArray(), null);
        
        List<Task> tasks = soaClient.getTaskQueryService().queryTasks(
				wfCtx
		        ,inboxColumns
		        ,optionalInfo
		        ,defaultAssignmentFilter
		        ,null
		        ,predicate // predicate
		        ,ordering
		        ,startRow
		        ,endRow);
        
        
        logger.debug("worklist task size : "+ tasks.size());
        
	}
	
	public void getDelayQueryTasks(String title, String state, String overdue) throws Exception {
		int startRow = 0;
	    int endRow = 30;
	    
	    
	  //조회할 컬럼을 정의한다.
		List<String> inboxColumns = new ArrayList<String>();
		
		inboxColumns.add(TableConstants.WFTASK_APPLICATIONNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASK_TASKNUMBER_COLUMN.getName());	// Task Number
		inboxColumns.add(TableConstants.WFTASK_TITLE_COLUMN.getName());			// Task Title
		inboxColumns.add(TableConstants.WFTASK_ACQUIREDBY_COLUMN.getName());		// Task AcquiredBy ( 획득자 )
		inboxColumns.add(TableConstants.WFTASK_ASSIGNEEUSERS_COLUMN.getName());		// Task Assigneeusers (할당자)
		inboxColumns.add(TableConstants.WFTASK_ASSIGNEESDISPLAYNAME_COLUMN.getName());		// Task Assigneeusers (할당자)
		inboxColumns.add(TableConstants.WFTASK_APPROVERS_COLUMN.getName());		// Task Assigneeusers (할당자)
		inboxColumns.add(TableConstants.WFTASK_STATE_COLUMN.getName());				// Task Status (ASSIGNED, COMPLETED...)
		inboxColumns.add(TableConstants.WFTASK_OUTCOME_COLUMN.getName());			// Task outcome (APPROVE,REJECT)
		inboxColumns.add(TableConstants.WFTASK_CREATEDDATE_COLUMN.getName());		// 생성일자
		inboxColumns.add(TableConstants.WFTASK_ENDDATE_COLUMN.getName());			// 완료일자
		inboxColumns.add(TableConstants.WFTASK_FROMUSER_COLUMN.getName());			// FromUser (위임자)
		inboxColumns.add(TableConstants.WFTASK_ACTIVITYNAME_COLUMN.getName());		// 액티비티
		inboxColumns.add(TableConstants.WFTASK_TASKGROUPID_COLUMN.getName());		// Task 유형이 Group Vote 일 경우 
		inboxColumns.add(TableConstants.WFTASK_TASKDEFINITIONNAME_COLUMN.getName());	// Task Definition 명
		inboxColumns.add(TableConstants.WFTASK_PROCESSID_COLUMN.getName()); 			// 프로세스 아이디
		inboxColumns.add(TableConstants.WFTASK_PROCESSNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASK_TEXTATTRIBUTE1_COLUMN.getName());		// DocType
		inboxColumns.add(TableConstants.WFTASK_TEXTATTRIBUTE2_COLUMN.getName());		// DocNum
		inboxColumns.add(TableConstants.WFTASK_DATEATTRIBUTE1_COLUMN.getName());		// Drafting Date
		inboxColumns.add(TableConstants.WFTASK_DATEATTRIBUTE2_COLUMN.getName());		// Drafting Date
		inboxColumns.add(TableConstants.WFTASK_APPROVALDURATION_COLUMN.getName());		// Drafting Date
		
		// 화면상에 테이블 컬럼명을 설정한다. (JSP에서 동적으로 컬럼을 설정하기 위함이며, 선택사항임.)
		List<String> inboxColumnLabels = new ArrayList<String>();
		inboxColumnLabels.add("Title");
		inboxColumnLabels.add("Assign Users");
		inboxColumnLabels.add("State");
		inboxColumnLabels.add("Start Date");
		inboxColumnLabels.add("End Date");
		inboxColumnLabels.add("Lead Time");
		
		// 화면에서 호출할 액션 리스트를 정의한다.
		/*List<OptionalInfo> optionalInfo = new ArrayList<OptionalInfo>();
		optionalInfo.add(ITaskQueryService.OptionalInfo.ALL_ACTIONS); // actions do not needed for listing page
		*/
		// 검색 조건을 저장한다.
		Predicate predicate = null;
		// 값 안나옴, 검토필요
		//Map<String, String> mapState = WorkflowUtility.getFilterCodeList(TableConstants.WFTASK_STATE_COLUMN.getName(), wfCtx.getIsAdmin(), wfCtx.getLocale());
		
		
		// 상태 조회 // ASSIGNED(진행중), COMPLETED (완료), state=ASSIGNED, state=COMPLETED (assignmentFilter = PREVIOUS)
		
		predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_STATE_COLUMN, Predicate.OP_NEQ, IWorkflowConstants.TASK_STATE_STALE);
		
		if(!state.equals(""))
			predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_STATE_COLUMN, Predicate.OP_EQ, state);
		
		if(title != null)
			predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_TITLE_COLUMN, Predicate.OP_LIKE, "%"+title+"%", true);
		
		predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_DATEATTRIBUTE2_COLUMN, Predicate.OP_IS_NOT_NULL,null);
		
		predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_UPDATEDBY_COLUMN, Predicate.OP_NEQ, "workflowsystem");
		
		if(overdue != null) {
			if(state.equals("COMPLETED")) {
				predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_DATEATTRIBUTE2_COLUMN, Predicate.OP_LTE, TableConstants.WFTASK_ENDDATE_COLUMN);
			} else {	// ASSIGNED
				Calendar curDate = Calendar.getInstance();

		    	logger.debug("curDate.getTime() : " + curDate.getTime());
		    	predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_DATEATTRIBUTE2_COLUMN, Predicate.OP_LTE, curDate.getTime());
			}
			
		}
		
        Ordering ordering = null;
        
        ordering = WorkflowUtility.createTaskOrdering(TableConstants.WFTASK_DATEATTRIBUTE2_COLUMN.getName(), OracleConstants.CODE.SORT_ORDER.ASCENDING);
        
        ITaskQueryService.AssignmentFilter defaultAssignmentFilter = null;
        
        defaultAssignmentFilter = ITaskQueryService.AssignmentFilter.ALL;
        
        logger.debug("defaultAssignmentFilter : "+ defaultAssignmentFilter.toString());
        logger.debug("predicate : "+ predicate.toString());
        
        SOAServiceClient soaClient = new SOAServiceClient();
        IWorkflowContext wfCtx = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate("weblogic", "welcome1".toCharArray(), null);
        
        List<Task> tasks = soaClient.getTaskQueryService().queryTasks(
				wfCtx
		        ,inboxColumns
		        ,null
		        ,defaultAssignmentFilter
		        ,null
		        ,predicate // predicate
		        ,ordering
		        ,startRow
		        ,endRow);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        for(Task task : tasks) {
        	if(task.getSystemAttributes().getState().equals("COMPLETED")) {	//완료
        		logger.debug("assigneeUser1 : " + task.getSystemAttributes().getApproversDisplayNames());
        		logger.debug("assigneeUser1-1 : " + task.getSystemAttributes().getApprovers());
        	}
        	else
        		logger.debug("assigneeUser2 : " + WorkflowUtility.getAssigneeDisplayNameString(task));
        		
        	logger.debug("taskId : " + task.getSystemAttributes().getTaskId());
        	logger.debug("getState : " + task.getSystemAttributes().getState());
        	logger.debug("getCreatedDate : " + task.getSystemAttributes().getCreatedDate());
        	logger.debug("duedate : " + task.getSystemMessageAttributes().getDateAttribute2());
        	
        	long approvalDuration = 0;
        		approvalDuration = (task.getSystemAttributes().getApprovalDuration() / 1000); //초;
        	//task.getSystemAttributes().get
        	System.out.println("getAssignedDate : " + task.getSystemAttributes().getAssignedDate());
        	System.out.println("getEndDate : " + task.getSystemAttributes().getEndDate());
        	if(approvalDuration == 0)
        		approvalDuration = ((task.getSystemAttributes().getEndDate().getTimeInMillis()/1000) - (task.getSystemAttributes().getAssignedDate().getTimeInMillis()/1000));
        	
        	
        	logger.debug("delaytime : " + WorkflowUtility.DelayCalc(WorkflowUtility.getTimeZoneBasedDateString(df, task.getSystemMessageAttributes().getDateAttribute2(), wfCtx.getTimeZone())));
        	//logger.debug("approvalduration(초) : " +((approvalDuration/3600) == 0 ? "" : (approvalDuration/3600)+ "시간 ")+((approvalDuration % 3600 / 60) == 0 ? "" : (approvalDuration % 3600 / 60)+"분 ")+((approvalDuration % 3600 % 60) == 0 ? "" : (approvalDuration % 3600 % 60)+" 초"));
        	logger.debug("approvalduration() : " +((approvalDuration/3600) == 0 ? "" : (approvalDuration/3600)+ "시간 ")+((approvalDuration % 3600 / 60) == 0 ? "" : (approvalDuration % 3600 / 60)+"분 "));
        	logger.debug("-------------------------------------------");
        }
        
        logger.debug("worklist task size : "+ tasks.size());
		
	}
	
	////202649
	public void queryShortTaskHistory(String tasknumber) throws Exception {
		SOAServiceClient soaClient = new SOAServiceClient();
        IWorkflowContext wfCtx = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate("weblogic", "welcome1".toCharArray(), null);
        
        Task task = soaClient.getWorkflowServiceClient().getTaskQueryService().getTaskDetailsByNumber(wfCtx, Integer.parseInt(tasknumber));
        
        System.out.println(task.getTitle());
        List<ShortHistoryTaskType> shortTaskHistorys = task.getSystemAttributes().getShortHistory().getTask();
        
        for(ShortHistoryTaskType shortTaskHistory : shortTaskHistorys) {
        	if(shortTaskHistory.getVersionReason() != null && shortTaskHistory.getVersionReason().equals(IWorkflowConstants.TASK_VERSION_REASON_OUTCOME_UPDATED) && !shortTaskHistory.getUpdatedBy().getId().equals("workflowsystem")) {
        		System.out.println("shortTaskHistory.getVersion() : " + shortTaskHistory.getVersion());
            	System.out.println("shortTaskHistory.getVersionReason() : " + shortTaskHistory.getVersionReason());
            	System.out.println("shortTaskHistory.getOutcome() : " + shortTaskHistory.getOutcome());
            	System.out.println("shortTaskHistory.getState() : " + shortTaskHistory.getState());
            	System.out.println("shortTaskHistory.getUpdatedBy() : " + shortTaskHistory.getUpdatedBy().getId());
            	System.out.println("shortTaskHistory.getUpdatedByName() : " + soaClient.getBPMIdentityService().lookupUser(shortTaskHistory.getUpdatedBy().getId()).getDisplayName());
            	System.out.println("shortTaskHistory.getUpdatedDate() : " + shortTaskHistory.getUpdatedDate());
            	System.out.println("-------------------------------------------------------");
        	}
        }
        
        
	}
	
	public void queryShortTaskHistory2(String tasknumber) throws Exception {
		SOAServiceClient soaClient = new SOAServiceClient();
        IWorkflowContext wfCtx = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate("weblogic", "welcome1".toCharArray(), null);
        
List<String> inboxColumns = new ArrayList<String>();
		
		inboxColumns.add(TableConstants.WFTASK_APPLICATIONNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASK_TASKNUMBER_COLUMN.getName());	// Task Number
		inboxColumns.add(TableConstants.WFTASK_TITLE_COLUMN.getName());			// Task Title
		inboxColumns.add(TableConstants.WFTASK_ACQUIREDBY_COLUMN.getName());		// Task AcquiredBy ( 획득자 )
		inboxColumns.add(TableConstants.WFTASK_ASSIGNEEUSERS_COLUMN.getName());		// Task Assigneeusers (할당자)
		inboxColumns.add(TableConstants.WFTASK_ASSIGNEESDISPLAYNAME_COLUMN.getName());		// Task Assigneeusers (할당자)
		inboxColumns.add(TableConstants.WFTASK_APPROVERS_COLUMN.getName());		// Task Assigneeusers (할당자)
		inboxColumns.add(TableConstants.WFTASK_STATE_COLUMN.getName());				// Task Status (ASSIGNED, COMPLETED...)
		inboxColumns.add(TableConstants.WFTASK_OUTCOME_COLUMN.getName());			// Task outcome (APPROVE,REJECT)
		inboxColumns.add(TableConstants.WFTASK_CREATEDDATE_COLUMN.getName());		// 생성일자
		inboxColumns.add(TableConstants.WFTASK_ENDDATE_COLUMN.getName());			// 완료일자
		inboxColumns.add(TableConstants.WFTASK_FROMUSER_COLUMN.getName());			// FromUser (위임자)
		inboxColumns.add(TableConstants.WFTASK_ACTIVITYNAME_COLUMN.getName());		// 액티비티
		inboxColumns.add(TableConstants.WFTASK_TASKGROUPID_COLUMN.getName());		// Task 유형이 Group Vote 일 경우 
		inboxColumns.add(TableConstants.WFTASK_TASKDEFINITIONNAME_COLUMN.getName());	// Task Definition 명
		inboxColumns.add(TableConstants.WFTASK_PROCESSID_COLUMN.getName()); 			// 프로세스 아이디
		inboxColumns.add(TableConstants.WFTASK_PROCESSNAME_COLUMN.getName());
		inboxColumns.add(TableConstants.WFTASK_TEXTATTRIBUTE1_COLUMN.getName());		// DocType
		inboxColumns.add(TableConstants.WFTASK_TEXTATTRIBUTE2_COLUMN.getName());		// DocNum
		inboxColumns.add(TableConstants.WFTASK_DATEATTRIBUTE1_COLUMN.getName());		// Drafting Date
		inboxColumns.add(TableConstants.WFTASK_DATEATTRIBUTE2_COLUMN.getName());		// Drafting Date
		inboxColumns.add(TableConstants.WFTASK_APPROVALDURATION_COLUMN.getName());		// Drafting Date
		
		
        Task task1 = soaClient.getTaskQueryService().getTaskDetailsByNumber(wfCtx, Integer.parseInt(tasknumber));
        List<Task> tasks = soaClient.getTaskQueryService().getTaskHistory(wfCtx, task1.getSystemAttributes().getTaskId(), inboxColumns);
        
        for(Task task : tasks) {
        	List assignees = task.getSystemAttributes().getAssigneeUsers();
			
        	System.out.println(assignees.size());
	        for(int i = 0 ; i < assignees.size() ; i++) {
	        	IdentityType type = (IdentityType)assignees.get(i);
	        	String name = type.getDisplayName();
	        	System.out.println(name);
	        }
        	System.out.println(task.getSystemAttributes().getOutcome());
        	System.out.println(task.getSystemAttributes().getState());
        }
        
	}
	
	public void getQueryInstances(String title, String state, String processName) throws Exception {
		
		IBPMContext bpmCtx = (IBPMContext) BPMUtility.getContext("weblogic", "welcome1");
		
		String cpage = "0";
		String rowSize = "20";
		
		int startRow = Integer.parseInt(cpage) * Integer.parseInt(rowSize) + 1;
	    int endRow = startRow + Integer.parseInt(rowSize) - 1;
	    
	    SOAServiceClient soaClient = new SOAServiceClient();
	    
	    //조회??컬럼???�의?�다.
		List<Column> displayColumns = new ArrayList<Column>();
		//displayColumns.add(IColumnConstants.PROCESS_NUMBER_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_INSTANCEID_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_COMPONENTNAME_COLUMN);

	    //displayColumns.add(IColumnConstants.PROCESS_COMPONENTTYPE_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_COMPOSITEDN_COLUMN);
	    displayColumns.add(IColumnConstants.PROCESS_COMPOSITEINSTANCEID_COLUMN);
		//displayColumns.add(IColumnConstants.PROCESS_ACTIVITYID_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_ACTIVITYNAME_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_ACTIVITYTYPE_COLUMN);
		//displayColumns.add(IColumnConstants.PROCESS_ID_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_PROCESSNAME_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_VERSION_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_STATE_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_TITLE_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_CREATOR_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_CREATEDDATE_COLUMN);
		//displayColumns.add(IColumnConstants.PROCESS_ASSIGNEE_TASKID_COLUMN);
		//displayColumns.add(IColumnConstants.PROCESS_DUEDATE_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_ENDDATE_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_UPDATEDDATE_COLUMN);
		
		/* other columns....
	      displayColumns.add(IColumnConstants.PROCESS_AGROOT_ID);
	      displayColumns.add(IColumnConstants.PROCESS_APPLICATIONCONTEXT_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_APPLICATIONNAME_COLUMN);
	      
	      displayColumns.add(IColumnConstants.PROCESS_ASSIGNEEATTRIBUTE_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_ASSIGNEES_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_ASSIGNEESDISPLAYNAME_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_ASSIGNEETYPEATTRIBUTE_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_ASSIGNMENTCONTEXT_COLUMN);
	      
	      displayColumns.add(IColumnConstants.PROCESS_COMPOSITENAME_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_COMPOSITEVERSION_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_CONVERSATIONID_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_DUEDATE_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_ENDDATE_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_EXPIRATIONDATE_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_EXPIRATIONDURATION_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_IDENTITYCONTEXT_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_INSTANCE_PRIORITY_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_ISGROUP_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_MAIN_THREAD_REAL_STATE);
	      displayColumns.add(IColumnConstants.PROCESS_NUMBER_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_NUMBEROFTIMESMODIFIED_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_OWNERGROUP_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_OWNERROLE_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_OWNERUSER_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_PARENTCOMPONENTINSTANCEID_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_PARENTID_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_PARENTVERSION_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_PARENTTHREAD_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_PREVIOUS_STATE_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_PROCESSDEFINITIONID_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_PROCESSDEFINITIONNAME_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_STEP_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_THREAD_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_UPDATEDBY_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_UPDATEDBYDISPLAYNAME_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_USER_COMMENTS);
	      displayColumns.add(IColumnConstants.PROCESS_VERSIONREASON_COLUMN);
	      */
		
		// ?�렬???�한 컬럼???�의
		Ordering ordering = new Ordering(IColumnConstants.PROCESS_NUMBER_COLUMN,false,true);  
		
		// 조건 ?�정
		Predicate pred = null;
		
		// BPMN�?조회
		pred = new Predicate(IColumnConstants.PROCESS_COMPONENTTYPE_COLUMN, Predicate.OP_EQ, "BPMN");
		
		if(state != null && !"".equals(state))
			pred.addClause(Predicate.AND, IColumnConstants.PROCESS_STATE_COLUMN, Predicate.OP_EQ, state);
		if(title != null && !"".equals(title))
			pred.addClause(Predicate.AND, IColumnConstants.PROCESS_TITLE_COLUMN, Predicate.OP_LIKE, "%"+title+"%");
		
		pred.addClause(Predicate.AND, IColumnConstants.PROCESS_COMPONENTTYPE_COLUMN, Predicate.OP_EQ, "BPMN");
		
		if(processName != null && !"".equals(processName))
			pred.addClause(Predicate.AND, IColumnConstants.PROCESS_COMPONENTNAME_COLUMN, Predicate.OP_EQ, processName);
		
		
		/*Predicate datePredicate = new Predicate(TableConstants.WFTASK_ENDDATE_COLUMN,
                Predicate.OP_ON,
                new Date());
		Predicate predicate = new Predicate(statePredicate, Predicate.AND, datePredicate);*/

		IInstanceQueryInput input = new InstanceQueryInput();
		
		input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.ALL);
		//input.setStartRow(startRow);
		//input.setEndRow(endRow);
		// state : OPEN, COMPLETED, ERRORED, CANCELED, ABORTED, STALE
		// title : %%
		// WHERE STATE = ? AND TITLE LIKE ? AND COMPONENT='BPMN' and assignmentFilter=ALL (모두)
                
		List instances = soaClient.getBPMServiceClient().getInstanceQueryService().queryInstances(
				bpmCtx, 
				displayColumns, 
				pred, 
				ordering, 
				input);
		
		logger.debug("instances.size() : "+instances.size());
		// iterate over tasks and build return data
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        
        for (int i = 0; i < instances.size(); i++) {
        	IProcessInstance instance = (IProcessInstance)instances.get(i);

        	logger.debug(instance.getCubeInstanceId());
	        //logger.debug(instance.getSystemAttributes().getProcessNumber());	//PROCESS_NUMBER_COLUMN
        	logger.debug(instance.getCubeInstanceId());
        	logger.debug(instance.getSystemAttributes().getProcessInstanceId());
        	logger.debug(instance.getSca().getComponentName());
	        //logger.debug(instance.getSystemAttributes().getComponentType());
        	logger.debug(instance.getSca().getCompositeDN());
        	logger.debug(instance.getSca().getCompositeInstanceId());
	        //logger.debug(instance.getSystemAttributes().getActivityName());
	        //logger.debug(instance.getSystemAttributes().getActivityType()); // PROCESS_ACTIVITYTYPE_COLUMN
	        //logger.debug(instance.getSystemAttributes().);	// PROCESS_ID_COLUMN
        	logger.debug(instance.getProcessName());
	        //instance.getSystemAttributes().getStep();
        	//logger.debug(instance.getSystemAttributes().getVersion());
        	logger.debug(instance.getSystemAttributes().getState());
        	logger.debug(instance.getTitle());
        	logger.debug(instance.getCreator());
            
        	logger.debug(BPMUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getCreatedDate(), bpmCtx.getTimeZone()));
	        logger.debug(BPMUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getProcessDueDate(), bpmCtx.getTimeZone()));
	        logger.debug(BPMUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getUpdatedDate(), bpmCtx.getTimeZone()));	// not c
	        logger.debug(BPMUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getUpdatedDate(), bpmCtx.getTimeZone()));
	        
	        logger.debug("Process Name : "+instance.getProcessName());
	        logger.debug("Process DN : "+instance.getProcessDN());	//default/BPMSample!1.0*/ApprovalProcess
	        logger.debug("Component Name : "+instance.getSca().getComponentName());
	       
        }
    }
        
    public AlterFlowResult alterflow(String instanceId, String grabSrcId, String grabTargetId)  throws Exception {
        
        logger.debug("test alter flow...");
        
        AlterFlowResult result = null;
        
        IBPMContext bpmCtx = (IBPMContext) BPMUtility.getContext("weblogic", "welcome1");
        SOAServiceClient soaClient = new SOAServiceClient();
        
        IInstanceQueryService iqs = soaClient.getBPMServiceClient().getInstanceQueryService();
        // find the instance
        IProcessInstance instance = iqs.getProcessInstance(bpmCtx, instanceId);
        
        if(instance == null) {
            logger.debug("alterFlowForInstance() instance not found:"+instanceId);
            return new AlterFlowResult(false,"Instance not found"+instanceId,null);
        }
        
        String instanceState = instance.getSystemAttributes().getState();
        if(!(
            instanceState.equalsIgnoreCase(IInstanceQueryInput.PROCESS_STATE_OPEN)
            || 
            instanceState.equalsIgnoreCase(IInstanceQueryInput.PROCESS_STATE_SUSPENDED)
            )) {    
            logger.debug("Instance is in state:"+instanceState+ " state must be either OPEN or SUSPENDED");
            return new AlterFlowResult(false,"Instance not in state OPEN or SUSPENDED:"+instanceState,null);
        }
        
        // get a grab context so we know the current activity and valid locations
        IGrabInstanceContextResponse grabContext = createGrabContext(bpmCtx, iqs, instance);
        // find the flow change for the requested source activity id
        IFlowChangeItem matchingFlowChange = null;
        
        // �������� ��Ƽ��Ƽ ��� ������....
        for(IFlowChangeItem flowChange:grabContext.getAvailableFlowChanges()) {
            IOpenActivityInfo openActivity = flowChange.getSourceActivity();
            Boolean isSrcContainer = openActivity.isContainerActivity();
            String openActivityDisplayName = openActivity.getDisplayName();
            String openActivityId = openActivity.getId();
            String openActivityPath = openActivity.getStringPath();  
            logger.debug("  FlowChange Open Activity[isSrcContainer:"+isSrcContainer+"][display:"+openActivityDisplayName+"][id:"+openActivityId+"][path:"+openActivityPath+"]");
            if(openActivityId != null && openActivityId.equals(grabSrcId)) {
                matchingFlowChange = flowChange;
                logger.debug("  Found open activity match for grabSrcId:"+grabSrcId);
                break;
            }
        }
        
        // if we didn't find one then return
        if(matchingFlowChange == null) {
            System.out.println("alterFlowForInstance() no flow change with macthing src location found:"+grabSrcId);
            return new AlterFlowResult(false,"No flow change matching id:"+grabSrcId,null);
        }
        
        String modifiedDataObject = "<approval xmlns=\"http://www.oracle.com/bpm/approval\">\n" + 
        "                <sign_info>\n" + 
        "                    <sign_id>modified</sign_id>\n" + 
        "                    <frm_id/>\n" + 
        "                    <frm_type/>\n" + 
        "                    <docno/>\n" + 
        "                    <sign_doc_title/>\n" + 
        "                    <isParallel/>\n" + 
        "                    <isSequential/>\n" + 
        "                    <isReader/>\n" + 
        "                    <isReceiver/>\n" + 
        "                </sign_info>\n" + 
        "                <sign_line_info/>\n" + 
        "            </approval>";
        // see if the requested target activity is valid
        // default the result to not-found
        result = new AlterFlowResult(false,"Did not find matching valid target with id:"+grabTargetId,null);
        
        // Target Activity ��� ������ (Source �� �ش��ϴ� Target)
        for(IActivityInfo targetActivity: matchingFlowChange.getValidGrabTargetActivities()) {
            String targetName = targetActivity.getDisplayName();
            String targetId = targetActivity.getId();
            logger.debug("    Checking Valid target [name:"+targetName+"][id:"+targetId+"]");
            if(targetId != null && targetId.equals(grabTargetId)) {
                logger.debug("    Found valid target maching requested target id:"+grabTargetId);
                // use the found activity as the target
                matchingFlowChange.setTargetActivity(targetActivity);
                // create the request to alter the flow
                IGrabInstanceRequest request = new GrabInstanceRequest();
                request.setProcessInstance(instance);
                request.setResumeInstanceIfRequired(true);
                request.setComments("AlterFlow from AlterFlowExample.java");
                request.setValidateValue(true);
                //request.setRequestedCorrelationKeyValueChanges(arg0); // Correlation Key
                //request.setRequestedVariableValueChanges(arg0); // Variable....
                List<IFlowChangeItem> flowChanges = new ArrayList<IFlowChangeItem>();
                flowChanges.add(matchingFlowChange);
                
                //modified dataobject
                final Set<IVariableItem> variableItemSet = new HashSet<IVariableItem>();
                variableItemSet.add(VariableItem.create("approvalDO", modifiedDataObject));
                
                request.setRequestedFlowChanges(flowChanges);
                request.setRequestedVariableValueChanges(variableItemSet);
                
                // do the alter flow
                IGrabInstanceResponse grabResponse = soaClient.getBPMServiceClient().getInstanceManagementService().grabInstance(bpmCtx, request);
                IInstanceSummary instanceSummary = grabResponse.getInstanceSummary();
                // create the result
                boolean isUpdated = instanceSummary.isSuccessfullyUpdated();
                String message = instanceSummary.getMessage();
                String exceptionMessage = instanceSummary.getExceptionMessage();
                logger.debug("    Grab Response: [isUpdated:"+isUpdated+"][message:"+message+"][exception:"+exceptionMessage+"]");
                result = new AlterFlowResult(isUpdated,message,exceptionMessage);
                // done
                break;
            }
        }
        return null;
    }
    
    private static IGrabInstanceContextResponse createGrabContext(IBPMContext ctx,
                                                                  IInstanceQueryService iqs,
                                                                  IProcessInstance instance) throws Exception {
        InstanceContextConfiguration.Builder builder = new InstanceContextConfiguration.Builder();
        IInstanceContextConfiguration configuration = builder.includeOpenActivities().build();
        IGrabInstanceContextRequest ctxReq = GrabInstanceContextRequest.create(instance, true, LocationInfo.ROOT_LOCATION, configuration);
        IGrabInstanceContextResponse ctxResp = iqs.createGrabInstanceContext(ctx, ctxReq);
        IGrabInstanceContext context = ctxResp.getGrabInstanceContext();
        return ctxResp;
    }
    
    // ���� ������ ���� ��� (���� �̸��� ���� ��)
    public void getVariableValueChange(String instanceId) throws Exception {
        IBPMContext bpmCtx = (IBPMContext) BPMUtility.getContext("weblogic", "welcome1");
        SOAServiceClient soaClient = new SOAServiceClient();
        IInstanceQueryService iqs = soaClient.getBPMServiceClient().getInstanceQueryService();
        // find the instance
        IProcessInstance instance = iqs.getProcessInstance(bpmCtx, instanceId);
        logger.debug(instance.getTitle());
        
        InstanceContextConfiguration.Builder builder = new InstanceContextConfiguration.Builder();
        IInstanceContextConfiguration configuration = builder.includeProcessDataObjects().build();  // Process Data Object ������..
        IGrabInstanceContextRequest ctxReq = GrabInstanceContextRequest.create(instance, false, LocationInfo.ROOT_LOCATION, configuration);
        IGrabInstanceContextResponse ctxResp = iqs.createGrabInstanceContext(bpmCtx, ctxReq);
        IGrabInstanceContext context = ctxResp.getGrabInstanceContext();
        
        Iterable<IVariableItem> availableVariables = context.getAvailableVariables();
        Iterable<IOpenActivityInfo> openActivities = context.getAvailableContainerActivities();
        Iterable<IVariableItem> availableAttributes = context.getAvailableInstanceAttributes();
        
        /*logger.debug(context.getLocation().getProcessInstanceInfo().getId());
        logger.debug(availableVariables.iterator().hasNext());
        logger.debug(openActivities.iterator().hasNext());
        logger.debug(availableAttributes.iterator().hasNext());*/
        
        Iterator iter = availableVariables.iterator();
        while (iter.hasNext()) {
            IVariableItem item = (IVariableItem) iter.next();
            logger.debug(item.getExpressionString());
            logger.debug(item.getName());
            logger.debug(item.getValue());
            //logger.debug(item.getType());
            //logger.debug(item.getNamespaceMapping());
        }
        
        //instance.get
        
        //soaClient.getBPMServiceClient().getInstanceQueryService().fetchVariableValue(arg0, arg1)
        //soaClient.getBPMServiceClient().getInstanceQueryService().getProcessAuditDiagram(arg0, arg1, arg2)
        
    }
    
    /**
     * 
     * @param wfCtx
     * @param params
     * @return
     * @throws Exception
     */
    public String getAggregatedTasks(IWorkflowContext wfCtx, Map<String, String> params) throws Exception {
		/*List taskCounts = querySvc.queryAggregatedTasks(ctx,
		Column.getColumn(WFTaskConstants.STATE_COLUMN),
		 ITaskQueryService.AssignmentFilter.MY,
		keyWordFilter,
		filterPredicate,
		false,orderByCount
		false, ascendingOrder);*/
		
		Predicate predicate = null;
		
		predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_COMPONENTTYPE_COLUMN, Predicate.OP_EQ, IWorkflowConstants.WORKFLOW_COMPONENT_TYPE);
		predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_HASSUBTASK_COLUMN, Predicate.OP_NEQ, true);
		
		if(params.get("state") != null)
			predicate = WorkflowUtility.setPredicate(predicate, TableConstants.WFTASK_STATE_COLUMN, Predicate.OP_EQ, (String) params.get("state"));
		
		ITaskQueryService.AssignmentFilter assignmentfilter = null;
		
		logger.debug("assignfilter : "+ (String) params.get("assignfilter"));
		if(params.get("assignfilter") != null) {
			if(((String)params.get("assignfilter")).equals("MY_AND_GROUP")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.MY_AND_GROUP;
			} else if(((String)params.get("assignfilter")).equals("ALL")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.ALL;
			} else if(((String)params.get("assignfilter")).equals("CREATOR")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.CREATOR;
			} else if(((String)params.get("assignfilter")).equals("GROUP")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.GROUP;
			} else if(((String)params.get("assignfilter")).equals("MY_AND_GROUP_ALL")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.MY_AND_GROUP_ALL;
			} else if(((String)params.get("assignfilter")).equals("MY")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.MY;
			} else if(((String)params.get("assignfilter")).equals("OWNER")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.OWNER;
			} else if(((String)params.get("assignfilter")).equals("OWNER")) {
				assignmentfilter = ITaskQueryService.AssignmentFilter.OWNER;
			}
			
		}
		
		SOAServiceClient soaClient = new SOAServiceClient();
		//args : IWorkflowContext, WFTaskConstants.STATE_COLUMN, ITaskQueryService.AssignmentFilter.MY, keyWordFilter, filterPredicate, orderByCount, ascendingOrder
		List<TaskCountType> task_aggt_count = (List<TaskCountType>)soaClient.getWorkflowServiceClient().getTaskQueryService().queryAggregatedTasks(wfCtx, Column.getColumn(WFTaskConstants.STATE_COLUMN), assignmentfilter, null, predicate, false, false);
		
		// ASSIGNED 1
		String value = "";
		int totalCnt = 0;
		for(TaskCountType type : task_aggt_count) {
			totalCnt += type.getCount();
			value += (value.equals("") ? "" : ",") + "\""+ type.getValue().toLowerCase() + "\":" + type.getCount();
		}
		
		if(!value.equals("")) 
			value += ",\"all\""+":"+totalCnt;
		
		logger.debug("value : "+ "{"+value+"}");
		
		if(value.equals(""))
			value = "\"all\":0,\"assigned\":0,\"completed\":0,\"expired\":0,\"stale\":0,\"withdrawn\":0";
		
		return "{"+value+"}";
		
	}
    
    /*
     * Holds result of grab
     */
    public final static class AlterFlowResult {
        private boolean succeeded;
        private String message;
        private String exceptionMessage;

        public AlterFlowResult(boolean succeeded, String message, String exceptionMessage) {
            this.succeeded = succeeded;
            this.message = message;
            this.exceptionMessage = exceptionMessage;
        }
        public void setSucceeded(boolean succeeded) {
            this.succeeded = succeeded;
        }

        public boolean isSucceeded() {
            return succeeded;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setExceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }

        public String getExceptionMessage() {
            return exceptionMessage;
        }
    }
}
