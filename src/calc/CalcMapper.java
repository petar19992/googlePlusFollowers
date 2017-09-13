package calc;

import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

	public class CalcMapper extends Mapper<LongWritable, Text, Text, Text>
	{

		Text newKey=new Text();
		Text newValue=new Text();
		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			{
				InputSplit is = context.getInputSplit();
				Method method;
				try
				{
					method = is.getClass().getMethod("getInputSplit");
					method.setAccessible(true);
					FileSplit fileSplit = (FileSplit) method.invoke(is);
					String currentFileName = fileSplit.getPath().getName();
							
//					String fileName = ((FileSplit) context.getInputSplit()).getPath().getName();
					newKey.set(currentFileName.split(".f")[0]);
					newValue.set(key.toString());
					context.write(newKey, value);
				} catch (NoSuchMethodException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
