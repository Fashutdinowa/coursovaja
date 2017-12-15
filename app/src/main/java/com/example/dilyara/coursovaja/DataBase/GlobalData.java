package com.example.dilyara.coursovaja.DataBase;
import com.example.dilyara.coursovaja.entity.Project;
import com.example.dilyara.coursovaja.entity.Role;
import com.example.dilyara.coursovaja.entity.Status;
import com.example.dilyara.coursovaja.entity.Task;
import com.example.dilyara.coursovaja.entity.User;
import com.example.dilyara.coursovaja.entity.Way;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dilyara on 08.12.2017.
 */

public class GlobalData {
    public static User user;
    public static List<Way> ways = new ArrayList<Way>();
    public static List<Role> roles= new ArrayList<Role>();
    public static List<Status> status= new ArrayList<Status>();
    public static List<Task> taskList= new ArrayList<Task>();
    public static List<Project> projectList= new ArrayList<Project>();

}
