package deep;
import java.sql.*;
import java.util.Scanner;

public class hotel_management {

	public static void main(String[] args) {
		
	
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/Hotel_db";
			String username="root";
			String password="0147";
			Connection connection=DriverManager.getConnection(url,username,password);
			
			while(true) {
				System.out.println();
				System.out.println("HOTEL MANAGEMENT SYSTEM");
				Scanner scanner=new Scanner(System.in);
				System.out.println("1. Reserve a room");
				System.out.println("2. View Reservations");
				System.out.println("3. Get Room Number");
				System.out.println("4. Update Reservations");
				System.out.println("5. delete Reservations");
				System.out.println("0. Exit");
				System.out.println("Choose an option");
				
				int choice=scanner.nextInt();
				switch(choice) {
					case 1:
						reserveRoom(connection, scanner);
						break;
					case 2:
						viewReservation(connection,scanner);
						break;
					case 3:
						getRoomNumber(connection,scanner);
						break;
					
					case 4:
						updateReservation(connection,scanner);
						break;
					case 5:
						deleteReservation(connection,scanner);
						break;
					case 0:
						exit();
						break;
//				}
//				
				
			}}
			
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
		
		
	
	
	private static void reserveRoom(Connection connection,Scanner scanner) {
		
		try {
			
			System.out.print("Enter Guest Name: ");
			String guestName=scanner.next();
			scanner.nextLine();
			System.out.print("Enter Room Number: ");
			int roomNumber=scanner.nextInt();
			System.out.print("Enter Contact Number: ");
			String contactNumber=scanner.next();
			
			String sql="INSERT INTO reservation (guest_name,room_number,contact_number)"+
					"VALUES('"+guestName+"','" +roomNumber+"','"+contactNumber+"')";
			
			try (Statement statement=connection.createStatement()){
				int affectedRows=statement.executeUpdate(sql);
				
				if(affectedRows>0) {
					System.out.println("Reservation Successful!!!");
				}else {
					System.out.println("Reservation Failed....");
				}
			
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static void viewReservation(Connection connection, Scanner scanner) throws SQLException {
		String sql="SELECT reservation_id,guest_name,room_number,contact_number,reservation_date from reservation";
		try(Statement statement= connection.createStatement();
				ResultSet resultSet=statement.executeQuery(sql)){
			
			System.out.println("Current Reservation");
			 System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
	         System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
	         System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
	         
	         while(resultSet.next()) {
	        	 int reservationId = resultSet.getInt("reservation_id");
	        	 String guestName=resultSet.getString("guest_name");
	        	 int roomNumber=resultSet.getInt("room_number");
	        	 String contactNumber=resultSet.getString("contact_number");
	        	 String reservationDate=resultSet.getString("reservation_date").toString();
	        	 
//	        	 System.out.print(reservationID+"     "+guestName+"      "+roomNumber+"     "+contactNumber+"    "+reservationDate);
//	        	
	        	 System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
	                          reservationId,guestName, roomNumber, contactNumber, reservationDate);
	         }
	         System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
			
		}
		
		
	}
	
	
	
	
	private static void getRoomNumber(Connection connection,Scanner scanner) throws SQLException {
		
		 try {
			 System.out.print("Enter guest name: ");
			 String guestName=scanner.next();
			 
			 
			 String sql="SELECT room_number from reservation "
			 		+ "where guest_name='"+guestName+"'";
			 
			 
			 try(Statement statement=connection.createStatement();
					 ResultSet resultSet=statement.executeQuery(sql)){
				 
				 System.out.println("+---------------------+-------------------+");
				 System.out.println("      Guest Name     "+"     Room Number    ");
				 System.out.println("+---------------------+-------------------+");
				 
				 while(resultSet.next()) {
					 int roomNumber=resultSet.getInt("room_number");
					  
					 
					 System.out.println("          "+guestName+"            "+roomNumber);
					 
				 }
				 System.out.println("+---------------------+-------------------+");
//				 else {
//					 System.out.print("Room Number Not Found !!!!");
//				 }
			 }
			 
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
	}
	
	
	private static void updateReservation(Connection connection, Scanner scanner)throws SQLException{
			
		try {
			System.out.print("Enter Reservation ID to update : ");
			int reservationId=scanner.nextInt();
			scanner.nextLine();
			
			System.out.println("Enter New Guest Name: ");
			String newGuestName=scanner.nextLine();
			
			System.out.println("Enter new Contact number: ");
			String newContactNumber=scanner.nextLine();
			
			System.out.println("enter new Room number: ");
			int newRoomNumber=scanner.nextInt();
			
			
			
			
			String sql = "UPDATE reservation SET guest_name = '" + newGuestName + "', " +
                    "room_number = " + newRoomNumber + ", " +
                    "contact_number = '" + newContactNumber + "'"+
                    "WHERE reservation_id = " + reservationId;
			
			Statement statement=connection.createStatement();
				int affectedRows=statement.executeUpdate(sql);
				if(affectedRows>0) {
					System.out.print("Reservaion Updated SuccessFull..");
				}else {
					System.out.print("Updation Failed!!!!");
				}
			
			
		}catch(Exception e) {
			e.printStackTrace();	}
	}

	private static void deleteReservation(Connection connection,Scanner scanner)throws SQLException{
		
		
		try {
			
			System.out.print("Enter Reservation ID to delete ");
			int reservationId=scanner.nextInt();
			
			String sql="DELETE FROM reservation WHERE reservation_id="+reservationId;
			
			Statement statement=connection.createStatement();
			int affectedRows=statement.executeUpdate(sql);
			
			if(affectedRows>0) {
				System.out.print("Reservation Deleted SuccessFul");
			}else {
				System.out.print("Not Deleted!!!");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	private static void exit()throws InterruptedException{
		
		System.out.print("Exiting System");
		int i=5;
		while(i!=0) {
			System.out.print(".");
			Thread.sleep(300);
			i--;
		}
		
		System.out.println();
		System.out.print("Thank you for using Hotel Management System..");
	}
	
}
	
	
	

