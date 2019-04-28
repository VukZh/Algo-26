package cachetimeapp;

public class CacheTimeApp {

    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public long fiboCache(int n, cacheTime cta) { // фибоначчи с использованием кэша
        long res;
        long timeStartA1 = System.currentTimeMillis();
        if (cta.get(n) != null) {
            res = (long) cta.get(n);
            System.out.println("уже есть в кэше - " + n);
        } else {
            res = fibonacci(n);
            System.out.println("добавляем в кэш - " + n);
            cta.put(n, res);
        }
        long timeStopA1 = System.currentTimeMillis() - timeStartA1;
        System.out.println("время вычисления " + timeStopA1 + " миллисекунд");
        return res;
    }

    public static void main(String[] args) {

        CacheTimeApp c = new CacheTimeApp();
        cacheTime c1 = new cacheTime(4, 1); // 4 объекта в кэше, время жизни 1 секунда  

        System.out.println("----------первое заполнение кэша----------");

        c.fiboCache(30, c1);
        c.fiboCache(31, c1);
        c.fiboCache(35, c1);
        c.fiboCache(35, c1);
        c.fiboCache(23, c1);
        c.fiboCache(29, c1);
        c.fiboCache(29, c1);

        System.out.println("");
        System.out.println("----------второе заполнение кэша до стирания объектов----------");

        c.fiboCache(30, c1);
        c.fiboCache(31, c1);
        c.fiboCache(35, c1);
        c.fiboCache(35, c1);
        c.fiboCache(23, c1);
        c.fiboCache(29, c1);
        c.fiboCache(29, c1);

        try { // пауза > времени жизни объектов кэша
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        System.out.println("");
        System.out.println("----------третье заполнение кэша после стирания объектов в кэше----------");
        c.fiboCache(30, c1);
        c.fiboCache(31, c1);
        c.fiboCache(35, c1);
        c.fiboCache(35, c1);
        c.fiboCache(23, c1);
        c.fiboCache(29, c1);
        c.fiboCache(29, c1);

        c1.removeAll(); // принудительная очистка кэша

        System.out.println("");
        System.out.println("----------проверка удаления старых объектов----------"); // удаление объектов с жизнью > 1 секунды
        c1.put(100, 101);
        try { // пауза 
            Thread.sleep(400);
        } catch (Exception e) {
        }
        c1.put(200, 201);
        try { // пауза 
            Thread.sleep(400);
        } catch (Exception e) {
        }
        c1.put(300, 301);
        try { // пауза 
            Thread.sleep(400);
        } catch (Exception e) {
        }
        c1.put(400, 401);
        try { // пауза > 
            Thread.sleep(400);
        } catch (Exception e) {
        }

    }
}
