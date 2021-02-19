/**
 * Utility-Klasse für (public static)-Utility-Methoden wie compareResults, um so was tun zu können:
 *     @Test
 *     public void testGetMax_1P() {
 *         Method testMethod = null;
 *         Loesung loesung = new Loesung();
 *         try {
 *             testMethod = Loesung.class.getMethod("getMax", int.class, int.class, int.class);
 *         }
 *         catch(Exception e) {
 *             fail("Keine public-Methode getMax(int, int, int) definiert.");
 *         }
 *         try {
 *             Random r = new Random();
 *             for(int i=0; i<50; i++) {
 *                 int val1 = r.nextInt();
 *                 int val2 = r.nextInt();
 *                 int val3 = r.nextInt();
 *                 int res = (int) testMethod.invoke(loesung, val1, val2, val3);
 *                 int exp = Math.max(val1, Math.max(val2, val3));
 *                 compareResults(exp, testMethod, val1, val2, val3);
 * //                assertEquals("Falscher Maximalwert zurückgegeben: Bei Aufruf mit " + val1 + ", ... wurde erwartet: " + exp + " aber zurückgegeben " + res + ".", res, exp);
 *             }
 *        } catch(Exception e) {
 *             fail("Exception beim Aufruf von getMax. Fehlermeldung von Java: " + getTraceString(e));
 *         }
 *     }
 *
 *     public static compareResults(int result, Method toCall, Object ...args) {
 *         int res = (int) testMethod.invoke(toCall, args);
 *         if(res == exp) {
 *             return;
 *         }
 *         else {
 *             raise AssertionException("Bai Aufruf der Methode " + toCall.getName() + " mit den Argumenten " + ... + " wurde erwatet: ")
 *         }
 *     }
 */
public class VPLUtilities {


}
