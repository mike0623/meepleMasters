package tw.com.eeit162.meepleMasters.michael.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import tw.com.eeit162.meepleMasters.jack.model.bean.Member;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.FavoriteGameList;
import tw.com.eeit162.meepleMasters.jim.mall.model.bean.Product;
import tw.com.eeit162.meepleMasters.michael.game.degree.model.GameDegree;

public class DataInterface {
	
	//資料介接用
	private static RestTemplate template = new RestTemplate();
	
//	public static HttpSession getSession() {
//	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//	    RequestContextHolder.setRequestAttributes(attr, true);
//	    return attr.getRequest().getSession(true); // true == allow create
//	}
	
	public static Member getMemberByMemberId(Integer memberId) {
		Member member = new Member();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/member/findMemberById?id="+memberId);
		RequestEntity<Void> requset = RequestEntity.get(uri).headers(headers).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response = template.exchange(requset, String.class);
		JSONObject memberJson = new JSONObject(response.getBody());
		
		member.setMemberId(memberJson.getInt("memberId"));
		member.setMemberEmail(memberJson.getString("memberEmail"));
//		member.setMemberPwd(memberJson.getString("memberPwd"));
		member.setMemberName(memberJson.getString("memberName"));
//		member.setMemberAge(memberJson.getInt("memberAge"));
//		member.setMemberGender(memberJson.getString("memberGender"));
//		member.setMemberTel(memberJson.getString("memberTel"));
//		member.setMemberAddress(memberJson.getString("memberAddress"));
		
		
		return member;
	}
	
	public static Member getMemberByEmail(String memberEmail) {
		Member member = new Member();
//		HttpHeaders headers = new HttpHeaders();
//		List<String> cookieList = new ArrayList<>();
//		cookieList.add("JSESSIONID=D015807F88976F585371CB105A335E99");
//		headers.put(HttpHeaders.COOKIE, cookieList);
		//.headers(headers)
//		headers.add("Cookie", "SESSION=D015807F88976F585371CB105A335E99");
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/member/findMemberByEmail?memberEmail="+memberEmail);
		RequestEntity<Void> requset = RequestEntity.get(uri).headers(headers).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<String> response = template.exchange(requset, String.class);
		JSONObject memberJson = new JSONObject(response.getBody());

		member.setMemberId(memberJson.getInt("memberId"));
		member.setMemberEmail(memberJson.getString("memberEmail"));
//		member.setMemberPwd(memberJson.getString("memberPwd"));
		member.setMemberName(memberJson.getString("memberName"));
//		member.setMemberAge(memberJson.getInt("memberAge"));
//		member.setMemberGender(memberJson.getString("memberGender"));
//		member.setMemberTel(memberJson.getString("memberTel"));
//		member.setMemberAddress(memberJson.getString("memberAddress"));
		
		
		return member;
	}
	
	public static Integer updateMemberCoin(Integer memberId, Integer coin) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberId", memberId);
		jsonObject.put("coin", coin);
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/member/updateMemberCoin");
		RequestEntity<String> requset = RequestEntity.post(uri).headers(headers).accept(MediaType.APPLICATION_JSON).body(jsonObject.toString());
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	
	
	public static Integer getNotRead(Integer senderId,Integer receiverId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/friendMessage/findNotRead?senderId="+senderId+"&receiverId="+receiverId);
		RequestEntity<Void> requset = RequestEntity.get(uri).headers(headers).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	public static Integer getMessageTextNewId(String messageText) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/messageText/insertMessageText");
		RequestEntity<String> requset = RequestEntity.post(uri).headers(headers).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json;charset=UTF-8").body(messageText);
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	public static Integer getMessageStickerNewId(Integer messageSticker) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/messageSticker/insertMessageSticker");
		RequestEntity<Integer> requset = RequestEntity.post(uri).headers(headers).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json;charset=UTF-8").body(messageSticker);
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	
	public static Integer getMessageStickerFkStickerIdById(Integer messageStickerId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/messageSticker/findById/"+messageStickerId);
		RequestEntity<Void> requset = RequestEntity.get(uri).headers(headers).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
	public static String insertWhenSendToOfflineFriend(String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/chatRoomOrder/insertWhenSendToOfflineFriend");
		RequestEntity<String> requset = RequestEntity.post(uri).headers(headers).accept(MediaType.APPLICATION_JSON).header("Content-Type", "application/json;charset=UTF-8").body(json);
		ResponseEntity<String> response = template.exchange(requset, String.class);
		return response.getBody();
	}
	
	public static Product getProductByProductName(String productName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		String parse = "";
		try {
			parse = URLEncoder.encode(productName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/mall/findProductByProductName/"+parse);
		RequestEntity<Void> requset = RequestEntity.get(uri).headers(headers).header("Content-Type", "application/json;charset=UTF-8").accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<Product> response = template.exchange(requset, Product.class);
		return response.getBody();
	}
	
	public static FavoriteGameList getFaveriteGameByEmail(String memberEmail) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/favoriteGame/favoriteGameList/"+memberEmail);
		RequestEntity<Void> requset = RequestEntity.get(uri).headers(headers).header("Content-Type", "application/json;charset=UTF-8").accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<FavoriteGameList> response = template.exchange(requset, FavoriteGameList.class);
		return response.getBody();
	}
	
	
	
	public static GameDegree getGameDegree(Integer memberId,Integer productId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/GameDegree/findGameDegree/"+memberId+"/"+productId);
		RequestEntity<Void> requset = RequestEntity.get(uri).headers(headers).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<GameDegree> response = template.exchange(requset, GameDegree.class);
		return response.getBody();
	}
	
	public static Integer updateGameDegreeByBoth(boolean isWin,Integer averageScore,Integer memberId,Integer productId) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("isServer", "yes");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("isWin", isWin);
		jsonObject.put("averageScore", averageScore);
		jsonObject.put("memberId", memberId);
		jsonObject.put("productId", productId);
		
		
		URI uri = URI.create("http://localhost:8080/meeple-masters/GameDegree/updateGameDegreeByBoth");
		RequestEntity<String> requset = RequestEntity.post(uri).headers(headers).accept(MediaType.APPLICATION_JSON).body(jsonObject.toString());
		ResponseEntity<Integer> response = template.exchange(requset, Integer.class);
		return response.getBody();
	}
	
}
