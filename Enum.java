/**
 * Enums are preferably to constants as they can:
 *       1. enforce type safety
 *       2. implement interfaces, have behaviour and so on (enums are basically classes)
 *       3. add fields, constructors and methods
 * 
 * The compiler converts enum Weekend {---------} into a class with name Weekend and extends Enum<Weekend>.
 * It won’t allow us to explicitly extend the Enum class.
 * 
 * When anything else other than constants are there, comma-separated constants should be terminated with a semicolon. Otherwise, semicolon is optional.
 */

//Assign a different behavior to each constant by introducing an abstract method into the enum and overriding this method in an anonymous subclass of the constant
enum Weekend {
    SATURDAY {
        @Override
        Public void someMethod(){}
    },
    SUNDAY {
        @Override
        Public void someMethod(){}
    };
    public abstract void someMethod();
}

//Enums are supported by the switch statement from java 5
//When using enum in switch statement, the enum constants are the only allowed labels for cases
enum Weekend {
    SATURDAY, SUNDAY;
}

public static void switch(enum Weekend) {
    Weekend weekends = weekend.SATURDAY;
    Switch (weekends) {
        Case SATURDAY:
            sysout (“Saturday”);
            break;
        Case SUNDAY:
            sysout (“sunday”);
            break;
    }
}

//Call enums
public enum CardColour {
    RED, BLACK
}

public enum Suit {
    SPADES(CardColour.BLACK),
    CLUBS(CardColour.BLACK),
    HEARTS(CardColour.RED),
    DIAMONDS(CardColour.RED);

    private final CardColour colour;
    Suit(CardColour colour) { this.colour = colour; }
    public CardColour getColour() { return colour; }
}

