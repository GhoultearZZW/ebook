package com.zzw.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.ebook.model.User;

public interface UserReadRepository extends JpaRepository<User, Integer>{

}
