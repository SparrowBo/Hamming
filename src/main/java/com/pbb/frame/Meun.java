package com.pbb.frame;

import javax.swing.*;

public class Meun {

    public static JMenuBar createMenuBar() {
        /*
         * 创建一个菜单栏
         */
        JMenuBar menuBar = new JMenuBar();

        /*
         * 创建一级菜单
         */
        MainJFrame.modelMenu = new JMenu("当前模式：编码");
        JMenu editMenu = new JMenu("编辑");
        JMenu aboutMenu = new JMenu("关于");
        // 一级菜单添加到菜单栏
        menuBar.add(MainJFrame.modelMenu);

        menuBar.add(editMenu);
        menuBar.add(aboutMenu);

        /*
         * 创建 "当前模式" 一级菜单的子菜单
         */
        JMenuItem encodeItem = new JMenuItem("编码");
        JMenuItem decodeItem = new JMenuItem("解码");
        JMenuItem exitMenuItem = new JMenuItem("退出");
        // 子菜单添加到一级菜单
        MainJFrame.modelMenu.add(encodeItem);
        MainJFrame.modelMenu.add(decodeItem);
        MainJFrame.modelMenu.addSeparator();       // 添加一条分割线
        MainJFrame.modelMenu.add(exitMenuItem);

        /*
         * 创建 "编辑" 一级菜单的子菜单
         */
        JMenuItem copyMenuItem = new JMenuItem("复制");
        JMenuItem pasteMenuItem = new JMenuItem("粘贴");
        // 子菜单添加到一级菜单
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        JLabel ecnu082 = new JLabel(" 71205902082 詹浩铎 ");
        JLabel ecnu112 = new JLabel(" 71205902112 潘波波 ");
        JLabel ecnu114 = new JLabel(" 71205902114 陈栩 ");
        JLabel ecnu115 = new JLabel(" 71205902115 夏雨生 ");
        aboutMenu.add(ecnu082);
        aboutMenu.add(ecnu112);
        aboutMenu.add(ecnu114);
        aboutMenu.add(ecnu115);

//        aboutMenu.add("by: \n71205902082 詹浩铎\n71205902112 潘波波\n71205902114 陈栩\n71205902115 夏雨生");
        /*
         * 菜单项的点击/状态改变事件监听，事件监听可以直接设置在具体的子菜单上（这里只设置其中几个）
         */
        // 设置 "编码" 子菜单被点击的监听器
        encodeItem.addActionListener((e) -> {
            System.out.println("encode  被点击");
            MainJFrame.curMode = false;
            MainJFrame.modelMenu.setText("当前模式：编码");
        });
        // 设置 "解码" 子菜单被点击的监听器
        decodeItem.addActionListener((e) -> {
            System.out.println("decode  被点击");
            MainJFrame.curMode = true;
            MainJFrame.modelMenu.setText("当前模式：解码");
        });
        // 设置 "退出" 子菜单被点击的监听器
        exitMenuItem.addActionListener((e) -> {
            System.out.println("退出  被点击");
            System.exit(1);
        });
        // 设置 "复制" 子菜单被点击的监听器
        copyMenuItem.addActionListener((e) -> {
            System.out.println("复制  被点击");
            Util.setClipboardString(MainJFrame.result.getText());
        });

        // 设置 "粘贴" 子菜单被点击的监听器
        pasteMenuItem.addActionListener((e) -> {
            System.out.println("粘贴  被点击");
            MainJFrame.input.setText(Util.getClipboardString());
        });
        return menuBar;
    }
}
