package com.pbb.frame;

import com.pbb.hamming.Hamming;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


public class MainJFrame extends JFrame{
    private JPanel mainPanel = new JPanel(new BorderLayout());

    static boolean curMode;
    static JMenu modelMenu;
    static JTextField input;
    static JTextArea result;

    public MainJFrame() throws HeadlessException {
        super ("海明码的编码与解码");

        initGUI();

        setSize(300, 305);
    }


    private void initGUI() {
        placeComponents(mainPanel);
        this.getContentPane().add(mainPanel);
        this.setJMenuBar(Meun.createMenuBar());
    }

    /*
    设置主界面
     */
    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        input = new JTextField(20);
        input.setBounds(25, 20, 240, 25);
        panel.add(input);

        result = new JTextArea();
        result.setBounds(25, 120, 240, 115);
        panel.add(result);

        JButton btn = new JButton();
        btn.setBounds(95, 60, 90, 50);

        btn.setIcon(new ImageIcon(MainJFrame.class.getResource("/imgs/button_normal.png")));
//        btn.setIcon(new ImageIcon("src/main/java/com/pbb/frame/img/button_normal.png"));
        btn.setPressedIcon(new ImageIcon(MainJFrame.class.getResource("/imgs/button_press.png")));
//        btn.setPressedIcon(new ImageIcon("src/main/java/com/pbb/frame/img/button_press.png"));


        btn.setBorderPainted(false);

        btn.addActionListener((e) -> {
                System.out.println("按钮被点击了");
                try {
                    if (!Hamming.check(input.getText()))
                        result.setText("输入有误请重新输入");
                    else if (curMode)
                        result.setText(Hamming.showDecode(input.getText()));
                    else result.setText(Hamming.encode(input.getText()));
                } catch (Exception ex) {
                    result.setText("输入有误请重新输入");
                }

        });

        panel.add(btn);
    }
}