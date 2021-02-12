package Analizador;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;



public class Comp extends JFrame
{
	JButton abrir,analizar;
	TextArea codigo; 
	JPanel panelbotones,paneltexto;
	File abre;
	boolean tipo=false;
	String nt[]={"Prog","bloque","Decvar","sentencia","sigif","restid","EL","EL'","E","E'","T","T'","F"};
	String t[]={"Program","FindePrograma","if","while","int","id","then","Begin","End","do","else","num","=",";","+","-","*","/","<",">",","};
	String tablaSintactica[][]={{"FindePrograma bloque ; id Program","","","","","","","","","","","","","","","","","","","","",""},
								{"","","","","sentencia Decvar","","","","","","","","","","","","","","","","",""},
								{"","","","","; restid id int"," ","","","","","","","","","","","","","","","",""},
								{"","","sentencia ; sigif End sentencia Begin then EL if","sentencia ; End sentencia Begin do EL while","","sentencia ; EL = id","",""," ","","","","","","","","","","","","",""},
								{"","","","","","","","","","","End sentencia Begin else","","","","","","","","","","",""},
								{"","","","","","","","","","","","",""," ","","","","","","","restid id ,"},
								{"","","","","","","","","","","","EL' E","","","","","","","","",""},
								{"","","","","","","","","","","","EL' E",""," ","","","","","EL' E <","EL' E >",""},
								{"","","","","","","","","","","","E' T","",""," ","","",""," "," ",""},
								{"","","","","","","","","","","","",""," ","E' T +","E' T -","",""," "," ",""},
								{"","","","","","","","","","","","T' F","","","","","","",""," ",""},
								{"","","","","","","","","","","","","","","","","T' F *","T' F /"," "," ",""},
								{"","","","","","","","","","","","num","","","","","","","",""}};
	LinkedList<String> lexemas=new LinkedList<>();
	LinkedList<String> iden=new LinkedList<>();
	Stack<String> pila;
	public Comp()
	{
		super("Compilador");
		setSize(600, 600);
		panelbotones=new JPanel();
		abrir=new JButton("Abrir");
		analizar=new JButton("Analizar");analizar.setEnabled(false);
		panelbotones.add(abrir);panelbotones.add(analizar);
		paneltexto=new JPanel();
		codigo=new TextArea(32,50);codigo.setEditable(false);
		paneltexto.add(codigo);
		add(panelbotones, BorderLayout.NORTH);add(paneltexto,BorderLayout.CENTER);
		abrir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				String texto="",aux="";
				int renglon=1;
				//Intenta abrir el archivo
				try
				{
					//Crea un filtro para seleccionar un archivo
					JFileChooser selectarch = new JFileChooser();
					selectarch.setFileSelectionMode(JFileChooser.FILES_ONLY);
					//Se selecciona que solo se puedan abrir archivos con extención txt
					FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de texto", "txt");
					selectarch.setFileFilter(filtro);
					int resultado = selectarch.showOpenDialog(Comp.this);
					// obtiene el archivo seleccionado
					abre = selectarch.getSelectedFile();
					if(abre!=null)
					{
					     FileReader archivo=new FileReader(abre);
					     BufferedReader lee=new BufferedReader(archivo);
					     while((aux=lee.readLine())!=null)
					     {
					    		  texto+=renglon+"|"+aux+"\n";
					    		  renglon++;
					     }
				         lee.close();
					}
				}
				catch(IOException e)
				{
					JOptionPane.showMessageDialog(null, "No se pudo abrir el archivo","Error",JOptionPane.ERROR_MESSAGE);
				}
				//Despliega el texto del archivo en el textarea
				codigo.setText(texto);
				//Habilita el boton para compilar el código
				analizar.setEnabled(true);
				
			}
		});
		analizar.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String lexema="";
				if(!codigo.getText().toString().equals(""))
				{
					pila=new Stack<>();
					pila.push("$");
					pila.push("Prog");
					try
					{
						String linea="";
						analizadorlexico a=new analizadorlexico();
						FileReader archl=new FileReader(abre);
						BufferedReader cont=new BufferedReader(archl);
						int pos=1;
						int columna=0;
						do
						{
							linea=cont.readLine();
							columna=0;
							if(linea!=null)
							{
								linea=linea.trim();
								do
								{
									if(columna<linea.length())
									{
										String caracter=linea.charAt(columna)+"";
										lexema="";
										if(a.isCaracter(caracter))
										{
											boolean r=false;
											do
											{
												lexema+=caracter;
												columna++;
												if(columna<linea.length())
												{
													caracter=linea.charAt(columna)+"";
													r=a.isCaracterX(caracter);
												}
												else
													r=false;
											}
											while(r);
											if(a.busquedapalres(lexema)!=-1)
											{
												System.out.println("Palabra reservada");
												if(lexema.equals("int"))
													tipo=true;
												Sintactico(lexema);
											}
											else
												if(a.isIdent(lexema))
												{
													if(tipo)
														iden.add(lexema);
													System.out.println("Identificador");
													Sintactico("id");
												}
										}
										else
											if(Character.isDigit(caracter.charAt(0)))
											{
												boolean r=false;
												do
												{
													lexema+=caracter;
													columna++;
													if(columna<linea.length())
													{
														caracter=linea.charAt(columna)+"";
														r=a.isNumx(caracter);
													}
													else
														r=false;
												}
												while(r);
												if(a.isEntero(lexema))
												{
													System.out.println("Número entero");
													Sintactico("num");
												}
												else
													if(a.isReal(lexema))
													{
														System.out.println("Número real");
														Sintactico("numf");
													}
													else
													{
														System.out.println("Simbolo no valido");
													}
											}
											else
												if(a.isPunto(caracter))
												{
													boolean r=false;
													do
													{
														lexema+=caracter;
														columna++;
														if(columna<linea.length())
														{
															caracter=linea.charAt(columna)+"";
															r=a.isNumx(caracter);
														}
														else
															r=false;
													}
													while(r);
													if(a.isReal(lexema))
													{
														System.out.println("Número real");
														Sintactico("numf");
													}
													else
														if(a.isPunto(lexema))
														{
															if(lexema.equals(";"))
																tipo=false;
															System.out.println("Signo de puntuación");
														}
												}
												else
													if(a.isDelimitador(caracter))
													{
														lexema+=caracter;
														if(lexema.equals("\""))
														{
															columna++;
															lexema="";
															while(columna<linea.length())
															{
																caracter=linea.charAt(columna)+"";
																if(caracter.equals("\""))
																	break;
																else
																	lexema+=caracter;
																columna++;
															}
															System.out.println("Literal");
															Sintactico("literal");
															columna++;
														}
														else
														{
															if(lexema.equals("'"))
															{
																columna++;
																lexema="";
																while(columna<linea.length())
																{
																	caracter=linea.charAt(columna)+"";
																	if(caracter.equals("\'"))
																		break;
																	else
																		lexema=caracter;
																	columna++;
																}
																System.out.println("Caracter");
																Sintactico("car");
																columna++;
															}
															else
															{
																columna++;
															System.out.println("Delimitador");
															Sintactico(lexema);
															}
														}
													}
													else
														if(a.isSignoPuntuacion(caracter))
														{
															lexema+=caracter;
															columna++;
															if(lexema.equals(";"))
																tipo=false;
															System.out.println("Signo de puntuación");
															Sintactico(lexema);
														}
														else
															if(a.isOperador(caracter))
															 {
																 lexema=caracter;
																 columna++;
																 System.out.println("Operador");
																 Sintactico(lexema);
																 lexema="";
															 }
																	else
																	{
																		System.out.println("Caracter invalido "+lexema);
																		columna++;
																	}
									}
								}
								while(columna<linea.length());
							}
							pos++;
						}
						while(linea!=null);
					}
					catch(IOException g)
					{
						System.out.println("Error de Apertura del Archivo");
					}
				}
				System.out.println("tipo\tid");
				for(int i=0;i<iden.size();i++)
					System.out.println("int\t"+iden.get(i));
			}
		});
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void Sintactico(String lex)
	{
		while(!pila.peek().equals("$") && !pila.peek().equals(lex))
		{
			int terminal=T(lex),noterminal=NT(pila.peek());
			System.out.println("Pila "+pila.peek()+" llego "+lex+" ["+noterminal+"]["+terminal+"]");
			if(noterminal!=-1)
			{
				String produccion=tablaSintactica[noterminal][terminal];
				System.out.println("Produccion "+produccion);
				if(!produccion.equals(""))
					if(produccion.equals(" "))
						System.out.println("Descartar "+pila.pop());
					else
					{
						System.out.println("Producir "+pila.pop());
						Producir(produccion);
					}
				else
				{
					System.out.println("No se esperaba "+lex);
					break;
				}
			}
			else
			{
				System.out.println("No se esperaba el lexema "+lex);
				break;
			}
		}
		if(pila.peek().equals(lex))
			System.out.println("Coincidieron "+pila.pop());
	}
	public int T(String te)
	{
		int ter=0;
		for(;ter<t.length;ter++)
			if(te.equals(t[ter]))
				break;
		return ter;
	}
	public int NT(String no)
	{
		int n=0;
		for(;n<nt.length;n++)
			if(no.equals(nt[n]))
				break;
		if(n==nt.length)
			n=-1;
		return n;
	}
	public void Producir(String prod)
	{
		String l="";
		for(int i=0;i<prod.length();i++)
		{
			if((prod.charAt(i)+"").equals(" "))
			{
				pila.add(l);
				System.out.println("Ingreso "+l);
				l="";
			}
			else
				l+=prod.charAt(i);
		}
		if(!l.equals(""))
		{
			pila.add(l);
			System.out.println("Ingreso "+l);
		}
	}
	public static void main(String[] args)
	{
		new Comp();
	}
}
