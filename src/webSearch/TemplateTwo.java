package webSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import linkedin.ExcelSheet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import tools.FileTools;
import tools.StringTools;

public class TemplateTwo 
{
	private final static String google = "http://www.google.com/search?q=site:linkedin.com+";
	private final static String protocol = "Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)";
	private static FileTools fileTools;
	private static StringTools stringTools = new StringTools();
	private static List<AttributeTag> attributeTags;
	private static Map entireMap=new HashMap();
	private static Map innerMap;
	private static List commList,errorList=new ArrayList(),templateList=new ArrayList();
	private static String modDate;
	private static String outUrl;
	private static int count;


	public static void main(String ar[])throws IOException
	{
		fileTools = new FileTools("d://data.csv","d://result3.csv");
		attributeTags = fileTools.getAttributeTags();
		TemplateTwo obj=new TemplateTwo();
		ExcelSheet ex=new ExcelSheet();
		Map url=obj.readFromExcel();
		//List urlList=new ArrayList();
		Map urlList=new HashMap();
		int count=0;
		for(int i=0;i<url.size();i++)
		{
			try
			{
				urlList=search(obj.getName((Map)url.get(i)));
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			url.put(i, urlList);
		}
		int num=0;
		List entireList=new ArrayList();
		List nameList=new ArrayList();
	for(int j=0; j<url.size();j++)
	{
		List innerList=new ArrayList();
		String input;
		Map urlLst=(Map)url.get(j);
		
		innerList=(List)urlLst.get(0);
		input=urlLst.get(1).toString();
		
		  for(int k=0;k<innerList.size();k++)
		  {
			  nameList.add(input);
			  entireList.add(innerList.get(k));
		  }
		  
	}
		for(int i=0;i<entireList.size();i++)
		{
			String t="";
			try
			{
			t = getSourceCode(entireList.get(i).toString());
			}
			catch(Exception exc)
			{
				errorList.add(entireList.get(i));
				continue;
			}
			parseAttributes(t,num,entireList.get(i).toString(),outUrl,nameList.get(i).toString());
			num++;
		}
	entireMap=getCompanyName(entireMap);
	ex.loadFile(entireMap,templateList);	
	}
	
	public static Map getCompanyName(Map entireMap)
	{
		List cellList=new ArrayList();
		for(int i=0;i<entireMap.size();i++)
		{
			 Map innerMap=(Map)entireMap.get(i);
			 cellList=(List)innerMap.get("Href");
			 
			 if(cellList!=null && cellList.size()!=0)
			 {
				 String comp="";
				 for(int j=0;j<cellList.size();j++)
					{
						String t="";
						try
						{
						t = getSourceCode(cellList.get(j).toString());
						
						if(j==0)
							comp=parseCompanyUrl(t);
						else
							comp+="\n"+parseCompanyUrl(t);	
						
						}
						catch(Exception ex)
						{
							continue;
						}
					}	
				 innerMap.put("urlCompany", comp);
				 entireMap.put(i,innerMap);
			 }
		}
		return entireMap;
	}
	
	public static String parseCompanyUrl(String text)
	{
		Pattern pattern;
		text = text.replaceAll(">\\s*<", "><");

		for (AttributeTag attributeTag : attributeTags) 
		{
			Attribute attribute = new Attribute();
			attribute.setName(attributeTag.getName());
			pattern = Pattern.compile(attributeTag.getValue());
			Matcher matcher = pattern.matcher(text);
			while(matcher.find())
			{
				String result = matcher.group(1).trim().replaceAll(",","");
				if(attributeTag.getName().toLowerCase().contains(("url company")))
				{
					return stringTools.replaceSpecialCharName(result);
				}
			}
		}
		return "";
		
	}
	
	
	
	public Map readFromExcel()
	{
		Map url=new HashMap();
		Map readExcel;
		
		try
		{
		    FileInputStream file = new FileInputStream(new File("D:\\input.xls"));
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();
		    String value="";
		    int flag=0,count=0;
		    while(rowIterator.hasNext()) {
		    	
		    	readExcel=new HashMap();
		        Row row = rowIterator.next();
		        //For each row, iterate through each columns
		        flag=0;
		        Iterator<Cell> cellIterator = row.cellIterator();
		        while(cellIterator.hasNext()) 
		        {
		        	
		            Cell cell = cellIterator.next();
		            switch (cell.getCellType()) {

		            case HSSFCell.CELL_TYPE_STRING:
		            	  value=stringTools.formatSpaces(cell.getStringCellValue().trim());
		                break;
		            case HSSFCell.CELL_TYPE_NUMERIC:
		            {
		            	double x=new Double(cell.getNumericCellValue());
		                int y=(int)x;
		            	value=Integer.toString(y);
		            	String v[]=value.split(".");
		                break;
		            }
		            default:
		            }
		            if(flag==0)
		            {
		            	readExcel.put("LastName",value);
		            	flag=1;
		            }
		            else if(flag==1)
		            {
		            	readExcel.put("FirstName",value);
		            	flag=2;
		            }
		            else if(flag==2)
		            {
		            	readExcel.put("School",value);
		            	flag=0;
		            }
		        }
		        url.put(count,readExcel);
		        System.out.println(readExcel.get("LastName"));
		        
		        
		        count++;
		    } 
		    file.close();
		    FileOutputStream out = new FileOutputStream(new File("D:\\input.xls"));
		    workbook.write(out);
		    out.close();
		}
			catch(Exception e)
			{
			
			}
			return url;
	}
	
	public String getName(Map nameMap)
	{
	 String retValue="";
	 
	 if(nameMap.get("FirstName")!=null && !nameMap.get("FirstName").toString().equals(""))
		 retValue=nameMap.get("FirstName").toString();
	 
	 if(nameMap.get("LastName")!=null && !nameMap.get("LastName").toString().equals(""))
		 retValue=retValue+"!"+nameMap.get("LastName").toString();
		 
	 if(nameMap.get("School")!=null && !nameMap.get("School").toString().equals(""))
		 retValue=retValue+"!"+nameMap.get("School").toString();
		 
		return retValue;
	}
	
	
	public static Map search(String query) throws IOException{
		Map inMap=new HashMap();
		List<String> urls = new ArrayList<String>();
		String urlName = google+query.replace("!",",");
		URL url = new URL(urlName);
		System.out.println("SERACH"+urlName);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent",protocol);
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		Pattern pattern = Pattern.compile("<cite>(.*?)</cite>");
		String line = in.readLine();
		Matcher m = pattern.matcher(line);
        int flag=0;
		while (m.find()) {
			String urlAttempt = m.group(1);
			if ((urlAttempt.contains("linkedin.com/in/") || 
					urlAttempt.contains("linkedin.com/pub/")) && 
					!urlAttempt.contains("pub/dir"))
				urls.add(stringTools.ridOfTags(urlAttempt));
			
				flag++;
				
				System.out.println(stringTools.ridOfTags(urlAttempt));
				if(flag==5)
					break;
		}

		inMap.put(0,urls);
		inMap.put(1, query);
		
		if(urls.size()==0)
			templateList.add(query);
		
		return inMap;
	}
	
	private static String getSourceCode(String url) throws IOException {
		
		url="http://"+url;
		URL link = new URL(url);
		 HttpURLConnection uc = (HttpURLConnection)link.openConnection();
		 Format date_format = new SimpleDateFormat("MM/dd/yyyy");
		 Date modDat=new Date(uc.getLastModified());
		 outUrl=uc.getURL().toString();
		 modDate=date_format.format(modDat);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(
				uc.getInputStream()));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			a.append(inputLine);
		in.close();
		
		return a.toString();
	}
	
	public static void parseAttributes(String text,int num,String url,String outputUrl,String input){
		Pattern pattern;
		Person person = new Person();
		text = text.replaceAll(">\\s*<", "><");
		List<Attribute> attributes = new ArrayList<Attribute>();
		String arg[]=input.split("!");
		innerMap=initializeMap();
		innerMap.put("url", url);
		innerMap.put("outUrl", outputUrl);
		if(arg.length==3)
		{
		innerMap.put("InputOne", stringTools.removeSpaces(arg[0]));
		innerMap.put("InputTwo",stringTools.removeSpaces(arg[1]));
		innerMap.put("InputThree",stringTools.removeSpaces(arg[2]));
		}
		else if(arg.length==2)
		{
			innerMap.put("InputOne", stringTools.removeSpaces(arg[0]));
			innerMap.put("InputTwo",stringTools.removeSpaces(arg[1]));
		}
			
		
		List comName=new ArrayList();
		List hrefList=new ArrayList();
		//For all the attribute tags the iteration is done and at the end of each iteration the value is fetched
		for (AttributeTag attributeTag : attributeTags) 
		{
			Attribute attribute = new Attribute();
			attribute.setName(attributeTag.getName());
			pattern = Pattern.compile(attributeTag.getValue());
			Matcher matcher = pattern.matcher(text);
			commList=new ArrayList();
			while(matcher.find())
			{
				String result = matcher.group(1).trim().replaceAll(",","");
				if(attributeTag.getName().equalsIgnoreCase(stringTools.addQuotes("IIT Alumni")))
				{
					if(result.contains("Illinois Institute of Technology"))
						attribute.setValue(stringTools.addQuotes("yes"));
					else
						attribute.setValue(stringTools.addQuotes("no"));
				}
				else{
					if(attributeTag.getName().toLowerCase().contains(("first name")))
					{
						innerMap.put("FirstName",stringTools.replaceSpecialCharName(result));
					}
					else if(attributeTag.getName().toLowerCase().contains(("last name")))
					{
						innerMap.put("LastName",stringTools.replaceSpecialCharName(result));
					}
					else if(attributeTag.getName().toLowerCase().contains(("profile picture")))
					{
						innerMap.put("Picture",stringTools.replaceSpecialCharName(result));
					}
					else if(attributeTag.getName().toLowerCase().contains(("headerposition")))
					{
						innerMap.put("HeaderPosition",stringTools.replaceSpecialCharName(result));
					}
					
					// If you want to add more than one result, you should do something like this
					else if(attributeTag.getName().toLowerCase().contains(("current company"))){
						String href="";
						
						Pattern subPattern = Pattern.compile("<span class=\"at\">(.+?)</span>(.+?)</li>");
						Matcher subMatcher = subPattern.matcher(result);
						//System.out.println(result);
						result = "";
						
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							System.out.println("subMatcher.group(2).trim()  :" +subMatcher.group(2).trim());
							result= stringTools.ridOfTags(subMatcher.group(2).trim());
							result=result.replaceAll("&amp;","&").replaceAll("&quot;","\"");
							comName.add(stringTools.replaceSpecialCharName(result)+"\n");
							if(subMatcher.group(2).trim().contains("href"))
							{
								href=subMatcher.group(2).trim();
								Pattern subPattern1 = Pattern.compile("href=\"(.+?)\">");
								Matcher subMatcher1 = subPattern1.matcher(href);
								String temp="";
								 if(subMatcher1.find())
								 {
									String ar[]=url.split("com");
									 
									href= subMatcher1.group(1).trim();
									temp=ar[0].toString()+"com"+href.toString();
									hrefList.add(stringTools.replaceSpecialCharName(temp));
								 }
							}
							
						}
						innerMap.put("CurrentCompany", comName);
						innerMap.put("Href",hrefList);
					}
					else if(attributeTag.getName().toLowerCase().contains(("past company"))){
						List pastName;
						if(innerMap.get("PastCompany")!=null)
						{
						 pastName=(List)innerMap.get("PastCompany");
						}
						else
						{
							pastName=new ArrayList();
						}
						Pattern subPattern = Pattern.compile("<span class=\"at\">at </span>(.+?)</li>");
						Matcher subMatcher = subPattern.matcher(result);
						result = "";
						
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result= stringTools.ridOfTags(subMatcher.group(1).trim());
							pastName.add(stringTools.replaceSpecialCharName(result)+"\n");
						}
						System.out.println(pastName.size());
						innerMap.put("PastCompany",pastName);
					}
					// If you want to add more than one result, you should do something like this
					else if(attributeTag.getName().toLowerCase().contains(("past position"))){
						List pastPosition;
						if(innerMap.get("PastPosition")!=null)
						{
							pastPosition=(List)innerMap.get("PastPosition");
						}
						else
						{
							pastPosition=new ArrayList();
						}
						Pattern subPattern = Pattern.compile("<li>(.+?)<span class=\"at\">");
						Matcher subMatcher = subPattern.matcher(result);
						result = "";
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result= stringTools.ridOfTags(subMatcher.group(1).trim());
							pastPosition.add(stringTools.replaceSpecialCharName(result)+"\n");
						}
						innerMap.put("PastPosition", pastPosition);
					}
					else if(attributeTag.getName().toLowerCase().contains(("degree"))){
						List degree;
						if(innerMap.get("Degree")!=null)
						{
							degree=(List)innerMap.get("Degree");
						}
						else
						{
							degree=new ArrayList();
						}
						Pattern subPattern = Pattern.compile("<span class=\"degree\">(.+?)</span>");
						Matcher subMatcher = subPattern.matcher(result);
						result = "";
						while(subMatcher.find()){
							result = stringTools.ridOfTags(subMatcher.group(1).trim());
							degree.add(stringTools.replaceSpecialCharName(result)+"\n");
						}
						innerMap.put("Degree", degree);
					}
					else if(attributeTag.getName().toLowerCase().contains(("major"))){
						List major;
						if(innerMap.get("Major")!=null)
						{
							major=(List)innerMap.get("Major");
						}
						else
						{
							major=new ArrayList();
						}
						Pattern subPattern = Pattern.compile("<span class=\"major\">(.+?)</span>");
						Matcher subMatcher = subPattern.matcher(result);
						result = "";
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result = stringTools.ridOfTags(subMatcher.group(1).trim());
							major.add(stringTools.replaceSpecialCharName(result)+"\n");
						}
						innerMap.put("Major", major);
					}
					
					else if(attributeTag.getName().toLowerCase().contains(("current position"))){
						List currPosition=new ArrayList();
						if(innerMap.get("CurrentPosition")!=null)
						{
							currPosition=(List)innerMap.get("CurrentPosition");
						}
						else
						{
							currPosition=new ArrayList();
						}
						Pattern subPattern = Pattern.compile("<li>(.+?)<span class=\"at\">");
						Matcher subMatcher = subPattern.matcher(result);
						result = "";
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result= stringTools.ridOfTags(subMatcher.group(1).trim());
							currPosition.add(stringTools.replaceSpecialCharName(result)+"\n");
						}
						innerMap.put("CurrentPosition", currPosition);
					}
					else if(attributeTag.getName().toLowerCase().contains(("locality")))
					{
						innerMap.put("Locality", stringTools.replaceSpecialCharName(result));
					}
					else if(attributeTag.getName().toLowerCase().contains(("new")))
					{
						innerMap.put("New", stringTools.replaceSpecialCharName(result));
					}
					else if(attributeTag.getName().toLowerCase().contains(("skills"))||attributeTag.getName().toLowerCase().contains(("extraskills")))
					{
						result=stringTools.ridOfTags(result);
						commList.add(stringTools.replaceSpecialCharName(result)+",");
						innerMap.put("Skills", commList);
					}
					
					else if(attributeTag.getName().toLowerCase().contains(("university")))
					{
						commList.add(stringTools.replaceSpecialCharName(result)+"\n");
						innerMap.put("University", commList);
					}
					else if(attributeTag.getName().toLowerCase().contains(("startendold")))
					{
						List startDate;
						List endDate;
						if(innerMap.get("StartDate")!=null)
						{
							startDate=(List)innerMap.get("StartDate");
							endDate=(List)innerMap.get("EndDate");
						}
						else
						{
							startDate=new ArrayList();
							endDate=new ArrayList();
						}
						String backup=result;
						Pattern startPattern = Pattern.compile("<abbr class=\"dtstart\" title=\"\\d*-\\d*-\\d*\">(.+?)</abbr>");
						Pattern endPattern = Pattern.compile("<abbr class=\"dtend\" title=\"\\d*-\\d*-\\d*\">(.+?)</abbr>");
						Matcher subMatcher = startPattern.matcher(backup);
						result = "";
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result = stringTools.ridOfTags(subMatcher.group(1).trim());
							startDate.add(result);
						}
						subMatcher = endPattern.matcher(backup);
						result = "";
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result = stringTools.ridOfTags(subMatcher.group(1).trim());
							endDate.add(result);
						}
						innerMap.put("StartDate", startDate);
						innerMap.put("EndDate", endDate);
					}
					else if(attributeTag.getName().toLowerCase().contains(("startend")))
					{
						List startDate=new ArrayList();
						List endDate=new ArrayList();
						String backup=result;
						Pattern startPattern = Pattern.compile("<abbr class=\"dtstart\" title=\"\\d*-\\d*-\\d*\">(.+?)</abbr>");
						Pattern endPattern = Pattern.compile("<abbr class=\"dtend\" title=\"\\d*-\\d*-\\d*\">(.+?)</abbr>");
						Matcher subMatcher = startPattern.matcher(backup);
						result = "";
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result = stringTools.ridOfTags(subMatcher.group(1).trim());
							startDate.add(result);
						}
						subMatcher = endPattern.matcher(backup);
						result = "";
						while(subMatcher.find()){
							//result += stringTools.ridOfTags(subMatcher.group(1).trim())+";";
							result = stringTools.ridOfTags(subMatcher.group(1).trim());
							endDate.add(result);
						}
						innerMap.put("StartDate", startDate);
						innerMap.put("EndDate", endDate);
					}
					
					if(!result.isEmpty())
						attribute.setValue(stringTools.addQuotes(result));
				}
			}
			
			//attribute.setValue("");
			//attributes.add(attribute);
		}
		innerMap.put("UpdateDate",modDate);
		
		if(innerMap.get("StartDate")!=null && innerMap.get("EndDate")!=null )
		{
		innerMap.put("StartEnd",groupDate((List)innerMap.get("StartDate"),(List)innerMap.get("EndDate")));
		}
		else
			innerMap.put("StartEnd",new ArrayList());
        
		
		if(innerMap.get("FirstName")!=null && innerMap.get("LastName")!=null && !innerMap.get("LastName").equals("") && !innerMap.get("FirstName").equals(""))
		{
			entireMap.put(count,innerMap);
			count++;
		}
		else
		{
			errorList.add(innerMap.get("url"));
		}
		person.setAttributes(attributes);
	}
	
	public static List groupDate(List start,List end)
	{
		List dateEnd=new ArrayList();
		
		String startEnd="";
		
	if(start.size()!=0 && end.size()!=0 && (start.size()==end.size()))	
	{
		for(int i=0;i<start.size();i++)
		{
			
			startEnd=start.get(i)+"-"+end.get(i);
			dateEnd.add(startEnd+"\n");
		}
	}
	
	else if(start!=null && end!=null)
	{
		for(int i=0,j=0;i<start.size();i++,j++)
		{
			if(start.get(i)!=null)
			{
				startEnd=start.get(i)+"-";
			}
			
			if(j<end.size()&& end.get(j)!=null)
			{
				startEnd=startEnd+end.get(j).toString();
			}
			dateEnd.add(startEnd+"\n");
		}
		
	}
	
		return dateEnd;
	}
	public static Map initializeMap()
	{
		Map returnMap=new HashMap();
		
		returnMap.put("InputOne", new String(""));
		returnMap.put("InputTwo",new String(""));
		returnMap.put("InputThree",new String(""));
		returnMap.put("FirstName", new String(""));
		returnMap.put("LastName", new String(""));
		returnMap.put("Picture", new String(""));
		returnMap.put("CurrentCompany", new ArrayList());
		returnMap.put("PastCompany", new ArrayList());
		returnMap.put("PastPosition", new ArrayList());
		returnMap.put("Degree", new ArrayList());
		returnMap.put("Major", new ArrayList());
		returnMap.put("Skills", new ArrayList());
		returnMap.put("University", new ArrayList());
		returnMap.put("StartEnd", new ArrayList());
		returnMap.put("HeaderPosition",new String(""));
		returnMap.put("Href", new ArrayList());
		
		return returnMap;
	}
	

}
