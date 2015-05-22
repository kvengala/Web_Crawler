package webSearch;

import java.util.ArrayList;
import java.util.List;

public class Person {
	List<Attribute> attributes = new ArrayList<Attribute>();

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(Attribute attribute){
		this.attributes.add(attribute);
	}

	public String toString(){
		String result = "+";
		for (int i = 0; i < attributes.size(); i++) {
			if(i == attributes.size()-1)
				result+=attributes.get(i).getValue();
			else
				result+=attributes.get(i).getValue()+"+";	
		}
		return result;
	}
}
