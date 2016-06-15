package com.pkgingo.SSCUtils;

public class SSCChartHeaderCreator
{
	private String COMMENT="---------------para-single - ----------------";
	private String NOTEDATA="";
	private String CHARTNAME="";
	private String STEPSTYPE="para-single";
	private String DESCRIPTION="";
	private String CHARTSTYLE="";
	private String DIFFICULTY="";
	private String METER="";
	private String RADARVALUES="0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000";
	private String CREDIT="PKGINGO's PPP2SSC Converter";
	private String OFFSET="0.000000";
	private String BPMS="";
	private String STOPS="";
	private String DELAYS="";
	private String WARPS="";
	private String TIMESIGNATURES="0.000000=4=4";
	private String TICKCOUNTS="0.000000=4";
	private String COMBOS="0.000000=1";
	private String SPEEDS="0.000000=1.000000=0.000000=0";
	private String SCROLLS="0.000000=1.000000";
	private String FAKES="";
	private String LABELS="0.000000=Song Start";
	private String DISPLAYBPM="";
	private String NOTES="";
	
	public String GenerateHeader()
	{
		 StringBuilder builder = new StringBuilder();
		 builder.append("//"+COMMENT+"\n");
		 builder.append("#NOTEDATA:"+NOTEDATA+";\n");
		 builder.append("#CHARTNAME:"+CHARTNAME+";\n");
		 builder.append("#STEPSTYPE:"+STEPSTYPE+";\n");
		 builder.append("#DESCRIPTION:"+DESCRIPTION+";\n");
		 builder.append("#CHARTSTYLE:"+CHARTSTYLE+";\n");
		 builder.append("#DIFFICULTY:"+DIFFICULTY+";\n");
		 builder.append("#METER:"+METER+";\n");
		 builder.append("#RADARVALUES:"+RADARVALUES+";\n");
		 builder.append("#CREDIT:"+CREDIT+";\n");
		 builder.append("#OFFSET:"+OFFSET+";\n");
		 builder.append("#BPMS:"+BPMS+"\n;\n");
		 builder.append("#STOPS:"+STOPS+";\n");
		 builder.append("#DELAYS:"+DELAYS+";\n");
		 builder.append("#WARPS:"+WARPS+";\n");
		 builder.append("#TIMESIGNATURES:"+TIMESIGNATURES+"\n;\n");
		 builder.append("#TICKCOUNTS:"+TICKCOUNTS+"\n;\n");
		 builder.append("#COMBOS:"+COMBOS+"\n;\n");
		 builder.append("#SPEEDS:"+SPEEDS+"\n;\n");
		 builder.append("#SCROLLS:"+SCROLLS+"\n;\n");
		 builder.append("#FAKES:"+FAKES+";\n");
		 builder.append("#LABELS:"+LABELS+";\n");
		 builder.append("#DISPLAYBPM:"+DISPLAYBPM+";\n");
		 builder.append("#NOTES:\n"+NOTES+";\n\n");

		 
		 return builder.toString();
	}
	
	public void setComment(String comment)
	{
		COMMENT=comment;
	}
	public void setNoteData(String notedata)
	{
		NOTEDATA=notedata;
	}
	public void setChartName(String chartname)
	{
		CHARTNAME=chartname;
	}
	public void setDescription(String description)
	{
		DESCRIPTION=description;
	}
	public void setChartStyle(String chartstyle)
	{
		CHARTSTYLE=chartstyle;
	}
	public void setDifficulty(String difficulty)
	{
		DIFFICULTY=difficulty;
	}
	public void setMeter(String meter)
	{
		METER=meter;
	}
	public void setRadarValues(String radarvalues)
	{
		RADARVALUES=radarvalues;
	}
	public void setTimeSignatures(String timesignatures)
	{
		TIMESIGNATURES=timesignatures;
	}
	public void setOffset(String offset)
	{
		OFFSET=offset;
	}
	public void setDisplayBPM(String displaybpm)
	{
		DISPLAYBPM=displaybpm;
	}
	public void setBPMs(String bpms)
	{
		BPMS=bpms;
	}
	public void setDelays(String delays)
	{
		DELAYS=delays;
	}
	public void setStops(String stops)
	{
		STOPS=stops;
	}
	public void setWarps(String warps)
	{
		WARPS=warps;
	}
	public void setCombos(String combos)
	{
		COMBOS=combos;
	}
	public void setTickCounts(String tickcounts)
	{
		TICKCOUNTS=tickcounts;
	}
	public void setSpeeds(String speeds)
	{
		SPEEDS=speeds;
	}
	public void setScrolls(String scrolls)
	{
		SCROLLS=scrolls;
	}
	public void setFakes(String fakes)
	{
		FAKES=fakes;
	}
	public void setLabels(String labels)
	{
		LABELS=labels;
	}
	public void setNotes(String notes)
	{
		NOTES=notes;
	}

/*
example:

	//---------------para-single - ----------------
	#NOTEDATA:;
	#CHARTNAME:;
	#STEPSTYPE:para-single;
	#DESCRIPTION:;
	#CHARTSTYLE:;
	#DIFFICULTY:Easy;
	#METER:1;
	#RADARVALUES:0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000;
	#CREDIT:;
	#OFFSET:0.000000;
	#BPMS:0.000000=157.067993
	,2.000000=157.000000;
	#STOPS:;
	#DELAYS:;
	#WARPS:;
	#TIMESIGNATURES:0.000000=4=4
	;
	#TICKCOUNTS:0.000000=4
	;
	#COMBOS:0.000000=1
	;
	#SPEEDS:0.000000=1.000000=0.000000=0
	;
	#SCROLLS:0.000000=1.000000
	;
	#FAKES:;
	#LABELS:0.000000=Song Start
	;
	#DISPLAYBPM:157.000000;
	#NOTES:
	// measure 0
	00000
	00000
	00000
	00000
	;

 */
}
