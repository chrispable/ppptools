package com.pkgingo.SSCUtils;

public class SSCMainHeaderCreator
{
	/* Example header
	 *  #VERSION:0.81;
		#TITLE:Speedway;
		#SUBTITLE:;
		#ARTIST:Niko;
		#TITLETRANSLIT:;
		#SUBTITLETRANSLIT:;
		#ARTISTTRANSLIT:;
		#GENRE:;
		#ORIGIN:;
		#CREDIT:;
		#BANNER:;
		#BACKGROUND:;
		#PREVIEWVID:;
		#JACKET:;
		#CDIMAGE:;
		#DISCIMAGE:;
		#LYRICSPATH:;
		#CDTITLE:;
		#MUSIC:speedway.mp3;
		#OFFSET:0.000000;
		#SAMPLESTART:31.799999;
		#SAMPLELENGTH:15.000000;
		#SELECTABLE:YES;
		#DISPLAYBPM:157.000000;
		#BPMS:0.000=157.068,224.000=628.272;
		#STOPS:;
		#DELAYS:;
		#WARPS:;
		#TIMESIGNATURES:0.000=4=4;
		#TICKCOUNTS:0.000=4;
		#COMBOS:0.000=1;
		#SPEEDS:0.000=1.000=0.000=0;
		#SCROLLS:0.000=1.000;
		#FAKES:;
		#LABELS:0.000=Song Start;
		#BGCHANGES:;
		#KEYSOUNDS:;
		#ATTACKS:
		;
	 */
	private String VERSION="0.81";
	private String TITLE="";
	private String SUBTITLE="";
	private String ARTIST="";
	private String TITLETRANSLIT="";
	private String SUBTITLETRANSLIT="";
	private String ARTISTTRANSLIT="";
	private String GENRE="";
	private String ORIGIN="";
	private String CREDIT="PKGINGO's PPP2SSC Converter";
	private String BANNER="";
	private String BACKGROUND="";
	private String PREVIEWVID="";
	private String JACKET="";
	private String CDIMAGE="";
	private String DISCIMAGE="";
	private String LYRICSPATH="";
	private String CDTITLE="";
	private String MUSIC="";
	private String OFFSET="0.000000";
	private String SAMPLESTART="0.000000";
	private String SAMPLELENGTH="15.000000";
	private String SELECTABLE="YES";
	private String DISPLAYBPM="";
	private String BPMS="";
	private String STOPS="";
	private String DELAYS="";
	private String WARPS="";
	private String TIMESIGNATURES="0.000=4=4";
	private String TICKCOUNTS="0.000=4";
	private String COMBOS="0.000=1";
	private String SPEEDS="0.000=1.000=0.000=0";
	private String SCROLLS="0.000=1.000";
	private String FAKES="";
	private String LABELS="0.000=Song Start";
	private String BGCHANGES="";
	private String KEYSOUNDS="";
	private String ATTACKS="";
	
	
	public void setVersion(String version)
	{
		VERSION=version;
	}
	public void setTitle(String title)
	{
		TITLE=title;
	}
	public void setSubtitle(String subtitle)
	{
		SUBTITLE=subtitle;
	}	
	public void setArtist(String artist)
	{
		ARTIST=artist;
	}	
	public void setGenre(String genre)
	{
		GENRE=genre;
	}	
	public void setSubTitleTranslit(String subtitletranslit)
	{
		SUBTITLETRANSLIT=subtitletranslit;
	}
	public void setArtistTranslit(String artisttranslit)
	{
		ARTISTTRANSLIT=artisttranslit;
	}	
	public void setOrigin(String origin)
	{
		ORIGIN=origin;
	}
	public void setBanner(String banner)
	{
		BANNER=banner;
	}
	public void setBackground(String background)
	{
		BACKGROUND=background;
	}
	public void setPreviewVid(String previewvid)
	{
		PREVIEWVID=previewvid;
	}
	public void setJacket(String jacket)
	{
		JACKET=jacket;
	}
	public void setCDImage(String cdimage)
	{
		CDIMAGE=cdimage;
	}
	public void setDiscImage(String discimage)
	{
		DISCIMAGE=discimage;
	}
	public void setLyricsPath(String lyricspath)
	{
		LYRICSPATH=lyricspath;
	}
	public void setCDTitle(String cdtitle)
	{
		CDTITLE=cdtitle;
	}
	public void setMusic(String music)
	{
		MUSIC=music;
	}
	public void setOffset(String offset)
	{
		OFFSET=offset;
	}
	public void setSampleStart(String samplestart)
	{
		SAMPLESTART=samplestart;
	}
	public void setSampleLength(String samplelength)
	{
		SAMPLELENGTH=samplelength;
	}
	public void setSelectable(String selectable)
	{
		SELECTABLE=selectable;
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
	public void setBGChanges(String bgchanges)
	{
		BGCHANGES=bgchanges;
	}
	public void setKeySounds(String keysounds)
	{
		KEYSOUNDS=keysounds;
	}
	public void setAttacks(String attacks)
	{
		ATTACKS=attacks;
	}
	
	public String GenerateHeader()
	{
		 StringBuilder builder = new StringBuilder();
		 builder.append("#VERSION:"+VERSION+";\n");
		 builder.append("#TITLE:"+TITLE+";\n");
		 builder.append("#SUBTITLE:"+SUBTITLE+";\n");
		 builder.append("#ARTIST:"+ARTIST+";\n");
		 builder.append("#TITLETRANSLIT:"+TITLETRANSLIT+";\n");
		 builder.append("#SUBTITLETRANSLIT:"+SUBTITLETRANSLIT+";\n");
		 builder.append("#ARTISTTRANSLIT:"+ARTISTTRANSLIT+";\n");
		 builder.append("#GENRE:"+GENRE+";\n");
		 builder.append("#ORIGIN:"+ORIGIN+";\n");
		 builder.append("#CREDIT:"+CREDIT+";\n");
		 builder.append("#BANNER:"+BANNER+";\n");
		 builder.append("#BACKGROUND:"+BACKGROUND+";\n");
		 builder.append("#PREVIEWVID:"+PREVIEWVID+";\n");
		 builder.append("#JACKET:"+JACKET+";\n");
		 builder.append("#CDIMAGE:"+CDIMAGE+";\n");
		 builder.append("#DISCIMAGE:"+DISCIMAGE+";\n");
		 builder.append("#LYRICSPATH:"+LYRICSPATH+";\n");
		 builder.append("#CDTITLE:"+CDTITLE+";\n");
		 builder.append("#MUSIC:"+MUSIC+";\n");
		 builder.append("#OFFSET:"+OFFSET+";\n");
		 builder.append("#SAMPLESTART:"+SAMPLESTART+";\n");
		 builder.append("#SAMPLELENGTH:"+SAMPLELENGTH+";\n");
		 builder.append("#SELECTABLE:"+SELECTABLE+";\n");
		 builder.append("#DISPLAYBPM:"+DISPLAYBPM+";\n");
		 builder.append("#BPMS:"+BPMS+";\n");
		 builder.append("#STOPS:"+STOPS+";\n");
		 builder.append("#DELAYS:"+DELAYS+";\n");
		 builder.append("#WARPS:"+WARPS+";\n");
		 builder.append("#TIMESIGNATURES:"+TIMESIGNATURES+";\n");
		 builder.append("#TICKCOUNTS:"+TICKCOUNTS+";\n");
		 builder.append("#COMBOS:"+COMBOS+";\n");
		 builder.append("#SPEEDS:"+SPEEDS+";\n");
		 builder.append("#SCROLLS:"+SCROLLS+";\n");
		 builder.append("#FAKES:"+FAKES+";\n");
		 builder.append("#LABELS:"+LABELS+";\n");
		 builder.append("#BGCHANGES:"+BGCHANGES+";\n");
		 builder.append("#KEYSOUNDS:"+KEYSOUNDS+";\n");
		 builder.append("#ATTACKS:"+ATTACKS+"\n;\n\n");

		 
		 return builder.toString();
	}
	
	
	public SSCMainHeaderCreator()
	{
		
	}
}
