package me.wuxie.wakeshow.wakeshow.listener;

import me.wuxie.wakeshow.wakeshow.WakeShow;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.api.event.*;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;

import me.wuxie.wakeshow.wakeshow.network.server_out.MessageDecryptImage;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageDownloadImage;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageHook;

import me.wuxie.wakeshow.wakeshow.ui.OpenedGui;
import me.wuxie.wakeshow.wakeshow.ui.WInventoryScreen;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.ui.inventory.InvSlotProxyScreen;
import me.wuxie.wakeshow.wakeshow.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerListener implements Listener {

    /*
     * 在服务器即将重载前，清理玩家客户端残留数据
     * @param e e
     *
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onCommand(ServerCommandEvent e){
        CommandSender sender = e.getSender();
        if(sender.isOp()&&e.getCommand().equalsIgnoreCase("reload")){
            for (Player player: Bukkit.getOnlinePlayers()){
                PacketHandler.sendToPlayer(player,new );
            }
        }
    }*/

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        /*Bukkit.getScheduler().runTaskLater(WakeShow.getPlugin(),()->{
            WHudPlayerDraw playerDraw = new WHudPlayerDraw(e.getPlayer(),"player",30,60,e.getPlayer());
            playerDraw.setTooltips(Arrays.asList("这是玩家立绘组件"));
            WHudCheckBox checkBox = new WHudCheckBox(e.getPlayer(),"box",10,70,30,30,"file:cbox1.png","file:cbox1_.png");
            checkBox.setTooltips(Arrays.asList("这是勾选框组件"));
            checkBox.setSelectTooltip(Arrays.asList("这是勾选框组件","选中状态"));
            WHudSlot slot = new WHudSlot(e.getPlayer(),"slot",10,105,new ItemStack(Material.APPLE));
            slot.setCanDrag(true);
            slot.setEmptyTooltips(Arrays.asList("这是空槽位状态"));
            WHudButton button = new WHudButton(e.getPlayer(),"button",10,130,60,20,"","file:buttonTextures/button.png","file:buttonTextures/button_.png","file:buttonTextures/button__.png");
            button.setTooltips(Arrays.asList("这是按钮组件"));
            button.setName("§c§l点我回城");
            button.setFunction((b,p)->{
                p.setHealth(0);
                p.sendMessage("§a你被骗啦，哈哈哈！！");
                p.sendMessage("§a你被骗啦，哈哈哈！！");
                p.sendMessage("§a你被骗啦，哈哈哈！！");
                p.sendMessage("§a你被骗啦，哈哈哈！！");
                p.sendMessage("§a你被骗啦，哈哈哈！！");
                p.sendMessage("§a你被骗啦，哈哈哈！！");
                p.sendMessage("§a你被骗啦，哈哈哈！！");
                p.sendMessage("§a你被骗啦，哈哈哈！！");
            });
            WHudImage image = new WHudImage(e.getPlayer(),"img","file:testUI/背布.png",10,160,60,20);
            image.setTooltips(Arrays.asList("这是图片组件"));
            WHudTextList textList = new WHudTextList(e.getPlayer(),"textList",10,185,60,20, Collections.singletonList("卧槽无情！！！"));
            textList.setTooltips(Arrays.asList("这是文本组件"));
            playerDraw.setZ(98);
            checkBox.setZ(99);
            button.setZ(99);
            textList.setZ(101);
            WHudHealth hudHealth = new WHudHealth(e.getPlayer(),"health",10,210,60,20,"file:cooolding/cooldingBJ.png","file:cooolding/cooldingMB.png",e.getPlayer());
            hudHealth.setTooltips(Arrays.asList("这是血条组件","实时对应着指定生物血量"));
            long time = System.currentTimeMillis();
            WHudCooldingTag cooldingTag = new WHudCooldingTag(e.getPlayer(),"cooldingTag",55,30,60,20,time,time+1000*20,"file:cooolding/cooldingBJ.png","file:cooolding/cooldingMB.png");
            cooldingTag.setTooltips(Arrays.asList("这是一个冷却条"));
            WuxieAPI.addHudComponent(e.getPlayer(),slot);
            WuxieAPI.addHudComponent(e.getPlayer(),button);
            WuxieAPI.addHudComponent(e.getPlayer(),playerDraw);
            WuxieAPI.addHudComponent(e.getPlayer(),checkBox);
            WuxieAPI.addHudComponent(e.getPlayer(),image);
            WuxieAPI.addHudComponent(e.getPlayer(),textList);
            WuxieAPI.addHudComponent(e.getPlayer(),hudHealth);
            WuxieAPI.addHudComponent(e.getPlayer(),cooldingTag);
            },70);*/
    }
    @EventHandler
    public void onRegc(PlayerRegisterChannelEvent e){
        if(e.getChannel().equals(PacketHandler.servercannel)){
            //if(!PacketHandler.isUseNettyServer()){
                PacketHandler.sendToPlayer(e.getPlayer(),new MessageHook(WuxieAPI.hookData));
                // 预下载网络图片
                if(WuxieAPI.urlImages.size()>0){
                    PacketHandler.sendToPlayer(e.getPlayer(),new MessageDownloadImage(WuxieAPI.urlImages));
                }
                PacketHandler.sendToPlayer(e.getPlayer(),new MessageDecryptImage());
            //}
        }
    }
    /*@EventHandler
    public void onJoin2(PlayerJoinEvent e){
        if(PacketHandler.isUseNettyServer()){
            PacketHandler.sendToPlayer(e.getPlayer(),new MessageNetty());
        }
    }*/

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        OpenedGui openedGui = WuxieAPI.getOpenedGui(e.getPlayer());
        if(openedGui!=null) {
            WxScreen screen = openedGui.getScreen();
            if (screen.getCursor() != null && !screen.getCursor().getType().equals(Material.AIR)) {
                e.getPlayer().getInventory().addItem(screen.getCursor());
            }
        }
        WakeShow.getOpenedGuiManager().getOpenedGuiMap().remove(e.getPlayer().getUniqueId());
        WakeShow.getPlayerHudComponentManager().removePlayerData(e.getPlayer());
        //Object o = ServerHandler.getPlayerChannelMap().get(e.getPlayer().getUniqueId());
        //ServerHandler.getPlayerChannelMap().remove(e.getPlayer().getUniqueId());
        //if(o!=null) ServerHandler.getChannelPlayerMap().remove(o);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        //System.out.println("1");
        OpenedGui openedGui = WuxieAPI.getOpenedGui(e.getEntity());
        if(openedGui!=null) {
            WxScreen screen = openedGui.getScreen();
            if (screen.getCursor() != null && !screen.getCursor().getType().equals(Material.AIR)) {
                if (e.getKeepInventory()) {
                    e.getEntity().getInventory().addItem(screen.getCursor());
                } else {
                    e.getDrops().add(screen.getCursor());
                }
                screen.setCursor(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCloseScreen(PlayerCloseScreenEvent e){
        ItemStack itemStack = e.getScreen().getCursor();
        Player player = e.getPlayer();
        if(player.isDead()){
            boolean keepInventory =  Boolean.parseBoolean(player.getWorld().getGameRuleValue("keepInventory"));
            if(!keepInventory){
                itemStack = null;
            }
        }
        WxScreen screen = e.getScreen();
        /* 付费版界面内容跳过关闭检查，使用原版 */
        if(screen instanceof InvSlotProxyScreen && !(screen instanceof WInventoryScreen)){
            return;
        }
        
        if(itemStack!=null&&!itemStack.getType().equals(Material.AIR)){
            PlayerDropItemEvent event = PlayerUtil.dropItem(player,screen.getCursor());
            if(event.isCancelled()){
                HashMap<Integer, ItemStack> map =  player.getInventory().addItem(screen.getCursor());
                // 物品溢出
                if(!map.isEmpty()){
                    PlayerItemLeftoverEvent itemLeftoverEvent = new PlayerItemLeftoverEvent(player,map);
                    Bukkit.getPluginManager().callEvent(itemLeftoverEvent);
                }
                e.getScreen().setCursor(null);
            }
        }
        WakeShow.getOpenedGuiManager().getOpenedGuiMap().remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void ondrop(PlayerDropItemEvent e){
        //System.out.println("2");
    }

    @EventHandler
    public void onWindowChange(PlayerWindowChangeEvent e){
        //System.out.println("PlayerWindowChangeEvent:  w:"+e.getWidth()+" h:"+e.getHeight());
    }
    /*@EventHandler
    public void onClick(PlayerPreClickInventorySlotEvent e){
        if(e.getMouseButton()==1) {
            e.setCancelled(true);
            if(e.getClickItem()!=null&&!e.getClickItem().getType().equals(Material.AIR)){
                WxScreen screen = (WxScreen) e.getScreen();
                Container container = screen.getContainer();
                WSubScreen subScreen = new WSubScreen(container,"imenu",
                        "file:cooolding/shu_.png",0,0,30,70);
                Container sc = subScreen.getContainer();
                WButton button0 = new WButton(sc,"a0","",
                        "file:buttonTextures/button.png",
                        "file:buttonTextures/button_.png",
                        "file:buttonTextures/button__.png",25,0);
                button0.setW(5);
                button0.setH(5);
                WButton button1 = new WButton(sc,"a1","§c§l强化",
                        "file:buttonTextures/button.png",
                        "file:buttonTextures/button_.png",
                        "file:buttonTextures/button__.png",5,10);
                button1.setW(20);
                button1.setH(15);
                WButton button2 = new WButton(sc,"a2","§a§l镶嵌",
                        "file:buttonTextures/button.png",
                        "file:buttonTextures/button_.png",
                        "file:buttonTextures/button__.png",5,30);
                button2.setW(20);
                button2.setH(15);
                WButton button3 = new WButton(sc,"a3","§6§l打磨",
                        "file:buttonTextures/button.png",
                        "file:buttonTextures/button_.png",
                        "file:buttonTextures/button__.png",5,50);
                button3.setW(20);
                button3.setH(15);
                sc.add(button0);
                sc.add(button1);
                sc.add(button2);
                sc.add(button3);
                container.add(subScreen);
                subScreen.setClickCloseComponent("a0");
                subScreen.setFollowMouse(true);
                ScreenAnimation animation = new ScreenAnimation();
                animation.alpha = 0.4F;
                animation.alphaTo = 0.8F;
                animation.scale = 0.5F;
                animation.animationFrame = 20;
                subScreen.setAnimation(animation);
                subScreen.open();
            }
        }
    }*/
}
