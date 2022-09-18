package com.xiongus.bear.console.entity.po;

import com.xiongus.bear.common.entity.BaseEntity;
import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * OAuth2.0客户端 Entity.
 *
 * @author xiongus
 */
@Getter
@Setter
@Entity
@Table(name = "oauth2_registered_client")
@SQLDelete(sql = "UPDATE oauth2_registered_client SET deleted = 1, deleted_time = ROUND(UNIX_TIMESTAMP(NOW(4))*1000) WHERE id = ?")
@Where(clause = "deleted = 0")
public class Oauth2RegisteredClient extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 7837169116228080167L;

	@Column(name = "client_id", length = 100, columnDefinition = "null")
	private String clientId;

	@Column(name = "client_id_issued_at", columnDefinition = "null")
	private java.time.LocalDateTime clientIdIssuedAt;

	@Column(name = "client_secret", length = 200, columnDefinition = "null")
	private String clientSecret;

	@Column(name = "client_secret_expires_at", columnDefinition = "null")
	private java.time.LocalDateTime clientSecretExpiresAt;

	@Column(name = "client_name", length = 200, columnDefinition = "null")
	private String clientName;

	@Column(name = "client_authentication_methods", length = 1000, columnDefinition = "null")
	private String clientAuthenticationMethods;

	@Column(name = "authorization_grant_types", length = 1000, columnDefinition = "null")
	private String authorizationGrantTypes;

	@Column(name = "redirect_uris", length = 1000, columnDefinition = "null")
	private String redirectUris;

	@Column(name = "scopes", length = 1000, columnDefinition = "null")
	private String scopes;

	@Column(name = "client_settings", length = 2000, columnDefinition = "null")
	private String clientSettings;

	@Column(name = "token_settings", length = 2000, columnDefinition = "null")
	private String tokenSettings;
}
