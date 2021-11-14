# auto项目简介
  该项目为简略自动化框架，包含Web、Api、Mobile自动化基础代码。使用maven进行管理，编程语言为java，Web使用selenium、api使用httpClient、mobile使用appium自动化，测试框架使用testng。
  项目默认只运行web、api测试
# 安装
## 测试用例运行
```
mvn test
```
# 项目目录简介
```
auto
│   README.md   
│
└─── src/main/resources/ 参数获取、测试数据  
│
└─── src/main/java/com/auto/
│    │  *util 工具类
│    │  *page 页面类，封装特定页面的特定方法
│    │  basePage 自动化基础动作封装
│    │  
└─── test
│    │  
│    └─── com/auto/testcase/
│    │    └─── web web测试用例
│    │    └─── api api测试用例
│    │    └─── app app测试用例
│    │   
│    └─── com/auto/xml/
│        │ testng.xml 总测试
│        │ web-test.xml web测试
│        │ api-test.xml api测试
│        │ app-test.xml app测试
│
└─── test-output
│    │ report.html 总测试报告
...
│   

```
