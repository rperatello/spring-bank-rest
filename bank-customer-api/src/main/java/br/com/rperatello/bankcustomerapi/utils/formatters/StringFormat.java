package br.com.rperatello.bankcustomerapi.utils.formatters;

public class StringFormat {

	public static String removeAllSpaces(String text) {
		if (text == null) return text; 
		return text.trim().replaceAll("\\s+", "");
	}
	
	public static String removeDoubleSpace(String text) {
		if (text == null) return text; 
		return text.trim().replaceAll("\\s+", " ");
	}

}
