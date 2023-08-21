package test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;


// Resto del codice del test

import org.junit.jupiter.api.Test;
import ast.NodeProgram;
import eccezioni.*;
import parser.Parser;
import scanner.Scanner;

class TestParser {
	Scanner sc2;
	Scanner sc3;
	Scanner sc5;
	Scanner sc6;
	Scanner sc7;
	Scanner sc8;
	Scanner sc9;
	Scanner sc10;

	{
		try {
			sc2 = new Scanner("./src/test/data/testParser/testParserCorretto1.txt");
			sc3 = new Scanner("./src/test/data/testParser/testParserCorretto2.txt");
			sc5 = new Scanner("./src/test/data/testParser/testParserEcc_0.txt");
			sc6 = new Scanner("./src/test/data/testParser/testParserEcc_1.txt");
			sc7 = new Scanner("./src/test/data/testParser/testParserEcc_2.txt");
			sc8 = new Scanner("./src/test/data/testParser/testParserEcc_3.txt");
			sc9 = new Scanner("./src/test/data/testParser/testParserEcc_4.txt");
			sc10 = new Scanner("./src/test/data/testParser/testParserEcc_5.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	Parser p2 = new Parser(sc2);
	Parser p3 = new Parser(sc3);
	Parser p5 = new Parser(sc5);
	Parser p6 = new Parser(sc6);
	Parser p7 = new Parser(sc7);
	Parser p8 = new Parser(sc8);
	Parser p9 = new Parser(sc9);
	Parser p10 = new Parser(sc10);

	
	@Test
	void testParserCorretto1() {
		try {
			NodeProgram node = p2.parse();
			System.out.println(node.toStringFormatted());
			assertEquals(node.toString(),"NodeProgram [decSts=[" +
					"NodeBinOp [op=MINUS, left=NodeBinOp [op=PLUS, left=NodeBinOp [op=PLUS, left=NodeCost [value=10, type=INTy], right=NodeCost [value=10, type=INTy]], right=NodeCost [value=10, type=INTy]], right=NodeCost [value=10, type=INTy]]," +
					" NodeBinOp [op=PLUS, left=NodeCost [value=20, type=INTy], right=NodeCost [value=20, type=INTy]]," +
					" NodeBinOp [op=MINUS, left=NodeCost [value=10.666, type=FLOATy], right=NodeCost [value=2, type=INTy]]," +
					" NodeBinOp [op=DIV, left=NodeCost [value=10.5, type=FLOATy], right=NodeCost [value=3.333, type=FLOATy]]," +
					" NodeBinOp [op=MINUS, left=NodeCost [value=10., type=FLOATy], right=NodeCost [value=10.2, type=FLOATy]]," +
					" NodeBinOp [op=MINUS, left=NodeCost [value=0.5, type=FLOATy], right=NodeCost [value=1, type=INTy]]," +
					" NodeBinOp [op=MINUS, left=NodeCost [value=20, type=INTy], right=NodeBinOp [op=DIV, left=NodeBinOp [op=TIMES, left=NodeCost [value=20, type=INTy], right=NodeCost [value=20, type=INTy]], right=NodeCost [value=20, type=INTy]]]," +
					" NodeBinOp [op=PLUS, left=NodeCost [value=10, type=INTy], right=NodeCost [value=10, type=INTy]]]]");

		}
		catch (SintatticaExeption e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testParserCorretto2() {
		try {
			NodeProgram node;
			node = p3.parse();
			System.out.println(node.toStringFormatted());
			assertEquals(node.toString(),"NodeProgram [decSts=[" +
					"NodeBinOp [op=MINUS, left=NodeBinOp [op=DIV, left=NodeCost [value=10, type=INTy], right=NodeCost [value=2, type=INTy]], right=NodeCost [value=10.5, type=FLOATy]]]]"
			);
		}
		catch (SintatticaExeption e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void testParserEcc_0() {
		NodeProgram node;
		try {
			node = p5.parse();
			System.out.println(node.toStringFormatted());
			assertEquals(node.toString(),"NodeProgram [decSts=[" +
					"NodeBinOp [op=PLUS, left=NodeCost [value=10, type=INTy], right=NodeCost [value=3, type=INTy]]]]"
			);
		} catch (SintatticaExeption e) {
			e.printStackTrace();
		}
		//Throwable e = assertThrows(SintatticaExeption.class,() -> p5.parse());
			//assertEquals(e.getMessage(),"ErroreSintattico:Il token SEMI alla riga 1 non è corretto\n");
	}
	
	@Test
	void testParserEcc_1() {
			Throwable e = assertThrows(SintatticaExeption.class,() -> p6.parse());
			assertEquals(e.getMessage(),"Errore lessicale\n");
	}
	
	@Test
	void testParserEcc_2()  {
			Throwable e = assertThrows(SintatticaExeption.class,() -> p7.parse());
			assertEquals(e.getMessage(),"ErroreSintattico:Il token INT alla riga 1 non è corretto\n");
	}
	
	@Test
	void testParserEcc_3() {
			Throwable e = assertThrows(SintatticaExeption.class,() -> p8.parse());
			assertEquals(e.getMessage(),"Errore il token trovato è di tipo PLUS ma il tipo che ci si aspettava era ASSIGN\n");
	}
	
	@Test
	void testParserEcc_4() {
			Throwable e = assertThrows(SintatticaExeption.class,() -> p9.parse());
			assertEquals(e.getMessage(),"Errore il token trovato è di tipo INT ma il tipo che ci si aspettava era ID\n");
	}
	
	@Test
	void testParserEcc_5() {
			Throwable e = assertThrows(SintatticaExeption.class,() -> p10.parse());
			assertEquals(e.getMessage(),"Errore il token trovato è di tipo INT ma il tipo che ci si aspettava era ID\n");
	}




}
