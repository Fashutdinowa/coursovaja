package com.example.dilyara.coursovaja.entity;

import java.util.Date;

/**
 * Created by Dilyara on 03.12.2017.
 */

public class Task {
    public int ID;
    public String Name;
    public int Status;
    public Date CreateDate;
    public Date CompletionDate;
    public int project;
    public String Description;
    public int Responsible;

    public Task(int id, String name, int status, Date create, Date  completion, int project, String description, int responsible){
        ID = id;
        Name = name;
        Status = status;
        CreateDate = create;
        CompletionDate = completion;
        this.project = project;
        Description = description;
        Responsible = responsible;

    }


}
