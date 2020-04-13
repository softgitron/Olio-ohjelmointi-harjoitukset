import java.util.ArrayList;

class Bank {

  public static enum AccountType {
    regular,
    credit
  }

  static final String notFound = "No account found with that name.";

  private ArrayList<Account> accounts = new ArrayList<Account>();

  public void createAccount(String accountNumber, Integer balance) {
    NormalAccount account = new NormalAccount(accountNumber, balance);
    accounts.add(account);
  }

  public void createAccount(
    String accountNumber,
    Integer balance,
    Integer limit
  ) {
    CreditAccount account = new CreditAccount(accountNumber, balance, limit);
    accounts.add(account);
  }

  public boolean deleteAccount(String accountNumber) {
    Account account = searchAccount(accountNumber);
    if (account != null) {
      accounts.remove(account);
      return true;
    } else {
      return false;
    }
  }

  public void printAccount(String accountNumber) {
    Account account = searchAccount(accountNumber);
    if (account != null) {
      System.out.println(
        "Account number: " +
        account.accountNumber +
        " Amount of money: " +
        account.getBalance()
      );
    } else {
      System.out.println(notFound);
    }
  }

  public void printAccounts() {
    for (Account account : accounts) {
      if (account instanceof NormalAccount) {
        System.out.println(
          "Account number: " +
          account.accountNumber +
          " Amount of money: " +
          account.getBalance().toString()
        );
      } else if (account instanceof CreditAccount) {
        System.out.println(
          "Account number: " +
          account.accountNumber +
          " Amount of money: " +
          account.getBalance().toString() +
          " Credit limit: " +
          ((CreditAccount) account).getCreditLimit().toString()
        );
      }
    }
  }

  public void deposit(String accountNumber, Integer amount) {
    Account account = searchAccount(accountNumber);
    if (account != null) {
      account.deposit(amount);
    } else {
      System.out.println(notFound);
    }
  }

  public void withdraw(String accountNumber, Integer amount) {
    Account account = searchAccount(accountNumber);
    if (account != null) {
      if (!account.withdraw(amount)) {
        System.out.println("Not enough money!");
      }
    } else {
      System.out.println(notFound);
    }
  }

  public Account searchAccount(String accountNumber) {
    for (Account account : accounts) {
      if (account.accountNumber.equals(accountNumber)) {
        return account;
      }
    }
    return null;
  }
}
