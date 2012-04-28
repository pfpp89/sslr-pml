/*
 * SonarSource Language Recognizer
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.sonar.sslr.test.lexer;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import com.sonar.sslr.api.Token;

class HasTokensMatcher extends BaseMatcher<List<Token>> {

  private final String[] tokenValues;

  HasTokensMatcher(String... tokenValues) {
    this.tokenValues = tokenValues;
  }

  public boolean matches(Object obj) {
    if ( !(obj instanceof List)) {
      return false;
    }
    List<Token> tokens = (List<Token>) obj;
    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      if ( !token.getValue().equals(tokenValues[i])) {
        return false;
      }
    }
    if (tokenValues.length != tokens.size()) {
      return false;
    }
    return true;
  }

  public void describeTo(Description desc) {
    desc.appendText(tokenValues.length + " tokens(");
    for (int i = 0; i < tokenValues.length; i++) {
      desc.appendText("'" + tokenValues[i] + "'");
      if (i < tokenValues.length - 1) {
        desc.appendText(",");
      }
    }
    desc.appendText(")");
  }
}
