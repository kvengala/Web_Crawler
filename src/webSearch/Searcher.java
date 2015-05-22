package webSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Searcher {
	public static String outFileName;
	public static String header;
	public static String outputHeader = "";

	public static void main(String[] args) throws Exception {
		File file = new File(args[0]);
		Scanner scanner = new Scanner(file);
		header = scanner.nextLine();
		outFileName = args[1];
		while(scanner.hasNextLine()){
			List<String> givenData = Arrays.asList(scanner.nextLine().split(","));
			String query = "http://search.yahoo.com/search?n=10&ei=UTF-8&va_vt=any&vo_vt=any&ve_vt=any&vp_vt=any&vf=all&vm=i&fl=0&fr=yfp-t-701&p=";
			for (String data : givenData) {
				if(!data.equals(addQuotes("yes")) && !data.equals(addQuotes("no")) 
						&& !data.contains("www.linkedin.com")
								&& !data.equals("\"\""))
					query+="+"+data;
				if(data.equals(addQuotes("yes")))
					query+="+"+addQuotes("Illinois Institute of Technology");
			}
			String sourceCode = "";
			query+="&vs=linkedin.com";
			sourceCode = getUrlSource(query.replace(" ", "%20"));
			System.out.println(query.replace(" ", "%20"));
			boolean end = false;
			System.out.println(sourceCode);
			while(!search(sourceCode, givenData, end)){
				if(query.contains("+")){
				query = query.substring(0, query.lastIndexOf("+"));
				sourceCode = getUrlSource(query);
				}
				else
					end=true;
			}
			System.out.println(query);
		}
		scanner.close();
	}

	private static boolean search(String sourceCode, List<String> givenData, boolean end) throws IOException{

		final List<String> urls = parse(sourceCode);
		List<AttributeTag> attributes = getAttributes();
		List<String> finalSet;
		List<String> blank = new ArrayList<String>();
		List<String> notFound = new ArrayList<String>();

		int space = countOccurrences(header, ',')+1;
		for (int i = 0; i < space; i++) {
			blank.add("");
			notFound.add("\"No Results Found\"");
		}

		if(urls.size() == 0){
			if(end){
				finalSet = new ArrayList<String>();
				finalSet.addAll(givenData);
				finalSet.addAll(notFound);
				writeToFile(finalSet);	
				return true;
			}
			else
				return false;
		}

		List<String> resultSet;

		boolean first = true;
		for (String url : urls) {
			finalSet = new ArrayList<String>();
			resultSet = parseAttributes(getUrlSource(url), attributes);
			resultSet.add(addQuotes(url));
			if(first){
				finalSet.addAll(givenData);
				first = false;
			}
			else{
				finalSet.addAll(blank);
			}
			finalSet.addAll(resultSet);
			writeToFile(finalSet);
		}
		return true;

	}

	private static void writeToFile(List<String> resultSet) throws IOException {
		FileWriter outFile = new FileWriter(outFileName, true);
		PrintWriter out = new PrintWriter(outFile);
		if(new File(outFileName).length() == 0)
			out.print(header + outputHeader + ",\"Linked In\"");
		out.println();

		for (int i = 0; i < resultSet.size()-1; i++) {
			out.print(resultSet.get(i)+",");
		}
		out.print(resultSet.get(resultSet.size()-1));
		out.close();
	}

	public static List<String> parseAttributes(String text, List<AttributeTag> attributes){
		Pattern pattern;
		List<String> resultSet = new ArrayList<String>();
		text = text.replaceAll(">\\s*<", "><");
		System.out.println(text);
		for (AttributeTag attribute : attributes) {
			pattern = Pattern.compile(attribute.getValue());
			Matcher matcher = pattern.matcher(text);
			if(matcher.find()){
				String result = matcher.group(1).trim().replaceAll(",","");

				if(attribute.getName().equalsIgnoreCase(addQuotes("IIT Alumni"))){
					if(result.contains("Illinois Institute of Technology"))
						resultSet.add(addQuotes("yes"));
					else
						resultSet.add(addQuotes("no"));
				}
				else{
					if(attribute.getName().toLowerCase().contains(("current company"))){
						Pattern subPattern = Pattern.compile("<span class=\"at\">at </span>(.+?)</li>");
						Matcher subMatcher = subPattern.matcher(result);
						result = "";
						while(subMatcher.find()){
							result += ridOfTags(subMatcher.group(1).trim())+";";
						}
					}
					else if(attribute.getName().toLowerCase().contains(("current position"))){
						Pattern subPattern = Pattern.compile("<li>(.+?)<span class=\"at\">");
						Matcher subMatcher = subPattern.matcher(result);
						result = "";
						while(subMatcher.find()){
							result += ridOfTags(subMatcher.group(1).trim())+";";
						}
					}
					if(!result.isEmpty())
						resultSet.add(addQuotes(result));
				}
			}
			else
				resultSet.add("");
		}
		return resultSet;
	}

	public static List<String> parse(String sourceCode){

		final Pattern tag = Pattern.compile("class=\"yschttl spt\" href=\"(.+?)\"");
		List<String> urls = new ArrayList<String>();
		Matcher matcher = tag.matcher(sourceCode);
		int i = 0;

		while (matcher.find()) {
			String entry = "http://"+matcher.group(1).trim();
			if(!entry.isEmpty()){ 
//					!entry.contains("linkedin.com/pub/dir") &&
//					(entry.contains("linkedin.com/in/") || 
//							entry.contains("linkedin.com/pub/"))
//							&& !urls.contains(entry)){
//				if(entry.contains("?")){
//					if(!urls.contains(entry.substring(0, entry.indexOf("?"))))
//						urls.add(entry);
//				}
			System.out.println(entry);
//			}
//				else
//					urls.add(entry);
				i++;
			}
			if(i==3)
				break;
		}	    
		return urls;
	}

	private static String getUrlSource(String url) throws IOException {
		URL link = new URL(url);
		URLConnection uc = link.openConnection();
		System.out.println(uc.getHeaderFields());
		BufferedReader in = new BufferedReader(new InputStreamReader(
				uc.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			a.append(inputLine);
		in.close();
		return a.toString();
	}

	private static List<AttributeTag> getAttributes() throws FileNotFoundException{
		File file = new File("d:/first/attributes.txt");
		Scanner scanner = new Scanner(file);
		List<AttributeTag> attributes = new ArrayList<AttributeTag>();
		AttributeTag attribute;
		while(scanner.hasNextLine()){
			String[] parsedAttribute = scanner.nextLine().split("->");
			attribute = new AttributeTag(parsedAttribute[0].trim(), parsedAttribute[1].trim());
			attributes.add(attribute);
			outputHeader+=","+attribute.getName();
		}
		scanner.close();
		return attributes;
	}

	private static String addQuotes(String string){
		return "\""+string+"\"";
	}

	private static String ridOfTags(String string){
		return string.replaceAll("\\<[^>]*>","");
	}
	
	public static int countOccurrences(String haystack, char needle)
	{
	    int count = 0;
	    for (int i=0; i < haystack.length(); i++)
	    {
	        if (haystack.charAt(i) == needle)
	        {
	             count++;
	        }
	    }
	    return count;
	}

}
