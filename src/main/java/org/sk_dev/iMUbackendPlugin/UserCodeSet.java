package org.sk_dev.iMUbackendPlugin;

import idtoken.IDTokenProvider;

import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserCodeSet {
    private static final UserCodeSet THIS = new UserCodeSet();
    private final ConcurrentSkipListSet<IDTokenProvider.UserCode> userCodeSet
            = new ConcurrentSkipListSet<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private UserCodeSet() {}

    public static UserCodeSet getInstance() {
        return THIS;
    }

    public void put(IDTokenProvider.UserCode userCode) {
        userCodeSet.add(userCode);
        scheduler.schedule(() -> {
            userCodeSet.remove(userCode);
        }, 3, TimeUnit.MINUTES);
    }

    public boolean contains(IDTokenProvider.UserCode userCode) {
        return userCodeSet.contains(userCode);
    }

    public void remove(IDTokenProvider.UserCode userCode) {
        userCodeSet.remove(userCode);
    }
}
