package oracle.bpm.workspace.client.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.repos.Column;
import oracle.bpel.services.workflow.repos.Ordering;
import oracle.bpel.services.workflow.repos.Predicate;
import oracle.bpel.services.workflow.repos.TableConstants;
import oracle.bpm.services.instancemanagement.model.IProcessInstance;
import oracle.bpm.services.instancequery.IColumnConstants;
import oracle.bpm.services.instancequery.IInstanceQueryInput;
import oracle.bpm.services.instancequery.impl.InstanceQueryInput;
import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.constants.OracleConstants;
import oracle.bpm.workspace.client.service.CustomBPMService;
import oracle.bpm.workspace.client.util.OBPMUtility;
import oracle.bpm.workspace.client.util.WorkflowUtility;
import oracle.bpm.workspace.client.vo.InstanceVO;
import oracle.soa.management.facade.ComponentInstance;

@Service("CustomBPMService")
public class CustomBPMServiceImpl implements CustomBPMService {
	
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	public List<InstanceVO> getQueryInstances(IBPMContext ctx, Map<String, String> params) throws Exception {
		String processname = (String)params.get("processname");
		String cpage = "";
		String rowSize = "";
		
		if(params.get(OracleConstants.PARAM.PAGE.CURRENT_PAGE) == null) 
			cpage = "0";
		else
			cpage = (String) params.get(OracleConstants.PARAM.PAGE.CURRENT_PAGE);
		
		if(params.get(OracleConstants.PARAM.PAGE.MAX_ROWS) == null) 
			rowSize = "5";
		else
			rowSize = (String) params.get(OracleConstants.PARAM.PAGE.MAX_ROWS);
		
		int startRow = Integer.parseInt(cpage) * Integer.parseInt(rowSize) + 1;
	    int endRow = startRow + Integer.parseInt(rowSize) - 1;
	    
		List<Column> displayColumns = new ArrayList<Column>();
		//displayColumns.add(IColumnConstants.PROCESS_NUMBER_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_INSTANCEID_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_COMPONENTNAME_COLUMN);
		
	    //displayColumns.add(IColumnConstants.PROCESS_COMPONENTTYPE_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_COMPOSITEDN_COLUMN);
	    displayColumns.add(IColumnConstants.PROCESS_COMPOSITEINSTANCEID_COLUMN);
		//displayColumns.add(IColumnConstants.PROCESS_ACTIVITYID_COLUMN);
	    displayColumns.add(IColumnConstants.PROCESS_ACTIVITYNAME_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_ACTIVITYTYPE_COLUMN);
		//displayColumns.add(IColumnConstants.PROCESS_ID_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_PROCESSNAME_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_VERSION_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_STATE_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_TITLE_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_CREATOR_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_CREATEDDATE_COLUMN);
		//displayColumns.add(IColumnConstants.PROCESS_ASSIGNEE_TASKID_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_DUEDATE_COLUMN);
		displayColumns.add(IColumnConstants.PROCESS_COMPOSITEVERSION_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_ENDDATE_COLUMN);
	    //displayColumns.add(IColumnConstants.PROCESS_UPDATEDDATE_COLUMN);
		
		//displayColumns.add(IColumnConstants.PROCESS_ASSIGNEES_COLUMN);
		
		/* other columns....
	      displayColumns.add(IColumnConstants.PROCESS_AGROOT_ID);
	      displayColumns.add(IColumnConstants.PROCESS_APPLICATIONCONTEXT_COLUMN);
	      displayColumns.add(IColumnConstants.PROCESS_APPLICATIONNAME_COLUMN);
	      
	      displayColumns.add(IColumnConstants.PROCESS_ASSIGNEEATTRIBUTE_COLUMN);
	      ;
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
		//  ordering = WorkflowUtility.createTaskOrdering(TableConstants.WFTASK_CREATEDDATE_COLUMN.getName(), OracleConstants.CODE.SORT_ORDER.DESCENDING);
		//  Ordering ordering = new Ordering(IColumnConstants.PROCESS_NUMBER_COLUMN,false,true);  
		Ordering ordering = new Ordering(IColumnConstants.PROCESS_CREATEDDATE_COLUMN,false,true); 
		
		if(params.get("orderBy") != null && !params.get("orderBy").equals("")){
			//ordering = WorkflowUtility.createTaskOrdering(IColumnConstants.PROCESS_DATEATTRIBUTE2_COLUMN.getName(), OracleConstants.CODE.SORT_ORDER.DESCENDING);
			ordering = new Ordering(IColumnConstants.PROCESS_DUEDATE_COLUMN,true,true); 
		}else{
			ordering = new Ordering(IColumnConstants.PROCESS_CREATEDDATE_COLUMN,false,true); 
		}
		
		Predicate pred = null;
		
		pred = new Predicate(IColumnConstants.PROCESS_COMPONENTTYPE_COLUMN, Predicate.OP_EQ, "BPMN");
		
		
		// 상태 조회
		if(!"".equals(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName())) && params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()) != null)
			pred.addClause(Predicate.AND, IColumnConstants.PROCESS_STATE_COLUMN, Predicate.OP_EQ, params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()));
		
		logger.debug("instance title : " + params.get(IColumnConstants.PROCESS_TITLE_COLUMN.getName()));
		// 제목조회
		if(!"".equals(params.get(IColumnConstants.PROCESS_TITLE_COLUMN.getName())) && params.get(IColumnConstants.PROCESS_TITLE_COLUMN.getName()) != null)
			pred.addClause(Predicate.AND, IColumnConstants.PROCESS_TITLE_COLUMN, Predicate.OP_LIKE, "%"+params.get(IColumnConstants.PROCESS_TITLE_COLUMN.getName())+"%");
		
		logger.debug("processname : " + params.get("processname"));
		if(processname != null && !"".equals(processname))
			//pred.addClause(Predicate.AND, IColumnConstants.PROCESS_PROCESSNAME_COLUMN, Predicate.OP_EQ, processname);
			pred.addClause(Predicate.AND, IColumnConstants.PROCESS_COMPONENTNAME_COLUMN, Predicate.OP_EQ, processname);
		
		// 날짜 검색
        if(!"".equals(params.get("fromDate")) && params.get("fromDate") != null) {
        	Calendar fromDate = WorkflowUtility.parseDateString(params.get("fromDate") + " 00:00", ctx.getLocale(), ctx.getTimeZone());
        	
        	pred.addClause(Predicate.AND, IColumnConstants.PROCESS_CREATEDDATE_COLUMN, Predicate.OP_GTE, fromDate.getTime());
        }
        
        if(!"".equals(params.get("toDate")) && params.get("fromDate") != null) {
        	Calendar toDate = WorkflowUtility.parseDateString(params.get("toDate") + " 00:00", ctx.getLocale(), ctx.getTimeZone());
        	toDate.add(Calendar.DATE, 1);
        	
        	pred.addClause(Predicate.AND, IColumnConstants.PROCESS_CREATEDDATE_COLUMN, Predicate.OP_LT, toDate.getTime());
        }
        
        // 기한 지난것만...
        if(!"".equals(params.get("overdue")) && params.get("overdue") != null && "YES".equals(params.get("overdue"))) {
        	Calendar curDate = Calendar.getInstance();
        	
        	logger.debug("curDate.getTime() : " + curDate.getTime());
        	pred.addClause(Predicate.AND, IColumnConstants.PROCESS_DUEDATE_COLUMN, Predicate.OP_LT, curDate.getTime());
        }
        else if(!"".equals(params.get("overdue")) && params.get("overdue") != null && "NO".equals(params.get("overdue"))) {
        	Calendar curDate = Calendar.getInstance();
        	
        	logger.debug("curDate.getTime() : " + curDate.getTime());
        	pred.addClause(Predicate.AND, IColumnConstants.PROCESS_DUEDATE_COLUMN, Predicate.OP_GT, curDate.getTime());
        }
		
		/*Predicate datePredicate = new Predicate(TableConstants.WFTASK_ENDDATE_COLUMN,
                Predicate.OP_ON,
                new Date());
		Predicate predicate = new Predicate(statePredicate, Predicate.AND, datePredicate);*/

		IInstanceQueryInput input = new InstanceQueryInput();
		
		if(params.get("assignmentFilter") == null || params.get("assignmentFilter").equals("ALL"))
			input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.ALL);
		else if(params.get("assignmentFilter").equals("MY"))
			input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.MY);
		else if(params.get("assignmentFilter").equals("GROUP"))
			input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.GROUP);
		else if(params.get("assignmentFilter").equals("MY_AND_GROUP"))
			input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.MY_AND_GROUP);
		else if(params.get("assignmentFilter").equals("PREVIOUS"))
			input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.PREVIOUS);
		else
			input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.ADMIN);
		
		
		logger.debug("startRow : " + startRow);
		logger.debug("endRow : " + endRow);
		
		// Size 체크할 경우를 위해서 페이징 제거 파라미터 추가 (unlimited)
		if(params.get("pageSize") == null || params.get("pageSize").equals("PAGING")) {
			input.setStartRow(startRow);	//신한은행 PoC 땜에 주석 by kdh (140528)
			input.setEndRow(endRow);
		}
		
		// state : OPEN, COMPLETED, ERRORED, CANCELED, ABORTED, STALE
		// title : %%
		// WHERE STATE = ? AND TITLE LIKE ? AND COMPONENT='BPMN' and assignmentFilter=ALL (모두)
		
		List instances = soaClient.getBPMServiceClient().getInstanceQueryService().queryInstances(
				ctx, 
				displayColumns, 
				pred, 
				ordering, 
				input);
		
		// 필터를 통한 전체 카운트를 가져오기 위한 API가 없음.
		// 이 방법은 비 효율적임 => 방안을 강구해야함.
		//input = new InstanceQueryInput();
		//input.setAssignmentFilter(IInstanceQueryInput.AssignmentFilter.ALL);
		List tot_instances = soaClient.getBPMServiceClient().getInstanceQueryService().queryInstances(
				ctx, 
				displayColumns, 
				pred, 
				ordering, 
				input);
		
		logger.debug("instances.size() : "+tot_instances.size());
		// iterate over tasks and build return data
        List<InstanceVO> result = new ArrayList<InstanceVO>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        for (int i = 0; i < instances.size(); i++) {
        	IProcessInstance instance = (IProcessInstance)instances.get(i);

	        InstanceVO instanceVO = new InstanceVO();
	        instanceVO.setPartition(instance.getProcessDN().substring(0, instance.getProcessDN().indexOf("/")));
	        //instanceVO.setNumber(instance.getSystemAttributes().getProcessNumber());	//PROCESS_NUMBER_COLUMN
	        instanceVO.setInstanceId(instance.getCubeInstanceId());
	        instanceVO.setProcessInstanceId(instance.getSystemAttributes().getProcessInstanceId());
	        instanceVO.setComponentName(instance.getSca().getComponentName());
	        //instanceVO.setComponentType(instance.getSystemAttributes().getComponentType());
	        instanceVO.setCompositeDN(instance.getSca().getCompositeDN());
	        instanceVO.setProcessDN(instance.getProcessDN());
	        instanceVO.setCompositeInstanceId(instance.getSca().getCompositeInstanceId());
	        //instanceVO.setActivityName(instance.getSystemAttributes().getActivityName());
	        //instanceVO.setAccessKey(instance.getSystemAttributes().getActivityType()); // PROCESS_ACTIVITYTYPE_COLUMN
	        //instanceVO.setTaskId(instance.getSystemAttributes().);	// PROCESS_ID_COLUMN
	        instanceVO.setProcessName(instance.getProcessName());
	        //instance.getSystemAttributes().getStep();
	        instanceVO.setVersion(instance.getSystemAttributes().getVersion());
	        instanceVO.setState(instance.getSystemAttributes().getState());
	        instanceVO.setTitle(instance.getTitle());
	        instanceVO.setCreator(instance.getCreator());
	        instanceVO.setProcessDueDate(instance.getSystemAttributes().getProcessDueDate());
	        instanceVO.setCompositeVersion(instance.getSca().getCompositeVersion());
	        
	        logger.debug("instance.getSystemAttributes().getUpdatedDate() : " + instance.getSystemAttributes().getUpdatedDate());
	        logger.debug("instance.getSystemAttributes().getProcessDueDate() : " + instance.getSystemAttributes().getProcessDueDate());
	        logger.debug("instance.getSystemAttributes().getState() : " + instance.getSystemAttributes().getState());
	        
	        long processDuration = 0;
	        // updateddate 가 null임... 나중에 확인필요.
	        //if(instance.getSystemAttributes().getState().equals(IInstanceQueryInput.PROCESS_STATE_COMPLETE))
	        //	processDuration = ((instance.getSca().get  .getUpdatedDate().getTimeInMillis()/1000) - (instance.getSystemAttributes().getProcessDueDate().getTimeInMillis()/1000));
	        //else
	        	processDuration = ((System.currentTimeMillis()/1000) - (instance.getSystemAttributes().getProcessDueDate().getTimeInMillis()/1000));
	        instanceVO.setActivityName(instance.getSystemAttributes().getActivityName());
	        
	        instanceVO.setCountDueDate(((processDuration/3600) == 0 ? "" : (processDuration/3600)+ "시간 ")+((processDuration % 3600 / 60) == 0 ? "" : (processDuration % 3600 / 60)+"분 "));
	        
	        // BGF PoC를 위해 사용자만 가져오도록 수정
	        // BPM 에서 나오는 사용자 정보는 모든 롤과 사용자를 다 가져옴 --> 이상함(?)
	        //instanceVO.setAssigneesDisplayName(WorkflowUtility.getAssigneeStringFromIdentityType(instance.getSystemAttributes().getAssignees()));
	        
	        instanceVO.setCreatedDate(WorkflowUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getCreatedDate(), ctx.getTimeZone()));
	        //instanceVO.setProcessDueDate(WorkflowUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getProcessDueDate(), bpmCtx.getTimeZone()));
	        //instanceVO.setEndDate(WorkflowUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getUpdatedDate(), bpmCtx.getTimeZone()));	// not c
	        //instanceVO.setUpdatedDate(WorkflowUtility.getTimeZoneBasedDateString(df, instance.getSystemAttributes().getUpdatedDate(), bpmCtx.getTimeZone()));
	        
	        logger.debug("Process Name : "+instance.getProcessName());
	        logger.debug("Process DN : "+instance.getProcessDN());	//default/BPMSample!1.0*/ApprovalProcess
	        logger.debug("Component Name : "+instance.getSca().getComponentName());
	        result.add(instanceVO);
        }
        
        // Total Size 가 필요한데...... SOA Infra API가 필요할지도......
        // OPEN, COMPLETED, ERRORED, CANCELED, ABORTED, STALE
        //ComponentInstance.STATE_COMPLETED_SUCCESSFULLY
        //ComponentInstance.STATE_FAULTED
        //ComponentInstance.STATE_RUNNING
        //ComponentInstance.STATE_STALE
        //ComponentInstance.STATE_SUSPENDED
        //ComponentInstance.STATE_TERMINATED_BY_USER
        //ComponentInstance.STATES_INFLIGHT
        
        int instanceState = 9;
        if(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()) != null && !"".equals(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()))) {
        	if(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()).equals("OPEN"))
        		instanceState = ComponentInstance.STATE_RUNNING;
        	else if(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()).equals("COMPLETED"))
        		instanceState = ComponentInstance.STATE_COMPLETED_SUCCESSFULLY;
        	else if(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()).equals("ERRORED"))
        		instanceState = ComponentInstance.STATE_FAULTED;
        	else if(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()).equals("STALE"))
        		instanceState = ComponentInstance.STATE_STALE;
        	else if(params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()).equals("CANCELED") || params.get(IColumnConstants.PROCESS_STATE_COLUMN.getName()).equals("ABORTED"))
        		instanceState = ComponentInstance.STATE_TERMINATED_BY_USER;
        }
        
        //int totalCount = customSOAService.getCountProcessInstancesByFilter(bpmCtx, Locator.SE_BPMN, instanceState);
        
        
        params.put(OracleConstants.PARAM.PAGE.TOTAL_ROWS, Integer.toString(tot_instances.size()));
        
        return result;
	}
	
}
