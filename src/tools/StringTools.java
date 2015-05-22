package tools;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {
	
	private Map<String,String> special=new HashMap();
	
	public String ridOfTags(String string){
		return string.replaceAll("\\<[^>]*>","");
	}
	
	public String ridOfQuotes(String string){
		return string.replaceAll("\"","");
	}
	
	public String addQuotes(String string){
		return "\""+string+"\"";
	}
	
	public String formatSpaces(String string){
		return string.replace(" ", "%20");
	}
	public String removeSpaces(String string){
		return string.replace("%20", " ");
	}

	
	public String replaceSpecialChar(String value)
	{
		Pattern replace = Pattern.compile("&\\w*;");
	    Matcher matcher2 = replace.matcher(value); 
	    return matcher2.replaceAll("@");
	}
	
	public String replaceSpecialCharName(String value)
	{
		Pattern replace;
		Matcher matcher2; 
		
		value=value.trim();
		
		 replace = Pattern.compile("&#xe1;");
		 matcher2 = replace.matcher(value); 
		 value=matcher2.replaceAll("a");
		 
		 replace = Pattern.compile("&#xe9;");
		 matcher2 = replace.matcher(value); 
		 value=matcher2.replaceAll("e");
		 
		 replace = Pattern.compile("&#x113;");
		 matcher2 = replace.matcher(value); 
		 value= matcher2.replaceAll("e");
		 
		 replace = Pattern.compile("&#xed;");
		 matcher2 = replace.matcher(value); 
		 value=matcher2.replaceAll("i");
		 
		 replace = Pattern.compile("&#xf3;");
		 matcher2 = replace.matcher(value); 
		 value=matcher2.replaceAll("o");
		 
		 replace = Pattern.compile("	");
		 matcher2 = replace.matcher(value); 
		 value=matcher2.replaceAll("o");
		 
		 replace = Pattern.compile("&#xae;");
		 matcher2 = replace.matcher(value); 
		 value=matcher2.replaceAll("@");
		 
		 replace = Pattern.compile("&#xf1;");
		 matcher2 = replace.matcher(value); 
		 value=matcher2.replaceAll("n");
		 
		 replace = Pattern.compile("&#xc7;");
		 matcher2 = replace.matcher(value); 
		 value= matcher2.replaceAll("c");
		 
		 replace = Pattern.compile(" &#xdc;");
		 matcher2 = replace.matcher(value); 
		 value= matcher2.replaceAll("u");
		 
		 replace = Pattern.compile("&#xe4;");
		 matcher2 = replace.matcher(value); 
		 value= matcher2.replaceAll("a");
		 
		 replace = Pattern.compile("&#x2013;");
		 matcher2 = replace.matcher(value); 
		 value= matcher2.replaceAll("-");
		 
		
		 
		

	     
		 
		 if(value.contains("&quot;"))
			value=value.replace("&quot;","\"");
		 if(value.contains("&amp;"))
				value=value.replace("&amp;","&");
		 if(value.contains("&frasl;"))
				value=value.replace("&frasl;","//");
		 if(value.contains("&lt;"))
				value=value.replace("&lt;","<");
		 if(value.contains("&gt;"))
				value=value.replace("&gt;",">");
		 if(value.contains("&ndash;"))
				value=value.replace("&quot;","–");
		 if(value.contains(" &mdash;"))
				value=value.replace("&quot;","—");
		 
	    return value;
	}
	
	
	public void intializeSpecial()
	{
		special.put("&quot;","@");
		special.put("&amp;","@");
		special.put("&frasl;","@");
		special.put("&lt;","@");
		special.put("&gt;","@");
		special.put("&ndash;","@");
		special.put("&mdash;","@");
		special.put("&nbsp;","@");
		special.put("&iexcl;","@");
		special.put("&cent;","@");
		special.put("&pound;","@");
		special.put("&curren;","@");
		special.put("&yen;","@");
		special.put("&brkbar;","@");
		special.put("&brvbar;","@");
		special.put("&sect;","@");
		special.put("&die;","@");
		special.put("&uml;","@");
		special.put("&copy;","@");
		special.put("&ordf;","@");
		special.put("&laquo;","@");
		special.put("&not;","@");
		special.put("&shy;","@");
		special.put("&reg;","@");
		special.put("&macr;","@");
		special.put("&hibar;","@");
		special.put("&deg;","@");
		special.put("&plusmn;","@");
		special.put("&sup2;","@");
		special.put("&sup3;","@");
		special.put("&acute;","@");
		special.put("&micro;","@");
		special.put("&para;","@");
		special.put("&middot;","@");
		special.put("&cedil;","@");
		special.put("&sup1;","@");
		special.put("&ordm;","@");
		special.put("&raquo;","@");
		special.put("&frac14;","@");
		special.put("&frac12;","@");
		special.put("&frac34;","@");
		special.put("&iquest;","@");
		special.put("&Agrave;","@");
		special.put("&Aacute;","@");
		special.put("&Acirc;","@");
		special.put("&Atilde;","@");
		special.put("&Auml;","@");
		special.put("&Aring;","@");
		special.put("&AElig;","@");
		special.put("&Ccedil;","@");
		special.put("&Egrave;","@");
		special.put("&Eacute;","@");
		special.put("&Ecirc;","@");
		special.put("&Euml;","@");
		special.put("&Igrave;","@");
		special.put("&Iacute;","@");
		special.put("&Icirc;","@");
		special.put("&Iuml;","@");
		special.put("&ETH;","@");
		special.put("&Ntilde;","@");
		special.put("&Ograve;","@");
		special.put("&Oacute;","@");
		special.put("&Ocirc;","@");
		special.put("&Otilde;","@");
		special.put("&Ouml;","@");
		special.put("&times;","@");
		special.put("&Oslash;","@");
		special.put("&Ugrave;","@");
		special.put("&Uacute;","@");
		special.put("&Ucirc;","@");
		special.put("&Uuml;","@");
		special.put("&Yacute;","@");
		special.put("&THORN;","@");
		special.put("&szlig;","@");
		special.put("&agrave;","@");
		special.put("&aacute;","@");
		special.put("&acirc;","@");
		special.put("&atilde;","@");
		special.put("&auml;","@");
		special.put("&aring;","@");
		special.put("&aelig;","@");
		special.put("&ccedil;","@");
		special.put("&egrave;","@");
		special.put("&eacute;","@");
		special.put("&ecirc;","@");
		special.put("&euml;","@");
		special.put("&igrave;","@");
		special.put("&iacute;","@");
		special.put("&icirc;","@");
		special.put("&iuml;","@");
		special.put("&eth;","@");
		special.put("&ntilde;","@");
		special.put("&ograve;","@");
		special.put("&oacute;","@");
		special.put("&ocirc;","@");
		special.put("&otilde;","@");
		special.put("&ouml;","@");
		special.put("&divide;","@");
		special.put("&oslash;","@");
		special.put("&ugrave;","@");
		special.put("&uacute;","@");
		special.put("&ucirc;","@");
		special.put("&uuml;","@");
		special.put("&yacute;","@");
		special.put("&thorn;","@");
		special.put("&yuml;","@");
		special.put("&#33;","@");
		special.put("&#34;","@");
		special.put("&#35;","@");
		special.put("&#36;","@");
		special.put("&#37;","@");
		special.put("&#38;","@");
		special.put("&#39;","@");
		special.put("&#40;","@");
		special.put("&#41;","@");
		special.put("&#47;","@");
		special.put("&#58;","@");
		special.put("&#59;","@");
		special.put("&#60;","@");
		special.put("&#61;","@");
		special.put("&#62;","@");
		special.put("&#63;","@");
		special.put("&#64;","@");
		special.put("&#91;","@");
		special.put("&#92;","@");
		special.put("&#93;","@");
		special.put("&#95;","@");
		special.put("&#96;","@");
		special.put("&#123;","@");
		special.put("&#124;","@");
		special.put("&#125;","@");
		special.put("&#126;","@");
		special.put("&#150;","@");
		special.put("&#151;","@");
		special.put("&#161;","@");
		special.put("&#162;","@");
		special.put("&#163;","@");
		special.put("&#164;","@");
		special.put("&#165;","@");
		special.put("&#166;","@");
		special.put("&#167;","@");
		special.put("&#168;","@");
		special.put("&#169;","@");
		special.put("&#170;","@");
		special.put("&#171;","@");
		special.put("&#172;","@");
		special.put("&#173;","@");
		special.put("&#174;","@");
		special.put("&#175;","@");
		special.put("&#176;","@");
		special.put("&#177;","@");
		special.put("&#178;","@");
		special.put("&#179;","@");
		special.put("&#180;","@");
		special.put("&#181;","@");
		special.put("&#182;","@");
		special.put("&#183;","@");
		special.put("&#184;","@");
		special.put("&#185;","@");
		special.put("&#186;","@");
		special.put("&#187;","@");
		special.put("&#188;","@");
		special.put("&#189;","@");
		special.put("&#190;","@");
		special.put("&#191;","@");
		special.put("&#192;","@");
		special.put("&#193;","@");
		special.put("&#194;","@");
		special.put("&#195;","@");
		special.put("&#196;","@");
		special.put("&#197;","@");
		special.put("&#198;","@");
		special.put("&#199;","@");
		special.put("&#200;","@");
		special.put("&#201;","@");
		special.put("&#202;","@");
		special.put("&#203;","@");
		special.put("&#204;","@");
		special.put("&#205;","@");
		special.put("&#206;","@");
		special.put("&#207;","@");
		special.put("&#208;","@");
		special.put("&#209;","@");
		special.put("&#210;","@");
		special.put("&#211;","@");
		special.put("&#212;","@");
		special.put("&#213;","@");
		special.put("&#214;","@");
		special.put("&#215;","@");
		special.put("&#216;","@");
		special.put("&#217;","@");
		special.put("&#218;","@");
		special.put("&#219;","@");
		special.put("&#220;","@");
		special.put("&#221;","@");
		special.put("&#222;","@");
		special.put("&#223;","@");
		special.put("&#224;","@");
		special.put("&#225;","@");
		special.put("&#226;","@");
		special.put("&#227;","@");
		special.put("&#228;","@");
		special.put("&#229;","@");
		special.put("&#230;","@");
		special.put("&#231;","@");
		special.put("&#232;","@");
		special.put("&#233;","@");
		special.put("&#234;","@");
		special.put("&#235;","@");
		special.put("&#236;","@");
		special.put("&#237;","@");
		special.put("&#238;","@");
		special.put("&#239;","@");
		special.put("&#240;","@");
		special.put("&#241;","@");
		special.put("&#242;","@");
		special.put("&#243;","@");
		special.put("&#244;","@");
		special.put("&#245;","@");
		special.put("&#246;","@");
		special.put("&#247;","@");
		special.put("&#248;","@");
		special.put("&#249;","@");
		special.put("&#250;","@");
		special.put("&#251;","@");
		special.put("&#252;","@");
		special.put("&#253;","@");
		special.put("&#254;","@");
		special.put("&#255;","@");
	}

}
