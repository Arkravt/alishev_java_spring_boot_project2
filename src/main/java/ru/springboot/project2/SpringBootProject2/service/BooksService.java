package ru.springboot.project2.SpringBootProject2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springboot.project2.SpringBootProject2.models.Book;
import ru.springboot.project2.SpringBootProject2.models.Person;
import ru.springboot.project2.SpringBootProject2.repositories.BooksRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();
    }

    public List<Book> findAll(Integer page, Integer itemsPerPage, boolean sortByYear) {
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findByNameStartingWith(String startingWith) {
        return booksRepository.findByNameStartingWith(startingWith);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void assign(int id, int personId) {
        Book book = booksRepository.findById(id).get();
        Person person = peopleService.findOne(personId);
        book.setOwner(person);
        book.setTakenAt(new Date());
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).get();
        book.setOwner(null);
        book.setTakenAt(null);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book book = booksRepository.findById(id).get();
        book.setId(id);
        book.setName(updatedBook.getName());
        book.setYear(updatedBook.getYear());
        book.setAuthor(updatedBook.getAuthor());
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
