/* ===========================================================================
 * IBA CZ Confidential
 *
 * (c) Copyright IBA CZ 2011 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 *
 * =========================================================================== */
package im.ligas.settings;

import java.util.Date;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class WatchDog {
    private static final WatchDog INSTANCE = new WatchDog();
    private static final int MAX_UPDATE_INTERVAL = 100000;

    private Date lastUpdate;

    private WatchDog() {
        lastUpdate = new Date(0);
    }

    public static WatchDog getInstance() {
        return INSTANCE;
    }

    public void update() {
        lastUpdate = new Date();
    }

    public boolean isReceiving() {
        Date now = new Date();
        long delta = now.getTime() - lastUpdate.getTime();

        return delta < MAX_UPDATE_INTERVAL;
    }

}
