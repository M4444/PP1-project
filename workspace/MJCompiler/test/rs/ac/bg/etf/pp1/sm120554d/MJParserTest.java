package rs.ac.bg.etf.pp1.sm120554d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.sm120554d.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

public class MJParserTest {
	
	// logger
	/*static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}*/
	
	private static String[] test= {
								"test/primer_program.mj",		// 0
								"test/program.mj",				// 1
								"test/test_count.mj",			// 2 CNT
								"test/test_error_recovery.mj",	// 3 ERR
								"test/test_print.mj"			// 4 PRINT
							};
	
	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			//File sourceCode = new File(test[0]);
			File sourceCode = new File(args[4]);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        if (!p.errorDetected) 
	    	{
	    		File objFile = new File("test/obj/program.obj");
	    		//log.info("Generating bytecode file: " + objFile.getAbsolutePath());
	    		if (objFile.exists())
	    			objFile.delete();
	    		Code.write(new FileOutputStream(objFile));
	    		
	    		p.log.info("Parsiranje uspesno zavrseno!");
	    	}
	    	else 
	    	{
	    		p.log.error("Parsiranje NIJE uspesno zavrseno!");
	    	}
	        
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
	}

}
