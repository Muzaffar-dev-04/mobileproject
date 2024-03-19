package uz.pdp.beck.repository.contracts;

import uz.pdp.beck.model.User;

import java.util.UUID;

public interface UserRepository {

    boolean save(User user);

    User findByUserName(String username);

    User findByUserId(UUID id);

    User changeName(UUID id,String newName);

    User changeUsername(UUID id,String newUsername);

    User changePhone(UUID id,String newPhone);

    User changePassword(UUID id,String newPassword);

}
