/* Generated By:JavaCC: Do not edit this line. RSQLParserConstants.java */
package io.github.suxil.rsql.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
interface RSQLParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int ESCAPED_CHAR = 5;
  /** RegularExpression Id. */
  int UNRESERVED_STR = 6;
  /** RegularExpression Id. */
  int SINGLE_QUOTED_STR = 7;
  /** RegularExpression Id. */
  int DOUBLE_QUOTED_STR = 8;
  /** RegularExpression Id. */
  int AND = 9;
  /** RegularExpression Id. */
  int OR = 10;
  /** RegularExpression Id. */
  int LPAREN = 11;
  /** RegularExpression Id. */
  int RPAREN = 12;
  /** RegularExpression Id. */
  int CONDITION = 13;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "<ESCAPED_CHAR>",
    "<UNRESERVED_STR>",
    "<SINGLE_QUOTED_STR>",
    "<DOUBLE_QUOTED_STR>",
    "<AND>",
    "<OR>",
    "\"(\"",
    "\")\"",
    "<CONDITION>",
  };

}
