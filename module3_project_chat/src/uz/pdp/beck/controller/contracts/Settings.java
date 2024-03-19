package uz.pdp.beck.controller.contracts;

import uz.pdp.beck.payload.UserDTO;

import java.util.UUID;

public interface Settings {
    UserDTO changeName(UUID id,String newName);
    UserDTO changeUsername(UUID id,String newUsername);
    UserDTO changePhone(UUID id,String newPhone);
    UserDTO changePassword(UUID id,String newPassword);
}
