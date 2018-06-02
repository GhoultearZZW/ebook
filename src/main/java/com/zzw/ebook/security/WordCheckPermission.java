package com.zzw.ebook.security;

import java.security.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WordCheckPermission extends Permission {
    private String action;

    public WordCheckPermission(String target,String anAction){
        super(target);
        System.out.println("exec WordCheckPermission");
        action=anAction;
    }

    public String getActions(){
    	System.out.println("exec getAction");
    	System.out.println(action);
        return action;
    }

    @Override
    public boolean implies(Permission other){
    	System.out.println("exec implies");
        if(!(other instanceof WordCheckPermission)) return false;
        WordCheckPermission b =(WordCheckPermission) other;
        if(action.equals("insert")){
            return b.action.equals("insert") &&
                    getName().contains(b.getName());
        }
        else if(action.equals("avoid")){
            if(b.action.equals("avoid"))
                return b.badWordSet().containsAll(badWordSet());
            else if(b.action.equals("insert")){
                for(String badWord : badWordSet())
                    if(b.getName().contains(badWord))
                        return false;
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    public Set<String> badWordSet(){
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(getName().split(",")));
        System.out.println(set);
        System.out.println("---------------");
        return set;
    }

    public boolean equals(Object other){
        if(other == null) return false;
        if(!getClass().equals(other.getClass())) return false;
        WordCheckPermission b = (WordCheckPermission) other;
        if(!action.equals(b.action)) return false;
        if(action.equals("insert"))
            return getName().equals(b.getName());
        else if(action.equals("avoid"))
            return badWordSet().equals(b.badWordSet());
        else return false;
    }

    public int hashCode(){
        return getName().hashCode()+action.hashCode();
    }
}