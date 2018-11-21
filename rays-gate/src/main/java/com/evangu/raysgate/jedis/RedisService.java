package com.evangu.raysgate.jedis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
/**
 * @author: Gu danpeng
 * @data: 2018-11-12
 * @version：1.0
 */
@Service
public class RedisService {

    private Jedis jedis;

    public RedisService(){
        if(this.jedis == null) {
            this.jedis = new Jedis("localhost");
        }
    }

    public String set(String key,String value) {
        return this.jedis.set(key,value);
    }

    public String get(String key){
        return this.jedis.get(key);
    }

    public static void main(String[] args){
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());
    }
}
