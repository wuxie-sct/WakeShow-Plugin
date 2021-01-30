package me.wuxie.wakeshow.wakeshow.api.event;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.hudcomponent.HudComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
/**
 * 玩家点击一个HUD组件后事件
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class PlayerPostClickHudComponentEvent  extends PlayerEvent {
    @Getter
    private HudComponent component;
    @Getter private int mouseButton;
    public PlayerPostClickHudComponentEvent(Player player, HudComponent component, int mouseButton) {
        super(player);
        this.component = component;
        this.mouseButton = mouseButton;
    }

    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
