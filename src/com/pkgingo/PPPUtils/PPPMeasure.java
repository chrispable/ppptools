package com.pkgingo.PPPUtils;

import com.pkgingo.GenericUtils.DataUtils;

public class PPPMeasure
{
	public static final int TOTAL_BEAT_SLOTS=192;
	public static final int NUMBER_OF_ARROWS=5;
	private char[][] measureData = new char[TOTAL_BEAT_SLOTS][NUMBER_OF_ARROWS];
	private boolean touched=false;
	
	private void initializeMeasureData()
	{
		for (int i=0;i<TOTAL_BEAT_SLOTS;i++)
		{
			for (int j=0;j<NUMBER_OF_ARROWS;j++)
			{
				measureData[i][j]='0';
			}
		}
	}
	
	public PPPMeasure()
	{
		initializeMeasureData();
	}
	
	public void setSlot(int beatSlot, byte arrow, char value)
	{
		if (arrow>4)
		{
			DataUtils.DebugPrintln("WARNING: Arrow column ("+arrow+") exceeds number of arrows possible! Tossing line!");
			return;
		}
		measureData[beatSlot][arrow]=value;
		if (value!='0')
		{
			touched=true;
		}
	}
	
	public void setSlot(int beat, int slotOfBeat, byte arrow, char value)
	{
		setSlot((beat*48)+slotOfBeat, arrow, value);
	}
	
	public String getMeasureString()
	{
		if (!touched)
		{
			return "00000\n00000\n00000\n00000\n";
		}
	    StringBuilder builder = new StringBuilder();
	    for(int i = 0; i < TOTAL_BEAT_SLOTS; i++)
	    {
	        for(int j = 0; j < NUMBER_OF_ARROWS; j++)
	        {
	            builder.append(measureData[i][j]);
	        }
	        builder.append("\n");
	    }    
	    return builder.toString();
	}
}
