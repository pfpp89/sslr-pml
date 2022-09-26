package com.sonar.sslr.test.pml;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.IdentifierAndKeywordChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;

public final class PmlLexer {
	
	private PmlLexer() {
		
	}
	
	
	public static enum Literals implements TokenType {

	    INTEGER,
	    DOUBLE;

	    @Override
	    public String getName() {
	      return name();
	    }

	    @Override
	    public String getValue() {
	      return name();
	    }

	    @Override
	    public boolean hasToBeSkippedFromAst(AstNode node) {
	      return false;
	    }
	  }
	  
	public static enum Punctuators implements TokenType {

		GLOBAL("!!"),
		LOCAL("!"),
	    PAREN_L("("),
	    PAREN_R(")"),
	    EQ("EQ"),
	    COMMA(","),
	    ADD("+"),
	    SUB("-"),
	    MUL("*"),
	    DIV("/"),
	    NE("NE"),
	    LT("LT"),
	    LE("LE"),
	    GT("GT"),
	    GE("GE"),
	    ASSIGNMENT("=")
	    
	    ;

	    private final String value;

	    private Punctuators(String value) {
	      this.value = value;
	    }

	    @Override
	    public String getName() {
	      return name();
	    }

	    @Override
	    public String getValue() {
	      return value;
	    }

	    @Override
	    public boolean hasToBeSkippedFromAst(AstNode node) {
	      return false;
	    }
	}
	
	public static enum Keywords implements TokenType {

		REAL("real"),
		DBREF("dbref"),
		ARRAY("array"),
		
		FUNC("define function"),
	    ENDFUNC("endfunction"),
	    VOID("void"),
	    RETURN("return"),
		IS("is");

	    private final String value;

	    private Keywords(String value) {
	      this.value = value;
	    }

	    @Override
	    public String getName() {
	      return name();
	    }

	    @Override
	    public String getValue() {
	      return value;
	    }

	    @Override
	    public boolean hasToBeSkippedFromAst(AstNode node) {
	      return false;
	    }

	    public static String[] keywordValues() {
	      Keywords[] keywordsEnum = Keywords.values();
	      String[] keywords = new String[keywordsEnum.length];
	      for (int i = 0; i < keywords.length; i++) {
	        keywords[i] = keywordsEnum[i].getValue();
	      }
	      return keywords;
	    }

	  }
	
	
	public static Lexer create() {
	    return Lexer.builder()
	        .withFailIfNoChannelToConsumeOneCharacter(false)
	        .withChannel(new IdentifierAndKeywordChannel("[a-zA-Z]([a-zA-Z0-9]*[a-zA-Z0-9])?+", false, Keywords.values()))
	        .withChannel(regexp(Literals.INTEGER, "[0-9]+"))
	        .withChannel(regexp(Literals.DOUBLE,"\\d+\\.?\\d*"))
	        .withChannel(commentRegexp("(?s)(--*.*?$)|(\\$\\(.*\\$\\))"))
	        .withChannel(new PunctuatorChannel(Punctuators.values()))
	        .withChannel(new BlackHoleChannel("[ \t\r\n]+"))
	        .build();
	  }
}

