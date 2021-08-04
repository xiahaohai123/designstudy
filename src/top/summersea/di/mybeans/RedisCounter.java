package top.summersea.di.mybeans;

public class RedisCounter {
    private String ip;
    private int count;

    public RedisCounter(String ip, Integer count) {
        this.ip = ip;
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
