package com.ag04.clidemo.model;

public class CliUser {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private Gender gender;
    private boolean superuser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isSuperuser() {
        return superuser;
    }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }
}
