import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Transaction_Handling {
    private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String username = "root";
    private static final String password = "Enter your mysql password";

    public static void main(String[] args) {
        String withdrawl = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String deposit = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        Scanner sc = new Scanner(System.in);

        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connection Successfull!!");
            con.setAutoCommit(false);


            try{
                System.out.print("Enter amount to transefer ");
                Double amount = sc.nextDouble();
                System.out.print("Enter your account number: ");
                sc.nextLine();
                String withdrawlAccount = sc.nextLine();
                System.out.print("Enter Deposit account number: ");
                String depositAccount = sc.nextLine();

                PreparedStatement withdrawlStmt = con.prepareStatement(withdrawl);
                PreparedStatement depositStmt = con.prepareStatement(deposit);

                withdrawlStmt.setDouble(1, amount);
                withdrawlStmt.setString(2, withdrawlAccount);

                depositStmt.setDouble(1, amount);
                depositStmt.setString(2, depositAccount);

                int withdrawlAffected = withdrawlStmt.executeUpdate();
                int depositAffected = depositStmt.executeUpdate();

                if(withdrawlAffected > 0 && depositAffected > 0){
                    System.out.println("Transaction Successfull!!");
                    con.commit();
                }
                else {
                    System.out.println("Transaction Failed!!");
                    con.rollback();
                }


            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

            con.close();
            System.out.println("Connection close Sccessfull");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
