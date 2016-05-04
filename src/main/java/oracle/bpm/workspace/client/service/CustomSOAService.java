package oracle.bpm.workspace.client.service;

import java.util.List;
import java.util.Map;

import oracle.soa.management.CompositeDN;
import oracle.soa.management.facade.ComponentInstance;
import oracle.soa.management.facade.Locator;

public interface CustomSOAService {
	public List<ComponentInstance> getComponentInstances(Locator locator, Map<String, String> params) throws Exception;
	public void invokeComposite(Locator locator, CompositeDN compositedn, String service, String operation, String payloadXML) throws Exception;
}
