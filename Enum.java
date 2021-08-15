/**
 * Enums are preferably to constants as they can enforce type safety.
 * Enums are basically classes; they can implement interfaces, have behaviour and so on.
 */

//implement interface
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

