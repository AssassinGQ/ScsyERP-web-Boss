/**
 * className：ListenException.java
 * @version：1.0
 * date: 2014-12-16-上午10:12:37
 * Copyright (c)  2014中益智正公司-版权所有
 */
package cn.AssassinG.ScsyERP.WebBoss.Intercepts;

import cn.AssassinG.ScsyERP.common.exceptions.DaoException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;


/**
 * className：ExceptionInterceptorLog <br>
 * Function：异常的处理 拦截 <br>
 */
public class ExceptionInterceptorLog implements ThrowsAdvice {

	private static final Log log = LogFactory.getLog(ExceptionInterceptorLog.class);

	/**
	 * 对未知异常的处理. <br>
	 * Method method 执行的方法 Object[] args <br>
	 * 方法参数 Object target <br>
	 * 代理的目标对象 Throwable RuntimeException 产生的异常 <br>
	 */
	public void afterThrowing(Method method, Object[] args, Object target, DaoException ex) {
		log.info("==>ExceptionInterceptorLog.DaoException");
		log.info("==>errCode:" + ex.getCode() + " errMsg:" + ex.getMsg());
		log.info("==>" + ex.fillInStackTrace());
	}

	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {

		log.error("==>ExceptionInterceptorLog.Exception");

		log.error("==>Error class: " + target.getClass().getName());
		log.error("==>Error method: " + method.getName());

		for (int i = 0; i < args.length; i++) {
			log.error("==>args[" + i + "]: " + args[i]);
		}

		log.error("==>Exception class: " + ex.getClass().getName());
		log.error("==>" + ex.fillInStackTrace());
		ex.printStackTrace();
	}

}
