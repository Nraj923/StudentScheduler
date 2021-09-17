package com.example.studentscheduler.ui.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.studentscheduler.Course;
import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;
import com.example.studentscheduler.Task;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {

    private EditText mTaskName;
    private EditText mDescription;
    private EditText mType;
    private Spinner mCourse;
    private EditText mDueDate;
    private EditText mTimeDue;
    private Button mAddTask;
    private ImageButton mDateButton;
    private ImageButton mTimeButton;
    private ArrayAdapter spinnerAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        mTaskName = rootView.findViewById(R.id.task_name);
        mDescription = rootView.findViewById(R.id.task_description);
        mType = rootView.findViewById(R.id.task_type);
        mCourse = rootView.findViewById(R.id.course_spinner);
        mDueDate = rootView.findViewById(R.id.task_due_date);
        mTimeDue = rootView.findViewById(R.id.task_time_due);
        mDateButton = rootView.findViewById(R.id.date_button);
        mTimeButton = rootView.findViewById(R.id.time_button);

        Database db = new Database(getActivity());
        spinnerAdapter = new ArrayAdapter<Course>(getActivity(),
                android.R.layout.simple_spinner_item, db.getAllCourses());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCourse.setAdapter(spinnerAdapter);

        // Allows user to select a date from calendar widget
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mDate = Calendar.getInstance();
                int mDay = mDate.get(Calendar.DAY_OF_MONTH);
                int mMonth = mDate.get(Calendar.MONTH);
                int mYear = mDate.get(Calendar.YEAR);

                DatePickerDialog mDatePickerDialog;
                mDatePickerDialog = new DatePickerDialog(AddTaskFragment.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mDueDate.setText("" + (month+1) + "/" + day + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                mDatePickerDialog.setTitle("Select Due Date");
                mDatePickerDialog.show();
            }
        });

        // Allows user to select a time from clock widget
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mTime = Calendar.getInstance();
                int minute = mTime.get(Calendar.MINUTE);
                int hour = mTime.get(Calendar.HOUR_OF_DAY);
                TimePickerDialog mTimePickerDialog = new TimePickerDialog(AddTaskFragment.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        if (minute < 10) {
                            mTimeDue.setText(hour + ":0" + minute);
                        } else {
                            mTimeDue.setText(hour + ":" + minute);
                        }
                    }
                }, hour, minute, true);
                mTimePickerDialog.setTitle("Select Time Due");
                mTimePickerDialog.show();
            }
        });

        // Stores task information in database
        mAddTask = rootView.findViewById(R.id.add_task);
        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task;
                try {
                    task = new Task(-1, mTaskName.getText().toString(),
                            mDescription.getText().toString(),
                            mType.getText().toString(),
                            mCourse.getSelectedItem().toString(),
                            mDueDate.getText().toString(),
                            mTimeDue.getText().toString());
                }
                catch(Exception e) {
                    Toast.makeText(getActivity(),
                            "Error adding course",
                            Toast.LENGTH_SHORT).show();
                    task = new Task(-1, "error", "error", "error", "error", "error", "error");
                }

                Database db = new Database(getActivity());

                db.addTask(task);

                db.close();

                Fragment taskFragment = new TaskFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), taskFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootView;
    }
}