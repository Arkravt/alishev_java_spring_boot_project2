package ru.springboot.project2.SpringBootProject2.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.springboot.project2.SpringBootProject2.models.Person;
import ru.springboot.project2.SpringBootProject2.service.PeopleService;
import ru.springboot.project2.SpringBootProject2.utils.PersonValidator;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String people(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleService.update(person, id);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
//        Person person = peopleService.getBooksByPersonId(id);
//        model.addAttribute("person", person);
//        model.addAttribute("books", person.getBooks());

        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "people/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        peopleService.delete(id);
        return "redirect:/people";
    }
}
