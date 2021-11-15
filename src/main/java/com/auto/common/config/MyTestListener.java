package com.auto.common.config;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class MyTestListener implements ITestListener {
    //用例执行结束后，用例执行成功时调用
    public void onTestSuccess(ITestResult tr) {
        logTestEnd(tr, "Success");
    }

    //用例执行结束后，用例执行失败时调用
    public void onTestFailure(ITestResult tr) {
        logTestEnd(tr, "Failed");
    }

    //用例执行结束后，用例执行skip时调用
    public void onTestSkipped(ITestResult tr) {
        logTestEnd(tr, "Skipped");
    }

    //每次方法失败但是已经使用successPercentage进行注释时调用，并且此失败仍保留在请求的成功百分比之内。
    public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
        logTestEnd(tr, "FailedButWithinSuccessPercentage");
    }

    //每次调用测试@Test之前调用
    public void onTestStart(ITestResult result) {
        logTestStart(result);
    }

    //在测试类被实例化之后调用，并在调用任何配置方法之前调用。
    public void onStart(ITestContext context) {
        return;
    }

    //在所有测试运行之后调用，并且所有的配置方法都被调用
    public void onFinish(ITestContext context) {
        return;
    }

    // 在用例执行结束时，打印用例的执行结果信息
    protected void logTestEnd(ITestResult tr, String result) {
        Reporter.log(String.format("-------------Result: %s-------------", result), true);
    }

    // 在用例开始时，打印用例的一些信息，比如@Test对应的方法名，用例的描述等等
    protected void logTestStart(ITestResult tr) {
        Reporter.log(String.format("-------------Run: %s.%s---------------", tr.getTestClass().getName(), tr.getMethod().getMethodName()), true);
        Reporter.log(String.format("用例描述: %s, 优先级: %s", tr.getMethod().getDescription(), tr.getMethod().getPriority()),true);
        return;
    }
}