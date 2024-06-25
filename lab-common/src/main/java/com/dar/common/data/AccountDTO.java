package com.dar.common.data;

import com.dar.common.utilites.PasswordCryptography;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
/**
 * дата-класс представляющий аккаунт - форма для хранения логина и пароля
 */

@Getter
@Setter
public class AccountDTO implements Account {

    @Getter
    @Setter
    private static AccountDTO commonAcc = new AccountDTO("common", PasswordCryptography.encodePassword("0000"));
    public String login;

    public String password;

    public AccountDTO(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public AccountDTO(Account account){
        this.login = account.getLogin();
        this.password = account.getPassword();
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + this.getLogin() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDTO account)) return false;
        return this.getLogin().equals(account.getLogin()) && this.getPassword().equals(account.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLogin(), this.getPassword());
    }

}
