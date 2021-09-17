package com.example.studentscheduler.ui.course;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.studentscheduler.Course;
import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;
import com.example.studentscheduler.ui.task.TaskFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseDetailsFragment extends Fragment {

    private TextView mCourseName;
    private TextView mCourseProfessor;
    private TextView mCourseDescription;
    private ImageButton mDeleteCourse;
    private int course_id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseDetailsFragment newInstance(String param1, String param2) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_course_details, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        course_id = getArguments().getInt("courseID");

        mCourseName = rootView.findViewById(R.id.course_name);
        mCourseProfessor = rootView.findViewById(R.id.course_professor);
        mCourseDescription = rootView.findViewById(R.id.course_description);
        mDeleteCourse = rootView.findViewById(R.id.delete_course);

        // Allows user to delete course from list
        mDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment courseFragment = new CourseFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("deleted_courseID", course_id);
                courseFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), courseFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Database db = new Database(getActivity());

        List<Course> courses = db.getAllCourses();
        for (Course course : courses) {
            if (course.getId() == course_id){
                mCourseName.setText(course.getCourseName());
                mCourseProfessor.setText(course.getProfessor());
                mCourseDescription.setText(course.getCourseDescription());
            }
        }

        db.close();

        return rootView;
    }
}