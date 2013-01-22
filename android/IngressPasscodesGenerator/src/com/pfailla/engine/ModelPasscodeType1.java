package com.pfailla.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;

import android.util.Log;

/**
* @Author: Pierluigi Failla <pierluigi.failla@gmail.com>
* https://sites.google.com/site/zeta/ingress-passcodes
* Started 21 Jan 2013
*
* The following class is dedicate to create the statistical model of the passcodes
* 
* #  L  L  #  W L  #  L  #  L
* N1 L2 L3 N4 W L5 N6 L7 N8 L9
*/

public class ModelPasscodeType1 {
	
	private String TAG = "IngressPasscodesGenerator";
	
	private String str_url = "https://sites.google.com/site/zeta/ingress-passcodes/PasscodesType1.tsv";
	
	private enum Parts{
		N1, L2, L3, N4, W, L5, N6, L7, N8, L9;
	}
	
	private ArrayList<Item> N1 = new ArrayList<Item>();
	private ArrayList<Item> L2 = new ArrayList<Item>();
	private ArrayList<Item> L3 = new ArrayList<Item>();
	private ArrayList<Item> N4 = new ArrayList<Item>();
	private ArrayList<Item> W = new ArrayList<Item>();
	private ArrayList<Item> L5 = new ArrayList<Item>();
	private ArrayList<Item> N6 = new ArrayList<Item>();
	private ArrayList<Item> L7 = new ArrayList<Item>();
	private ArrayList<Item> N8 = new ArrayList<Item>();
	private ArrayList<Item> L9 = new ArrayList<Item>();
	
	private int [] N1bin;
	private int [] L2bin;
	private int [] L3bin;
	private int [] N4bin;
	private int [] Wbin;
	private int [] L5bin;
	private int [] N6bin;
	private int [] L7bin;
	private int [] N8bin;
	private int [] L9bin;
	
	private SecureRandom source = new SecureRandom();
	
	public ModelPasscodeType1(){
		
	}
	
	/*
	 * load() from the remote file the statistics
	 * 
	 * I'm expecting a three columns separated by one space e.g. :
	 * N1 3 132
	 * L2 z 43
	 */
	public boolean load(){
		
	    URL url = null;
		try {
			url = new URL (str_url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
			return false;
		}
	    BufferedReader br = null;
		try {
			br = new BufferedReader (new InputStreamReader (url.openConnection ().getInputStream ()));
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
			return false;
		}
		
		String line = "";
	    do
	    {
	        try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, e.toString());
				return false;
			}
	        if (line != null){
	        	String tokens[] = line.replaceAll("\\s+", " ").trim().split(" ");
	        	switch(Parts.valueOf(tokens[0].trim())){
	        	case N1:
	        		N1.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case L2:
	        		L2.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case L3:
	        		L3.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case N4:
	        		N4.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case W:
	        		W.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case L5:
	        		L5.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case N6:
	        		N6.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case L7:
	        		L7.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case N8:
	        		N8.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	case L9:
	        		L9.add(new Item(tokens[1].trim(), Integer.parseInt(tokens[2].trim())));
	        		break;
	        	}
	        }
	    }while (line != null);
	    
	    Log.d(TAG, "N1 added: "+Integer.toString(N1.size()));
	    Log.d(TAG, "L2 added: "+Integer.toString(L2.size()));
	    Log.d(TAG, "L3 added: "+Integer.toString(L3.size()));
	    Log.d(TAG, "N4 added: "+Integer.toString(N4.size()));
	    Log.d(TAG, "W added: "+Integer.toString(W.size()));
	    Log.d(TAG, "L5 added: "+Integer.toString(L5.size()));
	    Log.d(TAG, "N6 added: "+Integer.toString(N6.size()));
	    Log.d(TAG, "L7 added: "+Integer.toString(L7.size()));
	    Log.d(TAG, "N8 added: "+Integer.toString(N8.size()));
	    Log.d(TAG, "L9 added: "+Integer.toString(L9.size()));
		
	    createSupport();
	    
		return true;
	}
	
	/*
	 * createSupport() define the intervals for each possible value
	 * 
	 */
	void createSupport(){
		
		N1bin = new int [N1.size()];
		L2bin = new int [L2.size()];
		L3bin = new int [L3.size()];
		N4bin = new int [N4.size()];
		Wbin = new int [W.size()];
		L5bin = new int [L5.size()];
		N6bin = new int [N6.size()];
		L7bin = new int [L7.size()];
		N8bin = new int [N8.size()];
		L9bin = new int [L9.size()];
		
		int bin = 0;
		for(int i=0; i<N1.size(); i++){
			bin += N1.get(i).getFrequency();
			N1bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<L2.size(); i++){
			bin += L2.get(i).getFrequency();
			L2bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<L3.size(); i++){
			bin += L3.get(i).getFrequency();
			L3bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<N4.size(); i++){
			bin += N4.get(i).getFrequency();
			N4bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<W.size(); i++){
			bin += W.get(i).getFrequency();
			Wbin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<L5.size(); i++){
			bin += L5.get(i).getFrequency();
			L5bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<N6.size(); i++){
			bin += N6.get(i).getFrequency();
			N6bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<L7.size(); i++){
			bin += L7.get(i).getFrequency();
			L7bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<N8.size(); i++){
			bin += N8.get(i).getFrequency();
			N8bin[i] = bin;
		}
		bin = 0;
		for(int i=0; i<L9.size(); i++){
			bin += L9.get(i).getFrequency();
			L9bin[i] = bin;
		}
	}
	
	public String generate(){
		String passcode = "";

		int id = 0;
		int r = source.nextInt(N1bin[N1.size()-1]);
		while(r > N1bin[id]){id++;}
		passcode = passcode + N1.get(id).getSymbol();
		//Log.d(TAG, "N1 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(N1bin[id]) + " "+  N1.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(L2bin[L2.size()-1]);
		while(r > L2bin[id]){id++;}
		passcode = passcode + L2.get(id).getSymbol();
		//Log.d(TAG, "L2 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(L2bin[id]) + " "+  L2.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(L3bin[L3.size()-1]);
		while(r > L3bin[id]){id++;}
		passcode = passcode + L3.get(id).getSymbol();
		//Log.d(TAG, "L3 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(L3bin[id]) + " "+  L3.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(N4bin[N4.size()-1]);
		while(r > N4bin[id]){id++;}
		passcode = passcode + N4.get(id).getSymbol();
		//Log.d(TAG, "N4 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(N4bin[id]) + " "+  N4.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(Wbin[W.size()-1]);
		while(r > Wbin[id]){id++;}
		passcode = passcode + W.get(id).getSymbol();
		//Log.d(TAG, "W i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(Wbin[id]) + " "+  W.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(L5bin[L5.size()-1]);
		while(r > L5bin[id]){id++;}
		passcode = passcode + L5.get(id).getSymbol();
		//Log.d(TAG, "L5 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(L5bin[id]) + " "+  L5.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(N6bin[N6.size()-1]);
		while(r > N6bin[id]){id++;}
		passcode = passcode + N6.get(id).getSymbol();
		//Log.d(TAG, "N6 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(N6bin[id]) + " "+  N6.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(L7bin[L7.size()-1]);
		while(r > L7bin[id]){id++;}
		passcode = passcode + L7.get(id).getSymbol();
		//Log.d(TAG, "L7 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(L7bin[id]) + " "+  L7.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(N8bin[N8.size()-1]);
		while(r > N8bin[id]){id++;}
		passcode = passcode + N8.get(id).getSymbol();
		//Log.d(TAG, "N8 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(N8bin[id]) + " "+  N8.get(id).getSymbol());
		
		id = 0;
		r = source.nextInt(L9bin[L9.size()-1]);
		while(r > L9bin[id]){id++;}
		passcode = passcode + L9.get(id).getSymbol();
		//Log.d(TAG, "L9 i: " + Integer.toString(id) + " r: " + Integer.toString(r)+ " > " + Integer.toString(L9bin[id]) + " "+  L9.get(id).getSymbol());
		
		Log.d(TAG, "Generate: " + passcode);
		
		return passcode;
	}
}
