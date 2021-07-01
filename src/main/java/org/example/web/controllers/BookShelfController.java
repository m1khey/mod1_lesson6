package org.example.web.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.log4j.Logger;
import org.example.app.exception.UploadDownloadFileException;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookToRemove;
import org.example.web.dto.BookToSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;

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
        model.addAttribute("bookToRemove", new BookToRemove());
        model.addAttribute("bookToSearch",new BookToSearch());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList",bookService.getFiles());

        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookIdToSearch",new BookToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList",bookService.getFiles());

            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());

            return "redirect:/books/shelf";
        }
    }

    //remove----------------------------------------------------------------

    @PostMapping("/remove")
    public String removeBook(@Valid BookToRemove bookToRemove, BindingResult bindingResult, Model model) {
        if(bindingResult.getFieldErrorCount()==4) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToSearch",new BookToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList",bookService.getFiles());

            return "book_shelf";
        } else {
            bookService.removeBook(bookToRemove);
            return "redirect:/books/shelf";
        }
    }
    //------------------------------------------------------------------remove

    //search------------------------------------------------------------------

    @GetMapping("/search")
    public String searchBook(@Valid BookToSearch bookToSearch,BindingResult bindingResult, Model model) {
        if (bindingResult.getFieldErrorCount()==4){
            model.addAttribute("book", new Book());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookToSearch",new BookToSearch());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("fileList",bookService.getFiles());

            return "book_shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookToSearch",new BookToSearch());
            model.addAttribute("bookList", bookService.searchBook(bookToSearch.getAuthor()
                    ,bookToSearch.getTitle(),bookToSearch.getSize()));
            model.addAttribute("fileList",bookService.getFiles());

            logger.info("current repository search list ");

            return "book_shelf";
        }
    }
    //------------------------------------------------------------------search

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file) throws Exception{

        String name = file.getOriginalFilename();
        if (name=="") {
            throw new UploadDownloadFileException("File not uploaded, upload file!");
        }
        bookService.addFile(name);
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

    @GetMapping("/downloadFile")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam(value = "fileName") String fileName) throws UploadDownloadFileException, IOException {
        if(fileName == null || fileName.isEmpty()) {
            throw new UploadDownloadFileException("Choose file to download");
        }
        String inputFileAbsolutePath = System.getProperty("catalina.home") + File.separator + "external_uploads" + File.separator + fileName;

        File inputFile = new File(inputFileAbsolutePath);
        if(!inputFile.exists() || inputFile.isDirectory()) {
            throw new UploadDownloadFileException("File '" + inputFileAbsolutePath + "' can not find");
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(inputFile));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + inputFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(inputFile.length())
                .body(resource);
    }

    @ExceptionHandler(UploadDownloadFileException.class)
    public String handlerUploadFiliException(Model model, UploadDownloadFileException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/500";
    }
}