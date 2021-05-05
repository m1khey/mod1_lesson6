package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {

        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public List<Book> searchItem(Object bookToSearch) {
        List<Book> searchBookList = new ArrayList<>();

        for (int i = 0; i <repo.size() ; i++) {
            if (repo.get(i).getId().toString().equals(bookToSearch.toString()) ||
                    repo.get(i).getAuthor().equals(bookToSearch) ||
                    repo.get(i).getTitle().equals(bookToSearch) ||
                    repo.get(i).getSize().toString().equals(bookToSearch.toString())) {
                logger.info("search book completed: " + repo.get(i));
                searchBookList.add(repo.get(i));
            }
        }
        if (bookToSearch.equals("")) {
            return repo;
        }

        return searchBookList;
    }

    @Override
    public boolean removeItemById(String bookToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookToRemove)) {
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    private void defaultInit(){
        logger.info("default INIT in book repo bean");
    }

    private void defaultDestroy(){
        logger.info("default DESTROY in book repo bean");
    }
}
