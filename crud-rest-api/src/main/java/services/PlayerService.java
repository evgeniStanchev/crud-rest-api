package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Player;

@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PlayerService {

    private final Map<String, Player> players = new ConcurrentHashMap<>();

    @PersistenceContext(unitName = "jdbc/football_pu")
    protected EntityManager em;

    @PostConstruct
    public void init() {
	for (Player p : getAllPlayersFromDB()) {
	    players.put(p.getId(), p);
	}

    }

    @TransactionAttribute
    @Schedule(second = "*/2", minute = "*", hour = "*")
    public void updateDatabase() {
	System.out.println("UPDATING DATABASE");
	final List<Player> playersDB = getAllPlayersFromDB();
	checkForCreated(playersDB);
	checkForRemovedAndUpdatedPlayers(playersDB);
	System.out.println("UPDATING DATABASE ENDS");
    }

    private void checkForCreated(final List<Player> playersDB) {
	for (Player player : this.players.values()) {
	    if (!playersDB.contains(player)) {
		em.createNativeQuery("INSERT INTO players (name, age, position) VALUES (?,?,?)")
			.setParameter(1, player.getName()).setParameter(2, player.getAge())
			.setParameter(3, player.getPosition()).executeUpdate();
	    }
	}
    }

    private void checkForRemovedAndUpdatedPlayers(final List<Player> playersDB) {
	for (Player playerDB : playersDB) {
	    final String id = playerDB.getId();
	    final Player player = players.get(id);
	    if (player == null) {
		em.createQuery("DELETE FROM Player p WHERE p.id = :id").setParameter("id", id).executeUpdate();
	    } else {
		checkForUpdated(playerDB, id, player);
	    }
	}
    }

    private void checkForUpdated(final Player playerDB, final String id, final Player player) {
	if (!player.equals(playerDB)) {
	    em.createQuery("UPDATE Player p SET p.name=:name, p.age=:age, p.position = :position WHERE p.id = :id")
		    .setParameter("id", id).setParameter("name", player.getName()).setParameter("age", player.getAge())
		    .setParameter("position", player.getPosition()).executeUpdate();
	}
    }

    @TransactionAttribute
    private void insertPlayer(final String name, final int age, final String position) {
	em.createNativeQuery("INSERT INTO players (name, age, position) VALUES (?,?,?)").setParameter(1, name)
		.setParameter(2, age).setParameter(3, position).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    private List<Player> getAllPlayersFromDB() {
	return em.createQuery("SELECT p FROM Player p").getResultList();
    }

    public List<Player> getPlayersByName(final String name) {
	final List<Player> players = new ArrayList<>();
	for (Player p : this.players.values()) {
	    if (p.getName().equals(name)) {
		players.add(p);
	    }
	}
	return players;
    }

    public List<Player> getPlayersByAge(final int age) {
	final List<Player> players = new ArrayList<>();
	for (Player p : this.players.values()) {
	    if (p.getAge() == age) {
		players.add(p);
	    }
	}
	return players;
    }

    public List<Player> getPlayersByPosition(final String position) {
	final List<Player> players = new ArrayList<>();
	for (Player p : this.players.values()) {
	    if (p.getPosition().contentEquals(position)) {
		players.add(p);
	    }
	}
	return players;
    }

    public void createPlayerInDB(final String name, final int age, final String position) {
	players.put(UUID.randomUUID().toString(), new Player(name, age, position));
    }

//    public Player updatePlayer(final int id, final String name, final int age) {
//	final Player p = players.get(id);
//	synchronized (p) {
//	    p.setName(name);
//	    p.setAge(age);
//	}
//	return players.get(id);
//    }
//
//    @TransactionAttribute
//    public Player updatePlayerPosition(final int id, final String newPosition) {
//	em.createQuery("UPDATE Player p SET p.position = :position WHERE p.id = :id").setParameter("id", id)
//		.setParameter("position", newPosition).executeUpdate();
//	return players.get(id);
//    }
//
//    @TransactionAttribute
//    public Player updatePlayerAge(final int id, final int newAge) {
//	em.createQuery("UPDATE Player p SET p.age = :age WHERE p.id = :id").setParameter("id", id)
//		.setParameter("age", newAge).executeUpdate();
//	return players.get(id);
//    }
//
//    @TransactionAttribute
//    public void removePlayer(final int id) {
//	em.createQuery("DELETE FROM Player p WHERE p.id = :id").setParameter("id", id).executeUpdate();
//    }

}
