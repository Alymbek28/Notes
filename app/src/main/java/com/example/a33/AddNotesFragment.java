package com.example.a33;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.datepicker.SingleDateSelector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddNotesFragment extends Fragment {
    private EditText etTitle, etDescription;
    private Button btnAdd;

    public AddNotesFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_notes, container, false);
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        btnAdd = view.findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String dateTime = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss", Locale.getDefault())
                        .format(new Date());

                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("description", description);
                bundle.putString("date", dateTime);

                requireActivity().getSupportFragmentManager().setFragmentResult("addNote", bundle);
                requireActivity().getSupportFragmentManager().popBackStack();



            }
        });

        listenerEditData();
        return view;
    }

    private void listenerEditData() {
        if(getArguments() != null){
            btnAdd.setText("Edit");
            etTitle.setText(requireArguments().getString("title"));
            etDescription.setText(requireArguments().getString("description"));

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = etTitle.getText().toString();
                    String description = etDescription.getText().toString();
                    String dateTime = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss", Locale.getDefault())
                            .format(new Date());

                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("description", description);
                    bundle.putString("date", dateTime);
                    bundle.putInt("position",requireArguments().getInt("position"));

                    requireActivity().getSupportFragmentManager().setFragmentResult("editNote", bundle);
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

    }

}