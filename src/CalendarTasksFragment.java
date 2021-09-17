package com.example.studentscheduler.ui.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;
import com.example.studentscheduler.Task;
import com.example.studentscheduler.ui.task.TaskDetailsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarTasksFragment extends Fragment {

    private TextView mDate;
    private ListView mTasksToday;
    private ArrayAdapter mTaskAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarTasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarTasksFragment newInstance(String param1, String param2) {
        CalendarTasksFragment fragment = new CalendarTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_calendar_tasks, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        mDate = rootView.findViewById(R.id.date);
        mTasksToday = rootView.findViewById(R.id.task_list);

        String selectedDate = getArguments().getString("selected_date");
        System.out.println(selectedDate);

        mDate.setText(selectedDate);

        Database db = new Database(getActivity());

        // Displays all tasks based on given date
        mTaskAdapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1,
                db.getTasksOnDate(selectedDate));
        mTasksToday.setAdapter(mTaskAdapter);

        return rootView;
    }
}