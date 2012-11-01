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
package org.sonar.sslr.tests;

import com.sonar.sslr.api.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.sslr.internal.matchers.GrammarElementMatcher;

public class RuleAssertTest {

  @org.junit.Rule
  public ExpectedException thrown = ExpectedException.none();

  private Rule rule;

  @Before
  public void setUp() {
    rule = new GrammarElementMatcher("ruleName").is("foo");
  }

  @Test
  public void ok() {
    new RuleAssert(rule)
        .matches("foo")
        .notMatches("bar");
  }

  @Test
  public void test_matches_failure() {
    thrown.expect(ParsingResultComparisonFailure.class);
    thrown.expectMessage("Rule 'ruleName' should match:\nbar");
    new RuleAssert(rule)
        .matches("bar");
  }

  @Test
  public void test_notMatches_failure() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("Rule 'ruleName' should not match:\nfoo");
    new RuleAssert(rule)
        .notMatches("foo");
  }

  @Test
  public void should_not_accept_null() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("expecting actual value not to be null");
    new RuleAssert((Rule) null).matches("");
  }

  @Test
  public void should_not_allow_prefix_match() {
    new RuleAssert(rule)
        .notMatches("foo bar");
  }

}
