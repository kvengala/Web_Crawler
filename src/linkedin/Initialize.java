package linkedin;

public class Initialize 
{
	
InputForm input=new InputForm();
OutputForm output=new OutputForm();

public InputForm initializeInput()
	{
	input.setLastName("Anderson");
	input.setFirstName("Robert");
	input.setSchoolName("Illinois Institute of Technology");
	return input;
	}

public OutputForm intializeOutput()
	{
	output.setFirstName(input.getFirstName());
	output.setLastName(input.getLastName());
	output.setSchoolName(input.getSchoolName());
	output.setOutConnection("25");
	output.setOutCurrPos("Software Engineer");
	output.setOutDegree("MSCS");
	output.setOutEmployers("Infosys");
	output.setOutLocation("California");
	output.setOutPic("//picture-url");
	output.setOutYear("2010");
	output.setOutHeadPos("Software Engineer");
	return output;
	}
}
