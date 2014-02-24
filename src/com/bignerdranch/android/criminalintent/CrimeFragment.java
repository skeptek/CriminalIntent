package com.bignerdranch.android.criminalintent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;



public class CrimeFragment extends Fragment {
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	public static final String EXTRA_CRIME_ID = 
			"com.bignerdranch.android.criminalintent.crime_id";
	public static final String DIALOG_DATE = "date";
	
	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@TargetApi(9)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, 
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		mTitleField = (EditText) v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(
					CharSequence c, int start, int before, int count) {
				mCrime.setTitle(c.toString());
			}
			
			public void beforeTextChanged(
					CharSequence c, int start, int count, int after) {
				// This space left blank
			}
			
			public void afterTextChanged(Editable c) {
				// This one too
			}
		});
		
		mDateButton = (Button) v.findViewById(R.id.crime_data); 
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			SimpleDateFormat sdf = new SimpleDateFormat("cccc, LLL d, yyyy");
			mDateButton.setText(sdf.format(mCrime.getDate()));
		} else {
			mDateButton.setText(DateFormat.getDateInstance().format(mCrime.getDate()));
		}
		
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = new DatePickerFragment();
				dialog.show(fm, DIALOG_DATE);
			}
		});
		
		mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//set the crime's solved prop
				mCrime.setSolved(isChecked);
			}
		});
		
		return v;
	}

}
