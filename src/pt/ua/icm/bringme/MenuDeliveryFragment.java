package pt.ua.icm.bringme;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pt.ua.icm.bringme.adapters.CourierAdapter;
import pt.ua.icm.bringme.adapters.DeliveryAdapter;
import pt.ua.icm.bringme.models.Courier;
import pt.ua.icm.bringme.models.Delivery;
import android.app.Activity;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link MenuDeliveryFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link MenuDeliveryFragment#newInstance} factory method to create
 * an instance of this fragment.
 * 
 */
public class MenuDeliveryFragment extends Fragment {

	private LinkedList<Delivery> deliveries = new LinkedList<Delivery>();
		
	private OnFragmentInteractionListener mListener;

	public static MenuDeliveryFragment newInstance() {
		MenuDeliveryFragment fragment = new MenuDeliveryFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public MenuDeliveryFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_menu_request,
				container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		List<String> menuOptions = new ArrayList<String>();
		
		menuOptions.add("Ask to Deliver");
		menuOptions.add("Delivery Status");
		
		BaseAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, menuOptions);
		ListView requestsMenu = (ListView) getActivity().findViewById(R.id.requestsMenu);
		requestsMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		requestsMenu.setAdapter(adapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public LinkedList<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(LinkedList<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
