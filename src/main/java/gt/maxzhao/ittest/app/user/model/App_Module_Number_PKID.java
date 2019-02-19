package gt.maxzhao.ittest.app.user.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author maxzhao
 */
@Setter
@Getter
@Entity
@Table(name = "APP_MODULE_PKID")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class App_Module_Number_PKID {
    @Id
    @Column(name = "pkid")
    @GeneratedValue(generator = "app_pkid")
    @GenericGenerator(
            name = "app_pkid",
            strategy = "enhanced-table"
            , parameters = {@org.hibernate.annotations.Parameter(name = "table_name", value = "APP_MODULE_PKID")
            , @org.hibernate.annotations.Parameter(name = "value_column_name", value = "pkid_value")
            , @org.hibernate.annotations.Parameter(name = "segment_column_name", value = "pkid_name")
            , @org.hibernate.annotations.Parameter(name = "segment_value", value = "app_pkid")
            , @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            , @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled-lo")})

    private Long pkid;

}
