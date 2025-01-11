/* Exercise 4.4
Design a data representation for this problem:
Develop a “bank account” program. The program keeps
track of the balances in a person’s bank accounts. Each account
has an id number and a customer’s name. There are three kinds
of accounts: a checking account, a savings account, and a certificate of deposit (CD).
Checking account information also includes the minimum balance. Savings account includes the interest rate.
A CD specifies the interest rate and the maturity date.
Naturally, all three types come with a current balance. */

interface IAccount{
}

class Checking implements IAccount{
    int id;
    String customerName;
    double balance;
    double minimumBalance;

    Checking(int id, String customerName, double balance, double minimumBalance){
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
        this.minimumBalance = minimumBalance;
    }
}

class Saving implements IAccount{
    int id;
    String customerName;
    double balance;
    double interestRate;

    Saving(int id, String customerName, double balance, double interestRate){
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
        this.interestRate = interestRate;
    }
}

class CD implements IAccount {
    int id;
    String customerName;
    double balance;
    double interestRate;
    String maturityDate;

    CD(int id, String customerName, double balance, double interestRate, String maturityDate) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
    }
}

class ExamplesAccount{
    IAccount checking = new Checking(1729, "Earl Gray", 1250, 500);
    IAccount saving = new CD(4104, "Ima Flatt", 10123, 0.04, "2005-06-01");
    IAccount cd = new Saving(2992, "Annie Proulx", 800, 3.5);
}