package gt.maxzhao.ittest.app.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author maxzhao
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "app_user")
@Data
public class AppUser extends App_Module_Number_PKID implements Serializable {
    @Transient
    private Long pkid;
    @Column(name = "chinaname")
    private String chinaname;
    @Column(name = "login_account")
    private String loginAccount;
    @JsonIgnore
    @Column(name = "login_passwd")
    private String loginPasswd;

    public AppUser() {
    }

    public AppUser(String loginAccount, String loginPasswd) {
        this.loginAccount = loginAccount;
        this.loginPasswd = loginPasswd;
    }
}




