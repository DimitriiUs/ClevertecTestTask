package by.home.aspect.annotations;

import by.home.aspect.caching.CacheInitializer;
import by.home.aspect.entity.Car;
import by.home.aspect.caching.Cache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

@Aspect
public class AspectCache {

    private CacheInitializer cacheInitializer = new CacheInitializer();
    private Cache<Object> cache = cacheInitializer.getCacheFromYamlFile("application.yaml");

    @AfterReturning(
            value = "@annotation(ru.clevertec.annotations.Cache) && execution(public * save*(..))",
            returning = "car")
    public void saveToCache(Car car) {
        cache.set(car.getId(), car);
        System.out.println("Car was saved into CACHE");
    }

    @Around("@annotation(ru.clevertec.annotations.Cache) && execution(public * get*(..))")
    public Object getFromCache(ProceedingJoinPoint joinPoint) throws Throwable {
        int id = (Integer) Arrays.stream(joinPoint.getArgs()).findFirst().get();

        if (cache.get(id) != null) {
            System.out.println("Got Car from CACHE");
            return cache.get(id);
        } else {
            return joinPoint.proceed();
        }
    }

    @Around("@annotation(ru.clevertec.annotations.Cache) && execution(public * delete*(..))")
    public Object deleteFromCache(ProceedingJoinPoint joinPoint) throws Throwable {
        int id = (Integer) Arrays.stream(joinPoint.getArgs()).findFirst().get();

        Object proceed = joinPoint.proceed();
        if (cache.get(id) != null) {
            cache.delete(id);
            System.out.println("Deleted Car from CACHE");
        }
        return proceed;
    }


    @AfterReturning(
            value = "@annotation(ru.clevertec.annotations.Cache) && execution(public * update*(..))",
            returning = "car")
    public void updateInCache(Car car) {
        cache.set(car.getId(), car);
        System.out.println("Car was updated in CACHE");
    }
}
