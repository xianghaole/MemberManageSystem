package cn.lger.security;

import cn.lger.domain.Admin;
import cn.lger.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2018-04-05.
 */
public class CustomerUserDetailsMember implements UserDetails {

    private Member member;
    //存放权限的集合
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public CustomerUserDetailsMember(Member member, Collection<? extends GrantedAuthority> authorities) {
        this(member, true, true, true,true,authorities);
    }

    public CustomerUserDetailsMember(Member member, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if(member.getMemberName() != null && !"".equals(member.getMemberName()) && member.getPassword() != null) {
            this.member = member;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = authorities;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    public Member getMember() {
        return member;
    }

    public void setAdmin(@NotNull Member member) {
        this.member = member;
    }

    public boolean equals(Object rhs) {
        return rhs instanceof CustomerUserDetailsMember && this.getUsername().equals(((CustomerUserDetailsMember) rhs).getUsername());
    }

    public int hashCode() {
        return this.getUsername().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getMemberName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
