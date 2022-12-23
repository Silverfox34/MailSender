package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {
	
	public static void main(String[] args) {
		List<List<String>> records = CSVReader.readCSV("src/test.txt");
		for(List<String> element : records) {
			for(String e : element) {
				System.out.println(e);
			}
		}
	}
	
	protected static List<List<String>> readCSV(String location){
		List<List<String>> records = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(location))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        records.add(Arrays.asList(values));
		    }
		    
		    return records;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
