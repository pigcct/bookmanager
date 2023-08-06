package com.hechaodong.bookmanager.jframe;

import java.sql.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JFrame;

import com.hechaodong.bookmanager.dao.BookTypeDao;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.hechaodong.bookmanager.model.BookType;
import com.hechaodong.bookmanager.utils.DbUtil;
import com.hechaodong.bookmanager.utils.ToolUtil;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class AdminBTypeEdit extends JFrame {
    private JFrame jf;
    private JTable table;
    private DefaultTableModel model;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    private DbUtil dbUtil = new DbUtil();
    private BookTypeDao bookTypeDao = new BookTypeDao();

    public AdminBTypeEdit() {

        // 初始化类别修改界面
        jf = new JFrame("类别修改");
        jf.setBounds(650, 250, 611, 472);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

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
        mnNewMenu.add(mntmNewMenuItem_1);

        JMenu mnNewMenu_2 = new JMenu("书籍管理");
        menuBar.add(mnNewMenu_2);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("书籍添加");
        mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new AdminBookAdd();
            }
        });
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

        // 顶部展示“类别信息”的JLabel
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "类别信息", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
        panel_1.setBounds(20, 10, 554, 208);

        // 表头栏数据
        String[] title = {"编号", "类别名称", "类别描述"};
        //具体的各栏行记录 先用空的二位数组占位
        String[][] dates = {};

        // 创建数据模型，实例化上面2个控件对象
        model = new DefaultTableModel(dates, title);
        table = new JTable(model);

        //获取数据库数据放置table中
        putDates();
        panel_1.setLayout(null);
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBounds(20, 22, 517, 163);
        jscrollpane.setViewportView(table);
        panel_1.add(jscrollpane);
        jf.getContentPane().add(panel_1);

        // 底部展示“类别编辑”的JLabel
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "类别编辑", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel.setBounds(20, 228, 554, 168);
        jf.getContentPane().add(panel);
        panel.setLayout(null);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                typeTableMousePressed(evt);
            }
        });

        // 编号 JLabel
        JLabel lblNewLabel = new JLabel("编号：");
        lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 14));
        lblNewLabel.setBounds(73, 26, 45, 28);
        panel.add(lblNewLabel);

        // 编号 文本框
        textField = new JTextField();
        textField.setBounds(116, 30, 92, 24);
        panel.add(textField);
        textField.setColumns(10);

        // 类别名称 JLabel
        JLabel label = new JLabel("类别名称：");
        label.setFont(new Font("幼圆", Font.BOLD, 14));
        label.setBounds(238, 26, 75, 28);
        panel.add(label);

        // 类别名称 文本框
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(314, 30, 122, 24);
        panel.add(textField_1);

        // 描述 JLabel
        JLabel label_1 = new JLabel("描述：");
        label_1.setFont(new Font("幼圆", Font.BOLD, 14));
        label_1.setBounds(73, 65, 45, 28);
        panel.add(label_1);

        // 描述 文本框
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(116, 69, 320, 24);
        panel.add(textField_2);

        // 修改按钮
        JButton btnNewButton = new JButton("修改");
        // 给修改按钮添加一个事件监听器
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取信息
                String typeId = textField.getText();
                String typeName = textField_1.getText();
                String typeRemark = textField_2.getText();
                if (ToolUtil.isEmpty(typeName) || ToolUtil.isEmpty(typeRemark)) {
                    JOptionPane.showMessageDialog(null, "请输入相关信息");
                    return;
                }
                // 封装数据BookType
                BookType bookType = new BookType();
                bookType.setTypeId(Integer.parseInt(typeId));
                bookType.setTypeName(typeName);
                bookType.setRemark(typeRemark);
                // 获取连接对象
                Connection con = null;
                try {
                    con = dbUtil.getConnection();
                    int i = bookTypeDao.update(con, bookType);
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "修改成功");
                        putDates();
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "修改异常");
                } finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
        btnNewButton.setBounds(128, 117, 93, 28);
        panel.add(btnNewButton);

        // 删除按钮
        JButton button = new JButton("删除");

        // 给删除按钮添加事件监听器
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取数据
                String typeId = textField.getText();
                if (ToolUtil.isEmpty(typeId)) {
                    JOptionPane.showMessageDialog(null, "请选择相关信息");
                    return;
                }
                // 获取连接对象
                Connection con = null;
                try {
                    con = dbUtil.getConnection();
                    int i = bookTypeDao.delete(con, typeId);
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "删除成功");
                        putDates();
                    } else if (i == 2) {
                        JOptionPane.showMessageDialog(null, "删除失败-类别最少保留一个");
                    } else if (i == 3) {
                        JOptionPane.showMessageDialog(null, "删除失败-该类别下有书籍");
                    } else {
                        JOptionPane.showMessageDialog(null, "删除失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "删除异常");
                } finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        button.setFont(new Font("幼圆", Font.BOLD, 14));
        button.setBounds(314, 117, 93, 28);
        panel.add(button);

        // 背景图片
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("lib/images/5.png"));
        lblNewLabel_1.setBounds(0, 0, 595, 413);
        jf.getContentPane().add(lblNewLabel_1);

        // 显示
        jf.setVisible(true);
        jf.setResizable(false);
    }

    // 点击表格获取信息
    protected void typeTableMousePressed(MouseEvent evt) {
        int row = table.getSelectedRow();
        textField.setText(table.getValueAt(row, 0).toString());
        textField_1.setText(table.getValueAt(row, 1).toString());
        textField_2.setText(table.getValueAt(row, 2).toString());

    }

    // 将图书类型信息显示在表格中
    public void putDates() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            ResultSet list = bookTypeDao.list(con, new BookType());
            while (list.next()) { // 遍历"list"对象中的每一行数据，将其转换为一个Vector对象
                Vector rowData = new Vector();
                rowData.add(list.getInt("id"));
                rowData.add(list.getString("type_name"));
                rowData.add(list.getString("remark"));
                model.addRow(rowData);
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
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AdminBTypeEdit();
    }
}
