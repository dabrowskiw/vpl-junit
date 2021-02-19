import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VPLCommunicationRunner extends JUnit4ClassRunner {

    public VPLCommunicationRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void invokeTestMethod(Method method, RunNotifier notifier) {
        Description description = methodDescription(method);
        Object test;
        if(!(VPLCommunicator.class.isAssignableFrom(getTestClass().getJavaClass()))) {
            notifier.fireTestStarted(description);
            notifier.fireTestFailure(new Failure(description,
                    new Exception("Can only run test classes extending VPLCommunicator using VPLCommunicationRunner.")));
            notifier.fireTestFinished(description);
        }

        try {
            test = createTest();
        } catch (InvocationTargetException e) {
            testAborted(notifier, description, e.getCause());
            return;
        } catch (Exception e) {
            testAborted(notifier, description, e);
            return;
        }
        TestMethod testMethod = wrapMethod(method);
        new VPLMethodRoadie(test, testMethod, notifier, description, method).run();
    }

    private void testAborted(RunNotifier notifier, Description description,
                             Throwable e) {
        notifier.fireTestStarted(description);
        notifier.fireTestFailure(new Failure(description, e));
        notifier.fireTestFinished(description);
    }
}
