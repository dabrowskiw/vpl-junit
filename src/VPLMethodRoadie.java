import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.TestTimedOutException;

import java.lang.reflect.Method;
import java.util.concurrent.*;

public class VPLMethodRoadie extends MethodRoadie {
    private Object test = null;
    private RunNotifier notifier = null;
    private Description description = null;
    private TestMethod testMethod = null;
    private Method method = null;

    public VPLMethodRoadie(Object test, TestMethod method, RunNotifier notifier, Description description, Method rawmethod) {
        super(test, method, notifier, description);
        this.test = test;
        this.notifier = notifier;
        this.description = description;
        testMethod = method;
        this.method = rawmethod;
    }

    @Override
    public void run() {
        if (testMethod.isIgnored()) {
            notifier.fireTestIgnored(description);
            return;
        }
        notifier.fireTestStarted(description);
        ((VPLCommunicator)test).initializeTest();
        ((VPLCommunicator)test).getTestResult().setMaxPoints(VplJUnitTester.getPointsForFunctionName(method.getName()));
        try {
            long timeout = testMethod.getTimeout();
            if (timeout > 0) {
                runWithTimeout(timeout);
            } else {
                runTest();
            }
        } finally {
            notifier.fireTestFailure(new Failure(description, ((VPLCommunicator)test).getTestResult()));
//            notifier.fireTestFinished(description);
        }
    }

    private void runWithTimeout(final long timeout) {
        runBeforesThenTestThenAfters(new Runnable() {

            public void run() {
                ExecutorService service = Executors.newSingleThreadExecutor();
                Callable<Object> callable = new Callable<Object>() {
                    public Object call() throws Exception {
                        runTestMethod();
                        return null;
                    }
                };
                Future<Object> result = service.submit(callable);
                service.shutdown();
                try {
                    boolean terminated = service.awaitTermination(timeout,
                            TimeUnit.MILLISECONDS);
                    if (!terminated) {
                        service.shutdownNow();
                    }
                    result.get(0, TimeUnit.MILLISECONDS); // throws the exception if one occurred during the invocation
                } catch (TimeoutException e) {
                    addFailure(new TestTimedOutException(timeout, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    addFailure(e);
                }
            }
        });
    }

    @Override
    protected void addFailure(Throwable e) {
//        notifier.fireTestFailure(new Failure(description, e));

        ((VPLCommunicator)test).getTestResult().setException(e);
        notifier.fireTestFailure(new Failure(description, ((VPLCommunicator)test).getTestResult()));


        //TODO: Dublicate line 44. 
    }
}
