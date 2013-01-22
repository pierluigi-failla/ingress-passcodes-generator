package com.pfailla.engine;

/**
* @Author: Pierluigi Failla <pierluigi.failla@gmail.com>
* https://sites.google.com/site/zeta/ingress-passcodes
* Started 21 Jan 2013
*
* The following class is dedicate to maintain the Items. Each Item is a portion
* of the passcode, as you now the passcode can be written as: 
* # L L # W L # L # L (see https://sites.google.com/site/zeta/ingress-passcodes)
*/

public class Item{
	
	private String symbol; // is a string containing the symbol: letters, numbers or words
	private int frequency; // is an integer number indicates how many time the symbols appeared
		
	public Item(String symbol, int frequency){
		this.symbol = symbol;
		this.frequency = frequency;
	}
	
	void setSymbol(String symbol){ this.symbol = symbol;}
	void setFrequency(int frequency){ this.frequency = frequency;}
	
	String getSymbol(){ return this.symbol;}
	int getFrequency(){ return this.frequency;}
}
