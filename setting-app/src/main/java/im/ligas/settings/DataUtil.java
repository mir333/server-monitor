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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class DataUtil {

    public static <T> List<T> bufferToList(Buffer storedData) {
        List<T> data = new ArrayList<>(storedData.size());
        for (Object o : storedData) {
            T serverData = (T) o;
            data.add(serverData);
        }
        Collections.reverse(data);
        return data;
    }
}
