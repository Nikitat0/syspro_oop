#!/usr/bin/env sh

SRC=src/main/java/ru/nikitat0/heapsort/Main.java

javac -d gradleless $SRC
javadoc -d gradleless/docs $SRC
java -classpath gradleless ru.nikitat0.heapsort.Main
