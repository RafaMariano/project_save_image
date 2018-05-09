package br.inpe.trash;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormatDecimal {
	private DecimalFormatSymbols otherSymbols; 
	private DecimalFormat df; 
	private SimpleDateFormat dateFormat;
	//transformar o formatDecimal em singleton
	
	public FormatDecimal(){
		
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");		
		this.otherSymbols = new DecimalFormatSymbols();
		this.otherSymbols.setDecimalSeparator('.');
		
		this.df = new DecimalFormat("##.##", otherSymbols);
		this.df.setMaximumFractionDigits(2);
	
	}
	
	public void setTime(String time) throws ParseException{
		Calendar.getInstance().setTime(this.dateFormat.parse(time));
		
	}
	
	public int getTime(int field){
		return Calendar.getInstance().get(field);
	}
	
	public String setFloat(int number1, int number2){
		return this.df.format( (float)number1 + ((float)number2/100));
		
	}
	
	
	
}
