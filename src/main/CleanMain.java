package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

public class CleanMain
{

	public static void main(String[] args)
	{
		//Ova klasa sluzi da se iz fajlova izbace mrtvi cvorovi, to jest id-evi korisnika koji nemaju svoj zaseban file sa zajednickim prijateljima
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
					Configuration conf = new Configuration();
					Path outputPath = new Path("output/input2.txt");
					outputPath.getFileSystem(conf).delete(outputPath, true);
					PrintWriter writer = new PrintWriter("output/input2.txt", "UTF-8");

					for (int i = 0; i < paths.size(); i++)
					{
						boolean firstWrite = true;
						BufferedReader br = new BufferedReader(new FileReader("input/" + paths.get(i)));
						try
						{
							StringBuilder sb = new StringBuilder();
							String line = br.readLine();
							boolean isNull = true;
							while (line != null)
							{
								if (NAME.contains(line))
								{
									if (firstWrite)
									{
										writer.print(paths.get(i).split(".f")[0] + "\t");
										firstWrite = false;
									}
									isNull = false;
									writer.print(line);
									line = br.readLine();
									if (line != null)
									{
										writer.print(" ");
									}
								} else
								{
									line = br.readLine();
								}
							}
							if (!isNull)
								writer.println();
						} catch (Exception ex)
						{
						} finally
						{
							br.close();
						}
					}
					writer.close();
				} catch (Exception e)
				{
					System.out.println("GRESKA 2");
					e.printStackTrace();
				}
	}

}
