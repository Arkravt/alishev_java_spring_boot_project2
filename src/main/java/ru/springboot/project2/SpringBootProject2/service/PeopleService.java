package ru.springboot.project2.SpringBootProject2.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.springboot.project2.SpringBootProject2.models.Book;
import ru.springboot.project2.SpringBootProject2.models.Person;
import ru.springboot.project2.SpringBootProject2.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    final private PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person updatedPerson, int id) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

//    public Person getPersonWithBooks(int id) {
//        Person person = peopleRepository.findById(id).get();
//        Hibernate.initialize(person.getBooks());
//        return person;
//    }

    public List<Book> getBooksByPersonId(int id) {

        Optional<Person> person = peopleRepository.findById(id);

        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Person> findByFullNameAndIdNot(String fullName, int id) {
        return Optional.ofNullable(peopleRepository.findByFullNameAndIdNot(fullName, id)).stream().findAny();
    }

}
