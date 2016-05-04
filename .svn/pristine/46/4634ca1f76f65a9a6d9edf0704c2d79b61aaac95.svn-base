package oracle.bpm.workspace.client.util;

import java.util.HashMap;

import oracle.soa.management.facade.CompositeInstance;

public class OBPMUtility {

	public static Object defaultToNull(Object src, Object value) {
		return (src == null) ? value : src;
	}
	
	// 11g BPM : 진행중 : OPEN, 완료 : COMPLETED, 오류 : ERRORED, 취소 : CANCELED, 강제종료 : ABORTED, 사용안됨 : STALE
	// 10g BPEL : 진행중 : 1,2 완료 : 5,7,8,9 오류 : 3,6
	// 11g BPM -> PRISM
	// OPEN (1,2), COMPLETED(5), ERRORED(3,6), CANCELED(7), ABORTED(8), STALE(9)
	public static String getInstanceStateTxt(int state) {
		String stateTxt = "";
		switch(state) {
			case 1 : stateTxt = "진행중"; break;
			case 2 : stateTxt = "진행중 (일시중지)"; break;
			case 3 : stateTxt = "진행중 (오류발생)"; break;
			case 4 : stateTxt = "완료 (보류중 취소)"; break;
			case 5 : stateTxt = "완료됨 (정상)"; break;
			case 6 : stateTxt = "완료됨 (오류발생)"; break;
			case 7 : stateTxt = "완료됨 (취소됨)"; break;
			case 8 : stateTxt = "완료됨 (중지됨)"; break;
			case 9 : stateTxt = "완료됨 (사용되지 않음)"; break;
			default : stateTxt = "시작안됨";
		}
		
		return stateTxt;
	}
	
	public static String getActivityEvalPoint(String state) {
		String evalPoint = "activation";
		
		if(state.equals("OPEN"))
			evalPoint = "activation";
		
		else if(state.equals("COMPLETED") || state.equals("CANCELED") || state.equals("ABORTED") || state.equals("STALE")) 
			evalPoint = "completion";
		
		else if(state.equals("ERRORED")) 
			evalPoint = "fault";
		
			
		return evalPoint;
	}
	
	/*  public static final int	PRIORITY_NORMAL	5
		public static final int	STATE_CLOSED_ABORTED	8	// ABORT
		public static final int	STATE_CLOSED_CANCELLED	7	// CANCEL
		public static final int	STATE_CLOSED_COMPLETED	5
		public static final int	STATE_CLOSED_FAULTED	6
		public static final int	STATE_CLOSED_PENDING_CANCEL	4
		public static final int	STATE_CLOSED_STALE	9
		public static final int	STATE_INITIATED	0
		public static final int	STATE_OPEN_FAULTED	3
		public static final int	STATE_OPEN_RUNNING	1
		public static final int	STATE_OPEN_SUSPENDED	2
	 * */
	
	// BPM, COMPONENT, CUBE_INSTANCE 의 상태가 서로 상이함.
	// 확인이 필요함.....
	public static String getCompositeStateAsString (int state) 

    {
		if (state == CompositeInstance.STATE_RUNNING)	// Component Instance : 0, Cube Instance : 1

            return ("running");
		
		else if (state == CompositeInstance.STATE_RECOVERY_REQUIRED)	// 1

            return ("recovery_required");
		
        if (state == CompositeInstance.STATE_COMPLETED_SUCCESSFULLY)	// 2

            return ("completed");

        else if (state == CompositeInstance.STATE_FAULTED)	// 3

            return ("faulted");
        
        else if (state == CompositeInstance.STATE_TERMINATED_BY_USER)	// 4

            return ("terminated_by_user");
        
        else if (state == CompositeInstance.STATE_SUSPENDED)	// 5

            return ("suspended");
        
        else if (state == CompositeInstance.STATE_STALE)	// 6

            return ("stale");
        
        else 

            return ("all");
    }
	
	public static int getCompositeState (String state) 

    {
        if (state.equals("completed"))
            return CompositeInstance.STATE_COMPLETED_SUCCESSFULLY;

        else if (state.equals("faulted"))
            return CompositeInstance.STATE_FAULTED;
        
        else if (state.equals("recovery"))
            return CompositeInstance.STATE_RECOVERY_REQUIRED;
        
        else if (state.equals("running"))
            return CompositeInstance.STATE_RUNNING;
        
        else if (state.equals("stale"))
            return CompositeInstance.STATE_STALE;
        
        else 
            return -1;
    }
	
	public static int getinstanceStateCodeForPrism(String state) {
		int prismInstanceCode = 0;
		
		if(state.equals("OPEN"))
			prismInstanceCode = 1;
		
		else if(state.equals("COMPLETED")) 
			prismInstanceCode = 5;
		
		else if(state.equals("ERRORED")) 
			prismInstanceCode = 6;
		
		else if(state.equals("CANCELED")) 
			prismInstanceCode = 7;
		
		else if(state.equals("ABORTED")) 
			prismInstanceCode = 8;
		
		else if(state.equals("STALE")) 
			prismInstanceCode = 9;
		
		else prismInstanceCode = 0;
			
		return prismInstanceCode;
			
	}
	
	// for BGF Evaluation Process....
	public static String getProcessActivityNameKo(String activityName) {
		HashMap<String, String> activityMap = new HashMap<String, String>();
		// 그때 그때 달라요.....
		activityMap.put("CreateMeeting", "회의진행");
		activityMap.put("EvaluationRequest", "심사요청");
		activityMap.put("ReceiveEvaluationResult", "심사결과");
		activityMap.put("ConfirmRequest", "요청확인");
		activityMap.put("Verification", "검증");
		activityMap.put("RequestDecision", "판단요청");
		activityMap.put("ReceiveDecision", "판단");
		activityMap.put("ManagerDecision", "팀장판단");
		activityMap.put("mailMessage", "메일작성");
		activityMap.put("NotifyResult", "결과통보");
		
		return (activityMap.get(activityName) == null ? activityName : activityMap.get(activityName));
	}
}
