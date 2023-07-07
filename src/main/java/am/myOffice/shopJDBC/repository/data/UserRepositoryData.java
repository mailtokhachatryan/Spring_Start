package am.myOffice.shopJDBC.repository.data;

import am.myOffice.shopJDBC.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryData extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(nativeQuery = true,value = "SELECT name from users WHERE email = :email")
    String getName(@Param("email") String email);

    @Modifying
    @Query(nativeQuery = true,value = "INSERT name from users WHERE email = :email")
    void insert();

}
