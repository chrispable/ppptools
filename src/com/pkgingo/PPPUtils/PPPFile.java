package com.pkgingo.PPPUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.pkgingo.GenericUtils.DataUtils;



public class PPPFile {
	private ArrayList<PPPLine> PPPLines = new ArrayList<PPPLine>();
	private int parsingOffset=0;
	private ArrayList<PPPEvent> PPPEvents = new ArrayList<PPPEvent>();
	private ArrayList<PPPMeasure> measures = new ArrayList<PPPMeasure>();
	private PPPLine header;
	private long totalms=0;
	private String DisplayBPM="0";
	
	
	/* PPP File header format
	 using speedway expert freestyle (01_C.BIN)
	 01 1B 00 00 01 1B 00 00 00 9D 00 9D 00 1B 00 71
	 00 00 01 1B 00 00 00 00 00 00 00 00 00 00 00 00
	 
	 There are 346 lines
	 There are 283 (0x011B Big endian) Arrow events, this corresponds to indexes 0x0-0x1, 0x4-0x5, 0x12-0x13
	 BPM is 157 (0x009D big endian) This corresponds to indexes  0x08-0x09, 0x0A-0x0B (Min and max bpm to display?)
	 There are 7 (0x07) hold arrows? - 276 (0x0114) taps
	 There are 57 (0x39) measures - 228 beats (0xE4)
	 
	 
	 Unknown values: 27 (0x1B) and 113 (0x71) ; they appear to be magic
	 
	*/
	
	public String getDisplayBPM()
	{
		return DisplayBPM;
	}
	
	public long getTotalMs()
	{
		return totalms;
	}
	
	public PPPLine getHeader()
	{
		return header;
	}
	
	private void binaryConstructor(String fname)
	{
		Path path = Paths.get(fname);
		byte[] file=null;
		byte[] headerb=null;
		byte[] line=null;
		try
		{
			DataUtils.DebugPrint("Trying to read from: " + fname);
			file = Files.readAllBytes(path);
			DataUtils.DebugPrintln(" Done! Length: " + file.length);
		}
		catch (IOException e)
		{
			DataUtils.DebugPrint("Error trying to read from: " + fname);
			e.printStackTrace();
		}
		
		headerb=getNextNBytes(file,32);
		header = new PPPLine(headerb);
		header.setLineType(PPPLineType.HEADER);
		//DataUtils.DebugPrintln(Main.bytesToHex(headerb));
		
		while (parsingOffset<file.length)
		{
			//DataUtils.DebugPrintln("Progress: " + parsingOffset + " / " + file.length);
			line=getNextNBytes(file,12);
			PPPLine pppline = new PPPLine(line,PPPEndian.BIG);
			PPPLines.add(pppline);
			int ms = pppline.getMsLineDeltaInt();
			//DataUtils.DebugPrint("Added at delta " + ms + "ms line type " + PPPLines.get(PPPLines.size()-1).getLineType());
			totalms+=ms;
			if (pppline.getLineType() == PPPLineType.ARROW)
			{
				if (pppline.getMsEventLengthInt()>0)
				{
					//DataUtils.DebugPrint("H");
				}
				else
				{
					//DataUtils.DebugPrint("N");
				}
				
			}
			
			//DataUtils.DebugPrint("\n");
		}
		//DataUtils.DebugPrintln("Total ms " + totalms);
		//DataUtils.DebugPrintln("Lines are parsed" + totalms);
		constructSSC(); // the measures have to all be converted FIRST so now we can backtrack and do this
	}
	
	
	public void constructSSC()
	{
		PPPEvent event;
		PPPLine line;
		PPPEventPosition pos;
		PPPEventPosition maxPos = new PPPEventPosition(0,0,0,0);
		int currMS=0;
		for (int i=0; i<PPPLines.size();i++)
		{
			line=PPPLines.get(i);
			currMS+=line.getMsLineDeltaInt();
			DataUtils.DebugPrintln("Got event type: " + line.getLineType());
			DataUtils.DebugPrintln("Curr ms: " + currMS + " (ms delta: " + line.getMsLineDeltaInt() + ")");
			pos = quantizeToMeasurePosition(currMS);
			switch (line.getLineType())
			{
				case EOF:
					event = new PPPEvent(PPPEventType.EOF, pos, line.getArrowColumn());
					PPPEvents.add(event);
					break;
				case BGA:
					event = new PPPEvent(PPPEventType.BGA, pos, line.getArrowColumn());
					PPPEvents.add(event);
					break;
				case SONG_END:
					event = new PPPEvent(PPPEventType.SONG_END, pos, line.getArrowColumn());
					PPPEvents.add(event);
					break;
				case E_EVENT:
					event = new PPPEvent(PPPEventType.E_EVENT, pos, line.getArrowColumn());
					PPPEvents.add(event);
					break;
				case B_EVENT:
					event = new PPPEvent(PPPEventType.B_EVENT, pos, line.getArrowColumn());
					PPPEvents.add(event);
					break;
				case MEASURE_IDENTIFIER:
					event = new PPPEvent(PPPEventType.MEASURE_IDENTIFIER, pos, line.getArrowColumn());
					PPPEvents.add(event);
					break;
				case HEADER:
				case UNKNOWN:
				default:
					break;
				case BPM:
					DisplayBPM=""+(int)(line.getArrowColumn()&0xFF);
					break;
				case ARROW:
					if (line.getMsEventLengthInt()>0) //hold arrow
					{
						event = new PPPEvent(PPPEventType.ARROW_HOLD_START, pos, line.getArrowColumn());
						PPPEvents.add(event);
						pos = quantizeToMeasurePosition(currMS+line.getMsEventLengthInt());
						event = new PPPEvent(PPPEventType.ARROW_HOLD_END, pos, line.getArrowColumn());
						PPPEvents.add(event);
					}
					else //tap arrow
					{
						
						event = new PPPEvent(PPPEventType.ARROW_TAP, pos, line.getArrowColumn());
						PPPEvents.add(event);
					}

					break;
			}
			if (maxPos.getEventMS()<pos.getEventMS())
			{
				maxPos=pos;
			}
			//if (pos.getMeasure()>4) break;

			
			
		}
		
		//now that we have the events, we SHOULD sort by their ms event time (we dont have to, but then we'd have to search or keep track)
		/*
		Collections.sort(PPPEvents, new Comparator<PPPEvent>() {
		    @Override
		    public int compare(PPPEvent o1, PPPEvent o2) {
		        return o1.getEventMS() - o2.getEventMS();
		    }
		});
		*/
		
		//get the last event to get what measure it occurs in, this will be the length of the measure events
		
		for (int i=0;i<=maxPos.getMeasure();i++)
		{
			measures.add(new PPPMeasure());
		}
		
		//now that all the measures are made, traverse the events and update the measures
		PPPEvent e;
		PPPEventPosition epos;
		for (int i=0;i<PPPEvents.size();i++)
		{
			e=PPPEvents.get(i);
			epos = e.getPosition();
			char value='0';
			switch (e.getType())
			{
				default:
					break;
				case ARROW_TAP:
					value='1';
					break;
				case ARROW_HOLD_START:
					value='2';
					break;
				case ARROW_HOLD_END:
					value='3';
					break;
			}
			DataUtils.DebugPrintln("DEBUG: " + epos.getBeat() + " -- " + epos.getBeatSlot()+ " -- " + e.getArrowColumn() + " -- " + value);
			measures.get(epos.getMeasure()).setSlot(epos.getBeat(),epos.getBeatSlot(),e.getArrowColumn(), value);
		}
		//String notes =generateNotesFromMeasures();
		//DataUtils.DebugPrintln(notes);
		
		
	}
	
	public String generateNotesFromMeasures()
	{
		StringBuilder b = new StringBuilder();
		for (int i=0;i<measures.size();i++)
		{
			b.append("// Measure " + i + "\n");
			b.append(measures.get(i).getMeasureString());
			if (i!=measures.size()-1)
			{
				b.append(",");
			}
		}
		return b.toString();
	}
	
	public void sscConstructor(String fname)
	{
		
	}
	
	public PPPFile (String fname)
	{
		binaryConstructor(fname);
	}
	
	
	public String createBPMSegment()
	{
		String res="";
		double lastBPM=0;
		double[] bpms = getMeasureBPMs();
		String seperator="";
		for (int i=0;i<bpms.length;i++)
		{
			if (bpms[i]!=lastBPM)
			{
				lastBPM=bpms[i];
				res+=(seperator+(i*4.0+"="+bpms[i]));
				seperator=",\n";
			}
		}
		return res;
	}
	
	public double[] getMeasureBPMs()
	{
		int[] l = getMeasureMsLengths();
		double[] bpms = new double[l.length];
		
		for (int i=0;i<l.length;i++)
		{
			//ms lengths to bpm formula, get length of a beat in ms, then divide 60000 by that
			//round to 6 decimal places
			bpms[i]=Math.round ((60000.0/(l[i]/4.0)) * 1000000.0) / 1000000.0;  ;
		}
		
		return bpms;
		
		
	}
	
	//get lengths of all measures in ms
	public int[] getMeasureMsLengths()
	{
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		int[] indexes=findMeasureIndexs();
		for (int i=0;i<indexes.length-1;i++)
		{
			int ms = getMsTotalBetween2Indexes(indexes[i], indexes[i+1]);
			lengths.add(ms);
		}
		int ms = getMsTotalBetween2Indexes(indexes[indexes.length-1], PPPLines.size()-1);
		lengths.add(ms);
		return PPPFile.convertIntegers(lengths);
	}
	
	//get ms total, the smaller of the 2 indexes will not have it's lines ms delta  included in the calculation unless upper==lower
	public int getMsTotalBetween2Indexes(int lowerIndex, int upperIndex)
	{
		int ms=0;
		
		//flip indexes if user is silly and swaps them
		if (lowerIndex > upperIndex)
		{
			int t=lowerIndex;
			lowerIndex=upperIndex;
			upperIndex=t;
		}
		DataUtils.DebugPrintln("Getting ms distance from lines " + lowerIndex + " to " + upperIndex);
		
		if (lowerIndex==upperIndex)
		{
			lowerIndex--;
		}
		
		lowerIndex++; //skip the lower index's line, it has an offset that defines where IT starts from the previous event
		for (int i=lowerIndex; i<=upperIndex;i++)
		{
			DataUtils.DebugPrintln("Line " + i + " ms delta is " + PPPLines.get(i).getMsLineDeltaInt());
			ms+=PPPLines.get(i).getMsLineDeltaInt();
		}
		DataUtils.DebugPrintln("ms total is " + ms);
		return ms;
	}
	
	public int[] findMeasureIndexs()
	{
		ArrayList<Integer> mIndexes = new ArrayList<Integer>();
		
		for (int i=0; i<PPPLines.size();i++)
		{
			if (PPPLines.get(i).getLineType()==PPPLineType.MEASURE_IDENTIFIER)
			{
				mIndexes.add(i);
			}
		}
		return PPPFile.convertIntegers(mIndexes);
	}
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	
	public static int[] convertDoubles(List<Double> doubles)
	{
	    int[] ret = new int[doubles.size()];
	    Iterator<Double> iterator = doubles.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	
	
	
	private byte[] getNextNBytes(byte[] file, int n)
	{
		byte[] b = Arrays.copyOfRange(file, parsingOffset, parsingOffset+n);
		parsingOffset+=n;
		return b;
	}
	
	/*
	private void setFileOffset(int n)
	{
		parsingOffset=n;
	}
	*/
	
	//converts from MS lengths 
	public PPPEventPosition quantizeToMeasurePosition(int msEvent)
	{
		double msEventd=msEvent*1.0;
		int[] l = getMeasureMsLengths(); // get measure lengths in MS
		int measure=0;
		//find which measure the event occurs in
		while (msEventd>=0)
		{
			if (measure>=l.length)
			{
				DataUtils.DebugPrintln("ERROR: cant find length for measure " + measure + "! ms remaining: " +msEventd  + " Going back 1 ms!");
				measure--;
				msEventd+=l[measure];
				msEventd--;
				break;
			}
			if (msEventd-l[measure]>=0)
			{
				msEventd-=l[measure];
				measure++;
			}
			else
			{
				break;
			}
		}
		//now we have the measure we want
		DataUtils.DebugPrintln("Event occurs in measure " + measure);
		
		double beatms = l[measure]/4.0; //get ms per beat
		DataUtils.DebugPrintln("MS per beat in measure " + measure + ": " + beatms + " - MS remaining: " + msEventd);
		//find the beat inside the measure we need to work with
		int beat=(int) Math.floor(msEventd/beatms);
		msEventd-=(beatms*beat);

		DataUtils.DebugPrintln("Event occurs in beat " + beat);
		
		
		//break down each beat into 48 pieces
		double msPer48thOfABeat=(1.0/48.0)*beatms;
		DataUtils.DebugPrintln("MS Per 48th of a beat " + msPer48thOfABeat);
		/*now get the sub beat; decimal equivs to 
		.0 /1
		.5 /2
		.3333333 /3
		.25  /4
		.167 /6
		.125 /8
		.083 /12
		.063 /16
		.021 /48
		---were always going to work in 1/48 to simplify things since everything is a factor
		*/
		//find which of those 48 pieces the event falls into
		
		int beatSlot=(int) Math.floor(msEventd/msPer48thOfABeat);
		msEventd-=(msPer48thOfABeat*beatSlot);
		

		DataUtils.DebugPrintln("Event occurs in beat slot " + beatSlot + " with excess of " + msEventd + "ms.");
		DataUtils.DebugPrintln("------------");
		
		//enhance quantizing, first lets see if we have the last beat slot
		if (beatSlot==47)
		{
			//if we do, we need to move to the next beat
			//check if we are in the last beat in the measure
			if(beat!=3)
			{
				//if we are not, we can perform the same math as interbeat work as long as we adjust the beat number
				msEventd+=(msPer48thOfABeat/2.0);
				//now if we take away a whole beat slot...
				msEventd-=msPer48thOfABeat;
				//we can check if its positive or negative. If positive, we should INCREASE the beatslot
				if (msEventd>0)
				{
					beatSlot=0;
					beat++;
				}
			}
			else
			{
				//at this point we have a new measure, so we need to check the new measures bpm
				//lets check if we have the LAST measure if we do, lets do nothing
				if (measure!=l.length-1)
				{
					//if we don't have the last measure
					//get the beatms of the next measure and calculate ms per 192nd note
					beatms = l[measure+1]/4.0;
					msPer48thOfABeat=(1.0/48.0)*beatms;
					//now do the same math as before
					msEventd+=(msPer48thOfABeat/2.0);
					msEventd-=msPer48thOfABeat;
					if (msEventd>0)
					{
						beatSlot=0;
						beat=0;
						measure++;
					}
					
				}
			}
		}
		else
		{
			//otherwise, we can adjust based on moving up 1 beat slot
			//lets add half a slot to the excess
			msEventd+=(msPer48thOfABeat/2.0);
			//now if we take away a whole beat slot...
			msEventd-=msPer48thOfABeat;
			//we can check if its positive or negative. If positive, we should INCREASE the beatslot
			if (msEventd>0)
			{
				beatSlot++;
			}
			
		}
		
		
		//create a position entry 
		return new PPPEventPosition(measure, beat, beatSlot, msEvent);
		
		
	}
	
	
	

}
