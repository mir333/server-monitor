/* ===========================================================================
 * IBA CZ Confidential
 *
 * (c) Copyright IBA CZ 2011 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 *
 * =========================================================================== */
package im.ligas.monitoring.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
@Component
public class WatchDog {

    @Value("${max-update-interval}")
    private Integer maxUpdateInterval;

    private Date lastUpdate;

    public WatchDog() {
        lastUpdate = new Date(0);
    }

    public void update() {
        lastUpdate = new Date();
    }

    public boolean isReceiving() {
        Date now = new Date();
        long delta = now.getTime() - lastUpdate.getTime();

        return delta < maxUpdateInterval;
    }

}
