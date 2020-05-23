package com.rosu.cotroller;

import com.rosu.model.Project;
import com.rosu.model.Task;
import com.rosu.repository.ProjectRepository;
import com.rosu.repository.TaskRepository;
import com.rosu.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class ControllerTask {

    public Button btnInsert;
    public Tab tabAddTask;
    public Tab tabAllTasks;
    public TableView<Task> tableView;
    public TableColumn<Task, String> colTaskDesc;
    public TableColumn<Task, Integer> colTaskId;
    public VBox vBoxTasks;
    public TextField txtFieldTask;
    public TableColumn<Task, String> colUsername;
    public TabPane tabPanee;


    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private boolean isConnectionSuccessful = false;
    private Task task;
    private ObservableList<Task> tasksList;

    public void initialize() {
        try {
            persistenceConnection();
            initColumn();
            getTasksList();
            tabPanee.getTabs().clear();
            tabPanee.getTabs().add(tabAddTask);
            tabPanee.getTabs().add(tabAllTasks);
            loadTasks();
        } catch (Exception ex) {
            System.out.println("Connection is not allowed ~ControllerTask~");
            ex.printStackTrace();
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

    public void initColumn(){
        try {
            colTaskId.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
            colTaskDesc.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
            colUsername.setCellValueFactory(new PropertyValueFactory<Task, String>("users"));
        }catch (NullPointerException e){
            System.out.println("Exception caught");
        }
    }

    public ObservableList<Task> getTasksList() {
        task = new Task();
        tasksList = FXCollections.observableArrayList(taskRepository.findAll());
        return tasksList;
    }


    public void insertTaskEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            insertTask();
        }
    }

    public void insertTask(ActionEvent actionEvent) {
        insertTask();
    }

    private void insertTask() {
        Task task = new Task();
        task.setCreatedAt(new Date());
        task.setDescription(txtFieldTask.getText());

        taskRepository.save(task);

        CheckBox checkBox = new CheckBox();
        checkBox.setText(task.getDescription());

        vBoxTasks.getChildren().add(checkBox);
    }


    public void loadTasks(){
        List<Task> tasks = taskRepository.findAll();
        final ObservableList<Task> dbTasks = FXCollections.observableList(tasks);
        tableView.setItems(dbTasks);
    }


}
