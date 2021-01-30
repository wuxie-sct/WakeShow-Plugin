package me.wuxie.wakeshow.wakeshow.network.client_in;

import io.netty.buffer.ByteBuf;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.network.InPacket;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.OpenedGui;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.ui.component.WTextField;
import me.wuxie.wakeshow.wakeshow.ui.ContainerOwner;
import org.bukkit.entity.Player;

public class MessageTextFieldInput extends InPacket {
    public MessageTextFieldInput(ByteBuf buf) {
        super(2, buf);
    }

    @Override
    public void handlePacket(Player player) {
        String data = getPacketBuffer().readString();
        String[] datasplit = data.split("<##>");
        String componentPath = datasplit[0];
        String path[] = componentPath.split("\\.");
        String text;
        if(datasplit.length>1) {
            text = datasplit[1];
        }else text="";
        OpenedGui openedGui = WuxieAPI.getOpenedGui(player);
        if(openedGui!=null){
            WxScreen screen = openedGui.getScreen();
            if(screen.getId().equals(path[0])) {
                if(screen.getId().equals(path[0])) {
                    ContainerOwner owner = screen;
                    Component inputComponent = null;
                    for (int a = 1;a<path.length;){
                        Component component = owner.getContainer().getComponent(path[a]);
                        if(component!=null) {
                            if (component instanceof ContainerOwner) {
                                owner = (ContainerOwner) component;
                            } else if(a+1==path.length){
                                inputComponent = component;
                            } else throw new ClassCastException("input component parent cannot cast ContainerOwner!  path:"+componentPath+" in:["+a+"]["+path[a]+"]");
                        }else throw new NullPointerException("not find input component! path:"+componentPath+" in:["+a+"]["+path[a]+"]");
                        a+=1;
                    }
                    assert inputComponent != null;
                    if(inputComponent instanceof WTextField){
                        ((WTextField) inputComponent).setText(text,true);
                    }else throw new ClassCastException("input component cannot cast ContainerOwner!  path:"+componentPath);
                }
            }
        }
    }
}
