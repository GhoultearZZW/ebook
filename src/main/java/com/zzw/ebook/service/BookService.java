package com.zzw.ebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zzw.ebook.model.Book;
import com.zzw.ebook.repository.BookRepository;

@Service
@Component
public class BookService {
    @Autowired
    BookRepository bookRepo;

    public Book getBookById(int bookId){
        return null;
    }

    public List<Book> getBooks(){
        return bookRepo.findAll();
    }
    
    public Book getBookByTitle(String title) {
    	return bookRepo.getBookByTitle(title);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void MinusOnInRemaining(String title) {
    	bookRepo.MinusOneInRemaining(title);
    }
}
