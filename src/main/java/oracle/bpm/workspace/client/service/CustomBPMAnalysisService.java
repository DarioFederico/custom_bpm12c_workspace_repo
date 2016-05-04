package oracle.bpm.workspace.client.service;

import java.util.List;

import oracle.bpm.workspace.client.vo.CustomProcessActivityAnalysisVO;


public interface CustomBPMAnalysisService {
	public List<CustomProcessActivityAnalysisVO> getProcessActivityAnalysis(String componentname) throws Exception;
}
