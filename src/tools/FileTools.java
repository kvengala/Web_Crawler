package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import webSearch.Attribute;
import webSearch.AttributeTag;
import webSearch.Person;

public class FileTools {

	public String outputFileName;
	public String inputFileName;
	public List<String> header;
	public String outputHeader = "";

	private StringTools stringTools = new StringTools();

	public FileTools(String inputFileName, String outputFileName) {
		super();
		this.outputFileName = outputFileName;
		this.inputFileName = inputFileName;
	}

	public List<Person> getQueries() throws IOException{ 
		List<Person> people = new ArrayList<Person>();
		File file = new File(inputFileName);
		Scanner scanner = new Scanner(file);
		
		header = Arrays.asList(scanner.nextLine().split(","));
		
		
		
		while(scanner.hasNextLine()){
			Person p = new Person();
			
			List<String> givenData = Arrays.asList(scanner.nextLine().split("\",\""));
			List<Attribute> attributes = new ArrayList<Attribute>();

			int dataIndex = 0;
			for (String value : givenData) {
				
				Attribute attribute = new Attribute(header.get(dataIndex), stringTools.ridOfQuotes(value));
				System.out.println("HEDAER"+header.get(dataIndex));
				System.out.println(stringTools.ridOfQuotes(value));
				dataIndex++;
				attributes.add(attribute);
				
			}

			for (Attribute attribute : attributes) {
				if(!attribute.getValue().equals("no")){
					if(attribute.getValue().equals("yes"))
						p.addAttribute(new Attribute(attribute.getName(),"Illinois Institute of Technology"));
					else
						p.addAttribute(attribute);
				}
			}
			people.add(p);
		}
		
		
		
		scanner.close();

		return people;
	}

	public void writeToFile(List<Person> people) throws IOException {
		FileWriter outFile = new FileWriter(outputFileName, true);
		PrintWriter out = new PrintWriter(outFile);
		if(new File(outputFileName).length() == 0){
			for (int i = 0; i < header.size(); i++) {
				if(i == header.size()-1)
					out.print(header.get(i));
				else
					out.print(header.get(i)+",");
			}
			out.print(outputHeader + ",\"Linked In\"\n");
		}

		for (Person person : people) {
			for (int i = 0; i < person.getAttributes().size(); i++) {
				Attribute attribute = person.getAttributes().get(i);
				if(i == person.getAttributes().size()-1)
					out.print(attribute.getValue());
				else
					out.print(attribute.getValue()+",");		
			}
			out.print("\n");
		}
		out.close();
	}

	public List<AttributeTag> getAttributeTags() throws FileNotFoundException
	{
		File file = new File("d:/attributes.txt");
		Scanner scanner = new Scanner(file);
		List<AttributeTag> attributeTags = new ArrayList<AttributeTag>();
		AttributeTag attributeTag;
		while(scanner.hasNextLine()){
			String[] parsedAttribute = scanner.nextLine().split("->");
			attributeTag = new AttributeTag(parsedAttribute[0].trim(), parsedAttribute[1].trim());
			attributeTags.add(attributeTag);
			outputHeader+=","+attributeTag.getName();
		}
		
		scanner.close();
		return attributeTags;
	}
}
