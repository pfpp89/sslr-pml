package com.sonar.sslr.test.pml;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.List;

import org.sonar.colorizer.Tokenizer;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.sonar.sslr.toolkit.Toolkit;
import org.sonar.sslr.toolkit.ValidationCallback;

import com.sonar.sslr.impl.Parser;


public class PmlToolkit {
	
	private PmlToolkit() {
		
	}
	
	public static void main(String[] args) {
	    Toolkit toolkit = new Toolkit("SonarSource : PML : Toolkit", new PmlConfigurationModel());
	    toolkit.run();
	  }
	
	static class PmlConfigurationModel extends AbstractConfigurationModel {

		private final ConfigurationProperty charsetProperty = new ConfigurationProperty("Charset", "Charset used when opening files.", "UTF-8", new ValidationCallback() {
			@Override
			public String validate(String newValueCandidate) {
				try {
					Charset.forName(newValueCandidate);
					return "";
					} catch (IllegalCharsetNameException e) {
						return "Illegal charset name: " + newValueCandidate;
					} catch (UnsupportedCharsetException e) {
						return "Unsupported charset: " + newValueCandidate;
					}
				}
			});
		
		@Override
		public List<ConfigurationProperty> getProperties() {
			// TODO Auto-generated method stub
			return Collections.singletonList(charsetProperty);
		}

		@Override
		public Parser doGetParser() {
			updateConfiguration();
			return PmlParser.create();
		}

		@Override
		public List<Tokenizer> doGetTokenizers() {
			// TODO Auto-generated method stub
			return PmlColorizer.getTokenizers();
		}
		
		private static void updateConfiguration() {
		      /* Construct a parser configuration object from the properties */
		    }
		
	}
}
