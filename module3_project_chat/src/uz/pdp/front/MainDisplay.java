package uz.pdp.front;

import org.w3c.dom.ls.LSOutput;
import uz.pdp.beck.controller.ChatControllerImpl;
import uz.pdp.beck.controller.MessageControllerImpl;
import uz.pdp.beck.controller.contracts.AuthController;
import uz.pdp.beck.controller.AuthControllerImpl;
import uz.pdp.beck.controller.contracts.ChatController;
import uz.pdp.beck.controller.contracts.MessageController;
import uz.pdp.beck.model.User;
import uz.pdp.beck.payload.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainDisplay {

    private final static AuthController authController = AuthControllerImpl.getInstance();
    private final static ChatController chatController = ChatControllerImpl.getInstance();
    private final static MessageController messageController = MessageControllerImpl.getInstance();

    private final static Scanner scannerInt = new Scanner(System.in);
    private final static Scanner scannerStr = new Scanner(System.in);
    private static boolean islogin = false;
    private static boolean islogSetting = false;
    private static UserDTO currentUser = null;

    public static void main(String[] args) {

        System.out.println("----------DehChat.com ga xush kelibsiz------------");

        try (
                FileInputStream fileInputStream = new FileInputStream("repository/users.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ){
            List<User> users = (List<User>) objectInputStream.readObject();
            for (User user : users) {
                System.out.println(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        while (true) {

            while (!islogin) {
                showRegistration();
                int auth = getInputAsInt("Choose => ");

                islogin = switch (auth) {
                    case 1 -> signIn();
                    case 2 -> signUp();
                    default -> false;
                };

            }

            showMenu();
            int menu = getInputAsInt("Choose => ");

            switch (menu) {
                case 1 -> search();
                case 2 -> myChats();
                case 3 -> settings();
                case 4 -> displayMyProfile();
                case 0 -> exit();
            }

        }

    }

    private static void displayMyProfile() {
        System.out.println("Name : " + currentUser.name());
        System.out.println("Username : " + currentUser.username());
        System.out.println("Phone : " + currentUser.phoneNumber());
    }


    private static void search() {
        try {
            System.out.println("----------Searching-------------");
            String username = getInputAsString("username : ");

            ChatDTO chatDTO = chatController.findOrCreateUserByUsername(username, currentUser.id());

            UserDTO contact = chatDTO.contact();

            List<MessageDTO> messages = messageController.findAllMessagesByChatId(chatDTO.id());

            displayMessages(messages, contact);

            String sms = getInputAsString(" => ");

            MessageAddDTO messageAddDTO = new MessageAddDTO(sms, chatDTO.id(), currentUser.id());

            messageController.add(messageAddDTO);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void settings() {
        System.out.println("------------ Settings ---------");
        islogSetting = false;
        while (!islogSetting){
            showMenuOfSettings();
            int menu = getInputAsInt("Choose => ");
            switch (menu){
                case 1 -> changeName();
                case 2 -> changeUsername();
                case 3 -> changePhone();
                case 4 -> changePassword();
                case 5 -> blockUser();
                case 0 -> exitSetting();
            }
            displayMyProfile();
        }
    }

    private static void blockUser() {
        System.out.println("----------- Block user ----------");
        List<ChatDTO> chats = chatController.MyChats(currentUser.id());
        if (chats.isEmpty()){
            System.out.println("You do not have contacts!!!");
            return;
        }
        displayChats(chats);
        int index = getInputAsInt("Choose => ");
        try {
            authController.blockUser(currentUser.id(),index - 1);
            System.out.println("Blocked successfully!!!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void changePassword() {
        String newPassword = getInputAsString("Enter new password => ");
        currentUser = authController.changePassword(currentUser.id(),newPassword);
        System.out.println("Password successfully changed!!!");
    }

    private static void changePhone() {
        String newPhone = getInputAsString("Enter new phone number => ");
        currentUser = authController.changePhone(currentUser.id(),newPhone);
        System.out.println("Phone successfully changed!!!");
    }

    private static void changeUsername() {
        String newUsername = getInputAsString("Enter new username => ");
        currentUser = authController.changeUsername(currentUser.id(),newUsername);
        System.out.println("Username successfully changed!!!");
    }

    private static void changeName() {
        String newName = getInputAsString("Enter new name => ");
        currentUser = authController.changeName(currentUser.id(),newName);
        System.out.println("Name successfully changed!!!");
    }

    private static void exitSetting() {
        islogSetting = true;
    }

    private static void showMenuOfSettings() {
        System.out.println("1 => Change name");
        System.out.println("2 => Change username");
        System.out.println("3 => Change phone number");
        System.out.println("4 => Change password");
        System.out.println("5 => block user");
        System.out.println("0 => exit");
    }

    private static void myChats() {
        System.out.println("------------My chats---------");
        List<ChatDTO> chats = chatController.MyChats(currentUser.id());
        displayChats(chats);
        int index = getInputAsInt("Choose => ");
        ChatDTO chatDTO = chats.get(index - 1);

        UserDTO contact = chatDTO.contact();

        List<MessageDTO> messages = messageController.findAllMessagesByChatId(chatDTO.id());

        displayMessages(messages, contact);

        String sms = getInputAsString(" => ");

        MessageAddDTO messageAddDTO = new MessageAddDTO(sms, chatDTO.id(), currentUser.id());

        messageController.add(messageAddDTO);
    }

    private static void displayChats(List<ChatDTO> chats) {
        for (int i = 0; i < chats.size(); i++) {
            System.out.println((i+1) + " => " + chats.get(i).contact().username());
        }
    }

    private static void exit() {
        islogin = false;
        currentUser = null;
        System.out.println("---------Yoq bo'ling 😊");
    }

    private static boolean signUp() {
        try {
            System.out.println("---------------Sign Up ------------------");
            String name = getInputAsString("Name : ");
            String username = getInputAsString("Username : ");
            String phone = getInputAsString("Phone : ");
            String password = getInputAsString("Password : ");

            SignUpDTO signUpDTO = new SignUpDTO(name, username, phone, password);

            currentUser = authController.signUp(signUpDTO);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static boolean signIn() {
        try {
            System.out.println("---------------Sign In ------------------");
            String username = getInputAsString("Username : ");
            String password = getInputAsString("Password : ");

            SignInDTO signInDTO = new SignInDTO(username, password);

            UserDTO userDTO = authController.signIn(signInDTO);

            System.out.println("Successfully logged in.\n");

            currentUser = userDTO;
            return true;
        }catch (Exception e) {
            System.out.println("Error in user : " + e.getMessage() + "\n");
            return false;
        }
    }


    public static void showRegistration() {
        System.out.println("Registration");
        System.out.println("1 ==> signIn");
        System.out.println("2 ==> signUP");
    }

    public static void showMenu() {
        System.out.println("Menu");
        System.out.println("1 => Search");
        System.out.println("2 => My chats");
        System.out.println("3 => Settings");
        System.out.println("4 => My profile");
        System.out.println("0 => exit");
    }

    private static String getInputAsString(String message) {
        System.out.print(message);
        return scannerStr.nextLine();
    }

    private static int getInputAsInt(String message) {
        System.out.print(message);
        return scannerInt.nextInt();
    }

    private static void displayMessages(List<MessageDTO> messages, UserDTO contact) {
        System.out.println("----------" + contact.name() + "------------");
        for (MessageDTO message : messages) {

            if (message.senderId().equals(currentUser.id())) {
                System.out.println("\t\t\t\t " + message.body());
            } else {
                System.out.println(message.body());
            }
        }
        System.out.println();
    }

}
