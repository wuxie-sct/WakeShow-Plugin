package me.wuxie.wakeshow.wakeshow.ui;

import lombok.Getter;

import java.util.*;

public class Container {
    private Map<String,Component> componentMap;
    /**
     * 即将更新的组件
     */
    @Getter
    private Set<Component> updateList;
    /**
     * 容器的拥有者
     */
    @Getter
    private ContainerOwner owner;

    public Container(ContainerOwner owner){
        componentMap = new HashMap<>();
        this.owner = owner;
        updateList = new HashSet<>();
    }

    /**
     * 根据id获得组件
     * @param id ID
     * @return 容器内组件
     */
    public Component getComponent(String id){
        return componentMap.get(id);
    }

    /**
     * 获得所有组件
     * @return 组件map
     */
    public Map<String, Component> getComponentMap() {
        Map<String, Component> componentMap = new HashMap<>();
        for (Map.Entry<String,Component> m: this.componentMap.entrySet()){
            componentMap.put(m.getKey(),m.getValue());
        }
        return componentMap;
    }

    /**
     * 删除某个组件
     * 在WuxieAPI执行update后客户端生效
     * @param component 组件
     */
    public void remove(Component component){
        if(component!=null&&component.getParent().equals(this)){
            componentMap.remove(component.getId());
            // 如果gui处于打开状态
            if(getOwner()!=null&&getOwner().getScreen()!=null&&getOwner().getScreen().isOpened()) {
                component.setUpdateType(Component.UpdateType.REMOVE);
                updateList.add(component);
                ContainerOwner owner = getOwner();
                if(owner instanceof Component && ((Component) owner).getUpdateType().equals(Component.UpdateType.NORMAL)){
                    ((Component)getOwner()).beforeUpdate();
                }
            }
        }
    }
    /**
     * 删除某个组件
     * 在WuxieAPI执行update后客户端生效
     * @param component ID
     */
    public void remove(String component){
        Component component1 = componentMap.get(component);
        if(component1!=null){
            componentMap.remove(component);
            // 如果gui处于打开状态
            if(getOwner()!=null&&getOwner().getScreen()!=null&&getOwner().getScreen().isOpened()) {
                component1.setUpdateType(Component.UpdateType.REMOVE);
                updateList.add(component1);
                ContainerOwner owner = getOwner();
                if(owner instanceof Component && ((Component) owner).getUpdateType().equals(Component.UpdateType.NORMAL)){
                    ((Component)getOwner()).beforeUpdate();
                }
            }
        }
    }

    /**
     * 仅在gui打开状态有效
     * 设置组件在下次执行screenUpdate时对客户端更新
     * @param component 组件
     */
    void setUpdate(Component component){
        // 如果gui处于打开状态
        if(getOwner()!=null&&getOwner().getScreen()!=null&&getOwner().getScreen().isOpened()) {
            if (component != null && component.getParent().equals(this)&&
                    componentMap.containsKey(component.getId())) {
                component.setUpdateType(Component.UpdateType.UPDATE);
                updateList.add(component);
            }
        }
    }

    void setUpdate(String component){
        // 如果gui处于打开状态
        if(getOwner()!=null&&getOwner().getScreen()!=null&&getOwner().getScreen().isOpened()) {
            Component component1 = componentMap.get(component);
            if (component1 != null) {
                component1.setUpdateType(Component.UpdateType.UPDATE);
                updateList.add(component1);
            }
        }
    }
    /**
     * 添加组件
     * 在WuxieAPI执行update后客户端生效
     * @param component 组件
     */
    public void add(Component component){
        String id = component.getId();
        if(!componentMap.containsKey(id)){
            componentMap.put(id,component);
            component.setParent(this);
            component.added = true;
            // 如果gui处于打开状态
            if(getOwner() != null && getOwner().getScreen()!=null) {
                if(getOwner().getScreen().isOpened()) {
                    component.setUpdateType(Component.UpdateType.ADD);
                    updateList.add(component);
                    ContainerOwner owner = getOwner();
                    if (owner instanceof Component &&
                            ((Component) owner).getUpdateType().equals(Component.UpdateType.NORMAL)) {
                        ((Component) getOwner()).beforeUpdate();
                    }
                }
            }
        }
    }

    /**
     * 一般不要调用，除非你真的知道你在干嘛
     * 发送更新之后
     * */
    public void afterUpdate(){
        for (Component c: updateList) {
            c.afterUpdate();
        }
        updateList.clear();
    }
}
