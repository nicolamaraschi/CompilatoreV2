package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import eccezioni.*;
import parser.Parser;
import scanner.Scanner;
import visitor.*;


class TestCodeGeneratorVisitor {
	Scanner s1;
	Scanner s2;
	Scanner s3;


	Parser p1;
	Parser p2;
	Parser p3;


	NodeProgram np;
	@BeforeEach
	void before() throws FileNotFoundException
	{
		s1 = new Scanner("./src/test/data/TestCodeGeneratorVisitor/test1.txt");
		s2 = new Scanner("./src/test/data/TestCodeGeneratorVisitor/test2.txt");
		s3 = new Scanner("./src/test/data/TestCodeGeneratorVisitor/test3.txt");

		p1 = new Parser(s1);
		p2 = new Parser(s2);
		p3 = new Parser(s3);


	}

	@Test
	void test_Codice1() {
		try {
			np = p1.parse();
			np.accept(new TypeCheking());
			np.accept(new CodeGenerator());
			System.out.println(np.getCodice());
			assertEquals(np.getCodice(),"10 2 / 0 k 2 + 0 k");
		} catch ( SintatticaExeption e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void test_Codice2() {
		try {
			np = p2.parse();
			np.accept(new TypeCheking());
			np.accept(new CodeGenerator());
			System.out.println(np.getCodice());
			assertEquals(np.getCodice(),"10 12 - 0 k 10 2.5 5 k / 0 k 4 5 k - 0 k 10 2 / 0 k 4 - 0 k");

		} catch ( SintatticaExeption e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void test_Codice3() {
		try {
			np =p3.parse();
			np.accept(new TypeCheking());
			np.accept(new CodeGenerator());
			System.out.println(np.getCodice());
			assertEquals(np.getCodice(),"10 10.5 5 k * 0 k 2 5 k - 0 k");

		} catch ( SintatticaExeption e) {
			e.printStackTrace();
		}
		
	}
	

}
