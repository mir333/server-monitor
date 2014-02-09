/* ===========================================================================
 * IBA CZ Confidential
 *
 * (c) Copyright IBA CZ 2011 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 *
 * =========================================================================== */
package im.ligas.settings;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class MemStorage {
    private static final MemStorage INSTANCE = new MemStorage(10, 5);
    private Buffer messages;
    private Buffer errors;

    private MemStorage(Integer len, Integer errorLen) {
        this.messages = new CircularFifoBuffer(len);
        this.errors = new CircularFifoBuffer(errorLen);
    }

    public static MemStorage getInstance() {
        return INSTANCE;
    }

    public void storeError(String message) {
        ErrorData data = new ErrorData(message);
        errors.add(data);
    }

    public void storeMessage(ServerData data) {
        messages.add(data);
    }

    public Buffer getErrors() {
        Iterator iterator = errors.iterator();
        while (iterator.hasNext()) {
            ErrorData next = (ErrorData) iterator.next();
            if(doDel(next.getDate())){
                iterator.remove();
            }
        }
        return errors;
    }

    private boolean doDel(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dayBack = calendar.getTime();
        return date.before(dayBack);
    }

    public Buffer getMessages() {
        return messages;
    }
}
