package com.zzw.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zzw.ebook.model.User;
import com.zzw.ebook.service.UserService;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select * from users where password=:password and username =:username",nativeQuery = true)
    User getUserByUserName(@Param("username") String username,@Param("password")String password);

    @Query(value = "select * from users where user_id=1",nativeQuery = true)
    User getUserById();
    
    @Query(value = "select * from users where username =:username",nativeQuery=true)
    User getUserByName(@Param("username")String username);
}
