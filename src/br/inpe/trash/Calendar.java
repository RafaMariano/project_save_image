package br.inpe.trash;

public class Calendar {
	private static java.util.Calendar calendar;
	private Calendar() {
	}

	public static synchronized java.util.Calendar getInstance() {
		if (calendar == null)
			calendar = java.util.Calendar.getInstance();

		return calendar;
	}
	
//	public java.util.Calendar getCalendar(){
//		
//	}
}
