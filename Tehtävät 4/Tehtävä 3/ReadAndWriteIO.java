import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadAndWriteIO {

  public static void readAndWrite(String input, String output) {
    String lines = readFile(input);
    writeFile(output, lines);
  }

  public static String readFile(String fileName) {
    BufferedReader in;
    try {
      in = new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      System.out.println(System.getProperty("user.dir"));
      return "";
    }
    String line;
    String lines = "";
    try {
      // Read first line
      lines = in.readLine();
      while ((line = in.readLine()) != null) {
        lines += "\n" + line;
      }
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }

  public static void writeFile(String fileName, String lines) {
    BufferedWriter out;
    try {
      out = new BufferedWriter(new FileWriter(fileName));
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    try {
      out.write(lines);
      out.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
