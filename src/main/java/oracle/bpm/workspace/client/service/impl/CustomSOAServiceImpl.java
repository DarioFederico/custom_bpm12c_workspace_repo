package oracle.bpm.workspace.client.service.impl;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.service.CustomSOAService;
import oracle.soa.api.XMLMessageFactory;
import oracle.soa.api.invocation.DirectConnection;
import oracle.soa.api.message.Message;
import oracle.soa.management.CompositeDN;
import oracle.soa.management.facade.ComponentInstance;
import oracle.soa.management.facade.Locator;
import oracle.soa.management.util.ComponentInstanceFilter;

@Service("CustomSOAService")
public class CustomSOAServiceImpl implements CustomSOAService {
	
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger
	
	@Override
	public List<ComponentInstance> getComponentInstances(Locator locator, Map<String, String> params) throws Exception {
		ComponentInstanceFilter cif = new ComponentInstanceFilter();
		
		cif.setEngineType(Locator.SE_BPMN);
		
		if(params.get("pageSize") != null && !params.get("pageSize").equals(""))
			cif.setPageSize(Integer.parseInt(params.get("pageSize")));
		if(params.get("componentName") != null && !params.get("componentName").equals(""))
			cif.setComponentName(params.get("componentName"));
		if(params.get("flowId") != null && !params.get("flowId").equals(""))
			cif.setFlowId(Long.parseLong(params.get("flowId")));
		if(params.get("componentInstanceId") != null && !params.get("componentInstanceId").equals(""))
			cif.setId("bpmn:" + params.get("componentInstanceId"));
		if(params.get("state") != null && !params.get("state").equals(""))
			cif.setNormalizedState(Integer.parseInt(params.get("state")));		// 0 : 진행중, 2 : 완료
		
		if(params.get("pageStart") != null && !params.get("pageStart").equals(""))
			cif.setPageStart(Integer.parseInt(params.get("pageStart")));		// 0
		if(params.get("orderBy") != null && !params.get("orderBy").equals(""))
			cif.setOrderBy(Integer.parseInt(params.get("orderBy")));		// ORDER_BY_CREATION_DATE_DESC = 0
		
		/*
		 * 2016.03.09 추가  compositeDN
		 */
		String partition = params.get("partition");	// default
		String composite = params.get("composite");	//	ExpenseReportApplication
		logger.debug(" ======================= ");
		logger.debug(" partition : "+partition);
		logger.debug(" composite : "+composite);
		logger.debug(" ======================= ");
		if(partition != null && !"".equals(partition) && composite != null && !"".equals(composite)){
			CompositeDN compositedn = new CompositeDN(partition, composite, null);
			cif.setCompositeDN(compositedn);
		}
		
		//(new int[] { Integer.parseInt(params.get("state")) });
		
		logger.debug("STATE_COMPLETED_SUCCESSFULLY : " + String.valueOf(ComponentInstance.STATE_COMPLETED_SUCCESSFULLY));
		logger.debug("STATE_FAULTED : " + String.valueOf(ComponentInstance.STATE_FAULTED));
		logger.debug("STATE_RECOVERED : " + String.valueOf(ComponentInstance.STATE_RECOVERED));
		logger.debug("STATE_RECOVERY_REQUIRED : " + String.valueOf(ComponentInstance.STATE_RECOVERY_REQUIRED));
		logger.debug("STATE_RUNNING : " + String.valueOf(ComponentInstance.STATE_RUNNING));
		logger.debug("STATE_STALE : " + String.valueOf(ComponentInstance.STATE_STALE));
		logger.debug("STATE_SUSPENDED : " + String.valueOf(ComponentInstance.STATE_SUSPENDED));
		logger.debug("STATE_TERMINATED_BY_USER : " + String.valueOf(ComponentInstance.STATE_TERMINATED_BY_USER));
		
		List<ComponentInstance> componentInstances = locator.getComponentInstances(cif);
		
		/*for(ComponentInstance componentInstance : componentInstances) {
			logger.debug("componentInstance.getComponentName() : " + componentInstance.getComponentName());
			logger.debug("componentInstance.getCreator() : " + componentInstance.getCreator());
			logger.debug("componentInstance.getFlowId() : " + componentInstance.getFlowId());
			logger.debug("componentInstance.getId() : " + componentInstance.getId());
			logger.debug("componentInstance.getState() : " + componentInstance.getState());
			//logger.debug("getTrackingState(componentInstance.getState()) : " + getTrackingState(componentInstance.getState()));
			logger.debug("componentInstance.getServiceEngine() : " + componentInstance.getServiceEngine());
			logger.debug("componentInstance.getStatus() : " + componentInstance.getStatus());
			logger.debug("componentInstance.getCikey() : " + componentInstance.getCikey());
			logger.debug("componentInstance.getCikey() : " + componentInstance.getNormalizedState());
			logger.debug("componentInstance.getNormalizedStateAsString() : " + componentInstance.getNormalizedStateAsString());
			logger.debug("componentInstance.getNormalizedState() : " + componentInstance.getNormalizedState());
		}*/
		
		return componentInstances;
	}
	
	@Override
	public void invokeComposite(Locator locator, CompositeDN compositedn, String service, String operation, String payloadXML) throws Exception {
		
		DirectConnection dc = null;
	    try {
	    	dc = locator.createDirectConnection(compositedn, service);
	         // Parse the XML request message
	         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	         
	         dbf.setNamespaceAware(true);
	         
	         Document doc =
	           dbf.newDocumentBuilder().parse(new InputSource(new StringReader(payloadXML)));

	         // Prepare the payload for inclusion in the Message object
	         Map<String, Element> payload = new HashMap<String, Element>();
	         payload.put("parameters", doc.getDocumentElement());

	         Message<Element> request = XMLMessageFactory.getInstance().createMessage(payload);

	         dc.post(operation, request);
	    } finally {
	    	dc.close();
	    }
	}
}
