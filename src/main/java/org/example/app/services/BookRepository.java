package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private final List<Book> searchBookList = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        if (searchBookList.size()==0) {
            return new ArrayList<>(repo);
        } else {
            return new ArrayList<>(searchBookList);
        }
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public void searchItem(Object bookToSearch) {
        searchBookList.clear();

        for (int i = 0; i <repo.size() ; i++) {
            if (repo.get(i).getId().toString().equals(bookToSearch.toString()) ||
                    repo.get(i).getAuthor().equals(bookToSearch) ||
                    repo.get(i).getTitle().equals(bookToSearch) ||
                    repo.get(i).getSize().toString().equals(bookToSearch.toString())) {
                logger.info("search book completed: " + repo.get(i));
                searchBookList.add(repo.get(i));
            }
        }
    }

    @Override
    public boolean removeItem(Object bookToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().toString().equals(bookToRemove.toString()) ||
            book.getAuthor().equals(bookToRemove) ||
            book.getTitle().equals(bookToRemove) ||
            book.getSize().toString().equals(bookToRemove.toString())) {
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return false;
    }
}
