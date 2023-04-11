package tw.com.eeit162.meepleMasters.michael.friend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.com.eeit162.meepleMasters.michael.friend.model.FriendInvite;
import tw.com.eeit162.meepleMasters.michael.friend.model.FriendInviteDao;

@Service
public class FriendInviteService {
	@Autowired
	private FriendInviteDao inviteDao;
	
	public void insertFriendInvite() {
		FriendInvite friendInvite = new FriendInvite();
		friendInvite.setFkInviterId(1);
		friendInvite.setFkAccepterId(2);
		
		inviteDao.save(friendInvite);
		
	
	}
}
