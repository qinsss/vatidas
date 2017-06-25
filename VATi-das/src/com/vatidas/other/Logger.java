package com.vatidas.other;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;

import com.opensymphony.xwork2.ActionContext;
import com.vatidas.entity.Log1;
import com.vatidas.entity.User;
import com.vatidas.service.ILogService;

/**
 * 日志的记录切面POJO类
 * @author qinshou
 *
 */
public class Logger {

	private ILogService logService;
	
	public void setLogService(ILogService logService){
		this.logService = logService;
	}
	public Object record(ProceedingJoinPoint pjp){
		Log1 log = new Log1();
		log.setOperateTime(new Date());
		System.out.println("为甚么没有进来");
		//取出当前用户
		ActionContext ac = ActionContext.getContext();
		if(ac != null){
			User user = (User) ac.getSession().get("user");
			if(user != null){
				log.setOperator(user.getAccount()+":"+user.getNickname());
			}
		}
		//获取方法名
		String methodName = pjp.getSignature().getName();
		log.setOperateName(methodName);
		//目标对象
		Object[] args = pjp.getArgs();
		StringBuffer buffer = new StringBuffer();
		for (Object object : args) {
			buffer.append(object+";");
		}
		log.setOperateParams(buffer.toString());
		try {
			Object proceed = pjp.proceed();
			log.setOperateResult("成功");
			if(proceed != null){
				return proceed;
			}
		} catch (Throwable e) {
			log.setOperateResult("失败");
		}finally{
			System.out.println(log);
			logService.saveLogEntity(log);
		}
		return null;
	}
	
}
