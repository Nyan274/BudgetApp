package com.nyan.budgetapp.utils;

import io.reactivex.Scheduler;

public interface ThreadExecutor {
    Scheduler io();

    Scheduler ui();
}
