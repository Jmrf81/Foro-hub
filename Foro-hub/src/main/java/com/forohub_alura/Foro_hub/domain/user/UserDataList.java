package com.forohub_alura.Foro_hub.domain.user;

public record UserDataList(Long id,
                           String name,
                           String email) {

    public UserDataList(User user){
        this(user.getId(),user.getName(),user.getEmail());
    }
}
