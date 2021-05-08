package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
//    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum)->
        {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));

            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parametrSource = new MapSqlParameterSource();
        parametrSource.addValue("author",book.getAuthor());
        parametrSource.addValue("title", book.getTitle());
        parametrSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author, title, size) VALUES (:author, :title, :size)",parametrSource);
        logger.info("store new book: " + book);
    }

    @Override
    public List<Book> searchItem(Object bookToSearch) {
        List<Book> searchBookList = new ArrayList<>();

//        for (int i = 0; i <repo.size() ; i++) {
//            if (repo.get(i).getId().toString().equals(bookToSearch.toString()) ||
//                    repo.get(i).getAuthor().equals(bookToSearch) ||
//                    repo.get(i).getTitle().equals(bookToSearch) ||
//                    repo.get(i).getSize().toString().equals(bookToSearch.toString())) {
//                logger.info("search book completed: " + repo.get(i));
//                searchBookList.add(repo.get(i));
//            }
//        }
//        if (bookToSearch.equals("")) {
//            return repo;
//        }

        return searchBookList;
    }

    @Override
    public boolean removeItemById(Integer bookToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id=:id",parameterSource);
        logger.info("remove book completed!");
        return true;
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
