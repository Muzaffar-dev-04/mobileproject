package uz.pdp.beck.payload;

import uz.pdp.beck.model.Chat;

import java.util.UUID;

public record ChatDTO(UUID id, UserDTO contact) {
    public ChatDTO(Chat chat) {
        this(chat.getId(),new UserDTO(chat.getFirstSide()));
    }
}