package sda.catalogue.sdacataloguerestapi.core.Authorization;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.containsKey("sda-catalogue-client-id")) {
            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("sda-catalogue-client-id");
            if (clientAccess.containsKey("roles")) {
                Collection<String> roles = (Collection<String>) clientAccess.get("roles");
                return roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }
}
