package me.wuxie.wakeshow.wakeshow.ui.component;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;

import java.util.ArrayList;
import java.util.List;
/**
 * 文本列表
 *
 * @date 2020/11/13
 * @author  wuxie
 * @version 1.6.0
 */
public class WTextList extends Component {
    /**
     * 文本列表
     */
    @Getter
    private List<String> content;
    /**
     * 字体大小
     */
    @Getter
    private double scale = 1.0d;

    /**
     * 构造器
     * @param parent 父容器
     * @param id id
     * @param content 文本列表
     * @param x x
     * @param y y
     * @param w w
     * @param h h
     */
    public WTextList(Container parent, String id, List<String> content,int x, int y,int w,int h) {
        super(parent, id, x, y);
        this.w = w;
        this.h = h;
        this.content = content;
    }

    public void setContent(List<String> content) {
        content=content==null?new ArrayList<>():content;
        this.content = content;
        beforeUpdate();
    }

    public void setScale(double scale) {
        this.scale = scale;
        beforeUpdate();
    }
}
