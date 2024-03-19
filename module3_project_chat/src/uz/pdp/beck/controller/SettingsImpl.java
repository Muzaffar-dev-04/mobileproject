package uz.pdp.beck.controller;

import uz.pdp.beck.controller.contracts.AuthController;
import uz.pdp.beck.controller.contracts.Settings;
import uz.pdp.beck.model.User;
import uz.pdp.beck.payload.UserDTO;
import uz.pdp.beck.repository.UserRepositoryImpl;
import uz.pdp.beck.repository.contracts.UserRepository;

import java.util.UUID;

public class SettingsImpl implements Settings {
    private static final Settings instance = new SettingsImpl();

    private SettingsImpl() {
    }

    public static Settings getInstance() {
        return instance;
    }

    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public UserDTO changeName(UUID id,String newName) {
        User user = userRepository.changeName(id,newName);
        return new UserDTO(user);
    }

    @Override
    public UserDTO changeUsername(UUID id,String newUsername) {
        User user = userRepository.changeUsername(id,newUsername);
        return new UserDTO(user);
    }

    @Override
    public UserDTO changePhone(UUID id,String newPhone) {
        User user = userRepository.changePhone(id,newPhone);
        return new UserDTO(user);
    }

    @Override
    public UserDTO changePassword(UUID id,String newPassword) {
        User user = userRepository.changePassword(id,newPassword);
        return new UserDTO(user);
    }
}
