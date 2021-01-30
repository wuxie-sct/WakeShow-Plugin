package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import com.mojang.authlib.GameProfile;
import lombok.Getter;
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
 * HUD玩家模型立绘
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WPlayerDraw
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudPlayerDraw extends HudComponent {
    @Getter
    private Map<String, ItemStack> equipmentMap;
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
    private GameProfile gameProfile;
    public WHudPlayerDraw(Player owner,String id,int x, int y, Player player) {
        super(owner,id, x, y);
        equipmentMap = new HashMap<>();
        if(player!=null){
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
            if(offHand!=null&&!offHand.getType().equals(Material.AIR)){
                equipmentMap.put("OFFHAND",offHand);
            }
            if(mainHand!=null&&!mainHand.getType().equals(Material.AIR)){
                equipmentMap.put("MAINHAND",mainHand);
            }
        }
    }

    public GameProfile getGameProfile() {
        if(gameProfile==null)
            return new GameProfile(UUID.nameUUIDFromBytes("testPlayer".getBytes()),"testPlayer");
        return gameProfile;
    }

    public WHudPlayerDraw(Player owner,String id, int x, int y, GameProfile gameProfile) {
        super(owner,id, x, y);
        equipmentMap = new HashMap<>();
        this.gameProfile = gameProfile;
    }

    public WHudPlayerDraw(Player owner,String id, int x, int y, UUID uuid) {
        super(owner,id, x, y);
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

    public UUID getUUID(){
        return gameProfile!=null?gameProfile.getId():null;
    }

    public String getName(){
        return gameProfile!=null?gameProfile.getName():null;
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

    public void setGameProfile(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
        //update();
    }
}