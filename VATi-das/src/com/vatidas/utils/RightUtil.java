package com.vatidas.utils;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vatidas.entity.Right1;
import com.vatidas.entity.Role;
import com.vatidas.entity.User;


public class RightUtil {

	public static int PI = 3;
	public static boolean validateRight(String namespace, String actionName, HttpServletRequest request) {
		if(namespace == null || "".equals(namespace.trim())|| "/".equals(namespace)){
			namespace = "";
		}
		if(actionName.contains("?")){
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		String url = namespace + "/" + actionName ;
		System.out.println(url+"rightUtil");
		System.out.println(request.getSession());
		HttpSession session = request.getSession();
		System.out.println(session.getServletContext());
		ServletContext sc = session.getServletContext();
		
		@SuppressWarnings("unchecked")
		Map<String, Right1> map = (Map<String, Right1>) sc.getAttribute("rightsMap");
		Right1 r = map.get(url);
		if(r == null){   //该URL不存在
			System.out.println("0");
			return false;
		}else{
			
			User u = (User) session.getAttribute("user");
			if(u == null){
				return true;
			}
			else{
				return true;
			}
			//开始过滤权限----------未完成
			/*else if(u.getRole().getRightValue() == -1){
				return true;
			}else if(u.hasRight(r)){
				return true;
			}else{
				return false;
			}*/
		}
	}
	
	//这里使用contains方法判断不出来。。。去重写equals方法吧
	public static boolean contains(Right1 r1, Role r2){
		Set<Right1> rights = r2.getRights();
		for (Right1 right1 : rights) {
			if(right1.equals(r1))
				return true;
		}
		return false;
	}
	
}
