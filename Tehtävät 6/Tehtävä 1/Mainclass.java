import java.util.Scanner;

class Mainclass {
  private enum MenuType {
    mainMenu
  }

  private enum TransactionDirection {
    deposit,
    withdraw
  }

  private Bank bank = new Bank();
  
  private static Scanner input = new Scanner(System.in);
  
  public static void main(String args[]) {
    mainMenu();
    input.close();
  }

  private static void printMenu(MenuType type) {
    switch (type) {
      case mainMenu:
        System.out.print(
          "\n*** BANK SYSTEM ***\n" + 
          "1) Add a regular account\n" +
          "2) Add a credit account\n" +
          "3) Deposit money\n" +
          "4) Withdraw money\n" +
          "5) Remove an account\n" +
          "6) Print account information\n" +
          "7) Print all accounts\n" +
          "0) Quit\n"
        );
        break;
    }
    System.out.print("Your choice: ");
  }

  private static void mainMenu() {
    String choice;
    while (true) {
      printMenu(MenuType.mainMenu);
      choice = input.nextLine();
      if (choice.length() == 1) {
        choice = choice.substring(0, 1);
      } else {
        choice = "E";
      }

      switch (choice) {
        case "1":
          addAccount(Bank.AccountType.regular);
          break;
        case "2":
          addAccount(Bank.AccountType.credit);
          break;
        case "3":
          handleMoney(TransactionDirection.deposit);
          break;
        case "4":
          handleMoney(TransactionDirection.withdraw);
          break;
        case "5":
          removeAccount();
          break;
        case "6":
          printAccountInformation();
          break;
        case "7":
          printAllAccounts();
          break;
        case "0":
          return;
        default:
          System.out.println("Invalid choice.");
          break;
      }
    }
  }

  private static void addAccount(Bank.AccountType type) {
    String accountNumber;
    Integer moneyToDeposit;
    Integer creditLimit = 0;

    System.out.print("Give an account number: ");
    accountNumber = input.nextLine();
    moneyToDeposit = getInteger("Amount of money to deposit: ");
    if (type == Bank.AccountType.credit) {
      creditLimit = getInteger("Give a credit limit: ");
    }

    System.out.println("Account number: " + accountNumber);
    System.out.println("Amount of money: " + moneyToDeposit.toString());
    if (type == Bank.AccountType.credit) {
      System.out.println("Credit: " + creditLimit.toString());
    }
  }

  private static void handleMoney(TransactionDirection direction) {
    String accountNumber;
    Integer money = 0;

    System.out.print("Give an account number: ");
    accountNumber = input.nextLine();
    if (direction == TransactionDirection.deposit) {
      money = getInteger("Amount of money to deposit: ");
    } else if (direction == TransactionDirection.withdraw) {
      money = getInteger("Amount of money to withdraw: ");
    }

    System.out.println("Account number: " + accountNumber);
    System.out.println("Amount of money: " + money.toString());
  }

  private static void removeAccount() {
    String accountNumber;

    System.out.print("Give the account number of the account to delete: ");
    accountNumber = input.nextLine();

    System.out.println("Account number: " + accountNumber);
  }

  private static void printAccountInformation() {
    String accountNumber;
    System.out.print("Give the account number of the account to print information from: ");
    accountNumber = input.nextLine();

    System.out.println("Account number: " + accountNumber);
  }

  private static void printAllAccounts() {
    System.out.println("Prints every account");
  }

  private static Integer getInteger(String hint) {
    while (true) {
      System.out.print(hint);
      String checkLine = input.nextLine();
      try {
        return Integer.parseInt(checkLine);
      } catch (Exception e) {
        System.out.println("Should have integer.");
      }
    }
  }
}
