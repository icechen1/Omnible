package com.omnible;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SettingsProvider {
	Context cxt;
	
    SharedPreferences settings;
	SharedPreferences.Editor edit;
	
	String sMariano = "Marianopolis";
	
	public enum Cegep{
		MARIANO
	}
	
	public enum Language{
		ENGLISH, FRENCH
	}
	
	SettingsProvider(Context cxt){
		this.cxt = cxt;
		settings = PreferenceManager.getDefaultSharedPreferences(cxt.getApplicationContext());
		edit = settings.edit();
	}
	
	/**
	 * Creates a new instance of SettingsProvider
	 * @param cxt Context
	 * @return a SettingsProvider object
	 */
	public static SettingsProvider newInstance(Context cxt){
		return new SettingsProvider(cxt);
	}

	/**
	 * Fetches the saved username
	 * @return integer: username (student ID)
	 */
	public int getUsername(){
		int user = settings.getInt("username", 0);
		return user;
	}
	
	/**
	 * Fetches the saved password
	 * @return integer: password
	 */
	public String getPassword(){
		String pass = settings.getString("pass", "");
		return pass;
	}
	
	/**
	 * Fetches the cegep type
	 * @return integer: password
	 */
	public Cegep getCegep(){
		String user = settings.getString("cegep", "N/A");
		if (user.equals(sMariano))
		{
			return Cegep.MARIANO;
		}
		return null;
	}
	
	/**
	 * Fetches the language type
	 * @return integer: password
	 */
	public Language getLang(){
		String user = settings.getString("cegep", "N/A");
		if (user.equals(sMariano))
		{
			return Language.ENGLISH;
		}
		return null;
	}

	/**
	 * Saves the username
	 * @param value Student ID
	 */
	public void saveUsername(int value){
		edit.putInt("username", value).commit();
	}
	
	/**
	 * Saves the password
	 * @param value password
	 */
	public void savePassword(String value){
		edit.putString("pass", value).commit();
	}
	
	/**
	 * Saves cegep name
	 * @param name cegep name (String)
	 */
	public void saveCegep(Cegep name){
		
		if (name.equals(Cegep.MARIANO))
		{
			edit.putString("cegep", sMariano).commit();
		}

	}
	
}
