package me.wuxie.wakeshow.wakeshow.ui;

import org.bukkit.entity.Player;

/**
 * 点击组件后执行的方法
 */
@FunctionalInterface
public interface ClickFunction {
    void run(int buttonId,Player player);
}