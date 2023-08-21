package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import eccezioni.*;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {

	// Crea un nuovo scanner per il file di testo
	Scanner sc5;
	Scanner sc6;
	Scanner sc7;
	Scanner sc8;
	Scanner sc9;
	Scanner sc10;
	Scanner sc11;
	Scanner sc12;
	Scanner sc13;
	Scanner sc14;

	{
		try {

			sc5 = new Scanner("./src/test/data/testScanner/testEOF.txt");
			sc6 = new Scanner("./src/test/data/testScanner/erroriIdNumbers.txt");
			sc7 = new Scanner("./src/test/data/testScanner/testFLOAT.txt");
			sc8 = new Scanner("./src/test/data/testScanner/testID.txt");
			sc9 = new Scanner("./src/test/data/testScanner/testINT.txt");

			sc11 = new Scanner("./src/test/data/testScanner/testOperators.txt");




		} catch (FileNotFoundException e) {
			// Solleva l'eccezione se il file di testo non è stato trovato
			e.printStackTrace();
		}
	}

	
	@Test
	void testEOF() {
		
		try {
			assertEquals("<EOF,r:3>",sc5.nextToken().toString());
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testErroriIdNumbers() {
		
		try {
			Throwable e = assertThrows(LessicaleException.class, () -> sc6.nextToken());
			assertEquals(e.getMessage()," Il carattere a in riga 1 non è legale per l'inizio di un Token");
			assertEquals("<ID,r:1,sd>",sc6.nextToken().toString());
			Throwable e1 = assertThrows(LessicaleException.class, () -> sc6.nextToken());
			assertEquals(e1.getMessage(),"Il carattere . in riga 2 non è legale per l'inizio di un Token\n");
			assertEquals("<INT,r:2,123>",sc6.nextToken().toString());
			assertEquals("<ID,r:3,asd>",sc6.nextToken().toString());
			Throwable e2 = assertThrows(LessicaleException.class, () -> sc6.nextToken());
			assertEquals(e2.getMessage(),"Il carattere . in riga 3 non è legale per l'inizio di un Token\n");
			assertEquals("<INT,r:3,123>",sc6.nextToken().toString());
			Throwable e3 = assertThrows(LessicaleException.class, () -> sc6.nextToken());
			assertEquals(e3.getMessage(),"Il carattere a letto in riga 4 non è valido per i caratteri numerici\n");
			assertEquals("<ID,r:4,sd>",sc6.nextToken().toString());
			Throwable e4 = assertThrows(LessicaleException.class, () -> sc6.nextToken());
			assertEquals(e4.getMessage(),"Il carattere 6 letto in riga 5 non è valido per i caratteri letterali\n");
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testFLOAT() {
		try {
			Throwable e = assertThrows(LessicaleException.class, () -> sc7.nextToken());
			assertEquals(e.getMessage(),"Il Token non è valido perchè lo zero come prima cifra non può essere seguito da numeri o da lettere\n");
			assertEquals("<FLOAT,r:1,8.895>", sc7.nextToken().toString());
			Throwable e1 = assertThrows(LessicaleException.class, () -> sc7.nextToken());
			assertEquals(e1.getMessage(),"Il Token non è valido per i caratteri numerici perché ci sono 0 decimali\n");
			Throwable e2 = assertThrows(LessicaleException.class, () -> sc7.nextToken());
			assertEquals(e2.getMessage(),"Il Token non è valido per i caratteri numerici perché ci sono 8 decimali\n");
			assertEquals("<FLOAT,r:7,99.89>", sc7.nextToken().toString());
			assertEquals("<FLOAT,r:9,5.0>", sc7.nextToken().toString());
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	}
		
	@Test
	void testID() {
		try {
			assertEquals("<ID,r:1,jskjdsfhkjdshkf>",sc8.nextToken().toString());
			assertEquals("<ID,r:2,printl>",sc8.nextToken().toString());
			assertEquals("<ID,r:4,hhhjj>",sc8.nextToken().toString());
			Throwable e = assertThrows(LessicaleException.class, () -> sc8.nextToken());
			assertEquals(e.getMessage(),"Il carattere 2 letto in riga 6 non è valido per i caratteri letterali\n");
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testINT() {
		try {
			assertEquals("<INT,r:2,698>",sc9.nextToken().toString());
			//Throwable e = assertThrows(LessicaleException.class, () -> sc9.nextToken());
			//assertEquals(e.getMessage(),"Il carattere a letto in riga 3 non è valido per i caratteri numerici\n");
			assertEquals("<INT,r:4,231>",sc9.nextToken().toString());
			Throwable e1 = assertThrows(LessicaleException.class, () -> sc9.nextToken());
			assertEquals(e1.getMessage(),"Il carattere a letto in riga 5 non è valido per i caratteri numerici\n");
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	
	}
	

	
	@Test
	void testOperators()  {
		try {
			assertEquals("<PLUS,r:1>",sc11.nextToken().toString());
			assertEquals("<MINUS,r:2>",sc11.nextToken().toString());
			assertEquals("<TIMES,r:2>",sc11.nextToken().toString());
			assertEquals("<DIV,r:3>",sc11.nextToken().toString());
			assertEquals("<ASSIGN,r:8>",sc11.nextToken().toString());
			assertEquals("<SEMI,r:10>",sc11.nextToken().toString());
		}
		catch (LessicaleException e) {
			e.printStackTrace();
		}
	}

	 




	@Test
	public void testNextToken_ReadsFloat() throws Exception {
		File file = new File("src/test/data/testScanner/test2.txt");
		Scanner scanner = new Scanner(file.getAbsolutePath());
		Token token = scanner.nextToken(); // legge "2.0" dalla prima riga del file
		assertEquals(TokenType.FLOAT, token.getType());
		assertEquals("1.5", token.getVal());
		token = scanner.nextToken(); // legge "1.5" dalla seconda riga del file
		assertEquals(TokenType.INT, token.getType());
		assertEquals("4", token.getVal());
		token = scanner.nextToken(); // legge "+" dalla terza riga del file
		//assertEquals(TokenType.ID, token.getType());
		assertEquals(TokenType.PLUS, token.getType());
		token = scanner.nextToken(); // legge ";"
		assertEquals(TokenType.SEMI, token.getType());
		token = scanner.nextToken(); // legge "0.5"
		assertEquals(TokenType.FLOAT, token.getType());
		assertEquals("0.5", token.getVal());
		token = scanner.nextToken();
		assertEquals(TokenType.FLOAT, token.getType());
		assertEquals("1111111.11111", token.getVal());
		LessicaleException e1 = assertThrows(LessicaleException.class, () -> scanner.nextToken());
		assertEquals("Il Token non è valido per i caratteri numerici perché ci sono 6 decimali\n", e1.getMessage());
		LessicaleException e2 = assertThrows(LessicaleException.class, () -> scanner.nextToken());
		assertEquals("Il carattere a in riga 10 non è legale per l'inizio di un Token\n", e2.getMessage());
		LessicaleException e3 = assertThrows(LessicaleException.class, () -> scanner.nextToken());
		assertEquals("Il carattere b letto in riga 11 non è valido per i caratteri numerici\n", e3.getMessage());
		//QUESTO è BUGGATO E NON CAPISCO PERCHE NON VADA
		LessicaleException e4 = assertThrows(LessicaleException.class, () -> scanner.nextToken());
		assertEquals("Il carattere b in riga 11 non è legale per l'inizio di un Token\n", e4.getMessage());
		LessicaleException e5 = assertThrows(LessicaleException.class, () -> scanner.nextToken());
		assertEquals("Il carattere . in riga 13 non è legale per l'inizio di un Token\n", e5.getMessage());
		token = scanner.nextToken(); // praticamente qua legge il 5 dopo il punto che dava errore
		token = scanner.nextToken(); // qua legge il 6.
		assertEquals(TokenType.FLOAT, token.getType());
		assertEquals("6.", token.getVal());

	}



	@Test
	public void testNextToken_ThrowsExceptionOnInvalidCharacter() throws Exception {
		File file = new File("src/test/data/testScanner/test2.txt");
		Scanner scanner = new Scanner(file.getAbsolutePath());
		scanner.nextToken(); // legge il carattere $
	}


}
