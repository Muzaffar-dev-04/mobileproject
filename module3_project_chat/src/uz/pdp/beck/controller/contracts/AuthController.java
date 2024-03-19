package uz.pdp.beck.controller.contracts;

import uz.pdp.beck.payload.ChatDTO;
import uz.pdp.beck.payload.SignInDTO;
import uz.pdp.beck.payload.SignUpDTO;
import uz.pdp.beck.payload.UserDTO;

import java.util.List;
import java.util.UUID;

public interface AuthController {
    UserDTO signUp(SignUpDTO dto);

    UserDTO signIn(SignInDTO signInDTO);

    UserDTO changeName(UUID id,String newName);

    UserDTO changeUsername(UUID id, String newUsername);

    UserDTO changePhone(UUID id, String newPhone);

    UserDTO changePassword(UUID id, String newPassword);

    boolean blockUser(UUID id, int i);
}
