package com.rosu.cotroller;

import com.rosu.model.Project;
import com.rosu.model.Task;
import com.rosu.repository.ProjectRepository;
import com.rosu.repository.TaskRepository;
import com.rosu.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class ControllerProject {

    public TableColumn colProjectTask;
    public Button btnViewTasks;
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private boolean isConnectionSuccessful = false;
    private ObservableList<Project> projectList;
    private Project project;


    public Tab tabProject;
    public Button btnInsertProject;
    public TextField txtFieldProject;
    public VBox vBoxProject;
    public TableView tableViewProject;
    public TableColumn<Task, String> colNameProject;
    public TableColumn<Task, Integer> colIdProject;
    public TabPane tabPanee;

    public void initialize(){
        try {
            persistenceConnection();
            initColumnProject();
            getProjectList();
            tabPanee.getTabs().clear();
            tabPanee.getTabs().add(tabProject);
            loadProjects();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Connection is not allowed ~ControllerProject~");
            isConnectionSuccessful = false;
        }
    }

    private void persistenceConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        userRepository = new UserRepository(entityManager);
        taskRepository = new TaskRepository(entityManager);
        projectRepository = new ProjectRepository(entityManager);
    }

    public void initColumnProject(){
        try {
            colIdProject.setCellValueFactory(new PropertyValueFactory<Task, Integer>("project_id"));
            colNameProject.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        }catch (NullPointerException e){
            System.out.println("Exception caught");
        }
    }

    public ObservableList<Project> getProjectList() {
        project = new Project();
        projectList = FXCollections.observableArrayList(projectRepository.findAll());
        return projectList;
    }

    public void insertProjectEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            insertProject();
        }
    }

    public void insertProject(ActionEvent actionEvent) {
        insertProject();
    }

    private void insertProject() {
        Project project = new Project();
        project.setCreatedAt(new Date());
        project.setName(txtFieldProject.getText());

        projectRepository.save(project);

        CheckBox checkBox = new CheckBox();
        checkBox.setText(project.getName());

        vBoxProject.getChildren().add(checkBox);
    }


    public void loadProjects(){
        //Project project = new Project();
        List<Project> projects = projectRepository.findAll();
        final ObservableList<Project> dbProjects = FXCollections.observableList(projects);
        tableViewProject.setItems(dbProjects);
        //tabPanee.getTabs().add(tabProject);
    }

    @FXML
    private void viewTasks(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("task.fxml");
        Parent root = fxmlLoader.load(resourceAsStream);

        Scene loginScene = new Scene(root,800,600);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setTitle("Tasks");
        window.setScene(loginScene);
        window.show();
    }
}
