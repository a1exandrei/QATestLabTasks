package myprojects.automation.assignment5.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserData {

	String name;
	String lastName;
	String email;
	
	public String generateLogin(){
		Date date = new Date();
		DateFormat generated = new SimpleDateFormat("MMddhhmm");
		return (generated.format(date));
	}
	
	public String generateLogin(String name){
		Date date = new Date();
		DateFormat generated = new SimpleDateFormat("MMddhhmm");
		return (name+generated.format(date));
	}
		
	public String generateName (){
		String name = "Name";
		this.name = name;
		return name;
	}

	public String generateLastName () {
		String lastName = "LastName";
		this.lastName = lastName;
		return lastName;
	}

	public String generateEmail () {
		String email = getNumber()+"@"+getNumber()+".com";
		return email;
	}
	
	public String generateZip(){
		Integer raw = new Random().nextInt(50000)+10000;
		return raw.toString();
	}
	
	public String generateAddress(){
		return "Street "+getNumber() + ", " + getNumber().substring(0, 2);
	}
	
	public String generateCity(){
		return "City" + getNumber();
	}
	
	public String getNumber(){
		Long l = System.currentTimeMillis();
		return l.toString().substring(8, 13);
	}
}