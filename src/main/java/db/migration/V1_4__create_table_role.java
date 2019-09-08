package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_4__create_table_role extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
    	.execute("CREATE TABLE cash("
    			+ "currency char(3) NOT NULL,"
    			+ "value decimal(20,2) DEFAULT NULL,"
    			+ "PRIMARY KEY (currency))"
    			+ "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1");
	}
}
