package me.wuxie.wakeshow.wakeshow.ui.animation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
/**
 * 图片动画节点列表
 * 可以使一个图像按不同的节点动态改变
 *
 * @date 2020/11/11
 * @author  wuxie
 * @version 1.6.0
 */
public class ImageAnimationNodeList {
    /**
     * 节点列表
     */
    public List<ImageAnimationNode> animationList = new ArrayList<>();
    /**
     * 停止在结束状态多tick，回转时等待的tick，针对图片
     * 20tick = 1s
     */
    public int backDelayFrame = 0;
    /**
     * 返回时返回到的节点
     */
    public int backTo = 0;
    /**
     * 旋转开始的角度(累计值)
     */
    public float rotate = 0.0f;
    /**
     * 结束时 0 回旋，1无限旋转，2帧播放结束停止旋转
     */
    public int rotateModel = 2;
    /**
     * 开始的透明度(累计值)
     */
    public float alpha = 1.0f;
    /**
     * 结束时透明度回调(否，播放完停止透明度变化，是，播放完，等待backDelayFrame帧，倒回去)
     */
    public boolean alphaBack = false;
    /**
     * 开始的大小(小于0时为0)(累计值)
     * 小于1，小放大，大于1，大缩小
     */
    public float scale = 1.0f;
    /**
     * 结束时大小回调(否，播放完停止大小变化，是，播放完，等待backDelayFrame帧，倒回去)
     */
    public boolean scaleBack = false;
    /**
     * 是否返回
     */
    public boolean XYBack = false;

    public JsonElement toJsonElement(){
        JsonElement je = new JsonObject();
        JsonObject jo = je.getAsJsonObject();
        jo.addProperty("backDelayFrame",backDelayFrame);
        jo.addProperty("backTo",backTo);
        jo.addProperty("rotate",rotate);
        jo.addProperty("rotateModel",rotateModel);
        jo.addProperty("alpha",alpha);
        jo.addProperty("alphaBack",alphaBack);
        jo.addProperty("scale",scale);
        jo.addProperty("scaleBack",scaleBack);
        jo.addProperty("XYBack",XYBack);
        List<String> animationNodes = new ArrayList<>();
        for (ImageAnimationNode node:animationList){
            animationNodes.add(node.toString());
        }
        jo.add("animationNodes", new Gson().toJsonTree(animationNodes,new TypeToken<List<String>>(){}.getType()));
        return je;
    }
    @Override
    public String toString() {
        return toJsonElement().toString();

    }
}
