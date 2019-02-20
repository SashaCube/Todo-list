package com.oleksandr.havryliuk.todo_list.addedittask;

import com.oleksandr.havryliuk.todo_list.BasePresenter;
import com.oleksandr.havryliuk.todo_list.BaseView;

public interface AddEditTaskContract {

    interface View extends BaseView<Presenter> {

        void showEmptyTaskError();

        void showTasksList();

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveTask(String title, String description);

        void populateTask();

        boolean isDataMissing();
    }
}