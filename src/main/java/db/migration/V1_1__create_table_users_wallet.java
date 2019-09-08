package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_1__create_table_users_wallet extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
    	.execute("CREATE TABLE users_wallet("
    			+ "id int(11) NOT NULL AUTO_INCREMENT,"
    			+ "usd decimal(20,2),"
    			+ "eur decimal(20,2),"
    			+ "chf decimal(20,2),"
    			+ "rub decimal(20,2),"
    			+ "czk decimal(20,2),"
    			+ "gbp decimal(20,2),"
    			+ "pln decimal(20,2),"
    			+ "PRIMARY KEY (id))"
    			+ "ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1");
	}
}
