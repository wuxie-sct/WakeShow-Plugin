package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 生物模型立绘
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WLivingEntityDraw extends Component {
    /**
     * 显示的装备
     * 键值为
     * mainhand 主手
     * offhand  副手
     * feet 脚
     * legs 腿
     * chest 护甲
     * head 头
     * 如果是已有生物，不需要绑定装备map也能有装备
     * 如果是虚构生物(未在世界生成)，需要显示装备则需要添加
     *  */
    @Getter
    private Map<String, ItemStack> equipmentMap;
    /** 生物名 */
    @Getter
    private String name = null;

    @Getter
    @Setter
    /** 自定义UUID */
    private UUID customUnicodeId;

    /** 生物类型
     *       "zombie","wither","dragon", "blaze","cavespider","creeper",
     *       "elderguardian","enderman","evoker", "ghast","giantzombie","guardian",
     *       "husk","illusionillager","irongolem", "pigzombie","magmacube","polarbear",
     *       "shulker","silverfish","skeleton", "slime","snowman","spider",
     *       "stray","vex","vindicator", "witch","witherskeleton","zombievillager",
     *       "bat","chicken","cow", "donkey","horse","llama",
     *       "mooshroom","mule","ocelot", "parrot","pig","rabbit",
     *       "sheep","skeletonhorse","squid", "villager","wolf",
     *       "zombiehorse","babyzombie","babypigzombie","babyzombievillager"
     *  */
    @Getter
    private String entityType;
    /**
     * 生物是否自动旋转
     */
    @Getter
    private boolean rotate = false;
    /** 旋转速度 */
    @Getter
    private double rotateSpeed = 0;
    /** 旋转多少圈倒回来 */
    @Getter
    private double rotateCircle = 0;
    /** 大小 */
    @Getter
    private int scale = 30;
    /** 生物ID */
    @Getter
    private int entityId = -1;
    /** 生物 */
    @Getter
    private LivingEntity livingEntity = null;

    /**
     * 虚构生物
     * @param parent 父容器
     * @param id id
     * @param entityType 生物类型
     * @param x x
     * @param y y
     */
    public WLivingEntityDraw(Container parent, String id,String entityType, int x, int y) {
        super(parent, id, x, y);
        this.entityType = entityType;
        equipmentMap = new HashMap<>();
    }

    /**
     * 真实生物
     * @param parent 父容器
     * @param id id
     * @param livingEntity 生物
     * @param x x
     * @param y y
     */
    public WLivingEntityDraw(Container parent, String id, LivingEntity livingEntity, int x, int y) {
        super(parent, id, x, y);
        equipmentMap = new HashMap<>();
        this.livingEntity = livingEntity;
        this.entityId = livingEntity.getEntityId();
    }

    public void setName(String name) {
        this.name = name;
        beforeUpdate();
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
        beforeUpdate();
    }

    public void setEntity(LivingEntity livingEntity) {
        this.entityId = livingEntity.getEntityId();
        this.livingEntity = livingEntity;
        name = null;
        beforeUpdate();
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
        beforeUpdate();
    }

    public void setRotateSpeed(double rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
        beforeUpdate();
    }

    public void setRotateCircle(double rotateCircle) {
        this.rotateCircle = rotateCircle;
        beforeUpdate();
    }

    public void setScale(int scale) {
        this.scale = scale;
        beforeUpdate();
    }
}
