package bankingProject;
import java.sql.*;
import java.util.Scanner;

public class BankingApp {

    static Connection con;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Statement stmt = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "system";       // use your actual username
            String password = "prakash@0607"; // use your actual password
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();

            int choice;

            do {
                System.out.println("\n------ IDB Mini Project Banking System ------");
                System.out.println();
                System.out.println("Choose the Operation you want to perform: ");
                System.out.println("1. Show Customer Records");
                System.out.println("2. Add Customer");
                System.out.println("3. Delete Customer");
                System.out.println("4. Update Customer Information");
                System.out.println("5. Show Account Details");
                System.out.println("6. Show Loan Details");
                System.out.println("7. Deposit Money");
                System.out.println("8. Withdraw Money");
                System.out.println("9. Exit");
                System.out.print("Enter your choice (1-9): ");
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {
                    case 1:
                        showCustomers();
                        break;
                    case 2:
                        addCustomer(sc);
                        break;
                    case 3:
                        deleteCustomer(sc);
                        break;
                    case 4:
                    	updateCustomer(sc);
                    	break;
                    case 5:
                        showAccountDetails(sc);
                        break;
                    case 6:
                        showLoanDetails(sc);
                        break;
                    case 7:
                        depositMoney(sc);
                        break;
                    case 8:
                        withdrawMoney(sc);
                        break;

                    // We'll implement 4-8 step by step later
                    case 9:
                        System.out.println("Exiting Program.");
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                }

            } while (choice != 9);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sc.close();
    }

    public static void showCustomers() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Customer");

        System.out.println("\nCustomer Records:");
        System.out.println();
        System.out.printf("%-10s %-20s %-15s %-15s\n", "Cust_No", "Name", "Phone", "City");
        System.out.println("-------------------------------------------------------");
        while (rs.next()) {
            System.out.printf("%-10s %-20s %-15s %-15s\n",
                rs.getString("cust_no"),
                rs.getString("name"),
                rs.getString("phoneno"),
                rs.getString("city")
            );
        }
    }

    public static void addCustomer(Scanner sc) throws SQLException {
        System.out.print("Enter Customer No: ");
        String cust_no = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneno = sc.nextLine();
        System.out.print("Enter City: ");
        String city = sc.nextLine();

        String query = "INSERT INTO Customer VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, cust_no);
        pstmt.setString(2, name);
        pstmt.setString(3, phoneno);
        pstmt.setString(4, city);
        System.out.println();

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Customer added successfully.");
            showCustomers();
        } else {
            System.out.println("Failed to add customer.");
        }
    }

    public static void deleteCustomer(Scanner sc) throws SQLException {
        System.out.print("Enter Customer No to Delete: ");
        String cust_no = sc.nextLine();

        String query = "DELETE FROM Customer WHERE cust_no = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, cust_no);

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Customer deleted successfully.");
            showCustomers();
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    public static void updateCustomer(Scanner sc) throws SQLException {
        System.out.print("Enter Customer No to Update: ");
        String cust_no = sc.nextLine();

        System.out.println("What do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Phone Number");
        System.out.println("3. City");
        System.out.print("Enter choice: ");
        int ch = sc.nextInt();
        sc.nextLine(); // clear buffer

        String column = "";
        switch (ch) {
            case 1:
                column = "name";
                break;
            case 2:
                column = "phoneno";
                break;
            case 3:
                column = "city";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.print("Enter new value: ");
        String newValue = sc.nextLine();

        String query = "UPDATE Customer SET " + column + " = ? WHERE cust_no = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, newValue);
        pstmt.setString(2, cust_no);

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Customer updated successfully.");
            showCustomers();
        } else {
            System.out.println("Customer not found.");
        }
    }
    
    public static void showAccountDetails(Scanner sc) throws SQLException {
        System.out.print("Enter Customer No: ");
        String cust_no = sc.nextLine();

        String query = "SELECT c.cust_no, c.name, a.account_no, a.type, a.balance, " +
                       "b.branch_code, b.branch_name, b.branch_city " +
                       "FROM Customer c " +
                       "JOIN Account a ON c.cust_no = a.cust_no " +
                       "JOIN Branch b ON a.branch_code = b.branch_code " +
                       "WHERE c.cust_no = ?";

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, cust_no);

        ResultSet rs = pstmt.executeQuery();

        boolean hasData = false;
        System.out.println("\nAccount Details:");
        while (rs.next()) {
            hasData = true;
            System.out.printf("Customer No   : %s\n", rs.getString("cust_no"));
            System.out.printf("Customer Name : %s\n", rs.getString("name"));
            System.out.printf("Account No    : %s\n", rs.getString("account_no"));
            System.out.printf("Account Type  : %s\n", rs.getString("type"));
            System.out.printf("Balance       : %.2f\n", rs.getDouble("balance"));
            System.out.printf("Branch Code   : %s\n", rs.getString("branch_code"));
            System.out.printf("Branch Name   : %s\n", rs.getString("branch_name"));
            System.out.printf("Branch City   : %s\n", rs.getString("branch_city"));
            System.out.println("----------------------------");
        }

        if (!hasData) {
            System.out.println("No account found for this customer.");
        }
    }

    public static void showLoanDetails(Scanner sc) throws SQLException {
        System.out.print("Enter Customer No: ");
        String cust_no = sc.nextLine();

        String query = "SELECT c.cust_no, c.name, l.loan_no, l.amount, " +
                       "b.branch_code, b.branch_name, b.branch_city " +
                       "FROM Customer c " +
                       "JOIN Loan l ON c.cust_no = l.cust_no " +
                       "JOIN Branch b ON l.branch_code = b.branch_code " +
                       "WHERE c.cust_no = ?";

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, cust_no);
        ResultSet rs = pstmt.executeQuery();

        boolean hasLoan = false;
        System.out.println("\nLoan Details:");
        while (rs.next()) {
            hasLoan = true;
            System.out.printf("Customer No   : %s\n", rs.getString("cust_no"));
            System.out.printf("Customer Name : %s\n", rs.getString("name"));
            System.out.printf("Loan No       : %s\n", rs.getString("loan_no"));
            System.out.printf("Amount        : %.2f\n", rs.getDouble("amount"));
            System.out.printf("Branch Code   : %s\n", rs.getString("branch_code"));
            System.out.printf("Branch Name   : %s\n", rs.getString("branch_name"));
            System.out.printf("Branch City   : %s\n", rs.getString("branch_city"));
            System.out.println("----------------------------");
        }

        if (!hasLoan) {
            System.out.println("🎉 Congratulations! This customer has no loans.");
        }
    }
    
    public static void depositMoney(Scanner sc) throws SQLException {
        System.out.print("Enter Account No: ");
        String account_no = sc.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // clear buffer

        String query = "UPDATE Account SET balance = balance + ? WHERE account_no = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setDouble(1, amount);
        pstmt.setString(2, account_no);

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Amount deposited successfully.");
        } else {
            System.out.println("❌ Account not found.");
            return;
        }

        // Show updated account details
        showAccountDetailsByAccountNo(account_no);
    }
    
    public static void showAccountDetailsByAccountNo(String account_no) throws SQLException {
        String query = "SELECT c.cust_no, c.name, a.account_no, a.type, a.balance, " +
                       "b.branch_code, b.branch_name, b.branch_city " +
                       "FROM Customer c " +
                       "JOIN Account a ON c.cust_no = a.cust_no " +
                       "JOIN Branch b ON a.branch_code = b.branch_code " +
                       "WHERE a.account_no = ?";

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, account_no);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            System.out.println("\nUpdated Account Details:");
            System.out.printf("Customer No   : %s\n", rs.getString("cust_no"));
            System.out.printf("Customer Name : %s\n", rs.getString("name"));
            System.out.printf("Account No    : %s\n", rs.getString("account_no"));
            System.out.printf("Account Type  : %s\n", rs.getString("type"));
            System.out.printf("Balance       : %.2f\n", rs.getDouble("balance"));
            System.out.printf("Branch Code   : %s\n", rs.getString("branch_code"));
            System.out.printf("Branch Name   : %s\n", rs.getString("branch_name"));
            System.out.printf("Branch City   : %s\n", rs.getString("branch_city"));
            System.out.println("----------------------------");
        } else {
            System.out.println("❌ Account not found.");
        }
    }

    public static void withdrawMoney(Scanner sc) throws SQLException {
        System.out.print("Enter Account No: ");
        String account_no = sc.nextLine();
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // clear buffer

        // First, get current balance
        String checkQuery = "SELECT balance FROM Account WHERE account_no = ?";
        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
        checkStmt.setString(1, account_no);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            double currentBalance = rs.getDouble("balance");

            if (amount > currentBalance) {
                System.out.println("❌ Insufficient balance.");
                return;
            }

            // Withdraw money
            String updateQuery = "UPDATE Account SET balance = balance - ? WHERE account_no = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setDouble(1, amount);
            updateStmt.setString(2, account_no);

            int rows = updateStmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Withdrawal successful.");
                showAccountDetailsByAccountNo(account_no);
            } else {
                System.out.println("❌ Withdrawal failed.");
            }
        } else {
            System.out.println("❌ Account not found.");
        }
    }

}
