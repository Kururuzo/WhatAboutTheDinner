package ru.restaurant.model;

public enum Role /*implements GrantedAuthority */ {
    USER,
    ADMIN;

//    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
