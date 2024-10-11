Java Type Generator Utility



Aşağıdaki gibi bir text dosya içerisinde bir sınıfın ait olacağı paket ismi, sınıfın ismi ve private property’leri verildiğinde bu bilgilerden sınıfın Java kaynak kodunu oluşturan bir utility program geliştiriniz. Oluşturulan kaynak kodunda  property’lerin getter/setter,equals ve hashCode metotları da yer almalıdır. Equals ve hashCode metotlarında yer alacak property’lerin sağında bir * karakteri yer alacaktır.

```java
package com.example.model
class Person
Integer age
String name
String email *
```

Yine benzer biçimde bu utility uygulamaya bir text dosya içerisinde belirli bir pakette yaratılması istenen interface’in tanımı verilecektir. Bu tanımda interface’de yer alacak metotlar, return tipi, metot ismi ve parametere isim ve tipleri şeklinde tanımlanmış olacaktır.

```java
package com.example.util
interface Calculator
Integer add Integer operand1 Integer operand2
Integer subtract Integer operand1 Integer operand2
```

Bu utility geliştirilirken JUnit dışında herhangi bir 3rd party kütüphane kullanılmamalıdır. Geliştirme sadece core Java kütüphaneleri kullanılarak yapılmalıdır.

raw lines of string --> transform into --> structured lines of string --> transform into source code structure --> transform into source code

String rawInput = "";
InputSource is = new InputSource(rawInput);
SourceCodeStructure sc = new SourceCodeStructure(is);
return sc.toJavaSource();

IO API & Decorator pattern

InputStream is = new BufferedInputStream(new FileInputStream(new File("x.txt")));
is.read()