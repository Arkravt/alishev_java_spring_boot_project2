package ru.springboot.project2.SpringBootProject2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.springboot.project2.SpringBootProject2.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByNameStartingWith(String startingWith);

}
