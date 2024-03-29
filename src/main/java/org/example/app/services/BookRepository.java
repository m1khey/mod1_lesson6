package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookToSearch;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
//    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final List<String> listFiles = new ArrayList<>();

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
    public List<Book> searchItem(Integer bookToSearchById, String bookToSearchByAutor,String bookToSearchByTitle
            ,Integer bookToSearchBySize) {

        if (bookToSearchByAutor == null && bookToSearchByTitle == null
                && bookToSearchBySize == null) {
            return retreiveAll();
        }

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id",bookToSearchById);
        parameterSource.addValue("author",bookToSearchByAutor);
        parameterSource.addValue("title",bookToSearchByTitle);
        parameterSource.addValue("size",bookToSearchBySize);
        List<Book> searchBookList = jdbcTemplate.query("SELECT * FROM books WHERE id=:id or author=:author " +
                " or title=:title " +
                "or size=:size",parameterSource,(ResultSet rs,int rown)-> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        logger.info("find books " + searchBookList.size());

        return searchBookList;
    }

    @Override
    public boolean removeItem(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id=:id",parameterSource);

        logger.info("remove book completed!" + bookIdToRemove);
        return true;
    }

    @Override
    public List<String> getFiles() {
        return new ArrayList<>(listFiles);
    }


    @Override
    public void addFile(String fileName) {
        listFiles.add(fileName);
        logger.info("File "+fileName+" added");
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
