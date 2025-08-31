package org.sk_dev.iMUbackendPlugin;

import java.util.concurrent.*;

public class TempCodeUserMap {
    private static final TempCodeUserMap THIS = new TempCodeUserMap();
    private final ConcurrentHashMap<String, UserNameId> userMap
            = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private TempCodeUserMap() {}

    public static TempCodeUserMap getInstance() {
        return THIS;
    }

    public void put(String userCode, UserNameId uni) {
        userMap.put(userCode, uni);
        scheduler.schedule(() -> {
            userMap.remove(userCode);
        }, 3, TimeUnit.MINUTES);
    }

    public UserNameId get(String userCode) {
        return userMap.get(userCode);
    }

    public void remove(String userCode) {
        userMap.remove(userCode);
    }
}
