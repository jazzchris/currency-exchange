package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_2__create_table_users extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
    	.execute("CREATE TABLE users("
    			+ "id int(11) NOT NULL AUTO_INCREMENT,"
    			+ "first_name varchar(45) NOT NULL,"
    			+ "last_name varchar(45) NOT NULL,"
    			+ "username varchar(50) NOT NULL,"
    			+ "password char(68) NOT NULL,"
    			+ "email varchar(45) NOT NULL,"
    			+ "users_wallet_id int(11) DEFAULT NULL,"
    			+ "PRIMARY KEY (id),"
    			+ "KEY FK_WALLET_idx (users_wallet_id),"
    			+ "CONSTRAINT FK_WALLET FOREIGN KEY (users_wallet_id)"
    			+ "REFERENCES users_wallet (id)"
    			+ "ON DELETE NO ACTION ON UPDATE NO ACTION)"
    			+ "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1");
	}
}
