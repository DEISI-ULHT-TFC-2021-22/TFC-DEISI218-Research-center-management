package pt.ulusofona.tfc.trabalho

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import java.io.File
import java.io.InputStream
import java.util.*

@Configuration
class OAuthSecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            //                .antMatchers("/**").authenticated() // Block this
            .antMatchers("/css/**", "/images/**", "/sass/**").permitAll()
            .antMatchers("/ceied-login").permitAll() // Allow this for all
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().logout()
            .logoutSuccessUrl("/ceied-login").permitAll()
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userAuthoritiesMapper(userAuthoritiesMapper())
    }

    private fun userAuthoritiesMapper(): GrantedAuthoritiesMapper {
        return GrantedAuthoritiesMapper { authorities: Collection<GrantedAuthority?> ->
            val mappedAuthorities: MutableSet<GrantedAuthority> = HashSet()
            authorities.forEach { authority ->
                if (authority is OidcUserAuthority) {
                    val oidcUserAuthority = authority
                    throw RuntimeException("Shouldn't be here")

                } else if (authority is OAuth2UserAuthority) {

                    val oauth2UserAuthority = authority
                    val userAttributes = oauth2UserAuthority.attributes

                    val inputStream: InputStream = File("src/main/resources/admin_list_test.txt").inputStream()
                    val lineList = mutableListOf<String>()
                    inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }

                    lineList.forEach{
                        if(userAttributes["id"] == it) {
                            mappedAuthorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
                        }
                    }
                    mappedAuthorities.add(SimpleGrantedAuthority("ROLE_USER"))
                }
            }
            mappedAuthorities
        }
    }
}