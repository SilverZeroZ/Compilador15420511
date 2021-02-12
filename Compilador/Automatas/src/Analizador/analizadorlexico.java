package Analizador;
import java.io.*;
import java.util.Vector;
import java.util.regex.Pattern;
public class analizadorlexico
{
	String palabrasR[]={"Begin","End","FindePrograma","Program","do","else","if","int","num","then","while"};

	public int busquedapalres(String palabra)
	{
		int ini=0,fin=palabrasR.length-1;
		do
		{
			int posb=(ini+fin)/2;
			if(palabra.equals(palabrasR[posb]))
				return posb;
			else
				if(palabra.compareTo(palabrasR[posb])>0)
				{
					ini=posb+1;
				}
				else
				{
					fin=posb-1;
				}
		}
		while(ini<=fin);
		return -1;
	}
	public boolean isCaracter(String cad)
	{
		String exp="[a-zA-Z_$]";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isCaracterX(String cad)
	{
		String exp="[a-zA-Z0-9_$]";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isIdent(String cad)
	{
		String exp="[a-zA-Z_$][a-zA-Z0-9_$]*";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isNumx(String cad)
	{
		String exp="[0-9\\.f]";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isEntero(String cad)
	{
		String exp="[0-9]+";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isReal(String cad)
	{
		String exp="[0-9]*\\.[0-9]+[f]*";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isPunto(String cad)
	{
		String exp="[\\.]";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isDelimitador(String cad)
	{
		String exp="[\\(|\\)|\\{|\\}|\\[|\\]|\\\"|\\']";
		return Pattern.matches(exp, cad);
	}
	
	public boolean isOperador(String cad)
	{
		String exp="[\\+\\-\\*\\/\\=\\<\\>]";
		boolean res=Pattern.matches(exp, cad);
		return res;
	}
	
	public boolean isSignoPuntuacion(String cad)
	{
		String exp="[\\;|\\,]";
		return Pattern.matches(exp, cad);
	}
}
