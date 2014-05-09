package com.example.csi;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.location.Location;

public class Criminal {

	public String name;
	public String gender;
	public String description;
	public int age;
	
	public ArrayList<Crime> crimes;
	
	public Drawable mugshot;
	public Location lastKnownLocation;
	
	public int getBountyInDollars() {
		int bounty = 0;
		for(Crime crime : crimes) {
			bounty += crime.bountyInDollars;
		}
		
		return bounty;
	}
}
