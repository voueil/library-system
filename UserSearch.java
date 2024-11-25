package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserSearch implements Searchable<User> {

    @Override
    public List<User> searchList(List<User> list, User target) {
        List<User> searchList = new ArrayList<>();

        String targetId = target.getID();

        Iterator<User> it = list.iterator();

        while (it.hasNext()) {
            User currentUser = it.next();
            if (currentUser.getID().equalsIgnoreCase(targetId)) {
                searchList.add(currentUser);
            }
        }
        return searchList;
    }
}
