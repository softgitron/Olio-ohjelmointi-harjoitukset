import java.util.Scanner;

class Mainclass {

  public static void main(String args[]) {
    Scanner input = new Scanner(System.in);
    String line;
    System.out.print("Give a name to the dog: ");
    line = input.nextLine();

    Dog dog = new Dog(line);

    boolean error;
    do {
      System.out.print("What does a dog say: ");
      line = input.nextLine();
      error = dog.speak(line);
    } while (error);
    input.close();
  }
}
