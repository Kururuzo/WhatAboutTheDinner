package ru.restaurant.to;

import ru.restaurant.model.User;

public class UserForVoteTo extends BaseTo{
    private String name;

    public UserForVoteTo() {
    }

    public UserForVoteTo(String name) {
        this.name = name;
    }

    public UserForVoteTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public UserForVoteTo(User user) {
        super.id = user.getId();
        this.name = user.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserForVoteTo{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
