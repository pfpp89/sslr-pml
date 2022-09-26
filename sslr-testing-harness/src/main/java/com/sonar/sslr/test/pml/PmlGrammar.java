package com.sonar.sslr.test.pml;

import com.sonar.sslr.api.Grammar;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;
import static com.sonar.sslr.api.GenericTokenType.EOL;
import static com.sonar.sslr.api.GenericTokenType.EOF;
import static com.sonar.sslr.test.pml.PmlLexer.Keywords.REAL;
import static com.sonar.sslr.test.pml.PmlLexer.Keywords.DBREF;
import static com.sonar.sslr.test.pml.PmlLexer.Keywords.ARRAY;

import static com.sonar.sslr.test.pml.PmlLexer.Literals.INTEGER;
import static com.sonar.sslr.test.pml.PmlLexer.Literals.DOUBLE;

import static com.sonar.sslr.test.pml.PmlLexer.Punctuators.GLOBAL;
import static com.sonar.sslr.test.pml.PmlLexer.Punctuators.LOCAL;
import static com.sonar.sslr.test.pml.PmlLexer.Punctuators.ASSIGNMENT;

public enum PmlGrammar implements GrammarRuleKey {

	
	BIN_TYPE,
	BIN_VARIABLE_DEFINITION,
	
	COMPILATION_UNIT,
	
	DEFINITION,
	VARIABLE_DEFINITION;

	
	public static Grammar create() {
		LexerfulGrammarBuilder b = LexerfulGrammarBuilder.create();
		
		b.rule(BIN_TYPE).is(b.firstOf(
				REAL,
				DBREF,
				ARRAY));
		
		b.rule(BIN_VARIABLE_DEFINITION).is(IDENTIFIER);
		
		b.rule(VARIABLE_DEFINITION).is(
	    		BIN_TYPE, 
	    		b.isOneOfThem(LOCAL, GLOBAL), 
	    		BIN_VARIABLE_DEFINITION,b.optional(
	    				ASSIGNMENT,
	    				b.isOneOfThem(INTEGER, DOUBLE)));
		
	    b.rule(COMPILATION_UNIT).is(b.zeroOrMore(VARIABLE_DEFINITION), EOF);
	    
	    b.setRootRule(COMPILATION_UNIT);
	    
		return b.build();
	}
	
}
