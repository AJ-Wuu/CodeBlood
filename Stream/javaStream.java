/**
 * @author AJWuu
 */

package stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

public class javaStream {

	/*
	 * The word stream is used in many places throughout the JavaAPI.
	 * These stream methods can be organized into the following types:
	 * 1. Source (where the data comes from)
	 * 2. Intermediate Computation / Methods (main function, as many as needed)
	 * 3. Terminal (output)
	 * 
	 * In java.util.function --
	 * Consumer<T>: takes one input of a generic type, and returns nothing.
	 * BiConsumer<T,U>: takes two inputs of generic types, and returns nothing.
	 * Supplier<T>: takes no inputs, and returns a value of a generic type.
	 * Function<T,R>: takes one input of a generic type, and returns a value of a generic type.
	 * 
	 * In java.lang.runnable -- 
	 * run(): takes no inputs, and returns nothing.
	 */

	public static void main(String[] args) throws Exception {
		//Source
		Stream<String> filenameStream = Stream.of(new File("P:\\Java\\Stream\\src\\stream\\data\\").list());

		//Intermediate
		filenameStream
			//returns a stream consisting of the results of applying the given function to the elements of this stream
			.map( filename -> "P:\\Java\\Stream\\src\\stream\\data\\"+ filename ) //change the value of the stream
			.map( path -> new File(path) ) //change the type of the stream (from String to File)
			.map( file -> { //change the stream from File to the Strings in the file
				try {
					Scanner fin = new Scanner(file);
					return fin.nextLine();    
				}
				catch(FileNotFoundException e) { 
					return "No file found";
				}
			})
			.flatMap( list -> Stream.of( list.split(",") ) ) //get rid of all the streams in direction down to just one stream (of String here)
			.filter( produce -> produce.length() > 6 ) //returns a stream consisting of the elements of this stream that match the given predicate
			.map( lowercase -> lowercase.toUpperCase() ) 
			.sorted() //returns a stream consisting of the elements of this stream, sorted according to natural order
			//Output
			.forEach( x -> System.out.println(x)); //performs an action for each element of this stream
		Stream<Integer> oddStream = Stream.iterate(1,i -> i + 2);
		Integer sum = oddStream
			//.limit(5) //returns a stream consisting of the elements of this stream, truncated to be no longer than maxSize in length
			.takeWhile( i -> i < 10 ) //returns, if this stream is ordered, a stream consisting of the longest prefix of elements taken from this stream that match the given predicate
			.map( i -> i + 1 )
			.reduce( (x,y) -> x+y ) //performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function, and returns the reduced value
			.orElse(0);
		//.forEach( i -> System.out.println(i));
		System.out.println(sum);
	}

}
