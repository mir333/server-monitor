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
import java.util.List;

/**
 * @author Miroslav Ligas <miroslav.ligas@ibacz.eu>
 */
public class ServerData {
    private Date now;
    private String remoteAddr;
    private List<GpuData> gpu;
    private String systemup;
    private String localAddr;

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public List<GpuData> getGpu() {
        return gpu;
    }

    public void setGpu(List<GpuData> gpu) {
        this.gpu = gpu;
    }

    public String getSystemup() {
        return systemup;
    }

    public void setSystemup(String systemup) {
        this.systemup = systemup;
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }
}
