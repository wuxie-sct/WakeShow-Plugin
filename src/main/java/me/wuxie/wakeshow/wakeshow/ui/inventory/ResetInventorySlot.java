package me.wuxie.wakeshow.wakeshow.ui.inventory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * 重置背包槽位属性
 */
public class ResetInventorySlot{
    /**
     * index - 槽位id
     */
    public int index;
    /** x，y - 在gui的坐标,-999为不改变坐标位置 */
    public int x=-999;
    /** x，y - 在gui的坐标,-999为不改变坐标位置 */
    public int y=-999;
    /** 大小 */
    public double scale = 1.0D;
    /** 空槽位背景 */
    public String background = null;
    /** 空槽位tip */
    public List<String> emptyTooltips = null;
    /** 能否拖拽 */
    public boolean canDrag = true;

    public JsonElement toJson(){
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("index",index);
        if(x!=-999){
            jo.addProperty("x",x);
        }
        if(y!=-999){
            jo.addProperty("y",y);
        }
        if(scale!=1.0D){
            jo.addProperty("scale",scale);
        }
        if(background!=null) {
            jo.addProperty("background", background);
        }
        if(emptyTooltips!=null){
            jo.add("emptyTooltips",new Gson().toJsonTree(emptyTooltips));
        }
        jo.addProperty("canDrag",canDrag);
        return je;
    }
}
