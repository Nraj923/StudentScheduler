package com.example.studentscheduler.ui.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;
import com.example.studentscheduler.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    private Button mAddTask;
    private ListView mTaskList;
    private ArrayAdapter taskArrayAdapter;
    private Database db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        db = new Database(getActivity());

        mTaskList = rootView.findViewById(R.id.task_list);

        // If arguments were passed to fragment, user was attempting to delete fragment and id was passed as a parameter
        if (getArguments() != null) {
            int deleted_task_id = getArguments().getInt("deleted_taskID");
            for (Task task : db.getAllTasks()) {
                if (task.getId() == deleted_task_id) {
                    db.deleteTask(task);
                    showTaskListView(db);
                }
            }
        }

        mAddTask = rootView.findViewById(R.id.add_task);
        // If user clicks to add a task, task is added to list
        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addTaskFragment = new AddTaskFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), addTaskFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        showTaskListView(db);

        // If task is selected from list, extra information about task is displayed in different screen
        mTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task clickedTask = (Task) adapterView.getItemAtPosition(i);
                Fragment taskDetailsFragment = new TaskDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("taskID", clickedTask.getId());

                taskDetailsFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), taskDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        db.close();

        return rootView;
    }

    private void showTaskListView(Database db) {
        taskArrayAdapter = new ArrayAdapter<Task>(getActivity(),
                android.R.layout.simple_list_item_1, db.getAllTasks());
        mTaskList.setAdapter(taskArrayAdapter);
    }
}