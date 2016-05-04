package oracle.bpm.workspace.client.service;

import java.util.HashMap;
import java.util.Map;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.bpm.workspace.client.vo.CustomBPMTrackerModelVO;


public interface CustomBPMTrackerService {
	public CustomBPMTrackerModelVO getCustomBPMTrackerModel(CustomBPMTrackerModelVO trackerModelVO);
	public String getProcessActivitiesXML(IBPMContext ctx, Map<String,String> params) throws Exception;
	public String getDummyProcessActivitiesXML(Map<String,String> params) throws Exception;
	public String getProcessAuditDataXML(IBPMContext ctx, Map<String,String> params) throws Exception;
	public String getProcessAuditDataXML2(IBPMContext ctx, Map<String,String> params) throws Exception;
	public String getProcessAuditDataXML3(Map<String,String> params) throws Exception;
	public String getProcessAggregatedTaskDataXML(IWorkflowContext ctx, Map<String,String> params) throws Exception;
	//public HashMap<String,Integer> getAggregatedTaskCountByUser(IWorkflowContext wfctx, Map<String, String> params) throws Exception;
	public HashMap<String,Integer> getAggregatedTaskCountByState(IWorkflowContext wfctx, Map<String, String> params) throws Exception;
	public String getSubprocessInfo(IBPMContext ctx, Map<String,String> params) throws Exception;
	public String getSubprocessInfoByAPI(IBPMContext bpmCtx, Map<String, String> params) throws Exception;
	public void mergeCustomBPMTrackerModel(CustomBPMTrackerModelVO trackerModelVO) throws Exception;
	public void insertCustomBPMTrackerModel(CustomBPMTrackerModelVO trackerModelVO) throws Exception;
	public void updateCustomBPMTrackerModel(CustomBPMTrackerModelVO trackerModelVO) throws Exception;
}
