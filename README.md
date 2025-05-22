> [!IMPORTANT]
> Der letzte reviewer ist für das Mergen verantwortlich. Regelmäßige überprüfungen wären angemessen um Konflikte zu vermeiden!

# Codestil
### **Kommentare:**
Unsere Kommentare ordnen wir nach Author, Erstellungsdatum, Beschreibung und zuletzt geupdatet. Dies fügen wir zum Anfang hinzu, um sofortige einsicht zu haben was wo hinzugefügt wurde und warum.

Beispiel:
```java
/**
 ** @Author: C M
 ** @Author: M K K
 ** @Author: M P
 ** @Author: H W
 ** @Date: 07.04.2025
 *
 ** @Description: Diese Klasse verwaltet die HTTP-Anfragen für die Lehrer-Entität.
 *
 ** @Last Update: 08.04.2025
 ** @Last Update by: C M
 ** @Reason: Kommentar hinzugefügt
 */
```

### **Methoden:**
Unsere Methoden schreiben wir im Camel Case

Beispiel:

```java
private void foo(int foo){
	while(true){
	DoSomething(foo);
	}
}
```

```java
private void fooDoo(String foo, Long doo){
	if(foo == "?" && doo == 1){
		DoSomething();
	}
	
	return;
}
```

### **Attribute**
Unsere Attribute schreiben wir ebenfalls im Camel Case

Beispiel:
```java
Long someNameHere = 0;
String name = "x"; 
```



