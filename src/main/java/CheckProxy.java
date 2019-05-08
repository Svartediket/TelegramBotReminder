import java.io.IOException;
import java.net.*;

public class CheckProxy  {

    public boolean check(String proxyIP, int proxyPort, String proxyType) throws IOException {
        SocketAddress addr = new InetSocketAddress(proxyIP, proxyPort);
        Proxy.Type _pType = (proxyType.equals("HTTP") ? Proxy.Type.HTTP : Proxy.Type.SOCKS);
        Proxy httpProxy = new Proxy(_pType, addr);
        HttpURLConnection urlConn = null;
        URL url;

        url = new URL("https://api.telegram.org:443");
        urlConn = (HttpURLConnection) url.openConnection(httpProxy);
        urlConn.setConnectTimeout(5000);
        urlConn.connect();
        return (urlConn.getResponseCode() == 200);

    }
}