/**
 * Enums are preferably to constants as they can:
 *       1. enforce type safety
 *       2. implement interfaces, have behaviour and so on (enums are basically classes)
 *       3. add fields, constructors and methods
 *       4. group things in a set
 *       5. limit inputs (the compiler won’t allow parsing a constant of a wrong enum to a method that expects an enum type)
 *       6. iterate (enums are iterable)
 *       7. be used as a singleton in java
 * 
 * Enum Methods: 
 *      hashCode() –> because equals() is overridden
 *      getDeclaringClass() –> returns the class Object corresponding to the current constant’s enum -> eg. class cl=weekends.getDeclaringClass();
 *      name() –> returns constant’s name
 *      ordinal() –> position of the constant within enum type
 *      compareTo() -> compares ordinals
 *      public static <T extends Enum <T>> T valueOf (class <T> enumType, String name) –> for returning enum constant from the specified enum with the specified name
 * 
 * The compiler converts enum Weekend {---------} into a class with name Weekend and extends Enum<Weekend>.
 * It won’t allow us to explicitly extend the Enum class.
 * 
 * When anything else other than constants are there, comma-separated constants should be terminated with a semicolon. Otherwise, semicolon is optional.
 */

// hashCode()
public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((enum1 == null) ? 0 : enum1.hashCode());
    result = prime * result + ((enum2 == null) ? 0 : enum2.hashCode());
    return result;
}

// Assign a different behavior to each constant by introducing an abstract method into the enum and overriding this method in an anonymous subclass of the constant
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

// Enums are supported by the switch statement from java 5
// When using enum in switch statement, the enum constants are the only allowed labels for cases
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

// Call enums
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

// Get Enum value from String
public enum Blah { A, B, C, D }
assertEquals(Blah.A, Blah.valueOf("A"));

// Override Enum value without @Getter
public String getValue() {
    return CaseUtils.toCamelCase(this.name(), capitalizeFirstLetter: true, '_');
｝
