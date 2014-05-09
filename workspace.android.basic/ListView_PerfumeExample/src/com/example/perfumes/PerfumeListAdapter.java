package com.example.perfumes;

import java.util.List;

import com.example.perfumes.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PerfumeListAdapter extends ArrayAdapter<Perfume> {
	private Context context;
	private List<Perfume> perfumes;
	
	public PerfumeListAdapter(Context context, List<Perfume> perfumes) {
		super(context, R.layout.main, perfumes);
		this.perfumes = perfumes;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View perfumeView  = inflater.inflate(R.layout.perfume, null);
		
		Perfume perfume = perfumes.get(position);	
		
		TextView nameView = (TextView) perfumeView.findViewById(R.id.textViewName);
		nameView.setText(perfume.name);
		
		TextView priceView = (TextView) perfumeView.findViewById(R.id.textViewPrice);
		priceView.setText(String.format("$ %d,-",perfume.price));
		
		return perfumeView;
	}
}
