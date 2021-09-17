package com.example.studentscheduler.ui.course;

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

import com.example.studentscheduler.Course;
import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;
import com.example.studentscheduler.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {

    private Button mAddCourse;
    private ListView mCourseList;
    private ArrayAdapter courseArrayAdapter;
    private Database db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseFragment newInstance(String param1, String param2) {
        CourseFragment fragment = new CourseFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_course, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        db = new Database(getActivity());

        mCourseList = rootView.findViewById(R.id.course_list);

        // If argument was passed to fragment, user was attempting to delete item and that item's id is parameter
        if (getArguments() != null) {
            int deleted_course_id = getArguments().getInt("deleted_courseID");
            for (Course course : db.getAllCourses()) {
                if (course.getId() == deleted_course_id) {
                    db.deleteCourse(course);
                    showCourseListView(db);
                }
            }
        }

        mAddCourse = rootView.findViewById(R.id.add_course);
        // If user clicks to add a course, moves to screen with course input form
        mAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addCourseFragment = new AddCourseFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), addCourseFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        showCourseListView(db);

        // When a course is selected, it displays extra details on different screen
        mCourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course clickedCourse = (Course) adapterView.getItemAtPosition(i);
                Fragment courseDetailsFragment = new CourseDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("courseID", clickedCourse.getId());

                courseDetailsFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), courseDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        db.close();

        return rootView;
    }

    private void showCourseListView(Database db) {
        courseArrayAdapter = new ArrayAdapter<Course>(getActivity(),
                android.R.layout.simple_list_item_1, db.getAllCourses());
        mCourseList.setAdapter(courseArrayAdapter);
    }
}