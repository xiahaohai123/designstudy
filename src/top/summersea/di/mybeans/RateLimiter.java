package top.summersea.di.mybeans;

public class RateLimiter {

    private RedisCounter redisCounter;

    public RateLimiter(RedisCounter redisCounter) {
        this.redisCounter = redisCounter;
    }

    public void test() {
        System.out.println("hello~");
    }
}
