# AXMLParser
对加密后的AndroidManifest.xml进行decode, 修改自AXMLPrinter项目, 暂时仅对其进行封装, 简化了API
<br>

# API
* 直接使用AxmlUtil下的静态方法即可
```java
String parse(String fileUrl);
String parse(InputStream in);
String parse(byte[] in);
```
<br>

# Example
```java
public static void main(String[] args) {
    String axmlPath = "./resource/AndroidManifest.xml";
    String content = AxmlUtil.parse(axmlPath); // content即为明文
    System.out.println(content);
}
```
<br>
