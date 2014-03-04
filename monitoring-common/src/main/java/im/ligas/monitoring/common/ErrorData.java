package im.ligas.monitoring.common;/* ===========================================================================
 * IBA CZ Confidential
 *
 * (c) Copyright IBA CZ 2011 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 *
 * =========================================================================== */

import java.util.Date;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class ErrorData {
    private Date date;
    private String message;

    public ErrorData(String message) {
        this.date = new Date();
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
