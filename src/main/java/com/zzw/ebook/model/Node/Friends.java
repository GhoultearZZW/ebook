package com.zzw.ebook.model.Node;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Friends extends BaseEntity{

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

