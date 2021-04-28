package fr.milekat.test_box.TraderAPI.events;

import fr.milekat.test_box.TraderAPI.classes.TraderInventory;
import fr.milekat.test_box.TraderAPI.classes.TraderTrade;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TradeComplete extends Event implements Cancellable {
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	private final TraderInventory inventory;
	private final Player player;
	private final TraderTrade trade;
	private final Integer count;
	private boolean isCancelled;

	public TradeComplete(TraderInventory inventory, Player player, TraderTrade trade, Integer count) {
		this.inventory = inventory;
		this.player = player;
		this.trade = trade;
		this.count = count;
	}

	public TraderInventory getInventory() {
		return inventory;
	}

	public Player getPlayer() {
		return player;
	}

	public TraderTrade getTrade() {
		return trade;
	}

	public Integer getCount() {
		return count;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
	}
}
