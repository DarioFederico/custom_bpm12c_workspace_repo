package oracle.bpm.workspace.client.util;

public class StrUtil {

	/**
     * null값 체크 후 문자열 리턴
     * @return String
     */
    public static String nvl(String param) {
        if ( param == null || "".equals(param) ) return "";
        else return param.trim();
    }

    /**
     * null값 체크 후 대체 문자열 리턴
     * @return String
     */
    public static String nvl(String param1,String param2) {
        if ( param1 == null || "".equals(param1) ) return nvl(param2);
        else return param1.trim();
    }
    
}
