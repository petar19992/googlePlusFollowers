package main;

import org.apache.hadoop.io.Text;

public class TextPair extends Text {
	public static char separator = '\t';

    public TextPair() {
      super();
    }

    public TextPair(String person1, String person2) {
      super(joinPersonsLexicographically(person1, person2));
    }

    public void set(String person1, String person2) {
      super.set(joinPersonsLexicographically(person1, person2));
    }

    public static String joinPersonsLexicographically(String person1,
                                                      String person2) {
      if (person1.compareTo(person2) < 0) {
        return person1 + separator + person2;
      }
      return person2 + separator + person1;
    }
  }
