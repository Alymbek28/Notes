package com.example.a33;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainFragment extends Fragment {
    private RecyclerView rvNotes;
    private NotesAdapter adapter;
    private FloatingActionButton btnOpenAddFragment;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NotesAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        rvNotes = view.findViewById(R.id.rv_notes);
        btnOpenAddFragment = view.findViewById(R.id.btn_open_add_fragment);
        listerNoteData();

        rvNotes.setAdapter(adapter);

        btnOpenAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, new AddNotesFragment());
                transaction.addToBackStack("AddNoteFragment");
                transaction.commit();
            }
        });


        return view;
    }

    private void listerNoteData() {
        requireActivity().getSupportFragmentManager().setFragmentResultListener("addNote", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(requestKey.equals("addNote")){
                    //Toast.makeText(requireContext(),result.getString("title"), Toast.LENGTH_SHORT).show();
                    adapter.addNote(new NotesModel(result.getString("title"),
                            result.getString("description"), result.getString("date")));

                }
            }
        });

        requireActivity().getSupportFragmentManager().setFragmentResultListener("editNote", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(requestKey.equals("editNote")){
                    //Toast.makeText(requireContext(),result.getString("title"), Toast.LENGTH_SHORT).show();
                    NotesModel model = new NotesModel(result.getString("title"),
                            result.getString("description"), result.getString("date"));
                    adapter.editNote(model, result.getInt("position"));
                }
            }
        });
    }
}