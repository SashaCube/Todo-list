package com.oleksandr.havryliuk.todo_list.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.oleksandr.havryliuk.todo_list.R;
import com.oleksandr.havryliuk.todo_list.auth.Auth;
import com.oleksandr.havryliuk.todo_list.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.todo_list.data.source.TasksRepository;
import com.oleksandr.havryliuk.todo_list.statistics.StatisticsActivity;
import com.oleksandr.havryliuk.todo_list.utils.ActivityUtils;

public class TasksActivity extends AppCompatActivity {

    private TasksPresenter mTasksPresenter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        TasksFragment tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.content_frame);
        }

        mTasksPresenter = new TasksPresenter(TasksRepository.getInstance(this), tasksFragment);
        tasksFragment.setPresenter(mTasksPresenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.statistics_navigation_menu_item:
                                startActivity(new Intent(TasksActivity.this, StatisticsActivity.class));
                                break;
                            case R.id.sign_out_navigation_menu_item:
                                Auth.signOut();
                                startActivity(new Intent(TasksActivity.this, AuthenticationActivity.class));
                                finish();
                            default:
                                break;
                        }
                        return true;
                    }
                });
    }
}


