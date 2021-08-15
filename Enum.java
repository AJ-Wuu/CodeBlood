/**
 * Enums are preferably to constants as they can:
 *       1. enforce type safety
 *       2. implement interfaces, have behaviour and so on (enums are basically classes)
 *       3. add fields, constructors and methods
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

//
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

