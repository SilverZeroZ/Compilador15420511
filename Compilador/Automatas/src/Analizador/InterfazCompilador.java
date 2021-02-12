package Analizador;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;


public class InterfazCompilador extends JFrame{

	JLabel archivoo1, archivoo2;
	JButton botonAbrir,botonCompilar,bEjecutar;
	JTextArea area1, area2, area3;
	JTextArea texto;
	   int index;
	   int vari=0;
	    Highlighter hilit;
	    Highlighter.HighlightPainter painter;
	    FileDialog buscar;
	    final JFileChooser fc = new JFileChooser();
	    String ruta="";
		String palabrasR[]={"pick"};
		  
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
		  Font fuente=new Font("Dialog", Font.PLAIN, 17);
		  Font fuente2=new Font("Dialog", Font.BOLD, 20);
		  	Vector<String> caracteres=new Vector<String>();
			String res="";
			String op;
			boolean termino=false;
			int con=0,exite=0;
			
			String nva="";
			String nva2="";
			String nva3="";
			String nva4="";
			String nva5="";
			String nva6="";
			int nv=0;
			String varin,var,vcomp,comp,vop,op1, inc;
			
		  
	public InterfazCompilador()
	{
		setSize(800,700);
		setTitle("Compilador");
		this.getContentPane().setBackground(new Color(34, 113, 179));
		
		
		
		archivoo1=new JLabel("Entrada");
		archivoo2=new JLabel("Salida");
		
		botonAbrir=new JButton("Abrir");
		botonCompilar=new JButton("Compilar");
		bEjecutar=new JButton("Ejecutar");
		
		
		area1=new JTextArea();
		area2=new JTextArea();
		area3=new JTextArea();
		area1.setFont(fuente);
		area2.setFont(fuente);
		area3.setFont(fuente2);
		
		JScrollPane td=new JScrollPane(area1);
		JScrollPane td2=new JScrollPane(area2);
		JScrollPane td3=new JScrollPane(area3);
		
		setLayout(null);
		archivoo1.setBounds(120, 400, 250, 50);
		archivoo2.setBounds(460,400,250,50);
		
		botonAbrir.setBounds(200,410,80,30);
		botonCompilar.setBounds(500,410,150,30);
		//bEjecutar.setBounds(560,220,90,30);
		
		td.setBounds(20,30,350,350);
		td2.setBounds(410,30,350,350);
		td3.setBounds(40,460,700,200);
		
		add(archivoo1);
		add(archivoo2);
		
		add(td);
		add(td2);
		add(td3);
		
		add(botonAbrir);
		add(botonCompilar);
		add(bEjecutar);
	
		hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(34, 113, 179));
        area1.setHighlighter(hilit);
        botonCompilar.setEnabled(false);
        
        buscar=new FileDialog(new Frame(),"Selecciona un Archivo",FileDialog.LOAD);
        bEjecutar.setEnabled(false);
        
        botonAbrir.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent arg0)
			{
				
				buscar.setVisible(true);
				ruta=buscar.getDirectory()+buscar.getFile();
				if(!ruta.equals(buscar.getDirectory()+"null"))
				{
					area3.setText("");
					borrarDatos();
					VisualizarArchivo(ruta);
					botonCompilar.setEnabled(true);
						
				}
				
			}
		});
        botonCompilar.addActionListener(new ActionListener() 
        {
			
			public void actionPerformed(ActionEvent e) 
			{
					if(!area1.getText().equals(""))
					{
						borrarDatosCompilador();
						
						String contenido="",contenido2;					
						contenido2=area1.getText();
						grabar(contenido2,ruta,"tempR.txt");
						try
						  {
						  archl=new FileReader(ruta);
						  contArch=new BufferedReader(archl);
						  Programa(); 
						  }
						catch(IOException o)
						{
							System.out.println("Error al Abrir el Archivo");
						}
					}
					else
					{
						area2.setText("");
						area3.setText("");
					}
			}
		});
        bEjecutar.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!area2.getText().equals(""))
				{
					
					String contenido="",contenido2,contenido3="";					
					contenido2=area2.getText();
					String rutaR=buscar.getDirectory()+"tempR.c";
					grabar(contenido2,rutaR,"");
					contenido3="cd "+buscar.getDirectory()+"\ngcc tempR.c -o tempRapeMe\ntempRapeMe\npause";
					String rutaR1=buscar.getDirectory()+"tempREject.CMD";
					grabar(contenido3,rutaR1,"");
					String rutaREjec=buscar.getDirectory()+"tempREject.CMD";
					try
					{
						Desktop.getDesktop().open(new File(rutaREjec));
					}
					catch(Exception ee)
					{
						
					}
					
				}
				
			}
		}); 
		setResizable(false);
		setVisible(true);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void VisualizarArchivo(String ruta)
	{
		try
		{
			FileReader archl=new FileReader(ruta);
			BufferedReader contArch=new BufferedReader(archl);
			String linea="";
			
			do
			{
				linea=contArch.readLine();

				if(linea!=null)
				{
					area1.append(linea+"\n"); 

				}
				
				
			}
			while(linea!=null);
		}
		catch(IOException e)
		{
			System.out.println("Error de apertura del archivo");
		}
	}
	 public void buscarError(int index) {
	     
	    try
	    {
	        hilit.removeAllHighlights();
	        boolean fin=false;
	        int end=0;
	        	if(index>1)
	        	{
	        		index=index-2;
	        		end=index+2;
	        	}
	        	else {
	        		if(index==area1.getText().length())
	        		{
	        			System.out.println("final");
	        			fin=true;
	        			index=index-2;
	        			end=area1.getText().length()-1;
	        		}
	        		else
	        		{
	        			if(index==0)
	        			{ 
	        				end=index+1;
	        			}
	        		}
	        	}
	       
	                try {
	                	
	                		hilit.addHighlight(index, end, painter);
		                    area1.setCaretPosition(end-1);
	                
	                    
	                    
	                } 
	                catch (BadLocationException e) 
	                {
	                    
	                }
	    }
	    catch(IllegalArgumentException e)
	    {
	    	System.out.println("No existe la posicion");
	    }
	    
	}
	 public void borrarDatos()
	{
		area1.setText("");
			
	}
	 public void borrarDatosCompilador()
	{
		 hilit.removeAllHighlights();
	      TF.removeAllElements();
		  caracter="";
		  lexema="";
		  columna=0;
		  renglon=0;
		  tipoDato="";
		  linea="";
		  error=false;
		  id="";
		  contcar=0;
		  tabula="";
		  area3.setText("");
		  area2.setText("");
		  
		  
	}
	
	public void grabar(String contenido, String ruta, String nombre)
	{
		FileOutputStream arche=null;
		try
		{
			arche=new FileOutputStream(ruta);
			arche.write(contenido.getBytes());
			arche.close();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "Error al escribir el archivo");
		}
		
		
	}
	
	public void MensajeConsola(String mensaje)
	{
		area3.setText(mensaje+"\n");
	}
	public static void main(String[] args)
	{
		new InterfazCompilador();
	}
//--------------------------------------------------------------------------------------------------------------------------------

class datos{
	String lexema,carac,line,id,tipoD;
	int ren,col,contadorC;
	boolean error;
	FileReader archivol;
	BufferedReader contArch;
	public datos(String l,String caracter2,int r,int c, boolean error2, String id2,String linea2,String tipoDato2, int contcar2)
	{
		lexema=l;carac=caracter2;
		ren=r;col=c;
		error=error2;
		id=id2;
		line=linea2;
		tipoD=tipoDato2;
		contadorC=contcar2;
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


@SuppressWarnings("serial")

	
	//---------------------------------------------------------------------------------------------------------------------------
	
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
	  public static boolean isrFor(String cadena)
	  {
		  return Pattern.matches("(for)",cadena);
		  
	  }
	  public boolean isSwitch(String cad)
	  {
		  return Pattern.matches("(switch)", cad);
	  }

	  public boolean isEntero(String cad)
	  {
		  String exp="[0-9]+|[\\-][0-9]+";
		  return Pattern.matches(exp, cad);
	  }
	  public boolean isIF(String cad)
	  {
		  return Pattern.matches("(if)", cad);
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
	  
	  
	public void Consola(String mensaje,int pos,boolean err)
	{
		if(err)
		{
			area3.append(mensaje+"\n");
			contcar++;
			buscarError(contcar);
		}
		else
		{
			area3.append(mensaje+"\n");
		}
	}
	public void GenerarCodigo(String mensaje)
	{
			area2.append(mensaje);
	}
	  
	  public datos Int(datos d)
	  {
		  caracter=d.carac;
		  lexema=d.lexema; 
		  columna=d.col;
		  renglon=d.ren;
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  
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
					  System.out.println("error nombre de variable duplicada "+id+" pos: "+contcar);
					  Consola("error nombre de variable duplicada "+id+" pos: "+contcar, contcar,true);
					  error=true;
					  break;
					  
				  }
				  if(isIdent(lexema) && caracter.equals(","))
				  {
					  System.out.println("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar,false);
					  GenerarCodigo(tabula+"int "+lexema+";\n");
					  TF.add(datosInt);
				  }
				  if(isIdent(lexema) && caracter.equals(";"))
				  {
					  System.out.println("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar,false);
					  GenerarCodigo(tabula+"int "+lexema+";\n");
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
						  System.out.println("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
						  estTablaS datosInt=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
						  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar,false);
						  GenerarCodigo(tabula+"int "+id+"="+lexema+";\n");
						  TF.add(datosInt);
					  }
					  else
					  {
						  if(isEntero(lexema) && caracter.equals(";"))
						  {
							  System.out.println("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
							  estTablaS datosInt=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
							  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar,false);
							  GenerarCodigo(tabula+"int "+id+"="+lexema+";\n");
							  TF.add(datosInt);
						  }
						  else
						  {

							  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
							  Consola("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar, contcar,true);
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
					  //GenerarCodigo("\n");
				  }
				  if(!isIdent(id))
				  {
					  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
				  if(isIdent(lexema) && caracter.equals(":"))
				  {
					  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
			}
			while(INT);
			return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	  }
	  
	  public datos Float(datos d)
	  {
		  caracter=d.carac;
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren;
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  
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
					  Consola("error nombre de variable duplicada "+id, contcar, true);
					  error=true;
					  break;
					  
				  }
				  if(isIdent(lexema) && caracter.equals(","))
				  {
					  System.out.println("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  GenerarCodigo(tabula+"float "+lexema+";\n");
					  TF.add(datosInt);
				  }
				  if(isIdent(lexema) && caracter.equals(";"))
				  {
					  System.out.println("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosInt=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  GenerarCodigo(tabula+"float "+lexema+";\n");
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
						  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
						  GenerarCodigo(tabula+"float "+id+"="+lexema+";\n");
						  TF.add(datosInt);
					  }
					  else
					  {
						  if(isReal(lexema) && caracter.equals(";"))
						  {
							  System.out.println(tabula+"tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
							  estTablaS datosInt=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
							  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
							  GenerarCodigo(tabula+"float "+id+"="+lexema+";\n");
							  TF.add(datosInt);
						  }
						  else
						  {
							  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
							  Consola("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar, contcar, true);
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
					  GenerarCodigo("\n");
				  }
				  if(!isIdent(id))
				  {
					  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
				  if(isIdent(lexema) && caracter.equals(":"))
				  {
					  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
			}
			while(INT);
			return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	  }
	  
	  public datos Char(datos d)
	  {
		  caracter=d.carac;
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren;
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  tipoDato="char";
		  GenerarCodigo(tabula+"char ");
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
					  System.out.println("error nombre de variable duplicada "+id+" pos: "+contcar);
					  Consola("error nombre de variable duplicada "+id+" pos: "+contcar, contcar, true);
					  error=true;
					  break;
					  
				  }
					  
				  if(isIdent(lexema) && caracter.equals(","))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  GenerarCodigo(tabula+"char "+lexema+";\n");
					  TF.add(datosChar);
					  
				  }
				  if(isIdent(lexema) && caracter.equals(";"))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  GenerarCodigo(tabula+"char "+lexema+";\n");
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
						  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
						  GenerarCodigo(id+"="+lexema+",");
						  TF.add(datosChar);
					  }
					  else
					  {
						  if(isCharAsignacion(lexema) && caracter.equals(";"))
						  {
							  System.out.println(tabula+"tipo de dato  "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
							  estTablaS datosChar=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
							  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
							  GenerarCodigo(id+"="+lexema+";");
							  TF.add(datosChar);
						  }
						  else
						  {
							  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
							  Consola("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar, contcar, true);
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
					  GenerarCodigo("\n");
				  }
				  if(!isIdent(id))
				  {
					  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
				  if(isIdent(lexema) && caracter.equals(":"))
				  {
					  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
			}
			while(INT);
			return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	  }
	  public datos If(datos d)
	  {
		  caracter=d.carac;
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren;
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  tipoDato="char";
		  GenerarCodigo(tabula+"char ");
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
					  System.out.println("error nombre de variable duplicada "+id+" pos: "+contcar);
					  Consola("error nombre de variable duplicada "+id+" pos: "+contcar, contcar, true);
					  error=true;
					  break;
					  
				  }
					  
				  if(isIdent(lexema) && caracter.equals(","))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  GenerarCodigo(tabula+"char "+lexema+";\n");
					  TF.add(datosChar);
					  
				  }
				  if(isIdent(lexema) && caracter.equals(";"))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  GenerarCodigo(tabula+"char "+lexema+";\n");
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
						  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
						  GenerarCodigo(id+"="+lexema+",");
						  TF.add(datosChar);
					  }
					  else
					  {
						  if(isCharAsignacion(lexema) && caracter.equals(";"))
						  {
							  System.out.println(tabula+"tipo de dato  "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
							  estTablaS datosChar=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
							  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
							  GenerarCodigo(id+"="+lexema+";");
							  TF.add(datosChar);
						  }
						  else
						  {
							  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
							  Consola("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar, contcar, true);
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
					  GenerarCodigo("\n");
				  }
				  if(!isIdent(id))
				  {
					  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
				  if(isIdent(lexema) && caracter.equals(":"))
				  {
					  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
			}
			while(INT);
			return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	  }
	  public datos String(datos d)
	  {
		  caracter=d.carac;  
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren; 
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  
		  tipoDato="String";
		  GenerarCodigo(tabula+"char ");
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
					  Consola("error nombre de variable duplicada "+id, contcar, true);
					  error=true;
					  break;
					  
				  }
					  
				  if(isIdent(lexema) && caracter.equals(","))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  GenerarCodigo(lexema+"[1024]=\"\",");
					  TF.add(datosChar);
					  
				  }
				  if(isIdent(lexema) && caracter.equals(";"))
				  {
					  System.out.println(tabula+"tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar);
					  estTablaS datosChar=new estTablaS(tipoDato, id,"", renglon, columna, contcar);
					  Consola("tipo de dato "+tipoDato+" "+lexema+" vacio"+" pos: "+contcar, contcar, false);
					  TF.add(datosChar);
					  GenerarCodigo(lexema+"[1024]=\"\";");
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
								    	 Consola("error se esperaba un terminal \" pos: "+contcar, contcar, true);
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
						  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
						  GenerarCodigo(id+"[]="+lexema+",");
						  TF.add(datosChar);
					  }
					  else
					  {
						  if(isCadenaAsignacion(lexema) && caracter.equals(";"))
						  {
							  System.out.println(tabula+"tipo de dato  "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
							  estTablaS datosChar=new estTablaS(tipoDato, id,lexema, renglon, columna, contcar);
							  Consola("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar, contcar, false);
							  GenerarCodigo(id+"[]="+lexema+";");
							  TF.add(datosChar);
						  }
						  else
						  {
							  System.out.println("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar);
							  Consola("Error en el Tipo de Dato "+tipoDato+" "+" pos: "+contcar, contcar, true);
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
					  GenerarCodigo("\n");
				  }
				  if(!isIdent(id))
				  {
					  System.out.println("Error se esperaba un identificador" +" pos: "+contcar);
					  Consola("Error se esperaba un identificador" +" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
				  if(isIdent(lexema) && caracter.equals(":"))
				  {
					  System.out.println("Error se esperaba un identificador"+" pos: "+contcar);
					  Consola("Error se esperaba un identificador"+" pos: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
			}
			while(INT);
			return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	  } 
	  
	  public datos ifs(datos d)
	  {
		  caracter=d.carac;    
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren; 
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error; 
		  id=d.id;
		  contcar=d.contadorC;
		  
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
								  d=ifsAsignacionID(d);
								  caracter=d.carac;  
								  lexema=d.lexema;
								  columna=d.col;
								  renglon=d.ren;
								  tipoDato=d.tipoD;
								  linea=d.line;
								  error=d.error;
								  id=d.id; 
								  contcar=d.contadorC;
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
											    		contcar++;
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
											    			  //GenerarCodigo("\n");
											    			  //GenerarCodigo(tabula+"{");
											    			  //GenerarCodigo("\n");
											    			  System.out.println(tabula+"{"); 	 										    			  
											    			  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
															  d=ifes(d); 
															  caracter=d.carac; 
															  lexema=d.lexema;
															  columna=d.col;
															  renglon=d.ren;
															  tipoDato=d.tipoD;
															  linea=d.line;
															  error=d.error;
															  id=d.id; 
															  contcar=d.contadorC;
															  INT=false;
															  if(error)
																  break;
															  if(caracter.equals("}"))
															  {
																  columna++;
																  contcar++;
																  //GenerarCodigo("}\n");
																  GenerarCodigo("finif:\n");
																  tabula="";
															  }
															  else
															  {
																  System.out.println("error se esperaba un terminal \"}\""+" pos: "+contcar);
																  Consola("error se esperaba un terminal \"}\""+" pos: "+contcar, contcar, true);
																  error=true;
																  break;
															  }
															  
															  System.out.println("}");
															  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
															  
														  }
														  else
														  {
															  System.out.println("error se esperaba un delimitador \"{\"");
															  Consola("error se esperaba un delimitador \"{\""+" pos: "+contcar, contcar, true);
					
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
				    		 Consola("error se esperaba un delimitador \"(\""+" pos: "+contcar, contcar, true);
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
	  
	  public datos ifsAsignacionID(datos d)
	  {
		  String condicion="",ini="",con1="",con2="",incre="";
		  caracter=d.carac;  
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren;
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  
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
					 					 Consola("Inicializacion "+id+"="+lexema, contcar, false);
				 						  ini=id+"="+lexema;
				 						  varin=id;
				 						  var=lexema;
				 						  contcar++;
				 						  
				 					  }
					 				 else
					 				 {
						 				 if(isEntero(lexema) && caracter.equals(","))
					 					  {
						 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
						 					 Consola("Error se esperaba un terminal ; pos:"+contcar, contcar, true);
					 						  error=true;
					 						  break; 
					 					  }
					 					  else
					 					  {
					 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
					 						 Consola("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar, contcar, true);
					 						  error=true;
					 						  break; 
					 						  
					 					  }
					 				 }
					 					  
					    		   }			    		   
					    	   }
					    	   else
					    	   {
					    		   System.out.println("error se esperaba una asignacion \"=\" pos: "+contcar);
					    		   Consola("error se esperaba una asignacion \"=\" pos: "+contcar, contcar, true);
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
				  Consola("error \""+id+ "\" no se encuentra declarado pos: "+contcar, contcar, true);
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
							  	Consola("Condicion "+con1+condicion+con2, contcar, false);
							  	vcomp=con1;
							  	op1=condicion;
							  	comp=con2;
							  	
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
												  vop=idfind3.id;
												  inc=lexema.charAt(0)+"";
												  System.out.println(tabula+"for("+ini+";"+con1+condicion+con2+";"+incre+")");
												  Consola("Incremento "+incre, contcar, false);
												  Consola("declaracion for("+ini+";"+con1+condicion+con2+";"+incre+")", contcar, false);
												  //GenerarCodigo(tabula+"for("+ini+";"+con1+condicion+con2+";"+idfind3.id+"="+idfind3.id+lexema.charAt(0)+"1)");
											  }
											  else
											  {
												  if(isIncremento(lexema) && !caracter.equals(")"))
												  {
													  System.out.println("error se esperaba un delimitador \")\"");
													  Consola("error se esperaba un delimitador \")\" pos: "+contcar, contcar, true);
												  }
												  else
												  {
													  System.out.println("Error en la declaracion de incremento");
													  Consola("Error en la declaracion de incremento pos: "+contcar, contcar, true);
												  }
											  }
										  }
										  else
		 								  {
		 									  System.out.println("error \""+idfind3.id+ "\" no se ha inicializado: "+contcar);
		 									 Consola("error \""+idfind3.id+ "\" no se ha inicializado: "+contcar, contcar, true);
		 									  error=true;
		 									  break;
		 								  }
									  }
									  else
		 							  {
		 								  System.out.println("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar);
		 								 Consola("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar, contcar, true);
		 								  error=true;
		 								  break;
		 							  }
									  
								  
							  }
		 				  else
		 				  {
			 				 if(isEntero(lexema) && caracter.equals(","))
		 					  {
			 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
			 					 Consola("Error se esperaba un terminal ; pos:"+contcar, contcar, true);
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
		 													  vop=idfind3.id;
		 													  inc=lexema.charAt(0)+"";
		 													  System.out.println(tabula+"for("+ini+";"+con1+condicion+con2+";"+incre+")");
		 													  Consola("declaracion for("+ini+";"+con1+condicion+con2+";"+incre+")", contcar, false);
		 													 //GenerarCodigo(tabula+"for("+ini+";"+con1+condicion+con2+";"+idfind3.id+"="+idfind3.id+lexema.charAt(0)+"1)");
		 												  }
		 												  else
		 												  {
		 													  if(isIncremento(lexema) && !caracter.equals(")"))
		 													  {
		 														  System.out.println("error se esperaba un delimitador \")\"");
		 														 Consola("error se esperaba un delimitador \")\" pos: "+contcar, contcar, true);
		 													  }
		 													  else
		 													  {
		 														  System.out.println("Error en la declaracion de incremento");
		 														 Consola("Error en la declaracion de incremento"+" pos: "+contcar, contcar, true);
		 													  }
		 												  }
		 											  }
		 											  else
		 			 								  {
		 			 									  System.out.println("error \""+idfind3.id+ "\" no se ha inicializado: "+contcar);
		 			 									Consola("error \""+idfind3.id+ "\" no se ha inicializado: "+contcar, contcar, true);
		 			 									  error=true;
		 			 									  break;
		 			 								  }
		 										  }
		 										  else
		 			 							  {
		 			 								  System.out.println("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar);
		 			 								Consola("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar, contcar, true);
		 			 								  buscarError(contcar);
		 			 								  error=true;
		 			 								  break;
		 			 							  } 
		 								  }
		 								  else
		 								  {
		 									  System.out.println("error \""+lexema+ "\" no se ha inicializado: "+contcar);
		 									 Consola("error \""+lexema+ "\" no se ha inicializado: "+contcar, contcar, true);
		 									  error=true;
		 									  break;
		 								  }
		 							  }
		 							  else
		 							  {
		 								  System.out.println("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar);
		 								 Consola("error \""+lexema+ "\" no se encuentra declarado pos: "+contcar, contcar, true);
		 								  
		 								  error=true;
		 								  break;
		 							  }
		 						 }
		 						else
		 		 				{
		 			 				if(isIdent(lexema) && caracter.equals(","))
		 		 					 {
		 			 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
		 			 					  Consola("Error se esperaba un terminal ; pos:"+contcar, contcar, true);
		 		 						  error=true;
		 		 						  break;
		 		 						   
		 		 					 }
		 			 				else
		 			 				{
		 			 					  System.out.println("error en la condicion pos: "+contcar);
		 			 					  Consola("error en la condicion pos: "+contcar, contcar, true);
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
						  Consola("error "+lexema+ " no es una condicion valida: "+contcar, contcar, true);
						  error=true;
						  break;
					  }
					  
				  }
				  else
				  {
					  System.out.println("error "+idfind2.id+ " no se ha inicializado: "+contcar);
					  Consola("error "+idfind2.id+ " no se ha inicializado: "+contcar, contcar, true);
					  error=true;
					  break;
				  }
			  }
			  else
			  {
				  System.out.println("error "+lexema+ " no se encuentra declarado pos: "+contcar);
				  Consola("error "+lexema+ " no se encuentra declarado pos: "+contcar, contcar, true);
				  
				  error=true;
				  break;
			  }
		} 
		while(r);
			  
			return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	  }
	  
	  public datos IdAsignacion(datos d)
	  {
		  caracter=d.carac;  
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren;
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  
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
			 						  Consola("Asignacion "+id+"="+lexema+" pos: "+contcar, contcar, false);
			 						  GenerarCodigo(tabula+id+"="+lexema+";\n");
			 						  columna++;
			 						  contcar++;
			 					  }
				 				 else
				 				 {
				 					if(isEntero(lexema) && isOperador(caracter))
				 					  {
				 						  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
										  d=IdOperacion(d);
										  caracter=d.carac; 
										  lexema=d.lexema;
										  columna=d.col;
										  renglon=d.ren;
										  tipoDato=d.tipoD;
										  linea=d.line;
										  error=d.error;
										  id=d.id; 
										  contcar=d.contadorC;
				 					  }
				 					 else
				 					 {
				 						if(isIdent(lexema) && isOperador(caracter))
					 					  {
					 						  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
											  d=IdOperacion(d); 
											  caracter=d.carac; 
											  lexema=d.lexema;
											  columna=d.col;
											  renglon=d.ren;
											  tipoDato=d.tipoD;
											  linea=d.line;
											  error=d.error;
											  id=d.id; 
											  contcar=d.contadorC; 
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
				 									Consola("Asignacion "+id+"="+lexema+" pos: "+contcar, contcar, false);
				 									GenerarCodigo(tabula+id+"="+lexema+";\n");
				 									columna++;
							 						contcar++;
				 								  }
				 								  else
				 								  {
				 									  System.out.println("Error en el tipo de dato se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
				 									 Consola("Error en el tipo de dato se esperaba un "+idfind.tipoDato+ " pos:"+contcar, contcar, true);
				 									  error=true;
				 									  break;  
				 								  }
							 					  
						 					  }
				 							else
				 							{
								 				 if(isEntero(lexema) && caracter.equals(","))
							 					  {
								 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
								 					 Consola("Error se esperaba un terminal ; pos:"+contcar, contcar, true);
							 						  error=true;
							 						  break; 
							 					  }
							 					  else
							 					  {
							 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
							 						 Consola("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar, contcar, true);
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
			 						 Consola("Asignacion "+id+"="+lexema+" pos: "+contcar, contcar, false); 
			 						GenerarCodigo(tabula+id+"="+lexema+";\n");
			 						  columna++;
			 						  contcar++;
			 					  }
				 				 else
				 				 {
					 				 if(isReal(lexema) && caracter.equals(","))
				 					  {
					 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
					 					 Consola("Error se esperaba un terminal ; pos:"+contcar, contcar, true); 
				 						  error=true;
				 						  break; 
				 					  }
				 					  else
				 					  {
				 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
				 						 Consola("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar, contcar, true);
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
			 						 Consola("Asignacion "+id+"="+lexema+" pos: "+contcar, contcar, false);
			 						GenerarCodigo(tabula+id+"="+lexema+";\n");
			 						  columna++;
			 						  contcar++;
			 					  }
				 				 else
				 				 {
					 				 if(isCharAsignacion(lexema) && caracter.equals(","))
				 					  {
					 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
					 					 Consola("Error se esperaba un terminal ; pos:"+contcar, contcar, true);
				 						  error=true;
				 						  break; 
				 					  }
				 					  else
				 					  {
				 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
				 						  Consola("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar, contcar, true);
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
				 							    	Consola("error se esperaba un terminal \" pos: "+contcar, contcar, true);
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
			 						 Consola("Asignacion "+id+"="+lexema+" pos: "+contcar, contcar, false);
			 						 GenerarCodigo("strcpy("+id+","+lexema+");\n");
			 						  columna++;
			 						  contcar++;
			 					  }
				 				 else
				 				 {
					 				 if(isCadenaAsignacion(lexema) && caracter.equals(","))
				 					  {
					 					  System.out.println("Error se esperaba un terminal ; pos:"+contcar);
					 					 Consola("Error se esperaba un terminal ; pos:"+contcar, contcar, true);
				 						  error=true;
				 						  break; 
				 					  }
				 					  else
				 					  {
				 						  System.out.println("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar);
				 						 Consola("Error en el tipo de dato, se esperaba un "+idfind.tipoDato+" pos: "+contcar, contcar, true);
				 						  error=true;
				 						  break; 
				 						  
				 					  }
				 				 }
				 					  
				    		   }
				    	   }
				    	   else
				    	   {
				    		   System.out.println("error se esperaba una asignacion =");
				    		   Consola("error se esperaba una asignacion \"=\" "+" pos: "+contcar, contcar, true);
				    		   error=true;
							   break;
				    	   }
				     }
				     else 
				    	 r=false;
				     //--------------------corchete
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
								    	 Consola("error se esperaba un terminal \" pos: "+contcar, contcar, true);
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
						  Consola(lexema+" pos: "+contcar, contcar, false);
						  System.out.println("mensaje("+lexema+");");
						  GenerarCodigo("printf(\"%s\\"+"n\""+","+lexema+");\n");
						  columna++;
						  contcar++;
					  }
					  else
					  {
						  System.out.println("se esperaba un terminal \")\" pos: "+contcar);
					  }
	   		   }
			  }
			  else
			  {
				  
				  if(id.equals("mensajeVar"))
				  {
					  if(caracter.equals("("))
		   		   {
						  boolean r=true;
						  lexema="";
						  caracter="";
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
						  if(isIdent(lexema))
						  {
							  if(buscarID(lexema))
							  {
								  estTablaS idBusca=traerID(lexema);
								  if(idBusca.tipoDato.equals("int"))
								  {
									  if(caracter.equals(")"))
									  {
										  Consola(lexema+" pos: "+contcar, contcar, false);
										  System.out.println("mensaje("+lexema+");");
										  GenerarCodigo("printf(\"%i\\"+"n\""+","+lexema+");\n");
										  columna++;
										  contcar++;
									  }
									  else
									  {
										  System.out.println("error se esperaba un terminal \")\" pos: "+contcar);
										  Consola("error se esperaba un terminal \")\" pos: "+contcar, contcar, true);
										  error=true;
									  }
								  }
								  else
								  {
									  if(idBusca.tipoDato.equals("String"))
									  {
										  if(caracter.equals(")"))
										  {
											  Consola(lexema+" pos: "+contcar, contcar, false);
											  System.out.println("mensaje("+lexema+");");
											  GenerarCodigo("printf(\"%s\\"+"n\""+","+lexema+");\n");
											  columna++;
											  contcar++;
										  }
										  else
										  {
											  System.out.println("error se esperaba un terminal \")\" pos: "+contcar);
											  Consola("error se esperaba un terminal \")\" pos: "+contcar, contcar, true);
											  error=true;
										  }  
									  }
									  else
									  {
										  if(idBusca.tipoDato.equals("float"))
										  {
											  if(caracter.equals(")"))
											  {
												  Consola(lexema+" pos: "+contcar, contcar, false);
												  System.out.println("mensaje("+lexema+");");
												  GenerarCodigo("printf(\"%f\\"+"n\""+","+lexema+");\n");
												  columna++;
												  contcar++;
											  }
											  else
											  {
												  System.out.println("error se esperaba un terminal \")\" pos: "+contcar);
												  Consola("error se esperaba un terminal \")\" pos: "+contcar, contcar, true);
												  error=true;
											  }
										  }
									  }
								  }
							  }
							  else
							  {
								  System.out.println("error "+id+ " no se encuentra declarado pos: "+contcar);
								  Consola("error "+id+ " no se encuentra declarado pos: "+contcar, contcar, true);
								  error=true;    
							  }
						  }
						  else
						  {
							  System.out.println("error se esperaba un identificador \"mensaje\"  pos: "+contcar);
							  Consola("error se esperaba un identificador \"mensaje\"  pos: "+contcar, contcar, true);
							  error=true;
						  }
		   		   }
				  }
				  else
				  {
					  System.out.println("error "+id+ " no se encuentra declarado pos: "+contcar);
					  Consola("error "+id+ " no se encuentra declarado pos: "+contcar, contcar, true);
					  error=true;  
				  }
				    
			  }
			  
		  }
		 
			return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	  }
	  
	
	  public boolean existeoper()
	  {
		  for(int i=0;i<caracteres.size();i++)
			{
				op=caracteres.get(i);
				if(op.equals("/") || op.equals("*")) 
				{
					return true;
				}
			}
		  return false;
	  }
	  public boolean existeoper2()
	  {
		  for(int i=0;i<caracteres.size();i++)
			{
				op=caracteres.get(i);
				if(op.equals("+") || op.equals("-")) 
				{
					return true;
				}
			}
		  return false;
	  }
	  public void NotacionOperadores(estTablaS idfind)
	  {
		  	System.out.println(caracteres);
		  	
		  	res="";
		  	

			do
			{ 
				for(int i=0;i<caracteres.size();i++)
				{
					op=caracteres.get(i);
					if(op.equals("/") || op.equals("*")) 
					{
						res+=caracteres.get(i-1);
						res+=caracteres.get(i);
						res+=caracteres.get(i+1);
						con++;
						caracteres.remove(i-1);
						caracteres.remove(i-1);
						caracteres.remove(i-1); 
						caracteres.add(i-1,"temp"+con);
						System.out.println("int temp"+con+"="+res);
						GenerarCodigo("int temp"+con+"="+res+";\n");
						res="";
						
						break;
					}
				}
			}
			while(existeoper());
			
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
						caracteres.add(i-1,"temp"+con);
						System.out.println("temp"+con+"="+res);
						GenerarCodigo("int temp"+con+"="+res+";\n");
						/*
						estTablaS datosInt=new estTablaS("int", "var"+con,res, 0, 0, contcar);
						Consola("tipo de dato "+"int"+" "+id+"="+lexema+" pos: "+contcar, contcar,false);
						GenerarCodigo(datosInt.id+"="+datosInt.asignacion+";");
						TF.add(datosInt);
						*/
						res="";
						break;
					}
					
				}
			}
			while(existeoper2());
			System.out.println(idfind.id+"="+caracteres.get(0));
			GenerarCodigo(idfind.id+"="+caracteres.get(0)+";\n");
			//GenerarCodigo(idfind.id+"="+caracteres.get(0)+"\n");
			
			//System.out.println("tipo de dato "+tipoDato+" "+id+"="+lexema+" pos: "+contcar);
			caracteres.removeAllElements();
			  
			
			
			
	  }
	  
	  public datos IdOperacion(datos d)
	  {
		  estTablaS idfind;
		  idfind=traerID(id); 
		  caracter=d.carac;  
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren;
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error;
		  id=d.id;
		  contcar=d.contadorC;
		  
		  id=lexema;
		  idfind.asignacion=lexema+caracter;
		  caracteres.add(lexema);
		  caracteres.add(caracter);
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
				  Consola(idfind.id+"="+idfind.asignacion, contcar, false);
				  caracteres.add(lexema);
				  caracteres.add(caracter);
				  
			  }
			 else
			 {
				 if(isEntero(lexema) && caracter.equals(";"))
				  {
					  idfind.asignacion+=lexema;
					  //System.out.println("Error se esperaba un terminal ; pos:"+contcar);
					  System.out.println(idfind.id+"="+idfind.asignacion);
					  Consola(idfind.id+"="+idfind.asignacion, contcar, false);
					  //GenerarCodigo(tabula+idfind.id+"="+idfind.asignacion+";\n");
					  contcar++;
					  columna++;
					  caracteres.add(lexema);
				
					  Termino=false;
					  break;
				  }
				 else
				 {
					 if(isEntero(lexema) && !isOperador(caracter) && caracter.equals("."))
					  {
						 System.out.println("Error en el tipo de dato de \""+lexema+caracter +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
						 Consola("Error en el tipo de dato de \""+lexema+caracter +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar, contcar, true);
						  error=true;
						  break; 
					  }
					 else
					 {
						 if(!isEntero(lexema) && !isIdent(lexema))
						  {
							 System.out.println("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
							 Consola("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar, contcar, true);
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
										  if(!idfind2.asignacion.equals(""))
										  {
											  idfind.asignacion+=lexema+caracter;
											  //System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);
											  System.out.println(idfind.id+"="+idfind.asignacion);
											  Consola(idfind.id+"="+idfind.asignacion, contcar, false);
											  caracteres.add(lexema);
											  caracteres.add(caracter);
											  
										  }
										  else
										  {
											  System.out.println("Error "+lexema+" no se ha inicializado pos: "+contcar);
											  Consola("Error "+lexema+" no se ha inicializado pos: "+contcar, contcar, true);
											  error=true;
											  break;
										  }
									  }
									  else
									  {
										  System.out.println("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
										  Consola("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar, contcar, true);
										  error=true;
										  break;  
									  }
									  
								  }
								  else
								  {
									  System.out.println("Error \""+lexema+ "\" no esta declarado pos:"+contcar);
									  Consola("Error \""+lexema+ "\" no esta declarado pos:"+contcar, contcar, true);
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
											  if(!idfind2.asignacion.equals(""))
											  {
												  idfind.asignacion+=lexema;
												  //System.out.println(tabula+"Asignacion "+id+"="+lexema+" pos: "+contcar);
												  System.out.println(idfind.id+"="+idfind.asignacion);
												  contcar++;
												  columna++;
												  Consola(idfind.id+"="+idfind.asignacion, contcar, false);
												 // GenerarCodigo(tabula+idfind.id+"="+idfind.asignacion+";\n");
												  caracteres.add(lexema);
												  
												  Termino=false;
												  break;
											  }
											  else
											  {
												  System.out.println("Error "+lexema+" no se ha inicializado pos: "+contcar);
												  Consola("Error "+lexema+" no se ha inicializado pos: "+contcar, contcar, true);
												  error=true;
												  break;
											  }
										  }
										  else
										  {
											  System.out.println("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar);
											  Consola("Error en el tipo de dato de \""+lexema +"\" se esperaba un "+idfind.tipoDato+ " pos:"+contcar, contcar, true);
											  error=true;
											  break;  
										  }
									  }
									  else
									  {
										  System.out.println("Error \""+lexema+ "\" no esta declarado pos:"+contcar);
										  Consola("Error \""+lexema+ "\" no esta declarado pos:"+contcar, contcar, true);
										  error=true;
										  break; 
									  }
								  }
								 else
								 {
									 System.out.println("Error se esperaba un terminal \";\" pos:"+contcar);
									 Consola("Error se esperaba un terminal \";\" pos:"+contcar, contcar, true);
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
		  NotacionOperadores(idfind);
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
	  
	public void  GenerarCodigo(boolean iniOfin)
	{
		if(iniOfin)
		{
			area2.append("#include<stdio.h>\n#include <stdlib.h>\n#include <string.h>\nint main()\n{\n\n");
			bEjecutar.setEnabled(true);
		}
		else
		{
			area2.append("\nsystem(\"PAUSE\");\nreturn 0;\n}");
			nv=0;
		    con=0;
		    bEjecutar.setEnabled(true);
		}
		
	}
	
	 public datos Switch(datos d)
	  {
		  caracter=d.carac;    
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren; 
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error; 
		  id=d.id;
		  contcar=d.contadorC;
		  String idenviar;
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
				    			  idenviar=lexema;
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
													    			  //System.out.println("switch("+idFind.id+")");
													    			  //System.out.println(tabula+"{");
													    			  Consola("declaracion switch("+idFind.id+")\n", contcar, false);
													    			  
													    			  //GenerarCodigo("switch("+idFind.id+")\n");
													    			  //GenerarCodigo("{\n");
													    			  //tabula+="\t";
													    			  d=new datos(lexema, caracter, renglon, columna, error, idenviar, linea,tipoDato,contcar);
																 	  d=SwitchCases(d);  
																	  caracter=d.carac;  
																	  lexema=d.lexema;
																	  columna=d.col;
																	  renglon=d.ren;
																	  tipoDato=d.tipoD;
																	  linea=d.line;
																	  error=d.error;
																	  id=d.id; 
																	  contcar=d.contadorC;
																	  INT=false;
																	  if(error)
																		  break; 
																	  if(caracter.equals("}"))
																	  {
																		  columna++;
																		  contcar++;
																		  GenerarCodigo("finsw:\n");
																		  
																	  }
																	  else
																	  {
																		  Consola("error se esperaba un terminal \"}\" pos= "+contcar+"\n", contcar, true);
																		  System.out.println("error se esperaba un terminal \"}\"");
																		  error=true;
																		  break;
																	  }
																	  
																	  //GenerarCodigo("}\n");
																	  System.out.println("}");
																	  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
																  }
																  else
																  {
																	  Consola("error se esperaba un delimitador \"{\""+ "pos= "+contcar+"\n", contcar, true);
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
				    						  Consola("error se esperaba un delimitador \")\""+ "pos= "+contcar+"\n", contcar, true);
				    				    		 error=true;
				    				    		 break; 
				    					  }
				    				  }
				    				  else
				    				  {
				    					  System.out.println("error "+lexema+" no se esta inicializado");
				    					  Consola("error "+lexema+" no se esta inicializado"+ "pos= "+contcar+"\n", contcar, true);
				    					  error=true;
				 			    		 break;
				    				  }
				    			  }
				    			  else
				    			  {
				    				  System.out.println("error "+lexema+" no se encuentra declarado pos: "+contcar);
				    				  Consola("error "+lexema+" no se encuentra declarado pos: "+contcar+"\n", contcar, true);
				    				  error=true;
							    		 break;
				    			  }
				    			  
				    		  }
				    		  else 
			    			  {
				    			  System.out.println("error "+lexema+" no se encuentra declarado pos: "+contcar);
				    			  Consola("error "+lexema+" no se encuentra declarado pos: "+contcar+"\n", contcar, true);
			  
			    				  error=true;
						    		 break;
			    			  } 
				    	   }
				    	   else
				    	   {
				    		 System.out.println("error se esperaba un delimitador \"(\"");
				    		 Consola("error se esperaba un delimitador \"(\""+" pos: "+contcar+"\n", contcar, true);
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
		  caracter=d.carac;    
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren; 
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error; 
		  id=d.id;
		  contcar=d.contadorC;
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
					 		 	        r=isCaracter(caracter);
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
						 			        r=isCaracterX2(caracter);
						 			     }
						 			     else 
						 			    	 r=false;
						 			  }
						 			  while(r);
									  estTablaS idFind=traerID(id);
									  if(idFind.tipoDato.equals("int"))
									  {
										  if(isEntero(lexema) && caracter.equals(":"))
										  {
											  cases+=" "+lexema+caracter;
											    
											    nva5=nvari();
												System.out.println("int "+nva5+"="+idFind.id+";\n");
												GenerarCodigo("int "+nva5+"="+idFind.id+";\n");
												nva6=nvari();
												System.out.println("int "+nva6+"="+lexema+";\n");
												GenerarCodigo("int "+nva6+"="+lexema+";\n");
												System.out.println("if("+nva5+"=="+nva6+")\n{\n");
												GenerarCodigo("if("+nva5+"=="+nva6+")\n{\n");

											  System.out.println(cases);
											  Consola(cases+"\n", contcar, false);
											  //GenerarCodigo(cases+"\n");
											  contcar+=3;
											  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
											  d=ProposicionSwitch(d);  
											  caracter=d.carac;  
											  lexema=d.lexema;
											  columna=d.col;
											  renglon=d.ren;
											  tipoDato=d.tipoD;
											  linea=d.line; 
											  error=d.error;
											  id=idFind.id; 
											  contcar=d.contadorC;
											  
											  if(lexema.equals("break"))
											  {
												  System.out.println("break;");
												  Consola("break;\n", contcar, false);
												  System.out.println("\tgoto finsw;\n}\n");
												  GenerarCodigo("\tgoto finsw;\n}\n");
												  break;
												  
											  }
											  
										  }
										  else
										  {
											  
											  if(isEntero(lexema) && !caracter.equals(":"))
											  {
												  System.out.println("error se esperaba un terminal \":\" pos: "+contcar);
												  Consola("error se esperaba un terminal \":\" pos: "+contcar, contcar, true);
												  
												  error=true;
												  break;
											  }
											  else
											  {
												  System.out.println("error en el tipo de dato se esperaba un int pos: "+contcar);
												  Consola("error en el tipo de dato se esperaba un int pos: "+contcar, contcar, true);
												  error=true;
												  break;
											  }
										  }
									  }
									  else
									  {
										 System.out.println("error en el tipo de dato se esperaba un int");
										 Consola("error en el tipo de dato se esperaba un int " +"pos: "+contcar, contcar, true);
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
										  Consola("error se esperaba un terminal \"case\" o \"}\""+" pos: "+contcar, contcar, true);
										  error=true;
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
	  

	public datos Proposicion(datos d2)
	{
		datos d;
		  caracter=d2.carac;  
		  lexema=d2.lexema;
		  columna=d2.col;
		  renglon=d2.ren;
		  tipoDato=d2.tipoD;
		  linea=d2.line;
		  error=d2.error;
		  id=d2.id;
		  contcar=d2.contadorC;
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
			    		if(columna<linea.length())
			    			caracter=linea.charAt(columna)+"";
			    		else
			    		{
			    			break;
			    		}
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
								  caracter=d.carac; 
								  lexema=d.lexema;
								  columna=d.col;
								  renglon=d.ren;
								  tipoDato=d.tipoD;
								  linea=d.line;
								  error=d.error;
								  id=d.id; 
								  contcar=d.contadorC;
								  
							  }
							  else
							  {
								  if(ischar(lexema))
								  {
									  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
									  d=Char(d);
									  caracter=d.carac; 
									  lexema=d.lexema;
									  columna=d.col;
									  renglon=d.ren;
									  tipoDato=d.tipoD;
									  linea=d.line;
									  error=d.error;
									  id=d.id; 
									  contcar=d.contadorC;
								  }
								  else
								  {
									  if(isfloat(lexema))
									  {
										  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
										  d=Float(d);
										  caracter=d.carac; 
										  lexema=d.lexema;
										  columna=d.col;
										  renglon=d.ren;
										  tipoDato=d.tipoD;
										  linea=d.line;
										  error=d.error;
										  id=d.id; 
										  contcar=d.contadorC;
									  }
									  else
									  {
										  if(isString(lexema))
										  {
											  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
											  d=String(d);
											  caracter=d.carac; 
											  lexema=d.lexema;
											  columna=d.col;
											  renglon=d.ren;
											  tipoDato=d.tipoD;
											  linea=d.line;
											  error=d.error;
											  id=d.id; 
											  contcar=d.contadorC;
										  }
										  else
										  {
											  if(isIF(lexema))
											  {
												  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
												  d=ifs(d);
												  caracter=d.carac; 
												  lexema=d.lexema;
												  columna=d.col;
												  renglon=d.ren;
												  tipoDato=d.tipoD;
												  linea=d.line;
												  error=d.error;
												  id=d.id; 
												  contcar=d.contadorC;
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
													  caracter=d.carac; 
													  lexema=d.lexema;
													  columna=d.col;
													  renglon=d.ren;
													  tipoDato=d.tipoD; 
													  linea=d.line;
													  error=d.error;
													  id=d.id; 
													  contcar=d.contadorC;
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
				contcar++;
			}
			while(linea!=null && error==false);
		} 
		catch(IOException e)
		{
			System.out.println("Error de apertura del archivo");
		}
	  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	}
	 
	public datos ProposicionSwitch(datos d2)
	{
		datos d;
		  caracter=d2.carac;  
		  lexema=d2.lexema;
		  columna=d2.col;
		  renglon=d2.ren;
		  tipoDato=d2.tipoD;
		  linea=d2.line;
		  error=d2.error;
		  id=d2.id;
		  contcar=d2.contadorC;
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
			    		if(columna<linea.length())
			    			caracter=linea.charAt(columna)+"";
			    		else
			    		{
			    			break;
			    		}
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
							  
												  if(lexema.equals("break"))
												  {
													  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
												  }
												  if(isIdent(lexema))
												  {
													  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
													  d=IdAsignacion(d);
													  caracter=d.carac; 
													  lexema=d.lexema;
													  columna=d.col;
													  renglon=d.ren;
													  tipoDato=d.tipoD; 
													  linea=d.line;
													  error=d.error;
													  id=d.id; 
													  contcar=d.contadorC;
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
				contcar++;
			}
			while(linea!=null && error==false);
		} 
		catch(IOException e)
		{
			System.out.println("Error de apertura del archivo");
		}
	  return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
	}
	
	public void Programa()
	{
	  GenerarCodigo(true);
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
			    	 // linea=linea.trim();
			    	
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
			    			concar++;
			    		}

			    		if(columna<linea.length())
			    			caracter=linea.charAt(columna)+"";
			    		else
			    		{
			    			break;
			    		}
			    		
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
								  caracter=d.carac; 
								  lexema=d.lexema;
								  columna=d.col;
								  renglon=d.ren;
								  tipoDato=d.tipoD;
								  linea=d.line;
								  error=d.error;
								  id=d.id; 
								  concar=d.contadorC;
								  
							  }
							  else
							  {
								  if(ischar(lexema))
								  {
									  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
									  d=Char(d);
									  caracter=d.carac; 
									  lexema=d.lexema;
									  columna=d.col;
									  renglon=d.ren;
									  tipoDato=d.tipoD;
									  linea=d.line;
									  error=d.error;
									  id=d.id; 
									  concar=d.contadorC;
								  }
								  else
								  {
									  if(isfloat(lexema))
									  {
										  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
										  d=Float(d);
										  caracter=d.carac; 
										  lexema=d.lexema;
										  columna=d.col;
										  renglon=d.ren;
										  tipoDato=d.tipoD;
										  linea=d.line;
										  error=d.error;
										  id=d.id; 
										  concar=d.contadorC;
									  }
									  else
									  {
										  if(isString(lexema))
										  {
											  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
											  d=String(d);
											  caracter=d.carac; 
											  lexema=d.lexema;
											  columna=d.col;
											  renglon=d.ren;
											  tipoDato=d.tipoD;
											  linea=d.line;
											  error=d.error;
											  id=d.id; 
											  concar=d.contadorC;
										  }
										  else
										  {
											  if(isIF(lexema))
											  {
												  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
												  d=ifs(d);
												  caracter=d.carac; 
												  lexema=d.lexema;
												  columna=d.col;
												  renglon=d.ren;
												  tipoDato=d.tipoD;
												  linea=d.line;
												  error=d.error;
												  id=d.id; 
												  concar=d.contadorC;
											  }
											  else
											  {
												  if(isSwitch(lexema))
												  {
													  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
													  //d=A.Switch(d,contArch);
													  d=Switch(d);
													  caracter=d.carac; 
													  lexema=d.lexema;
													  columna=d.col;
													  renglon=d.ren;
													  tipoDato=d.tipoD; 
													  linea=d.line;
													  error=d.error;
													  id=d.id; 
													  concar=d.contadorC;										  
													 
												  }
												  else
												  {
													  if(isIdent(lexema))
													  {
														  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,concar);
														  d=IdAsignacion(d);
														  caracter=d.carac; 
														  lexema=d.lexema;
														  columna=d.col;
														  renglon=d.ren;
														  tipoDato=d.tipoD; 
														  linea=d.line;
														  error=d.error;
														  id=d.id; 
														  concar=d.contadorC;
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
	  GenerarCodigo(false);
	  if(error)
	  {
		  area2.setText("");
	  }
	}

	
	
	
	public datos ifes(datos d) 
	{
		  caracter=d.carac;    
		  lexema=d.lexema;
		  columna=d.col;
		  renglon=d.ren; 
		  tipoDato=d.tipoD;
		  linea=d.line;
		  error=d.error; 
		  id=d.id; 
		  contcar=d.contadorC;
		nva=nvari();
		System.out.println("int "+nva+"="+var+";");
		GenerarCodigo("int "+nva+"="+var+";\n");
		if(varin.equals(vcomp))
		{
			if(vcomp.equals(vop))
			{
				if(vcomp.equals(comp))
				{
					nva2=nvari();
					System.out.println("int "+nva2+"="+nva+";\n");
					GenerarCodigo("int "+nva2+"="+nva+";\n");
					nva3=nvari();
					System.out.println("int "+nva3+"="+nva+";\n");
					GenerarCodigo("int "+nva3+"="+nva+";\n");
					nva4=nvari();
					System.out.println("int "+nva4+"="+nva+";\n");
					GenerarCodigo("int "+nva4+"="+nva+";\n");
					System.out.println("etafor:\n");
					GenerarCodigo("etaif:\n");
					System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
					GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
					
					  d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
					  d=Proposicion(d); 
					  caracter=d.carac; 
					  lexema=d.lexema;
					  columna=d.col;
					  renglon=d.ren;
					  tipoDato=d.tipoD;
					  linea=d.line;
					  error=d.error;
					  id=d.id; 
					  contcar=d.contadorC;
					
					if(inc.equals("+"))
					{
						System.out.println("\t"+nva2+"="+nva2+"+1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
					}
					else
					{
						System.out.println("\t"+nva2+"="+nva2+"-1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
					}
					
					
				}
				else
				{
					nva2=nvari();
					System.out.println("int "+nva2+"="+nva+";\n");
					GenerarCodigo("int "+nva2+"="+nva+";\n");
					nva3=nvari();
					System.out.println("int "+nva3+"="+comp+";\n");
					GenerarCodigo("int "+nva3+"="+comp+";\n");
					nva4=nvari();
					System.out.println("int "+nva4+"="+nva+";\n");
					GenerarCodigo("int "+nva4+"="+nva+";\n");
					System.out.println("etafor:\n");
					GenerarCodigo("etafor:\n");
					System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
					GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
					
					d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
					  d=Proposicion(d); 
					  caracter=d.carac; 
					  lexema=d.lexema;
					  columna=d.col;
					  renglon=d.ren;
					  tipoDato=d.tipoD;
					  linea=d.line;
					  error=d.error;
					  id=d.id; 
					  contcar=d.contadorC;
					
					if(inc.equals("+"))
					{
						System.out.println("\t"+nva2+"="+nva2+"+1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
					}
					else
					{
						System.out.println("\t"+nva2+"="+nva2+"-1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
					}
					
				}
				
			}
			else
			{
				if(comp.equals(vop)) {
					nva2=nvari();
					System.out.println("int "+nva2+"="+nva+";");
					GenerarCodigo("int "+nva2+"="+nva+";\n");
					nva3=nvari();
					System.out.println("int "+nva3+"="+comp+";");
					GenerarCodigo("int "+nva3+"="+comp+";\n");
					nva4=nvari();
					System.out.println("int "+nva4+"="+nva3+";");
					GenerarCodigo("int "+nva4+"="+nva3+";\n");
					
					System.out.println("etafor:\n");
					GenerarCodigo("etaif:\n");
					System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
					GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
					d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
					  d=Proposicion(d); 
					  caracter=d.carac; 
					  lexema=d.lexema;
					  columna=d.col;
					  renglon=d.ren;
					  tipoDato=d.tipoD;
					  linea=d.line;
					  error=d.error;
					  id=d.id; 
					  contcar=d.contadorC;
					
					if(inc.equals("+"))
					{
						System.out.println("\t"+nva2+"="+nva2+"+1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
					}
					else
					{
						System.out.println("\t"+nva2+"="+nva2+"-1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
					}
				
				}
				else
				{

					nva2=nvari();
				System.out.println("int "+nva2+"="+nva+";");
				GenerarCodigo("int "+nva2+"="+nva+";\n");
				nva3=nvari();
				System.out.println("int "+nva3+"="+comp+";");
				GenerarCodigo("int "+nva3+"="+comp+";\n");
				nva4=nvari();
				System.out.println("int "+nva4+"="+vop+";");
				GenerarCodigo("int "+nva4+"="+vop+";\n");
				System.out.println("etafor:\n");
				GenerarCodigo("etaif:\n");
				System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
				GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
				d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
				  d=Proposicion(d); 
				  caracter=d.carac; 
				  lexema=d.lexema;
				  columna=d.col;
				  renglon=d.ren;
				  tipoDato=d.tipoD;
				  linea=d.line;
				  error=d.error;
				  id=d.id; 
				  contcar=d.contadorC;
				
				if(inc.equals("+"))
				{
					System.out.println("\t"+nva2+"="+nva2+"+1;\n");
					GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
				}
				else
				{
					System.out.println("\t"+nva2+"="+nva2+"-1;\n");
					GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
				}
				}
			}
			
		}
		else
		{

			if(varin.equals(comp))
			{
				if(varin.equals(vop))
				{
					if(!varin.equals(vcomp))
					{
						nva2=nvari();
						System.out.println("int "+nva2+"="+vcomp+";");
						GenerarCodigo("int "+nva2+"="+vcomp+";\n");
						nva3=nvari();
						System.out.println("int "+nva3+"="+nva+";");
						GenerarCodigo("int "+nva3+"="+nva+";\n");
						nva4=nvari();
						System.out.println("int "+nva4+"="+nva+";\n");
						GenerarCodigo("int "+nva4+"="+nva+";\n");
						System.out.println("etafor:");
						GenerarCodigo("etafor:\n");
						System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
						GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
						d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
						  d=Proposicion(d); 
						  caracter=d.carac; 
						  lexema=d.lexema;
						  columna=d.col;
						  renglon=d.ren;
						  tipoDato=d.tipoD;
						  linea=d.line;
						  error=d.error;
						  id=d.id; 
						  contcar=d.contadorC;
						
						if(inc.equals("+"))
						{
							System.out.println("\t"+nva2+"="+nva2+"+1;\n");
							GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
						}
						else
						{
							System.out.println("\t"+nva2+"="+nva2+"-1;\n");
							GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
						}
							
					}
					
				}
				else
				{
					if(vcomp.equals(vop))
					{
						nva2=nvari();
						System.out.println("int "+nva2+"="+vcomp+";");
						GenerarCodigo("int "+nva2+"="+vcomp+";\n");
						nva3=nvari();
						System.out.println("int "+nva3+"="+nva+";");
						GenerarCodigo("int "+nva3+"="+nva+";\n");
						nva4=nvari();
						System.out.println("int "+nva4+"="+nva2+";");
						GenerarCodigo("int "+nva4+"="+nva2+";\n");
						System.out.println("etafor:\n");
						GenerarCodigo("etafor:\n");
						System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{");
						GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
						d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
						  d=Proposicion(d); 
						  caracter=d.carac; 
						  lexema=d.lexema;
						  columna=d.col;
						  renglon=d.ren;
						  tipoDato=d.tipoD;
						  linea=d.line;
						  error=d.error;
						  id=d.id; 
						  contcar=d.contadorC;
						
						if(inc.equals("+"))
						{
							System.out.println("\t"+nva2+"="+nva2+"+1;\n");
							GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
						}
						else
						{
							System.out.println("\t"+nva2+"="+nva2+"-1;\n");
							GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
						}
						
					}
					else
					{
						nva2=nvari();
						System.out.println("int "+nva2+"="+vcomp+";");
						GenerarCodigo("int "+nva2+"="+vcomp+";\n");
						nva3=nvari();
						System.out.println("int "+nva3+"="+nva+";");
						GenerarCodigo("int "+nva3+"="+nva+";\n");
						nva4=nvari();
						System.out.println("int "+nva4+"="+vop+";");
						GenerarCodigo("int "+nva4+"="+vop+";\n");
						System.out.println("etafor:");
						GenerarCodigo("etafor:\n");
						System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{");
						GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
						d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
						  d=Proposicion(d); 
						  caracter=d.carac; 
						  lexema=d.lexema;
						  columna=d.col;
						  renglon=d.ren;
						  tipoDato=d.tipoD;
						  linea=d.line;
						  error=d.error;
						  id=d.id; 
						  contcar=d.contadorC;
						
						if(inc.equals("+"))
						{
							System.out.println("\t"+nva2+"="+nva2+"+1;\n");
							GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
						}
						else
						{
							System.out.println("\t"+nva2+"="+nva2+"-1;\n");
							GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
						}
					}
					
					
				}
			}
			else
			{
				if(varin.equals(vop))
				{
					nva2=nvari();
				System.out.println("int "+nva2+"="+vcomp+";");
				GenerarCodigo("int "+nva2+"="+vcomp+";\n");
				nva3=nvari();
				System.out.println("int "+nva3+"="+comp+";");
				GenerarCodigo("int "+nva3+"="+comp+";\n");
				nva4=nvari();
				System.out.println("int "+nva4+"="+nva+";");
				GenerarCodigo("int "+nva4+"="+nva+";\n");
				System.out.println("etafor:");
				GenerarCodigo("etafor:\n");
				System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{");
				GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
				d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
				  d=Proposicion(d); 
				  caracter=d.carac; 
				  lexema=d.lexema;
				  columna=d.col;
				  renglon=d.ren;
				  tipoDato=d.tipoD;
				  linea=d.line;
				  error=d.error;
				  id=d.id; 
				  contcar=d.contadorC;
				
				if(inc.equals("+"))
				{
					System.out.println("\t"+nva2+"="+nva2+"+1;\n");
					GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
				}
				else
				{
					System.out.println("\t"+nva2+"="+nva2+"-1;\n");
					GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
				}
				}
				else
				{
					nva2=nvari();
					System.out.println("int "+nva2+"="+vcomp+";");
					GenerarCodigo("int "+nva2+"="+vcomp+";\n");
					nva3=nvari();
					System.out.println("int "+nva3+"="+comp+";");
					GenerarCodigo("int "+nva3+"="+comp+";\n");
					nva4=nvari();
					System.out.println("int "+nva4+"="+vop+";");
					GenerarCodigo("int "+nva4+"="+vop+";\n");
					System.out.println("etafor:");
					GenerarCodigo("etafor:\n");
					System.out.println("if("+nva2+""+op1+""+nva3+")\n"+"{");
					GenerarCodigo("if("+nva2+""+op1+""+nva3+")\n"+"{\n");
					d=new datos(lexema, caracter, renglon, columna, error, id, linea,tipoDato,contcar);
					  d=Proposicion(d); 
					  caracter=d.carac; 
					  lexema=d.lexema;
					  columna=d.col;
					  renglon=d.ren;
					  tipoDato=d.tipoD;
					  linea=d.line;
					  error=d.error;
					  id=d.id; 
					  contcar=d.contadorC;
					
					if(inc.equals("+"))
					{
						System.out.println("\t"+nva2+"="+nva2+"+1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"+1;\n");
					}
					else
					{
						System.out.println("\t"+nva2+"="+nva2+"-1;\n");
						GenerarCodigo("\t"+nva2+"="+nva2+"-1;\n");
					}
					
				}
				
			}
		
			
		}
		
		System.out.println("\tgoto etfor;\n}\n");
		GenerarCodigo("\tgoto etafor;\n}\n");
		System.out.println("else\n{\n");
		GenerarCodigo("else\n{\n");
		System.out.println("\tgoto finfor;\n}");
		GenerarCodigo("\tgoto finfor;\n}\n");
		System.out.println("finfor:\n");
		return d=new datos(lexema, caracter, renglon, columna, error, id, linea, tipoDato, contcar);
		
		
		
	}
	
		
	
		private String nvari() {
				
				String a="";
				a="var"+nv;	
				nv++;
				//System.out.println(a);
				return a;
			}	
	
	
	
	
	
}
