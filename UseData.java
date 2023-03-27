import java.util.*;

public class UseData
{
	// global var for entry number
	// dates array of Strings which is necessary for all entry objs
	int entryNum;
	ArrayList<String> dates;
	String suffix;
	
	// constructor - assign the provided int as the
	// entry number; this should pass the integer given in 
	// the source txt so '111' on the first line should assign
	// the entry number to 111.
	public UseData(int number)
	{
		entryNum = number;
		dates = new ArrayList<String>();
	}
	
	// add a date under an entry by adding it to this object's 
	// array of String dates.
	public void addDate(String date)
	{
		dates.add(date);
	}
	
	// convert a xx.yy.zz or xx.yy.zzpi date format into a human
	// format, such as January 1, 2022, and return it.
	public String readDate(String date)
	{
		String[] months = { "", "January ", "Feburary ", "March ", "April ",
							"May ", "June ", "July ", "August ", "September ",
							"October ", "November ", "December " };
						
		// string for finality, split date into fields
		String dateConverted = "";
		String[] fields = date.split("[.]");
		
		// format the date into written english
		dateConverted += months[ Integer.parseInt(fields[0]) ];
		dateConverted += Integer.parseInt(fields[1])+", ";
		dateConverted += "20"+( fields[2].substring(0,2) ); 
		
		// if there's a suffix - p1, p2, etc
		if(date.length() > 8)
		{	
			// record the suffix and add it
			suffix = fields[2].substring(3);
			dateConverted += "(P"+suffix+")";
		}
		
		// return the date
		return dateConverted;
	}
	
	// return the entry number of the object
	public int getNumber()
	{ 	return entryNum; }
	
	// return the dates associated with the object
	public ArrayList<String> getDates()
	{	return dates; }
	
	// return the number of dates associated with the object
	public int useCount()
	{	return dates.size(); }
	
	// return suffix
	public String getPartNo()
	{	return suffix; }
	
	// return the earliest date from an entry
	public String getEarliest()
	{
		boolean needsUpdate = true;
		
		// split date fields into integers (necessary for conversions)
		int lowestY = Integer.parseInt(dates.get(0).substring(0,2));
		int lowestM = Integer.parseInt(dates.get(0).substring(3,5));
		int lowestD = Integer.parseInt(dates.get(0).substring(6,8));
		
		String lowestDate = "";
		
		// for each date associated with an entry,
		for(String date : dates)
		{
			// segment date into fragments like above.
			String[] segments = date.split("[.]");
			segments[2] = segments[2].substring(0,2);
			// record the ints as fields
			int[] fields = { Integer.parseInt(segments[0]), 
							 Integer.parseInt(segments[1]),
							 Integer.parseInt(segments[2])  };
			
			// if the segment year < lowest year
			// instant update
			if( fields[2] < lowestY )
				needsUpdate = true;
			
			// if the segment and lowest year are equal
			else if( fields[2] == lowestY )
			{
				// if the segment month < lowest month
				// update
				if( fields[0] < lowestM )
					needsUpdate = true;
				// if the segment and lowest month are equal
				else if( fields[0] == lowestM )
					// if the segment day < lowest day
					// update
					if( fields[1] < lowestD )
						needsUpdate = true;
			}
		
			// if needed to update, record newest numbers
			// set object to lowest, reset flag
			if(needsUpdate)
			{
				lowestY = fields[2];
				lowestM = fields[0];
				lowestD = fields[1];
				
				lowestDate = date;
				needsUpdate = false;
			}
		}
		
		return lowestDate;
	}
	
	public UseData compareDates(UseData comparedEntry)
	{
		boolean needsUpdate = false;
		UseData earliestEntry = this;
		
		String objectEarliest = getEarliest();
		String comparedEarliest = comparedEntry.getEarliest();
		
		int lowestD = Integer.parseInt(objectEarliest.substring(0,2));
		int lowestM = Integer.parseInt(objectEarliest.substring(3,5));
		int lowestY = Integer.parseInt(objectEarliest.substring(6,8));
		
		String[] segments = comparedEarliest.split("[.]");
		segments[2] = segments[2].substring(0,2);
		int[] fields = { Integer.parseInt(segments[0]), 
						 Integer.parseInt(segments[1]),
						 Integer.parseInt(segments[2])  };
		
		if( fields[2] < lowestY )
			needsUpdate = true;
			
		else if( fields[2] == lowestY )
		{
			if( fields[0] < lowestM )
				needsUpdate = true;
			else if( fields[0] == lowestM )
				if( fields[1] < lowestD )
					needsUpdate = true;
		}
	
		if(needsUpdate)
			earliestEntry = comparedEntry;
		
		return earliestEntry;
	}
   
   // public long getFileSize()
   // {
   //    use binary search to locate a file which matches the entry number
   //    use file.length to get byte data of the file
   //    divide it by 1000 x 1000 to get the mb data of the file
   //    if over 1000 mb, format as 1.X gb
   // }
   
   // public String getName()
   // {
   //    use binary search to locate a file which matches the entry number
   //    print the file's name
   // }
   
	// toString format:
	//
	//    Entry number : 111
	//    Use count    :   6
	//       January 1, 2021
	//    September 22, 1991
	//
	public String toString()
	{
		String formatted = "";
		
		// begin entry - print number and use count
		formatted += "----------------------\n";
		formatted += String.format( "%-14s%s%7d\n", "Entry number", ":", getNumber() );
		formatted += String.format( "%-14s%s%7d\n", "Use count", ":", useCount() );
		// read all dates associated with entry
		formatted += "--\n";
		for( String date : dates )
			formatted += String.format( "%22s\n", readDate(date) );
		// earliestentry result
		formatted += "--\n";
		formatted += String.format( "%-14s%s\n", "Earliest use", ":" );
		formatted += String.format( "%22s\n", readDate(getEarliest()) );
		
		return formatted;
	}
}