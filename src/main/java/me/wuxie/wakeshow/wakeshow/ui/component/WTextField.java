package me.wuxie.wakeshow.wakeshow.ui.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageComponentReturnData;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.ui.Container;
/**
 * 输入框
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WTextField extends Component {
    /**
     * 框内文字
     */
    @Getter
    private String text = "";
    /**
     * 背景
     */
    @Getter
    private String background;

    /**
     * 构造器
     * @param parent 父容器
     * @param id ID
     * @param x x
     * @param y y
     * @param w w
     */
    public WTextField(Container parent, String id, int x, int y,int w) {
        super(parent, id, x, y);
        this.w = w;
    }

    public void setBackground(String background) {
        this.background = background;
        beforeUpdate();
    }

    /**
     *
     * @param text 文本
     * @param input 是否是客户端输入(服务器set需要使用false)
     */
    public void setText(String text,boolean input){
        text=text==null?"":text;
        this.text = text;
        WxScreen screen = getScreen();
        if(!input) {
            if (screen != null && screen.isOpened() && screen.getPlayer() != null) {
                JsonElement je = new JsonObject();
                JsonObject jo = je.getAsJsonObject();
                jo.addProperty("path", getPath());
                jo.addProperty("value", text);
                PacketHandler.sendToPlayer(screen.getPlayer(), new MessageComponentReturnData("WTextField", je));
            }
        }
    }
}
