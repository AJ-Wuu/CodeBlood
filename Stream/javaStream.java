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
	 */
	
    public static void main(String[] args) throws Exception {
    	//Source
        Stream<String> filenameStream = Stream.of(new File("P:\\Java\\Stream\\src\\stream\\data\\").list());

        //Intermediate
        filenameStream
            .map( filename -> "P:\\Java\\Stream\\src\\stream\\data\\"+ filename )
            .map( path -> new File(path) )
            .map( file -> {
                try {
                    Scanner fin = new Scanner(file);
                    return fin.nextLine();    
                } catch(FileNotFoundException e) { 
                    return "no file was found";
                }
            })
            .flatMap( list -> Stream.of( list.split(",") ) )
            .filter( produce -> produce.length() > 6 )
            .map( lowercase -> lowercase.toUpperCase() ) 
            .sorted()
            .forEach( x -> System.out.println(x) ); //Output
        Stream<Integer> oddStream = Stream.iterate(1,i -> i + 2);
        Integer sum = oddStream
            //.limit(5)
            .takeWhile( i -> i < 10 )
            .map( i -> i + 1 )
            .reduce( (x,y) -> x+y )
            .orElse(0);
            //.forEach( i -> System.out.println(i) );
        System.out.println( sum );
    }
    
}
