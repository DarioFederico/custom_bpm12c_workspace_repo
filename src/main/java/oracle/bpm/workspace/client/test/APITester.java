package oracle.bpm.workspace.client.test;

import java.lang.reflect.Method;

import oracle.bpm.services.instancequery.IInstanceQueryInput;

public class APITester {
	private static final String bpmapi = "oracle.bpm.workspace.client.test.api.bpm.BPMAPISamples";
	
	public APITester() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			/**
			 * BPMAPISamples Test
			 * param : Class, Method, Parameters(title, state, processname, overdue=Y).......
			 * 
			 * */
			
			//202665 병렬
			//202690 순차
			//202647 단일
		    invoke(bpmapi, "queryShortTaskHistory", "202647");
                    //invoke(bpmapi, "alterflow", "40005", "ACT1402748003782", "ACT14002344127258");
                    //invoke(bpmapi, "getVariableValueChange", "40005");          
			
			//invoke(clname, methodName, parameters)
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Object invoke(String clname, String methodName, Object... parameters) {
		Object object = null;
		Method method;
		
		try {
			Class<?> cl = Class.forName(clname);
			
			object = cl.newInstance();
			method = object.getClass().getMethod(methodName, getParemeterClasses(parameters));
			object = method.invoke(object, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	private static Class<?>[] getParemeterClasses(Object... parameters) {
		Class<?>[] classes = new Class[parameters.length];
		for (int i = 0; i < classes.length; i++) {
			
			classes[i] = toPrimitiveType(parameters[i]);
			
		}
		
		return classes;
	}
	
	private static Class<?> toPrimitiveType(Object obj) {

		Class<?> primitive = null;
		
		if(obj == null)
			primitive = String.class;
		else if(obj.getClass().getName().equals("java.lang.Boolean"))
			primitive = Boolean.TYPE;
		else if(obj.getClass().getName().equals("java.lang.Integer"))
			primitive = Integer.TYPE;
		else if(obj.getClass().getName().equals("java.lang.Character"))
			primitive = Character.TYPE;
		else if(obj.getClass().getName().equals("java.lang.Byte"))
			primitive = Byte.TYPE;
		else if(obj.getClass().getName().equals("java.lang.Short"))
			primitive = Short.TYPE;
		else if(obj.getClass().getName().equals("java.lang.Long"))
			primitive = Long.TYPE;
		else if(obj.getClass().getName().equals("java.lang.Float"))
			primitive = Float.TYPE;
		else if(obj.getClass().getName().equals("java.lang.Double"))
			primitive = Double.TYPE;
		else
			primitive = obj.getClass();
		
		return primitive;
	}
}
