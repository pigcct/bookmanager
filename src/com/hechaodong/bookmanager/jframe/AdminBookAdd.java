package com.hechaodong.bookmanager.jframe;

import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.hechaodong.bookmanager.dao.BookTypeDao;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.hechaodong.bookmanager.dao.BookDao;
import com.hechaodong.bookmanager.model.Book;
import com.hechaodong.bookmanager.model.BookType;
import com.hechaodong.bookmanager.utils.DbUtil;
import com.hechaodong.bookmanager.utils.ToolUtil;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;


public class AdminBookAdd extends JFrame {

    private JFrame jf;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_6;
    private JComboBox comboBox;
    BookDao bookDao = new BookDao();
    DbUtil dbUtil = new DbUtil();
    BookTypeDao bookTypeDao = new BookTypeDao();

    public AdminBookAdd() {
        // 初始化书籍添加界面
        jf = new JFrame("书籍添加");
        jf.setBounds(650, 250, 600, 378);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

        // 顶部展示“书籍添加”的JLabel
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "书籍添加", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel.setBounds(23, 21, 540, 275);
        jf.getContentPane().add(panel);
        panel.setLayout(null);

        // 初始化展示“书名”的JLabel
        JLabel lblNewLabel = new JLabel("书名：");
        lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 14));
        lblNewLabel.setBounds(58, 31, 45, 27);
        panel.add(lblNewLabel);

        // 初始化输入书名的文本框组件
        textField = new JTextField();
        textField.setBounds(101, 31, 129, 27);
        panel.add(textField);
        textField.setColumns(10);

        // 初始化展示“作者”的JLabel
        JLabel label = new JLabel("作者：");
        label.setFont(new Font("幼圆", Font.BOLD, 14));
        label.setBounds(294, 31, 45, 27);
        panel.add(label);

        // 初始化输入作者的文本框组件
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(338, 31, 128, 27);
        panel.add(textField_1);

        // 初始化展示“出版社”的JLabel
        JLabel label_1 = new JLabel("出版社：");
        label_1.setFont(new Font("幼圆", Font.BOLD, 14));
        label_1.setBounds(43, 79, 60, 27);
        panel.add(label_1);

        // 初始化输入出版社的文本框组件
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(101, 79, 129, 27);
        panel.add(textField_2);


        // 初始化展示“库存”的JLabel
        JLabel label_2 = new JLabel("库存：");
        label_2.setFont(new Font("幼圆", Font.BOLD, 14));
        label_2.setBounds(58, 125, 45, 27);
        panel.add(label_2);

        // 初始化输入库存的文本框组件
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(101, 125, 129, 27);
        panel.add(textField_3);

        // 初始化展示“价格”的JLabel
        JLabel label_3 = new JLabel("价格：");
        label_3.setFont(new Font("幼圆", Font.BOLD, 14));
        label_3.setBounds(294, 79, 45, 27);
        panel.add(label_3);

        // 初始化输入价格的文本框组件
        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(337, 79, 129, 27);
        panel.add(textField_4);

        // 初始化展示“类别”的JLabel
        JLabel label_4 = new JLabel("类别：");
        label_4.setFont(new Font("幼圆", Font.BOLD, 14));
        label_4.setBounds(294, 125, 45, 27);
        panel.add(label_4);

        // 初始化展示“描述”的JLabel
        JLabel label_5 = new JLabel("描述：");
        label_5.setFont(new Font("幼圆", Font.BOLD, 14));
        label_5.setBounds(58, 170, 45, 27);
        panel.add(label_5);

        // 初始化输入描述的文本框组件
        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(101, 173, 365, 27);
        panel.add(textField_6);

        // 添加按钮
        JButton btnNewButton = new JButton("添加");
        // 给添加按钮添加一个动作监听器
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookName = textField.getText();
                String author = textField_1.getText();
                String publish = textField_2.getText();
                String priceStr = textField_4.getText();
                String numberStr = textField_3.getText();
                String remark = textField_6.getText();
                if (ToolUtil.isEmpty(bookName) || ToolUtil.isEmpty(author)
                        || ToolUtil.isEmpty(publish) || ToolUtil.isEmpty(priceStr)
                        || ToolUtil.isEmpty(numberStr) || ToolUtil.isEmpty(remark)) {
                    JOptionPane.showMessageDialog(null, "请输入相关内容");
                    return;
                }
                BookType selectedItem = (BookType) comboBox.getSelectedItem();
                Integer typeId = selectedItem.getTypeId();
                int number;
                double price;
                try {
                    number = Integer.parseInt(numberStr);
                    price = new BigDecimal(priceStr).setScale(2, BigDecimal.ROUND_DOWN)
                            .doubleValue();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "参数错误");
                    return;
                }

                // 封装数据
                Book book = new Book();
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setBookTypeId(typeId);
                book.setNumber(number);
                book.setPrice(price);
                book.setPublish(publish);
                book.setRemark(remark);
                book.setStatus(1);
                Connection con = null;
                try {
                    con = dbUtil.getConnection();
                    int i = bookDao.add(con, book);
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "添加成功");
                        reset();
                    } else {
                        JOptionPane.showMessageDialog(null, "添加失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "添加异常");
                }
            }
        });
        btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
        btnNewButton.setBounds(124, 227, 77, 27);
        panel.add(btnNewButton);

        // 重置按钮
        JButton button = new JButton("重置");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        button.setFont(new Font("幼圆", Font.BOLD, 14));
        button.setBounds(329, 229, 77, 27);
        panel.add(button);

        // 类别 文本下拉框
        comboBox = new JComboBox();
        comboBox.setBounds(338, 126, 128, 26);
        panel.add(comboBox);

        // 背景图片
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("lib/images/4.png"));
        lblNewLabel_1.setBounds(0, -4, 584, 323);
        jf.getContentPane().add(lblNewLabel_1);
        getBookType();

        // 初始化菜单栏
        JMenuBar menuBar = new JMenuBar();
        jf.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("类别管理");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("类别添加");
        mntmNewMenuItem.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminMenuFrm();
            }
        });
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("类别修改");
        mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminBTypeEdit();
            }
        });
        mnNewMenu.add(mntmNewMenuItem_1);

        JMenu mnNewMenu_2 = new JMenu("书籍管理");
        menuBar.add(mnNewMenu_2);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("书籍添加");
        mnNewMenu_2.add(mntmNewMenuItem_2);

        JMenuItem mntmNewMenuItem_3 = new JMenuItem("书籍修改");
        mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminBookEdit();
            }
        });
        mnNewMenu_2.add(mntmNewMenuItem_3);

        JMenu menu1 = new JMenu("用户管理");
        menuBar.add(menu1);

        JMenuItem mntmNewMenuItem_4 = new JMenuItem("用户信息");
        mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminUserInfo();
            }
        });
        menu1.add(mntmNewMenuItem_4);

        JMenuItem mntmNewMenuItem_5 = new JMenuItem("借阅信息");
        mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminBorrowInfo();
            }
        });
        menu1.add(mntmNewMenuItem_5);

        JMenu mnNewMenu_1 = new JMenu("退出系统");
        mnNewMenu_1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "欢迎再次使用");
                jf.dispose();
            }
        });
        menuBar.add(mnNewMenu_1);


        jf.setVisible(true);
        jf.setResizable(true);
    }

    // 清空数据
    public void reset() {
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
        textField_4.setText("");
        textField_6.setText("");

    }

    public void getBookType() {

        // 获取连接对象
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            ResultSet list = bookTypeDao.list(con, new BookType());
            while (list.next()) {
                BookType bookType = new BookType();
                bookType.setTypeId(list.getInt("id"));
                bookType.setTypeName(list.getString("type_name"));
                comboBox.addItem(bookType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AdminBookAdd();
    }
}