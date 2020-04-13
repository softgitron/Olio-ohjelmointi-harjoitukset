import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ReadAndWriteIO {

  // https://stackoverflow.com/questions/15667125/read-content-from-files-which-are-inside-zip-file
  // https://docs.oracle.com/javase/8/docs/api/index.html?java/util/zip/ZipFile.html
  public static String readZip(String zipName) {
    String output = "";
    try {
      ZipFile packet = new ZipFile(zipName);
      Enumeration<? extends ZipEntry> files = packet.entries();

      while (files.hasMoreElements()) {
        ZipEntry file = files.nextElement();
        InputStream inStream = packet.getInputStream(file);

        // https://www.tutorialspoint.com/how-to-convert-inputstream-object-to-a-string-in-java
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        BufferedReader inBuffer = new BufferedReader(inStreamReader);
        output += readFileStream(inBuffer);
        inBuffer.close();
      }
      packet.close();
    } catch (IOException e) {
      System.out.println("Problem with the zipfile");
      System.exit(1);
    }
    return output;
  }

  private static String readFileStream(BufferedReader in) {
    String line;
    String lines = "";
    try {
      // Read first line
      lines = in.readLine();
      while ((line = in.readLine()) != null) {
        lines += "\n" + line;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }
}
