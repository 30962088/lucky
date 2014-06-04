package com.mengle.lucky.network.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "City")
public class City {
	
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String name;
	
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private Province province;
	
	public City() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void setProvince(Province province) {
		this.province = province;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	public Province getProvince() {
		return province;
	}
}
