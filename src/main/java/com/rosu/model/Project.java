package com.rosu.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int project_id;
    @Column
    private String name;

    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "in_progress")
    private boolean inProgress;

    public String toString(){
        return this.name;
    }
}
