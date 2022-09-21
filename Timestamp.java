import java.sql.Timestamp;
import java.time.Instant;

public class timestamp {
    
    public static void main(String[] args) {
        Timestamp instant1 = Timestamp.from(Instant.now()); //from() method Obtains an instance of Timestamp from an Instant object
        System.out.println("1. from() method will return " + instant1);

        String str = "2018-09-01 09:01:15";
        Timestamp timestamp = Timestamp.valueOf(str); //valueOf() method returns a Timestamp value corresponding to the given string
        System.out.println("2. value of Timestamp : " + timestamp);

        int valNano = timestamp.getNanos(); //getNanos() method gets the Timestamp object's nanos value
        System.out.println("3. Fractional seconds component : " + valNano);

        Timestamp ts1 = Timestamp.valueOf("2018-09-01 09:01:16");
        System.out.println("4. Boolean value returned : " + timestamp.before(ts1)); //before() returns Boolean value true if this ts comes earlier than given ts1

        Timestamp ts2 = Timestamp.valueOf("2018-09-01 09:01:15");
        long valMilli = ts2.getTime(); //getTime() method returns the number of milliseconds
        System.out.println("5. Milliseconds : " + valMilli);

        int valHash = ts2.hashCode(); //hashCode() method returns the hash code for this object
        System.out.println("6. Hash code : " + valHash);

        ts2.setNanos(54647); //setNanos() method sets nanos value for the specified integer value
        System.out.println("7. Timestamp after setting nanos : " + ts2);

        Instant instant2 = ts2.toInstant(); //toInstant() method returns an Instant which represents the same point on the time-line as this Timestamp
        System.out.println("8. Instant time span : " + instant2);
    }
    
}
