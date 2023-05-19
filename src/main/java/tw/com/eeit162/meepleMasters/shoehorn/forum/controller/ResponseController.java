package tw.com.eeit162.meepleMasters.shoehorn.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FollowResponse;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.Response;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseComment;
import tw.com.eeit162.meepleMasters.shoehorn.forum.service.ResponseService;

@Controller
public class ResponseController {

	@Autowired
	private ResponseService responseService;
	
//	-----Response-----
	
//	新增回文
	@ResponseBody
	@PostMapping("/response/createResponse")
	public void createResponse(@RequestBody String body) {
		responseService.createResponse(body);
	}
	
//	修改回文
	@ResponseBody
	@PostMapping("/response/updateResponse")
	public void updateResponse(@RequestBody String body) {
		responseService.updateResponse(body);
	}
	
//	查詢回文(多條件)
	@ResponseBody
	@PostMapping("/response/selectResponse")
	public List<Response> selectResponse(@RequestBody String body) {
		return responseService.selectResponse(body);
	}
	
//	刪除回文
	@ResponseBody
	@PostMapping("/response/deleteResponse")
	public void deleteResponse(@RequestBody String body) {
		responseService.deleteResponse(body);
	}
	
//	-----ResponseReview-----
	
//	更新回文評價	如果無該筆紀錄就新增
	@ResponseBody
	@PostMapping("/response/updateResponseReview")
	public void updateResponseReview(@RequestBody String body) {
		responseService.updateResponseReview(body);
	}
	
//	查詢回文評價
	@ResponseBody
	@PostMapping("/response/selectResponseReview")
	public Boolean selectResponseReview(@RequestBody String body) {
		return responseService.selectResponseReview(body);
	}
	
//	-----ResponseComment-----
	
//	新增回文留言
	@ResponseBody
	@PostMapping("/response/createResponseComment")
	public void createResponseComment(@RequestBody String body) {
		responseService.createResponseComment(body);
	}
	
//	修改回文留言
	@ResponseBody
	@PostMapping("/response/updateResponseComment")
	public void updateResponseComment(@RequestBody String body) {
		responseService.updateResponseComment(body);
	}
	
//	刪除回文留言
	@ResponseBody
	@PostMapping("/response/deleteResponseComment")
	public void deleteResponseComment(@RequestBody String body) {
		responseService.deleteResponseComment(body);
	}
	
//	查詢回文留言
	@ResponseBody
	@PostMapping("/response/selectResponseComment")
	public List<ResponseComment> selectResponseComment(@RequestBody String body) {
		return responseService.selectResponseComment(body);
	}
	
//	-----ResponseCommentReview-----
	
//	更新回文留言評價	如果無該筆紀錄就新增
	@ResponseBody
	@PostMapping("/response/updateResponseCommentReview")
	public void updateResponseCommentReview(@RequestBody String body) {
		responseService.updateResponseCommentReview(body);
	}
	
//	查詢回文留言評價
	@ResponseBody
	@PostMapping("/response/selectResponseCommentReview")
	public Boolean selectResponseCommentReview(@RequestBody String body) {
		return responseService.selectResponseCommentReview(body);
	}
	
//	-----FollowResponse-----
	
//	更新追蹤回文
	@ResponseBody
	@PostMapping("/response/updateFollowResponse")
	public void updateFollowResponse(@RequestBody String body) {
		responseService.updateFollowResponse(body);
	}
	
//	查詢追蹤回文
	@ResponseBody
	@PostMapping("/response/selectFollowResponse")
	public List<FollowResponse> selectFollowResponse(@RequestBody String body) {
		return responseService.selectFollowResponse(body);
	}	

//	查詢追蹤回文(以回文搜尋)
	@ResponseBody
	@PostMapping("/response/selectFollowResponseByResponse")
	public List<FollowResponse> selectFollowResponseByResponse(@RequestBody String body) {
		return responseService.selectFollowResponseByResponse(body);
	}
}
