package test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import eccezioni.SintatticaExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ast.*;
import parser.Parser;
import scanner.Scanner;

import visitor.*;

class TestTypeCheckingVisitor {

	Scanner s1;
	Scanner s2;
	Scanner s3;
	Scanner s4;
	Scanner s5;
	Scanner s6;
	Scanner s7;
	Scanner s8;


	Parser p1;
	Parser p2;
	Parser p3;
	Parser p4;
	Parser p5;
	Parser p6;
	Parser p7;
	Parser p8;

	TypeCheking tcv;
	NodeProgram np;

	@BeforeEach
	void before() throws FileNotFoundException
	{
		s1 = new Scanner("./src/test/data/TestTypeCheck/1_dicRipetute.txt");
		s2 = new Scanner("./src/test/data/TestTypeCheck/3_IdNotDeclare.txt");
		s3 = new Scanner("./src/test/data/TestTypeCheck/2_fileCorrect.txt");
		s4 = new Scanner("./src/test/data/TestTypeCheck/errorAssignConvert.txt");
		s5 = new Scanner("./src/test/data/TestTypeCheck/errorOp.txt");
		s6 = new Scanner("./src/test/data/TestTypeCheck/fileCorrect2.txt");
		s7 = new Scanner("./src/test/data/TestTypeCheck/testGenerale.txt");
		s8 = new Scanner("./src/test/data/TestTypeCheck/testGenerale2.txt");


		p1 = new Parser(s1);
		p2 = new Parser(s2);
		p3 = new Parser(s3);
		p4 = new Parser(s4);
		p5 = new Parser(s5);
		p6 = new Parser(s6);
		p7 = new Parser(s7);
		p8 = new Parser(s8);

		tcv = new TypeCheking();
	}
	@Test
	void test_dicRipetute() {
		try {
			np = p1.parse();
			np.accept(new TypeCheking());
			assertEquals(np.getDecSts().get(0).getResType(),TypeDescriptor.Float);
			assertEquals(np.getDecSts().get(1).getResType(),TypeDescriptor.Int);
		} catch ( SintatticaExeption e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void test_3_IdNotDeclare() {
		try {
			np = p2.parse();
			np.accept(new TypeCheking());
			assertEquals(np.getDecSts().get(0).getResType(),TypeDescriptor.Int);
			assertEquals(np.getDecSts().get(1).getResType(),TypeDescriptor.Int);
			assertEquals(np.getDecSts().get(2).getResType(),TypeDescriptor.Int);
			assertEquals(np.getDecSts().get(3).getResType(),TypeDescriptor.Float);
			assertEquals(np.getResType(),TypeDescriptor.Void);
		} catch ( SintatticaExeption e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	void test_2_fileCorrect() {
		try {
			np = p3.parse();
			np.accept(new TypeCheking());
			assertEquals(np.getDecSts().get(0).getResType(),TypeDescriptor.Float);
			assertEquals(np.getDecSts().get(1).getResType(),TypeDescriptor.Int);
			assertEquals(np.getResType(),TypeDescriptor.Void);
		} catch ( SintatticaExeption e) {
			e.printStackTrace();
		}
	}


	// QUESTI NON LI HO TOCCATI

}
