package com.intexsoft.service;

import com.intexsoft.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<Book> find(Map<String, String> userInput);
    void order(Map<String, String> paramsMap);
    void reBook(Map<String, String> paramsMap);
}
