package com.pkgingo.GenericUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;



public class DataUtils
{
	public static boolean debug=false;
	private static final Date buildDate = getClassBuildTime();
	private static String OS = null;
	private static String debugFile="E:\\debug.txt";
	private static boolean useDebugFile=false;
	
	
	   
   public static String getOsName()
   {
      if(OS == null) { OS = System.getProperty("os.name"); }
      return OS;
   }
   
   public static boolean isWindows()
   {
      return getOsName().startsWith("Windows");
   }
	
	public static String programName()
	{
		return "PPP2SSC v." + DataUtils.BuildDate() + " by PKGINGO";
	}
	
	public static String BuildDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(buildDate);
	}
	
	/**
	 * Handles files, jar entries, and deployed jar entries in a zip file (EAR).
	 * @return The date if it can be determined, or null if not.
	 */
	private static Date getClassBuildTime() {
	    Date d = null;
	    Class<?> currentClass = new Object() {}.getClass().getEnclosingClass();
	    URL resource = currentClass.getResource(currentClass.getSimpleName() + ".class");
	    if (resource != null) {
	        if (resource.getProtocol().equals("file")) {
	            try {
	                d = new Date(new File(resource.toURI()).lastModified());
	            } catch (URISyntaxException ignored) { }
	        } else if (resource.getProtocol().equals("jar")) {
	            String path = resource.getPath();
	            d = new Date( new File(path.substring(5, path.indexOf("!"))).lastModified() );    
	        } else if (resource.getProtocol().equals("zip")) {
	            String path = resource.getPath();
	            File jarFileOnDisk = new File(path.substring(0, path.indexOf("!")));
	            //long jfodLastModifiedLong = jarFileOnDisk.lastModified ();
	            //Date jfodLasModifiedDate = new Date(jfodLastModifiedLong);
	            try(JarFile jf = new JarFile (jarFileOnDisk)) {
	                ZipEntry ze = jf.getEntry (path.substring(path.indexOf("!") + 2));//Skip the ! and the /
	                long zeTimeLong = ze.getTime ();
	                Date zeTimeDate = new Date(zeTimeLong);
	                d = zeTimeDate;
	            } catch (IOException|RuntimeException ignored) { }
	        }
	    }
	    return d;
	}
	
	
	public static void DebugPrint(Object line, boolean force)
	{
		boolean t=debug;
		debug=force;
		DebugPrint(line);
		debug=t;
	}
	public static void DebugPrint(Object line)
	{
		String l = "" + line;
		if (debug)
		{
			System.out.print(line);
			if (useDebugFile)
			{
				try
				{
					PrintWriter p = new PrintWriter(new FileOutputStream(debugFile,true));
					p.write(l);
					p.close();
				}
				catch (FileNotFoundException e)
				{
					return;
				}
			}
		}
	}
	
	public static void DebugPrintln(Object line, boolean force)
	{
		
		boolean t=debug;
		debug=force;
		DebugPrintln(line);
		debug=t;
	}
	
	public static void DebugPrintln(Object line)
	{
		String l = "" + line;
		if (debug)
		{
			System.out.println(line);
			if (useDebugFile)
			{
				try
				{
					PrintWriter p = new PrintWriter(new FileOutputStream(debugFile,true));
					p.write(l+"\n");
					p.close();
				}
				catch (FileNotFoundException e)
				{
					return;
				}
			}
		}
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static boolean isBinaryFile(File f){
	    FileInputStream in;
		try
		{
			in = new FileInputStream(f);
		}
		catch (FileNotFoundException e)
		{
			DataUtils.DebugPrintln("Could not find " + f.getAbsolutePath());
			e.printStackTrace();
			return false;
		}
	    int size;
		try
		{
			size = in.available();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			try
			{
				in.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	    if(size > 1024) size = 1024;
	    byte[] data = new byte[size];
	    try
		{
			in.read(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			try
			{
				in.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	    try
		{
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}

	    int ascii = 0;
	    int other = 0;

	    for(int i = 0; i < data.length; i++) {
	        byte b = data[i];
	        if( b < 0x09 ) return true;

	        if( b == 0x09 || b == 0x0A || b == 0x0C || b == 0x0D ) ascii++;
	        else if( b >= 0x20  &&  b <= 0x7E ) ascii++;
	        else other++;
	    }

	    if( other == 0 ) return false;

	    return 100 * other / (ascii + other) > 95;
	}
}
