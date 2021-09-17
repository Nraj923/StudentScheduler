package com.example.studentscheduler.ui.calendar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;
import com.example.studentscheduler.Task;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    private CalendarView mCalendarView;
    private TextView mNumTasks;
    private Button mViewTasks;
    private String date;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        mCalendarView = rootView.findViewById(R.id.calendarView);
        mViewTasks = rootView.findViewById(R.id.view_tasks);
        mNumTasks = rootView.findViewById(R.id.num_tasks);

        // Calculates the number of tasks on any given day and displays it when the date is changed
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String selectedDate = (month+1) + "/" + day + "/" + year;
                date = selectedDate;
                Database db = new Database(getActivity());
                List<Task> tasks = db.getTasksOnDate(selectedDate);
                mNumTasks.setText(tasks.size() + "");
                db.close();
            }
        });

        mViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment calendarTasksFragment = new CalendarTasksFragment();

                Bundle bundle = new Bundle();
                bundle.putString("selected_date", date);

                // Based on the selected date, a list of tasks for that day will appear in this new fragment
                calendarTasksFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), calendarTasksFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootView;
    }
}