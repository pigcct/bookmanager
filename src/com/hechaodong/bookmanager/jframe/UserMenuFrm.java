package com.hechaodong.bookmanager.jframe;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.hechaodong.bookmanager.dao.BorrowDetailDao;
import com.hechaodong.bookmanager.dao.BookDao;
import com.hechaodong.bookmanager.model.BorrowDetail;
import com.hechaodong.bookmanager.utils.DbUtil;
import com.hechaodong.bookmanager.utils.ToolUtil;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.hechaodong.bookmanager.model.Book;

import javax.swing.ImageIcon;


public class UserMenuFrm extends JFrame {
    private JFrame jf;                  // 主界面的JFrame窗口
    private JLabel lblNewLabel_1;       // 展示当前登录用户的用户名所需要的JLabel
    private JLabel lblNewLabel_2;       // 展示顶部“欢迎你”的JLabel
    private JTable table;               // 借阅信息的表格组件
    private DefaultTableModel model;    // 借阅信息的表格组件所需要的数据模型对象
    private JTextField textField;       // 输入还书编号的文本框textField
    private JButton btnBackBook;        // 换书按钮
    private JButton button;             // 退出系统按钮
    private JPanel panel_2;             // 初始化展示图书信息所需要的面板
    private JComboBox comboBox;         // 搜索字段所需要的下拉选择框
    private JTextField textField_1;     // 搜索关键字所需要的文本框
    private JButton button_1;           // 查询按钮
    private JTable BookTable;           // 展示图书信息所需要的表格组件
    private DefaultTableModel BookModel;// 展示图书信息所需要的表格组件的数据模型组件
    private JTextField textField_2;     // 展示借书编号所需要的文本框组件
    private JTextField textField_3;     // 展示借书书名所需要的文本框组件
    private JLabel lblNewLabel_3;       // 展示背景图片所需要的JLabel

    private DbUtil dbUtil = new DbUtil();
    private BorrowDetailDao borrowDetailDao = new BorrowDetailDao();
    private BookDao BookDao = new BookDao();

    public UserMenuFrm() {

        // 初始化用户操作界面
        jf = new JFrame();
        jf.setTitle("用户页面");
        jf.setBounds(650, 250, 691, 896);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);

        // 顶部展示”借阅信息“的JPanel
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "借阅信息", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
        panel_1.setBounds(23, 48, 651, 239);

        // 表头栏数据
        String[] title = {"编号", "书名", "状态", "借书时间", "还书时间"};// 一维数组
        // 具体的各栏行记录 先用空的二位数组占位
        String[][] dates = {};


        // 初始化表格组件以及数据模型组件 （然后实例化 上面2个控件对象）
        model = new DefaultTableModel(dates, title);
        table = new JTable();
        table.setModel(model);

        // 获取数据库数据放置table中
        putDates(new BorrowDetail());

        // 将表格组件添加到JScrollPane中，并将JScrollPane添加到panel_1中，最后将panel_1添加到JFrame中
        panel_1.setLayout(null);
        JScrollPane jscrollpane = new JScrollPane();
        jscrollpane.setBounds(20, 22, 607, 188);
        jscrollpane.setViewportView(table);
        panel_1.add(jscrollpane);
        jf.getContentPane().add(panel_1);

        // 顶部问候语
        lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setForeground(Color.RED);
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 18));
        lblNewLabel_1.setBounds(315, 0, 197, 28);
        jf.getContentPane().add(lblNewLabel_1);
        lblNewLabel_1.setText(LoginFrm.currentUser.getUserName());

        lblNewLabel_2 = new JLabel("欢迎来到知识的海洋 ");
        lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 18));
        lblNewLabel_2.setBounds(254, 4, 258, 68);
        jf.getContentPane().add(lblNewLabel_2);

        // 还书操作界面的JPanel
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "还书", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
        panel.setBounds(23, 294, 651, 70);
        jf.getContentPane().add(panel);
        panel.setLayout(null);


        // 展示“编号”的JLabel
        JLabel lblNewLabel = new JLabel("编号：");
        lblNewLabel.setBounds(90, 25, 51, 27);
        panel.add(lblNewLabel);
        lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 16));

        // 初始化编号所对应的文本框组件
        textField = new JTextField();
        textField.setBounds(145, 28, 116, 24);
        panel.add(textField);
        textField.setColumns(10);

        // 初始化还书按钮
        btnBackBook = new JButton("还书");
        btnBackBook.setFont(new Font("Dialog", Font.BOLD, 15));
        btnBackBook.setBounds(299, 25, 85, 31);
        panel.add(btnBackBook);

        // 初始化退出系统按钮
        button = new JButton("退出系统");
        button.setFont(new Font("Dialog", Font.BOLD, 15));
        button.setBounds(407, 25, 103, 31);
        panel.add(button);

        // 初始化展示展示图书信息的面板
        panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "借阅信息", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel_2.setBounds(23, 374, 651, 346);
        jf.getContentPane().add(panel_2);
        panel_2.setLayout(null);

        // 初始化查询关所对应的文本框组件
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(252, 23, 135, 27);
        panel_2.add(textField_1);

        // 初始化查询按钮组件
        button_1 = new JButton("查询");
        button_1.addActionListener(new ActionListener() {       // 业务查询的代码逻辑

            public void actionPerformed(ActionEvent e) {

                // 获取查询字段
                int index = comboBox.getSelectedIndex();
                if (index == 0) { // 书名查询
                    String bookName = textField_1.getText();
                    Book book = new Book();
                    book.setBookName(bookName);
                    putDates(book);
                } else {          // 作者名查询
                    String authoerName = textField_1.getText();
                    Book book = new Book();
                    book.setAuthor(authoerName);
                    putDates(book);
                }
            }
        });
        // 查询按钮
        button_1.setFont(new Font("幼圆", Font.BOLD, 16));
        button_1.setBounds(408, 20, 93, 33);
        panel_2.add(button_1);


        // 初始化查询字段所对应的下拉选择框组件
        comboBox = new JComboBox();
        comboBox.setFont(new Font("幼圆", Font.BOLD, 15));
        comboBox.setBounds(123, 26, 109, 24);
        comboBox.addItem("书籍名称");
        comboBox.addItem("书籍作者");
        panel_2.add(comboBox);

        // 展示图书信息所对应的表头数据
        String[] BookTitle = {"编号", "书名", "类型", "作者", "描述"};

        // 具体的各栏行记录 先用空的二位数组占位
        String[][] BookDates = {};

        // 建立数据模型，实例化上面2个控件对象
        BookModel = new DefaultTableModel(BookDates, BookTitle);
        BookTable = new JTable(BookModel);

        // 获取数据库数据放置table中
        putDates(new Book());

        // 将bookTable添加到JScrollPane中，再将JScrollPane添加到panel_2中，最后将panel_2添加到JFrame中
        panel_2.setLayout(null);
        JScrollPane jscrollpane1 = new JScrollPane();
        jscrollpane1.setBounds(22, 74, 607, 250);
        jscrollpane1.setViewportView(BookTable);
        panel_2.add(jscrollpane1);
        jf.getContentPane().add(panel_2);

        // 创建展示借书的面板
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "借书", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel_3.setBounds(23, 730, 645, 87);
        jf.getContentPane().add(panel_3);
        panel_3.setLayout(null);

        // 初始化展示编号两个字所需要的JLabel
        JLabel label = new JLabel("编号：");
        label.setFont(new Font("Dialog", Font.BOLD, 15));
        label.setBounds(68, 31, 48, 33);
        panel_3.add(label);

        // 初始化展示所借图书编号所需要的文本框组件
        textField_2 = new JTextField();
        textField_2.setEditable(false);
        textField_2.setColumns(10);
        textField_2.setBounds(126, 34, 135, 27);
        panel_3.add(textField_2);

        // 初始化展示书名两个字所需要的JLabel
        JLabel label_1 = new JLabel("书名：");
        label_1.setFont(new Font("Dialog", Font.BOLD, 15));
        label_1.setBounds(281, 31, 48, 33);
        panel_3.add(label_1);

        // 初始化展示所借书名所需要的文本框组件
        textField_3 = new JTextField();
        textField_3.setEditable(false);
        textField_3.setColumns(10);
        textField_3.setBounds(339, 34, 135, 27);
        panel_3.add(textField_3);


        // 借书按钮(获取借书信息)
        JButton button_2 = new JButton("借书");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // 获取要借的书所对应的编号及其书名
                String bookId = textField_2.getText();
                String bookName = textField_3.getText();
                if (ToolUtil.isEmpty(bookId) || ToolUtil.isEmpty(bookName)) {
                    JOptionPane.showMessageDialog(null, "请选择相关书籍");
                    return;
                }
                // 把要借的书的相关信息封装成一个BorrowDetail
                BorrowDetail borrowDetail = new BorrowDetail();
                borrowDetail.setUserId(LoginFrm.currentUser.getUserId());
                borrowDetail.setBookId(Integer.parseInt(bookId));
                borrowDetail.setStatus(1);
                borrowDetail.setBorrowTime(ToolUtil.getTime());

                // 获取连接对象
                Connection con = null;
                try {
                    con = dbUtil.getConnection();
                    ResultSet list = borrowDetailDao.list(con, borrowDetail);//先查询是否有该书
                    while (list.next()) {
                        JOptionPane.showMessageDialog(null, "该书已在借,请先还再借");
                        return;
                    }
                    // 保存借阅信息
                    int i = borrowDetailDao.add(con, borrowDetail);
                    // 对返回结果进行判断
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "借书成功");
                        putDates(new BorrowDetail());
                    } else {
                        JOptionPane.showMessageDialog(null, "借书失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "借书异常");
                } finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        button_2.setFont(new Font("Dialog", Font.BOLD, 16));
        button_2.setBounds(495, 31, 80, 33);
        panel_3.add(button_2);

        // 初始化展示背景图片所需要的JLabel
        lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon("lib/images/7.png"));
        lblNewLabel_3.setBounds(0, 0, 684, 864);
        jf.getContentPane().add(lblNewLabel_3);

        // 给借书的表格添加一个鼠标监听器
        BookTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                tableMousePressed(evt);
            }
        });

        // 给退出系统添加一个动作监听按钮
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "欢迎再次使用");
                jf.dispose();
            }
        });
        btnBackBook.setVisible(false);// 关闭窗口

        // 给还书按钮添加一个动作监听按钮
        btnBackBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // 获取还书信息的编号(获取还书信息)
                String BorrowStr = textField.getText();
                if (ToolUtil.isEmpty(BorrowStr)) {
                    JOptionPane.showMessageDialog(null, "请选择未还的书籍");
                    return;
                }

                // 创建BorrowDetail对象，封装还书所对应的数据
                BorrowDetail detail = new BorrowDetail();
                detail.setBorrowId(Integer.parseInt(BorrowStr));
                detail.setStatus(2);
                detail.setReturnTime(ToolUtil.getTime());

                // 获取连接对象
                Connection con = null;
                try {
                    con = dbUtil.getConnection();
                    int i = borrowDetailDao.returnBook(con, detail);
                    if (i == 1) {
                        JOptionPane.showMessageDialog(null, "还书成功");
                    } else {
                        JOptionPane.showMessageDialog(null, "还书失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "还书异常");
                } finally {
                    try {
                        dbUtil.closeCon(con);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                // 重新查询借阅信息
                putDates(new BorrowDetail());
            }
        });

        // 让jf可以显示，并且可以更改jf的大小
        jf.setVisible(true);
        jf.setResizable(true);
    }

    // 点击表格获取数据（底部的借书项）
    public void tableMousePressed(MouseEvent evt) {

        // 获取当前用户所选择的数据行，然后将该行的数据在借书面板的相关组件中进行展示
        int row = BookTable.getSelectedRow();
        Object bookId = BookTable.getValueAt(row, 0);
        Object bookName = BookTable.getValueAt(row, 1);
        textField_2.setText(bookId.toString());
        textField_3.setText(bookName.toString());
    }

    //从数据库获取书籍信息
    public void putDates(Book book) {
        DefaultTableModel model = (DefaultTableModel) BookTable.getModel();
        model.setRowCount(0);// 将表格的行数设置为0，即清空表格中的所有行数据

        // 获取连接对象
        Connection con = null;
        try {
            con = dbUtil.getConnection();
            book.setStatus(1);// 给book对象设置sataus属性为1 上架
            ResultSet list = BookDao.list(con, book);// 调用BookDao中的方法进行查询
            while (list.next()) {
                Vector rowData = new Vector();
                rowData.add(list.getInt("id"));
                rowData.add(list.getString("book_name"));
                rowData.add(list.getString("type_name"));
                rowData.add(list.getString("author"));
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

    public void putDates(BorrowDetail borrowDetail) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // 将表格模型的行数设置为零。这个操作会清空表格中的所有数据
        Integer userId = LoginFrm.currentUser.getUserId();
        Connection con = null;

        // 获取连接对象
        try {
            con = dbUtil.getConnection();
            borrowDetail.setUserId(userId);
            ResultSet list = borrowDetailDao.list(con, borrowDetail);
            while (list.next()) {
                Vector rowData = new Vector();
                rowData.add(list.getInt("id"));
                rowData.add(list.getString("book_name"));
                int status = list.getInt("status");
                if (status == 1) {
                    rowData.add("在借");
                }
                if (status == 2) {
                    rowData.add("已还");
                }
                rowData.add(ToolUtil.getDateByTime(list.getLong("borrow_time")));
                if (status == 2) {
                    rowData.add(ToolUtil.getDateByTime(list.getLong("return_time")));
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

        // 添加一个鼠标监听器
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                putBack(me);
            }
        });
    }

    // 还书
    protected void putBack(MouseEvent me) {

        // 获取用户所选的行信息
        int row = table.getSelectedRow();
        Integer borrowId = (Integer) table.getValueAt(row, 0);
        String status = (String) table.getValueAt(row, 2);
        textField.setText(borrowId.toString());
        if (status.equals("在借")) {
            this.btnBackBook.setVisible(true);
        } else {
            this.btnBackBook.setVisible(false);
        }

    }

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new UserMenuFrm();
    }
}