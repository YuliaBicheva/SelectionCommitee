package ua.nure.bycheva.SummaryTask4.db;

import ua.nure.bycheva.SummaryTask4.db.entity.User;

/**
 * Created by yulia on 12.08.16.
 */
public enum Role {
    ADMIN, ENTRANT;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
