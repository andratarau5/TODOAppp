package com.rosu.repository;

import com.rosu.model.Project;

import javax.persistence.EntityManager;
import java.util.List;

public class ProjectRepository implements CrudRepository<Project,Integer> {

    private EntityManager entityManager;

    public ProjectRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Project> findAll() {
        return entityManager.createQuery("SELECT p FROM Project p").getResultList();
    }

    public void save(Project project) {
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        Project project = findById(id);
        if (project != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(project);
            entityManager.getTransaction().commit();
        }
    }

    public Project findById(Integer id) {
        try {
            Project project = entityManager.find(Project.class, id);
            return project;
        }catch (Exception e){
            System.out.println("Something went wrong (ProjectRepository)");
        }
        return null;
    }
}
