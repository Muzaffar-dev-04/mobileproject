package uz.pdp.beck.repository.contracts;

import uz.pdp.beck.model.User;

import java.util.UUID;

public interface BlockRepository {
    boolean addToBlock(UUID id, User user);
    boolean removeFromBlock(UUID id,User user);
}
