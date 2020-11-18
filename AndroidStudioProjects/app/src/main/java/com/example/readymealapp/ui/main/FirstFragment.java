package com.example.readymealapp.ui.main;




import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.readymealapp.R;
import com.example.readymealapp.UserInput;

public class FirstFragment extends Fragment implements View.OnClickListener {

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    private PageViewModel pageViewModel;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance(int index) {
        FirstFragment fragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//--------------------------------if a button is implemented for fragment-----------------------------------------------------------
        //--------------------------------------Button for Fragments------------------------
        View myView = inflater.inflate(R.layout.fragment_first, container, false);
        Button goToUserInputData = (Button) myView.findViewById(R.id.buttonUserInput);
        //this calls onClick from a fragment
        goToUserInputData.setOnClickListener(this);
        return myView;
        //--------------------------------------Button for Fragments------------------------


    }

    //--------------------------------------Button for Fragments------------------------
    //when the button is click, the onClick method is called
    @Override
    public void onClick(View v) {
        Intent UserInputActivity = new Intent (getActivity(), UserInput.class);
        startActivity(UserInputActivity);
    }
    //--------------------------------------Button for Fragments------------------------

//--------------------------------if a button is implemented for fragment-----------------------------------------------------------
}
