#include "BLEConnection.cpp"


class BLEOutputHandler {
  public:
    void Main();

    void init();
    void loop();

  private:
    BLEConnection ble;
    int ledPin;
};

void BLEOutputHandler::Main() {
  ledPin = 2;
}

void BLEOutputHandler::init() {
  Serial.begin(921600);
  pinMode(ledPin, OUTPUT);
  ble.init();
}

void BLEOutputHandler::loop() {
  if (!ble.isConnected()) {
    digitalWrite(ledPin, HIGH);
    Serial.println("Waiting for a client to connect....");
  } else {
    digitalWrite(ledPin, HIGH);
    int value = random(1, 9);
    ble.sendValue(value);
  }
  delay(4000);
}
