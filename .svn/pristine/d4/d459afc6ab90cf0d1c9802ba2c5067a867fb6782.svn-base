package oracle.bpm.workspace.client.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.service.CustomBPMTrackerService;
import oracle.bpm.workspace.client.vo.CustomBPMTrackerModelVO;

@Controller
public class CustomBPMTrackerController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	@Resource(name="CustomBPMTrackerService")
	protected CustomBPMTrackerService customBPMTrackerService;
	
	@RequestMapping("/tracker/custom/main")
	public String i_ptracking(HttpSession session, ModelMap model) throws Exception {
		return "bpm/tracker/processTracker";
	}
	
	@RequestMapping("/tracker/custom/getProcessActivitiesXML")
	public void getProcessActivitiesXML(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		IBPMContext ctx = null;
		
		if(accountData != null) {
			ctx = (IBPMContext) accountData.get("bpmContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			ctx = soaClient.getBPMUserAuthenticationService().authenticate(params.get("username"), "welcome1".toCharArray(), null);
		}
		
		String activitiesXML = customBPMTrackerService.getProcessActivitiesXML(ctx, params);
		
		response.getOutputStream().write(activitiesXML.getBytes("utf-8"));
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
    }
	
	@RequestMapping("/tracker/custom/getDummyProcessActivitiesXML")
	public void getDummyProcessActivitiesXML(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String activitiesXML = customBPMTrackerService.getDummyProcessActivitiesXML(params);
		
		response.getOutputStream().write(activitiesXML.getBytes("utf-8"));
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
    }
	
	@RequestMapping("/tracker/custom/getProcessAuditDataXML")
	public void getProcessAuditDataXML(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		IBPMContext ctx = null;
		
		if(accountData != null) {
			ctx = (IBPMContext) accountData.get("bpmContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			ctx = soaClient.getBPMUserAuthenticationService().authenticate((String)params.get("username"), "welcome1".toCharArray(), null);
		}
		
		String auditXML = customBPMTrackerService.getProcessAuditDataXML(ctx, params);
		
		response.getOutputStream().write(auditXML.getBytes("utf-8"));
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
    }
	
	@RequestMapping("/tracker/custom/getProcessAuditDataXML2")
	public void getProcessAuditDataXML2(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		IBPMContext ctx = null;
		
		if(accountData != null) {
			ctx = (IBPMContext) accountData.get("bpmContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			ctx = soaClient.getBPMUserAuthenticationService().authenticate((String)params.get("username"), "welcome1".toCharArray(), null);
		}
		
		String auditXML = customBPMTrackerService.getProcessAuditDataXML2(ctx, params);
		
		response.getOutputStream().write(auditXML.getBytes("utf-8"));
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
    }
	
	// for bpel
	@RequestMapping("/tracker/custom/getProcessAuditDataXML3")
	public void getProcessAuditDataXML3(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String auditXML = customBPMTrackerService.getProcessAuditDataXML3(params);
		
		response.getOutputStream().write(auditXML.getBytes("utf-8"));
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
    }
	
	@RequestMapping("/tracker/custom/getProcessAggregatedTaskDataXML")
	public void getProcessAggregatedTaskDataXML(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfctx = null;
		
		if(accountData != null) {
			wfctx = (IWorkflowContext) accountData.get("workflowContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			wfctx = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate((String)params.get("username"), "welcome1".toCharArray(), null);
		}
		
		
		String auditXML = customBPMTrackerService.getProcessAggregatedTaskDataXML(wfctx, params);
		
		response.getOutputStream().write(auditXML.getBytes("utf-8"));
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
    }
	
	@RequestMapping("/tracker/custom/getProcessAggregatedTaskByUser")
	public String getProcessAggregatedTaskByUser(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfctx = null;
		
		if(accountData != null) {
			wfctx = (IWorkflowContext) accountData.get("workflowContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			wfctx = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate((String)params.get("username"), "welcome1".toCharArray(), null);
		}
		
		//HashMap<String,Integer> aggr_task_cnt_by_user = customBPMTrackerService.getAggregatedTaskCountByUser(wfctx, params);
		HashMap<String,Integer> aggr_task_cnt_by_state = customBPMTrackerService.getAggregatedTaskCountByState(wfctx, params);
		
		//model.addAttribute("aggr_task_cnt_by_user", aggr_task_cnt_by_user);
		model.addAttribute("aggr_task_cnt_by_state", aggr_task_cnt_by_state);
	    
	    return "obpm/workflow/taskcnt_by_user";
		
    }
	
	@RequestMapping("/tracker/custom/getProcessAggregatedTaskByState")
	public String getProcessAggregatedTaskByState(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfctx = null;
		
		if(accountData != null) {
			wfctx = (IWorkflowContext) accountData.get("workflowContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			wfctx = soaClient.getWorkflowServiceClient().getTaskQueryService().authenticate((String)params.get("username"), "welcome1".toCharArray(), null);
		}
		
		
		HashMap<String,Integer> aggr_task_cnt_by_state = customBPMTrackerService.getAggregatedTaskCountByState(wfctx, params);
		
		model.addAttribute("result", aggr_task_cnt_by_state);
	    
	    return "obpm/workflow/taskcnt_by_user";
		
    }
	
	@RequestMapping("/tracker/custom/getSubProcessInfo")
	public String getSubCompositeDN(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		IBPMContext ctx = null;
		
		if(accountData != null) {
			ctx = (IBPMContext) accountData.get("bpmContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			ctx = soaClient.getBPMUserAuthenticationService().authenticate((String)params.get("username"), "welcome1".toCharArray(), null);
		}
		
		String subInfo = customBPMTrackerService.getSubprocessInfo(ctx, params);
		
		model.addAttribute("result", subInfo);
		
		return "common/response"; // JSP or Tiles definition 처리
    }
	
	@RequestMapping("/tracker/custom/getSubProcessInfoByAPI")
	public String getSubCompositeDNByAPI(HttpSession session, @RequestParam(required = false) Map<String, String> params, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		IBPMContext ctx = null;
		
		if(accountData != null) {
			ctx = (IBPMContext) accountData.get("bpmContext");
		} else {
			// 임시로 사용 - Security 사용 안할때를 위해서...
			ctx = soaClient.getBPMUserAuthenticationService().authenticate((String)params.get("username"), "welcome1".toCharArray(), null);
		}
		
		String subInfo = customBPMTrackerService.getSubprocessInfoByAPI(ctx, params);
		
		model.addAttribute("result", subInfo);
		
		return "common/response"; // JSP or Tiles definition 처리
    }
	
	@RequestMapping(value="/tracker/custom/model", method = RequestMethod.GET)
	public void getCustomBPMTrackerModel(@ModelAttribute("trackerModelVO") CustomBPMTrackerModelVO trackerModelVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CustomBPMTrackerModelVO customBPMTrackerModelVO = customBPMTrackerService.getCustomBPMTrackerModel(trackerModelVO);
		
		if(customBPMTrackerModelVO == null) {
			response.getOutputStream().write("".getBytes("utf-8"));
		} else {
			response.getOutputStream().write(customBPMTrackerModelVO.getModel_xml().getBytes("utf-8"));
		}
		
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
    }
	
	@RequestMapping("/tracker/custom/mergeTrackerModel")
	public String mergeTrackerModel(HttpSession session,@ModelAttribute("trackerModelVO") CustomBPMTrackerModelVO trackerModelVO, ModelMap model) throws Exception {
		
		// 11.2.0.2 이하일 경우 MyBatis에서 MERGE Statement 사용시 ORA-00600 오류가 발생함
		// Procedure 로 바꿈. 11.2.0.2 이상이 아닐 경우 프로시져 사용.
		customBPMTrackerService.mergeCustomBPMTrackerModel(trackerModelVO);

		return "common/json_response"; // JSP or Tiles definition 처리
    }
}
