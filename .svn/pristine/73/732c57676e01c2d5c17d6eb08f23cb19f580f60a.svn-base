package oracle.bpm.workspace.client.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import oracle.bpm.workspace.client.vo.CustomBPMTrackerDataVO;
import oracle.bpm.workspace.client.vo.CustomBPMTrackerModelVO;

public interface CustomBPMTrackerDAO {
	public List<CustomBPMTrackerDataVO> getCustomBPMTrackerData(@Param("instance_id") String instance_id);
	public CustomBPMTrackerModelVO getCustomBPMTrackerModel(@Param("trackerModel") CustomBPMTrackerModelVO trackerModel);
	public void mergeCustomBPMTrackerModel(@Param("trackerModel") CustomBPMTrackerModelVO trackerModelVO);
	public void insertCustomBPMTrackerModel(@Param("trackerModel") CustomBPMTrackerModelVO trackerModelVO);
	public void updateCustomBPMTrackerModel(@Param("trackerModel") CustomBPMTrackerModelVO trackerModelVO);
	public CustomBPMTrackerDataVO getParentTrackerData(@Param("instance_id") String instance_id, @Param("activity_id") String activity_id);
}
