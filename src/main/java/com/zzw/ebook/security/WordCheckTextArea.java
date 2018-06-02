package com.zzw.ebook.security;

import javax.swing.JTextArea;

public class WordCheckTextArea extends JTextArea {
    public boolean append(String text,String category){
    	WordCheckPermission p1 = new WordCheckPermission(category,"avoid");
        WordCheckPermission p = new WordCheckPermission(text,"insert");
        System.out.println(category);
        System.out.println(text);
        //SecurityManager manager = System.getSecurityManager();
        SecurityManager manager = new SecurityManager();
        System.setSecurityManager(manager);
        {
        	System.out.println("here");
            //manager.checkPermission(p);
        	if(p1.implies(p)){
        		manager = null;
        		System.out.println("it is here true");
        		return true;
        	}
        	else {
        		manager = null;
        		System.out.println("it is here false");
        		return false;
        	}
        }
    }
}