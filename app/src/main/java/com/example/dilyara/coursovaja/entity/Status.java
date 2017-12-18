package com.example.dilyara.coursovaja.entity;

import java.util.Objects;

/**
 * Created by Dilyara on 03.12.2017.
 */

public class Status {
    public int ID;
    public String Name;

    public Status(int id, String name) {
        ID = id;
        Name = name;
    }
    public String toString(){
        return Name;
    }


}
