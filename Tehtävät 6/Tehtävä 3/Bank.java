import java.util.ArrayList;

class Bank {

  public static enum AccountType {
    regular,
    credit
  }

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
      System.out.println("No account found with that name.");
    }
  }

  public Account searchAccount(String accountNumber) {
    for (Account account : accounts) {
      if (account.accountNumber == accountNumber) {
        return account;
      }
    }
    return null;
  }
}

class Account {
  public String accountNumber;
  private Integer balance;

  Account(String newAccountNumber, Integer initialBalance) {
    accountNumber = newAccountNumber;
    balance = initialBalance;
  }

  public Integer getBalance() {
    return balance;
  }
}

class NormalAccount extends Account {

  NormalAccount(String newAccountNumber, Integer initialBalance) {
    super(newAccountNumber, initialBalance);
  }
}

class CreditAccount extends Account {
  private Integer limit;

  CreditAccount(
    String newAccountNumber,
    Integer initialBalance,
    Integer newLimit
  ) {
    super(newAccountNumber, initialBalance);
    limit = newLimit;
  }
}
