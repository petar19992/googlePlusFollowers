package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import calc.CalcMapper;
import calc.CalcReducer;

public class Main
{

	public static void main(String[] args)
	{
		// BasicConfigurator.configure();
		String inputFile = args[0];
		String calcOutputDir = args[1];
		String sortOutputDir = args[2];

		File folder = new File("input/");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> names = new ArrayList<>();
		ArrayList<String> paths = new ArrayList<>();
		String NAME = "";

		String[] files = new String[listOfFiles.length];
		for (File file : listOfFiles)
		{
			if (file.isFile())
			{
				NAME += file.getName().split(".f")[0] + "";
				names.add(file.getName().split(".f")[0]);
				paths.add(file.getName());
				System.out.println(file.getName());
			}
		}

		try
		{
			 runCalcJob(listOfFiles, calcOutputDir);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.out.println("GRESKA 2");
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

	public static boolean runCalcJob(File[] listOfFiles, String output) throws Exception
	{
		Configuration conf = new Configuration();
		BasicConfigurator.configure();
		Job job = new Job(conf);
		job.setJarByClass(Main.class);
		job.setMapperClass(CalcMapper.class);
		job.setReducerClass(CalcReducer.class);

		job.setInputFormatClass(KeyValueTextInputFormat.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		Path outputPath = new Path(output);

		for (File file : listOfFiles)
		{
			MultipleInputs.addInputPath(job, new Path(file.getPath()), TextInputFormat.class, CalcMapper.class);
		}

		// FileInputFormat.setInputPaths(job, input);
		FileOutputFormat.setOutputPath(job, outputPath);

		outputPath.getFileSystem(conf).delete(outputPath, true);

		// System.exit(job.waitForCompletion(true) ? 0 : 1);
		return job.waitForCompletion(true);
	}
}
