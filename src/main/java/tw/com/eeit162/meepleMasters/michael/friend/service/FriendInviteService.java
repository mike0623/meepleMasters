package tw.com.eeit162.meepleMasters.michael.friend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.com.eeit162.meepleMasters.michael.friend.model.FriendInvite;
import tw.com.eeit162.meepleMasters.michael.friend.model.FriendInviteDao;

@Service
public class FriendInviteService {
	@Autowired
	private FriendInviteDao inviteDao;
	
	public void insertFriendInvite(Integer InviterId,Integer AccepterId) {
		FriendInvite friendInvite = new FriendInvite();
		friendInvite.setFkInviterId(InviterId);
		friendInvite.setFkAccepterId(AccepterId);
		
		inviteDao.save(friendInvite);
	}
	
	@Transactional
	public void deleteFriendInvite(Integer id) {
		inviteDao.deleteById(id);
	}
	
	
	public List<FriendInvite> findFriendInviteByInviterId(Integer InviterId) {
		return inviteDao.findFriendInviteByInviterId(InviterId);
	}
	
	
	public List<FriendInvite> findFriendInviteByAccepterId(Integer AccepterId) {
		return inviteDao.findFriendInviteByAccepterId(AccepterId);
	}
	
	
	public FriendInvite findFriendInviteByBoth(Integer InviterId,Integer AccepterId) {
		return inviteDao.findFriendInviteByBoth(InviterId, AccepterId);
	}
	
	
	public FriendInvite findFriendInviteById(Integer id) {
		Optional<FriendInvite> friendInvite = inviteDao.findById(id);
		if(friendInvite != null) {
			return friendInvite.get();
		}
		return null;
	}
	
	@Transactional
	public void updateFriendInvite(FriendInvite newFriendInvite) {
		FriendInvite oldFriendInvite = findFriendInviteById(newFriendInvite.getFriendInviteId());
		if(oldFriendInvite!=null) {
			oldFriendInvite.setFkInviterId(newFriendInvite.getFkInviterId());
			oldFriendInvite.setFkAccepterId(newFriendInvite.getFkAccepterId());
		}
	}
	
	
}
