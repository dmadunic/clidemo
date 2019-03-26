package com.ag04.clidemo.observer;

public class ProgressUpdateEvent {

    Long currentCount;
    Long totalCount;

    public ProgressUpdateEvent(Long currentRecord, Long totalRecords) {
        this.currentCount = currentRecord;
        this.totalCount = totalRecords;
    }

    public Long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Long currentCount) {
        this.currentCount = currentCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
