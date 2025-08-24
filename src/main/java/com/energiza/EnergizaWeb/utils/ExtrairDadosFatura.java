package com.energiza.EnergizaWeb.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExtrairDadosFatura {

	public String extrairFatura() {
		
		String line = "";
		String json = "";
		
		try {
	        
	        ProcessBuilder pb = new ProcessBuilder("python", "C:\\EnergizaWeb\\scripts\\extrairFatura.py");

	        pb.redirectErrorStream(true);
	        
	        Process process = pb.start();
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));	        

	        while ((line = reader.readLine()) != null) {
	        	json += line;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return json;
		
	}
	
}