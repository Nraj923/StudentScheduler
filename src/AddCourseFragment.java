package com.example.studentscheduler.ui.course;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentscheduler.Course;
import com.example.studentscheduler.Database;
import com.example.studentscheduler.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourseFragment extends Fragment {

    private EditText mCourseName;
    private EditText mProfessor;
    private EditText mCourseDescription;
    private EditText mEndTime;
    private Button mAddCourse;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCourseFragment newInstance(String param1, String param2) {
        AddCourseFragment fragment = new AddCourseFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_course, container, false);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

        mCourseName = rootView.findViewById(R.id.course_name);
        mProfessor = rootView.findViewById(R.id.professor);
        mCourseDescription = rootView.findViewById(R.id.course_description);

        mAddCourse = rootView.findViewById(R.id.add_course);
        //Stores course information in database
        mAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course course;
                try {
                    course = new Course(-1, mCourseName.getText().toString(),
                            mProfessor.getText().toString(),
                            mCourseDescription.getText().toString());
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(),
                            "Error adding course",
                            Toast.LENGTH_SHORT).show();
                    course = new Course(-1, "error", "error", "error");
                }

                Database db = new Database(getActivity());

                db.addCourse(course);

                db.close();

                Fragment courseFragment = new CourseFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), courseFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootView;
    }
}