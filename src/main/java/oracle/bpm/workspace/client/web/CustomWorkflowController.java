package oracle.bpm.workspace.client.web;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oracle.bpel.services.workflow.verification.IWorkflowContext;
import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.service.CustomWorkflowService;
import oracle.bpm.workspace.client.util.TestMapLog;
import oracle.bpm.workspace.client.vo.TaskHistoryVO;
import oracle.bpm.workspace.client.vo.TaskVO;

@Controller
public class CustomWorkflowController {
	@Resource(name = "soaClient")
	protected SOAServiceClient soaClient;
	
	@Resource(name="CustomWorkflowService")
	protected CustomWorkflowService customWorkflowService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); // Logger

	@RequestMapping("/bpm/workflow/tasks")
	public String tasks(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfCtx = null;
		
		wfCtx = (IWorkflowContext) accountData.get("workflowContext");
		
		TestMapLog.testMapLog(accountData);
		
		//Map<String, Object> userPrefs = (Map<String, Object>)accountData.get("bpm_user_preferences");
		//logger.debug("usrPrfmaxPageRows : "+userPrefs.get("usrPrfmaxPageRows"));
		
		List<TaskVO> tasks = customWorkflowService.getTaskList(wfCtx, params);
		
		if(tasks != null && tasks.size() > 0){
			logger.debug("###### task count : "+tasks.size());
			TestMapLog.testListTaskVOLog(tasks);
		}else{
			logger.debug("###### task count : "+tasks);
		}
		//String result = JsonUtility.getToJSONString(tasks);
		//logger.debug("##### task Json Str : "+ result);
		//List<TaskVO> fromJsonTasks = (List<TaskVO>) JsonUtility.getFromJSONString(result);
		
		model.addAttribute("params", params);
		model.addAttribute("result", tasks);
		
		return "bpm/home/subs/tasks";
	}
	
	/**
	 * 프로세스현황
	 * @param session
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bpm/workflow/taskProcess")
	public String taskProcess(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfCtx = null;
		
		wfCtx = (IWorkflowContext) accountData.get("workflowContext");
		
		TestMapLog.testMapLog(accountData);
		
		//Map<String, Object> userPrefs = (Map<String, Object>)accountData.get("bpm_user_preferences");
		//logger.debug("usrPrfmaxPageRows : "+userPrefs.get("usrPrfmaxPageRows"));
		
		List<TaskVO> tasks = customWorkflowService.getTaskList(wfCtx, params);
		
		if(tasks != null && tasks.size() > 0){
			logger.debug("###### task count : "+tasks.size());
			TestMapLog.testListTaskVOLog(tasks);
		}else{
			logger.debug("###### task count : "+tasks);
		}
		//String result = JsonUtility.getToJSONString(tasks);
		//logger.debug("##### task Json Str : "+ result);
		//List<TaskVO> fromJsonTasks = (List<TaskVO>) JsonUtility.getFromJSONString(result);
		
		model.addAttribute("params", params);
		model.addAttribute("result", tasks);
		
		return "bpm/home/subs/taskProcess";
	}
	
	/**
	 * 담당자현황
	 * @param session
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bpm/workflow/taskApproval")
	public String taskApproval(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {
		
		
		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfCtx = (IWorkflowContext) accountData.get("workflowAdminContext");
		
		//TestMapLog.testMapLog(accountData);
		
		//Map<String, Object> userPrefs = (Map<String, Object>)accountData.get("bpm_user_preferences");
		//logger.debug("usrPrfmaxPageRows : "+userPrefs.get("usrPrfmaxPageRows"));
		
		List<TaskVO> tasks = customWorkflowService.getDelayTaskList(wfCtx, params);
		
		if(tasks != null && tasks.size() > 0){
			logger.debug("###### task count : "+tasks.size());
			TestMapLog.testListTaskVOLog(tasks);
		}else{
			logger.debug("###### task count : "+tasks);
		}
		//String result = JsonUtility.getToJSONString(tasks);
		//logger.debug("##### task Json Str : "+ result);
		//List<TaskVO> fromJsonTasks = (List<TaskVO>) JsonUtility.getFromJSONString(result);
		
		model.addAttribute("params", params);
		model.addAttribute("result", tasks);
		
		return "bpm/home/subs/taskApproval";
	}
	
	@RequestMapping("/bpm/workflow/taskDetail")
	public String taskDetail(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {

		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfCtx = null;
		
		wfCtx = (IWorkflowContext) accountData.get("workflowContext");
		
		TestMapLog.testMapLog(accountData);
		
		//Map<String, Object> userPrefs = (Map<String, Object>)accountData.get("bpm_user_preferences");
		//logger.debug("usrPrfmaxPageRows : "+userPrefs.get("usrPrfmaxPageRows"));
		
		TaskVO tasks = customWorkflowService.getTaskDetail(wfCtx, Integer.parseInt(params.get("number")));
		
		//String result = JsonUtility.getToJSONString(tasks);
		logger.debug("##### tasks : "+ tasks);
		//List<TaskVO> fromJsonTasks = (List<TaskVO>) JsonUtility.getFromJSONString(result);
		
		model.addAttribute("params", params);
		model.addAttribute("task", tasks);
		
		return "bpm/home/subs/detail";
	}
	
	@RequestMapping("/bpm/workflow/sapDetail")
	public String sapDetail(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {

		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfCtx = null;
		
		wfCtx = (IWorkflowContext) accountData.get("workflowContext");
		
		TestMapLog.testMapLog(accountData);
		
		//Map<String, Object> userPrefs = (Map<String, Object>)accountData.get("bpm_user_preferences");
		//logger.debug("usrPrfmaxPageRows : "+userPrefs.get("usrPrfmaxPageRows"));
		
		TaskVO tasks = customWorkflowService.getTaskDetail(wfCtx, Integer.parseInt(params.get("number")));
		
		//String result = JsonUtility.getToJSONString(tasks);
		logger.debug("##### tasks : "+ tasks);
		//List<TaskVO> fromJsonTasks = (List<TaskVO>) JsonUtility.getFromJSONString(result);
		
		model.addAttribute("params", params);
		model.addAttribute("task", tasks);
		
		return "bpm/home/subs/sapDetail";
	}
	
	@RequestMapping("/bpm/workflow/doAction")
	public String doAction(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {

		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfCtx = null;
		
		wfCtx = (IWorkflowContext) accountData.get("workflowContext");
		
		TestMapLog.testMapLog(accountData);
		
		//Map<String, Object> userPrefs = (Map<String, Object>)accountData.get("bpm_user_preferences");
		//logger.debug("usrPrfmaxPageRows : "+userPrefs.get("usrPrfmaxPageRows"));
		
		logger.debug("##### params : "+ params);
		
		customWorkflowService.doAction(wfCtx, params); //미완성이므로 임시로 주석

		return "bpm/home/popup/close";
	}
	
	@RequestMapping("/bpm/workflow/taskHistory")
	public String taskHistory(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {

		HashMap<String, Object> accountData = (HashMap<String, Object>) session.getAttribute("accountData");
		
		IWorkflowContext wfCtx = null;
		
		wfCtx = (IWorkflowContext) accountData.get("workflowAdminContext");
		
		TestMapLog.testMapLog(accountData);
		
		//Map<String, Object> userPrefs = (Map<String, Object>)accountData.get("bpm_user_preferences");
		//logger.debug("usrPrfmaxPageRows : "+userPrefs.get("usrPrfmaxPageRows"));
		
		logger.debug("##### params : "+ params);
		
		List<TaskHistoryVO> taskHistories = customWorkflowService.getShortTaskHistory(wfCtx, params);
		
		model.addAttribute("result", taskHistories);
		
		return "bpm/home/subs/taskHistory";
	}
	
	/**
	 * 비용발생
	 * @param session
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bpm/workflow/taskInvoke")
	public String taskInvoke(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {
		
		String data = "";
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MINUTE,10);
		Date date = cal.getTime();		
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		data = simple.format(date);

		model.addAttribute("params", params);
		model.addAttribute("result", null);
		model.addAttribute("nextDay",data);
		return "bpm/home/popup/taskInvoke";
	}
	
	/**
	 * 프로세스관리
	 * @param session
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bpm/workflow/taskAdmin")
	public String taskAdmin(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {

		model.addAttribute("result", null);

		return "bpm/home/popup/taskAdmin";
	}
	
	/**
	 * 
	 * @param session
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bpm/workflow/approvalList")
	public String approvalList(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {

		model.addAttribute("result", null);

		return "bpm/home/subs/approvalList";
	}
}
