package ua.nure.bycheva.SummaryTask4.db;

import ua.nure.bycheva.SummaryTask4.db.entity.Entrant;

/**
 * Created by yulia on 25.08.16.
 */
public enum PassedStatus {
    BUDGET, CONTRACT;

    public static Status getStatus(Entrant entrant) {
        int statusId = entrant.getStatusId();
        return Status.values()[statusId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
