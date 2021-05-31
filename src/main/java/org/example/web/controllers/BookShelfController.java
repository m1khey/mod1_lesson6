package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exception.UploadFileException;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.remove.BookAuthorToRemove;
import org.example.web.dto.remove.BookIdToRemove;
import org.example.web.dto.remove.BookSizeToRemove;
import org.example.web.dto.remove.BookTitleToRemove;
import org.example.web.dto.search.BookAuthorToSearch;
import org.example.web.dto.search.BookIdToSearch;
import org.example.web.dto.search.BookSizeToSearch;
import org.example.web.dto.search.BookTitleToSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookIdToSearch",new BookIdToSearch());
        model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
        model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
        model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
        model.addAttribute("bookList", bookService.getAllBooks());

        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());

            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());

            return "redirect:/books/shelf";
        }
    }

    //remove----------------------------------------------------------------

    @PostMapping("/remove by id")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBook(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove by author")
    public String removeBook(@Valid BookAuthorToRemove bookAuthorToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBook(bookAuthorToRemove.getAuthor());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove by title")
    public String removeBook(@Valid BookTitleToRemove bookTitleToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBook(bookTitleToRemove.getTitle());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove by size")
    public String removeBook(@Valid BookSizeToRemove bookSizeToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBook(bookSizeToRemove.getSize());
            return "redirect:/books/shelf";
        }
    }

    //------------------------------------------------------------------remove

    //search------------------------------------------------------------------

    @GetMapping("/search by id")
    public String searchBook(@Valid BookIdToSearch bookIdToSearch,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());

            return "book_shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.searchBook(bookIdToSearch.getId()));
            logger.info("current sought repository by id");

            return "book_shelf";
        }

    }

    @GetMapping("/search by author")
    public String searchBook(@Valid BookAuthorToSearch bookAuthorToSearch,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());

            return "book_shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.searchBook(bookAuthorToSearch.getAuthor()));
            logger.info("current sought repository by author");

            return "book_shelf";
        }
    }

    @GetMapping("/search by title")
    public String searchBook(@Valid BookTitleToSearch bookTitleToSearch,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());

            return "book_shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.searchBook(bookTitleToSearch.getTitle()));
            logger.info("current sought repository by title");

            return "book_shelf";
        }
    }

    @GetMapping("/search by size")
    public String searchBook(@Valid BookSizeToSearch bookSizeToSearch,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());

            return "book_shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookIdToSearch",new BookIdToSearch());
            model.addAttribute("bookAuthorToSearch",new BookAuthorToSearch());
            model.addAttribute("bookTitleToSearch",new BookTitleToSearch());
            model.addAttribute("bookSizeToSearch",new BookSizeToSearch());
            model.addAttribute("bookList", bookService.searchBook(bookSizeToSearch.getSize()));
            logger.info("current sought repository by size");

            return "book_shelf";
        }
    }
    //------------------------------------------------------------------search

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file) throws Exception{

        String name = file.getOriginalFilename();
        if (name=="") {
            throw new UploadFileException("File not uploaded, upload file!");
        }
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsolutePath()+File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("new file saved at: "+ serverFile.getAbsolutePath());

        return "redirect:/books/shelf";
    }

    @ExceptionHandler(UploadFileException.class)
    public String handlerUploadFiliException(Model model, UploadFileException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/500";
    }
}
