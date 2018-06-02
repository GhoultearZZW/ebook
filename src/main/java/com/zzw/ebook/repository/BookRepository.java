package com.zzw.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzw.ebook.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
	
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	@Query(value = "select * from books where title=:title",nativeQuery = true)
	Book getBookByTitle(@Param("title") String title);
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.SERIALIZABLE)
	@Query(value = "update books set remaining = remaining - 1 where title =:title",nativeQuery = true)
	void MinusOneInRemaining(@Param("title")String title);
}

