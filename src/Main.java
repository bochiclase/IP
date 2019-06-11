

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
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
			System.out.println("Formato:");
			System.out.println("IP=(192.168.8.8) mensaje=(hola mundo) usuario=(bocheti)");
			System.out.print("> ");
			Scanner s = new Scanner(in.readLine());
			do {
				switch (estado) {
				case 0:

					try {

						token = s.skip("fin|FIN|IP\\s*=\\(").match().group();
						if (token.equalsIgnoreCase("fin")) {
							fin = true;
							fin2 = true;
							break;

						} else {
							estado = 1;
							fin2 = true;
						}
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba 'fin' o 'IP=('");
						fin2 = true;
						break;
					}

				case 1:
					try {
						ip = s.skip("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")
								.match().group();
						estado = 2;
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba una IP válida");
						estado = 0;
						fin2 = true;
						break;
					}

				case 2:
					try {
						token = s.skip("\\)\\s*mensaje\\=\\(.*\\)\\s*usuario\\=\\(").match().group();
						estado = 3;
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba 'mensaje=' y 'usuario='");
						estado = 0;
						fin2 = true;
						break;
					}

				case 3:

					try {
						usuario = s.skip("\\p{L}+").match().group();
						estado = 4;
					} catch (NoSuchElementException e) {
						System.out.println("Se esperaba nombre el nombre del usuario");
						estado = 0;
						fin2 = true;
						break;
					}

				case 4:
					try {
						token = s.skip("\\)").match().group();
						if (usuariosMap.containsKey(usuario)) {
							if (usuariosMap.get(usuario).containsKey(ip)) {
								usuariosMap.get(usuario).replace(ip, usuariosMap.get(usuario).get(ip), usuariosMap.get(usuario).get(ip) + 1);
								System.out.println("Nuevo mensaje para la IP " + ip + " del Usuario " + usuario);
								estado = 0;
								token = null;
								s.reset();
								fin2 = true;
							} else {
								usuariosMap.get(usuario).put(ip, 1);
								System.out.println("Nueva IP " + ip + " del Usuario " + usuario);
								estado = 0;
								token = null;
								s.reset();
								fin2 = true;
							}
						} else {
							mens = 1;
							usuariosMap.put(usuario, new HashMap<>());
							usuariosMap.get(usuario).put(ip, mens);
							System.out.println("Nuevo Usuario: " + usuario + " con la IP " + ip);
							estado = 0;
							token = null;
							s.reset();
							fin2 = true;
						}

					} catch (NoSuchElementException e) {
						System.out.println("se esperaba ')'");
						estado = 0;
						fin2 = true;
						break;
					}

				}

			} while (!fin2);
			s.close();
		} while (!fin);



		for (Entry<String, Map<String, Integer>> jugador : usuariosMap.entrySet()) {
			String clave = jugador.getKey();
			System.out.println(clave + "  ->  " + usuariosMap.get(clave));
		}
	}
}

//IP=(192.168.8.8) mensaje=(hola mundo) usuario=(bocheti)