package me.wuxie.wakeshow.wakeshow.network.server_out;

import me.wuxie.wakeshow.wakeshow.network.OutPacket;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;

public class MessageCloseGui extends OutPacket {
    public MessageCloseGui(WxScreen screen) {
        super(1);
        getPacketBuffer().writeString(screen.getId());
    }
}
