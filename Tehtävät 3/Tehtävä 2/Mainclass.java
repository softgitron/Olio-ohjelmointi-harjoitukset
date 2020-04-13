import java.util.Scanner;

class Mainclass {
  private static BottleDispenser dispenser = new BottleDispenser(0f);
  private static Scanner input = new Scanner(System.in);

  public static void main(String args[]) {
    initBottles();
    menu();
    input.close();
  }

  private static void initBottles() {
    Bottle pepsi = new Bottle("Pepsi Max", "Pepsi", 0.3, 0.5, 1.8);
    Bottle pepsiBig = new Bottle("Pepsi Max", "Pepsi", 0.3, 1.5, 2.2);
    Bottle cola = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.1, 0.5, 2.0);
    Bottle colaBig = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 1.5, 2.5);
    Bottle fanta = new Bottle("Fanta Zero", "Fanta", 0.7, 0.5, 1.95);
    dispenser.addBottle(pepsi);
    dispenser.addBottle(pepsiBig);
    dispenser.addBottle(cola);
    dispenser.addBottle(colaBig);
    dispenser.addBottle(fanta);
  }

  private static void menu() {
    int choice = 1;
    while (choice != 0) {
      System.out.print(
        "\n*** BOTTLE DISPENSER ***\n" +
        "1) Add money to the machine\n" +
        "2) Buy a bottle\n" +
        "3) Take money out\n" +
        "4) List bottles in the dispenser\n" +
        "0) End\n"
      );
      System.out.print("Your choice: ");
      if (input.hasNextInt()) {
        choice = input.nextInt();
      } else {
        input.nextLine();
        System.out.println("Bad choice!\n");
        continue;
      }
      switch (choice) {
        case 1:
          dispenser.addMoney();
          break;
        case 2:
          handlePurchase(dispenser);
          break;
        case 3:
          dispenser.returnMoney();
          break;
        case 4:
          dispenser.listBottles();
          break;
      }
    }
  }

  private static void handlePurchase(BottleDispenser dispenser) {
    int choice = 1;
    while (true) {
      dispenser.listBottles();
      System.out.print("Your choice: ");
      if (input.hasNextInt()) {
        choice = input.nextInt();
        if (choice < 1) {
          System.out.println("Bad choice!\n");
          continue;
        }
        dispenser.buyBottle(choice);
        break;
      } else {
        input.nextLine();
        System.out.println("Bad choice!\n");
        continue;
      }
    }
  }
}
