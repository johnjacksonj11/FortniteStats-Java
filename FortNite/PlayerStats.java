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

public class PlayerStats {
	public String name;
	public String url;
	public String ID;
	
	//More Specific Names for easier searches
	public static String[] statNames = new String[] {"Solo_Wins","Duo_Wins","Squad_Wins","Solo_Kills","Duo_Kills","Squad_Kills","Solo_KD",
			"Duo_KD","Squad_KD","Solo_Matches","Duo_Matches","Squad_Matches","Kills_Overall",
			"KD_Overall","KPG","Matches_Overall","Win_Percentage","Highest_Kill_Game","Longest_Win_Streak","Longest_Kill_Streak"
			};
	public int statWins =0;
	public static ArrayList<PlayerStats> playerList = new ArrayList<PlayerStats>();
	public Hashtable<String, Double> playerStatsTable = new Hashtable<String, Double>();
	
	PlayerStats(String id, String nname){
		url = "https://stormshield.one/pvp/stats/" + id;
		name = nname;
		ID = id;
		
		playerStatsTable = fillStatTable(url);
		playerList.add(this);
	}
	

	static Hashtable<String, Double> fillStatTable(String myUrl) {
		Hashtable<String, Double> statTable = new Hashtable<String, Double>();
		
		ArrayList<String> statValues = new ArrayList<String>();
		
		try {
			Document doc2 = Jsoup.connect(myUrl).ignoreContentType(true).parser(Parser.xmlParser()).get();

			//Parse for stats
			Elements values = doc2.select("div.stat__value");
			
			
			
			
			//ownText is used to convert Elements to String 
			for(int i = 0; i<statNames.length; i++) {
				statValues.add(values.get(i).ownText());
				
			}			
		
			//remove percentage signs from values
			for(int i = 0; i<statNames.length;i++) {
				statValues.set(i, statValues.get(i).replaceAll("%", ""));
				statTable.put(statNames[i],Double.parseDouble(statValues.get(i)));
			}
			
			}
		catch(IOException e){
			System.out.println("Error while getting stats");}
		return statTable;
		
	
	}
	//returns date in yyyy-mm-dd format for sql storage
	public static java.sql.Date getDateForSql(){
		java.util.Date uDate = new java.util.Date(); 
		java.sql.Date sDate= new java.sql.Date(uDate.getTime());
		return sDate;
	}
	public static void statsToMySql() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url= "jdbc:mysql://localhost:3306/firstschema";
			String username = "JJ";
			String password = "taylor";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url,username,password);
			System.out.println("Connected");
			for(int i = 0; i<PlayerStats.playerList.size();i++) {
				//Set Up Columns and Values in MySQL format
				String EPIC_ID = playerList.get(i).ID;
				Date date = getDateForSql();
				String columnNames= "INSERT INTO date_table (EPIC_ID,Date";
				String values = " values('" + EPIC_ID + "',"+"'" +date+"'";
				//Loop to fill in rest of columnNames and values
				for(int j = 0; j<statNames.length; j++) {
					columnNames+= ","+statNames[j];
					values+= ",'" +playerList.get(i).playerStatsTable.get(statNames[j])+ "'";
				}
				columnNames+=")";
				values+=")";
				String query = columnNames + values;
				System.out.println(query);
				//Run Query Through Prepared Statement
				PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(query);
				prepStmt.executeUpdate();
				
			}
			//String query = "insert into date_table (EPIC_ID) values('TruAmericanHero5')";
			//PreparedStatement prepStmt = (PreparedStatement) con.prepareStatement(query);
			//prepStmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e);}
		}
			

	static double topStat;
	static double secStat;
	static double thirdStat;
	static String topPlayer;
	static String secPlayer;
	static String thirdPlayer;
	
	
	//Eventually plan to clean up the printTop3 for loop 
	//Right now it doesn't handle Ties 
	
	
	//static FortniteStats firstPlace;
	//static FortniteStats secondPlace;
	//static FortniteStats thirdPlace;
	
	
	public static void printCurrentTop3(ArrayList<PlayerStats> playerList) {
	
		System.out.println(getDateForSql());
		
		for(int i=0; i<statNames.length-1;i++) {
			
			
			double topStat=0;
			double secStat=0;
			double thirdStat=0;
			String topPlayer= "";
			String secPlayer= "";
			String thirdPlayer="";

			for(int j=0; j<playerList.size();j++) {
				if(playerList.get(j).playerStatsTable.get(statNames[i])>=topStat) {
					//playerList.get(j)
					thirdStat=secStat;
					thirdPlayer=secPlayer;
					secStat = topStat;
					secPlayer = topPlayer;
					topStat=playerList.get(j).playerStatsTable.get(statNames[i]);
					topPlayer = playerList.get(j).name;}

				
				else if(playerList.get(j).playerStatsTable.get(statNames[i])<topStat && playerList.get(j).playerStatsTable.get(statNames[i])>secStat) {
					thirdStat = secStat;
					thirdPlayer = secPlayer;
					secStat=playerList.get(j).playerStatsTable.get(statNames[i]);
					secPlayer = playerList.get(j).name;
				}
				else if(playerList.get(j).playerStatsTable.get(statNames[i])>thirdStat) {
					thirdStat=playerList.get(j).playerStatsTable.get(statNames[i]);
					thirdPlayer = playerList.get(j).name;
				}

				
				
			}
			
			
			System.out.printf("%-20s : 1.%-11s [%11f] 2.%-11s [%11f] 3.%-11s [%11f] %n %s %n", statNames[i],topPlayer,topStat, secPlayer, secStat, thirdPlayer, thirdStat,"--------------------------------------------------------------------------------------------");
			
		}	
		
		
		
	}



	public static void statsToText(String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName,true);
			//write date
			String date = getDateForSql().toString();
			fw.write("$\nDate ("+ date +")\n--------------");
			fw.write("\n");
			
			for(PlayerStats player: playerList) {
				
				//write playerID: 
				fw.write(player.name + "---" + player.ID + ": \n");
				
				//write playerName:
				String pname = player.name;
				for(int i=0;i<statNames.length;i++) {
					
					String stN = statNames[i];
				
					Double stV =player.playerStatsTable.get(statNames[i]);
					
					fw.write(stN + "===" + stV + "\n");
					
				}
				fw.write("################################ \n");
				
			}
			fw.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	
	
	
	
	
	}
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		PlayerStats Brad = new PlayerStats("cooterdestoyr69", "Brad");
		PlayerStats Jordy = new PlayerStats("truamericanhero5", "Jordy" );
		PlayerStats JohnJohn = new PlayerStats("SithariWindu", "JohnJohn");
		PlayerStats Carson = new PlayerStats("BusterzRedRocket", "Carson");

		
		printCurrentTop3(playerList);
		
		//You will have to change this to a location of your choice
		//String fName = "C:\\Users\\johnj\\Desktop\\FortStats";
		
		//statsToText(fName);
		
		
		//statsToMySql();
		
		
	}

}
