package com.hechaodong.bookmanager.model;


// 图书类型
public class BookType {
	private Integer typeId;
	private String typeName;
	private String remark;

	/**
	 * 获取
	 * @return typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * 设置
	 * @param typeId
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * 获取
	 * @return typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 设置
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
		return "BookType{typeId = " + typeId +
				", typeName = " + typeName +
				", remark = " + remark +
				"}";
	}
}
