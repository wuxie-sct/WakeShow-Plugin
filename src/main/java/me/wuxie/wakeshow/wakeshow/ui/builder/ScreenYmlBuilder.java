package me.wuxie.wakeshow.wakeshow.ui.builder;

import lombok.Getter;
import me.wuxie.wakeshow.wakeshow.ui.WxScreen;
import org.bukkit.configuration.ConfigurationSection;
/**
 *  配置文件UI构造器
 *  未完成
 */
public class ScreenYmlBuilder {
    @Getter
    private ConfigurationSection section;
    public ScreenYmlBuilder(ConfigurationSection section){
        this.section = section;
    }
    public WxScreen builder(){
        return null;
    }
}
