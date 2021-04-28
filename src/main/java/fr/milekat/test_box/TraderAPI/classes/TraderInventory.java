package fr.milekat.test_box.TraderAPI.classes;

import org.bukkit.entity.Player;

import java.util.List;

public class TraderInventory {
	private String name;
	private Player forWho;
	private List<TraderTrade> trades;
	private int xp;

	public TraderInventory(String name, Player forWho, List<TraderTrade> trades, int xp) {
		this.name = name;
		this.trades = trades;
		this.forWho = forWho;
		this.xp = xp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getForWho() {
		return forWho;
	}

	public void setForWho(Player forWho) {
		this.forWho = forWho;
	}

	public List<TraderTrade> getTrades() {
		return trades;
	}

	public void setTrades(List<TraderTrade> trades) {
		this.trades = trades;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}
}
