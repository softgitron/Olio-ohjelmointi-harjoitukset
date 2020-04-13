class Account {
  public String accountNumber;
  protected Integer balance;

  Account(String newAccountNumber, Integer initialBalance) {
    accountNumber = newAccountNumber;
    balance = initialBalance;
  }

  public Integer getBalance() {
    return balance;
  }

  public void deposit(Integer amount) {
    balance += amount;
  }

  public boolean withdraw(Integer amount) {
    if (balance - amount >= 0) {
      balance -= amount;
      return true;
    } else {
      return false;
    }
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

  @Override
  public boolean withdraw(Integer amount) {
    if (balance - amount + limit >= 0) {
      balance -= amount;
      return true;
    } else {
      return false;
    }
  }

  public Integer getCreditLimit() {
    return limit;
  }
}
