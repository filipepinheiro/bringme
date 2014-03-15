package pt.ua.icm.bringme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link FindCourierFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link FindCourierFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class FindCourierFragment extends Fragment{

	ExpandableListView courierListView;
	
	public static FindCourierFragment newInstance() {
		FindCourierFragment fragment = new FindCourierFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	
	

	public FindCourierFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_find_courier, container,
				false);
	}

}
