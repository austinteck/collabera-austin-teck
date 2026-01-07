package com.collabera.austinteck.service;

import com.collabera.austinteck.model.Book;

import java.util.List;

/**
 * Book Service
 * @author : Austin Teck
*/

public interface BookService {

    Book createBook(Book book);

    List<Book> getBooks();
}
