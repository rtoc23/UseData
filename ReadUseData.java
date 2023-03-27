import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;

public class ReadUseData
{
   // init scanner and list of entries (no size)
   private static Scanner input;
   private static ArrayList<UseData> videos = new ArrayList<UseData>();

   public static void main(String[] args)
   {
	  // filename
	  String s = "000 - data.txt";
	  
	  // open, read, and close file as to
	  // take use data from txt
      openFile(s);
      readRecords();
      closeFile();
	  
	  // cycle and print all data from file read 
     /*
	  for(UseData use : videos)
		System.out.println( use ); 
	  */
	  
	  System.out.println(earliestUseCase());
	  System.out.println(mostPopularCase());
	  
	  getRandom( );
	  // getRandom(4);
   } 

   // opening the file. p simple.
   public static void openFile(String file)
   {
      try
      {
	   input = new Scanner(Paths.get(file)); 
      } 
      catch (IOException ioException)
      {
		System.err.println("Error opening file. Terminating.");
		System.exit(1);
      } 
   }

   public static void readRecords()
   {
      try 
      {
		 // retain first file as integer. eat the newline character
		 int entryNum = input.nextInt();
						input.nextLine();
		 // System.out.println(entryNum); // debug
		 
		 //		input.hasNextLine()
         while( input.hasNextLine() ) // while there is more to read
         {
			 // flag for "has items"
			 boolean dateItem = true;

			 // entry item created : this item will take
			 // dates until a new int is found and then create
			 // a new entry item.
			 UseData entry = new UseData(entryNum);
			
			 while( dateItem ) // while there is more to read
			 {
				 // save the current line as a string
				 //					  input.nextLine();
				 String currentLine = input.nextLine();
				 currentLine = currentLine.trim();
				 // System.out.println(currentLine); // debug
				 
				 // if the line is a date (has a period, all
				 // dates are formatted with period delimiters in 
				 // this environment) add a date to the entry obj.
				 if( currentLine.contains(".") )
					 entry.addDate(currentLine);
				 // otherwise, make sure the current line string escapes
				 // this loop so it can be used to create a new entry obj. 
				 // then break loop.
				 else
				 {
					 entryNum = Integer.parseInt(currentLine);
					 dateItem = false;
				 }
				 
				 // check to see if end of file has been reached to break
				 // while loop and prevent a catch block from running.
				 if( !input.hasNextLine() )
				 	 dateItem = false;
			 }
			 
			 // add the entry object to an array. the entry object
			 // in turn has an array of Strings as dates inside of it
			 videos.add(entry);
         }
      } 
	  // if no more elements or wrong element, catch
      catch (NoSuchElementException elementException)
      {
         System.err.println("File improperly formed. Terminating.");
      } 
      catch (IllegalStateException stateException)
      {
         System.err.println("Error reading from file. Terminating.");
      } 
   } 

   public static void closeFile()
   {
      if (input != null)
         input.close();
   } 
   
   // return the earliest date of any entry
   public static String earliestUseCase()
   {
	   // begin with the earliest entry in the text file
	   UseData earliestItem = videos.get(0);
	   
	   // comb every entry. the compare method returns the object
	   // with the earlier date. if the object returned by the method
	   // is equal to the current entry, make the current entry 
	   // the earliest recorded entry.
	   for(UseData item : videos)
		   if( earliestItem.compareDates(item).equals(item) )
			   earliestItem = item;
		   
	   // 22 hyphens
	   // left aligned earliest entry : 
	   // right aligned entry num
	   // right aligned earliest date
	   String complete = "---------------------- *\n";
			  complete += String.format( "%-14s%s\n", "Earliest entry", ":");
			  complete += String.format( "%22d\n", earliestItem.getNumber() );
			  complete += String.format( "%22s\n", earliestItem.getEarliest() );
			  // print filename as last line as well?
			  
	   return complete;
   }
   
   // return the most popular entry (most uses)
   // same as above, except it measures the use count
   public static String mostPopularCase()
   {
	   UseData popularItem = videos.get(0);
	   
	   for(UseData item : videos)
		   if( item.useCount() > popularItem.useCount() )
			   popularItem = item;
		   
	   String complete = "---------------------- *\n";
			  complete += String.format( "%-14s%s\n", "Most popular", ":");
			  complete += String.format( "%22d\n", popularItem.getNumber() );
			  complete += String.format( "%22s\n", popularItem.useCount() );
			  // print filename as last line as well?
			  
	   return complete;
   }
   
   public static void getRandom()
   {
	   int choice = (int)(Math.random() * videos.size());
	   UseData chosen = videos.get(choice);
	   
	   System.out.println("Random item   :");
	   System.out.println(chosen);
   }
   
   public static void getRandom(int runcount)
   {
	   System.out.println("Random cases  :    "+runcount);
	   
	   for(int i = 0; i < runcount; i++)
		   getRandom();
   }
}   