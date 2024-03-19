package uz.pdp.beck.controller;

import uz.pdp.beck.controller.contracts.AuthController;
import uz.pdp.beck.controller.contracts.Settings;
import uz.pdp.beck.model.User;
import uz.pdp.beck.payload.ChatDTO;
import uz.pdp.beck.payload.SignInDTO;
import uz.pdp.beck.payload.SignUpDTO;
import uz.pdp.beck.payload.UserDTO;
import uz.pdp.beck.repository.BlockRepositoryImpl;
import uz.pdp.beck.repository.contracts.BlockRepository;
import uz.pdp.beck.repository.contracts.UserRepository;
import uz.pdp.beck.repository.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthControllerImpl implements AuthController {
    private final Settings settings = SettingsImpl.getInstance();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final BlockRepository blockRepository = BlockRepositoryImpl.getInstance();
    private static final AuthController instance = new AuthControllerImpl(); // eager
    private AuthControllerImpl() {
    }
    public static AuthController getInstance() {
        return instance;
    }

    @Override
    public UserDTO signUp(SignUpDTO dto) {

        // username unique
        // password strong

        User byUserName = userRepository.findByUserName(dto.username());
        if (byUserName != null)
            throw new RuntimeException("User Already exists");

        User user = new User();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setPhoneNumber(dto.phone());
        user.setPassword(dto.password());

        userRepository.save(user);
        return new UserDTO(user);
    }

    @Override
    public UserDTO signIn(SignInDTO signInDTO) {
        User user = userRepository.findByUserName(signInDTO.username());
        if (user == null)
            throw new RuntimeException("User not found");

        if (!user.getPassword().equals(signInDTO.password()))
            throw new RuntimeException("Password incorrect");;

        return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getPhoneNumber());
    }

    @Override
    public UserDTO changeName(UUID id,String newName){
        return settings.changeName(id,newName);
    }

    @Override
    public UserDTO changeUsername(UUID id, String newUsername) {
        return settings.changeUsername(id,newUsername);
    }

    @Override
    public UserDTO changePhone(UUID id, String newPhone) {
        return settings.changePhone(id,newPhone);
    }

    @Override
    public UserDTO changePassword(UUID id, String newPassword) {
        return settings.changePassword(id,newPassword);
    }

    @Override
    public boolean blockUser(UUID id, int i) {
        User user = userRepository.findByUserId(id);
        blockRepository.addToBlock(id,user);
        return true;
    }
}
