package oracle.bpm.workspace.client.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.bpm.workspace.client.vo.TaskVO;

public class TestMapLog {
	private static Logger logger = LoggerFactory.getLogger("oracle.bpm.workspace.client.util.TestMapLog");	// Logger
	
	public static void testMapLog(Map map){
		Set<String> key = map.keySet();
		logger.debug("================================ testLog from ================================");
		for (String str : key) {
			logger.debug("testLog str : ["+str+"]");
		}
		logger.debug("================================ testLog end  ================================");
	}
	
	public static void testListLog(List<Map> list){
		if(list != null && list.size() > 0){
			for (Map map : list) {
				testMapLog(map);
			}
		}
	}
	
	public static void testVOLog(TaskVO vo){
		String str = vo.toString();
		logger.debug("================================ testLog TaskVO from ================================");
		logger.debug("testLog TaskVO str : ["+str+"]");
		logger.debug("================================ testLog TaskVO end  ================================");
	}
	
	public static void testListTaskVOLog(List<TaskVO> tasks){
		if(tasks != null && tasks.size() > 0){
			for (TaskVO vo : tasks) {
				testVOLog(vo);
			}
		}
	}
}
