package me.wuxie.wakeshow.wakeshow.ui;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.inventory.InvSlotProxyScreen;
import me.wuxie.wakeshow.wakeshow.ui.inventory.ResetInventorySlot;

import java.util.ArrayList;
import java.util.List;

/**
 * 有玩家背包槽位的界面
 */
public class WInventoryScreen extends WxScreen implements InvSlotProxyScreen {
    /**
     * 修改原版槽位
     */
    @Getter
    private List<ResetInventorySlot> resetSlots;
    /**
     * 原版槽位的x坐标
     */
    @Getter private int slotLeft;
    /**
     * 原版槽位的y坐标
     */
    @Getter private int slotTop;

    /**
     * 构造器
     * @param id ID
     * @param background 背景
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     * @param slotLeft 原版槽位的x坐标
     * @param slotTop 原版槽位的y坐标
     */
    public WInventoryScreen(String id, String background, int x, int y, int w, int h,int slotLeft,int slotTop) {
        super(id, background, x, y, w, h);
        this.slotLeft = slotLeft;
        this.slotTop = slotTop;
        this.resetSlots = new ArrayList<>();
    }
}
