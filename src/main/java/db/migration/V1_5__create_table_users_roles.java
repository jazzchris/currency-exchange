package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_5__create_table_users_roles extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
    	.execute("CREATE TABLE users_roles("
    			+ "users_id int(11) NOT NULL,"
    			+ "role_id int(11) NOT NULL,"
    			+ "PRIMARY KEY (users_id, role_id),"
    			+ "KEY FK_ROLE_idx (role_id),"
    			+ "CONSTRAINT FK_USERS_05 FOREIGN KEY (users_id)"
    			+ "REFERENCES users (id)"
    			+ "ON DELETE NO ACTION ON UPDATE NO ACTION,"
    			+ "CONSTRAINT FK_ROLE FOREIGN KEY (role_id)"
    			+ "REFERENCES role (id)"
    			+ "ON DELETE NO ACTION ON UPDATE NO ACTION)"
    			+ "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1");
	}
}
