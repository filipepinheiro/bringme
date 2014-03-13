package pt.ua.icm.bringme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link QuickActionFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link QuickActionFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class QuickActionFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_quick_action, container,
				false);
	}

}
