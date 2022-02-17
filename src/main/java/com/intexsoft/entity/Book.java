package com.intexsoft.entity;

public class Book {
    private Long id;
    private String library;
    private String author;
    private String name;
    private String date;
    private String user;

    public Book() {
    }

    public Book(Long id, String author, String name, String date, String user, String library) {
        this.id = id;
        this.library = library;
        this.author = author;
        this.name = name;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "main.java.com.intexsoft.entity.Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                ", library='" + library + '\'' +
                '}' ;
    }
}
