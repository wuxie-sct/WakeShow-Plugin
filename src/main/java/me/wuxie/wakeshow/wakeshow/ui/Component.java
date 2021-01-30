package me.wuxie.wakeshow.wakeshow.ui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPostClickComponentEvent;
import me.wuxie.wakeshow.wakeshow.api.event.PlayerPreClickComponentEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
/**
 * GUI组件抽象类
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public abstract class Component {
    /**
     * 是否已被添加进容器
     */
    boolean added = false;
    /**
     * 一般不需要开发者手动set
     * 更新状态 (在下次调用update时，在客户端和服务器执行相应的操作)
    * */
    @Getter
    @Setter
    private UpdateType updateType = UpdateType.NORMAL;
    /**
     * 唯一ID
     */
    @Getter
    private String id;
    /**
     * 父容器，存放该组件的容器
     */
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private Container parent;
    /**
     * x 坐标
     */
    @Getter protected int x;
    /**
     * y 坐标
     */
    @Getter protected int y;
    /**
     * 宽度
     */
    @Getter protected int w;
    /**
     * 高度
     */
    @Getter protected int h;
    /**
     * z 坐标
     * 优先级排序
     * 越大越在前面
     * 默认值100
     */
    @Getter protected int z=100;

    /**
     * 点击这个组件后执行的语句
     */
    @Getter
    @Setter
    protected ClickFunction function = null;

    /**
     * 是否跟随容器偏移(仅针对界面(WxScreen)的Container容器)
     * 组件的xy轴以界面(WxScreen)的xy轴为基础/还是以屏幕为基础
     */
    @Getter
    private boolean followOffset = true;
    /**
     * 悬停文本*/
    @Getter
    private List<String> tooltips = null;
    public Component(Container parent, String id, int x, int y){
        if(parent ==null) {
            throw new NullPointerException("Parent container can not be null");
        }
        this.id = id;
        this.parent = parent;
        this.x = x;
        this.y = y;
    }

    /**
     * 一般不要调用，除非你真的知道你在干嘛
     * 更新前
     * 设置完属性后，需要执行beforeUpdate
     */
    protected void beforeUpdate(){
        if(getScreen().isOpened()&&added) {
            parent.setUpdate(this);
            ContainerOwner owner = parent.getOwner();
            if (owner instanceof Component) {
                ((Component) owner).beforeUpdate();
            }
        }
    }

    /**
     * 一般不要调用，除非你真的知道你在干嘛
     * 删除前
     * */
    public void beforeRemove(){
        parent.remove(this);
    }

    /**
     * 一般不要调用，除非你真的知道你在干嘛
     * 更新后(发送更新后)
     * */
    protected void afterUpdate(){
        updateType = UpdateType.NORMAL;
    }

    /**
     * @return 获得这个组件的界面实例
     * */
    public WxScreen getScreen() {
        ContainerOwner owner = getParent().getOwner();
        while (owner instanceof Component){
            owner = ((Component) owner).getParent().getOwner();
        }
        return (WxScreen) owner;
    }

    public void setX(int x) {
        this.x = x;
        beforeUpdate();
    }

    public void setY(int y) {
        this.y = y;
        beforeUpdate();
    }

    public void setW(int w) {
        this.w = w;
        beforeUpdate();
    }

    public void setH(int h) {
        this.h = h;
        beforeUpdate();
    }

    /**
     * 显示等级(zLevel)
     * 数值越大，它将在越靠前显示
     * @param z zLevel
     */
    public void setZ(int z) {
        this.z = z;
        beforeUpdate();
        if(getScreen().getMaxZ()<z){
            getScreen().setMaxZ(z);
        }
    }

    public void setTooltips(List<String> tooltips) {
        this.tooltips = tooltips;
        beforeUpdate();
    }

    public void setFollowOffset(boolean followOffset) {
        this.followOffset = followOffset;
        beforeUpdate();
    }

    /**
     * 当被点击时
     * @param mouseButtonId 0 左键 1 右键 2 中键
     * @param player 点击的玩家
     */
    public void onClick(int mouseButtonId, Player player){
        PlayerPreClickComponentEvent playerClickComponentEvent = new PlayerPreClickComponentEvent(player,getScreen(),this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerClickComponentEvent);
        if(playerClickComponentEvent.isCancelled()) {
            return;
        }
        if(function!=null) {
            function.run(mouseButtonId, player);
        }
        PlayerPostClickComponentEvent playerPostClickComponentEvent = new PlayerPostClickComponentEvent(player,getScreen(),this,mouseButtonId);
        Bukkit.getPluginManager().callEvent(playerPostClickComponentEvent);
    }

    /**
     * 组件路径 界面.容器…组件
     * @return 界面.容器…组件
     */
    public String getPath(){
        return getParent().getOwner().getPath()+"."+id;
    }
    public enum UpdateType{
        /**
         * 添加
         */
        ADD,
        /**
         * 删除
         */
        REMOVE,
        UPDATE,
        /**
         * 平常
         */
        NORMAL;
    }
}
