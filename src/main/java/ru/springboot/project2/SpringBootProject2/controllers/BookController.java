package ru.springboot.project2.SpringBootProject2.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.springboot.project2.SpringBootProject2.dao.BookDao;
import ru.springboot.project2.SpringBootProject2.dao.PersonDao;
import ru.springboot.project2.SpringBootProject2.models.Book;
import ru.springboot.project2.SpringBootProject2.models.Person;
import ru.springboot.project2.SpringBootProject2.service.BooksService;
import ru.springboot.project2.SpringBootProject2.service.PeopleService;

import java.util.ArrayList;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao, BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String books(@RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "books_per_page", required = false) Integer itemsPerPage,
                        @RequestParam(name = "sort_by_year", required = false) boolean sortByYear,
                        Model model) {

        if (page != null)
            page--;

        if (page == null || itemsPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByYear));
        else
            model.addAttribute("books", booksService.findAll(page, itemsPerPage, sortByYear));

        model.addAttribute("page", page);
        model.addAttribute("books_per_page", itemsPerPage);
        model.addAttribute("sort_by_year", sortByYear);

        return "book/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id);

        model.addAttribute(book);

        if (book.getOwner() == null) {
            model.addAttribute("people", peopleService.findAll());
        } else {
            model.addAttribute("owner", book.getOwner());
        }

        return "book/show";
    }

    @PatchMapping("{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.assign(id, person.getId());
        return "redirect:/books/{id}";
    }

    @PatchMapping("{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "book/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/edit";
        }

        booksService.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("books", new ArrayList<>());
        return "book/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "starting_with", required = false) String startingWith, Model model) {
        model.addAttribute("books", booksService.findByNameStartingWith(startingWith));
        return "book/search";
    }

}
