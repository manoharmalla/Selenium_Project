package retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class retryAnalyzer implements IRetryAnalyzer {
    private static final int maxRetryCount = 1;
    private static final Map<String, Integer> retryCounts = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String testKey = result.getInstanceName() + "." + result.getName();
        int currentRetryCount = retryCounts.getOrDefault(testKey, 0);

        if (currentRetryCount < maxRetryCount) {
            int nextRetryCount = currentRetryCount + 1;
            retryCounts.put(testKey, nextRetryCount);
            System.out.println("Retrying test: " + result.getName());
            System.out.println("Retry attempt: " + nextRetryCount);
            return true;
        }

        retryCounts.remove(testKey);
        return false;
    }
}
