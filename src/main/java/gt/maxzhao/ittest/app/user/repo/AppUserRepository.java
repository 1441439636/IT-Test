package gt.maxzhao.ittest.app.user.repo;

import gt.maxzhao.ittest.app.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maxzhao
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    /**
     *
     * @param loginAccount
     * @return
     */
    AppUser findByLoginAccount(String loginAccount);
}
