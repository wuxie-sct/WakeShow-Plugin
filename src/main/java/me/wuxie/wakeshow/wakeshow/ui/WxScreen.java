package me.wuxie.wakeshow.wakeshow.ui;

import lombok.Getter;
import lombok.Setter;

import me.wuxie.wakeshow.wakeshow.ui.animation.ScreenAnimation;
import me.wuxie.wakeshow.wakeshow.ui.component.WSlot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * GGGGGG！
 * UUUUUU！
 * IIIIII！
 */
public class WxScreen implements ContainerOwner {
    /**
     * 是否是打开状态
     */
    @Getter
    boolean opened = false;
    /**
     * 打开这个gui的玩家
     */
    @Getter
    Player player;
    /**
     * 坐标 大小
     */
    @Getter
    @Setter
    private int x,y,w,h;
    /**
     * ID 背景
     */
    @Getter
    private String id,background;
    /**
     * 组件容器
     */
    @Getter
    private Container container;
    /**
     * 鼠标上的物品
     */
    @Getter
    @Setter
    private ItemStack cursor = null;
    /** 将要更新的槽位组件 */
    @Getter
    private Set<WSlot> updateSlots;
    /**
     * 该界面中最大的z轴组件的z值
     */
    @Getter
    @Setter
    private int maxZ = 100;
    /**
     * 打开动画
     */
    @Getter
    @Setter
    private ScreenAnimation animation = new ScreenAnimation();

    /**
     * 构造器
     * @param id ID
     * @param background 背景
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    public WxScreen(String id,String background,int x,int y,int w,int h){
        container = new Container(this);
        updateSlots = new HashSet<>();
        this.id = id;
        this.background = background;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public WxScreen getScreen() {
        return this;
    }

    @Override
    public String getPath() {
        return getId();
    }

    /*
     * 叠加式打开
     * 如果当前子界面没有关闭，则为当前子界面打开下一层子界面
     * @param subScreen 子界面
     */
    /*public void openSubScreen(WChainSubScreen subScreen){
        if(!opened)return;
        if(this.subScreen!=null){
            if(this.subScreen.getNextSubScreen()==null){
                this.subScreen.openSubScreen(subScreen);
            }else {
                WChainSubScreen subScreen1 = this.subScreen.getNextSubScreen();
                while (true){
                    // 当子界面没有下一个子界面时，为当前子界面打开子界面
                    if(subScreen1.getNextSubScreen()==null){
                        break;
                    }else {
                        subScreen1 = subScreen1.getNextSubScreen();
                    }
                }
                subScreen1.openSubScreen(subScreen);
            }
        } else {
            this.subScreen = subScreen;
            subScreen.parent = this;
            PlayerOpenSubScreenEvent playerOpenSubScreenEvent = new PlayerOpenSubScreenEvent(player,subScreen);
            Bukkit.getPluginManager().callEvent(playerOpenSubScreenEvent);
            if(!playerOpenSubScreenEvent.isCancelled()) {
                subScreen = playerOpenSubScreenEvent.getSubScreen();
                // 发包
                MessageOpenSubScreen messageOpenSubScreen = new MessageOpenSubScreen(subScreen);
                PacketHandler.sendToPlayer(getScreen().player, messageOpenSubScreen);
                // 触发事件
            } else {
                this.subScreen = null;
                subScreen.parent = null;
            }
        }
    }*/

    /**
     * 关闭的子界面必须是当前 WxScreen 子界面层链中的某一个子界面
     * @param subScreen
     */
    /*public void closeSubScreen(WChainSubScreen subScreen) {
        if(this.subScreen!=null && subScreen!=null){
            if(subScreen.getScreen().equals(this)){
                if(subScreen.parent instanceof WxScreen){
                    this.subScreen = null;
                    // 发包
                    MessageCloseSubScreen messageCloseSubScreen =  new MessageCloseSubScreen(getPath());
                    PacketHandler.sendToPlayer(getScreen().player,messageCloseSubScreen);
                    // 触发事件
                    PlayerCloseSubScreenEvent playerCloseSubScreenEvent = new PlayerCloseSubScreenEvent(player,subScreen);
                    Bukkit.getPluginManager().callEvent(playerCloseSubScreenEvent);
                }else {
                    // 获得这个子界面的上一层，然后关闭子界面
                    ((WChainSubScreen)subScreen.parent).closeSubScreen();
                }
            }
        }
    }*/
}
