package com.hechaodong.bookmanager.model;

// 借阅信息
public class BorrowDetail {
	private Integer borrowId;
	private Integer userId;
	private Integer bookId;
	private Integer status;//状态  1在借  2已还
	private Long borrowTime;
	private Long returnTime;

	/**
	 * 获取
	 * @return borrowId
	 */
	public Integer getBorrowId() {
		return borrowId;
	}

	/**
	 * 设置
	 * @param borrowId
	 */
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	/**
	 * 获取
	 * @return userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 设置
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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
	 * @return borrowTime
	 */
	public Long getBorrowTime() {
		return borrowTime;
	}

	/**
	 * 设置
	 * @param borrowTime
	 */
	public void setBorrowTime(Long borrowTime) {
		this.borrowTime = borrowTime;
	}

	/**
	 * 获取
	 * @return returnTime
	 */
	public Long getReturnTime() {
		return returnTime;
	}

	/**
	 * 设置
	 * @param returnTime
	 */
	public void setReturnTime(Long returnTime) {
		this.returnTime = returnTime;
	}

	public String toString() {
		return "BorrowDetail{borrowId = " + borrowId +
				", userId = " + userId +
				", bookId = " + bookId +
				", status = " + status +
				", borrowTime = " + borrowTime +
				", returnTime = " + returnTime +
				"}";
	}
}
