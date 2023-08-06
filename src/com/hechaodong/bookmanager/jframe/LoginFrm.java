package com.hechaodong.bookmanager.jframe;

import java.awt.*;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.hechaodong.bookmanager.dao.UserDao;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import com.hechaodong.bookmanager.model.User;
import com.hechaodong.bookmanager.utils.DbUtil;
import com.hechaodong.bookmanager.utils.ToolUtil;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// 用户登录界面
public class LoginFrm extends JFrame {

    public static User currentUser;             // 当登录成功后，使用该变量存储登录的用户
    private JFrame jf;                          // 登录界面的窗体结构
    private JTextField userNameText;            // 输入用户名的文本框
    private JTextField passwordText;            // 输入密码的文本框
    private JComboBox<String> comboBox;         // 用户选项的下拉选择框

    private UserDao userDao = new UserDao();
    private DbUtil dbUtil = new DbUtil();

    public LoginFrm() {
        // 初始化窗体组件
        jf = new JFrame("图书管理");
        jf.getContentPane().setFont(new Font("幼圆", Font.BOLD, 14));
        jf.setBounds(650, 250, 640, 960);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

        // 初始化登录界面的图片
        JLabel lblNewLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("lib/images/2.jpg");
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(280, 168, Image.SCALE_DEFAULT);
        imageIcon.setImage(image);
        lblNewLabel.setIcon(imageIcon);

        lblNewLabel.setBounds(180, 72, 430, 218);
        jf.getContentPane().add(lblNewLabel);

        // 初始化展示“用户名”的JLabel
        JLabel label = new JLabel("用户名：");
        label.setFont(new Font("黑体", Font.BOLD, 24));
        label.setBounds(150, 292, 100, 100);
        jf.getContentPane().add(label);

        // 初始化用户名的文本框组件
        userNameText = new JTextField();
        userNameText.setBounds(250, 322, 142, 40);
        jf.getContentPane().add(userNameText);
        userNameText.setColumns(10);

        // 初始化展示“密码”的JLabel
        JLabel label_1 = new JLabel("密码：");
        label_1.setFont(new Font("黑体", Font.BOLD, 24));
        label_1.setBounds(170, 362, 100, 100);
        jf.getContentPane().add(label_1);

        // 初始化密码的文本框组件
        passwordText = new JPasswordField();
        passwordText.setColumns(10);
        passwordText.setBounds(250, 392, 142, 40);
        jf.getContentPane().add(passwordText);

        // 初始化展示“权限”的JLabel
        JLabel label_2 = new JLabel("权限：");
        label_2.setFont(new Font("黑体", Font.BOLD, 24));
        label_2.setBounds(172, 442, 80, 80);
        jf.getContentPane().add(label_2);

        // 初始权限的文本框组件
        comboBox = new JComboBox();
        comboBox.setBounds(250, 462, 142, 40);
        comboBox.addItem("用户");
        comboBox.addItem("管理员");
        jf.getContentPane().add(comboBox); // 用户-----管理员

        // 登录按钮
        JButton button = new JButton("登录");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkLogin(e); // 检查登录（获取数据比对数据库信息）
            }
        });
        button.setBounds(150, 650, 120, 48);
        jf.getContentPane().add(button);

        // 注册按钮
        JButton button_1 = new JButton("注册");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regUser(e); // 跳转到注册界面
            }
        });
        button_1.setBounds(350, 650, 120, 48);
        jf.getContentPane().add(button_1);

        // 登录界面背景图
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("lib/images/3.jpg"));
        lblNewLabel_1.setBounds(0, 0, 640, 940);
        jf.getContentPane().add(lblNewLabel_1);


        // 显示界面，不可以调整界面大小
        jf.setVisible(true);
        jf.setResizable(false);

    }

    // 跳转到注册页面
    public void regUser(ActionEvent e) {
        jf.setVisible(false);
        new RegFrm();

    }

    // 用户登录的逻辑操作（获取数据并校对数据库数据是否一致）
    public void checkLogin(ActionEvent e) {
        // 获取用户登录信息
        String userName = userNameText.getText();
        String password = passwordText.getText();
        int index = comboBox.getSelectedIndex(); // 权限
        if (ToolUtil.isEmpty(userName) || ToolUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
            return;
        }

        // 把数据封装到User对象中
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        if (index == 0) {
            user.setRole(1);// 用户
        } else {
            user.setRole(2);// 管理员
        }

        // 获取一个连接对象
        Connection con = null;
        try {
            con = dbUtil.getConnection(); // 获取数据库连接
            User login = userDao.login(con, user);// 使用获取的连接和传递进来的"user"对象进行登录验证
            currentUser = login;
            if (login == null) {
                JOptionPane.showMessageDialog(null, "登录失败");
            } else {
                // 权限 1普通 2管理员
                if (index == 0) {
                    jf.dispose(); // 释放与当前窗口关联的所有资源
                    new UserMenuFrm(); // 用户界面
                } else {
                    jf.dispose();// 释放与当前窗口关联的所有资源
                    new AdminMenuFrm(); // 管理员界面
                }
            }
        } catch (Exception e21) {
            e21.printStackTrace();
            JOptionPane.showMessageDialog(null, "登录异常");
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e31) {
                e31.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new LoginFrm();
    }
}
