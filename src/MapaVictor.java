

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MapaVictor {
	public static void main(String[] args) throws IOException {
		Map<String, Integer> ipMap = new HashMap<>();
		Map<String, Map<String, Integer>> usuariosMap = new HashMap<>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String ip = null;
		String usuario = null;
		int mens = 0;
		boolean fin = false;
		boolean fin2 = false;
		String token;
		int estado = 0;
		do {
			System.out.println("Introducir los datos:");
			System.out.print("> ");
			Scanner s = new Scanner(in.readLine());
			do {
				switch (estado) {
				case 0:

					try {

						token = s.skip("fin|FIN|IP\\s*=\\(").match().group();
						System.out.println("0");
						if (token.equalsIgnoreCase("fin")) {
							fin=true;
							fin2=true;
							
						} else {
							estado = 1;
							fin2= true;
						}
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba 'fin' o 'IP=('");
						fin2=true;
						break;
					}

				case 1:
					try {
						ip = s.skip(
								"(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")
								.match().group();
						System.out.println("1");
						estado = 2;
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba una IP válida");
						estado =0 ;
						fin2=true;
						break;
					}

				case 2:
					try {
						token = s.skip("\\)\\s*mensaje\\=\\(.*\\)\\s*usuario\\=\\(").match().group();
						mens++;
						estado = 3;
						System.out.println("2");
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba 'mensaje=' y 'usuario='");
						estado =0 ;
						fin2=true;
						break;
					}

				case 3:
					
					try {
						usuario = s.skip("\\p{L}+").match().group();
						estado = 4;
						System.out.println("3");
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba nombre el nombre del usuario");
						estado =0 ;
						fin2=true;
						break;
					}

				case 4:
					try {
						token = s.skip("\\)").match().group();
						ipMap.put(ip, mens);
						usuariosMap.put(usuario, ipMap);
						System.out.println(usuariosMap.get(usuario));
						estado=0;
						token=null;
						s.reset();
						fin2 = true;
					
					} catch (NoSuchElementException e) {
						System.out.println("se esperaba ')'");
						estado =0 ;
						fin2=true;
						break;
						
					}

				}

			} while (!fin2);
		} while (!fin);
		
		
		
		for (Entry<String, Map<String, Integer>> jugador : usuariosMap.entrySet()){
			String clave = jugador.getKey();
			Map<String, Integer> valor = jugador.getValue();
			System.out.println(clave+"  ->  "+ usuariosMap.get(clave));
		}
		
		//for (Entry<String, Map<String, Integer>> e: usuariosMap.entrySet()) {
			
	//			System.out.println( e + usuariosMap.values() );
	//	}
			
			// System.out.println(usuariosMap.get(usuario));
		

		// System.out.println("FIN DEL PROGRAMA");
		
		
		//System.out.println(usuariosMap.get(usuario) + usuario);

	}

}


//IP=(192.168.8.8) mensaje=(hola mundo) usuario=(bocheti)