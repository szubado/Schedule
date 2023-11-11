package it.camp.schedule.model.dto;

import it.camp.schedule.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserListResponse {
    private final List<User> userList;
}
