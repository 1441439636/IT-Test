package gt.maxzhao.ittest.app.user.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "APP_MODULE_PKID")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class App_Module_String_PKID {
    @Id
    @Column(name = "pkid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String pkid;
}
