import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InputOutput {

	private BufferedReader inputFile;
	private BufferedWriter outputFile;
	
	public InputOutput(String[] filePaths) {
		
		try {
			inputFile = new BufferedReader(new FileReader(filePaths[0]));
			outputFile = new BufferedWriter(new FileWriter(filePaths[1]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Reads the next line from input file and returns it
	public String readInputLine() {
		
		try {
			return inputFile.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
	// Calls the toString method of the object and prints it to the file
	public void write(Object toPrint) {
		
		try {
			outputFile.write(toPrint.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFiles() {
		
		try {
			inputFile.close();
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
