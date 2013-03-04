package com.omnible.parser;

import java.io.IOException;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class NotifParser
{
	private Document homepage;	
	private Elements assignments, documents, collegeDocs, mios, grades;
	private ArrayList<String> notifs = new ArrayList<String>();
	private ArrayList<String> links = new ArrayList<String>();
	
	private String loginURL;
	private String homeURL = "https://marianopolis.omnivox.ca";
	
	public NotifParser(String un, String pw)
	{		
		loginURL = "https://marianopolis.omnivox.ca/intr/Module/Identification/Login/Login.aspx?NoDA=" + un + "&PasswordEtu=" + pw + "&ReturnUrl=%2fintr%2fwebpart.ajax%3fIdWebPart%3d00000000-0000-0000-0003-000000000008%26L%3dANG&IdWebPart=00000000-0000-0000-0003-000000000008&L=ANG";
	}
	
	public void refresh() throws IOException
	{
	//	try
	//	{
			homepage = Jsoup.connect(loginURL).get();
	//	}catch(Exception e){} //TODO Add error handling (or, you know, logging).
		
		documents = homepage.getElementsMatchingOwnText("new documents? from your teachers");		
		assignments = homepage.getElementsMatchingOwnText("new assignment instructions? available");
		collegeDocs = homepage.getElementsMatchingOwnText("new documents? from the College");
		mios = homepage.getElementsMatchingOwnText("new Mio");
		grades = homepage.getElementsMatchingOwnText("new evaluation grades?"); //TODO Correct this?
	
	}

	public ArrayList<String> getNotifs()
	{				
		notifs.clear();

		try{
			if(!documents.text().equals("")) 
				notifs.add(documents.text());	
		} catch(Exception e) {

		}
		try{
			if(!assignments.text().equals(""))
				notifs.add(assignments.text());			
		} catch(Exception e) {

		}
		try{
			if(!collegeDocs.text().equals(""))
				notifs.add(collegeDocs.text());	
		} catch(Exception e) {

		}

		try{
			if(!mios.text().equals(""))
				notifs.add(mios.text());

		} catch(Exception e) {

		}
		try{
			if(!grades.text().equals(""))
				notifs.add(grades.text());	

		} catch(Exception e) {

		}

		for (String s: notifs){
			Log.d("NOTIFPARSER", s);
		}
		return notifs;
	}
	
	public ArrayList<String> getLinks()
	{
		links.clear();
		
		if(!documents.text().equals("")) 
			links.add(homeURL + documents.attr("href"));	
		if(!assignments.text().equals(""))
			links.add(homeURL + assignments.attr("href"));	
		if(!collegeDocs.text().equals(""))
			links.add(homeURL + collegeDocs.attr("href"));	
		if(!mios.text().equals(""))
			links.add(homeURL + mios.attr("href"));	
		if(!grades.text().equals(""))
			links.add(homeURL + grades.attr("href"));
		
		return links;
	}
	
	public void printResults()
	{
		for(String a : notifs)
		{
			System.out.println(a);//TODO Log instead of println, maybe?
		}
		
		for(String a : links)
		{
			System.out.println(a);
		}
	}
}
