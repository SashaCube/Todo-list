package com.oleksandr.havryliuk.todo_list.statistics;

import com.oleksandr.havryliuk.todo_list.BasePresenter;
import com.oleksandr.havryliuk.todo_list.BaseView;

public interface StatisticsContract {

    interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);

        void showStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

    }
}