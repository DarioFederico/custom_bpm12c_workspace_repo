package oracle.bpm.workspace.client.service;

import java.util.List;
import java.util.Map;

import oracle.bpel.services.bpm.common.IBPMContext;
import oracle.bpm.workspace.client.vo.InstanceVO;


public interface CustomBPMService {
	public List<InstanceVO> getQueryInstances(IBPMContext ctx, Map<String, String> params) throws Exception;
}
