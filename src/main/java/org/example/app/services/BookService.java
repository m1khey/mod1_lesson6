package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookToRemove;
import org.example.web.dto.BookToSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

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

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public List<Book> searchBook(Integer bookToSearchById, String bookToSearchByAutor,String bookToSearchByTitle
            ,Integer bookToSearchBySize){

        logger.info("try search books");

        return bookRepo.searchItem(bookToSearchById, bookToSearchByAutor,bookToSearchByTitle
                ,bookToSearchBySize);
    }

    public boolean removeBook(BookToRemove bookToRemove) {
        String bookIdToRemove = String.valueOf(bookToRemove.getId());
        String bookAuthorToRemove = bookToRemove.getAuthor();
        String bookTitleToRemove = bookToRemove.getTitle();
        String bookSizeToRemove = String.valueOf(bookToRemove.getSize());

        logger.info(String.valueOf(bookToRemove.getId())=="");
        logger.info("try delete book" + bookIdToRemove + bookAuthorToRemove + bookTitleToRemove + bookSizeToRemove);

        boolean result = false;

        if (bookIdToRemove.isEmpty() &&
                bookAuthorToRemove.isEmpty() &&
                bookTitleToRemove.isEmpty() &&
                bookSizeToRemove.equals("null"))
        {
            logger.info("all field is empty");
            return false;
        }

        boolean checkId;
        boolean checkAuthor;
        boolean checkTitle;
        boolean checkSize;

        for (Book tmpBook :
                bookRepo.retreiveAll()) {

            checkId = checkParam(String.valueOf(tmpBook.getId()), bookIdToRemove, false);
            checkAuthor = checkParam(tmpBook.getAuthor(), bookAuthorToRemove, false);
            checkTitle = checkParam(tmpBook.getTitle(), bookTitleToRemove, false);
            checkSize = checkParam(tmpBook.getSize().toString(), bookSizeToRemove, false);

            logger.info("check status: " + checkId + checkAuthor + checkTitle + checkSize);
            if (checkId && checkAuthor && checkTitle && checkSize){
                result = bookRepo.removeItem(tmpBook.getId());
            }
        }

        return result;
    }

    public boolean checkParam (String bookParam, String frontParam, boolean isFilter){
        boolean result = false;
        if (bookParam == null || frontParam == null || frontParam.equals("") || frontParam.equals("null") ) {
            result = true;
        }
        else {
            if (isFilter) {
                if (bookParam.contains(frontParam)) {
                    result = true;
                } else {
                    try{
                        if (bookParam.matches(frontParam)){
                            result = true;
                        }
                    }
                    catch(PatternSyntaxException e){
                        result = false;
                    }
                }
            }
            else{
                if (bookParam.equals(frontParam)) {
                    result = true;
                } else{
                    try{
                        if (bookParam.matches(frontParam)){
                            result = true;
                        }
                    }
                    catch(PatternSyntaxException e){
                        result = false;
                    }
                }
            }
        }
        return result;
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
