package com.sonar.sslr.test.pml;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.Parser;

public final class PmlParser {
	
	private static final Parser<Grammar> P = PmlParser.create();
	
	private PmlParser() {
	}
	
	public static Parser<Grammar> create() {
		Grammar grammar = PmlGrammar.create();
		Lexer lexer = PmlLexer.create();
		
		Parser<Grammar> myParser = Parser.builder(grammar).withLexer(lexer).build();
		
		return myParser;
	}
	
	public static AstNode parseFile(String filePath) {
	    File file = FileUtils.toFile(PmlParser.class.getResource(filePath));
	    if (file == null || !file.exists()) {
	      throw new AssertionError("The file \"" + filePath + "\" does not exist.");
	    }

	    return P.parse(file);
	  }
	
	public static AstNode parseString(String source) {
	    return P.parse(source);
	  }
}
