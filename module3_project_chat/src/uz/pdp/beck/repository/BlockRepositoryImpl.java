package uz.pdp.beck.repository;

import uz.pdp.beck.model.User;
import uz.pdp.beck.repository.contracts.BlockRepository;
import uz.pdp.beck.repository.contracts.UserRepository;

import java.util.*;

public class BlockRepositoryImpl implements BlockRepository {
    private static final BlockRepository instance = new BlockRepositoryImpl();
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private final Set<User> users = new HashSet<>();
    private BlockRepositoryImpl() {
    }

    public static BlockRepository getInstance() {
        return instance;
    }

    @Override
    public boolean addToBlock(UUID id, User user) {
        users.add(user);
        //user.addToBlockUs(id);
        return true;
    }

    private User findById(UUID id) {
        for (User user : users) {
            if (user.getId().equals(id))   return user;
        }
        return null;
    }

    @Override
    public boolean removeFromBlock(UUID id, User user) {
        return false;
    }


}
