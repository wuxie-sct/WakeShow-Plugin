package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPostClickComponentEvent;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPreClickComponentEvent;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.WInventoryScreen;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.inventory.InvSlotProxyScreen;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
/**
 * 槽位
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WSlot extends Component {
    /** 这个槽位的物品是否可以拖拽  */
    @Getter
    boolean canDrag = false;
    /**
     * 物品
     */
    @Getter
    private ItemStack itemStack;
    /**
     * 槽位大小
     * 原版是16
     * 例: 1.5 则为16*1.5 = 24
     */
    @Getter
    private float scale = 1.0f;
    /**
     * 空槽位背景图(仿原版装备栏槽位)
     */
    @Getter
    private String background = null;
    /**
     * 空槽位显示的tip
     */
    @Getter
    private List<String> emptyTooltips = null;

    /**
     * 构造器
     * @param parent 父容器
     * @param id id
     * @param itemStack 物品
     * @param x x
     * @param y y
     */
    public WSlot(Container parent, String id, ItemStack itemStack, int x, int y) {
        super(parent, id, x, y);
        this.itemStack = itemStack;
    }
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        beforeUpdate();
    }

    public void setBackground(String background) {
        this.background = background;
        beforeUpdate();
    }

    public void setEmptyTooltips(List<String> emptyTooltips) {
        this.emptyTooltips = emptyTooltips;
        beforeUpdate();
    }

    public void setScale(float scale) {
        this.scale = scale;
        beforeUpdate();
    }

    public void setCanDrag(boolean canDrag){
        this.canDrag = canDrag;
        beforeUpdate();
    }

    @Override
    protected void afterUpdate(){
        if(!getUpdateType().equals(UpdateType.REMOVE)) {
            setUpdateType(UpdateType.NORMAL);
        }
    }

    // 还原
    protected void backClick(Player player){
        setItemStack(itemStack);
        //getScreen().setCursor(getScreen().getCursor());
        //if (!(getScreen() instanceof InvSlotProxyScreen) || getScreen() instanceof WInventoryScreen) {
            getScreen().setCursor(getScreen().getCursor());
        //} /*else {
            //Bukkit.getScheduler().runTaskLater(WakeShow.getPlugin(), player::updateInventory, 1);
        //}*/
        // 处理完，更新客户端
        WuxieAPI.getOpenedGui(player).update();
    }

    @Override
    protected void beforeUpdate() {
        //super.beforeUpdate();
        getScreen().getUpdateSlots().add(this);
    }

    // 处理槽位点击

    /**
     * 内部使用
     * 一般情况禁止调用该方法
     * 除非你真的知道你在干嘛
     * @param mouseButtonId 0 左键 1 右键 2 中键
     * @param player 点击的玩家
     */
    @Override
    public void onClick(int mouseButtonId, Player player) {
        PlayerPreClickComponentEvent playerClickComponentEvent =
                new PlayerPreClickComponentEvent(player,getScreen(),this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerClickComponentEvent);
        // 如果事件被取消
        if(playerClickComponentEvent.isCancelled()){
            // 如果槽位可拖动物品
            if(canDrag){
                // 还原客户端槽位的点击
                backClick(player);
            }
            return;
        }
        if(function!=null) {
            function.run(mouseButtonId, player);
        }
        WxScreen screen = getScreen();
        // 克隆光标和槽位
        ItemStack cursor = screen.getCursor();
        cursor=cursor!=null?cursor.clone():null;
        ItemStack itemStack = this.itemStack;
        itemStack=itemStack!=null?itemStack.clone():null;
        if(canDrag){
            switch (mouseButtonId){
                case 0: {
                    // 光标有物品
                    if(cursor!=null&&!cursor.getType().equals(Material.AIR)){
                        // 物品不同，交换
                        if(!cursor.isSimilar(itemStack)) {
                            screen.setCursor(itemStack);
                            this.itemStack = cursor;
                            //物品相同，合并
                        }else {
                            int cursorAmount = cursor.getAmount();
                            // isSimilar物品为空返回false
                            assert itemStack != null;
                            int slotAmount = itemStack.getAmount();
                            int all = cursorAmount+slotAmount;
                            int maxSize = itemStack.getMaxStackSize();
                            if(all>maxSize){
                                itemStack.setAmount(maxSize);
                                this.itemStack = itemStack;
                                cursor.setAmount(all-maxSize);
                                screen.setCursor(cursor);
                            }else {
                                itemStack.setAmount(all);
                                this.itemStack = itemStack;
                                screen.setCursor(null);
                            }
                        }
                        // 光标没物品
                    }else {
                        screen.setCursor(itemStack);
                        this.itemStack = null;
                    }
                }
                break;
                // 右键
                case 1:{
                    // 如果光标有物品
                    if(cursor!=null&&!cursor.getType().equals(Material.AIR)){
                        // 如果槽位有物品
                        if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)){
                            // 如果两个相同,光标减一，槽位加一
                            if(itemStack.isSimilar(cursor)){
                                int cursorAmount = cursor.getAmount();
                                int slotAmount = itemStack.getAmount();
                                int maxSize = itemStack.getMaxStackSize();
                                if(slotAmount+1<maxSize){
                                    itemStack.setAmount(slotAmount+1);
                                    this.itemStack = itemStack;
                                    if(cursorAmount>1) {
                                        cursor.setAmount(cursorAmount - 1);
                                    } else {
                                        cursor = null;
                                    }
                                    screen.setCursor(cursor);
                                }
                                // 否则交换
                            }else {
                                screen.setCursor(itemStack);
                                this.itemStack = cursor;
                            }
                            // 如果槽位没有物品,光标减一，槽位加一光标物品
                        }else {
                            int cursorAmount = cursor.getAmount();
                            if(cursorAmount>1) {
                                cursor.setAmount(cursorAmount - 1);
                                screen.setCursor(cursor.clone());
                                cursor.setAmount(1);
                                this.itemStack = cursor;
                            // 如果光标只有一个物品
                            }else {
                                this.itemStack = cursor;
                                screen.setCursor(null);
                            }
                        }
                        // 如果光标没有物品，槽位物品减半，光标物品得半
                    } else if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)){
                        int slotAmount = itemStack.getAmount();
                        int half = (int)Math.round(slotAmount/2.0);
                        itemStack.setAmount(half);
                        screen.setCursor(itemStack.clone());
                        itemStack.setAmount(slotAmount-half);
                        this.itemStack = itemStack;
                    }
                }
                break;
                // 鼠标中键,不在客户端预处理// 克隆一组
                case 2:{
                    if(player.isOp()){
                        if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)) {
                            itemStack.setAmount(itemStack.getMaxStackSize());
                            getScreen().setCursor(itemStack);
                            // 处理完，更新客户端
                            WuxieAPI.getOpenedGui(player).update();
                        }
                    }
                }
                break;
                default:
                    throw new IllegalStateException("Unexpected value: " + mouseButtonId);
            }
        }
        PlayerPostClickComponentEvent playerPostClickComponentEvent = new PlayerPostClickComponentEvent(player,getScreen(),this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerPostClickComponentEvent);
    }
}