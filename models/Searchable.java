
package models;

import java.util.List;

public interface Searchable<T> {
    List<T> searchList(List<T> list, T target);
}
