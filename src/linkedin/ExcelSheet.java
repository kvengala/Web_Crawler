package linkedin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;

import tools.StringTools;



public class ExcelSheet 
{
	List<String> outMap=new ArrayList<String>();
	List<String> inMap=new ArrayList<String>();
	private static StringTools stringTools = new StringTools();

	public void initializeMap()
	{
		outMap.add("Input field A");
		outMap.add("Input field B");
		outMap.add("Input field C");
		outMap.add("Output URL");
		outMap.add("Output Last Name");
		outMap.add("Output First Name");
		outMap.add("Output HeaderPosition");
		outMap.add("Current Company Name");
		outMap.add("URL Company Link Name");
		outMap.add("Current Position");
		outMap.add("Past Company Name");
		outMap.add("Past Position");
		outMap.add("School Names");
		outMap.add("Degree");
		outMap.add("Major");
		outMap.add("School Years");
		outMap.add("Location");
		outMap.add("Member Industry");
		outMap.add("Skills");
		outMap.add("Last Updated");
		outMap.add("Output Current Company URL");
		outMap.add("Picture URL");
		//outMap.add("Security Flag");
		inMap.add("Input field A");
		inMap.add("Input field B");
		
	}
	
	public void loadFile(Map entireMap,List errorList)
	{
		System.out.println("AM INSIDE LOAD FILE");
		ExcelSheet e=new ExcelSheet();
		/*Initialize init=new Initialize();
		InputForm in=init.initializeInput();
		OutputForm ou=init.intializeOutput();
		e.createFile(in,ou);*/
			e.createFile(entireMap,errorList);
	}
	
	
	
	public void createFile(Map entireMap,List errorList)
	{
	try
	{
	System.out.println("MAP SIZE::"+entireMap.size());
	initializeMap();
	FileInputStream file = new FileInputStream(new File("d:\\output.xls"));
	HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet("LinkedIn");
    HSSFRow row = sheet.createRow(0);
    HSSFCell cell=row.createCell(0);
    /*cell.setCellValue("INPUT FORM");*/
    
    HSSFCellStyle style = wb.createCellStyle(); 
    style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); 
    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
    style.setWrapText(true);
   /* row=sheet.createRow(2);
    
    cell=row.createCell(0);
    cell.setCellStyle(style); */
    HSSFFont font = wb.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    HSSFRichTextString richString;
    
   /* for(int i=0;i<inMap.size();i++)
    {
    	cell=row.createCell(i);
    	cell.setCellStyle(style); 
        richString= new HSSFRichTextString(outMap.get(i));
        richString.applyFont(0,inMap.get(i).length(),font);
        cell.setCellValue(richString);
    }*/
   
   /* row=sheet.createRow(3);
    cell=row.createCell(0);
    cell.setCellValue("");
    
    
    row=sheet.createRow(7);
    cell=row.createCell(0);
    cell.setCellValue("Results form:");*/
    
    row=sheet.createRow(0);
    
    
    for(int i=0;i<outMap.size();i++)
    {
    	if(i>2)
    	{
    	cell=row.createCell(i);
    	cell.setCellStyle(style); 
        richString= new HSSFRichTextString(outMap.get(i));
        richString.applyFont(0,outMap.get(i).length(),font);
        cell.setCellValue(richString+"\n");
    	}
    	else
    	{
    		cell=row.createCell(i);
    	    cell.setCellValue(outMap.get(i)+"\n");
    	}
    }
  
    int flag=0;
    String cellValue="";
    List cellList=new ArrayList();
    for(int i=0;i<entireMap.size();i++)
    {
 	int secFlag=0;
    Map innerMap=(Map)entireMap.get(i);
    row=sheet.createRow(1+i);
    cell=row.createCell(0);
    cell.setCellValue(innerMap.get("InputTwo").toString());
    
    cell=row.createCell(1);
    cell.setCellValue(innerMap.get("InputOne").toString());
    
    cell=row.createCell(2);
    cell.setCellValue(innerMap.get("InputThree").toString());
    
    cell=row.createCell(3);
    cell.setCellValue(innerMap.get("outUrl").toString());
    
   
    cell=row.createCell(4);
    if(innerMap.get("LastName")!=null && !innerMap.get("LastName").equals(""))
    	cell.setCellValue(innerMap.get("LastName").toString());
    else
    	cell.setCellValue("NA");
   
    
    cell=row.createCell(5);
    
    if(innerMap.get("FirstName")!=null && !innerMap.get("FirstName").equals(""))
    	cell.setCellValue(innerMap.get("FirstName").toString());
    else
    	cell.setCellValue("NA");
    
    cell=row.createCell(6);
    
    String outputHeader="";
    String outputHeaderPos="";
    
    if(innerMap.get("HeaderPosition")!=null && !innerMap.get("HeaderPosition").equals(""))
    {
    	cell.setCellValue(innerMap.get("HeaderPosition").toString());
    	outputHeader=innerMap.get("HeaderPosition").toString();
    }
    else
    	cell.setCellValue("NA");
    
    cell=row.createCell(7);


    
	    cellList=(List)innerMap.get("CurrentCompany");
	    
	    if(cellList!=null && cellList.size()!=0)
	    {
	    	for(int j=0;j<cellList.size();j++)
	    		{
	    			if(j==0)
	    				cellValue=cellList.get(j).toString()+"\n";
	    			else
	    				cellValue=cellValue+cellList.get(j).toString()+"\n";
	    		}
	    }
	    else
	    {
	        cellValue="NA";
	        secFlag=1;
	        if(outputHeader!=null && !outputHeader.equals(""))
	        {
	        	if(outputHeader.contains(" at "))
	        	{
	        		String ar[]=outputHeader.split(" at ");
	        		cellValue=ar[1];
	        		outputHeaderPos=ar[0];
	        	}
	        	else if(outputHeader.contains(" in "))
	        	{
	        		String ar[]=outputHeader.split(" in ");
	        		cellValue=ar[1];
	        		outputHeaderPos=ar[0];
	        	}
	        	else
	        	{
	        		outputHeaderPos=outputHeader;
	        	}
	        		
	        }
	        
	    }
    	
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
 cell=row.createCell(8);
    
    if(innerMap.get("urlCompany")!=null && !innerMap.get("urlCompany").equals(""))
    	cell.setCellValue(innerMap.get("urlCompany").toString());
    else
    	cell.setCellValue("NA");
    
    
    
    
    cell=row.createCell(9);
    

	    cellList=(List)innerMap.get("CurrentPosition");
	    if(cellList!=null && cellList.size()!=0)
	    {
			for(int j=0;j<cellList.size();j++)
			{
				if(j==0)
				cellValue=cellList.get(j).toString()+"\n";
				else
				cellValue=cellValue+cellList.get(j).toString()+"\n";
			}
	    }
	    else 
	    {
	    	cellValue="NA";
	    	secFlag=1;
	        if(!outputHeaderPos.equals(""))
	        {
	        	cellValue=outputHeaderPos;
	        }
	    	

	    }
    cell.setCellValue(new HSSFRichTextString(cellValue));
   
   
    cell=row.createCell(10);

    cellList=(List)innerMap.get("PastCompany");
    
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    	cellValue=cellList.get(j).toString()+"\n";
	    	else
	    	cellValue=cellValue+cellList.get(j).toString()+"\n";
	    }
    }
    else
    	cellValue="NA";

    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    cell=row.createCell(11);
    

    cellList=(List)innerMap.get("PastPosition");
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    	cellValue=cellList.get(j).toString()+"\n";
	    	else
	    	cellValue=cellValue+cellList.get(j).toString()+"\n";
	    }
    }
    else 
    	cellValue="NA";

    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    cell=row.createCell(12);

    cellList=(List)innerMap.get("University");
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    	cellValue=cellList.get(j).toString()+"\n";
	    	else
	    	cellValue=cellValue+cellList.get(j).toString()+"\n";
	    }
    }
    else 
    	cellValue="NA";

    
    cell.setCellValue(new HSSFRichTextString(cellValue));
   
    /*cellList=(List)innerMap.get("Degree");
    List major=(List)innerMap.get("Major");
    
    if(cellList==null && major==null|| cellList.size()==0 && major.size()==0)
    {
    	cellValue="NA";
    }
    
    else if(cellList.size()!=0 && major.size()!=0 && cellList.size()==major.size())
    {
    	for(int j=0;j<cellList.size();j++)
    	{
    		if(j==0)
    			cellValue=cellList.get(j).toString()+"--"+major.get(j).toString()+"\n";
    		else
    			cellValue=cellValue+cellList.get(j).toString()+"--"+major.get(j).toString()+"\n";
    	}
    }
    
    else if(cellList.size()!=0 && major.size()!=0)
    {
    	int deg=cellList.size();
    	int maj=major.size();
    	String ar[]=new String[100];
    		if(deg>maj)
    		{
    			for(int j=0;j<deg;j++)
    	    	{
    				ar[j]=cellList.get(j).toString()+"--";
    	    	}
    			
    			for(int j=0;j<maj;j++)
    			{
    				ar[j]=ar[j]+major.get(j).toString()+"\n";
    			}
    			
      			for(int j=0;j<deg;j++)
    	    	{
      				if(j==0)
      					cellValue=ar[j];
      				else
      					cellValue=cellValue+ar[j];
    	    	}
      		
    			
    		}
    		else if(deg>maj)
    		{
    			for(int j=0;j<deg;j++)
    	    	{
    				ar[j]=major.get(j).toString()+"--";
    	    	}
    			
    			for(int j=0;j<maj;j++)
    			{
    				ar[j]=cellList.get(j).toString()+ar[j]+"\n";
    			}
    			for(int j=0;j<maj;j++)
    	    	{
    				if(j==0)
      					cellValue=ar[j];
      				else
      					cellValue=cellValue+ar[j];
    	    	}
      			cellValue=ar.toString();
    			
    		}
    	
    	
    }
    
    else if(cellList.size()!=0)
    {
    	for(int j=0;j<cellList.size();j++)
    	{
    		if(j==0)
    			cellValue=cellList.get(j).toString()+"\n";
    		else
    			cellValue=cellValue+cellList.get(j).toString()+"\n";
    	}
    }
    else if(major.size()!=0)
    {
    	for(int j=0;j<cellList.size();j++)
    	{
    		if(j==0)
    			cellValue=major.get(j).toString()+"\n";
    		else
    			cellValue=cellValue+major.get(j).toString()+"\n";
    	}
    }

    
    cell.setCellValue(new HSSFRichTextString(cellValue));*/
    
    
    cell=row.createCell(13);
    cellList=(List)innerMap.get("Degree");
    
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    	cellValue=cellList.get(j).toString()+"\n";
	    	else
	    	cellValue=cellValue+cellList.get(j).toString()+"\n";
	    }
    }
    else 
    	cellValue="NA";

    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    cell=row.createCell(14);
    cellList=(List)innerMap.get("Major");
    
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    	cellValue=cellList.get(j).toString()+"\n";
	    	else
	    	cellValue=cellValue+cellList.get(j).toString()+"\n";
	    }
    }
    else 
    	cellValue="NA";

    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    
    
    cell=row.createCell(15);
    

    cellList=(List)innerMap.get("StartEnd");
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    	cellValue=cellList.get(j).toString()+"\n";
	    	else
	    	cellValue=cellValue+cellList.get(j).toString()+"\n";
	    }
    }
    else 
    	cellValue="NA";

    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    cell=row.createCell(16);
    if(innerMap.get("Locality")!=null)
    	cellValue=innerMap.get("Locality").toString();
    else
    	cellValue="NA";
    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
   
    cell=row.createCell(17);
    if(innerMap.get("New")!=null)
    	cellValue=innerMap.get("New").toString();
    else
    	cellValue="NA";
    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    
    cell=row.createCell(18);

    cellList=(List)innerMap.get("Skills");
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    	cellValue=cellList.get(j).toString()+"\n";
	    	else
	    	cellValue=cellValue+cellList.get(j).toString()+"\n";
	    }
    }
    else 
    	cellValue="NA";

    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    cell=row.createCell(19);
    
    if(innerMap.get("UpdateDate")!=null)
    	cellValue=innerMap.get("UpdateDate").toString();
    else
    	cellValue="NA";
    
    cell.setCellValue(new HSSFRichTextString(cellValue));
    
    cell=row.createCell(20);

    cellList=(List)innerMap.get("Href");
    
    if(cellList!=null && cellList.size()!=0)
    {
	    for(int j=0;j<cellList.size();j++)
	    {
	    	if(j==0)
	    		 cellValue=cellList.get(j).toString();
	    	else
	    		cellValue=cellValue+" , "+cellList.get(j).toString();
	    }
    }
    else 
    	cellValue="NA";

    
    System.out.println(cellValue);
    cell.setCellValue(new HSSFRichTextString(cellValue));
   
    cell=row.createCell(21);

    if(innerMap.get("Picture")!=null && !innerMap.get("Picture").equals(""))
    	cell.setCellValue(innerMap.get("Picture").toString());
    else
    	cell.setCellValue("NA");  
    
    /*cell=row.createCell(17);
    	cell.setCellValue(secFlag);*/
    
    flag++;
    }
    
    int invalid=entireMap.size()+3;
    row=sheet.createRow(invalid);

    	cell=row.createCell(0);
    	cell.setCellStyle(style); 
        richString= new HSSFRichTextString("Invalid URL");
        System.out.println("HERE");
        richString.applyFont(0,11,font);
        cell.setCellValue(richString+"\n");
    invalid=invalid+1;
    String split[]=new String[5];
    for(int k=0;k<errorList.size();k++)    
    {
    	row=sheet.createRow(invalid+k);
    	split=errorList.get(k).toString().split("!");
    	
    	if(split.length==3)
		{
    		cell=row.createCell(0);
        	cell.setCellValue(stringTools.removeSpaces(split[0]));
    		cell=row.createCell(1);
        	cell.setCellValue(stringTools.removeSpaces(split[1]));
    		cell=row.createCell(2);
        	cell.setCellValue(stringTools.removeSpaces(split[2]));
		}
		else if(split.length==2)
		{
    		cell=row.createCell(0);
        	cell.setCellValue(stringTools.removeSpaces(split[0]));
    		cell=row.createCell(1);
        	cell.setCellValue(stringTools.removeSpaces(split[1]));
		}
			
    
    }
    
    file.close();
   
    FileOutputStream out = new FileOutputStream(new File("D:\\output.xls"));
    
    wb.write(out);
    out.close();
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
	}
	}
}
