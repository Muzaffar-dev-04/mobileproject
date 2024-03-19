package uz.pdp.beck.model;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Указываем serialVersionUID

    private final UUID id = UUID.randomUUID();
    private String name;
    private String username; // unique
    private String phoneNumber;
    private String password;
    //private final Set<UUID> uuidsBlockedUs = new HashSet<>();

    public User() {
    }

    public User(String name, String username, String phoneNumber, String password) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public void removeFromBlock(UUID id){
        uuidsBlockedUs.remove(id);
    }

    public void addToBlockUs(UUID id) {
        boolean add = uuidsBlockedUs.add(id);
        if (!add){
            throw new RuntimeException("This user is already blocked !!!");
        }
        return;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
