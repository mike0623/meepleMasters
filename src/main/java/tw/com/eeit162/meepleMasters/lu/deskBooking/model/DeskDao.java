package tw.com.eeit162.meepleMasters.lu.deskBooking.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DeskDao extends JpaRepository<DeskBean, Integer> {

	@Query("SELECT d FROM DeskBean d WHERE d.deskId = ?1")
    DeskBean getDeskById(int deskId);
}
