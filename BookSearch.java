package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookSearch implements Searchable<Book> {

    @Override
    public List<Book> searchList(List<Book> list, Book target) {
        List<Book> searchList = new ArrayList<>();
        
        String targetTitle = target.getTitle();
        
        Iterator<Book> it = list.iterator();
        while(it.hasNext()){
            Book currentBook = it.next();
            if (currentBook.getTitle().equalsIgnoreCase(targetTitle)){
                searchList.add(currentBook);
            }
        }
        return searchList;
    }
}

