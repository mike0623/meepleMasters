package tw.com.eeit162.meepleMasters.michael.friend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.michael.friend.model.Friend;
import tw.com.eeit162.meepleMasters.michael.friend.model.FriendDao;

@Service
public class FriendService {
	
	@Autowired
	private FriendDao friendDao;
	
	public void insertFriend(Integer fkMemberAId,Integer fkMemberBId) {
		Friend friend = new Friend();
		friend.setFkMemberAId(fkMemberAId);
		friend.setFkMemberBId(fkMemberBId);
		friendDao.save(friend);
	}
	
	@Transactional
	public void updateFriend(Friend newFriend) {
		Optional<Friend> oldFriend = friendDao.findById(newFriend.getFriendId());
		if(oldFriend!=null) {
			Friend friend = oldFriend.get();
			friend.setFkMemberAId(newFriend.getFkMemberAId());
			friend.setFkMemberBId(newFriend.getFkMemberBId());
			
			if(newFriend.getFriendCreatedDate()!=null) {
				friend.setFriendCreatedDate(newFriend.getFriendCreatedDate());
			}
		}
		
	}
	
	
	public List<Friend> findFriendByMemberId(Integer memberId) {
		return friendDao.findFriendByMemberId(memberId);
	}
	
	
	public Friend findFriendByBoth(Integer fkMemberAId,Integer fkMemberBId) {
		return friendDao.findFriendByBoth(fkMemberAId, fkMemberBId);
	}
	
	@Transactional
	public void deleteFriendById(Integer id) {
		friendDao.deleteById(id);
	}
	
	
	public boolean isFriend(Integer fkMemberAId,Integer fkMemberBId) {
		Friend result1 = friendDao.findFriendByBoth(fkMemberAId,fkMemberBId);
		Friend result2 = friendDao.findFriendByBoth(fkMemberBId,fkMemberAId);
		if(result1 != null || result2 != null) {
			return true;
		}
		return false;
	}
	
}
