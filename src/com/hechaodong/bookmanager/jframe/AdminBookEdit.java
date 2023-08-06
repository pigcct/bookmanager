package com.hechaodong.bookmanager.jframe;

import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.hechaodong.bookmanager.dao.BookTypeDao;
import com.hechaodong.bookmanager.model.BookType;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.hechaodong.bookmanager.dao.BookDao;
import com.hechaodong.bookmanager.model.Book;

import com.hechaodong.bookmanager.utils.DbUtil;
import com.hechaodong.bookmanager.utils.ToolUtil;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Font;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class AdminBookEdit extends JFrame {

    private JFrame jf;
    private JTextField textField;
    private JTable table;
    private DefaultTableModel model;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;

    private JComboBox comboBox;
    private JComboBox comboBox_1;
    private JComboBox comboBox_2;

    private DbUtil dbUtil = new DbUtil();
    private BookDao bookDao = new BookDao();
    private BookTypeDao bookTypeDao = new BookTypeDao();

    public AdminBookEdit() {

        // 初始化书籍修改界面
        jf = new JFrame("书籍修改");
        jf.setBounds(650, 250, 600, 672);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化菜单栏
        JMenuBar menuBar = new JMenuBar();
        jf.setJMenuBar(menuBar);

        // 类别管理
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


        // 书籍管理
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
        mnNewMenu_2.add(mntmNewMenuItem_3);

        // 用户管理
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


        // 退出系统
        JMenu mnNewMenu_1 = new JMenu("退出系统");
        mnNewMenu_1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "欢迎再次使用");
                jf.dispose();
            }
        });
        menuBar.add(mnNewMenu_1);
        jf.getContentPane().setLayout(null);

        // 顶部展示“书目查询”的JLabel
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "书目查询", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel.setBounds(20, 10, 541, 78);
        jf.getContentPane().add(panel);
        panel.setLayout(null);

        // 下拉框选项
        comboBox = new JComboBox();
        comboBox.setFont(new Font("幼圆", Font.BOLD, 15));
        comboBox.setBounds(55, 28, 109, 24);
        comboBox.addItem("书籍名称");
        comboBox.addItem("书籍作者");
        panel.add(comboBox);

        // 初始化查询所对应的文本框
        textField = new JTextField();
        textField.setBounds(185, 28, 146, 24);
        panel.add(textField);
        textField.setColumns(10);

        // 查询按钮
        JButton btnNewButton = new JButton("查询");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                if (index == 0) {
                    String bookName = textField.getText();
                    Book book = new Book();
                    book.setBookName(bookName);
                    putDates(book);
                } else {
                    String authoerName = textField.getText();
                    Book book = new Book();
                    book.setAuthor(authoerName);
                    putDates(book);
                }
            }
        });
        btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
        btnNewButton.setBounds(352, 28, 81, 25);
        panel.add(btnNewButton);


        // 中部展示“书籍信息”的JLabel
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "书籍信息", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
        panel_1.setBounds(20, 105, 541, 195);

        // 表头栏数据  一位数组
        String[] title = {"编号", "书名", "类别", "作者", "价格", "库存", "状态"};
        // 具体的各栏行记录 先用空的二位数组占位
        String[][] dates = {};

        // 创建数据模型，实例化上面2个控件对象
        model = new DefaultTableModel(dates, title);
        table = new JTable(model);

        // 获取数据库数据放置table中
        putDates(new Book());
        panel_1.setLayout(null);
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBounds(20, 22, 496, 154);
        jscrollpane.setViewportView(table);
        panel_1.add(jscrollpane);
        jf.getContentPane().add(panel_1);

        // 给表格添加一个鼠标监听器
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                tableMousePressed(evt);
            }
        });

        // 创建一个Panel对象
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(20, 310, 541, 292);
        jf.getContentPane().add(panel_2);
        panel_2.setLayout(null);

        // 初始化展示“编号”的JLabel
        JLabel label = new JLabel("编号：");
        label.setFont(new Font("幼圆", Font.BOLD, 14));
        label.setBounds(58, 10, 45, 27);
        panel_2.add(label);

        // 初始化输入编号的文本框组件
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(101, 10, 129, 27);
        panel_2.add(textField_1);

        // 初始化展示“书名”的JLabel
        JLabel label_1 = new JLabel("书名：");
        label_1.setFont(new Font("幼圆", Font.BOLD, 14));
        label_1.setBounds(294, 10, 45, 27);
        panel_2.add(label_1);

        // 初始化输入书名的文本框组件
        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(338, 10, 128, 27);
        panel_2.add(textField_2);

        // 初始化展示“作者”的JLabel
        JLabel label_2 = new JLabel("作者：");
        label_2.setFont(new Font("幼圆", Font.BOLD, 14));
        label_2.setBounds(58, 58, 45, 27);
        panel_2.add(label_2);

        // 初始化输入作者的文本框组件
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(101, 58, 129, 27);
        panel_2.add(textField_3);

        // 价格 JLabel
        JLabel label_3 = new JLabel("价格：");
        label_3.setFont(new Font("幼圆", Font.BOLD, 14));
        label_3.setBounds(58, 104, 45, 27);
        panel_2.add(label_3);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(101, 104, 129, 27);
        panel_2.add(textField_4);

        // 出版 JLabel
        JLabel label_4 = new JLabel("出版：");
        label_4.setFont(new Font("幼圆", Font.BOLD, 14));
        label_4.setBounds(294, 58, 45, 27);
        panel_2.add(label_4);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(337, 58, 129, 27);
        panel_2.add(textField_5);

        // 类别 JLabel
        JLabel label_5 = new JLabel("类别：");
        label_5.setFont(new Font("幼圆", Font.BOLD, 14));
        label_5.setBounds(58, 189, 45, 27);
        panel_2.add(label_5);

        // “类别”下拉选择框
        comboBox_1 = new JComboBox();
        comboBox_1.setBounds(102, 190, 128, 26);

        //获取类别
        getBookType();
        panel_2.add(comboBox_1);


        // 库存 JLabel
        JLabel label_6 = new JLabel("库存：");
        label_6.setFont(new Font("幼圆", Font.BOLD, 14));
        label_6.setBounds(294, 104, 45, 27);
        panel_2.add(label_6);

        textField_6 = new JTextField();
        textField_6.setColumns(10);
        textField_6.setBounds(337, 104, 129, 27);
        panel_2.add(textField_6);

        // 描述 JLabel
        JLabel label_7 = new JLabel("描述：");
        label_7.setFont(new Font("幼圆", Font.BOLD, 14));
        label_7.setBounds(58, 152, 45, 27);
        panel_2.add(label_7);

        textField_7 = new JTextField();
        textField_7.setColumns(10);
        textField_7.setBounds(101, 152, 365, 27);
        panel_2.add(textField_7);


        // 状态 JLabel
        JLabel label_8 = new JLabel("状态：");
        label_8.setFont(new Font("幼圆", Font.BOLD, 14));
        label_8.setBounds(294, 190, 45, 27);
        panel_2.add(label_8);

        comboBox_2 = new JComboBox();
        comboBox_2.setBounds(338, 191, 128, 26);
        comboBox_2.addItem("上架");
        comboBox_2.addItem("下架");
        panel_2.add(comboBox_2);

		// 修改按钮
        JButton btnNewButton_1 = new JButton("修改");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 获取文本框组件中的相关数据
                String bookName = textField_2.getText();
                String author = textField_3.getText();
                String priceStr = textField_4.getText();
                String publish = textField_5.getText();
                String numberStr = textField_6.getText();
                String remark = textField_7.getText();
                String bookId = textField_1.getText();

                // 对数据进行校验
                if (ToolUtil.isEmpty(bookId) || ToolUtil.isEmpty(bookName)
                        || ToolUtil.isEmpty(author) || ToolUtil.isEmpty(publish)
                        || ToolUtil.isEmpty(priceStr)
                        || ToolUtil.isEmpty(numberStr) || ToolUtil.isEmpty(remark)) {
                    JOptionPane.showMessageDialog(null, "请输入相关内容");
                    return;
                }

                // 获取图书的类别数据
                BookType bookType = (BookType) comboBox_1.getSelectedItem();
                Integer typeId = bookType.getTypeId();

                int index = comboBox_2.getSelectedIndex();

                // 对库存数据价格数据进行处理
                int number;
                double price;
                try {
                    number = Integer.parseInt(numberStr);
                    price = new BigDecimal(priceStr).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "参数错误");
                    return;
                }

                // 创建Book对象进行数据封装
                Book book = new Book();
                book.setBookId(Integer.parseInt(bookId));
                book.setBookName(bookName);
                book.setAuthor(author);
                book.setBookTypeId(typeId);
                book.setNumber(number);
                book.setPrice(price);
                book.setPublish(publish);
                book.setRemark(remark);
                book.setStatus(1);
                // 判断上架下架状态
                if (index == 0) {
                    book.setStatus(1);
                } else if (index == 1) {
                    book.setStatus(2);
                }

                // 获取连接对象
                Connection con = null;
                try {
                    con = dbUtil.getConnection();
                    int i = bookDao.update(con, book);

                    // 判断
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "修改成功");
                        putDates(new Book()); //+++++++
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

                putDates(new Book());
            }
        });
        // 修改按钮
        btnNewButton_1.setFont(new Font("幼圆", Font.BOLD, 14));
        btnNewButton_1.setBounds(304, 235, 93, 35);
        panel_2.add(btnNewButton_1);

        // 背景图片
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("lib/images/5.png"));
        lblNewLabel.setBounds(0, 0, 584, 613);
        jf.getContentPane().add(lblNewLabel);

        jf.setVisible(true);
        jf.setResizable(true);
    }

    // 获取图书类型
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
                System.out.println(bookType);
                /*
                 * 添加图书中类别设置
                 * */
                comboBox_1.addItem(bookType);
//                comboBox_1.addItem(String.valueOf(bookType.getTypeName()));
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

    //点击表格获取数据
    public void tableMousePressed(MouseEvent evt) {
        int row = table.getSelectedRow();
        Integer bookId = (Integer) table.getValueAt(row, 0);
        Book book = new Book();
        book.setBookId(bookId);

        // 获取连接对象
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            ResultSet list = bookDao.list(con, book);
            if (list.next()) {
                textField_1.setText(list.getString("id"));
                textField_2.setText(list.getString("book_name"));
                textField_3.setText(list.getString("author"));
                textField_5.setText(list.getString("publish"));
                textField_4.setText(list.getString("price"));
                textField_6.setText(list.getString("number"));
                textField_7.setText(list.getString("remark"));
                int status = list.getInt("status");
                if (status == 1) {
                    comboBox_2.setSelectedIndex(0);
                } else {
                    comboBox_2.setSelectedIndex(1);
                }
                int typeId = list.getInt("type_id");
                int count = comboBox_1.getItemCount();
                for (int i = 0; i < count; i++) {
                    BookType bookType = (BookType) comboBox_1.getItemAt(i);
                    if (bookType.getTypeId() == typeId) {
                        comboBox_1.setSelectedIndex(i);
                    }
                }
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

    // 从数据库中获取数据
    private void putDates(Book book) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            ResultSet resultSet = bookDao.list(con, book);
            while (resultSet.next()) {
                Vector rowData = new Vector();
                rowData.add(resultSet.getInt("id"));
                rowData.add(resultSet.getString("book_name"));
                rowData.add(resultSet.getString("type_name"));
                rowData.add(resultSet.getString("author"));
                rowData.add(resultSet.getDouble("price"));
                rowData.add(resultSet.getInt("number"));
                if (resultSet.getInt("status") == 1) {
                    rowData.add("上架");
                } else {
                    rowData.add("下架");
                }
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
        new AdminBookEdit();
    }
}
