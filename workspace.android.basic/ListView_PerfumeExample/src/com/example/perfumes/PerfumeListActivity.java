package com.example.perfumes;

import java.util.ArrayList;
import java.util.List;

import com.example.perfumes.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PerfumeListActivity extends Activity implements OnItemClickListener {
  
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        List<Perfume> perfumes = getDummyPerfumes();
        
        PerfumeListAdapter perfumeListAdapter = 
        		new PerfumeListAdapter(getApplicationContext(), perfumes);

        ListView listView = (ListView) findViewById(R.id.listViewPerfume);
        listView.setAdapter(perfumeListAdapter);
        
        listView.setOnItemClickListener(this);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PerfumeListAdapter perfumeListAdapter = (PerfumeListAdapter) parent.getAdapter(); 
		Perfume perfume = (Perfume) perfumeListAdapter.getItem(position);
		Toast toast = Toast.makeText(this, "Clicked on " + perfume.name,Toast.LENGTH_SHORT);
		toast.show();
	}

	private List<Perfume> getDummyPerfumes() {
		List<Perfume> perfumes = new ArrayList<Perfume>();
	    for(int i = 0; i < 25; i++) {
	    	Integer price = 45 + i*20;
	    	perfumes.add(new Perfume("Chanel no. " + i, price));
	    }
	    
	    return perfumes;
	}
}