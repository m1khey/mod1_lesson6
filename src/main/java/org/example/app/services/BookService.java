package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public List<Book> searchBook(Object bookToSearch){
        return bookRepo.searchItem(bookToSearch);
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBook(Object bookIdToRemove) {
        return bookRepo.removeItem(bookIdToRemove);
    }

    public List<String> getFiles(){
        return bookRepo.getFiles();
    }

    public void addFile(String file){
        bookRepo.addFile(file);
    }

    private void defaultInit(){
        logger.info("default INIT in book service");
    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book service");
    }
}
