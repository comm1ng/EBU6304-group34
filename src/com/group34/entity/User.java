package com.group34.entity;

public class User {
    private String id;
    private String username;
    private String pwd;
    private String role;

    public User() {}

    public User(String id, String username, String pwd, String role) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.role = role;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
