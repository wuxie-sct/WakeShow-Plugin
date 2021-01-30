package me.wuxie.wakeshow.wakeshow.ui;

/**
 * 代表实现该接口的对象是一个容器的拥有者
 */
public interface ContainerOwner {
    String getId();
    Container getContainer();
    WxScreen getScreen();
    String getPath();
}
