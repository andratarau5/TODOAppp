package com.rosu.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data                    //for getter and setter
@NoArgsConstructor       // for no arg constructor
@Entity
@Table(name = "users")  // user - redundant, because table will be named implicitly user
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //AUTO-INCREMENT
    private int user_id;

    @Column(name="username")
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //CascadeType All = daca un user se sterge, se sterg toate taskurile lui
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "working_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    @OneToOne(mappedBy = "user")
    private PendingUser pendingUser;

    public String toString(){
        return this.username;
    }

}
