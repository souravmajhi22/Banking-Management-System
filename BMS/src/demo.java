import java.sql.*;
import java.util.Scanner;
public class demo {
    static final String DB_URL = "jdbc:mysql://localhost:3306/bankdata";
     static final String USER = "root";
     static final String PASS = "";
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Scanner sc = new Scanner(System.in);
    
    try(
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
       )
    {    
      String customer = "CREATE TABLE CUSTOMER " +
              "(CUST_NO VARCHAR(5) CHECK(CUST_NO LIKE 'C____'), " +
                  " NAME VARCHAR(30) not NULL, "+
              " PHONE_NO VARCHAR(11), "+
                  " CITY VARCHAR(20) not NULL, "+
              " PRIMARY KEY ( CUST_NO ))";
      
      String branch = "CREATE TABLE BRANCH "+
                           "(BRANCH_CODE VARCHAR(5) PRIMARY KEY, "+
                       " BRANCH_NAME VARCHAR(20) NOT NULL, "+
                           " BRANCH_CITY VARCHAR(10) CHECK(BRANCH_CITY IN ('DELHI' , 'MUMBAI' ,'KOLKATA', 'CHENNAI')))";
           
              
          String account = "CREATE TABLE ACCOUNT"+
                           "(ACCOUNT_NO VARCHAR(5) PRIMARY KEY CHECK(ACCOUNT_NO LIKE 'A____'), "+
                       " TYPES VARCHAR(2) CHECK(TYPES='SB' OR TYPES = 'FD' OR TYPES = 'CA'), "+
                           " BALANCE INT(10) CHECK(BALANCE<10000000), "+
                       " BRANCH_CODE VARCHAR(20), "+
                           " FOREIGN KEY(BRANCH_CODE) REFERENCES BRANCH(BRANCH_CODE))";
          
          String depositor = "CREATE TABLE DEPOSITOR"+
                    "(CUST_NO VARCHAR(5) REFERENCES CUSTOMER(CUST_NO), "+
                          " ACCOUNT_NO VARCHAR(5) REFERENCES ACCOUNT(ACCOUNT_NO), "+
                    " PRIMARY KEY(CUST_NO,ACCOUNT_NO))";
                          
            String loan = "CREATE TABLE LOAN"+
                    "(LOAN_NO VARCHAR(5) PRIMARY KEY CHECK(LOAN_NO LIKE 'L____'), "+
                       "CUST_NO VARCHAR(5) CHECK(CUST_NO LIKE 'C____'), "+
                     " AMOUNT INT CHECK(AMOUNT>1000), "+
                       " BRANCH_CODE VARCHAR(20) REFERENCES BRANCH(BRANCH_CODE),"+
                     " FOREIGN KEY(CUST_NO) REFERENCES CUSTOMER(CUST_NO))";
            String installment = "CREATE TABLE INSTALLMENT"+
                          "(INST_NO VARCHAR(10) CHECK(INST_NO<=10), "+
                             " LOAN_NO VARCHAR(5) REFERENCES LOAN(LOAN_NO), "+
                          " INST_AMOUNT INT NOT NULL, "+
                             " PRIMARY KEY(INST_NO,LOAN_NO))";
////           if(stmt.executeUpdate(customer)>0)
////             System.out.println("Table customer already exists");
////           else
////             System.out.println("CUSTOMER table is created..");   
////           stmt.executeUpdate(branch);
////             System.out.println("BRANCH table is created..");
//           stmt.executeUpdate(account);
//             System.out.println("ACCOUNT table is created..");
////           stmt.executeUpdate(depositor);
////                System.out.println("DEPOSITOR table is created..");
////             stmt.executeUpdate(loan);
////              System.out.println("LOAN table is created..");  
////             stmt.executeUpdate(installment);
////              System.out.println("INSTALLMENT table is created..");  
              
              int choice,ch;
              
            do {
              System.out.println("\n\n***** Banking Management System*****\n");
              System.out.println("1. Show Customer Records:");
              System.out.println("2. Add Customer Record:");
              System.out.println("3. Delete Customer Record:");
              System.out.println("4. Update Customer Information:");
              System.out.println("5. Show Account Details of a Customer:");
              System.out.println("6. Show Loan Details of a Customer:");
              System.out.println("7. Deposit Money to an Account:");
              System.out.println("8. Withdraw Money from an Account:");
              System.out.println("9. Exit the Program");
              
              System.out.println("Enter your choice(1-9):");
              choice = sc.nextInt();
              
              switch(choice)
              {
                case 1:
                  String query = "SELECT * FROM CUSTOMER";
                  ResultSet rs = stmt.executeQuery(query);
                  System.out.println("*******************CUSTOMER DETAILS*******************\n");
                  System.out.println("CUST_NO\t  "+"NAME\t\t"+"PHONE_NO\t"+"CITY");
                  System.out.println("_______________________________________________________");
                  while(rs.next())
                  {    
                    System.out.println(rs.getString(1)+"\t  "+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
                    System.out.println("-------------------------------------------------------");
                  }
                    break;
                case 2:
                	
                	System.out.println("*******************CUSTOMER DETAILS*******************\n");
                    
                    System.out.println("Enter the customer number(like C1120):");
                    String custno = sc.next();
                    sc.nextLine();                         
                    System.out.println("Enter customer name:");
                    String name = sc.nextLine();
                    
                    System.out.println("Enter customer phone number:");
                    String phone = sc.next();
                    sc.nextLine();
                    System.out.println("Enter the customer city:");
                    String city = sc.nextLine();                                               
                    String query1 = "INSERT INTO CUSTOMER(CUST_NO,NAME,PHONE_NO,CITY)"+"VALUES(?,?,?,?)";
                    PreparedStatement preparedStmt = conn.prepareStatement(query1);
                    preparedStmt.setString (1, custno);
                    preparedStmt.setString (2, name);
                    preparedStmt.setString (3, phone);
                    preparedStmt.setString(4, city);
                    preparedStmt.execute();
                    System.out.println("Data inserted");
                    break;
                	

                case 3:
                    System.out.println("*******************Delete Customer Record:*******************\n");

                      System.out.println("Enter the customer number(like C1120):");
                      String custno1 = sc.next();
                      String query2 ="DELETE FROM CUSTOMER WHERE CUST_NO= ?";
                      PreparedStatement preparedStmt1 = conn.prepareStatement(query2);
                      preparedStmt1.setString (1, custno1);
                      preparedStmt1.execute();
                      System.out.println("Data Deleted");
                      break;
                case 4:
                	System.out.println("*******************Update Customer Information:*******************\n");
                	sc.nextLine();
                	System.out.println("Enter the customer number(like C1120):");
                    String custno2 = sc.nextLine();
                    
                    System.out.println("1: Update name.");
                    System.out.println("2: Update Phoneno.");
                    System.out.println("3: Update city");
                    System.out.println("Enter your choice(1-3):");
                    ch = sc.nextInt();
                    
                    switch(ch)
                    {
                      case 1:
                    	  sc.nextLine();
                    	  System.out.println("Enter the updated name: ");
                    	  String upname=sc.nextLine();
                    	  String query3="UPDATE CUSTOMER SET NAME=? WHERE CUST_NO=?";
                    	  PreparedStatement preparedStmt2 = conn.prepareStatement(query3);
                    	  preparedStmt2.setString (1, upname);
                    	  preparedStmt2.setString (2, custno2);
                    	  preparedStmt2.execute();
                    	  System.out.println("Name updated");
                    	  break;
                      case 2:
                    	  System.out.println("Enter the updated Phoneno: ");
                    	  String upph=sc.next();
                    	  String query4="UPDATE CUSTOMER SET PHONE_NO=? WHERE CUST_NO=?";
                    	  PreparedStatement preparedStmt3 = conn.prepareStatement(query4);
                    	  preparedStmt3.setString (1, upph);
                    	  preparedStmt3.setString (2, custno2);
                    	  preparedStmt3.execute();
                    	  System.out.println("Phoneno updated");
                    	  break;
                      case 3:
                    	  System.out.println("Enter the updated City: ");
                    	  String cty=sc.next();
                    	  String query5="UPDATE CUSTOMER SET CITY=? WHERE CUST_NO=?";
                    	  PreparedStatement preparedStmt4 = conn.prepareStatement(query5);
                    	  preparedStmt4.setString (1, cty);
                    	  preparedStmt4.setString (2, custno2);
                    	  preparedStmt4.execute();
                    	  System.out.println("City updated");
                    	  break;
                     default:
                    	 System.out.println("Invalid case");
                    	 	 
                    }
                    break;
                    
                case 5:                           
                    System.out.println("Enter the customer number(like C1120):");
                    String cust = sc.next();
                                               
                    String query5 = "SELECT * FROM CUSTOMER INNER JOIN DEPOSITOR USING(CUST_NO) INNER JOIN ACCOUNT USING(ACCOUNT_NO) INNER JOIN BRANCH USING(BRANCH_CODE) WHERE CUST_NO='"+cust+"'";                    
                    ResultSet rs5 = stmt.executeQuery(query5);
                    System.out.println("*******************Account Details of a Customer:*******************\n");
                    while(rs5.next())
                    {
                      System.out.println("Account number :"+rs5.getString("ACCOUNT_NO"));
                      System.out.println("Type           :"+rs5.getString("TYPES"));
                      System.out.println("Balance        :"+rs5.getInt("BALANCE"));
                      System.out.println("Branch code    :"+rs5.getString("BRANCH_CODE"));
                      System.out.println("Branch name    :"+rs5.getString("BRANCH_NAME"));
                      System.out.println("Branch city    :"+rs5.getString("BRANCH_CITY"));
                    }
                    break;
                    

                    
                case 6:
                    System.out.println("Enter the customer number:");
                    String cust_no = sc.next();
                    String query6 = "SELECT * FROM LOAN INNER JOIN BRANCH USING(BRANCH_CODE) WHERE CUST_NO=cust_no";
                    ResultSet rs6 = stmt.executeQuery(query6);
                 System.out.println("*******************LOAN DETAILS*******************\n");
                 while(rs6.next())
                 {
                   System.out.println("Loan number    :"+rs6.getString("LOAN_NO"));
                   System.out.println("Customer number:"+rs6.getString("CUST_NO"));
                   System.out.println("Loan amount    :"+rs6.getInt("AMOUNT"));
                   System.out.println("Branch code    :"+rs6.getString("BRANCH_CODE"));
                   System.out.println("Branch name    :"+rs6.getString("BRANCH_NAME"));
                   System.out.println("Branch city    :"+rs6.getString("BRANCH_CITY"));
                 }
                 break;
                 
                case 7:
                    System.out.println("Enter the account number of customer:");
                    String account_no = sc.next();
                    System.out.println("Enter the amount to be deposited");
                    String amount = sc.next();
                    String query7="UPDATE ACCOUNT SET BALANCE=BALANCE+? WHERE ACCOUNT_NO=?";
                      PreparedStatement preparedStmt7 = conn.prepareStatement(query7);
                      preparedStmt7.setString (1, amount);
                      preparedStmt7.setString (2, account_no);
                      preparedStmt7.execute();
                      System.out.println("Amount Deposited");
                      break;
                case 8:
                    System.out.println("Enter the account number of customer:");
                    String account_no8 = sc.next();
                    System.out.println("Enter the amount to be withdrawlled");
                    String amount8 = sc.next();
                    String query8="UPDATE ACCOUNT SET BALANCE=BALANCE-? WHERE ACCOUNT_NO=?";
                      PreparedStatement preparedStmt8 = conn.prepareStatement(query8);
                      preparedStmt8.setString (1, amount8);
                      preparedStmt8.setString (2, account_no8);
                      preparedStmt8.execute();
                      System.out.println("Amount Withdrawlled");
                      break;
                      
                  case 9:
                    stmt.close();
                    System.exit(0);
                  default:
                    System.out.println("Invalide input.Please try again.");
                    
              }
              
            }while(choice !=9);
              
              
             
        } catch (SQLException e) {
           e.printStackTrace();
        }
 
  
  }

}