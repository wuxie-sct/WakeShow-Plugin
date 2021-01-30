package me.wuxie.wakeshow.wakeshow;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;

import me.wuxie.wakeshow.wakeshow.encrypt.FileEncryptor;
import me.wuxie.wakeshow.wakeshow.listener.PlayerListener;
import me.wuxie.wakeshow.wakeshow.manager.CooldownManager;
import me.wuxie.wakeshow.wakeshow.manager.OpenedGuiManager;
import me.wuxie.wakeshow.wakeshow.manager.PlayerHudComponentManager;
import me.wuxie.wakeshow.wakeshow.network.PacketHandler;

import me.wuxie.wakeshow.wakeshow.network.server_out.MessageClientReload;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageDownloadImage;
import me.wuxie.wakeshow.wakeshow.network.server_out.MessageHook;

import me.wuxie.wakeshow.wakeshow.ui.WInventoryScreen;
import me.wuxie.wakeshow.wakeshow.ui.animation.*;
import me.wuxie.wakeshow.wakeshow.ui.component.*;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.inventory.ResetInventorySlot;
import me.wuxie.wakeshow.wakeshow.util.TickUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

public class WakeShow extends JavaPlugin {

    @Getter
    private static WakeShow plugin;
    @Getter
    @Setter
    private static PacketHandler packetHandler;
    @Getter
    @Setter
    private static OpenedGuiManager openedGuiManager;
    @Getter
    @Setter
    private static PlayerHudComponentManager playerHudComponentManager;
    @Getter
    @Setter
    private static CooldownManager cooldownManager;
    @Getter
    private static String encryptKey;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        encryptKey = getConfig().getString("encryptKey","defaultKey");
        WuxieAPI.urlImages.addAll(getConfig().getStringList("preDownloadImages"));
        WakeShow.setPacketHandler(new PacketHandler());
        Bukkit.getConsoleSender().sendMessage("注册信息接收通道……");
        // 接收通道
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin,PacketHandler.clientcannel, WakeShow.getPacketHandler());
        Bukkit.getConsoleSender().sendMessage("注册信息发送通道……");
        // 发出通道
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin,PacketHandler.servercannel);
        WakeShow.setOpenedGuiManager(new OpenedGuiManager());
        WakeShow.setPlayerHudComponentManager(new PlayerHudComponentManager());
        WakeShow.setCooldownManager(new CooldownManager());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),plugin);

        for (Player player:Bukkit.getOnlinePlayers()){
            PacketHandler.sendToPlayer(player,new MessageHook(WuxieAPI.hookData));
            // 预下载网络图片
            if(WuxieAPI.urlImages.size()>0){
                PacketHandler.sendToPlayer(player,new MessageDownloadImage(WuxieAPI.urlImages));
            }
        }
        plugin_init();
    }

    private void plugin_init() {
        TickUtil.init();
        Bukkit.getPluginCommand("wakeshow").setExecutor(plugin);
        new Metrics(plugin);
        Set<String> dependPlugins = new HashSet<>();
        label:
        for (Plugin plugin: Bukkit.getPluginManager().getPlugins()){
            List<String> depends = plugin.getDescription().getDepend();
            List<String> softDepends = plugin.getDescription().getSoftDepend();
            for (String s:depends){
                if(s.equals(this.getName())){
                    dependPlugins.add(plugin.getName());
                    continue label;
                }
            }
            for (String s:softDepends){
                if(s.equals(this.getName())){
                    dependPlugins.add(plugin.getName());
                    continue label;
                }
            }
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp())return true;
        if(args.length==0)return true;
        if(args[0].equalsIgnoreCase("encrypt")){
            File file = new File(getDataFolder(),"images");
            List<File> images = new ArrayList<>();
            if(file.exists()&&file.isDirectory()){
                List<File> directorys = new ArrayList<>();
                directorys.add(file);
                while (directorys.size()>0){
                    File file1 = directorys.get(0);
                    File[] files = file1.listFiles();
                    if(files!=null&&files.length>0){
                        for (File file2: files){
                            if(!file2.isDirectory()){
                                if(file2.getName().endsWith(".png")||file2.getName().endsWith(".gif")){
                                    images.add(file2);
                                }
                            } else {
                                directorys.add(file2);
                            }
                        }
                    }
                    directorys.remove(0);
                }
            } else {
                file.mkdirs();
            }
            if(images.size()>0){
                sender.sendMessage("§a在 §eimages §a文件夹中找到了 §b"+images.size()+" §a个需要加密的图片!");
                sender.sendMessage("§a推荐将需要加密的整个目录文件夹拖入 §bimages §a文件夹!");
                sender.sendMessage("§a>>>>在加密时将会自动按文件夹目录分类存放!");
                sender.sendMessage("§a现在开始逐个为图片加密…………");
                Bukkit.getScheduler().runTaskAsynchronously(this,()->{
                    File encryptImages = new File(getDataFolder(),"encryptImages");
                    if(!encryptImages.exists()){
                        encryptImages.mkdirs();
                    }
                    try {
                        for (File file1: images){
                            String path = file1.getPath().split("images")[1];
                            File file2 = new File(encryptImages,path+".wsi");
                            File file3 = new File(encryptImages,path.replace(file1.getName(),""));
                            if(!file3.exists()){
                                file3.mkdirs();
                            }
                            file2.createNewFile();
                            FileEncryptor.encryptFile(file1,file2,encryptKey);
                            sender.sendMessage("§6>>加密: §aimages"+path+"\n" +
                                    "§c------------------> §bencryptImages"+path+".wsi");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                sender.sendMessage("§7在 §eimages §7文件夹中没有找到任何图片文件!");
            }
        }else if(args[0].equalsIgnoreCase("reloadClient")&&sender instanceof Player){
            PacketHandler.sendToPlayer((Player) sender,new MessageClientReload());
        }
        else if(args[0].equalsIgnoreCase("test")){
            WInventoryScreen screen = new WInventoryScreen("测试UI", "file:testUI/背布.png", -1,-1,240,193,10,90);
            //WInventoryScreen screen = new WInventoryScreen("测试UI", "file:gqtt.png", -1,-1,1500,1500,10,90);

            ResetInventorySlot inventorySlot1 = new ResetInventorySlot();
            inventorySlot1.index = 36;
            inventorySlot1.x = 72+20;
            inventorySlot1.y = 54;

            ResetInventorySlot inventorySlot2 = new ResetInventorySlot();
            inventorySlot2.index = 37;
            inventorySlot2.x = 54+20;
            inventorySlot2.y = 54;

            ResetInventorySlot inventorySlot3 = new ResetInventorySlot();
            inventorySlot3.index = 38;
            inventorySlot3.x = 36+20;
            inventorySlot3.y = 54;

            ResetInventorySlot inventorySlot4 = new ResetInventorySlot();
            inventorySlot4.index = 39;
            inventorySlot4.x = 18+20;
            inventorySlot4.y = 54;

            ResetInventorySlot inventorySlot5 = new ResetInventorySlot();
            inventorySlot5.index = 40;
            inventorySlot5.x = 0+20;
            inventorySlot5.y = 54;

            screen.getResetSlots().add(inventorySlot1);
            screen.getResetSlots().add(inventorySlot2);
            screen.getResetSlots().add(inventorySlot3);
            screen.getResetSlots().add(inventorySlot4);
            screen.getResetSlots().add(inventorySlot5);
            ScreenAnimation sa = new ScreenAnimation();
            sa.animationFrame = 60;
            sa.scale = 0.2f;
            sa.alpha = 0.0f;
            sa.alphaTo = 0.8f;
            sa.rotate = 315;
            sa.rotateTo = 2;
            //screen.setAnimation(sa);
            Container container=screen.getContainer();
            WImage page = new WImage(container,"测试UI页面","file:testUI/页面.png", 3,41,234,146);
            page.setZ(50);
            // 不断旋转的八卦
            WImage bagua = new WImage(container,"八卦","file:testUI/八卦.png", 40,80,73,73);
            // 设置绘制优先级(数字越大覆盖在越前)
            bagua.setZ(101);
            ImageAnimation baguaAnimation = new ImageAnimation();
            baguaAnimation.animationFrame = 72;
            baguaAnimation.rotateTo = 360.0f;
            baguaAnimation.rotateModel = 1;
            bagua.setImageAnimation(baguaAnimation);
            // 不断显隐的香炉
            WImage xianglu = new WImage(container,"香炉","file:testUI/香炉.png", 240-36-21,147,21,34);
            // 设置绘制优先级(数字越大覆盖在越前)
            xianglu.setZ(101);
            ImageAnimation xiangluAnimation = new ImageAnimation();
            xiangluAnimation.animationFrame = 100;
            xiangluAnimation.alphaTo = 0.7f;
            xiangluAnimation.alphaBack = true;
            xianglu.setImageAnimation(xiangluAnimation);
            // 倾斜后淡出的剑鞘
            WImage jianqiao = new WImage(container,"剑鞘","file:testUI/剑鞘.png", 20,130,15,69);
            // 剑鞘覆盖在剑上，剑鞘的优先级比剑大
            jianqiao.setZ(103);
            ImageAnimationNodeList jianqiaoAnimationNodeList = new ImageAnimationNodeList();
            List<ImageAnimationNode> jianqiaoAnimationNodes =  jianqiaoAnimationNodeList.animationList;
            ImageAnimationNode jqnode0 = new ImageAnimationNode();
            jqnode0.animationFrame = 15;
            jqnode0.rotateTo = 45.0f;
            ImageAnimationNode jqnode1 = new ImageAnimationNode();
            jqnode1.animationFrame = 50;
            ImageAnimationNode jqnode2 = new ImageAnimationNode();
            jqnode2.animationFrame = 50;
            jqnode2.alphaTo = 0.0f;
            jianqiaoAnimationNodes.add(jqnode0);
            jianqiaoAnimationNodes.add(jqnode1);
            jianqiaoAnimationNodes.add(jqnode2);
            jianqiao.setAnimationNodeList(jianqiaoAnimationNodeList);
            // 运动的剑，结束后逐渐放大消失
            WImage jian = new WImage(container,"剑身","file:testUI/剑身.png", 20,130,15,69);
            // 剑的优先级比其它组件大
            jian.setZ(102);
            ImageAnimationNodeList jianAnimationNodeList = new ImageAnimationNodeList();
            List<ImageAnimationNode> jianAnimationNodes =  jianAnimationNodeList.animationList;
            // 和剑鞘同步第一动画节点
            ImageAnimationNode jnode1 = new ImageAnimationNode();
            jnode1.rotateTo = 45.0f;
            jnode1.animationFrame = 15;
            // 出剑动画
            ImageAnimationNode jnode2 = new ImageAnimationNode();
            jnode2.moveX = 40;
            // 负数向上
            jnode2.moveY = -40;
            jnode2.animationFrame = 20;
            // 飞剑，旋转并移动
            ImageAnimationNode jnode3 = new ImageAnimationNode();
            jnode3.animationFrame = 100;
            jnode3.rotateTo = 400.0f;
            jnode3.moveX = 130;
            jnode3.moveY = -100;
            // 停顿 50 帧
            ImageAnimationNode jnode4 = new ImageAnimationNode();
            jnode4.animationFrame = 50;
            // 放大 下移 淡出
            ImageAnimationNode jnode5 = new ImageAnimationNode();
            jnode5.animationFrame = 30;
            jnode5.scaleTo = 1.5f;
            // 下移
            jnode5.moveY = 20;
            jnode5.alphaTo = 0.0f;
            jianAnimationNodes.add(jnode1);
            jianAnimationNodes.add(jnode2);
            jianAnimationNodes.add(jnode3);
            jianAnimationNodes.add(jnode4);
            jianAnimationNodes.add(jnode5);
            jian.setAnimationNodeList(jianAnimationNodeList);
            container.add(page);
            //container.add(bagua);
            container.add(xianglu);
            container.add(jianqiao);
            container.add(jian);
            WButton button0 = new WButton(container,"11","hh",
                    "file:buttonTextures/button.png",
                    "file:buttonTextures/button_.png",
                    "file:buttonTextures/button__.png",3,31);
            button0.setW(20);
            button0.setH(10);
            container.add(button0);
            WSubScreen subScreen = new WSubScreen(container,"subscreen1","file:inventory.png",3,41,200,90);
            Container sc = subScreen.getContainer();
            WButton button = new WButton(sc,"1","hh",
                    "file:buttonTextures/button.png",
                    "file:buttonTextures/button_.png",
                    "file:buttonTextures/button__.png",0,0);
            button.setW(20);
            button.setH(10);
            WSlot slot = new WSlot(container,"slot",new ItemStack(Material.APPLE),120,0);
            WSlot slot2 = new WSlot(container,"slot2",new ItemStack(Material.DIAMOND),140,0);
            //slot.setBackground("file:icon/icon_1.png");
            slot.setEmptyTooltips(Arrays.asList("这个槽位没有物品","你要放一个吗?"));
            slot.setCanDrag(true);
            long time = System.currentTimeMillis();
            //me.wuxie.wakeshow.wakeshow.ui.laji.WCooldingTag cooldingTag = new me.wuxie.wakeshow.wakeshow.ui.laji.WCooldingTag(sc,"tag",10,30,50,20,time,time+5000,"file:cooolding/cooldingBJ.png","file:cooolding/cooldingMB.png");
            //cooldingTag.setStuff(true);
            //me.wuxie.wakeshow.wakeshow.ui.laji.WCooldingTag cooldingTag2 = new me.wuxie.wakeshow.wakeshow.ui.laji.WCooldingTag(sc,"tag2",70,30,20,50,time,time+5000,"file:cooolding/shu_.png","file:cooolding/shu.png");
            //cooldingTag2.setVertical(true);
            //WImage image = new WImage(sc,"aa","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1811374275,2590746539&fm=26&gp=0.jpg",100,30,30,30);
            WProportionTag proportionTag = new WProportionTag(sc,"p",140,30,50, 20,0.3,"file:cooolding/cooldingBJ.png","file:cooolding/cooldingMB.png");
            sc.add(proportionTag);
            //sc.add(image);
            //sc.add(cooldingTag);
            //sc.add(cooldingTag2);
            sc.add(button);
            sc.add(slot);
            sc.add(slot2);
            subScreen.setClickCloseComponent("1");
            subScreen.setZ(104);
            //subScreen.setFollowMouse(true);
            WPlayerDraw playerDraw = new WPlayerDraw(container,"playerdraw",(Player) sender,150,135);
            playerDraw.setZ(55);
            container.add(playerDraw);
            WPlayerDraw playerDraw2 = new WPlayerDraw(sc,"playerdraw2",(Player) sender,40,50);
            sc.add(playerDraw2);
        /*WCooldingTag ccooldingTag2 = new WCooldingTag(container,"tag",10,30,50,20,100,200,"file:cooolding/cooldingBJ.png","file:cooolding/cooldingMB.png");
        ccooldingTag2.setVertical(false);
        ccooldingTag2.setZ(120);
        ccooldingTag2.setTooltips(Arrays.asList("aaaa","bbbb"));
        container.add(ccooldingTag2);*/
            WuxieAPI.openGui((Player) sender,screen);
            //WHudCooldingTag hudCooldingTag = new WHudCooldingTag((Player) sender,"tag",0,0,200,20,0,200,"file:cooolding/cooldingBJ.png","file:cooolding/cooldingMB.png");
            //WuxieAPI.addHudComponent((Player) sender,hudCooldingTag);
        /*Bukkit.getScheduler().runTaskLater(this,()->{
            WuxieAPI.removeHudComponent((Player) sender,"tag");
            Bukkit.getScheduler().runTaskLater(this,()-> {
                hudCooldingTag.updateTime();
                WuxieAPI.addHudComponent((Player) sender,hudCooldingTag);
                Bukkit.getScheduler().runTaskLater(this,()-> {
                    WuxieAPI.removeHudComponent((Player) sender,"tag");
                    },50);
                },50);
            },100);*/
            //Bukkit.getScheduler().runTaskLater(this, subScreen::open,60);
        }
        return true;
    }
}
