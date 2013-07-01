package com.cis600.minesweeper;

/*
 *  Author: Junwen Bu
 *  Course: CIS 600
 *  HW6
 *  MainActivity.java
 *  Created on 4/12/13.
 *  Copyright (c) 2013 Junwen Bu. All rights reserved.
 */

import java.util.HashMap;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.*;
import android.view.*;
import android.widget.*;


public class MainActivity extends Activity implements 
		GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener  {
	// used to set difficulty of the game
	public static final int INIT = -1;
	public static final int EASY = 1;
	public static final int NORMAL =2;
	public static final int HARD =3;
	
	private boolean gameOver;
	private MineField mField;
	private HashMap<Integer, View> cellViews;
	private int cellWidth, cellHeight;
	private int difficulty, formerDifficulty;
	private int xi, yi;
	private View topView, bottomView;
	private LinearLayout linearLayout;

	private GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gestureDetector = new GestureDetector(this, this);
		gestureDetector.setOnDoubleTapListener(this);
		formerDifficulty = INIT;
		difficulty = HARD;
		initGame();	
	}

	@SuppressLint("UseSparseArrays")
	private void initGame() {
		// switch difficulty of the game
		switch (difficulty) {
		case EASY:
			mField = new MineField(7, 7, 6); break;
		case NORMAL:
			mField = new MineField(10, 10, 15); break;
		case HARD:
			mField = new MineField(16, 16, 35); break;
		default:
			mField = new MineField(16, 16, 35); break;
		}
		
		gameOver = false;
		
		if (difficulty != formerDifficulty) 
		{ // no need to do initialize this part if there is no so much difference
			formerDifficulty = difficulty;
			linearLayout = new LinearLayout(this);
			linearLayout.setOrientation(LinearLayout.VERTICAL);
			topView = getTopUI();
			linearLayout.addView(topView);
			// get width and height of the mine field
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int fieldSize = metrics.heightPixels >= metrics.widthPixels ? metrics.widthPixels
					: metrics.heightPixels;
			cellWidth = fieldSize / mField.getXs();
			cellHeight = fieldSize / mField.getYs();
			
			ViewGroup.LayoutParams cellParams = new ViewGroup.LayoutParams(
					cellWidth, cellHeight); // LayoutParams for cells

			cellViews = new HashMap<Integer, View>(mField.getXs()
					* mField.getYs());
			// add cell views to mine field
			for (int yi = 0; yi < mField.getYs(); yi++) {
				LinearLayout subLayout = new LinearLayout(this);
				subLayout.setOrientation(LinearLayout.HORIZONTAL);
				for (int xi = 0; xi < mField.getXs(); xi++) {
					View cellView = new ImageView(this);
					cellView.setBackgroundResource(R.drawable.p00);
					cellViews.put(yi * mField.getXs() + xi, cellView);
					subLayout.addView(cellView, cellParams);
				}
				linearLayout.addView(subLayout);
			}
			ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1,-1);
			bottomView = getBottomUI();
			linearLayout.addView(bottomView);
			setContentView(linearLayout, layoutParams);
			linearLayout.requestFocus();
			// display number of flags
			((TextView)findViewById(R.id.flagNum)).setText(Integer.toString(mField.getFlags()));
		}
	}
	
	// this method will be called when user click the "face" button
	public void startNewGame(View view){
		ImageButton button = (ImageButton) findViewById(R.id.newGameButton);
		button.setBackgroundResource(R.drawable.happy);
		for(int i=0; i < cellViews.size(); i++)
			cellViews.get(i).setBackgroundResource(R.drawable.p00);
		initGame();
		((TextView) findViewById(R.id.gameInfo)).setText("Be careful!");
		((TextView)findViewById(R.id.flagNum)).setText(Integer.toString(mField.getFlags()));
	}
	
	// get the top part of the UI
	private View getTopUI(){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.top, null);
	}
	
	// get the bottom part of the UI
	private View getBottomUI(){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.bottom, null);
	}
	
	private View getCellViewByXandY(int xi, int yi){
		return cellViews.get(yi*mField.getXs()+xi);
	}
	
	// set xi and yi coordinators when get position info from user's gesture 
	private void setXandY(float x, float y) {
		int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();  
    	xi=(int)x/cellWidth;
    	yi=(int)(y-topView.getBottom()-contentTop)/cellHeight;
    }

	// menu items
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0,1,1,R.string.action_exit);
		menu.add(0,2,2,R.string.action_about);
		//menu.add(0,3,3,R.string.action_settings);
		SubMenu subMenu = menu.addSubMenu(1,100,100,R.string.action_diff);
		subMenu.add(2,3,3,"Easy");
		subMenu.add(2,4,4,"Normal");
		subMenu.add(2,5,5,"Hard");
		return super.onCreateOptionsMenu(menu);
	}
	
	// actions of the menu items
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==1){
			finish();
		}else if(item.getItemId()==2){
			String content = "Minesweeper is a famouse game." +
					" The object of the game is to " +
					"clear an abstract minefield " +
					"without detonating a mine.\n" +
					"Author: Junwen Bu\nVersion: 1.0\n ";
			Toast toast=Toast.makeText(this, content, Toast.LENGTH_LONG);
			toast.show();
		}else if(item.getItemId()==3){
			difficulty = 1;
			if(formerDifficulty != 1)
				initGame();
		}else if(item.getItemId()==4){
			difficulty = 2;
			if(formerDifficulty != 2)
				initGame();
		}else if(item.getItemId()==5){
			difficulty =3;
			if(formerDifficulty != 3)
				initGame();
		}
		return super.onOptionsItemSelected(item);
	}
	
	// prevent screen rotation
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
    	return gestureDetector.onTouchEvent(event);
    }
	
	// double tap gestures
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		if(gameOver == false){
			setXandY(e.getX(), e.getY());
			Log.d("double", "Tapped at: (" + xi + "," + yi + ")");
			if(0<=yi&&yi<mField.getYs()){
				// open cells: if the value of a cell is zero then open cells around
				Cell tempCell = mField.getCellByIndex(xi, yi);
				expandAroundCells(tempCell);
				((TextView) findViewById(R.id.flagNum)).setText(Integer.toString(mField.getFlags()));
				if(mField.isBlowUp())
				{ // game is lost
					ImageButton button = (ImageButton) findViewById(R.id.newGameButton);
					button.setBackgroundResource(R.drawable.cry);
					((TextView) findViewById(R.id.gameInfo)).setText("I'm dead！(⊙ˍ⊙) Lost");
					gameOver = true;
				}
			}
			return true;
		}
		return false;
	}
	
	// uncover the cells
	private void expandAroundCells(Cell tempCell) {
		// get value of cell
		if(!tempCell.isExplored())
		{
			tempCell.setExplored(true);
			if(tempCell.isFlaged()) {
				tempCell.setFlaged(false);
				mField.flagsPlus(1);
			}
			int a = tempCell.getCellValue();
			if(a<0)
			{ // you lost the game
				mField.setBlowUp(true);
				for(Cell mineCell: mField.getMineCells()){
					if(mineCell.getX()==tempCell.getX() && mineCell.getY() == tempCell.getY())
						getCellViewByXandY(mineCell.getX(), mineCell.getY()).setBackgroundResource(R.drawable.p13);
					else if(mineCell.isFlaged())
						getCellViewByXandY(mineCell.getX(), mineCell.getY()).setBackgroundResource(R.drawable.flag_down);
					else
						getCellViewByXandY(mineCell.getX(), mineCell.getY()).setBackgroundResource(R.drawable.p12);
				}
			} else if(a == 0)
			{
				getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p09);
				for(Cell roundCell: mField.calculateCellsAroundByIndex(tempCell.getX(), tempCell.getY()))
					expandAroundCells(roundCell);
			}else{
				switch(a){
				case 1:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p01); break;
				case 2:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p02); break;
				case 3:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p03); break;
				case 4:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p04); break;
				case 5:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p05); break;
				case 6:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p06); break;
				case 7:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p07); break;
				case 8:
					getCellViewByXandY(tempCell.getX(), tempCell.getY()).setBackgroundResource(R.drawable.p08); break;
				default: break;
				}
			}
		} // end outer if
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0) {
		return false;
	}
	
	// single tap gestures
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		// toggle flag, etc. (incl. invalidating view to force re-draw)
		if (gameOver == false) {
			setXandY(e.getX(), e.getY());
			Log.d("single", "Tapped at: (" + xi + "," + yi + ")");
			if (0 <= yi && yi < mField.getYs()) {
				Cell tempCell = mField.getCellByIndex(xi, yi);
				if (!tempCell.isExplored()) { // set Flag in cell
					if (!tempCell.isFlaged()) {
						tempCell.setFlaged(true);
						getCellViewByXandY(xi, yi).setBackgroundResource(R.drawable.flag);
						mField.flagsPlus(-1);
						((TextView) findViewById(R.id.flagNum)).setText(Integer.toString(mField.getFlags()));
						if (tempCell.getCellValue() == -1) { // cell is a mine
							mField.remainMinesPlus(-1);
							if (mField.getRemainMines() == 0 && mField.getFlags() == 0) { // win the game
								ImageButton button = (ImageButton) findViewById(R.id.newGameButton);
								button.setBackgroundResource(R.drawable.win);
								((TextView) findViewById(R.id.gameInfo)).setText("[]~(￣▽￣)~* You win!");
								gameOver = true;
							}
						}
					} else 
					{  // isFlaged is True, already flaged
						tempCell.setFlaged(false);
						mField.flagsPlus(1);
						getCellViewByXandY(xi, yi).setBackgroundResource(R.drawable.p00);
						((TextView) findViewById(R.id.flagNum)).setText(Integer.toString(mField.getFlags()));
						if (tempCell.getCellValue() == -1) {
							mField.remainMinesPlus(1);
						} else // is not a mine
						{ // after remove unnecessary flags, the game may win
							if (mField.getRemainMines() == 0
									&& mField.getFlags() == 0) {
								ImageButton button = (ImageButton) findViewById(R.id.newGameButton);
								button.setBackgroundResource(R.drawable.win);
								((TextView) findViewById(R.id.gameInfo)).setText("[]~(￣▽￣)~* You win!");
								gameOver = true;
							}
						}
					}
				}
				return true;
			} else
				return false;
		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return true;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}
}
