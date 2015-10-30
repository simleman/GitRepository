package demo.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import demo.entity.Mydemo;

public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * 登陆拦截器
	 */
	private static final long serialVersionUID = 7713492690432432420L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String,Object>session=invocation.getInvocationContext().getSession();
		Mydemo user=(Mydemo) session.get("loginUser");
		String result=null;
		if(user!=null){
			result=invocation.invoke();
		}else{
			result="login"; 
		}
		return result;
	}

}
