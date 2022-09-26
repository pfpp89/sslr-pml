package com.sonar.sslr.test.pml;

import java.util.Arrays;
import java.util.List;

import org.sonar.colorizer.KeywordsTokenizer;
import org.sonar.colorizer.Tokenizer;

public final class PmlColorizer {
	private PmlColorizer() {
	}
	
	public static List<Tokenizer> getTokenizers() {
	    return Arrays.asList(
	        new KeywordsTokenizer("<span class=\"k\">", "</span>", PmlLexer.Keywords.keywordValues()));
	  }
}
