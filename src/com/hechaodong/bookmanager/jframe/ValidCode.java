package com.hechaodong.bookmanager.jframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.Random;
 
import javax.swing.JComponent;

// 展示验证码的组件
public class ValidCode extends JComponent implements MouseListener {
 
	private String code;				// 验证码的文本信息
	private int width, height = 40;		// 验证码图片大小
	private int codeLength = 4; 		// 验证码文本信息长度
 
	private Random random = new Random();

	public ValidCode() {
		width = this.codeLength * 16 + (this.codeLength - 1) * 10;
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		this.addMouseListener(this);
		setToolTipText("点击可以更换验证码");
	}
 
	public int getCodeLength() {
		return codeLength;
	}
 
	// 设置验证码文字的长度
	public void setCodeLength(int codeLength) {
		if(codeLength < 4) {
			this.codeLength = 4;
		} else {
			this.codeLength = codeLength;
		}
	}
 
	public String getCode() {
		return code;
	}
 
	// 产生随机的颜色
	public Color getRandColor(int min, int max) {
		if (min > 255)
			min = 255;
		if (max > 255)
			max = 255;
		int red = random.nextInt(max - min) + min;
		int green = random.nextInt(max - min) + min;
		int blue = random.nextInt(max - min) + min;
		return new Color(red, green, blue);
	}

	// 设置验证码具体的字母是什么
	public String generateCode() {
		char[] codes = new char[this.codeLength];
		for (int i = 0, len = codes.length; i < len; i++) {
			if (random.nextBoolean()) {
				codes[i] = (char) (random.nextInt(26) + 65);
			} else {
				codes[i] = (char) (random.nextInt(26) + 97);
			}
		}
		this.code = new String(codes);
		return this.code;
	}
 
	@Override
	protected void paintComponent(Graphics g) { // 	Graphics是画笔对象，用来画出验证码
		super.paintComponent(g);
		if(this.code == null || this.code.length() != this.codeLength) {
			this.code = generateCode();
		}

		width = this.codeLength * 16 + (this.codeLength - 1) * 10;
		super.setSize(width, height);
		super.setPreferredSize(new Dimension(width, height));
		Font mFont = new Font("Arial", Font.BOLD | Font.ITALIC, 25);
		g.setFont(mFont);

		//绘制出验证码的背景的矩形轮廓
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getRandColor(200, 250));
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(getRandColor(180, 200));
		g2d.drawRect(0, 0, width - 1, height - 1); // 画一个矩阵

		//绘制出验证码背景的线
		int i = 0, len = 150;
		for (; i < len; i++) {
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int x1 = random.nextInt(width - 10) + 10;
			int y1 = random.nextInt(height - 4) + 4;
			g2d.setColor(getRandColor(180, 200));
			g2d.drawLine(x, y, x1, y1);  // 画线
		}

		//绘制出验证码的具体字母
		i = 0; len = this.codeLength;
		FontMetrics fm = g2d.getFontMetrics();
		int base = (height - fm.getHeight())/2 + fm.getAscent();
		for(;i<len;i++) {
			int b = random.nextBoolean() ? 1 : -1;
			g2d.rotate(random.nextInt(10)*0.01*b);
			g2d.setColor(getRandColor(20, 130));
			g2d.drawString(code.charAt(i)+"", 16 * i + 10, base);// 从二维字符中获取对应的字符将其划到界面中
		}
	}
 
	//下一个验证码
	public void nextCode() {
		generateCode();	// 重新生成一个4位数的验证码
		repaint();		// 重新渲染组件
	}
 
	@Override
	public void mouseClicked(MouseEvent e) {
		nextCode();
	}
 
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
 
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
 
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
 
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}