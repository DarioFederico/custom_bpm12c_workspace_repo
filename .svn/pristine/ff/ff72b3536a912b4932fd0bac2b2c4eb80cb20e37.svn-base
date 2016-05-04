package oracle.bpm.workspace.client.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.service.CustomBPMAnalysisService;
import oracle.bpm.workspace.client.vo.CustomProcessActivityAnalysisVO;

@Controller
public class CustomBPMAnalysisController {
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	@Resource(name="CustomBPMAnalysisService")
	protected CustomBPMAnalysisService customBPMAnalysisService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); // Logger
	
	@RequestMapping("/bpm/analysis/processActivityAnalysis")
	public String processActivityAnalysis(HttpSession session, ModelMap model) throws Exception {	
		return "/bpm/analysis/processActivityAnalysis";
	}
	
	@RequestMapping("/bpm/analysis/getProcessActivityAnalysisData")
	public String getProcessActivityAnalysisData(HttpSession session, @RequestParam String componentname, ModelMap model) throws Exception {	
		
		List<CustomProcessActivityAnalysisVO> processActivityAnalysis = customBPMAnalysisService.getProcessActivityAnalysis(componentname);
		
		String ct_result = "";
		String lt_result = "";
		for(CustomProcessActivityAnalysisVO data : processActivityAnalysis) {
			ct_result += "\""+data.getLabel()+"\":"+data.getActivitycnt()+",";
		}
		
		for(CustomProcessActivityAnalysisVO data : processActivityAnalysis) {
			lt_result += "\""+data.getLabel()+"\":"+data.getActivityavglt()+",";
		}
		
		model.put("result", "{\"ct_result\" : {"+ct_result.substring(0, ct_result.length()-1)+"},\"lt_result\" : {"+lt_result.substring(0, lt_result.length()-1)+"}}");
		
		return "common/response";
	}
}
