package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPostClickHudComponentEvent;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPreClickHudComponentEvent;
import me.wuxie.wakeshow.wakeshow.ui.OpenedGui;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
/**
 * HUD槽位
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WSlot
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudSlot extends HudComponent {
    // 是否可以拖拽
    @Getter
    boolean canDrag = false;
    @Getter
    private ItemStack itemStack;
    @Getter
    private float scale = 1.0f;
    @Getter
    private String background = null;
    @Getter
    private List<String> emptyTooltips = null;

    public WHudSlot(Player owner,String id, int x, int y, ItemStack itemStack) {
        super(owner,id, x, y);
        this.itemStack = itemStack;
    }
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        //update();
    }

    public void setBackground(String background) {
        this.background = background;
        //update();
    }

    public void setEmptyTooltips(List<String> emptyTooltips) {
        this.emptyTooltips = emptyTooltips;
        //update();
    }

    public void setScale(float scale) {
        this.scale = scale;
        //update();
    }

    public void setCanDrag(boolean canDrag){
        this.canDrag = canDrag;
        //update();
    }

    // 还原
    protected void backClick(Player player){
        setItemStack(itemStack);
        OpenedGui openedGui = WuxieAPI.getOpenedGui(player);
        if(openedGui!=null){
            WxScreen screen = openedGui.getScreen();
            screen.setCursor(screen.getCursor());
            // 处理完，更新客户端
            WuxieAPI.getOpenedGui(player).update();
        }else {
            player.setItemOnCursor(player.getItemOnCursor());
        }
        update();
    }

    // 处理槽位点击
    @Override
    public void onClick(int mouseButtonId, Player player) {
        PlayerPreClickHudComponentEvent playerClickHudComponentEvent =
                new PlayerPreClickHudComponentEvent(player,this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerClickHudComponentEvent);
        // 如果事件被取消
        if(playerClickHudComponentEvent.isCancelled()){
            // 如果槽位可拖动物品
            if(canDrag){
                // 还原客户端槽位的点击
                backClick(player);
            }
            return;
        }
        if(function!=null) function.run(mouseButtonId, player);
        OpenedGui openedGui = WuxieAPI.getOpenedGui(player);
        WxScreen screen = null;
        if(openedGui!=null){
            screen = openedGui.getScreen();
        }
        // 克隆光标和槽位
        ItemStack cursor = screen!=null?screen.getCursor():player.getItemOnCursor();
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
                            if(screen!=null) {
                                screen.setCursor(itemStack);
                            }else player.setItemOnCursor(itemStack);
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
                                if(screen!=null) {
                                    screen.setCursor(cursor);
                                }else player.setItemOnCursor(cursor);
                            }else {
                                itemStack.setAmount(all);
                                this.itemStack = itemStack;
                                if(screen!=null) {
                                    screen.setCursor(null);
                                }else player.setItemOnCursor(null);
                            }
                        }
                        // 光标没物品
                    }else {
                        if(screen!=null) {
                            screen.setCursor(itemStack);
                        }else player.setItemOnCursor(itemStack);
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
                                    if(cursorAmount>1) cursor.setAmount(cursorAmount - 1);
                                    else cursor = null;
                                    if(screen!=null) {
                                        screen.setCursor(cursor);
                                    }else player.setItemOnCursor(cursor);
                                }
                                // 否则交换
                            }else {
                                if(screen!=null) {
                                    screen.setCursor(itemStack);
                                }else player.setItemOnCursor(itemStack);
                                this.itemStack = cursor;
                            }
                            // 如果槽位没有物品,光标减一，槽位加一光标物品
                        }else {
                            int cursorAmount = cursor.getAmount();
                            if(cursorAmount>1) {
                                cursor.setAmount(cursorAmount - 1);
                                if(screen!=null) {
                                    screen.setCursor(cursor.clone());
                                }else player.setItemOnCursor(cursor.clone());
                                cursor.setAmount(1);
                                this.itemStack = cursor;
                            // 如果光标只有一个物品
                            }else {
                                this.itemStack = cursor;
                                if(screen!=null) {
                                    screen.setCursor(null);
                                }else player.setItemOnCursor(null);
                            }
                        }
                        // 如果光标没有物品，槽位物品减半，光标物品得半
                    } else if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)){
                        int slotAmount = itemStack.getAmount();
                        int half = (int)Math.round(slotAmount/2.0);
                        itemStack.setAmount(half);
                        if(screen!=null) {
                            screen.setCursor(itemStack.clone());
                        }else player.setItemOnCursor(itemStack.clone());
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
                            if(screen!=null) {
                                screen.setCursor(itemStack);
                                // 处理完，更新客户端
                                openedGui.update();
                            }else player.setItemOnCursor(itemStack);
                        }
                    }
                }
                break;
            }
            //System.out.println("slot:"+(this.itemStack==null?0:this.itemStack.getAmount()));
            //System.out.println("cursor:"+(getScreen().getCursor()==null?0:getScreen().getCursor().getAmount()));
        }
        PlayerPostClickHudComponentEvent postClickHudComponentEvent = new PlayerPostClickHudComponentEvent(player,this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(postClickHudComponentEvent);
    }
}