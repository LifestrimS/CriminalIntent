package com.example.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CrimeListFragment extends Fragment {

    private Unbinder unbinder;
    private CrimeAdapter mAdapter;

    @BindView(R.id.crime_recycler_view)
    RecyclerView mCrimeRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list,
                container,
                false);
        unbinder = ButterKnife.bind(this, view);

        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(View v){
            super(v);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mTitleTextView = itemView.findViewById(R.id.crime_title);
            itemView.setOnClickListener(this);
        }


        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;
        private final int TYPE1 = 1; //Required
        private final int TYPE2 = 2; //NoRequired

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case TYPE1:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime_2, parent, false);
                    break;
                case TYPE2:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime, parent, false);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + viewType);
            }
            //LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemViewType(int position) {
            if (position % 2 == 0) {
                return TYPE1;
            } else {
                return TYPE2;
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
