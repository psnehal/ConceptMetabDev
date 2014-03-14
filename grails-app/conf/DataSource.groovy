dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    //username = "sa"
    //password = ""
	dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
           //  dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
            //url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
		    url = "jdbc:mysql://sartor-webdev.bicc.med.umich.edu:3306/conceptmetab?useUnicode=yes&characterEncoding=UTF-8"
			//url = "jdbc:mysql://localhost/conceptmetab?useUnicode=yes&characterEncoding=UTF-8"
			//url = "jdbc:mysql://127.0.0.1:33066/test?useUnicode=yes&characterEncoding=UTF-8"
			username = "snehal"
			password = "snehal"
			//logSql    = true
			//formatSql = true
			 properties {
				maxActive   = 10
				maxIdle     = 5
				minIdle     = 2
				initialSize = 2
		    }
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
			url = "jdbc:mysql://sartor-webdev.bicc.med.umich.edu:3306/conceptmetab?useUnicode=yes&characterEncoding=UTF-8"
			//url = "jdbc:mysql://127.0.0.1:33066/conceptmetab?useUnicode=yes&characterEncoding=UTF-8"
			username = "snehal"
			password = "snehal"
			logSql    = false
			formatSql = false
			 properties {
				maxActive   = 50
				maxIdle     = 25
				minIdle     = 5
				initialSize = 5
			}
        }
    }
}
