package me.wuxie.wakeshow.wakeshow.ui.builder;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.Component;
import me.wuxie.wakeshow.wakeshow.ui.Container;
import org.bukkit.configuration.ConfigurationSection;

/**
 *  配置文件组件构造器
 *  未完成
 */
public abstract class ComponentYmlBuilder {
    @Getter
    private Container container;
    @Getter
    private ConfigurationSection section;
    public ComponentYmlBuilder(Container container, ConfigurationSection section){
        this.container = container;
        this.section = section;
    }

    public abstract Component builder();

    public Component builderAddToContainer(){
        Component component = builder();
        container.add(component);
        return component;
    }
}
