package remotify.client;

import org.apache.log4j.Logger;

/**
 * .
 * User: Ostap
 * Date: 5/2/14
 * Time: 3:01 PM
 */
public class Pinger {

    private static Logger l = Logger.getLogger(Pinger.class);

//    private boolean isInternetAvailable()
//    {
//        boolean res=false;
//
//        {
//            long time = System.currentTimeMillis();
//            boolean check = checkDNS("google.com", "8.8.8.8", 10000);
//            if (check)
//                l.trace("Connect to google public DNS 8.8.8.4 was susseccful in "
//                        +(System.currentTimeMillis()-time)+" msec");
//            else
//                l.error("Can't connect to google public DNS 8.8.8.8");
//
//            res = res || check;
//        }
//
//        {
//            long time = System.currentTimeMillis();
//            boolean check = checkDNS("---no-such-domain---", "198.41.0.4", 10000);
//
//            if (check)
//                l.trace("Connect to A root DNS server was susseccful in "
//                        +(System.currentTimeMillis()-time)+" msec");
//            else
//                l.error("Can't connect to A root DNS server");
//
//            res = res || check;
//        }
//        return res;
//    }
//
//
//    private static boolean checkDNS(String host, String dns, int timeout)
//    {
//        try
//        {
//            Lookup l = new Lookup(host);
//            Resolver r = new SimpleResolver(dns);
//            r.setTimeout(timeout/1000);
//            l.setResolver(r);
//            l.setCache(null);
//            l.run();
//
//            return l.getResult()!=Lookup.UNRECOVERABLE && l.getResult()!=Lookup.TRY_AGAIN;
//        } catch (Exception ex)
//        {
//            return false;
//        }
//    }

}
