package com.mycompany.smartlibrary;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanDetail {
    private LocalDate borrowedAt;
    private LibraryResource resource;

    public LoanDetail(LibraryResource resource) {
        this.resource = resource;
        this.borrowedAt = LocalDate.now();
    }

    public long getDaysLate() {
        LocalDate dueDate = this.borrowedAt.plusDays(Constants.MAX_RETURN_DAYS);
        LocalDate today = LocalDate.now();

        long diff = ChronoUnit.DAYS.between(dueDate, today);
        return diff > 0 ? diff : 0;
    }

    public LibraryResource getResource() {
        return resource;
    }

    public void setResource(LibraryResource resource) {
        this.resource = resource;
    }
}
