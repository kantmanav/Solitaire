import java.util.List;
import java.util.ArrayList;

/**
 * This class represents a Board that can be used in a collection
 * of solitaire games similar to Elevens.  The variants differ in
 * card removal and the board size.
 */
public abstract class Board {

    /**
     * The cards on this board.
     */
    private Card[] originalCards;
    private Card[] compCards;
    private Card[] playerCards;

    /**
     * The deck of cards being used to play the current game.
     */
    private Deck originalDeck;
    private Deck compDeck = new Deck();
    private Deck playerDeck = new Deck();

    /**
     * Flag used to control debugging print statements.
     */
    private static final boolean I_AM_DEBUGGING = false;

    /**
     * Creates a new <code>Board</code> instance.
     * @param size the number of cards in the board
     * @param ranks the names of the card ranks needed to create the deck
     * @param suits the names of the card suits needed to create the deck
     * @param pointValues the integer values of the cards needed to create
     *                    the deck
     */
    public Board(int size, String[] ranks, String[] suits, int[] pointValues) {
        originalCards = new Card[size];
        originalDeck = new Deck(ranks, suits, pointValues);
        if (I_AM_DEBUGGING) {
            System.out.println(originalDeck);
            System.out.println("----------");
        }
        for (int i = 0; i < originalDeck.size() / 2; i++) {
            Card card = new Card(originalDeck.getCard(i).rank(), originalDeck.getCard(i).suit(), originalDeck.getCard(i).pointValue());
            if (I_AM_DEBUGGING) {
                System.out.println(card);
            }
            compDeck.addCard(card);
        }
        for (int j = originalDeck.size() / 2; j < originalDeck.size(); j++) {
            Card card = new Card(originalDeck.getCard(j).rank(), originalDeck.getCard(j).suit(), originalDeck.getCard(j).pointValue());
            if (I_AM_DEBUGGING) {
                System.out.println(card);
            }
            playerDeck.addCard(card);
        }
        System.out.println(compDeck);
        System.out.println(playerDeck);
        dealMyCards();
    }

    /**
     * Start a new game by shuffling the deck and
     * dealing some cards to this board.
     */
    public void newGame() {
        originalDeck.shuffle();
        dealMyCards();
    }

    /**
     * Accesses the size of the board.
     * Note that this is not the number of cards it contains,
     * which will be smaller near the end of a winning game.
     * @return the size of the board
     */
    public int compSize() {
        return compCards.length;
    }

    /**
     * Determines if the board is empty (has no cards).
     * @return true if this board is empty; false otherwise.
     */
    public boolean isEmpty() {
        for (int k = 0; k < compCards.length; k++) {
            if (compCards[k] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deal a card to the kth position in this board.
     * If the deck is empty, the kth card is set to null.
     * @param k the index of the card to be dealt.
     */
    public void deal(int k) {
        compCards[k] = compDeck.deal();
    }

    /**
     * Accesses the deck's size.
     * @return the number of undealt cards left in the deck.
     */
    public int deckSize() {
        return compDeck.size();
    }

    /**
     * Accesses a card on the board.
     * @return the card at position k on the board.
     * @param k is the board position of the card to return.
     */
    public Card cardAt(int k) {
        return compCards[k];
    }

    /**
     * Replaces selected cards on the board by dealing new cards.
     * @param selectedCards is a list of the indices of the
     *        cards to be replaced.
     */
    public void replaceSelectedCards(List<Integer> selectedCards) {
        for (Integer k : selectedCards) {
            deal(k.intValue());
        }
    }

        /**
         * Gets the indexes of the actual (non-null) cards on the board.
         *
         * @return a List that contains the locations (indexes)
         *         of the non-null entries on the board.
         */
        public List<Integer> cardIndexes() {
            List<Integer> selected = new ArrayList<Integer>();
            for (int k = 0; k < compCards.length; k++) {
                if (compCards[k] != null) {
                    selected.add(new Integer(k));
                }
            }
            return selected;
        }

        /**
	 * Generates and returns a string representation of this board.
	 * @return the string version of this board.
	 */
	public String toString() {
		String s = "compCards:\n";
		for (int k = 0; k < compCards.length; k++) {
			s = s + k + ": " + compCards[k] + "\n";
		}
		s = s + "playerCards:\n";
		for (int k = 0; k < playerCards.length; k++) {
			s = s + k + ": " + playerCards[k] + "\n";
		}
		return s;
	}

	/**
	 * Determine whether or not the game has been won,
	 * i.e. neither the board nor the deck has any more cards.
	 * @return true when the current game has been won;
	 *         false otherwise.
	 */
	public boolean gameIsWon() {
		if (compDeck.isEmpty()) {
			if (playerDeck.isEmpty()) {
        		        for (Card c : compCards) {
        				if (c != null) {
        					return false;
        				}
        			}
        			for (Card c : playerCards) {
        				if (c != null) {
        					return false;
        				}
        			}
        			return true;
                        }
                        return false;
		}
		return false;
	}

	/**
	 * Method to be completed by the concrete class that determines
	 * if the selected cards form a valid group for removal.
	 * @param selectedCards the list of the indices of the selected cards.
	 * @return true if the selected cards form a valid group for removal;
	 *         false otherwise.
	 */
	public abstract boolean isLegal(List<Integer> selectedCards);

	/**
	 * Method to be completed by the concrete class that determines
	 * if there are any legal plays left on the board.
	 * @return true if there is a legal play left on the board;
	 *         false otherwise.
	 */
	public abstract boolean anotherPlayIsPossible();

	/**
	 * Deal cards to this board to start the game.
	 */
	private void dealMyCards() {
		for (int k = 0; k < compCards.length; k++) {
			compCards[k] = compDeck.deal();
		}
		for (int k = 0; k < playerCards.length; k++) {
			playerCards[k] = playerDeck.deal();
		}
	}
}
