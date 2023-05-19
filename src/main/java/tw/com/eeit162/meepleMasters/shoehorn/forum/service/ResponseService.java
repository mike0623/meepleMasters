package tw.com.eeit162.meepleMasters.shoehorn.forum.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FollowArticle;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FollowResponse;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.FollowResponseDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.Response;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseComment;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseCommentDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseCommentReview;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseCommentReviewDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseDao;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseReview;
import tw.com.eeit162.meepleMasters.shoehorn.forum.model.ResponseReviewDao;

@Service
public class ResponseService {

	@Autowired
	private ResponseDao responseDao;
	@Autowired
	private ResponseReviewDao responseReviewDao;
	@Autowired
	private ResponseCommentDao responseCommentDao;
	@Autowired
	private ResponseCommentReviewDao responseCommentReviewDao;
	@Autowired
	private FollowResponseDao followResponseDao;
	
	
	
	
	
//	-----Response-----
	
//	新增回文
	public void createResponse(String jsonString) {
	
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Response response = new Response();
		
		response.setFkMemberId(jsonObject.getInt("memberId"));
		response.setFkArticleId(jsonObject.getInt("articleId"));
		response.setResponseCreatedDate(
				Date.from(LocalDateTime.now()
//						.truncatedTo(ChronoUnit.SECONDS) //將毫秒清0，但仍有小數點(.000)
						.atZone(ZoneId.systemDefault())
						.toInstant())
				);
		response.setResponseContent(jsonObject.getString("content"));
		
		responseDao.save(response);
	}
	
//	修改回文
	public void updateResponse(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		String content = jsonObject.getString("content");
		
		Date responseUpdatedDate =
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant());
		responseDao.updateResponseContentById(jsonObject.getInt("responseId"), responseUpdatedDate, content);
	}
	
//	查詢回文
	public List<Response> selectResponse(String jsonString){
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		
//		System.out.println(
//				Date.from(LocalDateTime.parse(jsonObject.getString("updatedDate")).atZone(ZoneId.systemDefault()).toInstant())
//				);
		
		return responseDao.selectResponse(
				jsonObject.has("memberId")?jsonObject.getInt("memberId"):null, 
				jsonObject.has("articleId")?jsonObject.getInt("articleId"):null
				);
//				jsonObject.has("createdDate")?jsonObject.getString("createdDate"):null,
//				jsonObject.has("updatedDate")?jsonObject.getString("updatedDate"):null);
//				jsonObject.has("createdDate")?Date.from(LocalDateTime.parse(jsonObject.getString("createdDate")).atZone(ZoneId.systemDefault()).toInstant()):null, 
//				jsonObject.has("updatedDate")?Date.from(LocalDateTime.parse(jsonObject.getString("updatedDate")).atZone(ZoneId.systemDefault()).toInstant()):null);
		
	}
	
//	刪除回文
	public void deleteResponse(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);

		int responseId = jsonObject.getInt("responseId");
		
		responseDao.deleteById(responseId);
	}
	
//	-----ResponseReview-----
	
//	更新回文評價	如果無該筆紀錄就新增
//	評價:	responseReview	無評:null		好評:1	負評:0
	public void updateResponseReview(String jsonString) {

		JSONObject jsonObject = new JSONObject(jsonString);

		ResponseReview responseReview = new ResponseReview();
		
		if (jsonObject.has("responseReviewId") && !responseReviewDao.findById(jsonObject.getInt("responseReviewId")).isEmpty()) {
//		更新
			responseReviewDao.updateResponseReview(jsonObject.getInt("responseReviewId"),
					jsonObject.isNull("responseReview")?null:jsonObject.getBoolean("responseReview"));
		} else {
//		新增
			responseReview.setFkResponseId(jsonObject.getInt("responseId"));
			responseReview.setFkMemberId(jsonObject.getInt("memberId"));
			responseReview.setResponseReview(
					jsonObject.isNull("responseReview")?null:jsonObject.getBoolean("responseReview")
					);
			responseReviewDao.save(responseReview);
		}
		
	}
	
//	查詢回文評價
	public Boolean selectResponseReview(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Optional<ResponseReview> findById = responseReviewDao.findById(jsonObject.getInt("responseReviewId"));
		
		return findById.isPresent()?findById.get().getResponseReview():null;
		
	}
	
//	-----ResponseComment-----
	
//	新增回文留言
	public void createResponseComment(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		ResponseComment responseComment = new ResponseComment();
		
		responseComment.setFkMemberId(jsonObject.getInt("memberId"));
		responseComment.setFkResponseId(jsonObject.getInt("responseId"));
		responseComment.setResponseCommentCreatedDate(
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant())				
				);
		responseComment.setResponseCommentContent(jsonObject.getString("content"));
		responseCommentDao.save(responseComment);		
		
	}
	
//	修改文章留言	
	
	public void updateResponseComment(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		String responseCommentContent = jsonObject.getString("content");
		
		Date responseCommentUpdatedDate =
				Date.from(LocalDateTime.now()
						.atZone(ZoneId.systemDefault())
						.toInstant());
		
		responseCommentDao.updateResponseCommentById(jsonObject.getInt("responseCommentId"), responseCommentUpdatedDate, responseCommentContent);
		
	}
	
//	刪除文章留言
	
	public void deleteResponseComment (String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		responseCommentDao.deleteById(jsonObject.getInt("responseCommentId"));
		
	}
	
//	查詢文章留言
//	動態多條件查詢
//	尚未加入日期的條件搜尋、標題&日期的模糊搜尋
	public List<ResponseComment> selectResponseComment(String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		List<ResponseComment> dynamicSelect = responseCommentDao.selectResponseComment(
				jsonObject.has("responseCommentId")?jsonObject.getInt("responseCommentId"):null,
				jsonObject.has("memberId")?jsonObject.getInt("memberId"):null,
				jsonObject.has("responseId")?jsonObject.getInt("responseId"):null
				);
		
		return dynamicSelect;
	}
	
//	-----ResponseCommentReview-----
	
//	更新回文留言評價	如果無該筆紀錄就新增
//	評價:	articleCommentReview	無評:null		好評:1	負評:0
	public void updateResponseCommentReview(String jsonString) {

		JSONObject jsonObject = new JSONObject(jsonString);

		ResponseCommentReview responseCommentReview = new ResponseCommentReview();
		
		if (jsonObject.has("responseCommentReviewId") && !responseCommentReviewDao.findById(jsonObject.getInt("responseCommentReviewId")).isEmpty()) {
//		更新
			responseCommentReviewDao.updateResponseCommentReview(jsonObject.getInt("responseCommentReviewId"),
					jsonObject.isNull("commentReview")?null:jsonObject.getBoolean("commentReview"));
		} else {
//		新增
			responseCommentReview.setFkResponseCommentId(jsonObject.getInt("responseCommentId"));
			responseCommentReview.setFkMemberId(jsonObject.getInt("memberId"));
			responseCommentReview.setCommentReview(
					jsonObject.isNull("commentReview")?null:jsonObject.getBoolean("commentReview")
					);
			responseCommentReviewDao.save(responseCommentReview);
		}
		
	}
	
//	查詢回文留言評價
	public Boolean selectResponseCommentReview(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Optional<ResponseCommentReview> findById = responseCommentReviewDao.findById(jsonObject.getInt("responseCommentReviewId"));
		
		return findById.isPresent()?findById.get().getCommentReview():null;

	}
	
//	-----FollowResponse-----
	
//	查詢追蹤回文
	public List<FollowResponse> selectFollowResponse(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return followResponseDao.findByFkMemberId(jsonObject.getInt("memberId"));
	}
	
//	更新追蹤回文
	public void updateFollowResponse(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		FollowResponse followResponse = new FollowResponse();
		
		if (jsonObject.has("followResponseId") && !followResponseDao.findById(jsonObject.getInt("followResponseId")).isEmpty()) {
			
			followResponseDao.deleteById(jsonObject.getInt("followResponseId"));
			
		} else {
			followResponse.setFkMemberId(jsonObject.getInt("memberId"));
			followResponse.setFkResponseId(jsonObject.getInt("responseId"));
				
			followResponseDao.save(followResponse);
		}	
		
	}
	
//	查詢追蹤回文(以回文搜尋)
	public List<FollowResponse> selectFollowResponseByResponse(String jsonString) {
		
		JSONObject jsonObject = new JSONObject(jsonString);
		
		return followResponseDao.findByFkResponseId(jsonObject.getInt("responseId"));
	}
	
}
