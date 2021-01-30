package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.ui.*;
import org.bukkit.entity.Player;


public class MessageClickComponent extends InPacket {
    public MessageClickComponent(ByteBuf buf) {
        super(0,buf);
    }

    @Override
    public void handlePacket(Player player) {
        int mouseButtonId = getPacketBuffer().readByte();
        String componentPath = getPacketBuffer().readString();
        //System.out.println(componentPath);
        String[] path = componentPath.split("\\.");
        OpenedGui openedGui = WuxieAPI.getOpenedGui(player);
        if(openedGui!=null){
            WxScreen screen = openedGui.getScreen();
            if(screen.getId().equals(path[0])) {
                ContainerOwner owner = screen;
                Component clickedComponent = null;
                for (int a = 1;a<path.length;){
                    String pathId = path[a];
                    Component component = owner.getContainer().getComponent(pathId);
                    if(component!=null) {
                        if (component instanceof ContainerOwner) {
                            owner = (ContainerOwner) component;
                        } else if(a+1==path.length){
                            clickedComponent = component;
                        } else throw new ClassCastException("clicked component parent cannot cast ContainerOwner!  path:"+componentPath+" in:["+a+"]["+path[a]+"]");
                    } else throw new NullPointerException("not find clicked component! path:"+componentPath+" in:["+a+"]["+path[a]+"]");
                    a+=1;
                }
                if(clickedComponent!=null) {
                    clickedComponent.onClick(mouseButtonId, player);
                }
            }
        }
    }
}
