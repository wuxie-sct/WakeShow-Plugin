package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
/**
 * HUD生物立绘
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WLivingEntityDraw
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudLivingEntityDraw extends HudComponent {
    @Getter
    private Map<String, ItemStack> equipmentMap;
    @Getter
    private String name = null;
    //@Getter
    //@Setter
    //private UUID uuid = null;
    @Getter
    private String entityType;
    @Getter
    private boolean rotate = false;
    // 旋转速度
    // 是否旋转
    @Getter
    private double rotateSpeed = 0;
    // 旋转圈数回折
    @Getter
    private double rotateCircle = 0;
    @Getter
    private int scale = 30;
    @Getter
    private int entityId = -1;
    @Getter
    private LivingEntity livingEntity = null;
    public WHudLivingEntityDraw(Player owner, String id, int x, int y, String entityType) {
        super(owner,id, x, y);
        this.entityType = entityType;
        equipmentMap = new HashMap<>();
    }
    public WHudLivingEntityDraw(Player owner,String id, LivingEntity livingEntity, int x, int y) {
        super(owner,id, x, y);
        equipmentMap = new HashMap<>();
        this.livingEntity = livingEntity;
        this.entityId = livingEntity.getEntityId();
    }

    public void setName(String name) {
        this.name = name;
        //update();
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
        //update();
    }

    public void setEntity(LivingEntity livingEntity) {
        this.entityId = livingEntity.getEntityId();
        this.livingEntity = livingEntity;
        name = null;
        //update();
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
        //update();
    }

    public void setRotateSpeed(double rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
        //update();
    }

    public void setRotateCircle(double rotateCircle) {
        this.rotateCircle = rotateCircle;
        //update();
    }

    public void setScale(int scale) {
        this.scale = scale;
        //update();
    }
}