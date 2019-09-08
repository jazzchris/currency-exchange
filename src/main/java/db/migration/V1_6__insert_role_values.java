package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1_6__insert_role_values extends BaseJavaMigration {

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
    	.execute("INSERT INTO role (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN')");
	}
}
