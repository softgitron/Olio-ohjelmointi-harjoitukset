import java.util.Scanner;

class Mainclass {
  private static String input = "input.txt";
  private static String output = "output.txt";
  private static Integer willBeKept = 30;

  public static void main(String args[]) {
    String lines = ReadAndWriteIO.readFile(input);
    String results = analyze(lines);
    ReadAndWriteIO.writeFile(output, results);
  }

  private static String analyze(String lines) {
    Scanner lineByline = new Scanner(lines);
    String line;
    String results = "";
    while (lineByline.hasNext()) {
      line = lineByline.nextLine();
      if (
        line.length() < willBeKept && line.replaceAll(" ", "").length() != 0
      ) {
        results += line + "\n";
      }
    }
    results = results.substring(0, results.length() - 1);
    lineByline.close();
    return results;
  }
}
