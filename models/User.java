
package models;

public abstract class User {
    
    private String ID;
    private String username;
    private String role;

    public User(String ID, String username, String role) {
        this.ID = ID;
        this.username = username;
        this.role = role;
    }
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", username=" + username + '}';
    }
    
}
