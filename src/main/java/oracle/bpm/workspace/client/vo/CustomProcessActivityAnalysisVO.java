package oracle.bpm.workspace.client.vo;


@SuppressWarnings("serial")
public class CustomProcessActivityAnalysisVO {
	private String componentname = "";
	private String label = "";
	private String activityid = "";
	private int activitycnt = 0;
	private int activityavglt = 0;
	
	public String getComponentname() {
		return componentname;
	}
	public void setComponentname(String componentname) {
		this.componentname = componentname;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getActivityid() {
		return activityid;
	}
	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
	public int getActivitycnt() {
		return activitycnt;
	}
	public void setActivitycnt(int activitycnt) {
		this.activitycnt = activitycnt;
	}
	public int getActivityavglt() {
		return activityavglt;
	}
	public void setActivityavglt(int activityavglt) {
		this.activityavglt = activityavglt;
	}
	
}
