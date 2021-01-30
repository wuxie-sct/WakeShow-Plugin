package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPostClickHudComponentEvent;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPreClickHudComponentEvent;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.hud.MessageAddHudComponent;
import me.wuxie.wakeshow.wakeshow.ui.ClickFunction;
import me.wuxie.wakeshow.wakeshow.util.JsonUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
/**
 * HUD组件抽象类
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public abstract class HudComponent {
    /**
     * 该组件对应的玩家
     */
    @Getter
    private Player player;
    /**
     * 该组件的唯一ID
     */
    @Getter
    private String id;
    /**
     * x 坐标
     * -1 为居中
     * 正数从左往右数
     * 负数从右往左
     */
    @Getter protected int x;
    /**
     * y 坐标
     * -1 为居中
     * 正数从上往下数
     * 负数从下往上
     */
    @Getter protected int y;
    /**
     * 宽度
     */
    @Getter protected int w;
    /**
     * 高度
     */
    @Getter protected int h;
    /**
     * z 轴
     * 优先级排序
     * 越大越在前面
     * 默认值100
     */
    @Getter protected int z = 100;
    /**
     * 点击这个组件后执行的语句
     */
    @Getter
    @Setter
    protected ClickFunction function = null;
    /**
     * 悬停文本
     */
    @Getter
    private List<String> tooltips = null;
    public HudComponent(Player player,String id, int x, int y){
        this.player = player;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public HudComponent(Player player,String id, int x, int y, int w, int h){
        this(player,id,x,y);
        this.w = w;
        this.h = h;
    }

    /**
     * 修改属性后调用
     * 可在玩家屏幕更新
     * 建议统一修改，单次调用
     * 就是如果有多处修改，修改完，只需要调用一次
     */
    public void update(){
        PacketHandler.sendToPlayer(player,new MessageAddHudComponent(JsonUtil.hudComponentToJson(this)));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
        //update();
    }
    public void setZ(int z) {
        this.z = z;
        //update();
    }
    public void setTooltips(List<String> tooltips) {
        this.tooltips = tooltips;
        //update();
    }
    /**
     * 当被点击时
     * @param mouseButtonId 0 左键 1 右键 2 中键
     * @param player 点击的玩家
     */
    public void onClick(int mouseButtonId, Player player){
        PlayerPreClickHudComponentEvent playerClickComponentEvent = new PlayerPreClickHudComponentEvent(player,this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerClickComponentEvent);
        if(playerClickComponentEvent.isCancelled())return;
        if(function!=null) {
            function.run(mouseButtonId, player);
        }
        PlayerPostClickHudComponentEvent postClickHudComponentEvent = new PlayerPostClickHudComponentEvent(player,this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(postClickHudComponentEvent);
    }
}
