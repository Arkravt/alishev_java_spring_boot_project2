package ru.springboot.project2.SpringBootProject2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname")
    @NotEmpty(message = "ФИО должно быть заполнено")
    private String fullName;

    @Column(name = "yearbirth")
    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
    private int yearBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    public Person() {
    }

    public Person(String fullName, int yearBirth) {
        this.fullName = fullName;
        this.yearBirth = yearBirth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(int yearBirth) {
        this.yearBirth = yearBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearBirth=" + yearBirth +
                '}';
    }
}
