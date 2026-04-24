package com.javarush;

import com.javarush.domain.City;
import com.javarush.mapper.CityMapper;
import com.javarush.redis.CityCountry;
import com.javarush.service.CityService;
import com.javarush.service.RedisService;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        CityService cityService = new CityService();
        RedisService redisService = new RedisService();
        CityMapper cityMapper = new CityMapper();
        System.out.println("We start loading cities: ");

        List<City> cities = cityService.fetchData();
        List<CityCountry> preparedData = cityMapper.transformData(cities);
        redisService.pushToRedis(preparedData);

        List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

        long startRedis = System.currentTimeMillis();
        redisService.testRedisData(ids);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        cityService.testMysqlData(ids);
        long stopMysql = System.currentTimeMillis();

        System.out.println("Redis time: " + (stopRedis - startRedis));
        System.out.println("Mysql time: " + (stopMysql - startMysql));

        cityService.shutdown();
        redisService.shutdown();

    }
}