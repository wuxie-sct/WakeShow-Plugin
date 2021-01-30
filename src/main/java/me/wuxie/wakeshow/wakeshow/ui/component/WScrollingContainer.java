package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.ContainerOwner;
/**
 * 滚动条
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WScrollingContainer extends Component implements ContainerOwner {
    /** 该滚动条的组件容器 */
    @Getter private final Container container;
    /** 滚动条背景 */
    @Getter private String background ;
    /** 滚轮贴图 */
    @Getter private String scrollBar;
    /** 滚轮背景贴图 */
    @Getter private String scrollBarBack;
    /** 滚动条深度 */
    @Getter private int scrollHeight;
    /**
     * 滚轮和滚轮背景宽度
     * -1 跟随系统默认宽度
     * */
    @Getter
    private int barWidth = -1;
    /**
     * 是否显示滚轮
     */
    @Getter
    private boolean showScrollBar = true;

    /**
     * 构造器
     * @param parent 父容器
     * @param id ID
     * @param x x
     * @param y y
     * @param w w
     * @param h 高度
     * @param sh 深度
     */
    public WScrollingContainer(Container parent, String id, int x, int y,int w,int h,int sh) {
        super(parent, id, x, y);
        container = new Container(this);
        this.w = w;
        this.h = h;
        this.scrollHeight = sh;
    }

    /**
     * 构造器
     * @param parent 父容器
     * @param id ID
     * @param background 滚动条背景
     * @param scrollBar 滚轮图片
     * @param scrollBarBack 滚轮背景图片
     * @param x x
     * @param y y
     * @param w w
     * @param h 高度
     * @param sh 深度
     */
    public WScrollingContainer(Container parent, String id,String background,String scrollBar,String scrollBarBack,
                               int x, int y,int w,int h,int sh) {
        super(parent, id, x, y);
        container = new Container(this);
        this.background = background;
        this.scrollBar = scrollBar;
        this.scrollBarBack = scrollBarBack;
        this.w = w;
        this.h = h;
        this.scrollHeight = sh;
    }

    public void setBackground(String background) {
        this.background = background;
        beforeUpdate();
    }

    public void setScrollBar(String scrollBar) {
        this.scrollBar = scrollBar;
        beforeUpdate();
    }

    public void setScrollBarBack(String scrollBarBack) {
        this.scrollBarBack = scrollBarBack;
        beforeUpdate();
    }

    public void setScrollHeight(int scrollHeight) {
        this.scrollHeight = scrollHeight;
        beforeUpdate();
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        beforeUpdate();
    }

    public void setShowScrollBar(boolean showScrollBar) {
        this.showScrollBar = showScrollBar;
        beforeUpdate();
    }
}
