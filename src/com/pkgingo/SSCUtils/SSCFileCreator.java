package com.pkgingo.SSCUtils;

import com.pkgingo.PPPUtils.PPPFile;

public class SSCFileCreator
{
	SSCMainHeaderCreator fileheader = new SSCMainHeaderCreator();
	PPPFile parapara=null;
	PPPFile normal=null;
	PPPFile hard=null;
	PPPFile expert=null;
	PPPFile another=null;
	String globalbpm="0";
	String globalbpms="";
	String music="track01.wav";
	
	
	public SSCFileCreator()
	{
		
	}
	
	public void setMusicFileName(String musicFileName)
	{
		music=musicFileName;
	}
	
	public void addParaParaChart(PPPFile pppchart)
	{
		parapara=pppchart;
		globalbpm=parapara.getDisplayBPM();
		globalbpms=parapara.createBPMSegment();
	}
	
	public void addNormalChart(PPPFile normalchart)
	{
		normal=normalchart;
		globalbpm=normal.getDisplayBPM();
		globalbpms=normal.createBPMSegment();
	}
	
	public void addHardChart(PPPFile hardchart)
	{
		hard=hardchart;
		globalbpm=hard.getDisplayBPM();
		globalbpms=hard.createBPMSegment();
	}
	
	public void addExpertChart(PPPFile expertchart)
	{
		expert=expertchart;
		globalbpm=expert.getDisplayBPM();
		globalbpms=expert.createBPMSegment();
	}
	
	public void addAnotherChart(PPPFile anotherchart)
	{
		another=anotherchart;
		globalbpm=another.getDisplayBPM();
		globalbpms=another.createBPMSegment();
	}
	
	private String generateChart(PPPFile chart, String difficulty)
	{
		SSCChartHeaderCreator chartheader = new SSCChartHeaderCreator();
		chartheader.setBPMs(chart.createBPMSegment());
		chartheader.setDisplayBPM(chart.getDisplayBPM());
		chartheader.setDifficulty(difficulty);
		chartheader.setNotes(chart.generateNotesFromMeasures());
		int meter=1;
		switch (difficulty)
		{
			default:
			case "Beginner":
				meter=1;
				break;
			case "Easy":
				meter=2;
				break;
			case "Medium":
				meter=3;
				break;
			case "Hard":
				meter=4;
				break;
			case "Challenge":
				meter=5;
				break;
		}
		chartheader.setMeter(""+meter);
		return chartheader.GenerateHeader();
	}
	
	public String generateSSCFile()
	{
		StringBuilder out = new StringBuilder();
		if (parapara!=null || normal!=null || hard!=null || expert!= null || another != null)
		{
			fileheader.setDisplayBPM(parapara.getDisplayBPM());
			fileheader.setBPMs(globalbpms);
			fileheader.setMusic(music);
			out.append(fileheader.GenerateHeader());
			
			if (parapara !=null)
			{
				out.append(generateChart(parapara,"Beginner"));
			}
			
			if (normal !=null)
			{
				out.append(generateChart(normal,"Easy"));
			}
			
			if (hard !=null)
			{
				out.append(generateChart(hard,"Medium"));
			}
			
			if (expert !=null)
			{
				out.append(generateChart(expert,"Hard"));
			}
			
			if (another !=null)
			{
				out.append(generateChart(another,"Challenge"));
			}
			
			
		}
		
		
		
		
		return out.toString();
	}
	
	

}
