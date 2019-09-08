package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_3__create_table_cash extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
    	.execute("CREATE TABLE role("
    			+ "id int(11) NOT NULL AUTO_INCREMENT,"
    			+ "name varchar(50) DEFAULT NULL,"
    			+ "PRIMARY KEY (id))"
    			+ "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1");
	}
}
