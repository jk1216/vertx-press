package io.vertPress.manage.init;

import io.vertPress.manage.utils.ResultUtil;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.RoutingContext;

/**
 * @ClassName: InitDataBase
 * @Description: TODO 初始化数据库
 * @author FoamValue foamvalue@live.cn
 * @date 2017年2月26日 下午4:20:07
 * 
 */
public class InitDatabase {
	
	/**
	 * @Fields LOGGER : 日志对象
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(InitDatabase.class);

	/**
	 * @Fields WP_COMMENTMETA_SQL : TODO 创建 wp_commentmeta 表
	 */
	private final static String WP_COMMENTMETA_SQL = "CREATE TABLE IF NOT EXISTS wp_commentmeta ( meta_id bigint(20) NOT NULL AUTO_INCREMENT, comment_id bigint(20) DEFAULT NULL, meta_key varchar(255) DEFAULT NULL, meta_value longtext DEFAULT NULL, PRIMARY KEY (meta_id) ) ENGINE=InnoDB";

	/**
	 * @Fields WP_COMMENTS_SQL : TODO 创建 wp_comments 表
	 */
	private final static String WP_COMMENTS_SQL = "CREATE TABLE IF NOT EXISTS wp_comments ( comment_ID bigint(20) NOT NULL AUTO_INCREMENT,comment_post_ID bigint(20) DEFAULT NULL,comment_author tinytext DEFAULT NULL, comment_author_email varchar(200) DEFAULT NULL,comment_author_url varchar(200) DEFAULT NULL, comment_author_IP varchar(100) DEFAULT NULL, comment_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,comment_date_gmt datetime DEFAULT NULL,comment_content text DEFAULT NULL, comment_approved varchar(20) DEFAULT '0', comment_agent varchar(255)	 DEFAULT NULL,comment_type varchar(20) DEFAULT NULL,comment_parent bigint(20) DEFAULT NULL,user_id bigint(20) DEFAULT NULL,PRIMARY KEY (comment_ID)) ENGINE=InnoDB";

	/**
	 * @Fields WP_LINKS_SQL : TODO 创建 wp_links 表
	 */
	private final static String WP_LINKS_SQL = "CREATE TABLE IF NOT EXISTS wp_links (link_id bigint(20) NOT NULL AUTO_INCREMENT, link_url varchar(255) DEFAULT NULL, link_name varchar(255) DEFAULT NULL, link_target varchar(25) DEFAULT '_blank',  link_category varchar(255) DEFAULT NULL, link_description varchar(255) DEFAULT NULL, link_visible varchar(20) DEFAULT 'Y', link_owner bigint(20) DEFAULT NULL,  link_rating int(11) DEFAULT 0,link_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, link_notes mediumtext DEFAULT NULL,link_rss varchar(255) DEFAULT NULL, PRIMARY KEY (link_id)) ENGINE=InnoDB";

	/**
	 * @Fields WP_OPTIONS_SQL : TODO 创建 wp_options 表
	 */
	private final static String WP_OPTIONS_SQL = "	CREATE TABLE IF NOT EXISTS wp_options ( option_id bigint(20) NOT NULL AUTO_INCREMENT,option_name varchar(64) DEFAULT NULL, option_value longtext DEFAULT NULL, autoload varchar(20) DEFAULT NULL,  PRIMARY KEY (option_id)) ENGINE=InnoDB";

	/**
	 * @Fields WP_POSTMETA_SQL : TODO 创建 wp_postmeta 表
	 */
	private final static String WP_POSTMETA_SQL = "CREATE TABLE IF NOT EXISTS wp_postmeta (meta_id bigint(20) NOT NULL AUTO_INCREMENT, post_id bigint(20) DEFAULT NULL, meta_key varchar(255) DEFAULT NULL, meta_value longtext DEFAULT NULL,  PRIMARY KEY (meta_id)) ENGINE=InnoDB";

	/**
	 * @Fields WP_POSTS_SQL : TODO 创建 wp_posts 表
	 */
	private final static String WP_POSTS_SQL = "CREATE TABLE IF NOT EXISTS wp_posts (  id bigint(20) NOT NULL AUTO_INCREMENT, post_author bigint(20) DEFAULT NULL,  post_date datetime DEFAULT NULL,  post_date_gmt datetime DEFAULT NULL,   post_content longtext DEFAULT NULL, post_title text DEFAULT NULL,  post_excerpt text DEFAULT NULL,  post_status varchar(20) DEFAULT NULL,  comment_status varchar(20) DEFAULT NULL, post_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  post_modified_gmt datetime DEFAULT NULL,   post_parent bigint(20) DEFAULT 0,  guid varchar(255) DEFAULT NULL,  post_type varchar(20) DEFAULT NULL,  comment_count bigint(20) DEFAULT 0,  PRIMARY KEY (id) ) ENGINE=InnoDB";

	/**
	 * @Fields WP_TERMS_SQL : TODO 创建 wp_terms 表
	 */
	private final static String WP_TERMS_SQL = "CREATE TABLE IF NOT EXISTS wp_terms (   term_id bigint(20) NOT NULL AUTO_INCREMENT,  name bigint(20) DEFAULT NULL, slug varchar(32) DEFAULT NULL,   term_group longtext DEFAULT NULL,   PRIMARY KEY (term_id) ) ENGINE=InnoDB";

	/**
	 * @Fields WP_TERM_TAXONOMY_SQL : TODO 创建 wp_term_taxonomy 表
	 */
	private final static String WP_TERM_TAXONOMY_SQL = "CREATE TABLE IF NOT EXISTS wp_term_taxonomy (  term_taxonomy_id bigint(20) NOT NULL AUTO_INCREMENT,  term_id bigint(20) DEFAULT NULL, taxonomy varchar(32) DEFAULT NULL,   description longtext DEFAULT NULL,  parent bigint(20) DEFAULT NULL,  count bigint(20) DEFAULT NULL, PRIMARY KEY (term_taxonomy_id) ) ENGINE=InnoDB";

	/**
	 * @Fields WP_TERM_RELATIONSHIPS_SQL : TODO 创建 wp_term_relationships 表
	 */
	private final static String WP_TERM_RELATIONSHIPS_SQL = "	CREATE TABLE IF NOT EXISTS wp_term_relationships (  object_id bigint(20) NOT NULL AUTO_INCREMENT,   term_taxonomy_id bigint(20) DEFAULT NULL,  term_order int(11) DEFAULT NULL,  PRIMARY KEY (object_id) ) ENGINE=InnoDB";

	/**
	 * @Fields WP_USERMETA_SQL : TODO 创建 wp_usermeta 表
	 */
	private final static String WP_USERMETA_SQL = "	CREATE TABLE IF NOT EXISTS wp_usermeta (  umeta_id bigint(20) NOT NULL AUTO_INCREMENT, user_id bigint(20) DEFAULT NULL,   meta_key varchar(255) DEFAULT NULL,  meta_value longtext DEFAULT NULL, PRIMARY KEY (umeta_id) 	) ENGINE=InnoDB";

	/**
	 * @Fields WP_USERS_SQL : TODO 创建 wp_users 表
	 */
	private final static String WP_USERS_SQL = "CREATE TABLE IF NOT EXISTS wp_users (id bigint(20) NOT NULL AUTO_INCREMENT,user_login VARCHAR(60) DEFAULT NULL,user_pass VARCHAR(64) DEFAULT NULL,user_nicename VARCHAR(50) DEFAULT NULL,user_email VARCHAR(100) DEFAULT NULL,user_url VARCHAR(100) DEFAULT NULL,user_registered DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, user_activation_key VARCHAR(60) DEFAULT NULL,user_status int(1) DEFAULT 0,PRIMARY KEY (id)) ENGINE=InnoDB";

	public void handleGetProduct(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		SQLConnection conn = routingContext.get("conn");
		conn.execute(WP_COMMENTMETA_SQL, ddl1 -> {
			if (ddl1.failed()) {
				throw new RuntimeException(ddl1.cause());
			} else {
				conn.execute(WP_COMMENTS_SQL, ddl2 -> {
					if (ddl2.failed()) {
						throw new RuntimeException(ddl2.cause());
					} else {
						conn.execute(WP_LINKS_SQL, ddl3 -> {
							if (ddl3.failed()) {
								throw new RuntimeException(ddl3.cause());
							} else {
								conn.execute(WP_OPTIONS_SQL, ddl4 -> {
									if (ddl4.failed()) {
										throw new RuntimeException(ddl4.cause());
									} else {
										conn.execute(WP_POSTMETA_SQL, ddl5 -> {
											if (ddl5.failed()) {
												throw new RuntimeException(ddl5.cause());
											} else {
												conn.execute(WP_POSTS_SQL, ddl6 -> {
													if (ddl6.failed()) {
														throw new RuntimeException(ddl6.cause());
													} else {
														conn.execute(WP_TERMS_SQL, ddl7 -> {
															if (ddl7.failed()) {
																throw new RuntimeException(ddl7.cause());
															} else {
																conn.execute(WP_TERM_TAXONOMY_SQL, ddl8 -> {
																	if (ddl8.failed()) {
																		throw new RuntimeException(ddl8.cause());
																	} else {
																		conn.execute(WP_TERM_RELATIONSHIPS_SQL,
																				ddl9 -> {
																					if (ddl9.failed()) {
																						throw new RuntimeException(
																								ddl9.cause());
																					} else {
																						conn.execute(WP_USERMETA_SQL,
																								ddl10 -> {
																									if (ddl9.failed()) {
																										throw new RuntimeException(
																												ddl9.cause());
																									} else {
																										conn.execute(
																												WP_USERS_SQL,
																												ddl11 -> {
																													if (ddl11
																															.failed()) {
																														throw new RuntimeException(
																																ddl11.cause());
																													} else {
																														LOGGER.info("Database is Init.");
																													}
																												});
																									}
																								});
																					}
																				});
																	}
																});
															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
		ResultUtil.sendJSON("Initialize the database successfully!", response);
	}

}
