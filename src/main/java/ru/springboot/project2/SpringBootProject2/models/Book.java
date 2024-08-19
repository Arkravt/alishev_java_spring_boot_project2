package ru.springboot.project2.SpringBootProject2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Наименование должно быть заполнено")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Автор должен быть заполнен")
    private String author;

    @Column(name = "year")
    @Min(value = 1500, message = "Год должен быть больше 1500")
    private int year;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean delay;


    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;


    public Book() {
    }

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isDelay() {

        Calendar calendar;

        if (this.getTakenAt() == null)
            return false;
        else {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -10);
            return this.takenAt.before(calendar.getTime());
        }
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
