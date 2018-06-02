package com.zzw.ebook.security;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

public class LoginManager {

	  public static boolean login(HttpServletRequest request) {
	    try {
	      String username = request.getParameter("username");
	      String password = request.getParameter("password");
	      //此处指定了使用配置文件的“Sample”验证模块，对应的实现类为SampleLoginModule
	      LoginContext lc = new LoginContext("Sample", new SampleCallbackHandler(
	          username, password));
	      lc.login();// 如果验证失败会抛出异常
	      return true;
	    } catch (LoginException e) {
	      e.printStackTrace();
	      return false;
	    } catch (SecurityException e) {
	      e.printStackTrace();
	      return false;
	    }
	  }

	}
