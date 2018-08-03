package com.dianet.efd_app;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class HomeFragmentNavigationDrawer extends Fragment {
	
	public HomeFragmentNavigationDrawer(){


    }


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
       // Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
       // Intent intent = new Intent(getActivity(), MainActivity.class);
        //Intent intent = new Intent(HomeFragmentNavigationDrawer.this, MainActivity.class);
       // startActivity(intent);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
          startActivity(intent);
        return rootView;
    }
}
