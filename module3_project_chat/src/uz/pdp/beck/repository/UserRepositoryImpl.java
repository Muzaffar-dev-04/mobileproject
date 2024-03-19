package uz.pdp.beck.repository;

import uz.pdp.beck.model.User;
import uz.pdp.beck.repository.contracts.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    private List<User> users;
    private static final UserRepository instance = new UserRepositoryImpl(); // eager
    private UserRepositoryImpl() {
    }
    public static UserRepository getInstance() {
        return instance;
    }

    @Override
    public boolean save(User user) {
        users = read();
        //if (users == null)   users = new ArrayList<>();
        users.add(user);
        write(users);
        return true;
    }

    @Override
    public User findByUserName(String username) {
        List<User> userList = read();
        users = userList;
        //System.out.println(users.toString());
        //if (users == null)  users = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    @Override
    public User findByUserId(UUID id) {
        users = read();
        //if (users == null)  users = new ArrayList<>();
        for (User user : users) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }

    @Override
    public User changeName(UUID id,String newName) {
        User user = findByUserId(id);
        user.setName(newName);
        write(users);
        return user;
    }

    @Override
    public User changeUsername(UUID id,String newUsername) {
        User user = findByUserId(id);
        user.setUsername(newUsername);
        write(users);
        return user;
    }

    @Override
    public User changePhone(UUID id,String newPhone) {
        User user = findByUserId(id);
        user.setPhoneNumber(newPhone);
        write(users);
        return user;
    }

    @Override
    public User changePassword(UUID id,String newPassword) {
        User user = findByUserId(id);
        user.setPassword(newPassword);
        write(users);
        return user;
    }

    @SuppressWarnings("unchecked")
    private List<User> read(){
        File file = new File("repository/users.txt");
        if (file.length() == 0)     return new ArrayList<>();
        try(
                FileInputStream fileInputStream = new FileInputStream("repository/users.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ){
            users = (List<User>) objectInputStream.readObject();
            //users.forEach(System.out::println);
            //if (users == null)  users = new ArrayList<User>();
            //users.forEach(System.out::println);
            return users;
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
        return new ArrayList<User>();
    }

    @SuppressWarnings("unckecked")
    private void write(List<User> users){
        try (
                FileOutputStream fileOutputStream = new FileOutputStream("repository/users.txt");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                ){
            objectOutputStream.writeObject(users);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
