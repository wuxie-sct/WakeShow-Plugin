package me.wuxie.wakeshow.wakeshow.network.server_out;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.network.OutPacket;
import me.wuxie.wakeshow.wakeshow.network.PacketBuffer;
import me.wuxie.wakeshow.wakeshow.ui.WInventoryScreen;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.util.JsonUtil;

public class MessageOpenGui extends OutPacket {
    @Getter
    private WxScreen screen;
    public MessageOpenGui(WxScreen screen) {
        super(0);
        this.screen = screen;
        PacketBuffer buffer = getPacketBuffer();
        if(screen.getAnimation()!=null&&screen.getAnimation().animationFrame>0){
            // 帧数
            buffer.writeInt(screen.getAnimation().animationFrame);
            // 角度
            buffer.writeFloat(screen.getAnimation().rotate);
            buffer.writeInt(screen.getAnimation().rotateTo);
            // 透明
            buffer.writeFloat(screen.getAnimation().alpha);
            buffer.writeFloat(screen.getAnimation().alphaTo);
            // 大小
            buffer.writeFloat(screen.getAnimation().scale);
        } else buffer.writeInt(0);
        buffer.writeString(JsonUtil.screenToJson(screen));
        // 清除槽位调整的类，节省之后的update传输字节
        if(screen instanceof WInventoryScreen){
            ((WInventoryScreen) screen).getResetSlots().clear();
        }
    }
}
