package com.zzw.ebook.backup;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class backup {
	private String serverUrl;
	private String username;
	private String password;
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String DBbackup(String backupPath,String dbName)throws IOException{
		String backupFile = backupPath+ new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".sql";
		String mysql =backupPath+"mysqldump "+"--host="+serverUrl+" --user=" + username + " --password="+ password + " --opt " + dbName + "> "+backupFile ;
		java.lang.Runtime.getRuntime().exec("cmd /c "+mysql);
		return backupFile;
	}
	
	public void restore(String restoreFile,String dbName,String path)throws Exception{
		String mysql = path+"mysql "+"-h"+serverUrl+" -u" + username + " -p"+ password + " " + dbName + " < " + restoreFile;
		java.lang.Runtime.getRuntime().exec("cmd /c " + mysql);
	}
}
