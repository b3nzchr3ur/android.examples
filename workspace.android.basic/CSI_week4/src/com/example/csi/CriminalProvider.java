package com.example.csi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.csi.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

/**
 * This class can be used to generate random criminals, and retrieve them based on their index.
 * @author Michael
 *
 */
@SuppressLint("UseSparseArrays")
public class CriminalProvider{

	/**
	 * This list contains the generated criminals.
	 * Notice the static. This means that all CriminalProvider classes share the same list.
	 */
	private static List<Criminal> criminalList;
	/**
	 * This context can be used to acquire the resources.
	 */
	private Context context;

	/**
	 * Constructor of the CriminalProvider.
	 * @param context The context (i.e. the activity) that is using this provider.
	 */
	public CriminalProvider(Context context)
	{
		this.context = context;

		if(criminalList == null)
		{
			criminalList = new ArrayList<Criminal>();
			fillCriminalList();
		}
	}

	/**
	 * Get the list with all stored criminals
	 * @return the list with criminals
	 */
	public List<Criminal> GetCriminals()
	{
		return criminalList;
	}
	
	/**
	 * Get a specific criminal 
	 * @param the index of the criminal in the list
	 * @return the criminal
	 */
	public Criminal GetCriminal(int index) {
		if(index >= criminalList.size() ) return null;
		
		return criminalList.get(index);
	}

	/**
	 * Creates a criminals.
	 */
	private void fillCriminalList()
	{
		//Load criminal and crime information from resource xml files (see res/values folder):
		String[] criminalNames = context.getResources().getStringArray(R.array.criminalNames);
		String[] criminalDetails = context.getResources().getStringArray(R.array.criminalDetails);
		
		int[] drawableIds = new int[] { R.drawable.mugshot1, R.drawable.mugshot2,
				R.drawable.mugshot3, R.drawable.mugshot4, R.drawable.mugshot5 };
		
		String[] crimeNames = context.getResources().getStringArray(R.array.crimeNames);
		String[] crimeDetails = context.getResources().getStringArray(R.array.crimeDetails);
		
		for(int criminalIndex = 0; criminalIndex < criminalNames.length; criminalIndex++)
		{
			Criminal someCriminal = new Criminal();
			
			someCriminal.name = criminalNames[criminalIndex];
			someCriminal.description = criminalDetails[criminalIndex];
			
			int drawableId = drawableIds[criminalIndex];
			someCriminal.mugshot = context.getResources().getDrawable(drawableId);
			
			Random r = new Random();
			someCriminal.gender = r.nextBoolean() ? "Male":"Female";
			someCriminal.age = 10 + r.nextInt(100);
			
			someCriminal.lastKnownLocation = new Location("");
			someCriminal.lastKnownLocation.setLatitude(-180.0 + r.nextDouble() * 180.0);
			someCriminal.lastKnownLocation.setLongitude(-180.0 + r.nextDouble() * 180.0);
			
			someCriminal.crimes = new ArrayList<Crime>();
			int maxNumberOfCrimes = context.getResources().getStringArray(R.array.crimeNames).length;
			int numCrimes = 1 + r.nextInt(maxNumberOfCrimes);
			for (int c = 0 ; c < numCrimes; c++)
			{
				Crime crime = createRandomCrime(crimeNames, crimeDetails);
				someCriminal.crimes.add(crime);
			}

			criminalList.add(someCriminal);
		}
		
		Collections.shuffle(criminalList);
	}

	/**
	 * Creates a random crime. The name and description are picked from the resource files.
	 * @return The random crime.
	 */
	private Crime createRandomCrime(String [] crimeNames, String[] crimeDetails)
	{
		Crime randomCrime = new Crime();
		Random r = new Random();
	
		int randomInt = r.nextInt(crimeNames.length);
		randomCrime.name = crimeNames[randomInt];
		
		randomInt = r.nextInt(crimeDetails.length);
		randomCrime.description = crimeDetails[randomInt];
		
		randomCrime.bountyInDollars = r.nextInt(100000);
	
		return randomCrime;
	}
}

