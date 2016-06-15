package com.pkgingo.PPPUtils;

public class PPPEvent
{
	private int ms;
	private PPPEventPosition sscPos =null;
	private PPPEventType type= PPPEventType.UNKNOWN;
	private byte arrowColumn=0;
	
	public PPPEvent(PPPEventType eventType, PPPEventPosition eventPosition)
	{
		type=eventType;
		sscPos=eventPosition;
		ms=sscPos.getEventMS();
	}
	
	public PPPEvent(PPPEventType eventType, PPPEventPosition eventPosition, byte arrow)
	{
		type=eventType;
		sscPos=eventPosition;
		arrowColumn=arrow;
		ms=sscPos.getEventMS();
	}
	
	public byte getArrowColumn()
	{
		return arrowColumn;
	}
	
	public int getEventMS() //cant set this, must send a new event position to set this
	{
		return ms;
	}
	
	public PPPEventPosition getPosition()
	{
		return sscPos;
	}
	
	public PPPEventType getType()
	{
		return type;
	}
	
	public void setType(PPPEventType eventType)
	{
		type=eventType;
	}
	
	public void setPosition(PPPEventPosition eventPosition)
	{
		sscPos=eventPosition;
		ms=sscPos.getEventMS();
	}
	
	public void setArrow(byte arrow)
	{
		arrowColumn=arrow;
	}
	
	
}
