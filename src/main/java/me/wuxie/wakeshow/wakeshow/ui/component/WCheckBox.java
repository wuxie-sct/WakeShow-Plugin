package me.wuxie.wakeshow.wakeshow.ui.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

import me.wuxie.wakeshow.wakeshow.api.event.PlayerPostClickComponentEvent;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPreClickComponentEvent;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageComponentReturnData;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.animation.ReplacementAnimation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
/**
 * 勾选框
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class WCheckBox extends Component {
    /**
     * 未选中时的图片
     */
    @Getter
    private String url1;
    /**
     * 选中时的图片
     */
    @Getter
    private String url2;
    /**
     * 选中状态 true 选中 false 未选中
     */
    @Getter
    private boolean select;
    /**
     * 选中状态下的tooltip
     */
    @Getter
    private List<String> selectTooltip;
    /** 未选中时显示在勾选框的文字 */
    @Getter
    private String name = "";
    /**
     * 选中时显示在勾选框的文字
     * */
    @Getter
    private String selectName = "";


    /* 未选中和选中时的文本的x轴偏移量 */
    @Getter
    private float offsetName = 2.0f;

    /** 未选中时的动画效果 */
    @Getter
    private ReplacementAnimation normalAnimation;
    /** 选中时的动画效果 */
    @Getter
    private ReplacementAnimation selectAnimation;

    /**
     * 勾选框构造器
     * @param parent 父容器
     * @param id ID
     * @param url1 未选中状态贴图
     * @param url2 选中状态贴图
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    public WCheckBox(Container parent, String id, String url1, String url2 , int x, int y, int w, int h) {
        this(parent,id,url1,url2,false,x,y,w,h);
    }

    /**
     * 勾选框构造器
     * @param parent 父容器
     * @param id ID
     * @param url1 未选中状态贴图
     * @param url2 选中状态贴图
     * @param select 选中状态(true选中/false未选中)
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    public WCheckBox(Container parent, String id,String url1,String url2,boolean select, int x, int y,int w,int h) {
        super(parent, id, x, y);
        this.url1 = url1;
        this.url2 = url2;
        this.select = select;
        this.w = w;
        this.h = h;
    }

    public void setNormalAnimation(ReplacementAnimation normalAnimation) {
        this.normalAnimation = normalAnimation;
        beforeUpdate();
    }

    public void setSelectAnimation(ReplacementAnimation selectAnimation) {
        this.selectAnimation = selectAnimation;
        beforeUpdate();
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
        beforeUpdate();
    }

    public void setName(String name) {
        this.name = name;
        beforeUpdate();
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
        beforeUpdate();
    }

    public void setOffsetName(float offsetName) {
        this.offsetName = offsetName;
        beforeUpdate();
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
        beforeUpdate();
    }

    public void setSelect(boolean select) {
        this.select = select;
        beforeUpdate();
    }

    public void setSelectTooltip(List<String> selectTooltip) {
        this.selectTooltip = selectTooltip;
        beforeUpdate();
    }

    @Override
    public void onClick(int mouseButtonId, Player player) {
        PlayerPreClickComponentEvent playerClickComponentEvent = new PlayerPreClickComponentEvent(player,getScreen(),this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerClickComponentEvent);
        if(playerClickComponentEvent.isCancelled()){
            return;
        }
        this.select = !select;
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("path", getPath());
        jo.addProperty("value", true);
        PacketHandler.sendToPlayer(player,new MessageComponentReturnData("WCheckBox",je));
        if(function!=null) {
            function.run(mouseButtonId, player);
        }
        PlayerPostClickComponentEvent playerPostClickComponentEvent = new PlayerPostClickComponentEvent(player,getScreen(),this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerPostClickComponentEvent);
    }
}
