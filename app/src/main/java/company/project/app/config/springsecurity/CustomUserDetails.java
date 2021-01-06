package company.project.app.config.springsecurity;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * the spring security user details info with custom data
 *
 * @author huang
 */
public class CustomUserDetails implements UserDetails, CredentialsContainer {

    private Long id;

    private String password;

    private String username;

    private Boolean enabled;

    private String displayName;

    private String phone;

    private String email;

    private String createBy;

    private Date createTime;

    private Boolean locked;

    private Date lockTime;

    private Date showLockTime;

    private Date shortLockTime;

    private Integer loginFailTimes;

    private Date validTime;

    private Set<GrantedAuthority> authorities;

    public CustomUserDetails() {
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled && isAccountNonExpired() && isAccountNonLocked()
                && (shortLockTime==null || new Date(shortLockTime.getTime() + 24* 3600 *1000).before(new Date()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return validTime.after(new Date());
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.locked == false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                ", displayName='" + displayName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", locked=" + locked +
                ", lockTime=" + lockTime +
                ", showLockTime=" + showLockTime +
                ", shortLockTime=" + shortLockTime +
                ", loginFailTimes=" + loginFailTimes +
                ", validTime=" + validTime +
                ", authorities=" + authorities +
                ", enable=" + isEnabled() +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getShowLockTime() {
        return showLockTime;
    }

    public void setShowLockTime(Date showLockTime) {
        this.showLockTime = showLockTime;
    }

    public Date getShortLockTime() {
        return shortLockTime;
    }

    public void setShortLockTime(Date shortLockTime) {
        this.shortLockTime = shortLockTime;
    }

    public Integer getLoginFailTimes() {
        return loginFailTimes;
    }

    public void setLoginFailTimes(Integer loginFailTimes) {
        this.loginFailTimes = loginFailTimes;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

}
