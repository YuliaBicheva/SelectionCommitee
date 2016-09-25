package ua.nure.bycheva.SummaryTask4.db;

import ua.nure.bycheva.SummaryTask4.db.entity.Sheet;

/**
 * Created by yulia on 03.09.16.
 */
public enum  ShetStatus {
    IN_WORK, FINALLY;

    public static ShetStatus getStatus(Sheet sheet) {
        int statusId = sheet.getStatusId();
        return ShetStatus.values()[statusId];
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_"," ");
    }
}
