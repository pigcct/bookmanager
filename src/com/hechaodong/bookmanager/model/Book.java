package com.hechaodong.bookmanager.model;

// 图书类
public class Book {
	private Integer bookId;
	private String bookName;
	private String author;
	private Integer status;//状态 1上架  2下架
	private Integer bookTypeId;
	private String publish;
	private Integer number;// 库存数量
	private double price;
	private String remark;

	/**
	 * 获取
	 * @return bookId
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * 设置
	 * @param bookId
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * 获取
	 * @return bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * 设置
	 * @param bookName
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * 获取
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取
	 * @return bookTypeId
	 */
	public Integer getBookTypeId() {
		return bookTypeId;
	}

	/**
	 * 设置
	 * @param bookTypeId
	 */
	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	/**
	 * 获取
	 * @return publish
	 */
	public String getPublish() {
		return publish;
	}

	/**
	 * 设置
	 * @param publish
	 */
	public void setPublish(String publish) {
		this.publish = publish;
	}

	/**
	 * 获取
	 * @return number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * 设置
	 * @param number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * 获取
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * 设置
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 获取
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
		return "Book{bookId = " + bookId +
				", bookName = " + bookName +
				", author = " + author +
				", status = " + status +
				", bookTypeId = " + bookTypeId +
				", publish = " + publish +
				", number = " + number +
				", price = " + price +
				", remark = " + remark +
				"}";
	}
}
