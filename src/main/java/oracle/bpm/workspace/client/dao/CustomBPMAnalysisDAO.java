package oracle.bpm.workspace.client.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import oracle.bpm.workspace.client.vo.CustomProcessActivityAnalysisVO;

public interface CustomBPMAnalysisDAO {
	public List<CustomProcessActivityAnalysisVO> getProcessActivityAnalysis(@Param("componentname") String componentname);
}
