package com.hechaodong.bookmanager.jframe;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import com.hechaodong.bookmanager.dao.UserDao;

import com.hechaodong.bookmanager.model.User;
import com.hechaodong.bookmanager.utils.DbUtil;
import com.hechaodong.bookmanager.utils.ToolUtil;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


// 用户注册界面
// 密码校验的正则表达式： ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$
// 手机号校验的正则表达式：^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$
public class RegFrm extends JFrame {

    private JFrame jf;                          //用户注册的窗体组件
    private JLabel label;                       //展示“用户名”的JLabel
    private JTextField textField;               //输入用户名所对应的文本框
    private JLabel label_1;                     // 展示“密码”的JLabel
    private JTextField textField_1;             //输入密码的文本框
    private JLabel label_2;                     //展示“手机号”的JLabel
    private JTextField textField_2;             //输入手机号所对应的文本框
    private JLabel label_3;                     //展示“性别”的JLabel

    private JRadioButton rdbtnNewRadioButton;   //性别男
    private JRadioButton rdbtnNewRadioButton_1; //性别女

    private JLabel usernameMes;                 //用户名校验结果对应的提示框
    private JLabel passwordMes;                 //密码校验结果对应的提示框
    private JLabel phoneMes;                    //手机号校验结果对应的提示框

    private JLabel label_4;                     //展示“验证码”的JLabel
    private JTextField textField_3;             //输入验证码所对应的文本框
    private ValidCode vcode;                    //展示验证码的图片框
    private JButton button;                     //注册按钮
    private JButton button_1;                   //前往登录按钮

    private JLabel lblNewLabel;                 // 背景图片
    private JLabel lblNewLabel_1;               // 用户注册

    // 创建UserDao对象
    private DbUtil dbUtil = new DbUtil();
    private UserDao userDao = new UserDao();

    public RegFrm() {
        // 初始化窗体
        jf = new JFrame("用户注册");
        jf.getContentPane().setFont(new Font("幼圆", Font.BOLD, 16));
        jf.setBounds(650, 250, 640, 960);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

        // 初始化展示“用户名”的JLabel
        label = new JLabel("用户名：");
        label.setForeground(Color.BLACK);
        label.setFont(new Font("幼圆", Font.BOLD, 24));
        label.setBounds(110, 202, 100, 40);
        jf.getContentPane().add(label);

        // 初始化用户名的文本框组件
        textField = new JTextField();
        textField.setFont(new Font("幼圆", Font.BOLD, 14));
        textField.setForeground(Color.BLACK);
        textField.setColumns(10);
        textField.setBounds(202, 202, 164, 30);
        jf.getContentPane().add(textField);

        // 添加焦点事件监听器，当失去焦点的时候需要对用户的用户名进行校验
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {        // 用户名校验
                String text = textField.getText();
                if (ToolUtil.isEmpty(text)) {
                    usernameMes.setText("用户名不能为空");
                    usernameMes.setForeground(Color.RED);
                } else {
                    usernameMes.setText("√");
                    usernameMes.setForeground(Color.GREEN);
                }
            }
        });

        // 初始化展示“密码”的JLabel
        label_1 = new JLabel("密码：");
        label_1.setForeground(Color.BLACK);
        label_1.setFont(new Font("幼圆", Font.BOLD, 24));
        label_1.setBounds(130, 252, 100, 40);
        jf.getContentPane().add(label_1);

        // 初始化密码的文本框组件
        textField_1 = new JTextField();
        textField_1.setFont(new Font("Dialog", Font.BOLD, 14));
        textField_1.setToolTipText("");
        textField_1.setColumns(10);
        textField_1.setBounds(202, 262, 164, 30);
        jf.getContentPane().add(textField_1);

        // 添加焦点事件监听器，当失去焦点的时候需要对用户的密码进行校验
        textField_1.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                String pwd = textField_1.getText();
                if (ToolUtil.isEmpty(pwd)) {
                    passwordMes.setText("密码不能为空");
                    passwordMes.setForeground(Color.RED);
                } else {
                    /* 正则表达式
                    * 1. ^(?! [0-9]+$)：以任意字符开头，后面不能全部是数字。
                    * 2. (?![a-zA-Z]+$)：后面不能全部是字母。
                    * 3. [0-9A-Za-z]{6,16}：包含6-16位数字和字母的组合。
                    * 4. $：以任意字符结尾。
                    * */
                    boolean flag = pwd.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
                    if (flag) {
                        passwordMes.setText("√");
                        passwordMes.setForeground(Color.GREEN);
                    } else {
                        JOptionPane.showMessageDialog(null, "密码需为6-16位数字和字母的组合");
                        passwordMes.setText("");
                    }
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
            }

        });

        // 初始化展示“手机号”的JLabel
        label_2 = new JLabel("手机号：");
        label_2.setForeground(Color.BLACK);
        label_2.setFont(new Font("幼圆", Font.BOLD, 24));
        label_2.setBounds(110, 302, 100, 60);
        jf.getContentPane().add(label_2);

        // 初始化手机号的文本框组件
        textField_2 = new JTextField();
        textField_2.setFont(new Font("Dialog", Font.BOLD, 14));
        textField_2.setColumns(10);
        textField_2.setBounds(202, 320, 164, 30);
        jf.getContentPane().add(textField_2);

        // 添加焦点事件监听器，当失去焦点的时候需要对用户的手机号进行校验
        textField_2.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                String phone = textField_2.getText();
                if (ToolUtil.isEmpty(phone)) {
                    phoneMes.setText("手机号不能为空");
                    phoneMes.setForeground(Color.RED);
                } else {
                    /*
                      1. ^：以任意字符开头。
                      2. ((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))：匹配手机号码的前三位，包括130-139、145、147、150-153、155-159、180、185-189等号段。
                      3. \d{8}：匹配手机号码后8位数字。
                      4. $：以任意字符结尾
                     */
                    boolean flag = phone.matches("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");
                    if (flag) {
                        phoneMes.setText("√");
                        phoneMes.setForeground(Color.GREEN);
                    } else {
                        JOptionPane.showMessageDialog(null, "请输入正确的手机号格式");
                        phoneMes.setText("");
                    }
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
            }

        });

        // 初始化展示“性别”的JLabel
        label_3 = new JLabel("性别：");
        label_3.setForeground(Color.BLACK);
        label_3.setFont(new Font("幼圆", Font.BOLD, 24));
        label_3.setBounds(132, 362, 120, 48);
        jf.getContentPane().add(label_3);

		// “男”按钮
        rdbtnNewRadioButton = new JRadioButton("男");
        rdbtnNewRadioButton.setFont(new Font("幼圆", Font.BOLD, 16));
        rdbtnNewRadioButton.setBounds(202, 377, 58, 23);
        jf.getContentPane().add(rdbtnNewRadioButton);

		// “女”按钮
        rdbtnNewRadioButton_1 = new JRadioButton("女");
        rdbtnNewRadioButton_1.setFont(new Font("幼圆", Font.BOLD, 16));
        rdbtnNewRadioButton_1.setBounds(292, 377, 65, 23);
        jf.getContentPane().add(rdbtnNewRadioButton_1);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rdbtnNewRadioButton_1);
        bg.add(rdbtnNewRadioButton);

        // 初始化”用户名“校验结果提示的JLabel
        usernameMes = new JLabel("");
        usernameMes.setFont(new Font("Dialog", Font.BOLD, 15));
        usernameMes.setBounds(372, 202, 122, 27);
        jf.getContentPane().add(usernameMes);

        // 初始化”密码“校验结果提示的JLabel
        passwordMes = new JLabel("");
        passwordMes.setFont(new Font("Dialog", Font.BOLD, 15));
        passwordMes.setBounds(372, 262, 122, 27);
        jf.getContentPane().add(passwordMes);

        // 初始化”手机号“校验结果提示的JLabel
        phoneMes = new JLabel("");
        phoneMes.setFont(new Font("Dialog", Font.BOLD, 15));
        phoneMes.setBounds(372, 324, 122, 30);
        jf.getContentPane().add(phoneMes);

        // 展示验证码的图片框
        vcode = new ValidCode();
        vcode.setLocation(362, 422);
        jf.getContentPane().add(vcode);

        // 初始化展示“验证码”的JLabel
        label_4 = new JLabel("验证码：");
        label_4.setForeground(Color.BLACK);
        label_4.setFont(new Font("幼圆", Font.BOLD, 24));
        label_4.setBounds(110, 420, 100, 40);
        jf.getContentPane().add(label_4);

        // 初始化输入验证码的文本框组件
        textField_3 = new JTextField();
        textField_3.setColumns(18);
        textField_3.setBounds(230, 426, 83, 30);
        jf.getContentPane().add(textField_3);

        // 初始化注册按钮
        button = new JButton("注册");
        // 给注册按钮添加一个动作监听器（校验验证码是否正确）
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // 获取验证码内容
                String code = textField_3.getText();
                if (ToolUtil.isEmpty(code)) {
                    JOptionPane.showMessageDialog(null, "请输入验证码");
                } else {
                    if (code.equalsIgnoreCase(vcode.getCode())) {
                        RegCheck(e);// 验证码校验成功
                    } else {
                        JOptionPane.showMessageDialog(null, "验证码错误，请重新输入");
                    }
                }
            }
        });
        button.setFont(new Font("幼圆", Font.BOLD, 15));
        button.setBounds(120, 620, 120, 48);
        jf.getContentPane().add(button);

        // 	初始化前往登录界面的按钮
        button_1 = new JButton("前往登录页面");

        // 给登录界面的按钮添加一个动作监听器按钮
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
                new LoginFrm();
            }
        });
        button_1.setFont(new Font("幼圆", Font.BOLD, 15));
        button_1.setBounds(380, 620, 132, 48);
        jf.getContentPane().add(button_1);


        // 初始化”用户注册的“的JLabel
        lblNewLabel_1 = new JLabel("用户注册");
        lblNewLabel_1.setFont(new Font("黑体", Font.BOLD, 33));
        lblNewLabel_1.setForeground(Color.RED); // 设置前景色为红色
        lblNewLabel_1.setBounds(214, 20, 200, 200);
        jf.getContentPane().add(lblNewLabel_1);

        // 初始化界面注册页面背景图片所需的JLabel
        lblNewLabel = new JLabel("");
        lblNewLabel.setForeground(Color.BLACK);
        ImageIcon imageIcon = new ImageIcon("lib/images/1.jpg");
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(640, 960, Image.SCALE_DEFAULT);
        imageIcon.setImage(image);
        lblNewLabel.setIcon(imageIcon);

        lblNewLabel.setBounds(0, 0, 640, 960);
        jf.getContentPane().add(lblNewLabel);

        // 显示页面
        jf.setVisible(true);
        // 是否可以更改jf的大小
        jf.setResizable(false);
    }

    // 完成用户注册
    public void RegCheck(ActionEvent e) {
        // 获取相关组件数据
        String username = textField.getText();
        String password = textField_1.getText();
        String phone = textField_2.getText();
        String sex = "";
        if (rdbtnNewRadioButton.isSelected()) {
            sex = rdbtnNewRadioButton.getText();
        } else {
            sex = rdbtnNewRadioButton_1.getText();
        }
        // 判断数据是否为空
        if (ToolUtil.isEmpty(username) || ToolUtil.isEmpty(password) || ToolUtil.isEmpty(phone)) {
            JOptionPane.showMessageDialog(null, "请输入相关信息");
            return;
        }

        // 把数据封装到User对象中
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setPhone(phone);
        user.setRole(1);

        // 获取一个连接对象
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            int i = userDao.addUser(con, user);// 使用获取的连接和传递进来的"user"对象进行注册。将注册结果存储在名为"i"的整型变量中
            if (i == 2) {
                JOptionPane.showMessageDialog(null, "该用户名已存在,请重新注册");
            } else if (i == 0) {
                JOptionPane.showMessageDialog(null, "注册失败");
            } else {
                JOptionPane.showMessageDialog(null, "注册成功");
                jf.dispose(); // 关闭窗口释放资源
                new LoginFrm();// 打开登录界面
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e1) {
                e1.printStackTrace();
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
        new RegFrm(); // 注册界面
    }
}
