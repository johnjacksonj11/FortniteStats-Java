package apps;

import java.util.ArrayList;

import java.util.Hashtable;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class PlayerStatsFrame {
	public String name;
	public String url;
	public String ID;
	
	//I only used the first 20 stats
	public static String[] statNames = new String[] {"Solo_Wins","Duo_Wins","Squad_Wins","Solo_Kills","Duo_Kills","Squad_Kills","Solo_KD",
			"Duo_KD","Squad_KD","Solo_Matches","Duo_Matches","Squad_Matches","Kills_Overall",
			"KD_Overall","KPG","Matches_Overall","Win_Percentage","Highest_Kill_Game","Longest_Win_Streak","Longest_Kill_Streak"
			};
	public static ArrayList<PlayerStatsFrame> playerList = new ArrayList<PlayerStatsFrame>();
	public Hashtable<String, Double> PlayerStatsFrameTable = new Hashtable<String, Double>();
	
	PlayerStatsFrame(String id, String nname){
		url = "https://stormshield.one/pvp/stats/" + id;
		name = nname;
		ID = id;
		
		PlayerStatsFrameTable = fillStatTable(url);
		playerList.add(this);
	}

	static Hashtable<String, Double> fillStatTable(String myUrl) {
		
		//.ownText() is used to convert Elements to String 
			
		//remove percentage signs from values
			
	}
	//returns date in yyyy-mm-dd format for sql storage
	public static java.sql.Date getDateForSql(){
		//returns date in yyyy-mm-dd format for sql storage
		
	}
	public static void statsToMySql() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url= "jdbc:mysql://localhost:3306/yourSchemaName";
			String username = "yourUsername";
			String password = "yourPassword";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
		
			
			//Create String query
			//Run Query Through Prepared Statement
			//PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(query);
			//prepStmt.executeUpdate();
				
			
			//String query = "insert into date_table (EPIC_ID,etc) values('TruAmericanHero5','etc')";
			//PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(query);
			//prepStmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e);}
		}
			

	
	
	public static void printCurrentTop3(ArrayList<PlayerStatsFrame> playerList) {
		//Bonus: Handle ties
	
		System.out.println(getDateForSql());
		//Loop thru each statValue and loop thru each objects statValue 
			
		//printf
		//System.out.printf("%-20s : 1.%-20s [%11f] 2.%-20s [%11f] 3.%-20s [%11f] %n %s %n", statNames[i],topPlayer,topStat, secPlayer, secStat, thirdPlayer, thirdStat,"--------------------------------------------------------------------------------------------");
			
		}	
		
		
		
	



	public static void statsToText(String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName,true);
			//write date
			
			//write playerName:
				
	
		}catch(Exception e) {System.out.println(e);}
	}
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		//PlayerStatsFrame Brad = new PlayerStatsFrame("cooterdestoyr69", "Brad");
		//PlayerStatsFrame Jordy = new PlayerStatsFrame("truamericanhero5", "Jordy" );
		//PlayerStatsFrame JohnJohn = new PlayerStatsFrame("SithariWindu", "JohnJohn");
		//PlayerStatsFrame Carson = new PlayerStatsFrame("BusterzRedRocket", "Carson");

		
		//printCurrentTop3(playerList);
		
		//You will have to change this to a location of your choice
		//String fName = "C:\\Users\\johnj\\Desktop\\FortStats";
		
		//statsToText(fName);
		
		
		//statsToMySql();
		
		
	}

}