package com.zzw.ebook.controller;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.swing.Spring;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzw.ebook.model.User;
import com.zzw.ebook.repository.UserRepository;
import com.zzw.ebook.security.SamplePrincipal;
import com.zzw.ebook.service.UserService;
import com.zzw.ebook.utils.SpringUtil;

public class SampleLoginModule implements LoginModule{
	@Autowired
	UserService userService= (UserService) SpringUtil.getBean(UserService.class);
	
	//userService = (UserService) SpringUtil.getBean("UserService");
	
	private boolean isAuthenticated = false;
	private CallbackHandler callbackHandler;
	private Subject subject;
	private SamplePrincipal principal;
	  
	public void initialize(Subject subject, CallbackHandler callbackHandler,
		Map sharedState, Map options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}

	@SuppressWarnings({ "unused", "finally" })
	public boolean login() throws LoginException {
	try {
	NameCallback nameCallback = new NameCallback("username");
	PasswordCallback passwordCallback = new PasswordCallback("password",
		          false);
	final Callback[] calls = new Callback[] { nameCallback, passwordCallback };

		      // 获取用户数据
	callbackHandler.handle(calls);
	String username = nameCallback.getName();
	String password = String.valueOf(passwordCallback.getPassword());

		      // TODO 验证，如：查询数据库、LDAP。。。
	System.out.println("here111");
	User user = userService.CheckUser(username, password);
	System.out.println("here222");
	if (user != null) {// 验证通过
		principal = new SamplePrincipal(username);
		isAuthenticated = true;
	} else {
		throw new LoginException("user or password is wrong");
	}

	} catch (IOException e) {
		throw new LoginException("no such user");
	} catch (UnsupportedCallbackException e) {
		throw new LoginException("login failure");
	}finally {
		return isAuthenticated;
	}
	}

		  /**
		   * 验证后的处理,在Subject中加入用户对象
		   */
	public boolean commit() throws LoginException {
		if (isAuthenticated) {
		   subject.getPrincipals().add(principal);
		} else {
		   throw new LoginException("Authentication failure");
		}
		return isAuthenticated;
	}

	public boolean abort() throws LoginException {
		return false;
	}

	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(principal);
		principal = null;
		return true;
	}
}
