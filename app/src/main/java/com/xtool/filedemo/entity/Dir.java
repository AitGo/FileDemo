package com.xtool.filedemo.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xtool on 2017/11/28.
 */

public class Dir {

    public static String ROOTPATH = "null";
    //下一级的子dir
    private List<Dir> children = new ArrayList<>();
    //当前dir名称
    private String dirName;
    //层级
    private int level;
    //父dir
    private Dir parent;

    /**
     * 是否为跟节点
     *
     * @return
     */
    public boolean isRoot()
    {
        return parent == null;
    }

    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf()
    {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel()
    {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public List<Dir> getChildren() {
        return children;
    }

    public void setChildren(List<Dir> children) {
        this.children = children;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Dir getParent() {
        return parent;
    }

    public void setParent(Dir parent) {
        this.parent = parent;
    }
}
