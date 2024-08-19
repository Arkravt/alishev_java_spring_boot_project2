package ru.springboot.project2.SpringBootProject2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.springboot.project2.SpringBootProject2.dao.PersonDao;
import ru.springboot.project2.SpringBootProject2.models.Person;
import ru.springboot.project2.SpringBootProject2.service.PeopleService;

@Component
public class PersonValidator implements Validator {

    private PeopleService peopleService;
    private PersonDao personDao;

    @Autowired
    public PersonValidator(PeopleService peopleService, PersonDao personDao) {
        this.peopleService = peopleService;
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

        if(peopleService.findByFullNameAndIdNot(person.getFullName(), person.getId()).isPresent()) {
            errors.rejectValue("fullName","","Это ФИО уже существует");
        }
//        if(personDao.get(person.getId(), person.getFullName()).isPresent()) {
//            errors.rejectValue("fullName","","Это ФИО уже существует");
//        }

    }
}
