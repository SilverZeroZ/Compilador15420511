package Analizador;


import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;


class datos{
	String lexema,caracter,linea,id,tipoDato;
	int renglon,columna,contcar;
	boolean error;
	FileReader archl;
	BufferedReader contArch;
	public datos(String l,String caracter2,int r,int c, boolean error2, String id2,String linea2,String tipoDato2, int contcar2)
	{
		lexema=l;caracter=caracter2;
		renglon=r;columna=c;
		error=error2;
		id=id2;
		linea=linea2;
		tipoDato=tipoDato2;
		contcar=contcar2;
	}

}
class estTablaS{
	String id,tipoDato,asignacion;
	int renglon,columna,contcar;
	public estTablaS(String tipoDato2,String id2,String asignacion2,int r,int c, int contcar2)
	{
		
		renglon=r;columna=c;
		id=id2;
		tipoDato=tipoDato2;
		asignacion=asignacion2;
		contcar=contcar2;
	}
}
public class Final
{
	String palabrasR[]={"}"};
  
  Vector <estTablaS> TF=new Vector<estTablaS>();
  Vector <String> TE=new Vector<String>();
  Vector <estTablaS> TablaReservadas=new Vector<estTablaS>();
  Vector <estTablaS> TablaNoIdentificado=new Vector<estTablaS>();
  String lexema,caracter,linea,id,tipoDato,tabula="";
  int renglon,columna,contcar;
  boolean error;
  datos d;
  FileReader archl;
  BufferedReader contArch;
  
  public Final() 
  {
	
  }
  public Final(String ruta) 
  {
	  try
	  {
	  archl=new FileReader(ruta);
	  contArch=new BufferedReader(archl);
	  
	  }
	  catch(IOException e){}
  }
  public int busquedaBin(String pr)
  {  int ini=0,fin=palabrasR.length-1;
     do{
        int posb=(ini+fin)/2;
        if(pr.equals(palabrasR[posb]))
        	   return posb;
        else
        	if(pr.compareTo(palabrasR[posb])>0)
        		ini=posb+1;
        	else
        		fin=posb-1;
     }while(ini<=fin);
     return -1;
  }
  //patrones
  public boolean isCaracter(String cad)
  {
	  String exp="[a-zA-Z_$]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isCaracterX(String cad)
  {
	  String exp="[a-zA-Z0-9_$_-]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isCaracterX3(String cad)
  {
	  String exp="[a-zA-Z0-9_$]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isCaracterX2(String cad)
  {
	  String exp="[a-zA-Z0-9_$_-_.]";
	  return Pattern.matches(exp, cad);
  }
  
  public boolean isSwitch(String cad)
  {
	  return Pattern.matches("(switch)", cad);
  }

  public boolean isIdent(String cad)
  {
	  String exp="[a-zA-Z_$][a-zA-Z0-9_$]*";
	  return Pattern.matches(exp, cad);
  }
  public boolean isCadenaAsignacion(String cad)
  {
	  String exp="[\"][a-zA-Z0-9_$_._ ]*[\"]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isCondicion(String cad)
  {
	  String exp="[<]|[>]|(<=)|(>=)|(==)|(!=)";
	  return Pattern.matches(exp, cad);
  }
  public boolean isOperador(String cad)
  {
	  String exp="[\\+]|[\\-]|[\\*]|[\\/]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isCondicionChar(String cad)
  {
	  String exp="[<]|[>]|[=]|[!]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isIncrementoChar(String cad)
  {
	  String exp="[+]|[-]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isIncremento(String cad)
  {
	  String exp="[\\+][\\+]|[\\-][\\-]";
	  return Pattern.matches(exp, cad);
  }
  public static boolean isInt(String cadena)
  {
	  return Pattern.matches("(int)",cadena);
  }
  public static boolean isIntIdent(String cadena)
  {
	  return Pattern.matches("(int )",cadena);
  }
  public static boolean ischar(String cadena)
  {
	  return Pattern.matches("(char)",cadena);
  }
  public static boolean isString(String cadena)
  {
	  return Pattern.matches("(String)",cadena);
	  
  }
  public static boolean isfloat(String cadena)
  {
	  return Pattern.matches("(float)",cadena);
	  
  }
  public static boolean isFor(String cadena)
  {
	  return Pattern.matches("(for)",cadena);
	  
  }

  public boolean isEntero(String cad)
  {
	  String exp="[0-9]+|[\\-][0-9]+";
	  return Pattern.matches(exp, cad);
  }
  public boolean isCharAsignacion(String cad)
  {
	  String exp="[\\'].[\\']";
	  return Pattern.matches(exp, cad);
  }
  public boolean isReal(String cad)
  {
	  String exp="[0-9]*\\.[0-9]+[f]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isPunto(String cad)
  {
	  String exp="[\\.]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isDelimitador(String cad)
  {
	  String exp="[\\(|\\)|\\{|\\}|\\[|\\]]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isDelimitador2(String cad)
  {
	  String exp="[\\{|\\}]";
	  return Pattern.matches(exp, cad);
  }
  public boolean isSignoPuntuacion(String cad)
  {
	  String exp="[\\;|\\,|\\.]";
	  return Pattern.matches(exp, cad);
  }
  
  
  
  
  public datos Int(datos d)
  {
	  caracter=d.caracter;
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon;
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error;
	  id=d.id;
	  contcar=d.contcar;
	  
	  tipoDato="int";
	   boolean INT=true;
	   boolean r=false;
		do
		{
		   caracter="";
		   lexema="";
		    r=true;
			  do
			  {
				 if(!caracter.equals(" "))
					 lexema+=caracter;
			     columna++;
			     contcar++;
			     if(columna<linea.length())
			     { 
			       caracter=linea.charAt(columna)+"";
			       if(!caracter.equals(" "))
			    	   r=isCaracterX(caracter);
			     }
			     else 
			    	 r=false;
			  }
			  while(r);
			  id=lexema;  
			  if(buscarID(id)) //nombre de id repetido
			  {
				  System.out.println("error nombre de variable duplicada "+id);
				  error=true;
				  break;
				  
			  }
			  if(isIdent(lexema) && caracter.equals(","))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosInt);
			  }
			  if(isIdent(lexema) && caracter.equals(";"))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosInt);
			  }
			  if(caracter.equals("="))
			  {
				  id=lexema;
				  caracter="";
				  lexema="";
				  r=true;
				  do
				  {
					  if(!caracter.equals(" "))
						  lexema+=caracter;
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" "))
				    	   r=isCaracterX(caracter);
				       
				     }
				     else 
				    	 r=false;
				  }
				  while(r);
				  if(isEntero(lexema) && caracter.equals(","))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
					  estTablaS datosInt=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
					  TF.add(datosInt);
				  }
				  else
				  {
					  if(isEntero(lexema) && caracter.equals(";"))
					  {
						  System.out.println(tabula+"tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
						  estTablaS datosInt=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
						  TF.add(datosInt);
					  }
					  else
					  {
						  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
						  error=true;
						  break; 
						  
					  }
					  
				  }
				  
				 
			  }
	
			  if(caracter.equals(";"))
			  {
				  INT=false;
				  columna++;
				  contcar++;
			  }
			  if(!isIdent(id))
			  {
				  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
				  error=true;
				  break;
			  }
			  if(isIdent(lexema) && caracter.equals(":"))
			  {
				  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
				  error=true;
				  break;
			  }
		}
		while(INT);
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  
  public datos Float(datos d)
  {
	  caracter=d.caracter;
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon;
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error;
	  id=d.id;
	  contcar=d.contcar;
	  
	  tipoDato="float";
	   boolean INT=true;
	   boolean r=false;
		do
		{
		   caracter="";
		   lexema="";
		    r=false;
			  do
			  {
				 if(!caracter.equals(" "))
					 lexema+=caracter;
			     columna++;
			     contcar++;
			     if(columna<linea.length())
			     { 
			       caracter=linea.charAt(columna)+"";
			       if(!caracter.equals(" "))
			    	   r=isIdent(caracter);
			     }
			     else 
			    	 r=false;
			  }
			  while(r);
			  id=lexema;  
			  if(buscarID(id)) //nombre de id repetido
			  {
				  System.out.println("error nombre de variable duplicada "+id);
				  error=true;
				  break;
				  
			  }
			  if(isIdent(lexema) && caracter.equals(","))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosInt);
			  }
			  if(isIdent(lexema) && caracter.equals(";"))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosInt);
			  }
			  if(caracter.equals("="))
			  {
				  id=lexema;
				  caracter="";
				  lexema="";
				  r=true;
				  do
				  {
					  if(!caracter.equals(" "))
						  lexema+=caracter;
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" "))
				    	   r=isCaracterX2(caracter);
				       
				     }
				     else 
				    	 r=false;
				  }
				  while(r);
				  if(isReal(lexema) && caracter.equals(","))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
					  estTablaS datosInt=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
					  TF.add(datosInt);
				  }
				  else
				  {
					  if(isReal(lexema) && caracter.equals(";"))
					  {
						  System.out.println(tabula+"tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
						  estTablaS datosInt=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
						  TF.add(datosInt);
					  }
					  else
					  {
						  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
						  error=true;
						  break; 
						  
					  }
					  
				  }
				  
				 
			  }
	
			  if(caracter.equals(";"))
			  {
				  INT=false;
				  columna++;
				  contcar++;
			  }
			  if(!isIdent(id))
			  {
				  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
				  error=true;
				  break;
			  }
			  if(isIdent(lexema) && caracter.equals(":"))
			  {
				  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
				  error=true;
				  break;
			  }
		}
		while(INT);
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  
  public datos Char(datos d)
  {
	  caracter=d.caracter;
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon;
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error;
	  id=d.id;
	  contcar=d.contcar;
	  
	  tipoDato="char";
	   boolean INT=true;
	   boolean r=false;
		do
		{
		   caracter="";
		   lexema="";
		    r=true;
			  do
			  {
				  if(!caracter.equals(" "))
					  lexema+=caracter;
			     columna++;
			     contcar++;
			     if(columna<linea.length())
			     { 
			       caracter=linea.charAt(columna)+"";
			       if(!caracter.equals(" "))
			    	   r=isIdent(caracter);
			     }
			     else 
			    	 r=false;
			  }
			  while(r);
			  
			  id=lexema;
			  
			  if(buscarID(id)) //nombre de id repetido
			  {
				  System.out.println("error nombre de variable duplicada "+id);
				  error=true;
				  break;
				  
			  }
				  
			  if(isIdent(lexema) && caracter.equals(","))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosChar);
				  
			  }
			  if(isIdent(lexema) && caracter.equals(";"))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosChar);
			  }
			  if(caracter.equals("="))
			  {
				  id=lexema;
				  caracter="";
				  lexema="";
				  boolean es=true;
				  do //quitar espacios en blanco 
				  {
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" "))
				    	   es=false;
				     }
				     else 
				    	 es=false;
				  }
				  while(es);
				  int le=0;
				  columna--;
				  contcar--;
				  caracter="";
				  do
				  {
				     lexema+=caracter; 
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       le++;
				     }
				     else 
				    	 r=false;
				  }
				  while(le!=4);
				  es=true;
				  if(caracter.equals(" "))
				  {
					  do //quitar espacios en blanco 
					  {
					     columna++;
					     contcar++;
					     if(columna<linea.length())
					     { 
					       caracter=linea.charAt(columna)+"";
					       if(!caracter.equals(" "))
					    	   es=false;
					     }
					     else 
					    	 es=false;
					  }
					  while(es);
				  }
				  if(isCharAsignacion(lexema) && caracter.equals(","))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
					  TF.add(datosChar);
				  }
				  else
				  {
					  if(isCharAsignacion(lexema) && caracter.equals(";"))
					  {
						  System.out.println(tabula+"tipo de dato  "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
						  estTablaS datosChar=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
						  TF.add(datosChar);
					  }
					  else
					  {
						  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
						  error=true;
						  break; 
						  
					  }
					  
				  }
				  
				 
			  }
	
			  if(caracter.equals(";"))
			  {
				  INT=false;
				  columna++;
				  contcar++;
			  }
			  if(!isIdent(id))
			  {
				  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
				  error=true;
				  break;
			  }
			  if(isIdent(lexema) && caracter.equals(":"))
			  {
				  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
				  error=true;
				  break;
			  }
		}
		while(INT);
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  public datos String(datos d)
  {
	  caracter=d.caracter;  
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon; 
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error;
	  id=d.id;
	  contcar=d.contcar;
	  
	  tipoDato="String";
	   boolean INT=true;
	   boolean r=false;
		do
		{
		   caracter="";
		   lexema="";
		    r=true;
			  do
			  {
				  if(!caracter.equals(" "))
					  lexema+=caracter;
			     columna++;
			     contcar++;
			     if(columna<linea.length())
			     { 
			       caracter=linea.charAt(columna)+"";
			       if(!caracter.equals(" "))
			    	   r=isIdent(caracter);
			     }
			     else 
			    	 r=false;
			  }
			  while(r);
			  
			  id=lexema;
			  
			  if(buscarID(id)) //nombre de id repetido
			  {
				  System.out.println("error nombre de variable duplicada "+id);
				  error=true;
				  break;
				  
			  }
				  
			  if(isIdent(lexema) && caracter.equals(","))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosChar);
				  
			  }
			  if(isIdent(lexema) && caracter.equals(";"))
			  {
				  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
				  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
				  TF.add(datosChar);
			  }
			  if(caracter.equals("=")) 
			  {
				  id=lexema;
				  caracter="";
				  lexema="";
				  
				  boolean es=true;
				  do //quitar espacios en blanco 
				  {
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" "))
				    	   es=false;
				     }
				     else 
				    	 es=false;
				  }
				  while(es);
				  int le=0;
				  columna--;
				  contcar--;
				  caracter="";
				  do
				  {
				     lexema+=caracter;
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(caracter.equals("\""))
				       {
				    	   le++;
				    	   do
							  {
							     lexema+=caracter;
							     columna++;
							     contcar++;
							     if(columna<linea.length())
							     { 
							       caracter=linea.charAt(columna)+"";
							       if(caracter.equals("\""))
							       {
							    	   le++;
							       }
							     }
							     else 
							     {
							    	 System.out.println("error se esperaba un terminal \" pos: "+contcar);
 							    	 error=true;
 							    	 break;
							     }
							  }
							  while(le!=2);
				       }
				       else
				       {
				    	   error=true;
				    	   break; 
				       }
				     }
				     else 
				     {
				    	 if(error)
				    		 return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar); 
				     }
				  }
				  while(le!=2);
				  lexema+=caracter;
				  es=true;
				  do //quitar espacios en blanco
				  {
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" "))
				    	   es=false;
				     }
				     else 
				    	 es=false;
				  }
				  while(es);
				  
				  if(isCadenaAsignacion(lexema) && caracter.equals(","))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
					  TF.add(datosChar);
				  }
				  else
				  {
					  if(isCadenaAsignacion(lexema) && caracter.equals(";"))
					  {
						  System.out.println(tabula+"tipo de dato  "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
						  estTablaS datosChar=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
						  TF.add(datosChar);
					  }
					  else
					  {
						  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
						  error=true;
						  break; 
						  
					  }
					  
				  }
				  
				 
			  }
	
			  if(caracter.equals(";"))
			  {
				  INT=false;
				  columna++;
				  contcar++;
			  }
			  if(!isIdent(id))
			  {
				  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
				  error=true;
				  break;
			  }
			  if(isIdent(lexema) && caracter.equals(":"))
			  {
				  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
				  error=true;
				  break;
			  }
		}
		while(INT);
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  } 
  
  public datos For(datos d)
  {
	  caracter=d.caracter;    
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon; 
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error; 
	  id=d.id;
	  contcar=d.contcar;
	  
	  tipoDato="String";
	   boolean INT=true;
	   boolean r=false;
		do
		{
	       columna--;
		   contcar--;
		   caracter="";
		   lexema="";
		    r=true;
			  do
			  {
				  if(!caracter.equals(" ")) 
					  lexema+=caracter;
			     columna++;
			     contcar++;
			     if(columna<linea.length())
			     { 
			       caracter=linea.charAt(columna)+"";
			       if(!caracter.equals(" "))
			    	   if(caracter.equals("("))
			    	   {
			    		  caracter="";
			    		  lexema="";
			    		  do //----------------Despues del (
			 			  {
			 				  if(!caracter.equals(" "))
			 					  lexema+=caracter;
			 			     columna++;
			 			     contcar++;
			 			     if(columna<linea.length())
			 			     { 
			 			       caracter=linea.charAt(columna)+"";
			 			        r=isCaracterX(caracter);
			 			     }
			 			     else 
			 			    	 r=false;
			 			  }
			 			  while(r); //----------despues del (
			    		  	  
			    		      d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
							  d=ForAsignacionID(d);
							  caracter=d.caracter;  
							  lexema=d.lexema;
							  columna=d.columna;
							  renglon=d.renglon;
							  tipoDato=d.tipoDato;
							  linea=d.linea;
							  error=d.error;
							  id=d.id; 
							  contcar=d.contcar;
							  if(caracter.equals(")"))
							  {
								  lexema="";
								  caracter="";
								  r=true;
								  do
					 			  {
					 				  if(!caracter.equals(" "))
					 					  lexema+=caracter;
					 			     columna++;
					 			     contcar++;
					 			     if(columna<linea.length())
					 			     { 
					 			       caracter=linea.charAt(columna)+"";
					 			      if(!caracter.equals(" "))
					 			        r=isDelimitador(caracter);
					 			     }
					 			     else 
					 			    	 r=false;
					 			  }
					 			  while(r);
								  renglon++;
								  contcar++;
								  try
								  {
									  
									  do
									  {
										linea=contArch.readLine();
										columna=0;
										if(linea!=null)
										{ 
											//linea=linea.trim();
											do{
										    	if(columna<linea.length())
										    	{ 
										    		caracter=linea.charAt(columna)+"";
										    		if(caracter.equals(" "))
										    		{
										    			columna++;
										    			contcar++;
										    		}
										    		if(caracter.equals("\t"))
										    		{
										    			columna++;
										    			contcar++;
										    		}
										    		caracter=linea.charAt(columna)+"";
										    		lexema="";
										    		do
										 			  {
										 				  if(!caracter.equals(" "))
										 					  lexema+=caracter;
										 			     columna++;
										 			     contcar++;
										 			     if(columna<linea.length())
										 			     { 
										 			       caracter=linea.charAt(columna)+"";
										 			      if(!caracter.equals(" "))
										 		 	        r=isDelimitador(caracter);
										 			     }
										 			     else 
										 			    	 r=false;
										 			  }
										 			  while(r);
										    		  if(lexema.equals("{"))
													  {
										    			  
										    			  System.out.println(tabula+"{");
										    			  tabula+="\t";
										    			  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
														  d=Proposicion(d); 
														  caracter=d.caracter;  
														  lexema=d.lexema;
														  columna=d.columna;
														  renglon=d.renglon;
														  tipoDato=d.tipoDato;
														  linea=d.linea;
														  error=d.error;
														  id=d.id; 
														  contcar=d.contcar;
														  INT=false;
														  if(error)
															  break; 
														  if(caracter.equals("}"))
														  {
															  columna++;
															  contcar++;
														  }
														  else
														  {
															  System.out.println("error se esperaba un terminal \"}\"");
															  error=true;
															  break;
														  }
														  System.out.println("}");
														  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
													  }
													  else
													  {
														  
														  System.out.println("error se esperaba un delimitador \"{\"");
														  error=false;
														  break;
														  
													  }
										    		  
												   
										    	}
											}
											while(columna<linea.length() && error==false);
											
										}
										renglon++;
										contcar++;
									  }
									  while(linea!=null && error==false);
								  }
							
								  catch (IOException e) 
								  {
									
								  }
								  
							  }
							 
			    		   
			    	   }
			    	   else
			    	   {
			    		 System.out.println("error se esperaba un delimitador \"(\"");
			    		 error=true;
			    		 break;
			    	   }
			     }
			     else 
			    	 r=false;
			  }
			  while(r && error==false);
			  
		}
		while(INT && error==false);
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  
  public datos ForAsignacionID(datos d)
  {
	  String condicion="",ini="",con1="",con2="",incre="";
	  caracter=d.caracter;  
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon;
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error;
	  id=d.id;
	  contcar=d.contcar;
	  
	  id=lexema;
	  boolean r=false;
	  do
	  {
		  if(buscarID(id)) //Ver si existe el id
		  {
			  estTablaS idfind;
			  idfind=traerID(id);
			    
				caracter="";
				lexema="";
			    r=true;
			    columna--;
				contcar--;
				  do
				  {
					 if(!caracter.equals(" "))
						 lexema+=caracter;
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" ")) 
				    	   if(caracter.equals("=")) 
				    	   {
				    		   if(idfind.tipoDato.equals("int"))
				    		   {
				    			  
				 				  caracter="";
				 				  lexema="";
				 				  r=true;
				 				  do
				 				  {
				 					  if(!caracter.equals(" "))
				 						  lexema+=caracter;
				 				     columna++;
				 				     contcar++;
				 				     if(columna<linea.length())
				 				     { 
				 				       caracter=linea.charAt(columna)+"";
				 				       if(!caracter.equals(" "))
				 				    	   r=isCaracterX(caracter);
				 				       
				 				     }
				 				     else 
				 				    	 r=false;
				 				  }
				 				  while(r);
				 				 
				 				 if(isEntero(lexema) && caracter.equals(";"))
			 					  {
				 					  idfind.asignacion=lexema;
			 						 // System.out.println(tabula+"for("+id+"="+lexema+";");
			 						  ini=id+"="+lexema;
			 						  contcar++;
			 						  
			 					  }
				 				 else
				 				 {
					 				 if(isEntero(lexema) && caracter.equals(","))
				 					  {
					 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
				 						  error=true;
				 						  break; 
				 					  }
				 					  else
				 					  {
				 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
				 						  error=true;
				 						  break; 
				 						  
				 					  }
				 				 }
				 					  
				    		   }			    		   
				    	   }
				    	   else
				    	   {
				    		   System.out.println("error se esperaba una asignacion =");
				    		   error=true;
		 					   break; 
				    	   }
				     }
				     else 
				    	 r=false;
				  }
				  while(r);	  
		  }
		  else
		  {
			  System.out.println("error \""+id+ "\" no se encuentra declarado pos: "+contcar);
			  error=true;
			  break;
		  }
		  //-------------------------------------------- condicion del for----------------------------------
		  if(error)
			  break;
		  caracter="";
		  lexema="";
		  r=true;
		  do
		  {
			  if(!caracter.equals(" "))
				  lexema+=caracter;
		     columna++;
		     contcar++;
		     if(columna<linea.length())
		     { 
		       caracter=linea.charAt(columna)+"";
		       if(!caracter.equals(" "))
		    	   r=isCaracterX(caracter);
		       
		     }
		     else 
		    	 r=false;
		  } 
		  while(r);  	  
		  if(buscarID(lexema)) //Ver si existe el id
		  {
			  estTablaS idfind2;
			  idfind2=traerID(lexema);
			  if(!idfind2.asignacion.equals(""))
			  {
				  con1=lexema;
				  lexema="";
				  r=true;
				  do //condicion < > <= >= == !=
				  {
					 lexema+=caracter;
				     columna++;
				     contcar++;
				     if(columna<linea.length()) 
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!isCondicionChar(caracter))
				    	   r=false;
				     }
				     else 
				    	 r=false;
				  }
				  while(r);
				  if(isCondicion(lexema))
				  {
					  condicion=lexema;
					  lexema="";
					  r=true;
					  do
					  {
						  if(!caracter.equals(" "))
							  lexema+=caracter;
					     columna++;
					     contcar++;
					     if(columna<linea.length())
					     { 
					       caracter=linea.charAt(columna)+"";
					       if(!caracter.equals(" "))
					    	   r=isCaracterX(caracter);
					       
					     }
					     else 
					    	 r=false;
					  }
					  while(r); 
					  
					  if(isEntero(lexema) && caracter.equals(";"))
						  {
	 					      
						  	  con2=lexema;
							 // System.out.println("for("+ini+";"+con1+condicion+con2+";");
							  lexema="";
							  caracter="";
								  r=true;
								  do
								  {
									  if(!caracter.equals(" "))
										  lexema+=caracter;
								     columna++;
								     contcar++;
								     if(columna<linea.length())
								     { 
								       caracter=linea.charAt(columna)+"";
								       if(!caracter.equals(" "))
								    	   r=isCaracterX(caracter);
								       
								     }
								     else 
								    	 r=false;
								  }
								  while(r); 
								  if(buscarID(lexema)) //Ver si existe el id
								  {
									  estTablaS idfind3;
									  idfind3=traerID(lexema);
									  if(!idfind3.asignacion.equals(""))
									  {
										  con1=lexema;
										  lexema="";
										  r=true;
										  do ///////INCREMENTO ++
										  {
											  if(!caracter.equals(" "))
												  lexema+=caracter;
										     columna++;
										     contcar++;
										     if(columna<linea.length())
										     { 
										       caracter=linea.charAt(columna)+"";
										       if(!caracter.equals(" "))
										    	   r=isIncrementoChar(caracter);
										     }
										     else 
										    	 r=false;
										  }
										  while(r); 
										  if(isIncremento(lexema) && caracter.equals(")"))
										  {
											  incre=idfind3.id+lexema;
											  System.out.println(tabula+"for("+ini+";"+con1+condicion+con2+";"+incre+")");
										  }
										  else
										  {
											  if(isIncremento(lexema) && !caracter.equals(")"))
											  {
												  System.out.println("error se esperaba un delimitador \")\"");
											  }
											  else
											  {
												  System.out.println("Error en la declaracion de incremento");
											  }
										  }
									  }
									  else
	 								  {
	 									  System.out.println("error \""+idfind3.id+ "\" no se ha inicializado: "+contcar);
	 									  error=true;
	 									  break;
	 								  }
								  }
								  else
	 							  {
	 								  System.out.println("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar);
	 								  error=true;
	 								  break;
	 							  }
								  
							  
						  }
	 				  else
	 				  {
		 				 if(isEntero(lexema) && caracter.equals(","))
	 					  {
		 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
	 						  error=true;
	 						  break;
	 						   
	 					  }
	 					  else
	 					  {
	 						 if(isIdent(lexema) && caracter.equals(";"))
	 						 {
	 							 
	 							if(buscarID(lexema)) //Ver si existe el id
	 							  {   
	 								  estTablaS idfind;
	 								  idfind=traerID(lexema);
	 								  if(!idfind.asignacion.equals(""))
	 								  {
	 									  con2=lexema;   
	 									  //System.out.println("for("+ini+";"+con1+condicion+con2+";");
	 ///////////////////////////////////////INCREMENTOOOOOOOOOOO//////////////////////////////////////////////////////////////
	 									 lexema="";
	 									  caracter="";
	 										  r=true;
	 										  do
	 										  {
	 											  if(!caracter.equals(" "))
	 												  lexema+=caracter;
	 										     columna++;
	 										     contcar++;
	 										     if(columna<linea.length())
	 										     { 
	 										       caracter=linea.charAt(columna)+"";
	 										       if(!caracter.equals(" "))
	 										    	   r=isCaracterX(caracter);
	 										       
	 										     }
	 										     else 
	 										    	 r=false;
	 										  }
	 										  while(r); 
	 										  if(buscarID(lexema)) //Ver si existe el id
	 										  {
	 											  estTablaS idfind3;
	 											  idfind3=traerID(lexema);
	 											  if(!idfind3.asignacion.equals(""))
	 											  {
	 												 
	 												  lexema="";
	 												  r=true;
	 												  do ///////INCREMENTO ++
	 												  {
	 													  if(!caracter.equals(" "))
	 														  lexema+=caracter;
	 												     columna++;
	 												     contcar++;
	 												     if(columna<linea.length())
	 												     { 
	 												       caracter=linea.charAt(columna)+"";
	 												       if(!caracter.equals(" "))
	 												    	   r=isIncrementoChar(caracter);
	 												     }
	 												     else 
	 												    	 r=false;
	 												  }
	 												  while(r); 
	 												  if(isIncremento(lexema) && caracter.equals(")"))
	 												  {
	 													  incre=idfind3.id+lexema;
	 													  System.out.println(tabula+"for("+ini+";"+con1+condicion+con2+";"+incre+")");
	 												  }
	 												  else
	 												  {
	 													  if(isIncremento(lexema) && !caracter.equals(")"))
	 													  {
	 														  System.out.println("error se esperaba un delimitador \")\"");
	 													  }
	 													  else
	 													  {
	 														  System.out.println("Error en la declaracion de incremento");
	 													  }
	 												  }
	 											  }
	 											  else
	 			 								  {
	 			 									  System.out.println("error \""+idfind3.id+ "\" no se ha inicializado: "+contcar);
	 			 									  error=true;
	 			 									  break;
	 			 								  }
	 										  }
	 										  else
	 			 							  {
	 			 								  System.out.println("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar);
	 			 								  error=true;
	 			 								  break;
	 			 							  } 
	 								  }
	 								  else
	 								  {
	 									  System.out.println("error \""+lexema+ "\" no se ha inicializado: "+contcar);
	 									  error=true;
	 									  break;
	 								  }
	 							  }
	 							  else
	 							  {
	 								  System.out.println("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar);
	 								  
	 								  error=true;
	 								  break;
	 							  }
	 						 }
	 						else
	 		 				{
	 			 				if(isIdent(lexema) && caracter.equals(","))
	 		 					 {
	 			 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
	 		 						  error=true;
	 		 						  break;
	 		 						   
	 		 					 }
	 			 				else
	 			 				{
	 			 					  System.out.println("error en la condicion pos: "+contcar);
			 						  error=true;
			 						 break;
	 			 				}
	 						  
	 					    }
	 					  }
	 				   }/////////////////////////////////////////////////////////////////////////////7	  
				  }
				  else
				  {
					  System.out.println("error "+lexema+ " no es una condicion valida: "+contcar);
					  error=true;
					  break;
				  }
				  
			  }
			  else
			  {
				  System.out.println("error "+idfind2.id+ " no se ha inicializado: "+contcar);
				  error=true;
				  break;
			  }
		  }
		  else
		  {
			  System.out.println("error "+lexema+ " no se encuentra declarado pos: "+contcar);
			  
			  error=true;
			  break;
		  }
	} 
	while(r);
		  
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  
  public datos IdAsignacion(datos d)
  {
	  caracter=d.caracter;   
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon;
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error;
	  id=d.id;
	  contcar=d.contcar;
	  
	  id=lexema;
	  
	  
	  if(buscarID(id)) //Ver si existe el id
	  {
		  estTablaS idfind;
		  idfind=traerID(id);
		    boolean r=false;
			caracter="";
			lexema="";
		    r=true;
		    columna--;
			contcar--;
			  do
			  {
				 if(!caracter.equals(" "))
					 lexema+=caracter;
			     columna++;
			     contcar++;
			     if(columna<linea.length())
			     { 
			       caracter=linea.charAt(columna)+"";
			       if(!caracter.equals(" ")) 
			    	   if(caracter.equals("=")) 
			    	   {
			    		   if(idfind.tipoDato.equals("int"))
			    		   {
			    			  
			 				  caracter="";
			 				  lexema="";
			 				  r=true;
			 				  do
			 				  {
			 					  if(!caracter.equals(" "))
			 						  lexema+=caracter;
			 				     columna++;
			 				     contcar++;
			 				     if(columna<linea.length())
			 				     { 
			 				       caracter=linea.charAt(columna)+"";
			 				       if(!caracter.equals(" "))
			 				    	   r=isCaracterX(caracter);
			 				       
			 				     }
			 				     else 
			 				    	 r=false;
			 				  }
			 				  while(r);
			 				 
			 				 if(isEntero(lexema) && caracter.equals(";"))
		 					  {
			 					  idfind.asignacion=lexema;
		 						  System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);		 						  
		 						  columna++;
		 						  contcar++;
		 					  }
			 				 else
			 				 {
			 					if(isEntero(lexema) && isOperador(caracter))
			 					  {
			 						  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
									  d=IdOperacion(d);
									  caracter=d.caracter; 
									  lexema=d.lexema;
									  columna=d.columna;
									  renglon=d.renglon;
									  tipoDato=d.tipoDato;
									  linea=d.linea;
									  error=d.error;
									  id=d.id; 
									  contcar=d.contcar;
			 					  }
			 					 else
			 					 {
			 						if(isIdent(lexema) && isOperador(caracter))
				 					  {
				 						  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
										  d=IdOperacion(d); 
										  caracter=d.caracter; 
										  lexema=d.lexema;
										  columna=d.columna;
										  renglon=d.renglon;
										  tipoDato=d.tipoDato;
										  linea=d.linea;
										  error=d.error;
										  id=d.id; 
										  contcar=d.contcar;
				 					  }
			 						else
			 						{
			 							if(isIdent(lexema) && caracter.equals(";"))
					 					  {
			 								  estTablaS idfind3;
			 								  idfind3=traerID(lexema);
			 								  if(idfind3.tipoDato.equals("int"))
			 								  {
			 									 idfind.asignacion=lexema;
			 									System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);
			 									columna++;
						 						contcar++;
			 								  }
			 								  else
			 								  {
			 									  System.out.println("Error en el tipo de dato se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
			 									  error=true;
			 									  break;  
			 								  }
						 					  
					 					  }
			 							else
			 							{
							 				 if(isEntero(lexema) && caracter.equals(","))
						 					  {
							 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
						 						  error=true;
						 						  break; 
						 					  }
						 					  else
						 					  {
						 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
						 						  error=true;
						 						  break; 
						 						  
						 					  }
			 							}
			 						}
			 					 }
			 				 }
			 					  
			    		   }
			    		   //-------------------------------------------------------------------------------------------
			    		   if(idfind.tipoDato.equals("float"))
			    		   {
			    			  
			 				  caracter="";
			 				  lexema="";
			 				  r=true;
			 				  do
			 				  {
			 					  if(!caracter.equals(" "))
			 						  lexema+=caracter;
			 				     columna++;
			 				     contcar++;
			 				     if(columna<linea.length())
			 				     { 
			 				       caracter=linea.charAt(columna)+"";
			 				       if(!caracter.equals(" "))
			 				    	   r=isCaracterX2(caracter);
			 				       
			 				     }
			 				     else 
			 				    	 r=false;
			 				  }
			 				  while(r);
			 				 
			 				 if(isReal(lexema) && caracter.equals(";"))
		 					  {
			 					  idfind.asignacion=lexema;
		 						  System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);		 						  
		 						  columna++;
		 						  contcar++;
		 					  }
			 				 else
			 				 {
				 				 if(isReal(lexema) && caracter.equals(","))
			 					  {
				 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
			 						  error=true;
			 						  break; 
			 					  }
			 					  else
			 					  {
			 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
			 						  error=true;
			 						  break; 
			 						  
			 					  }
			 				 }
			 					  
			    		   }
			    		 //-------------------------------------------------------------------------------------------
			    		   if(idfind.tipoDato.equals("char"))
			    		   {
			    			  
			    			   caracter="";
			 				  lexema="";
			 				  boolean es=true;
			 				  do //quitar espacios en blanco 
			 				  {
			 				     columna++;
			 				     contcar++;
			 				     if(columna<linea.length())
			 				     { 
			 				       caracter=linea.charAt(columna)+"";
			 				       if(!caracter.equals(" "))
			 				    	   es=false;
			 				     }
			 				     else 
			 				    	 es=false;
			 				  }
			 				  while(es);
			 				  int le=0;
			 				  columna--;
			 				  contcar--;
			 				  caracter="";
			 				  do
			 				  {
			 				     lexema+=caracter; 
			 				     columna++;
			 				     contcar++;
			 				     if(columna<linea.length())
			 				     { 
			 				       caracter=linea.charAt(columna)+"";
			 				       le++;
			 				     }
			 				     else 
			 				    	 r=false;
			 				  }
			 				  while(le!=4);
			 				  es=true;
			 				  if(caracter.equals(" "))
			 				  {
			 					  do //quitar espacios en blanco 
			 					  {
			 					     columna++;
			 					     contcar++;
			 					     if(columna<linea.length())
			 					     { 
			 					       caracter=linea.charAt(columna)+"";
			 					       if(!caracter.equals(" "))
			 					    	   es=false;
			 					     }
			 					     else 
			 					    	 es=false;
			 					  }
			 					  while(es);
			 				  }
			 				 
			 				 if(isCharAsignacion(lexema) && caracter.equals(";"))
		 					  {
			 					  idfind.asignacion=lexema;
		 						  System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);		 						  
		 						  columna++;
		 						  contcar++;
		 					  }
			 				 else
			 				 {
				 				 if(isCharAsignacion(lexema) && caracter.equals(","))
			 					  {
				 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
			 						  error=true;
			 						  break; 
			 					  }
			 					  else
			 					  {
			 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
			 						  error=true;
			 						  break; 
			 						  
			 					  }
			 				 }
			 					  
			    		   }
			    		 //-------------------------------------------------------------------------------------------
			    		   if(idfind.tipoDato.equals("String"))
			    		   {
			    			   caracter="";
			 				  lexema="";
			 				  
			 				  boolean es=true;
			 				  do //quitar espacios en blanco 
			 				  {
			 				     columna++;
			 				     contcar++;
			 				     if(columna<linea.length())
			 				     { 
			 				       caracter=linea.charAt(columna)+"";
			 				       if(!caracter.equals(" "))
			 				    	   es=false;
			 				     }
			 				     else 
			 				    	 es=false;
			 				  }
			 				  while(es);
			 				  int le=0;
			 				  columna--;
			 				  contcar--;
			 				  caracter="";
			 				  do
			 				  {
			 				     lexema+=caracter;
			 				     columna++;
			 				     contcar++;
			 				     if(columna<linea.length())
			 				     { 
			 				       caracter=linea.charAt(columna)+"";
			 				       if(caracter.equals("\""))
			 				       {
			 				    	   le++;
			 				    	   do
			 							  {
			 							     lexema+=caracter;
			 							     columna++;
			 							     contcar++;
			 							     if(columna<linea.length())
			 							     { 
			 							       caracter=linea.charAt(columna)+"";
			 							       if(caracter.equals("\""))
			 							       {
			 							    	   le++;
			 							       }
			 							     } 
			 							     else 
			 							     {
			 							    	 System.out.println("error se esperaba un terminal \" pos: "+contcar);
			 							    	 error=true;
			 							    	 break;
			 							     }
			 							  }
			 							  while(le!=2);
			 				       }
			 				       else
			 				       {
			 				    	   error=true;
			 				    	   break; 
			 				       }
			 				     }
			 				     else 
			 				     {
			 				    	 if(error)
			 				    		 return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
			 				     }
			 				  }
			 				  while(le!=2);
			 				  lexema+=caracter;
			 				  es=true;
			 				  do //quitar espacios en blanco
			 				  {
			 				     columna++;
			 				     contcar++;
			 				     if(columna<linea.length())
			 				     { 
			 				       caracter=linea.charAt(columna)+"";
			 				       if(!caracter.equals(" "))
			 				    	   es=false;
			 				     }
			 				     else 
			 				    	 es=false;
			 				  }
			 				  while(es);
			 				 
			 				 if(isCadenaAsignacion(lexema) && caracter.equals(";"))
		 					  {
			 					  idfind.asignacion=lexema;
		 						  System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);		 						  
		 						  columna++;
		 						  contcar++;
		 					  }
			 				 else
			 				 {
				 				 if(isCadenaAsignacion(lexema) && caracter.equals(","))
			 					  {
				 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
			 						  error=true;
			 						  break; 
			 					  }
			 					  else
			 					  {
			 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
			 						  error=true;
			 						  break; 
			 						  
			 					  }
			 				 }
			 					  
			    		   }
			    		   
			    		   			    		   
			    		   
			    	   }
			    	   else
			    	   {
			    		   System.out.println("error se esperaba una asignacion =");
			    		   error=true;
	 					   break; 
			    	   }
			     }
			     else 
			    	 r=false;
			  }
			  while(r);
		  
	  }
	  else
	  {
		  if(id.equals("mensaje"))
		  {
			  if(caracter.equals("("))
   		   {
   			  caracter="";
				  lexema="";
				  
				  boolean es=true;
				  do //quitar espacios en blanco 
				  {
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" "))
				    	   es=false;
				     }
				     else 
				    	 es=false;
				  }
				  while(es);
				  int le=0;
				  columna--;
				  contcar--;
				  caracter="";
				  do
				  {
				     lexema+=caracter;
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(caracter.equals("\""))
				       {
				    	   le++;
				    	   do
							  {
							     lexema+=caracter;
							     columna++;
							     contcar++;
							     if(columna<linea.length())
							     { 
							       caracter=linea.charAt(columna)+"";
							       if(caracter.equals("\""))
							       {
							    	   le++;
							       }
							     } 
							     else 
							     {
							    	 System.out.println("error se esperaba un terminal \" pos: "+contcar);
				//			    	Consola("error se esperaba un terminal \" pos: "+contcar, contcar, true);
							    	 error=true;
							    	 break;
							     }
							  }
							  while(le!=2);
				       }
				       else
				       {
				    	   error=true;
				    	   break; 
				       }
				     }
				     else 
				     {
				    	 if(error)
				    		 return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
				     }
				  }
				  while(le!=2);
				  lexema+=caracter;
				  es=true;
				  do //quitar espacios en blanco
				  {
				     columna++;
				     contcar++;
				     if(columna<linea.length())
				     { 
				       caracter=linea.charAt(columna)+"";
				       if(!caracter.equals(" "))
				    	   es=false;
				     }
				     else 
				    	 es=false;
				  }
				  while(es);
				  if(caracter.equals(")"))
				  {
					 //Consola(lexema+" pos: "+contcar, contcar, false);
					  System.out.println("mensa("+lexema+");");
					  columna++;
					  contcar++;
				  }
   		   }
		  }
		  else
		  {
			  System.out.println("error "+id+ " no se encuentra declarado pos: "+contcar);
			  
			  error=true;  
		  }
		  
	  }
	 
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  
  public datos IdOperacion(datos d)
  {
	  estTablaS idfind;
	  idfind=traerID(id); 
	  caracter=d.caracter;  
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon;
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error;
	  id=d.id;
	  contcar=d.contcar;
	  
	  id=lexema;
	  idfind.asignacion=lexema+caracter;
	  boolean r=true,Termino=true;
	  do
	  {
		  caracter="";
		  lexema="";
		  do
		  {
			  if(!caracter.equals(" "))
				  lexema+=caracter;
		     columna++;
		     contcar++;
		     if(columna<linea.length())
		     { 
		       caracter=linea.charAt(columna)+"";
		       if(!caracter.equals(" "))
		    	   r=isCaracterX3(caracter);
		       
		     }
		     else 
		    	 r=false;
		  }
		  while(r);
		  if(isEntero(lexema) && isOperador(caracter))
		  {
			  idfind.asignacion+=lexema+caracter;
			  //System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);
			  System.out.println(idfind.id+"="+idfind.asignacion);
		  }
		 else
		 {
			 if(isEntero(lexema) && caracter.equals(";"))
			  {
				  idfind.asignacion+=lexema;
				  //System.out.println("Error se esperaba un terminal ; pos:"+contcar);
				  System.out.println(idfind.id+"="+idfind.asignacion);
				  contcar++;
				  columna++;
				  Termino=false;
				  break;
			  }
			 else
			 {
				 if(isEntero(lexema) && !isOperador(caracter) && caracter.equals("."))
				  {
					 System.out.println("Error en el tipo de dato de \""+lexema+caracter +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
					  error=true;
					  break; 
				  }
				 else
				 {
					 if(!isEntero(lexema) && !isIdent(lexema))
					  {
						 System.out.println("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
						  error=true;
						  break; 
					  }
					 else
					 {
						 if(isIdent(lexema) && isOperador(caracter))
						  {
							  estTablaS idfind2;
							  if(buscarID(lexema))
							  {
								  idfind2=traerID(lexema);
								  if(idfind2.tipoDato.equals("int"))
								  {
									  idfind.asignacion+=lexema+caracter;
									  //System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);
									  System.out.println(idfind.id+"="+idfind.asignacion);
								  }
								  else
								  {
									  System.out.println("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
									  error=true;
									  break;  
								  }
							  }
							  else
							  {
								  System.out.println("Error \""+lexema+ "\" no esta declarado pos:"+contcar);
								  error=true;
								  break; 
							  }
						  }
						 else
						 {
							 if(isIdent(lexema) && caracter.equals(";"))
							  {
								  estTablaS idfind2;
								  if(buscarID(lexema))
								  {
									  idfind2=traerID(lexema);
									  if(idfind2.tipoDato.equals("int"))
									  {
										  idfind.asignacion+=lexema;
										  //System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);
										  System.out.println(idfind.id+"="+idfind.asignacion);
										  contcar++;
										  columna++;
										  Termino=false;
										  break;
									  }
									  else
									  {
										  System.out.println("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
										  error=true;
										  break;  
									  }
								  }
								  else
								  {
									  System.out.println("Error \""+lexema+ "\" no esta declarado pos:"+contcar);
									  error=true;
									  break; 
								  }
							  }
							 else
							 {
								 System.out.println("Error se esperaba un terminal \";\" pos:"+contcar);
								  error=true;
								  break; 
							 }
						 }
					 }
					  
				 }
			 }
		  }
		  
		  
		  
	  }
	  while(Termino);
	  
	  
	  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
    
  public void mostrarTodo()
  {
	  estTablaS mostrar;
	  for(int i=0; i<TF.size(); i++)
	  {
		  mostrar=TF.get(i);
		  if(mostrar.asignacion.equals(""))
			  System.out.println("tipo de dato  "+mostrar.tipoDato+" "+mostrar.id+"="+"null"+" pos: "+mostrar.contcar
					  +" columna: "+mostrar.columna+" renglon: "+mostrar.renglon);
		  else
			  System.out.println("tipo de dato  "+mostrar.tipoDato+" "+mostrar.id+"="+mostrar.asignacion+" pos: "+mostrar.contcar
					  +" columna: "+mostrar.columna+" renglon: "+mostrar.renglon);
	  }
  }
  public boolean buscarID(String id)
  {
	  estTablaS mostrar;
	  for(int i=0; i<TF.size(); i++)
	  {
		  mostrar=TF.get(i);
		  if(mostrar.id.equals(id))
			  return true;
	  }
	return false;
  }
  
  public estTablaS traerID(String id)
  {
	  estTablaS mostrar = null;
	  for(int i=0; i<TF.size(); i++)
	  {
		  mostrar=TF.get(i);
		  if(mostrar.id.equals(id))
			  return mostrar;
	  }
	return mostrar;
  }
  
  
  public datos Switch(datos d)
  {
	  caracter=d.caracter;    
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon; 
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error; 
	  id=d.id;
	  contcar=d.contcar;
	  
	  tipoDato="String";
	   boolean INT=true;
	   boolean r=false;
		do
		{
	       columna--;
		   contcar--;
		   caracter="";
		   lexema="";
		    r=true;
			  do
			  {
				  if(!caracter.equals(" ")) 
					  lexema+=caracter;
			     columna++;
			     contcar++;
			     if(columna<linea.length())
			     { 
			       caracter=linea.charAt(columna)+"";
			       if(!caracter.equals(" "))
			    	   if(caracter.equals("("))
			    	   {
			    		  caracter="";
			    		  lexema="";
			    		  do //----------------Despues del (
			 			  {
			 				  if(!caracter.equals(" "))
			 					  lexema+=caracter;
			 			     columna++;
			 			     contcar++;
			 			     if(columna<linea.length())
			 			     { 
			 			       caracter=linea.charAt(columna)+"";
			 			        r=isCaracterX(caracter);
			 			     }
			 			     else 
			 			    	 r=false;
			 			  }
			 			  while(r); //----------despues del (
			    		  if(isIdent(lexema))
			    		  {
			    			  estTablaS idFind=traerID(lexema);
			    			  if(buscarID(lexema))
			    			  {
			    				  if(!idFind.asignacion.equals(""))
			    				  {
			    					  if(caracter.equals(")"))
									  {
										  lexema="";
										  caracter="";
										  r=true;
										  do
							 			  {
							 				  if(!caracter.equals(" "))
							 					  lexema+=caracter;
							 			     columna++;
							 			     contcar++;
							 			     if(columna<linea.length())
							 			     { 
							 			       caracter=linea.charAt(columna)+"";
							 			      if(!caracter.equals(" "))
							 			        r=isDelimitador(caracter);
							 			     }
							 			     else 
							 			    	 r=false;
							 			  }
							 			  while(r);
										  renglon++;
										  contcar++;
										  try
										  {
											  
											  do
											  {
												linea=contArch.readLine();
												columna=0;
												if(linea!=null)
												{ 
													//linea=linea.trim();
													do{
												    	if(columna<linea.length())
												    	{ 
												    		caracter=linea.charAt(columna)+"";
												    		if(caracter.equals(" "))
												    		{
												    			columna++;
												    			contcar++;
												    		}
												    		if(caracter.equals("\t"))
												    		{
												    			columna++;
												    			contcar++;
												    		}
												    		caracter=linea.charAt(columna)+"";
												    		lexema="";
												    		do
												 			  {
												 				  if(!caracter.equals(" "))
												 					  lexema+=caracter;
												 			     columna++;
												 			     contcar++;
												 			     if(columna<linea.length())
												 			     { 
												 			       caracter=linea.charAt(columna)+"";
												 			      if(!caracter.equals(" "))
												 		 	        r=isDelimitador(caracter);
												 			     }
												 			     else 
												 			    	 r=false;
												 			  }
												 			  while(r);
												    		  if(lexema.equals("{"))
															  {
												    			  System.out.println("switch("+idFind.id+")");
												    			  
												    			  System.out.println(tabula+"{");
												    			  //tabula+="\t";
												    			  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
																  d=SwitchCases(d); 
																  caracter=d.caracter;  
																  lexema=d.lexema;
																  columna=d.columna;
																  renglon=d.renglon;
																  tipoDato=d.tipoDato;
																  linea=d.linea;
																  error=d.error;
																  id=idFind.id; 
																  contcar=d.contcar;
																  INT=false;
																  if(error)
																	  break; 
																  if(caracter.equals("}"))
																  {
																	  columna++;
																	  contcar++;
																  }
																  else
																  {
																	  System.out.println("error se esperaba un terminal \"}\"");
																	  error=true;
																	  break;
																  }
																  System.out.println("}");
																  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
															  }
															  else
															  {
																  
																  System.out.println("error se esperaba un delimitador \"{\"");
																  error=false;
																  break;
																  
															  }
												    		  
														   
												    	}
													}
													while(columna<linea.length() && error==false);
													
												}
												renglon++;
												contcar++;
											  }
											  while(linea!=null && error==false);
										  }
									
										  catch (IOException e) 
										  {
											
										  }
										  
									  }
			    					  else
			    					  {
			    						  System.out.println("error se esperaba un delimitador \")\"");
			    				    		 error=true;
			    				    		 break; 
			    					  }
			    				  }
			    				  else
			    				  {
			    					  System.out.println("error "+lexema+" no se esta inicializado");
			    					  error=true;
			 			    		 break;
			    				  }
			    			  }
			    			  else
			    			  {
			    				  System.out.println("error "+lexema+" no se cuentra declarado");
			    				  error=true;
						    		 break;
			    			  }
			    			  
			    		  }
			    		  else 
		    			  {
		    				  System.out.println("error "+lexema+" no se cuentra declarado");
		    				  error=true;
					    		 break;
		    			  } 
			    	   }
			    	   else
			    	   {
			    		 System.out.println("error se esperaba un delimitador \"(\"");
			    		 error=true;
			    		 break;
			    	   }
			     }
			     else 
			    	 r=false;
			  }
			  while(r && error==false);
			  
		}
		while(INT && error==false);
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  
  public datos SwitchCases(datos d)
  {
	  caracter=d.caracter;    
	  lexema=d.lexema;
	  columna=d.columna;
	  renglon=d.renglon; 
	  tipoDato=d.tipoDato;
	  linea=d.linea;
	  error=d.error; 
	  id=d.id;
	  contcar=d.contcar;
	  String cases="";
	   boolean INT=true;
	   boolean r=false;
	   try 
		  {
			  
			  do
			  {
				linea=contArch.readLine();
				columna=0;
				if(linea!=null)
				{ 
					//linea=linea.trim();
					do{ 
				    	if(columna<linea.length())
				    	{ 
				    		caracter=linea.charAt(columna)+""; 
				    		if(caracter.equals(" "))
				    		{
				    			columna++;
				    			contcar++;
				    		}
				    		if(caracter.equals("\t"))
				    		{
				    			columna++;
				    			contcar++;
				    		}
				    		caracter=linea.charAt(columna)+"";
				    		lexema="";
				    		do
				 			  {
				 				  if(!caracter.equals(" "))
				 					  lexema+=caracter;
				 			     columna++;
				 			     contcar++;
				 			     if(columna<linea.length())
				 			     { 
				 			       caracter=linea.charAt(columna)+"";
				 			      if(!caracter.equals(" "))
				 		 	        r=isIdent(caracter);
				 			     }
				 			     else 
				 			    	 r=false;
				 			  }
				 			  while(r);
				    		  if(lexema.equals("case"))
							  {
				    			  cases=lexema;
				    			  lexema="";
								  caracter="";
								  contcar--;
								  columna--;
								  r=true;
								  do
					 			  {
					 				  if(!caracter.equals(" "))
					 					  lexema+=caracter;
					 			     columna++;
					 			     contcar++;
					 			     if(columna<linea.length())
					 			     { 
					 			       caracter=linea.charAt(columna)+"";
					 			      if(!caracter.equals(" "))
					 			        r=isCaracterX(caracter);
					 			     }
					 			     else 
					 			    	 r=false;
					 			  }
					 			  while(r);
								  estTablaS idFind=traerID(id);
								  if(idFind.tipoDato.equals("int"))
								  {
									  if(isEntero(lexema) && caracter.equals(caracter))
									  {
										  cases+=" "+lexema+caracter;
										  System.out.println(cases);
										  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
										  d=Proposicion(d);  
										  caracter=d.caracter;  
										  lexema=d.lexema;
										  columna=d.columna;
										  renglon=d.renglon;
										  tipoDato=d.tipoDato;
										  linea=d.linea; 
										  error=d.error;
										  id=idFind.id; 
										  contcar=d.contcar;
										  
										  if(lexema.equals("break"))
											  break;
										  
									  }
									  else
									  {
										  if(!caracter.equals(":"))
										  {
											  System.out.println("error se esperaba un terminal \":\"");
											  error=true;
											  break;
										  }
										  else
										  {
											  System.out.println("error en el tipo de dato se esperaba un int");
										  }
									  }
								  }
								  else
								  {
									 System.out.println("error en el tipo de dato se esperaba un int");
									 error=true;
									 break;
								  }
							  }
							  else
							  {
								  if(caracter.equals("}"))
								  {
									  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
								  }
								  else
								  {
									  System.out.println("error se esperaba un terminal \"case\" o \"}\"");
									  error=false;
									  break;
								  }
								  
							  }
				    		  
						   
				    	}
					}
					while(columna<linea.length() && error==false);
					
				}
				renglon++;
				contcar++;
			  }
			  while(linea!=null && error==false);
		  }
	
		  catch (IOException e) 
		  {
			
		  }
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
  }
  

  
public void Programa()
{
  datos d;
  int concar  =  0;
  try
	{
	  
		String linea="";
		String id="",tipoDato="";
		boolean error=false;
		int renglon=1,columna=0;
		do{
			linea=contArch.readLine();
			columna=0;
		    if(linea!=null)
		    { 
		    	  //linea=linea.trim();
		    	
		      do{
		    	if(columna<linea.length())
		    	{ 
		    		String caracter=linea.charAt(columna)+"";
		    		if(caracter.equals(" "))
		    		{
		    			columna++;
		    			concar++;
		    		}
		    		if(caracter.equals("\t")) 
		    		{
		    			columna++;
		    			concar+=8;
		    		}
		    		caracter=linea.charAt(columna)+"";
		    		String lexema="";
				  if(isCaracter(caracter))
				  {   
					  boolean r=false;
					  do
					  {
					     lexema+=caracter;
					     columna++;
					     concar++;
					     if(columna<linea.length())
					     { 
					       caracter=linea.charAt(columna)+"";
					       r=isCaracterX(caracter);
					     }
					     else 
					    	 r=false;
					  }
					  while(r);
					  if(busquedaBin(lexema)!=-1)
					  {
						   System.out.println("reservada");
							
					  }
					  else
					  {
						  if(isInt(lexema))
						  {
							  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
							  d=Int(d);
							  caracter=d.caracter; 
							  lexema=d.lexema;
							  columna=d.columna;
							  renglon=d.renglon;
							  tipoDato=d.tipoDato;
							  linea=d.linea;
							  error=d.error;
							  id=d.id; 
							  concar=d.contcar;
							  
						  }
						  else
						  {
							  if(ischar(lexema))
							  {
								  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
								  d=Char(d);
								  caracter=d.caracter; 
								  lexema=d.lexema;
								  columna=d.columna;
								  renglon=d.renglon;
								  tipoDato=d.tipoDato;
								  linea=d.linea;
								  error=d.error;
								  id=d.id; 
								  concar=d.contcar;
							  }
							  else
							  {
								  if(isfloat(lexema))
								  {
									  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
									  d=Float(d);
									  caracter=d.caracter; 
									  lexema=d.lexema;
									  columna=d.columna;
									  renglon=d.renglon;
									  tipoDato=d.tipoDato;
									  linea=d.linea;
									  error=d.error;
									  id=d.id; 
									  concar=d.contcar;
								  }
								  else
								  {
									  if(isString(lexema))
									  {
										  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
										  d=String(d);
										  caracter=d.caracter; 
										  lexema=d.lexema;
										  columna=d.columna;
										  renglon=d.renglon;
										  tipoDato=d.tipoDato;
										  linea=d.linea;
										  error=d.error;
										  id=d.id; 
										  concar=d.contcar;
									  }
									  else
									  {
										  if(isFor(lexema))
										  {
											  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
											  d=For(d);
											  caracter=d.caracter; 
											  lexema=d.lexema;
											  columna=d.columna;
											  renglon=d.renglon;
											  tipoDato=d.tipoDato;
											  linea=d.linea;
											  error=d.error;
											  id=d.id; 
											  concar=d.contcar;
										  }
										  else
										  {
											  if(isSwitch(lexema))
											  {
												  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
												  //d=A.Switch(d,contArch);
												  d=Switch(d);
												  caracter=d.caracter; 
												  lexema=d.lexema;
												  columna=d.columna;
												  renglon=d.renglon;
												  tipoDato=d.tipoDato; 
												  linea=d.linea;
												  error=d.error;
												  id=d.id; 
												  concar=d.contcar;										  
												 
											  }
											  else
											  {
												  if(isIdent(lexema))
												  {
													  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
													  d=IdAsignacion(d);
													  caracter=d.caracter; 
													  lexema=d.lexema;
													  columna=d.columna; 
													  renglon=d.renglon;
													  tipoDato=d.tipoDato; 
													  linea=d.linea;
													  error=d.error;
													  id=d.id; 
													  concar=d.contcar;
												  }
											  }
										  }
									  }
								  }
							  }
							  
						  }
						  
					  }
				  }//if isCaracter
				  
		    	}
		    	if(linea==null)
		    		break;
		      }
		      while(columna<linea.length() && error==false);
		    }//if linea null
			renglon++;
			concar++;
		}
		while(linea!=null && error==false);
	} 
	catch(IOException e)
	{
		System.out.println("Error de apertura del archivo");
		
	}
} 

public datos Proposicion(datos d2)
{
	datos d;
	  caracter=d2.caracter;  
	  lexema=d2.lexema;
	  columna=d2.columna;
	  renglon=d2.renglon;
	  tipoDato=d2.tipoDato;
	  linea=d2.linea;
	  error=d2.error;
	  id=d2.id;
	  contcar=d2.contcar;
	  tabula+="\t";
  
  try
	{
		boolean error=false;
		do{
			linea=contArch.readLine();
			columna=0;
		    if(linea!=null)
		    { 
		    	  //linea=linea.trim();
		    	
		      do{
		    	if(columna<linea.length())
		    	{ 
		    		caracter=linea.charAt(columna)+"";
		    		if(caracter.equals(" "))
		    		{
		    			columna++;
		    			contcar++;
		    		}
		    		if(caracter.equals("\t"))
		    		{
		    			columna++;
		    			contcar++; 
		    		}
		    		caracter=linea.charAt(columna)+""; 
		    		contcar++;
		    		lexema="";
				  if(isCaracter(caracter))
				  {   
					  boolean r=false;
					  do
					  {
					     lexema+=caracter;
					     columna++;
					     contcar++;
					     if(columna<linea.length())
					     { 
					       caracter=linea.charAt(columna)+"";
					       r=isCaracterX(caracter);
					     }
					     else 
					    	 r=false;
					  }
					  while(r);
					  if(busquedaBin(lexema)!=-1)
					  {
						   System.out.println("reservada");
							
					  }
					  else
					  {
						  if(isInt(lexema))
						  {
							  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
							  d=Int(d);
							  caracter=d.caracter; 
							  lexema=d.lexema;
							  columna=d.columna;
							  renglon=d.renglon;
							  tipoDato=d.tipoDato;
							  linea=d.linea;
							  error=d.error;
							  id=d.id; 
							  contcar=d.contcar;
							  
						  }
						  else
						  {
							  if(ischar(lexema))
							  {
								  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
								  d=Char(d);
								  caracter=d.caracter; 
								  lexema=d.lexema;
								  columna=d.columna;
								  renglon=d.renglon;
								  tipoDato=d.tipoDato;
								  linea=d.linea;
								  error=d.error;
								  id=d.id; 
								  contcar=d.contcar;
							  }
							  else
							  {
								  if(isfloat(lexema))
								  {
									  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
									  d=Float(d);
									  caracter=d.caracter; 
									  lexema=d.lexema;
									  columna=d.columna;
									  renglon=d.renglon;
									  tipoDato=d.tipoDato;
									  linea=d.linea;
									  error=d.error;
									  id=d.id; 
									  contcar=d.contcar;
								  }
								  else
								  {
									  if(isString(lexema))
									  {
										  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
										  d=String(d);
										  caracter=d.caracter; 
										  lexema=d.lexema;
										  columna=d.columna;
										  renglon=d.renglon;
										  tipoDato=d.tipoDato;
										  linea=d.linea;
										  error=d.error;
										  id=d.id; 
										  contcar=d.contcar;
									  }
									  else
									  {
										  if(isFor(lexema))
										  {
											  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
											  d=For(d);
											  caracter=d.caracter; 
											  lexema=d.lexema;
											  columna=d.columna;
											  renglon=d.renglon;
											  tipoDato=d.tipoDato;
											  linea=d.linea;
											  error=d.error;
											  id=d.id; 
											  contcar=d.contcar;
										  }
										  else
										  {
											  if(lexema.equals("break"))
											  {
												  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
											  }
											  if(isIdent(lexema))
											  {
												  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
												  d=IdAsignacion(d);
												  caracter=d.caracter; 
												  lexema=d.lexema;
												  columna=d.columna;
												  renglon=d.renglon;
												  tipoDato=d.tipoDato; 
												  linea=d.linea;
												  error=d.error;
												  id=d.id; 
												  contcar=d.contcar;
											  }
										  }
									  }
								  }
							  }
							  
						  }
						  
					  }
				  }//if isCaracter
				  else
				  {
					 if(caracter.equals("}"))
						 return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
				  }
		    	}
		    	if(linea==null)
		    		break;
		      }
		      while(columna<linea.length() && error==false);
		    }//if linea null
			renglon++;
		}
		while(linea!=null && error==false);
	} 
	catch(IOException e)
	{
		System.out.println("Error de apertura del archivo");
	}
  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
}
  

  public static void main(String[] args) {
	  
			Final A=new Final();
			/*
			String entrada="";
			System.out.println("Dame la cadena a evaluar");
			Scanner sc=new Scanner(System.in);
			entrada=sc.nextLine();
			
			if(A.isOperador(entrada))
			{
				System.out.println("La cadena es valida para la expresion");

			}
			else
			{
				System.out.println("La cadena no es valida para la expresion");
			}
			
		    */
			//A.Programa();
			String a="7+5*7+7/4";
			Vector<String> caracteres=new Vector<String>();
			String res="";
			String op;
			boolean termino=false;
			int con=0,exite=0;
			
			for(int i=0;i<a.length();i++)
			{
				caracteres.add(a.charAt(i)+"");
				
			}
			
			do
			{ 
				for(int i=0;i<caracteres.size();i++)
				{
					op=caracteres.get(i);
					exite++;
					if(op.equals("/") || op.equals("*")) 
					{
						res+=caracteres.get(i-1);
						res+=caracteres.get(i);
						res+=caracteres.get(i+1);
						con++;
						caracteres.remove(i-1);
						caracteres.remove(i-1);
						caracteres.remove(i-1); 
						caracteres.add(i-1,"res"+con);
						System.out.println("res"+con+"="+res);
						res="";
						
						break;
					}
				}
			}
			while(exite<15);
			exite=0;
			do
			{ 
				for(int i=0;i<caracteres.size();i++)
				{
					op=caracteres.get(i);
					if(op.equals("+") || op.equals("-")) 
					{
						res+=caracteres.get(i-1);
						res+=caracteres.get(i);
						res+=caracteres.get(i+1);
						con++;
						caracteres.remove(i-1);
						caracteres.remove(i-1);
						caracteres.remove(i-1); 
						caracteres.add(i-1,"res"+con);
						System.out.println("res"+con+"="+res);
						res="";
						exite++;
						break;
					}
					
				}
			}
			while(exite<caracteres.size());
			
			//System.out.println("Termino");
			
		//A.mostrarTodo();	
		} 
  
  	
	
}



