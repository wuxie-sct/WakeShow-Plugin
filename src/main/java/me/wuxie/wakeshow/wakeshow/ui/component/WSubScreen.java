package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import lombok.Setter;
import me.wuxie.wakeshow.wakeshow.api.WuxieAPI;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.ContainerOwner;
import me.wuxie.wakeshow.wakeshow.ui.animation.ScreenAnimation;
/**
 * 子界面
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WSubScreen extends Component implements ContainerOwner {
    /**
     * 该子界面的容器
     */
    @Getter
    private final Container container;
    /**
     * 打开动画
     */
    @Getter
    @Setter
    private ScreenAnimation animation = new ScreenAnimation();
    /**
     * 背景
     */
    @Getter
    private String background;
    /** 打开时，以鼠标的当前点为偏移顶点
     * 0,0 则正好在鼠标点
     * */
    @Getter
    @Setter
    private boolean followMouse;

    /**
     * 构造器
     * @param parent 父容器
     * @param id ID
     * @param background 背景
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    public WSubScreen(Container parent, String id,String background, int x, int y,int w,int h) {
        super(parent, id, x, y);
        container = new Container(this);
        this.w = w;
        this.h = h;
        this.background = background;
    }

    public void setBackground(String background) {
        this.background = background;
        beforeUpdate();
    }
    // 关闭
    public void close(){
        beforeRemove();
        if(getScreen().isOpened()) {
            WuxieAPI.getOpenedGui(getScreen().getPlayer()).update();
        }
    }
    // 打开
    public void open(){
        if(!getParent().getComponentMap().containsValue(this)){
            getParent().add(this);
        }
        if (getScreen().isOpened()) {
            WuxieAPI.getOpenedGui(getScreen().getPlayer()).update();
        }
    }
    /**
     * 为子界面内的组件设置一个点击关闭子界面的功能
     * @param id 子界面内的组件id
     */
    public void setClickCloseComponent(String id){
        Component component = container.getComponentMap().get(id);
        if(component!=null) {
            component.setFunction((mouseId,player)-> close());
        }
    }
}
