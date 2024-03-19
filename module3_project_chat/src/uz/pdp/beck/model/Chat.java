package uz.pdp.beck.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Chat implements Serializable{

    private final UUID id = UUID.randomUUID();
    private User firstSide;
    private User secondSide;

    public Chat(User firstSide, User secondSide) {
        this.firstSide = firstSide;
        this.secondSide = secondSide;
    }

    public UUID getId() {
        return id;
    }

    public User getFirstSide() {
        return firstSide;
    }

    public void setFirstSide(User firstSide) {
        this.firstSide = firstSide;
    }

    public User getSecondSide() {
        return secondSide;
    }

    public void setSecondSide(User secondSide) {
        this.secondSide = secondSide;
    }
}
