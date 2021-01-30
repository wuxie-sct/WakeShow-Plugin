package me.wuxie.wakeshow.wakeshow.util;

import com.google.gson.*;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.ContainerOwner;
import me.wuxie.wakeshow.wakeshow.ui.WInventoryScreen;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.ui.animation.ScreenAnimation;
import me.wuxie.wakeshow.wakeshow.ui.component.*;
import me.wuxie.wakeshow.wakeshow.ui.component.WScrollingContainer;
import me.wuxie.wakeshow.wakeshow.ui.hudcomponent.*;
import me.wuxie.wakeshow.wakeshow.ui.inventory.ResetInventorySlot;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 不用管
 * 不用看
 */
public class JsonUtil {
    private static final Gson gson = new Gson();
    private static final JsonParser jsonParser = new JsonParser();
    public static String screenToJson(WxScreen screen){
        if(screen == null) {
            return "{\"id\":\"nullScreen\"}";
        }
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("id",screen.getId());
        if(screen instanceof WInventoryScreen){
            jo.addProperty("slotLeft",((WInventoryScreen) screen).getSlotLeft());
            jo.addProperty("slotTop",((WInventoryScreen) screen).getSlotTop());
            jo.addProperty("type","WInventoryScreen");
            JsonArray array = new JsonArray();
            for (ResetInventorySlot slot:((WInventoryScreen) screen).getResetSlots()){
                array.add(slot.toJson());
            }
            jo.add("resetInventorySlots",array);
        }else {
            jo.addProperty("type","WxScreen");
        }
        jo.addProperty("background",screen.getBackground());
        jo.addProperty("x",screen.getX());
        jo.addProperty("y",screen.getY());
        jo.addProperty("w",screen.getW());
        jo.addProperty("h",screen.getH());
        JsonArray ja = new JsonArray();
        for (Component component: screen.getContainer().getComponentMap().values()){
            ja.add(componentToJson(component));
        }
        jo.add("container",ja);
        return jo.toString();
    }

    public static JsonElement componentToJson(Component component){
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("id",component.getId());
        jo.addProperty("x",component.getX());
        jo.addProperty("y",component.getY());
        jo.addProperty("w",component.getW());
        jo.addProperty("h",component.getH());
        //jo.addProperty("ownerPath",getOwnerPath(component.getParent().getOwner()));
        if(component.getTooltips()!=null&& component.getTooltips().size()>0){
            jo.add("tooltips",gson.toJsonTree(component.getTooltips()));
        }
        if(!component.isFollowOffset()){
            jo.addProperty("followOffset",component.isFollowOffset());
        }
        if(component.getZ()!=0){
            jo.addProperty("z",component.getZ());
        }else {
            jo.addProperty("z",100);
        }
        if(component instanceof WButton){
            jo.addProperty("type","WButton");
            WButton button = (WButton) component;
            jo.addProperty("name",button.getName());
            jo.addProperty("url1",button.getUrl1());
            jo.addProperty("url2",button.getUrl2());
            jo.addProperty("url3",button.getUrl3());
            if(!button.isCanPress()){
                jo.addProperty("canPress",button.isCanPress());
            }
            if(button.getNormalAnimation()!=null){
                jo.addProperty("normalAnimation",button.getNormalAnimation().toString());
            }else {
                jo.addProperty("normalAnimation","0");
            }
            if(button.getHoverAnimation()!=null){
                jo.addProperty("hoverAnimation",button.getHoverAnimation().toString());
            }else {
                jo.addProperty("hoverAnimation","0");
            }
            if(button.getPressAnimation()!=null){
                jo.addProperty("pressAnimation",button.getPressAnimation().toString());
            }else {
                jo.addProperty("pressAnimation","0");
            }
        }else if(component instanceof WDropField){
            jo.addProperty("type","WDropField");
            WDropField dropField = (WDropField) component;
            if(dropField.getName()!=null) {
                jo.addProperty("name", dropField.getName());
            }
            if(dropField.getUrl1()!=null) {
                jo.addProperty("url1", dropField.getUrl1());
            }
            if(dropField.getUrl2()!=null) {
                jo.addProperty("url2", dropField.getUrl2());
            }
            if(dropField.getNormalAnimation()!=null){
                jo.addProperty("normalAnimation",dropField.getNormalAnimation().toString());
            }else {
                jo.addProperty("normalAnimation","0");
            }
            if(dropField.getHoverAnimation()!=null){
                jo.addProperty("hoverAnimation",dropField.getHoverAnimation().toString());
            }else {
                jo.addProperty("hoverAnimation","0");
            }
        }else if(component instanceof WCheckBox){
            jo.addProperty("type","WCheckBox");
            WCheckBox checkBox = (WCheckBox) component;
            jo.addProperty("url1",checkBox.getUrl1());
            jo.addProperty("url2",checkBox.getUrl2());
            if(checkBox.isSelect()){
                jo.addProperty("select",checkBox.isSelect());
            }
            if(checkBox.getSelectTooltip()!=null&&checkBox.getSelectTooltip().size()>0) {
                jo.add("selectTooltip",gson.toJsonTree(checkBox.getSelectTooltip()));
            }
            if(checkBox.getName()!=null&&!checkBox.getName().isEmpty()) {
                jo.addProperty("name",checkBox.getName());
            }
            if(checkBox.getSelectName()!=null&&!checkBox.getSelectName().isEmpty()) {
                jo.addProperty("selectName",checkBox.getSelectName());
            }
            if(checkBox.getOffsetName()!=2) {
                jo.addProperty("offsetName",checkBox.getOffsetName());
            }
            if(checkBox.getNormalAnimation()!=null){
                jo.addProperty("normalAnimation",checkBox.getNormalAnimation().toString());
            }else {
                jo.addProperty("normalAnimation","0");
            }
            if(checkBox.getSelectAnimation()!=null){
                jo.addProperty("selectAnimation",checkBox.getSelectAnimation().toString());
            }else {
                jo.addProperty("selectAnimation","0");
            }
        }else if(component instanceof WImage){
            jo.addProperty("type","WImage");
            WImage image = (WImage) component;
            if(image.getImageAnimation()!=null&&image.getImageAnimation().animationFrame>0){
                jo.addProperty("imageAnimation",image.getImageAnimation().toString());
            } else if(image.getAnimationNodeList()!=null&&image.getAnimationNodeList().animationList.size()>0){
                jo.add("imageAnimationNodeList",image.getAnimationNodeList().toJsonElement());
            }
            jo.addProperty("url1",image.getUrl1());
        }else if(component instanceof WLivingEntityDraw){
            jo.addProperty("type","WLivingEntityDraw");
            WLivingEntityDraw livingEntityDraw = (WLivingEntityDraw) component;
            if(livingEntityDraw.getName()!=null){
                jo.addProperty("name",livingEntityDraw.getName());
            }
            if(livingEntityDraw.getLivingEntity()!=null){
                jo.addProperty("entityId",livingEntityDraw.getEntityId());
            } else {
                jo.addProperty("entityType",livingEntityDraw.getEntityType());
                jo.add("equipmentMap",itemMapToJson(livingEntityDraw.getEquipmentMap()));
                if(livingEntityDraw.getCustomUnicodeId()!=null){
                    jo.addProperty("uuid",livingEntityDraw.getCustomUnicodeId().toString());
                }
            }
            if(livingEntityDraw.isRotate()){
                jo.addProperty("rotate",true);
                if(livingEntityDraw.getRotateCircle()>0){
                    jo.addProperty("rotateCircle",livingEntityDraw.getRotateCircle());
                }
                if(livingEntityDraw.getRotateSpeed()>0){
                    jo.addProperty("rotateSpeed",livingEntityDraw.getRotateSpeed());
                }
            }
            if(livingEntityDraw.getScale() != 30){
                jo.addProperty("scale",livingEntityDraw.getScale());
            }
        }else if(component instanceof WPlayerDraw){
            jo.addProperty("type","WPlayerDraw");
            WPlayerDraw playerDraw = (WPlayerDraw) component;
            NBTTagCompound nbtTagCompound = GameProfileSerializer.serialize(new NBTTagCompound(),playerDraw.getGameProfile());
            jo.addProperty("gameProfile",nbtTagCompound.toString());
            if(playerDraw.isRotate()){
                jo.addProperty("rotate",true);
                if(playerDraw.getRotateCircle()>0) {
                    jo.addProperty("rotateCircle",playerDraw.getRotateCircle());
                }
                if(playerDraw.getRotateSpeed()>0) {
                    jo.addProperty("rotateSpeed",playerDraw.getRotateSpeed());
                }
            }
            if(playerDraw.getScale()!=30) {
                jo.addProperty("scale",playerDraw.getScale());
            }

        }else if(component instanceof WTextList){
            jo.addProperty("type","WTextList");
            WTextList textList = (WTextList) component;
            jo.add("content", gson.toJsonTree(textList.getContent()));
            if(textList.getScale()!=1.0d){
                jo.addProperty("scale",textList.getScale());
            }
        }else if(component instanceof WTextField){
            jo.addProperty("type","WTextField");
            WTextField textField = (WTextField) component;
            if(!"".equals(textField.getText())){
                jo.addProperty("text", textField.getText());
            }
            if(textField.getBackground()!=null&&!textField.getBackground().isEmpty()){
                jo.addProperty("background", textField.getBackground());
            }

        }else if(component instanceof WSlot){
            jo.addProperty("type","WSlot");
            WSlot slot = (WSlot) component;
            //jo.addProperty("item", itemToJson(slot.getItemStack()));
            if(slot.isCanDrag())
            jo.addProperty("canDrag",slot.isCanDrag());
            if (slot.getScale()>1.0f){
                jo.addProperty("scale",slot.getScale());
            }
            if(slot.getBackground()!=null){
                jo.addProperty("background",slot.getBackground());
            }
            if(slot.getEmptyTooltips()!=null&&slot.getEmptyTooltips().size()>0){
                jo.add("emptyTooltips",gson.toJsonTree(slot.getEmptyTooltips()));
            }

        }else if(component instanceof WScrollingContainer){
            jo.addProperty("type","WScrollingContainer");
            WScrollingContainer scrollingContainer = (WScrollingContainer) component;
            jo.addProperty("scrollHeight",scrollingContainer.getScrollHeight());
            jo.addProperty("showScrollBar",scrollingContainer.isShowScrollBar());
            if(scrollingContainer.getBarWidth()>-1) {
                jo.addProperty("barWidth",scrollingContainer.getBarWidth());
            }
            if(scrollingContainer.getBackground()!=null&&!scrollingContainer.getBackground().isEmpty()) {
                jo.addProperty("background",scrollingContainer.getBackground());
            }
            if(scrollingContainer.getScrollBar()!=null&&!scrollingContainer.getScrollBar().isEmpty()) {
                jo.addProperty("scrollBar",scrollingContainer.getScrollBar());
            }
            if(scrollingContainer.getScrollBarBack()!=null&&!scrollingContainer.getScrollBarBack().isEmpty()){
                jo.addProperty("scrollBarBack",scrollingContainer.getScrollBarBack());
            }
            JsonArray ja = new JsonArray();
            for (Component component1: scrollingContainer.getContainer().getComponentMap().values()) {
                ja.add(componentToJson(component1));
            }
            jo.add("container",ja);

        }else if(component instanceof WSubScreen){
            jo.addProperty("type","WSubScreen");
            WSubScreen subScreen = (WSubScreen) component;
            jo.addProperty("background",subScreen.getBackground());
            jo.addProperty("followMouse",subScreen.isFollowMouse());
            ScreenAnimation animation = subScreen.getAnimation();
            if(animation!=null&&animation.animationFrame>0){
                jo.addProperty("animation",
                        animation.animationFrame + "-" +
                        animation.rotate + "-" +
                        animation.rotateTo + "-" +
                        animation.alpha + "-" +
                        animation.alphaTo + "-" +
                        animation.scale);
            }
            JsonArray ja = new JsonArray();
            for (Component component1: subScreen.getContainer().getComponentMap().values()) {
                ja.add(componentToJson(component1));
            }
            jo.add("container",ja);
        }else if(component instanceof WCooldingTag){
            jo.addProperty("type","WCooldingTag");
            WCooldingTag cooldingTag = (WCooldingTag) component;
            jo.addProperty("texture",cooldingTag.getTexture());
            jo.addProperty("cooldingCover",cooldingTag.getCooldingCover());
            jo.addProperty("currentTime",cooldingTag.getCurrentTime());
            jo.addProperty("maxTime",cooldingTag.getMaxTime());
            jo.addProperty("vertical",cooldingTag.isVertical());
            jo.addProperty("stuff",cooldingTag.isStuff());
        }
        else if(component instanceof WProportionTag){
            jo.addProperty("type","WProportionTag");
            WProportionTag proportionTag = (WProportionTag) component;
            jo.addProperty("texture",proportionTag.getTexture());
            jo.addProperty("cover",proportionTag.getCover());
            jo.addProperty("proportion",proportionTag.getProportion());
            jo.addProperty("vertical",proportionTag.isVertical());
        }else if(component instanceof WHealth){
            jo.addProperty("type","WHealth");
            WHealth health = (WHealth) component;
            jo.addProperty("texture",health.getTexture());
            jo.addProperty("cover",health.getCover());
            jo.addProperty("entityId",health.getLivingEntity().getEntityId());
            jo.addProperty("vertical",health.isVertical());
        }else {
            jo.addProperty("type","UnKnow");
        }
        return je;
    }

    public static JsonElement hudComponentToJson(HudComponent component){
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("id",component.getId());
        jo.addProperty("x",component.getX());
        jo.addProperty("y",component.getY());
        jo.addProperty("w",component.getW());
        jo.addProperty("h",component.getH());
        if(component.getTooltips()!=null&& component.getTooltips().size()>0){
            jo.add("tooltips",gson.toJsonTree(component.getTooltips()));
        }
        if(component.getZ()!=100){
            jo.addProperty("z",component.getZ());
        }
        if(component instanceof WHudButton){
            jo.addProperty("type","WHudButton");
            WHudButton button = (WHudButton) component;
            jo.addProperty("name",button.getName());
            jo.addProperty("url1",button.getUrl1());
            jo.addProperty("url2",button.getUrl2());
            jo.addProperty("url3",button.getUrl3());
            if(!button.isCanPress()){
                jo.addProperty("canPress",button.isCanPress());
            }

        }else if(component instanceof WHudCheckBox){
            jo.addProperty("type","WHudCheckBox");
            WHudCheckBox checkBox = (WHudCheckBox) component;
            jo.addProperty("url1",checkBox.getUrl1());
            jo.addProperty("url2",checkBox.getUrl2());
            if(checkBox.isSelect()){
                jo.addProperty("select",checkBox.isSelect());
            }
            if(checkBox.getSelectTooltip()!=null&&checkBox.getSelectTooltip().size()>0) {
                jo.add("selectTooltip",gson.toJsonTree(checkBox.getSelectTooltip()));
            }
            if(checkBox.getName()!=null&&!checkBox.getName().isEmpty()) {
                jo.addProperty("name",checkBox.getName());
            }
            if(checkBox.getSelectName()!=null&&!checkBox.getSelectName().isEmpty()) {
                jo.addProperty("selectName",checkBox.getSelectName());
            }
            if(checkBox.getOffsetName()!=2) {
                jo.addProperty("offsetName",checkBox.getOffsetName());
            }

        }else if(component instanceof WHudImage){
            jo.addProperty("type","WHudImage");
            WHudImage image = (WHudImage) component;
            jo.addProperty("url1",image.getUrl1());

        }else if(component instanceof WHudLivingEntityDraw){
            jo.addProperty("type","WHudLivingEntityDraw");
            WHudLivingEntityDraw livingEntityDraw = (WHudLivingEntityDraw) component;
            if(livingEntityDraw.getName()!=null){
                jo.addProperty("name",livingEntityDraw.getName());
            }
            if(livingEntityDraw.getLivingEntity()!=null){
                jo.addProperty("entityId",livingEntityDraw.getEntityId());
            } else{
                jo.addProperty("entityType",livingEntityDraw.getEntityType());
                jo.add("equipmentMap",itemMapToJson(livingEntityDraw.getEquipmentMap()));
            }
            if(livingEntityDraw.isRotate()){
                jo.addProperty("rotate",true);
                if(livingEntityDraw.getRotateCircle()>0){
                    jo.addProperty("rotateCircle",livingEntityDraw.getRotateCircle());
                }
                if(livingEntityDraw.getRotateSpeed()>0){
                    jo.addProperty("rotateSpeed",livingEntityDraw.getRotateSpeed());
                }
            }
            if(livingEntityDraw.getScale() != 30){
                jo.addProperty("scale",livingEntityDraw.getScale());
            }

        }else if(component instanceof WHudPlayerDraw){
            jo.addProperty("type","WHudPlayerDraw");
            WHudPlayerDraw playerDraw = (WHudPlayerDraw) component;
            NBTTagCompound nbtTagCompound = GameProfileSerializer.serialize(new NBTTagCompound(),playerDraw.getGameProfile());
            jo.addProperty("gameProfile",nbtTagCompound.toString());
            if(playerDraw.isRotate()){
                jo.addProperty("rotate",true);
                if(playerDraw.getRotateCircle()>0) {
                    jo.addProperty("rotateCircle",playerDraw.getRotateCircle());
                }
                if(playerDraw.getRotateSpeed()>0) {
                    jo.addProperty("rotateSpeed",playerDraw.getRotateSpeed());
                }
            }
            if(playerDraw.getScale()!=30) {
                jo.addProperty("scale",playerDraw.getScale());
            }

        }else if(component instanceof WHudTextList){
            jo.addProperty("type","WHudTextList");
            WHudTextList textList = (WHudTextList) component;
            jo.add("content", gson.toJsonTree(textList.getContent()));
            if(textList.getScale()!=1.0d){
                jo.addProperty("scale",textList.getScale());
            }
        }else if(component instanceof WHudSlot){
            jo.addProperty("type","WHudSlot");
            WHudSlot slot = (WHudSlot) component;
            jo.addProperty("item", itemToJson(slot.getItemStack()));
            if(slot.isCanDrag())
                jo.addProperty("canDrag",slot.isCanDrag());
            if (slot.getScale()>1.0f){
                jo.addProperty("scale",slot.getScale());
            }
            if(slot.getBackground()!=null){
                jo.addProperty("background",slot.getBackground());
            }
            if(slot.getEmptyTooltips()!=null&&slot.getEmptyTooltips().size()>0){
                jo.add("emptyTooltips",gson.toJsonTree(slot.getEmptyTooltips()));
            }

        }else if(component instanceof WHudCooldingTag){
            jo.addProperty("type","WHudCooldingTag");
            WHudCooldingTag cooldingTag = (WHudCooldingTag) component;
            jo.addProperty("texture",cooldingTag.getTexture());
            jo.addProperty("cooldingCover",cooldingTag.getCooldingCover());
            jo.addProperty("currentTime",cooldingTag.getCurrentTime());
            jo.addProperty("maxTime",cooldingTag.getMaxTime());
            jo.addProperty("vertical",cooldingTag.isVertical());
            jo.addProperty("stuff",cooldingTag.isStuff());

        } else if(component instanceof WHudProportionTag){
            jo.addProperty("type","WHudProportionTag");
            WHudProportionTag proportionTag = (WHudProportionTag) component;
            jo.addProperty("texture",proportionTag.getTexture());
            jo.addProperty("cover",proportionTag.getCover());
            jo.addProperty("proportion",proportionTag.getProportion());
            jo.addProperty("vertical",proportionTag.isVertical());

        }else if(component instanceof WHudHealth){
            jo.addProperty("type","WHudHealth");
            WHudHealth hudHealth = (WHudHealth) component;
            jo.addProperty("texture",hudHealth.getTexture());
            jo.addProperty("cover",hudHealth.getCover());
            jo.addProperty("entityId",hudHealth.getLivingEntity().getEntityId());
            jo.addProperty("vertical",hudHealth.isVertical());
        }
        else {
            jo.addProperty("type","UnKnow");
        }
        return je;
    }


    public static String updateComponentToJson(ContainerOwner owner, Collection<Component> components){
        if(owner==null) {
            throw new NullPointerException("update owner cannot be null");
        }
        if(components==null) {
            components = new ArrayList<>();
        }
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        // 这个 ownerId 相当于一个容器路径（当容器只是WxScreen时返回的就是WxScreen的id）
        // 否则 返回这个WxScreen容器中对应于owner的路径
        // 例如WxScreen(id:testScreen)中的scroll（id:testScroll）容器 : testScreen.testScroll
        // 如果scroll中有有容器（id:testContainer） : testScreen.testScroll.testContainer
        jo.addProperty("ownerPath",owner.getPath());
        if(owner instanceof WxScreen) {
            WxScreen screen = (WxScreen) owner;
            jo.addProperty("x",screen.getX());
            jo.addProperty("y",screen.getY());
            jo.addProperty("w",screen.getW());
            jo.addProperty("h",screen.getH());
            jo.addProperty("background",screen.getBackground());
            ItemStack cursor = ((WxScreen) owner).getCursor();
            if(screen instanceof WInventoryScreen){
                jo.addProperty("type","WInventoryScreen");
                JsonArray array = new JsonArray();
                for (ResetInventorySlot slot:((WInventoryScreen) screen).getResetSlots()){
                    array.add(slot.toJson());
                }
                jo.add("resetInventorySlots",array);
            }else {
                jo.addProperty("type","WxScreen");
            }
            if(cursor!=null&&!cursor.getType().equals(Material.AIR)){
                jo.addProperty("cursor",itemToJson(cursor));
            }
        }
        JsonArray ja = new JsonArray();
        label:
        for (Component component: components){
            JsonElement je1 = new JsonObject();
            JsonObject jo1 = je1.getAsJsonObject();
            switch (component.getUpdateType()){
                case ADD:{
                    jo1.addProperty("updateType","ADD");
                    jo1.add("update",componentToJson(component));
                    break;
                }
                case REMOVE:{
                    jo1.addProperty("updateType","REMOVE");
                    jo1.addProperty("update",component.getId());
                    break;
                }
                case UPDATE:{
                    jo1.addProperty("updateType","UPDATE");
                    JsonElement je2 = componentToJson(component);
                    // 容器组件的组件会另外发包
                    if(component instanceof ContainerOwner){
                        je2.getAsJsonObject().remove("container");
                    }
                    jo1.add("update",je2);
                    break;
                }
                case NORMAL:{
                    continue label;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + component.getUpdateType());
            }
            ja.add(je1);
        }
        jo.add("value",ja);
        return je.toString();
    }


    public static JsonElement itemMapToJson(Map<?, ItemStack> itemStackMap){
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        for (Map.Entry<?, ItemStack> mp: itemStackMap.entrySet()){
            jo.addProperty(mp.getKey().toString(),itemToJson(mp.getValue()));
        }
        return je;
    }

    public static NBTTagCompound toNBT(ItemStack itemStack){
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        if(itemStack!=null) {
            CraftItemStack.asNMSCopy(itemStack).save(nbtTagCompound);
        } else {
            CraftItemStack.asNMSCopy(new ItemStack(Material.AIR)).save(nbtTagCompound);
        }
        return nbtTagCompound;
    }

    public static String itemToJson(ItemStack itemStack){
        return toNBT(itemStack).toString();
    }

    public static ItemStack jsonToItem(String json) throws MojangsonParseException {
        net.minecraft.server.v1_12_R1.ItemStack itemStack = new net.minecraft.server.v1_12_R1.ItemStack(MojangsonParser.parse(json));
        return CraftItemStack.asBukkitCopy(itemStack);
    }
}
