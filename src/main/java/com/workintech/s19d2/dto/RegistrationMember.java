package com.workintech.s19d2.dto;

public record RegistrationMember(String email, String password, String role) {
    public RegistrationMember(String email, String password) {
        this(email, password, "USER");
    }
}
