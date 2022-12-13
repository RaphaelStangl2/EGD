#include <Arduino.h>

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(921600);
  Serial.println("Sui from Setup");
}

void loop() {
  delay(1000);
  digitalWrite(LED_BUILTIN, HIGH);
  Serial.println("Sui from loop");
  delay(1000);
  
}