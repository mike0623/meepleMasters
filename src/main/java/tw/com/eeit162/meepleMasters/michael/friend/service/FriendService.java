package tw.com.eeit162.meepleMasters.michael.friend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.friend.model.Friend;
import tw.com.eeit162.meepleMasters.michael.friend.model.FriendDao;

@Service
public class FriendService {
	
	@Autowired
	private FriendDao friendDao;
	
	public Friend insertFriend() {
		Friend friend = new Friend();
		friend.setFkMemberAId(1);
		friend.setFkMemberBId(2);
		friendDao.save(friend);
		return friend;
	}
	
}
