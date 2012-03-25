package model;

import java.beans.PropertyChangeListener;
import model.Round;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 * @author Anton Lindgren
 * @modified by John Hult, Victor Lindhé
 */
public class Game {

    // Instances
    private final Battlefield battlefield;
    private final List<Player> players;
    private final int numberOfRounds;
    private List<Round> comingRounds;
    private Round currentRound;

    /**
     * Create a game with given parameters.
     * A game consists of a number of rounds containing a given amount of players.
     * The Game class starts new rounds and keeps track of player-score and rules
     * set for this game and its rounds as well as speaks to controller and view. 
     * @param battlefield The battlefield passed from Model which we'll play on.
     * @param numberOfRounds Number of rounds to be played.
     * @param numberOfPlayers Number of players in this game.
     */
    public Game(Battlefield battlefield, int numberOfRounds, int numberOfPlayers) {
        this.battlefield = battlefield;
        this.numberOfRounds = numberOfRounds;
        
        this.comingRounds = this.createRounds(numberOfRounds);
        this.players = this.createPlayers(numberOfPlayers);
    }
    
    /**
     * Creates a Game with a default 100x100 Battlefield, 1 round and 1 player.
     */
    public Game() {
        this(new Battlefield(), 1, 1);
    }

    /**
     * Updates the running game. Gets called each frame
     * @param tpf Time since last update
     */
    public void update(double tpf) {
        for (Player player : players) {
            player.updateUnitPosition(tpf);
        }
    }

    /**
     * Accelerates a player's unit.
     * @param id The player's ID
     * @param tpf Time since last frame
     */
    public void acceleratePlayerUnit(int id, float tpf) {
        this.players.get(id-1).accelerateUnit(tpf);
    }
    
    /**
     * Returns the size of the logical battlefield
     * @return The size as a Vector
     */
    public Vector getBattlefieldSize() {
        return battlefield.getSize();
    }
    
    /**
     * Creates a list of all the rounds this game includes
     * @param numberOfRounds Number of rounds
     */
    private List<Round> createRounds(int numberOfRounds) {
        List<Round> list = new LinkedList<Round>();

        for (int i = 0; i < numberOfRounds; i++) {
            Round round = new Round(); //TODO Maybe needs an argument?
            list.add(round);
        }
        return list;
    }

    /**
     * Creates and returns a list with the players for this game.
     * @param nOfPlayers is the amount of players we create.
     * @return Returns an ArrayList of the players.
     */
    private List<Player> createPlayers(int nOfPlayers) {
        List<Player> list = new ArrayList<Player>();

        for (int i = 0; i < nOfPlayers; i++) {
            // Initial positioning is done by battlefield, create with zeroVectors
            Vector zeroVector = new Vector(0, 0);
            Player player = new Player(i+1);
            player.setUnit(new Unit(zeroVector, zeroVector));
            list.add(player);
        }
        return list;
    }
    
    /**
     * Steers a player's unit in a set direction.
     * @param direction The direction, clockwise or anti-clockwise
     * @param playerID The player's ID
     * @param tpf Time since last update
     */
    public void steerPlayer(Direction direction, int playerID, double tpf) {
        Player player = players.get(playerID-1);
        player.steerUnit(direction, tpf);
    }
    
    public void addUnitListener(int playerID, PropertyChangeListener pl) {
        this.players.get(playerID-1).addUnitListener(pl);
    }
    
    public void removeUnitListener(int playerID, PropertyChangeListener pl) {
        this.players.get(playerID-1).removeUnitListener(pl);
    }

    /**
     * Start a new round.
     * Sets position of all players
     */
    public void startRound() {
        // TODO Insert stastics-handling for each round here in the future maybe?
        currentRound = comingRounds.remove(0);
        
        // Uncomment when we want items
        //battlefield.addItem();
    }
    
    public void placeUnit(int id, Vector vector) {
        this.players.get(id-1).getUnit().setPosition(vector);
    }

    /**
     * Call when the round ends, ie one player is last man standing or the
     * clock runs out.
     * It delivers statistics from played round, sets score to the winner
     * and clears the battlefield.
     */
    public void endRound() {
        battlefield.removeItem();
        // TODO Add score for the winner here   
    }
    
    /*
     * Returns number of players.
     */
    public int getNbrOfPlayers() {
        return this.players.size();
    }
    
    /**
     * Returns a player's unitposition.
     * @param playerID The player's ID
     * @return A Vector representing the position of a player's unit.
     */
    public Vector getPlayerPosition(int playerID) {
        return players.get(playerID -1).getUnitPosition();
    }
}
