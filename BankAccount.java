import java.util.Scanner;

public class BankAccount {
    private double balance;
    private final String pin;

    public BankAccount(double initialBalance, String pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited " + amount + ", new balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " attempted to deposit a non-positive amount: " + amount);
        }
    }

    public synchronized void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ", new balance: " + balance);
        } else if (amount > 0) {
            System.out.println(Thread.currentThread().getName() + " attempted to withdraw " + amount + " but only " + balance + " available.");
        } else {
            System.out.println(Thread.currentThread().getName() + " attempted to withdraw a non-positive amount: " + amount);
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    public void showBankDetails() {
        System.out.println("Bank Name: Example Bank");
        System.out.println("Branch: Main Branch");
        System.out.println("Account Type: Savings");
        System.out.println("Account Balance: " + getBalance());
    }

    public boolean authenticate(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(50000, "252223");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter PIN: ");
        String enteredPin = scanner.nextLine();

        if (account.authenticate(enteredPin)) {
            boolean loggedIn = true;
            while (loggedIn) {
                System.out.println("Select an option: ");
                System.out.println("1. Withdraw");
                System.out.println("2. Check Balance");
                System.out.println("3. Bank Details");
                System.out.println("4. Logout");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter amount to withdraw: ");
                        double amount = scanner.nextDouble();
                        account.withdraw(amount);
                        break;
                    case 2:
                        System.out.println("Current balance: " + account.getBalance());
                        break;
                    case 3:
                        account.showBankDetails();
                        break;
                    case 4:
                        loggedIn = false;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Invalid PIN. Access denied.");
        }

        scanner.close();
    }
}
