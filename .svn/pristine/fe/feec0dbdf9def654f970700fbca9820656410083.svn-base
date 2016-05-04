package oracle.bpm.workspace.client.util;

import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hajimaro
 *
 */
public class CommonUtility {
	
    public static Class<?> loadClass(String className) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		classLoader = (classLoader == null) ? ClassLoader.getSystemClassLoader() : classLoader;
		
    	Class<?> clazz = classLoader.loadClass(className);
        return (clazz == null) ? Class.forName(className) : clazz;
    }

    /*
	 *map (
	 *		titles list (
	 * 			String : 컬럼??	 * 		),
	 * 		contents list (
	 * 			map (
	 * 				String : 컬럼??
	 * 				Object : 컬럼??	 * 			)
	 * 		)
	 * )
	 */
	public static Map<String, Object> transDatasList(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = null;
        
        Map<String, Object> data = new HashMap<String, Object>();
        Collection<String> titles = new ArrayList<String>();
        Collection<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

        try {
            rsmd = rs.getMetaData();

            for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
        		titles.add(rsmd.getColumnName(colIdx).toLowerCase());
            
            while(rs.next()) {
            	Map<String, Object> m = new HashMap<String, Object>();

            	for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
            		if (rs.getObject(rsmd.getColumnName(colIdx)) instanceof Clob) {
						StringBuffer output = new StringBuffer();
						Reader input = rs.getCharacterStream(rsmd.getColumnName(colIdx));
						char[] buffer = new char[1024];
						int byteRead;
						while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
							output.append(buffer, 0, byteRead);
						}
						input.close();

						m.put(rsmd.getColumnName(colIdx).toLowerCase(), output.toString());
					} else {
	            		m.put(rsmd.getColumnName(colIdx).toLowerCase(), rs.getObject(rsmd.getColumnName(colIdx)));
					}
            	
            	contents.add(m);
            }
            
            data.put("titles", titles);
            data.put("contents", contents);
            
            return data;
		} catch (SQLException e) {
			throw e;
		} finally {
		}
	}
	
	/*
	 *map (
	 *		titles list (
	 * 			String : 컬럼??	 * 		),
	 * 		contents map (
	 * 			String : ??컬럼??
	 * 			map (
	 * 				String : 컬럼??
	 * 				Object : 컬럼??	 * 			)
	 * 		)
	 * )
	 */
	public static Map<String, Object> transDatasMap(ResultSet rs, String key_field) throws Exception {
        ResultSetMetaData rsmd = null;
        
        Map<String, Object> data = new HashMap<String, Object>();
        Collection<String> titles = new ArrayList<String>();
        Map<String, Object> contents = new HashMap<String, Object>();

        try {
            rsmd = rs.getMetaData();

            for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
        		titles.add(rsmd.getColumnName(colIdx).toLowerCase());
            
            while(rs.next()) {
            	Map<String, Object> m = new HashMap<String, Object>();

            	for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
            		if (rs.getObject(rsmd.getColumnName(colIdx)) instanceof Clob) {
						StringBuffer output = new StringBuffer();
						Reader input = rs.getCharacterStream(rsmd.getColumnName(colIdx));
						char[] buffer = new char[1024];
						int byteRead;
						while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
							output.append(buffer, 0, byteRead);
						}
						input.close();

						m.put(rsmd.getColumnName(colIdx).toLowerCase(), output.toString());
					} else {
	            		m.put(rsmd.getColumnName(colIdx).toLowerCase(), rs.getObject(rsmd.getColumnName(colIdx)));
					}
            	
            	contents.put(String.valueOf(m.get(key_field)), m);
            }
            
            data.put("titles", titles);
            data.put("contents", contents);
            
            return data;
		} catch (SQLException e) {
			throw e;
		} finally {
		}
	}

	/*
	 *map (
	 *		titles list (
	 * 			String : ??컬럼??	 * 		),
	 *		map (
	 *			String : ?? 컬럼??
	 *			Object : ?? 컬럼??	 * 		)
	 *)
	 */
	public static Map<String, Object> transDatasMap(ResultSet rs, String key_field, String value_field, boolean returnTitles) throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        Collection<String> titles = new ArrayList<String>();
        Map<String, Object> contents = new HashMap<String, Object>();

        try {
            while(rs.next()) {
            	contents.put(String.valueOf(rs.getObject(key_field)), rs.getObject(value_field));
            }
            
            titles.addAll(contents.keySet());
            
            data.put("titles", titles);
            data.put("contents", contents);

            return (returnTitles) ? data : contents;
		} catch (SQLException e) {
			throw e;
		} finally {
		}
	}

	/*
	 *Map (
	 *		titles List (
	 * 			String : 컬럼??	 * 		),
	 * 		contents Map (
	 * 			String : 분류?? 컬럼??
	 * 			classified_data List (
	 * 				Map (
	 * 					String : 컬럼??
	 * 					Object : 컬럼??	 * 				)
	 * 			)
	 * 		)
	 * )
	 */
	public static Map<String, Object> transDatasClassifiedList(ResultSet rs, String classify_key_field) throws Exception {
        ResultSetMetaData rsmd = null;
        
        Map<String, Object> data = new HashMap<String, Object>();
        Collection<String> titles = new ArrayList<String>();
        Map<Object, Object> contents = new HashMap<Object, Object>();

        try {
            rsmd = rs.getMetaData();

            for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
        		titles.add(rsmd.getColumnName(colIdx).toLowerCase());
            
            while(rs.next()) {
            	Map<String, Object> m = new HashMap<String, Object>();

            	for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
            		if (rs.getObject(rsmd.getColumnName(colIdx)) instanceof Clob) {
						StringBuffer output = new StringBuffer();
						Reader input = rs.getCharacterStream(rsmd.getColumnName(colIdx));
						char[] buffer = new char[1024];
						int byteRead;
						while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
							output.append(buffer, 0, byteRead);
						}
						input.close();

						m.put(rsmd.getColumnName(colIdx).toLowerCase(), output.toString());
					} else {
	            		m.put(rsmd.getColumnName(colIdx).toLowerCase(), rs.getObject(rsmd.getColumnName(colIdx)));
					}
            	if(!contents.containsKey(m.get(classify_key_field)))
            		contents.put(m.get(classify_key_field), new ArrayList<Map<String, Object>>());
            	
            	((Collection<Object>) contents.get(m.get(classify_key_field))).add(m);
            }
            
            data.put("titles", titles);
            data.put("contents", contents);
            
            return data;
		} catch (SQLException e) {
			throw e;
		} finally {
		}
	}

	/*
	 *map (
	 *		titles list (
	 * 			String : 컬럼??	 * 		),
	 * 		contents map (
	 *			String : 컬럼??
	 *			Object : 컬럼??	 * 		)
	 * )
	 */
	public static Map<String, Object> transDataMap(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = null;
        
        Map<String, Object> data = new HashMap<String, Object>();
        Collection<String> titles = new ArrayList<String>();
        Map<String, Object> contents = new HashMap<String, Object>();

        try {
            rsmd = rs.getMetaData();
            
            for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
        		titles.add(rsmd.getColumnName(colIdx).toLowerCase());

            if(rs.next())
            	for (int colIdx = 1; colIdx <= rsmd.getColumnCount(); colIdx++)
            		if (rs.getObject(rsmd.getColumnName(colIdx)) instanceof Clob) {
						StringBuffer output = new StringBuffer();
						Reader input = rs.getCharacterStream(rsmd.getColumnName(colIdx));
						char[] buffer = new char[1024];
						int byteRead;
						while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
							output.append(buffer, 0, byteRead);
						}
						input.close();

						/*
						BufferedReader reader = new BufferedReader(rs.getCharacterStream(rsmd.getColumnName(colIdx)));
						String line = null;
						StringBuffer text = new StringBuffer();
						while ((line = reader.readLine()) != null)
							text.append(line).append("\r\n");

						cf)
						Reader reader = new StringReader(data.getContent());
						pstmt.setCharacterStream(2, reader, data.getContent().length());
						*/
						
						contents.put(rsmd.getColumnName(colIdx).toLowerCase(), output.toString());
					} else {
	            		contents.put(rsmd.getColumnName(colIdx).toLowerCase(), rs.getObject(rsmd.getColumnName(colIdx)));
					}
            
            data.put("titles", titles);
            data.put("contents", contents);
            
            return data;
		} catch (SQLException e) {
			throw e;
		} finally {
		}
	}

	/*
	 *map (
	 *	String : ??라미터??
	 *	Object : ??라미터??	 * )
	 */
	public static Map<String, Object> transDataMap(HttpServletRequest request) throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();

        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for(String name : parameterMap.keySet()) {
            	String[] values = parameterMap.get(name);
            	parameters.put(name, values.length > 1 ? values : values[0]);
            }

            return parameters;
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	public static void setCookie(HttpServletResponse response, String name, String value) throws Exception {
		value = java.net.URLEncoder.encode(value.toString(),"UTF-8");
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain("skylove.com"); 
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static String getCookie(HttpServletRequest request, String CookieName) throws Exception {
		Cookie [] cookies = request.getCookies();
		if(cookies==null) return null;
		String value = "";
		for(int i=0;i<cookies.length;i++) {
			if(CookieName.equals(cookies[i].getName())) {
				value = java.net.URLDecoder.decode(cookies[i].getValue(),"UTF-8");
				break;
			}
		}
		return value;
	}
	
	public static String getParameterStr (Map<String,Object> parameters, String key){
		
		Object obj = parameters.get(key);
		
		if(obj == null){
			return "";
		}else{
			return (String) obj;
		}
	}
	
	public static String removeDelimitedRepeatingString(String delemitedStr) {
		
		String [] arr = delemitedStr.split(",");
		String remove_delimited_repeating_value = "";
		
		if(arr.length > 0) {
			for(String value : arr) {
				
				if(value.contains(":")) {
					String [] arr2 = value.split(":");
					
					for(String value2 : arr2) {
						if(!remove_delimited_repeating_value.contains(value2))
							remove_delimited_repeating_value += value2 + ",";
					}
				} else if(!remove_delimited_repeating_value.contains(value))
					remove_delimited_repeating_value += value + ",";
			}
			return remove_delimited_repeating_value.substring(0, remove_delimited_repeating_value.length()-1);
		} else
			return remove_delimited_repeating_value;
	}
	
	public static List<HashMap<String,String>> getWeekInfoByMonth(String year, String month){
		List<HashMap<String,String>> weekList = new ArrayList<HashMap<String,String>>();
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(year),Integer.parseInt(month)-1,0);
		
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	    int nextVar = 0;
	    int nextWeek = 1;
		int startDay = cal.get(Calendar.DAY_OF_WEEK);
		int firstDay = 1;
		
		nextVar = startDay+1;
		HashMap<String,String> map = null;
		
		for(int i=1; i<=lastDay; i++){
			if(firstDay == 1){
				map = new HashMap<String,String>(); 
				map.put("week", Integer.toString(nextWeek));
				map.put("firstDate", dayByday(Integer.toString(i))+"/"+dayByday(month)+"/"+year);
				firstDay += startDay == 7?startDay:firstDay+(7-startDay-1);
			}
			
			if(firstDay == i){
				map = new HashMap<String,String>(); 
				map.put("week", Integer.toString(nextWeek));
				map.put("firstDate", dayByday(Integer.toString(i))+"/"+dayByday(month)+"/"+year);
				firstDay += 7;
			}
			
			if(nextVar % 7 == 0 || lastDay==i){
				map.put("lastDate", dayByday(Integer.toString(i))+"/"+dayByday(month)+"/"+year);
				weekList.add(map);
				nextWeek++;
			}
			nextVar++;
		}
		return weekList;
	}
	
	public static String dayByday(String day){
		if(day.length() == 1)
			return "0"+day;
		return day;
	}
	
	public static String getDateString(Calendar calDate, Locale userLocale, TimeZone timeZone) {
		if (calDate == null)
			return "";
		else {
			DateFormat df = getDateFormatInstance(userLocale);
			return getTimeZoneBasedDateString(df, calDate, timeZone);
		}
	}
	
	public static String getDateString(Date date, Locale userLocale, TimeZone timeZone) {
		if (date == null)
			return "";
		else {
			Calendar calDate = Calendar.getInstance();
			calDate.setTime(date);
			DateFormat df = getDateFormatInstance(userLocale);
			return getTimeZoneBasedDateString(df, calDate, timeZone);
		}
	}
	
	private static DateFormat getDateFormatInstance(Locale userLocale) {
		String localeLanguage = userLocale.getLanguage();
		if(localeLanguage.equals(""))
			localeLanguage = "DEFAULT";
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm"); //DateFormat.getDateInstance(DateFormat.MEDIUM, userLocale);
	}
	
	private static String getTimeZoneBasedDateString(DateFormat df, Calendar calDate, TimeZone timeZone) {
	    // get existing timezone
	    TimeZone defaultTimeZone = df.getTimeZone();
	    if(timeZone != null)
	    	df.setTimeZone(timeZone);

	    String formattedDate = df.format(calDate.getTime());
	    
	    // reset to existing timezone
	    df.setTimeZone(defaultTimeZone);

	    return formattedDate;
	}
	
	public static String notNull(String input) {
		if (input == null) return "";
		return input;
	}
	
	public static Object notNull(Object input) {
		if (input == null) return "";
		
		return input;
	}
}