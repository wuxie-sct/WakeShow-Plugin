package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import me.wuxie.wakeshow.wakeshow.ui.animation.ImageAnimation;
import me.wuxie.wakeshow.wakeshow.ui.animation.ImageAnimationNodeList;
/**
 * 图片
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WImage extends Component {
    /**
     * 图片路径
     * 以‘file:’开头为获取本地图片，从客户端的'wakeshow'文件夹寻找
     * 以网络链接的形式为获取网络图片
     * 以‘location:’开头然后键入标准的资源包路径为获取资源包图片
     * */
    @Getter
    private String url1;
    /** 图片单节点动画，单节点动画和多节点动画不共存，优先多节点 */
    @Getter
    private ImageAnimation imageAnimation;
    /** 图片多节点动画，单节点动画和多节点动画不共存，优先多节点 */
    @Getter
    private ImageAnimationNodeList animationNodeList;

    /**
     * 构造器
     * @param parent 父容器
     * @param id ID
     * @param url1 路径
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    public WImage(Container parent, String id,String url1, int x, int y,int w,int h) {
        super(parent, id, x, y);
        this.w = w;
        this.h = h;
        this.url1 = url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
        beforeUpdate();
    }
    public void setImageAnimation(ImageAnimation imageAnimation){
        this.imageAnimation = imageAnimation;
    }

    public void setAnimationNodeList(ImageAnimationNodeList animationNodeList) {
        this.animationNodeList = animationNodeList;
        beforeUpdate();
    }
}
