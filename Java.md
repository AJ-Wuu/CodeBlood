# Java SE 11
## How Java Works
* Source code -> plain text -> .java
* Compile into byte code -> .class **for JVM**
* Java Virtual Machine must be installed on a target computer
## Class & Object
* Java code is structured with CLASS, which represents a concept and defines attributes it can store & operations and algorithms it is capable of
* Object is a specific instance of a class, which can have specific values for each attribute, invoke operations at run time, and be referenced using a variable of a relevant type
## Inheritance
* Superclass is more general, subclass is more specific
* Subclass must include all attributes in the superclass, but it could define more
## Name should not start with number characters (0~9), underscore \_ or dollar sign $
## StringBuilder (java.lang.StringBuilder)
## Wrapper Classes for Primitives
* Construct wrapper object out of primitive or string using the ```valueof()``` method
* Extract primitive values out of the wrapper using the ```xxxValue()``` method
* Auto-boxing & Auto-unboxing
* Create wrapper or primitive out of the string using the ```parseXXX()``` method
* Covert a primitive to a string using the ```String.valueOf()``` method
* Wrapper classes provide constants for each type, such as ```Integer.MIN_VALUE```
## BigDecimal (java.math.BigDecimal) -> exact precision
* Parse Numerical Values (java.text.NumberFormat) -> NumberFormat.parse()
## Local Date and Time
* LocalDateTime.of(year, month, day, hours, minutes, seconds, nanoseconds)
* LocalDate.of(year, month, day)
* LocalTime.of(hours, minutes, seconds, nanoseconds)
* someDay.atTime(someTime), someDateTime.toLocalDate(), someDateTime.toLocalTime()
* .plus(XXX), .minus(XXX), .withXXX(), isBefore(), isAfter()
* Format and Parse Date and Time (java.time.format.DateTimeFormatter)
* Instants (java.time.Instant), Durations (java.time.Duration), Periods (java.time.Period)
* Zoned Date and Time (java.time.ZonedDateTime)
* Locale (java.util.Locale)
## Unified Modelling Language
