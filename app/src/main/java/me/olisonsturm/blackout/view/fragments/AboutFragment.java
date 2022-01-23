package me.olisonsturm.blackout.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.view.activitys.MainActivity;

public class AboutFragment extends Fragment {
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        return view;
    }

}
