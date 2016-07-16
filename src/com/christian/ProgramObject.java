/**

 * 
 */
package com.christian;

import java.util.Arrays;




/**
 * @author Christian
 *
 */
public class ProgramObject {

	Object ob;
	SingleProgram[] programlist = new SingleProgram[2];			
	public String type = "";
	ProgramObject(){
		programlist = new SingleProgram[2];
		for (SingleProgram sp: programlist){
			sp = new SingleProgram();
		}
		type="program";
		
	}
	

	
	
}

class SingleProgram {
	String[] times = new String[11];
	boolean[] days = new boolean[6];
	String start = new String();
	
	SingleProgram(){
		Arrays.fill(times,"0");
		Arrays.fill(days, false);
		start = "20";
		
	}
	
	

	
}