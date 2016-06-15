package com.pkgingo.PPPUtils;
// Container class to hold an event position from a PPP chart
// basically just a construct so I can return variables when calculating an event position
public class PPPEventPosition
{
	//3 parts of an event I care about
	private int measure = 0;
	private int beat = 0;
	private int beatSlot = 0;
	private int ms=0;

	//constructor
	public PPPEventPosition(int eventMeasure, int eventBeat, int eventBeatSlot, int eventMS)
	{
		measure = eventMeasure;
		beat = eventBeat;
		beatSlot = eventBeatSlot;
		ms=eventMS;
	}

	//gets and sets for each event location marker I care about
	public int getMeasure()
	{
		return measure;
	}

	public int getBeat()
	{
		return beat;
	}

	public int getBeatSlot()
	{
		return beatSlot;
	}
	
	public int getEventMS()
	{
		return ms;
	}
	
	public void setMeasure(int eventMeasure)
	{
		measure = eventMeasure;
	}
	
	public void setBeat(int eventBeat)
	{
		beat = eventBeat;
	}
	
	public void setBeatSlot(int eventBeatSlot)
	{
		beatSlot = eventBeatSlot;
	}
	
	public void setEventMS(int eventMs)
	{
		ms = eventMs;
	}
	
	
}
