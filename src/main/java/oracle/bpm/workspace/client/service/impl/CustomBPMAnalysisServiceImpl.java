package oracle.bpm.workspace.client.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oracle.bpm.workspace.client.config.SOAServiceClient;
import oracle.bpm.workspace.client.dao.CustomBPMAnalysisDAO;
import oracle.bpm.workspace.client.service.CustomBPMAnalysisService;
import oracle.bpm.workspace.client.vo.CustomProcessActivityAnalysisVO;

@Service("CustomBPMAnalysisService")
public class CustomBPMAnalysisServiceImpl implements CustomBPMAnalysisService {
	
	@Resource(name="soaClient")
    protected SOAServiceClient soaClient;
	
	@Autowired
	private SqlSession sqlSession;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());	// Logger

	public List<CustomProcessActivityAnalysisVO> getProcessActivityAnalysis(String componentname) throws Exception {
		
		CustomBPMAnalysisDAO bpmAnalysisDAO = sqlSession.getMapper(CustomBPMAnalysisDAO.class);
		
	    List<CustomProcessActivityAnalysisVO> processActivityAnalysisVO = bpmAnalysisDAO.getProcessActivityAnalysis(componentname);
		
	    return processActivityAnalysisVO;
	}
	
}
