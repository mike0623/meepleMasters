package tw.com.eeit162.meepleMasters.jack.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.com.eeit162.meepleMasters.jack.model.bean.Post;

public interface PostDao extends JpaRepository<Post, Integer> {

}
