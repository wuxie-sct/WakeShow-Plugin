package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
/**
 * HUD血条
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WHealth
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudHealth extends HudComponent {
    @Getter
    @Setter
    private LivingEntity livingEntity;
    @Getter
    @Setter
    private String texture;
    @Getter
    @Setter
    private String cover;
    @Getter @Setter private boolean vertical = false;
    public WHudHealth(Player player, String id, int x, int y, int w, int h, String texture, String cover, LivingEntity livingEntity) {
        super(player, id, x, y, w, h);
        this.livingEntity = livingEntity;
        this.texture = texture;
        this.cover = cover;
    }
}
