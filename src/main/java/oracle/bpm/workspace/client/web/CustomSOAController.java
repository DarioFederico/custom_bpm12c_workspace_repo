package oracle.bpm.workspace.client.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.service.CustomSOAService;
import oracle.bpm.workspace.client.util.StrUtil;
import oracle.soa.management.CompositeDN;
import oracle.soa.management.facade.ComponentInstance;
import oracle.soa.management.facade.Locator;

@Controller
public class CustomSOAController {
	@Resource(name = "soaClient")
	protected SOAServiceClient soaClient;
	
	@Resource(name="CustomSOAService")
	protected CustomSOAService customSOAService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); // Logger

	@RequestMapping("/soa/component/instances")
	public String componentInstances(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {
		
		Locator locator = soaClient.getLocator();
		List<ComponentInstance> componentInstances = customSOAService.getComponentInstances(locator, params);
		
		model.addAttribute("result", componentInstances);

		return "bpm/home/subs/componentInstances";
	}
	
	/**
	 * 프로세스 현황
	 * @param session
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/soa/component/wfInstances")
	public String ProcessInstances(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {
		
		Locator locator = soaClient.getLocator();
		/*
		 *  pageStart 시작 페이지
		 *  params.put("pageStart", "0");
		 */
		int pageSize = 0;
		if(params.get("pageSize") != null && !params.get("pageSize").equals(""))
			pageSize = Integer.parseInt(params.get("pageSize"));
		
		// state 진행중 // 0 : 진행중, 2 : 완료
		params.remove("pageSize");
		params.put("state", "0"); 
		List<ComponentInstance> componentInstances = customSOAService.getComponentInstances(locator, params);
		// state 완료 // 0 : 진행중, 2 : 완료
		params.put("state", "2");
		componentInstances.addAll(customSOAService.getComponentInstances(locator, params));
		
		if(componentInstances != null && componentInstances.size() >= pageSize && pageSize >= 5){
			componentInstances.subList(pageSize, componentInstances.size()).clear();
		}
		
		model.addAttribute("result", componentInstances);

		return "bpm/home/subs/taskProcess";
	}
	
	/**
	 * Worst 현황
	 * @param session
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/soa/component/worstInstances")
	public String worstInstances(HttpSession session, @RequestParam(required = false) Map<String, String> params,
			ModelMap model) throws Exception {
		
		Locator locator = soaClient.getLocator();
		/*
		 *  pageStart 시작 페이지
		 *  params.put("pageStart", "0");
		 */
		int pageSize = 0;
		if(params.get("pageSize") != null && !params.get("pageSize").equals(""))
			pageSize = Integer.parseInt(params.get("pageSize"));
		
		params.remove("pageSize");
		List<ComponentInstance> componentInstances = customSOAService.getComponentInstances(locator, params);
		
		if(componentInstances != null && componentInstances.size() >= pageSize && pageSize >= 5){
			componentInstances.subList(pageSize, componentInstances.size()).clear();
		}
		
		model.addAttribute("result", componentInstances);

		return "bpm/home/subs/worstProcess";
	}
	
	@RequestMapping("/soa/composite/invoke")
	public String invokeComposite(HttpSession session,@RequestParam(required = false) Map<String, String> params, ModelMap model) throws Exception {
		
		String partition = params.get("partition");	// default
		String composite = params.get("composite");	//	ExpenseReportApplication
		//String version = params.get("version");		//	1.0
		String service = params.get("service");		// ExpenseReportProcessDirect.service
		String operation = params.get("operation");	// start
		String payloadXML = params.get("payloadXML");	//
		
		CompositeDN compositedn = new CompositeDN(partition, composite, null);
		
		/*payloadXML = "<ns1:start xmlns:ns1=\"http://xmlns.oracle.com/bpmn/bpmnProcess/ExpenseReportProcess\" xmlns:ns2=\"http://www.example.org\">\r\n" + 
				"            			<ns2:expense>\r\n" + 
				"                				<ns2:expenseNumber>0001</ns2:expenseNumber>\r\n" + 
				"                				<ns2:requesterId>bpmuser1</ns2:requesterId>\r\n" + 
				"                				<ns2:requestDate>2016-03-03T12:00:00</ns2:requestDate>\r\n" + 
				"                				<ns2:amount>10000</ns2:amount>\r\n" + 
				"                				<ns2:paymentType>corporation/individual</ns2:paymentType>\r\n" + 
				"                				<ns2:expenseType>차량유지비/접대비/복리후생비/출장비</ns2:expenseType>\r\n" + 
				"                				<ns2:justification>시스템 장애지원</ns2:justification>\r\n" + 
				"                				<ns2:dueDate>2016-03-05T12:00:00</ns2:dueDate>\r\n" + 
				"                				<ns2:outcome></ns2:outcome>\r\n" + 
				"                				<ns2:approval>\r\n" + 
				"                    					<ns2:line>approvaluser1,approvaluser2</ns2:line>\r\n" + 
				"                    					<ns2:type>SEQUENTIAL</ns2:type>\r\n" +
				"                    					<ns2:ruleset>비용처리승인규칙</ns2:ruleset>\r\n" +
				"                </ns2:approval>\r\n" + 
				"            </ns2:expense>\r\n" + 
				"        </ns1:start>"; */

		/*
		 * 초기 입력
		 * 비용 발생
		 */
		String expenseNumber = StrUtil.nvl(params.get("expenseNumber"));
		String requesterId   = StrUtil.nvl(params.get("requesterId"));
		String requestDate   = StrUtil.nvl(params.get("requestDate"));
		String amount        = StrUtil.nvl(params.get("amount"));  // 비용 콤마제거
		String paymentType   = StrUtil.nvl(params.get("paymentType"));
		String expenseType   = StrUtil.nvl(params.get("expenseType"));
		String justification = StrUtil.nvl(params.get("justification"));
		String dueDate       = StrUtil.nvl(params.get("dueDate"));
		String currency      = StrUtil.nvl(params.get("currency")); // 비용 금액 콤마 포함
		String docnum        = StrUtil.nvl(params.get("currency")); // 비용 금액 콤마 포함
		String line          = StrUtil.nvl(params.get("line"));
		String type          = StrUtil.nvl(params.get("type"));
		String ruleset       = StrUtil.nvl(params.get("ruleset"));
		
		dueDate = dueDate.substring(0, 10)+"T"+dueDate.substring(11, 19);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<ns1:start xmlns:ns1=\"http://xmlns.oracle.com/bpmn/bpmnProcess/ExpenseReportProcess\" xmlns:ns2=\"http://www.example.org\">\r\n");
		sb.append("  <ns2:expense>\r\n");
		sb.append("    <ns2:expenseNumber>").append(expenseNumber).append("</ns2:expenseNumber>\r\n");
		sb.append("    <ns2:requesterId>").append(requesterId).append("</ns2:requesterId>\r\n");
		
		sb.append("    <ns2:requestDate>").append(requestDate).append("</ns2:requestDate>\r\n");
		sb.append("    <ns2:amount>").append(amount).append("</ns2:amount>\r\n");
		sb.append("    <ns2:paymentType>").append(paymentType).append("</ns2:paymentType>\r\n");
		sb.append("    <ns2:expenseType>").append(expenseType).append("</ns2:expenseType>\r\n");
		sb.append("    <ns2:justification>").append(justification).append("</ns2:justification>\r\n");
		sb.append("    <ns2:dueDate>").append(dueDate + "+09:00").append("</ns2:dueDate>\r\n");
		sb.append("    <ns2:outcome></ns2:outcome>\r\n");
		sb.append("    <ns2:currency>").append(currency).append("</ns2:currency>\r\n"); //textAttribute16
		sb.append("    <ns2:docnum>").append(docnum).append("</ns2:docnum>\r\n"); //textAttribute15
		sb.append("    <ns2:approval>\r\n");
		sb.append("      <ns2:line>").append(line).append("</ns2:line>\r\n");
		sb.append("      <ns2:type>").append(type).append("</ns2:type>\r\n");
		sb.append("      <ns2:ruleset>").append(ruleset).append("</ns2:ruleset>\r\n");
		sb.append("    </ns2:approval>\r\n");
		sb.append("  </ns2:expense>  \r\n");
		sb.append("</ns1:start>\r\n");
		
		payloadXML = sb.toString();
		
		logger.debug("start call service...");
		logger.debug("===================================");
		logger.debug(payloadXML);
		logger.debug("===================================");
		customSOAService.invokeComposite(soaClient.getLocator(), compositedn, service, operation, payloadXML);
		logger.debug("end call service...");
		return "bpm/home/popup/close";
		
	}
}
