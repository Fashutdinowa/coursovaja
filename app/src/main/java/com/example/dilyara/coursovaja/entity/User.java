package com.example.dilyara.coursovaja.entity;

/**
 * Created by Dilyara on 03.12.2017.
 */

public class User {
    public int ID;
    public String Name;
    public String Surname;
    public int Role;
    public String Email;
    public String Login;
    public String Password;

    public User(int id, String name, String surname, int role, String email, String login, String password){
        ID = id;
        Name = name;
        Surname = surname;
        Role = role;
        Email = email;
        Login = login;
        Password = password;
    }
}
