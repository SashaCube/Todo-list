package com.oleksandr.havryliuk.todo_list.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oleksandr.havryliuk.todo_list.data.Task;
import com.oleksandr.havryliuk.todo_list.data.source.TasksDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TasksRemoteDataSource implements TasksDataSource {

    private DatabaseReference mTasksRef;

    private static TasksRemoteDataSource INSTANCE;

    private static HashMap<String, Task> TASKS_SERVICE_DATA;
    private String USERID;

    static {
        TASKS_SERVICE_DATA = new HashMap<>();
    }

    public static TasksRemoteDataSource getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteDataSource();
        }
        return INSTANCE;

    }

    public void destroyInstance() {
        TASKS_SERVICE_DATA.clear();
        INSTANCE = null;
    }

    // Prevent direct instantiation.
    private TasksRemoteDataSource() {
        USERID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.i("Remote", "connected: user");
        mTasksRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(USERID);

        mTasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getTasksFromSnapShot(dataSnapshot);
                    Log.i("Firebase DB", "update value for user " + USERID);
                } else {
                    saveNewUser(USERID);
                    Log.i("Firebase DB", "create new tasks List for user " + USERID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Firebase", "error");
            }
        });
    }

    private void getTasksFromSnapShot(DataSnapshot dataSnapshot) {
        TASKS_SERVICE_DATA.clear();

        HashMap<String, HashMap<String, Object>> map = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
        for (Map.Entry<String, HashMap<String, Object>> entry : map.entrySet()) {

            String title = (String) entry.getValue().get("title");
            String description = (String) entry.getValue().get("description");
            String taskId = (String) entry.getValue().get("id");
            Boolean completed = (Boolean) entry.getValue().get("completed");
            //boolean completed = (boolean) entry.getValue().get("completed");

            Task task = new Task(title, description, taskId, completed);
            TASKS_SERVICE_DATA.put(taskId, task);
            Log.i("DataSnapshot", "Task from remote repo " + task);
        }
    }

    private void saveNewUser(String userId) {
        mTasksRef.child(userId).setValue(TASKS_SERVICE_DATA);
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        List<Task> tasks = new ArrayList<Task>(TASKS_SERVICE_DATA.values());
        callback.onTasksLoaded(tasks);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull final GetTaskCallback callback) {
        final Task task = TASKS_SERVICE_DATA.get(taskId);
        callback.onTaskLoaded(task);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        TASKS_SERVICE_DATA.put(task.getId(), task);
        mTasksRef.setValue(TASKS_SERVICE_DATA);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), true);
        TASKS_SERVICE_DATA.put(completedTask.getId(), completedTask);
        mTasksRef.setValue(TASKS_SERVICE_DATA);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        // Not required for the remote data source because the TasksRepository handles
        // converting from a taskId to a task using its cached data.
    }

    @Override
    public void activateTask(@NonNull Task task) {
        Task activeTask = new Task(task.getTitle(), task.getDescription(), task.getId());
        TASKS_SERVICE_DATA.put(activeTask.getId(), activeTask);
        mTasksRef.setValue(TASKS_SERVICE_DATA);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        // Not required for the remote data source because the TasksRepository handles
        // converting from a taskId to a task using its cached data.
    }

    @Override
    public void clearCompletedTasks() {
        Iterator<Map.Entry<String, Task>> it = TASKS_SERVICE_DATA.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
        mTasksRef.setValue(TASKS_SERVICE_DATA);
    }

    @Override
    public void refreshTasks() {
        // Not required because the TasksRepository handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllTasks() {
        TASKS_SERVICE_DATA.clear();
        mTasksRef.setValue(TASKS_SERVICE_DATA);
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        TASKS_SERVICE_DATA.remove(taskId);
        mTasksRef.setValue(TASKS_SERVICE_DATA);
    }
}
