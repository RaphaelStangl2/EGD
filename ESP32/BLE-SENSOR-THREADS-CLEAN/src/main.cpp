#include "BLEOutputHandler.cpp"

BLEOutputHandler bleOt;

void setup() {
  bleOt.init();
}

void loop() {
  bleOt.loop();
}
