/**
 * Enums are preferably to constants as they can enforce type safety.
 * Enums are basically classes; they can implement interfaces, have behaviour and so on.
 */
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
