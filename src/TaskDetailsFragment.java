package com.example.studentscheduler.ui.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;
import com.example.studentscheduler.Task;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailsFragment extends Fragment {

    private TextView mTaskName;
    private TextView mTaskDescription;
    private TextView mTaskType;
    private TextView mTaskCourse;
    private TextView mTaskDueDate;
    private TextView mTaskTimeDue;
    private ImageButton mDeleteTask;
    private int task_id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String param1;
    private int taskID;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TaskDetailsFragment newInstance(String param1, int taskID) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, taskID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString(ARG_PARAM1);
            taskID = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        task_id = getArguments().getInt("taskID");

        mTaskName = rootView.findViewById(R.id.task_name);
        mTaskDescription = rootView.findViewById(R.id.task_description);
        mTaskType = rootView.findViewById(R.id.task_type);
        mTaskCourse = rootView.findViewById(R.id.task_course);
        mTaskDueDate = rootView.findViewById(R.id.task_due_date);
        mTaskTimeDue = rootView.findViewById(R.id.task_time_due);
        mDeleteTask = rootView.findViewById(R.id.delete_task);

        // Deletes task from list
        mDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment taskFragment = new TaskFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("deleted_taskID", task_id);
                taskFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), taskFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Database db = new Database(getActivity());

        List<Task> tasks = db.getAllTasks();
        for (Task task : tasks) {
            if (task.getId() == task_id) {
                mTaskName.setText(task.getTaskName());
                mTaskDescription.setText(task.getDescription());
                mTaskType.setText(task.getType());
                mTaskCourse.setText(task.getCourse());
                mTaskDueDate.setText(task.getDueDate());
                mTaskTimeDue.setText(task.getTimeDue());
            }
        }

        db.close();

        return rootView;
    }
}