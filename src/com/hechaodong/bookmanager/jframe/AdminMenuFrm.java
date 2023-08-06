package com.hechaodong.bookmanager.jframe;

import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import java.awt.Color;
import javax.swing.ImageIcon;

public class AdminMenuFrm extends JFrame {

	private JFrame jf;					// 主操作界面所对应的窗体
	private JTextField textField;		// 输入类别名称所需要的文本框组件
	private JButton btnNewButton;		//添加按钮
	private JTextArea textArea;			//输入类别详情信息所需要的文本框组件

	private DbUtil dbUtil=new DbUtil();
	private BookTypeDao bookTypeDao=new BookTypeDao();


	public AdminMenuFrm(){

		// 初始化主界面所对应的窗体组件
		jf=new JFrame("管理员界面");
		jf.setBounds(650, 250, 600, 429);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);

		// 初始化展示“类别名称”的 JLabel
		JLabel label = new JLabel();
		label.setFont(new Font("幼圆", Font.BOLD, 14));
		label.setText("类别说明：");
		label.setBounds(112, 107, 75, 26);
		jf.getContentPane().add(label);

		// 初始化展示“类别说明”的JLabel
		JLabel label_1 = new JLabel();
		label_1.setFont(new Font("幼圆", Font.BOLD, 14));
		label_1.setText("类别名称：");
		label_1.setBounds(112, 38, 75, 26);
		jf.getContentPane().add(label_1);

		// 初始化输入类别名称所需要的文本框组件
		textField = new JTextField();
		textField.setBounds(197, 38, 241, 26);
		jf.getContentPane().add(textField);

		// 初始化添加按钮
		btnNewButton = new JButton("添加");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 获取类别名称和类别说明数据
				String typeName = textField.getText();
				String typeRemark = textArea.getText();
				// 判断数据
				if (ToolUtil.isEmpty(typeName) || ToolUtil.isEmpty(typeRemark)) {
					JOptionPane.showMessageDialog(null, "请输入相关信息");
					return;
				}

				// 把数据封装到BookType对象
				BookType bookType = new BookType();
				bookType.setTypeName(typeName);
				bookType.setRemark(typeRemark);

				// 获取连接对象
				Connection con = null;
				try {
					// 把数据保存到数据库中
					con = dbUtil.getConnection();
					int i = bookTypeDao.add(con, bookType);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "添加成功");
						reset();
					}else if(i == 2){
						JOptionPane.showMessageDialog(null, "添加失败,类别已存在");
					} else {
						JOptionPane.showMessageDialog(null, "添加失败");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}finally{
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton.setBounds(182, 281, 80, 26);
		jf.getContentPane().add(btnNewButton);

		// 初始化重置按钮
		JButton button = new JButton("重置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset(); // 清空数据
			}
		});
		button.setFont(new Font("幼圆", Font.BOLD, 14));
		button.setBounds(360, 281, 80, 26);
		jf.getContentPane().add(button);

		// 初始化类别详细信息所需要的文本域组件
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setRows(5);
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(197, 109, 241, 132);
		jf.getContentPane().add(textArea);


		// 初始化展示背景图片所需要的JLabel
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("lib/images/6.png"));
		lblNewLabel.setBounds(0, 0, 584, 370);
		jf.getContentPane().add(lblNewLabel);

		// 创建菜单栏组件
		JMenuBar menuBar = new JMenuBar();
		jf.setJMenuBar(menuBar);

		// 添加类别管理
		JMenu mnNewMenu = new JMenu("类别管理");
		menuBar.add(mnNewMenu);

		// 添加类别添加菜单项
		JMenuItem mntmNewMenuItem = new JMenuItem("类别添加");
		mnNewMenu.add(mntmNewMenuItem);

		// 添加类别修改菜单项
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("类别修改");
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBTypeEdit();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		// 添加书籍管理菜单
		JMenu mnNewMenu_2 = new JMenu("书籍管理");
		menuBar.add(mnNewMenu_2);

		// 添加书籍添加菜单
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("书籍添加");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBookAdd();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);

		//添加书籍修改菜单
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("书籍修改");
		mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBookEdit();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);

		// 添加用户管理菜单
		JMenu menu1 = new JMenu("用户管理");
		menuBar.add(menu1);

		// 添加用户信息菜单
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("用户信息");
		mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminUserInfo();
			}
		});
		menu1.add(mntmNewMenuItem_4);

		// 添加借阅信息菜单
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("借阅信息");
		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBorrowInfo();
			}
		});
		menu1.add(mntmNewMenuItem_5);

		// 添加退出系统菜单
		JMenu mnNewMenu_1 = new JMenu("退出系统");
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				JOptionPane.showMessageDialog(null, "欢迎再次使用");
				jf.dispose();
			}
		});
		menuBar.add(mnNewMenu_1);

		// 让jf显示，并且可以更改jf大小
		jf.setVisible(true);
		jf.setResizable(true);
	}
	// 清空类别数据
	public void reset() {
		textField.setText("");
		textArea.setText("");
	}

	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new AdminMenuFrm();
	}
}
