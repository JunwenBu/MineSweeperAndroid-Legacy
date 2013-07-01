package com.cis600.minesweeper;

/*
 *  Author: Junwen Bu
 *  Course: CIS 600
 *  HW6
 *  Cell.java
 *  Created on 4/12/13.
 *  Copyright (c) 2013 Junwen Bu. All rights reserved.
 */

public class Cell {
	private boolean isExplored, isFlaged;
	private int x, y;
	private int cellValue;

	public Cell() {
		isExplored = false;
		isFlaged = false;
		x = 0;
		y = 0;
		cellValue = 0;
	}

	public Cell(boolean iE, boolean iF, int xi, int yi, int v) {
		isExplored = iE;
		isFlaged = iF;
		x = xi;
		y = yi;
		cellValue = v;
	}

	// getter and setter
	public boolean isExplored() {
		return isExplored;
	}

	public void setExplored(boolean isExplored) {
		this.isExplored = isExplored;
	}

	public boolean isFlaged() {
		return isFlaged;
	}

	public void setFlaged(boolean isFlaged) {
		this.isFlaged = isFlaged;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCellValue() {
		return cellValue;
	}

	public void setCellValue(int cellValue) {
		this.cellValue = cellValue;
	}

}
