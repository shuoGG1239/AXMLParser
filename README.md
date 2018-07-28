# AXMLParser
对加密后的AndroidManifest.xml进行decode, 修改自AXMLPrinter项目, 暂时仅对其进行封装, 简化了API
<br>

# API
```java
AxmlUtil.parse(String fileUrl);
AxmlUtil.parse(InputStream in);
AxmlUtil.parse(byte[] in);
```
<br>

# Example
```java
public static void main(String[] args) {
    String axmlPath = "./resource/AndroidManifest.xml";
    String content = AxmlUtil.parse(axmlPath);
    System.out.println(content);
}
```
<br>
