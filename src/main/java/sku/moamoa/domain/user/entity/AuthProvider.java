package sku.moamoa.domain.user.entity;

import java.util.Arrays;

public enum AuthProvider {
    GITHUB("GITHUB"),
    KAKAO("KAKAO"),
    EMPTY("");

    private String authProvider;

    public String getAuthProvider() {
        return authProvider;
    }

    AuthProvider(String authProvider){
        this.authProvider = authProvider;
    }

    public static AuthProvider findByCode(String code){
        return Arrays.stream(AuthProvider.values())
                .filter(provider -> provider.getAuthProvider().equals(code))
                .findFirst()
                .orElse(EMPTY);
    }
}
