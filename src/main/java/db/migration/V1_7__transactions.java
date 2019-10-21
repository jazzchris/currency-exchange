package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_7__transactions extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
    	.execute("CREATE TABLE orders(" +
				"id int(11) NOT NULL AUTO_INCREMENT," +
				"users_id int(11) NOT NULL," +
				"currency varchar(3) NOT NULL," +
				"rate decimal(7,4) NOT NULL," +
				"amount decimal(10,0) NOT NULL," +
				"trans_type varchar(4) NOT NULL," +
				"PRIMARY KEY (id, users_id)," +
				"CONSTRAINT FK_USERS_idx FOREIGN KEY(users_id)" +
				"REFERENCES users (id)" +
				"ON DELETE NO ACTION ON UPDATE NO ACTION)" +
				"ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1");
	}
}
