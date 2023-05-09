package tw.com.eeit162.meepleMasters.michael.game.room;

import java.util.HashMap;
import java.util.Map;

import tw.com.eeit162.meepleMasters.michael.game.Game;

public class GameRoomUtil {
	private static final Map<String,Game> EXIST_GAME_ROOM = new HashMap<>();
	
	private static final Map<String,Game> OPENED_GAME_ROOM = new HashMap<>();
	
	private static Integer count = 1;

	
	public static String createNewRoom(String gameCode,Game game) {
		String tableCode = gameCode + count;
		if(count>=10000) {
			count = 1;
		}else {
			count++;
		}
		EXIST_GAME_ROOM.put(tableCode, game);
		return tableCode;
	}
	
	public static Map<String, Game> getExistGameRoom() {
		return EXIST_GAME_ROOM;
	} 
	
	
	public static Game getGameByTableCode(String tableCode) {
		return EXIST_GAME_ROOM.get(tableCode);
	}
	
	public static void removeGameByTableCode(String tableCode) {
		EXIST_GAME_ROOM.remove(tableCode);
	}

	
	
	//以下為跟openGameRoom有關的方法------------------------------
	public static Map<String, Game> getOpenedGameRoom() {
		return OPENED_GAME_ROOM;
	}
	
	public static void openingRoom(String tableCode,Game game) {
		OPENED_GAME_ROOM.put(tableCode, game);
	}
	
	public static void removeOpenedRoom(String tableCode) {
		OPENED_GAME_ROOM.remove(tableCode);
	}
	//-----------------------------------------------------------
	
	
}
