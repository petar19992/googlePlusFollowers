package calc;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CalcReducer extends Reducer<Text, Text, Text, Text>
{

	Text t=new Text();
	/**
	 * Mi ovde kao ulaz dobijemo:
	 * Kljuc- to je par prijatelja tipa : "Petar Milos"
	 * Value je niz, 1 ako su prijatelji, i 2 ako imaju zajednickog prijatelja
	 * */
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException
	{
		StringBuilder s=new StringBuilder();
		for(Text hops : values)
		{
			s.append(hops.toString());
			s.append(" ");
			
		}
		t.set(s.toString());
		context.write(key, t);
	}
}

		