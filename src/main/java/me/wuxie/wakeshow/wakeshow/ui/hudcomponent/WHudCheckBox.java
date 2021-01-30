package me.wuxie.wakeshow.wakeshow.ui.hudcomponent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPostClickHudComponentEvent;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPreClickHudComponentEvent;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageComponentReturnData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
/**
 * HUD勾选框
 * @see me.wuxie.wakeshow.wakeshow.ui.component.WCheckBox
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WHudCheckBox extends HudComponent {
    @Getter
    private String url1, url2;
    @Getter
    private boolean select;
    @Getter
    private List<String> selectTooltip;
    @Getter
    // 未选中和选中时的文本
    private String name = "",selectName = "";

    @Getter
    /* 未选中和选中时的文本的x轴偏移量*/
    private float offsetName = 2.0f;

    public WHudCheckBox(Player owner,String id, int x, int y, int w, int h,String url1, String url2,boolean select) {
        super(owner,id, x, y, w, h);
        this.url1 = url1;
        this.url2 = url2;
        this.select = select;
    }
    public WHudCheckBox(Player owner, String id, int x, int y, int w, int h, String url1, String url2) {
        this(owner,id,x,y,w,h,url1,url2,false);
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
        //update();
    }

    public void setName(String name) {
        this.name = name;
        //update();
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
        //update();
    }

    public void setOffsetName(float offsetName) {
        this.offsetName = offsetName;
        //update();
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
        //update();
    }

    public void setSelect(boolean select) {
        this.select = select;
        //update();
    }

    public void setSelectTooltip(List<String> selectTooltip) {
        this.selectTooltip = selectTooltip;
        //update();
    }

    @Override
    public void onClick(int mouseButtonId, Player player) {
        PlayerPreClickHudComponentEvent playerClickComponentEvent = new PlayerPreClickHudComponentEvent(player,this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerClickComponentEvent);
        if(playerClickComponentEvent.isCancelled()){
            return;
        }
        this.select = !select;
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("id", this.getId());
        jo.addProperty("value", true);
        PacketHandler.sendToPlayer(player,new MessageComponentReturnData("WHudCheckBox",je));
        if(function!=null) {
            function.run(mouseButtonId, player);
        }
        PlayerPostClickHudComponentEvent postClickHudComponentEvent = new PlayerPostClickHudComponentEvent(player,this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(postClickHudComponentEvent);
    }

}
