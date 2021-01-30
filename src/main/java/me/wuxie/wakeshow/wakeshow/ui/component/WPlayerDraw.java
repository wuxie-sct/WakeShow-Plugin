package me.wuxie.wakeshow.wakeshow.ui.component;

import com.mojang.authlib.GameProfile;
import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * 玩家模型立绘
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WPlayerDraw extends Component {
    /**
     * @see me.wuxie.wakeshow.wakeshow.ui.component.WLivingEntityDraw
     * */
    @Getter
    private Map<String, ItemStack> equipmentMap;
    /**
     * @see me.wuxie.wakeshow.wakeshow.ui.component.WLivingEntityDraw
     * */
    @Getter
    private boolean rotate = false;
    /**
     * @see me.wuxie.wakeshow.wakeshow.ui.component.WLivingEntityDraw
     * */
    @Getter
    private double rotateSpeed = 0;
    /**
     * @see me.wuxie.wakeshow.wakeshow.ui.component.WLivingEntityDraw
     * */
    @Getter
    private double rotateCircle = 0;
    /**
     * @see me.wuxie.wakeshow.wakeshow.ui.component.WLivingEntityDraw
     * */
    @Getter
    private int scale = 30;
    private GameProfile gameProfile;
    @Getter
    private UUID uuid;
    @Getter
    private String name;

    /**
     * 1.12.2版本使用的构造器
     * @param parent 父容器
     * @param id id
     * @param player 玩家
     * @param x x
     * @param y y
     */
    public WPlayerDraw(Container parent, String id, Player player, int x, int y) {
        super(parent, id, x, y);
        equipmentMap = new HashMap<>();
        if(player!=null){
            uuid = player.getUniqueId();
            name = player.getName();
            this.gameProfile = ((CraftPlayer)player).getProfile();
            ItemStack boots =  player.getInventory().getBoots();
            ItemStack leggings = player.getInventory().getLeggings();
            ItemStack chestplate = player.getInventory().getChestplate();
            ItemStack helmet = player.getInventory().getHelmet();
            ItemStack offHand = player.getInventory().getItemInOffHand();
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if(boots!=null&&!boots.getType().equals(Material.AIR)){
                equipmentMap.put("FEET",boots);
            }
            if(leggings!=null&&!leggings.getType().equals(Material.AIR)){
                equipmentMap.put("LEGS",leggings);
            }
            if(chestplate!=null&&!chestplate.getType().equals(Material.AIR)){
                equipmentMap.put("CHEST",chestplate);
            }
            if(helmet!=null&&!helmet.getType().equals(Material.AIR)){
                equipmentMap.put("HEAD",helmet);
            }
            if (offHand != null && !offHand.getType().equals(Material.AIR)) {
                equipmentMap.put("OFFHAND", offHand);
            }
            if(mainHand!=null&&!mainHand.getType().equals(Material.AIR)){
                equipmentMap.put("MAINHAND",mainHand);
            }
        }
    }

    public GameProfile getGameProfile() {
        if(gameProfile==null) {
            return new GameProfile(UUID.nameUUIDFromBytes("testPlayer".getBytes()),"");
        }
        return gameProfile;
    }

    /**
     *  其它版本使用的构造器
     *  弃用
     * @param parent 父容器
     * @param id ID
     * @param gameProfile craftPlayer.getGameProfile
     * @param player 玩家
     * @param version 版本号 如1.8就是8
     * @param x x
     * @param y y
     */
    @Deprecated
    public WPlayerDraw(Container parent, String id, GameProfile gameProfile,Player player,int version, int x, int y) {
        super(parent, id, x, y);
        equipmentMap = new HashMap<>();
        this.gameProfile = gameProfile;
        if(player!=null){
            uuid = player.getUniqueId();
            name = player.getName();
            ItemStack boots =  player.getInventory().getBoots();
            ItemStack leggings = player.getInventory().getLeggings();
            ItemStack chestplate = player.getInventory().getChestplate();
            ItemStack helmet = player.getInventory().getHelmet();
            ItemStack offHand = null;
            if(version>=9) {
                offHand = player.getInventory().getItemInOffHand();
            }
            ItemStack mainHand = player.getInventory().getItemInMainHand();
            if(boots!=null&&!boots.getType().equals(Material.AIR)){
                equipmentMap.put("FEET",boots);
            }
            if(leggings!=null&&!leggings.getType().equals(Material.AIR)){
                equipmentMap.put("LEGS",leggings);
            }
            if(chestplate!=null&&!chestplate.getType().equals(Material.AIR)){
                equipmentMap.put("CHEST",chestplate);
            }
            if(helmet!=null&&!helmet.getType().equals(Material.AIR)){
                equipmentMap.put("HEAD",helmet);
            }
            if (offHand != null && !offHand.getType().equals(Material.AIR)) {
                equipmentMap.put("OFFHAND", offHand);
            }
            if(mainHand!=null&&!mainHand.getType().equals(Material.AIR)){
                equipmentMap.put("MAINHAND",mainHand);
            }
        }
    }

    public WPlayerDraw(Container parent, String id, UUID uuid, int x, int y) {
        super(parent, id, x, y);
        equipmentMap = new HashMap<>();
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer!=null) {
            this.gameProfile = ((CraftPlayer)offlinePlayer).getProfile();
            ItemStack boots = ((CraftPlayer) offlinePlayer).getInventory().getBoots();
            ItemStack leggings = ((CraftPlayer) offlinePlayer).getInventory().getLeggings();
            ItemStack chestplate = ((CraftPlayer) offlinePlayer).getInventory().getChestplate();
            ItemStack helmet = ((CraftPlayer) offlinePlayer).getInventory().getHelmet();
            ItemStack offHand = ((CraftPlayer) offlinePlayer).getInventory().getItemInOffHand();
            ItemStack mainHand = ((CraftPlayer) offlinePlayer).getInventory().getItemInMainHand();
            if(boots!=null&&!boots.getType().equals(Material.AIR)){
                equipmentMap.put("FEET",boots);
            }
            if(leggings!=null&&!leggings.getType().equals(Material.AIR)){
                equipmentMap.put("LEGS",leggings);
            }
            if(chestplate!=null&&!chestplate.getType().equals(Material.AIR)){
                equipmentMap.put("CHEST",chestplate);
            }
            if(helmet!=null&&!helmet.getType().equals(Material.AIR)){
                equipmentMap.put("HEAD",helmet);
            }
            if(offHand!=null&&!offHand.getType().equals(Material.AIR)){
                equipmentMap.put("OFFHAND",offHand);
            }
            if(mainHand!=null&&!mainHand.getType().equals(Material.AIR)){
                equipmentMap.put("MAINHAND",mainHand);
            }
        }
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

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
        beforeUpdate();
    }
}
