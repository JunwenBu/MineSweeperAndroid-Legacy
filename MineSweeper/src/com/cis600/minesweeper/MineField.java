package com.cis600.minesweeper;

/*
 *  Author: Junwen Bu
 *  Course: CIS 600
 *  HW6
 *  MineField.java
 *  Created on 4/12/13.
 *  Copyright (c) 2013 Junwen Bu. All rights reserved.
 */

import java.util.ArrayList;

public class MineField {
	private int mines, remainMines, flags;
	private ArrayList<Cell> mineField; // store cells of all kinds
	private ArrayList<Cell> mineCells; // store mine cells
	private int xs, ys;
	private boolean blowUp;

	public MineField(int numberOfX, int numberOfY, int numberOfMines) {
		blowUp = false;
		xs = numberOfX;
		ys = numberOfY;
		mines = remainMines = flags = numberOfMines;
		// initialize mine field matrix, here I use one-dimensional ArrayList:
		// (xi,yi) => yi*xs+xi
		mineField = new ArrayList<Cell>(xs * ys);
		mineCells = new ArrayList<Cell>(mines);
		for (int yi = 0; yi < ys; yi++) {
			for (int xi = 0; xi < xs; xi++) {
				Cell cell = new Cell(false, false, xi, yi, 0);
				mineField.add(cell);
			}
		}
		// set up mines randomly
		int mineSet = mines, randX = 0, randY = 0;
		while (mineSet > 0) {
			// formula: randomNum = minimum + (int)(Math.random()*maximum)
			randX = (int) (Math.random() * xs);
			randY = (int) (Math.random() * ys);
			if (mineField.get(randY * xs + randX).getCellValue() != -1) {
				mineField.get(randY * xs + randX).setCellValue(-1);
				mineCells.add(mineField.get(randY * xs + randX));
				mineSet--;
			}
		}
		// calculate 8 neighbor cell
		for (int yi = 0; yi < ys; yi++) {
			for (int xi = 0; xi < xs; xi++) {
				int tempValue = 0;
				if (mineField.get(yi * xs + xi).getCellValue() != -1) {
					if (xi - 1 >= 0 && yi - 1 >= 0
							&& mineField.get((yi - 1) * xs + xi - 1).getCellValue() == -1)
						tempValue++;
					if (yi - 1 >= 0
							&& mineField.get((yi - 1) * xs + xi).getCellValue() == -1)
						tempValue++;
					if (xi + 1 < xs && yi - 1 >= 0
							&& mineField.get((yi - 1) * xs + xi + 1).getCellValue() == -1)
						tempValue++;
					if (xi - 1 >= 0
							&& mineField.get(yi * xs + xi - 1).getCellValue() == -1)
						tempValue++;
					if (xi + 1 < xs
							&& mineField.get(yi * xs + xi + 1).getCellValue() == -1)
						tempValue++;
					if (xi - 1 >= 0 && yi + 1 < ys
							&& mineField.get((yi + 1) * xs + xi - 1).getCellValue() == -1)
						tempValue++;
					if (yi + 1 < ys
							&& mineField.get((yi + 1) * xs + xi).getCellValue() == -1)
						tempValue++;
					if (xi + 1 < xs && yi + 1 < ys
							&& mineField.get((yi + 1) * xs + xi + 1).getCellValue() == -1)
						tempValue++;
					mineField.get(yi * xs + xi).setCellValue(tempValue);
				}
			}
		}
	}
	
	// get cells around the cell whose coordinator is (xi, yi)
	public ArrayList<Cell> calculateCellsAroundByIndex(int xi, int yi) {
		ArrayList<Cell> retArray = new ArrayList<Cell>();
		if (xi - 1 >= 0 && yi - 1 >= 0)
			retArray.add(mineField.get((yi - 1) * xs + xi - 1));
		if (yi - 1 >= 0)
			retArray.add(mineField.get((yi - 1) * xs + xi));
		if (xi + 1 < xs && yi - 1 >= 0)
			retArray.add(mineField.get((yi - 1) * xs + xi + 1));
		if (xi - 1 >= 0)
			retArray.add(mineField.get(yi * xs + xi - 1));
		if (xi + 1 < xs)
			retArray.add(mineField.get(yi * xs + xi + 1));
		if (xi - 1 >= 0 && yi + 1 < ys)
			retArray.add(mineField.get((yi + 1) * xs + xi - 1));
		if (yi + 1 < ys)
			retArray.add(mineField.get((yi + 1) * xs + xi));
		if (xi + 1 < xs && yi + 1 < ys)
			retArray.add(mineField.get((yi + 1) * xs + xi + 1));
		return retArray;
	}

	public Cell getCellByIndex(int xi, int yi) {
		return mineField.get(yi * xs + xi);
	}

	public void remainMinesPlus(int i){
		remainMines+=i;
	}
	
	public void flagsPlus(int i){
		flags+=i;
	}
	
	// getters and setters
	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public int getRemainMines() {
		return remainMines;
	}

	public void setRemainMines(int remainMines) {
		this.remainMines = remainMines;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public ArrayList<Cell> getMineField() {
		return mineField;
	}

	public void setMineField(ArrayList<Cell> mineField) {
		this.mineField = mineField;
	}

	public ArrayList<Cell> getMineCells() {
		return mineCells;
	}

	public void setMineCells(ArrayList<Cell> mineCells) {
		this.mineCells = mineCells;
	}

	public int getXs() {
		return xs;
	}

	public void setXs(int xs) {
		this.xs = xs;
	}

	public int getYs() {
		return ys;
	}

	public void setYs(int ys) {
		this.ys = ys;
	}

	public boolean isBlowUp() {
		return blowUp;
	}

	public void setBlowUp(boolean blowUp) {
		this.blowUp = blowUp;
	}
}
