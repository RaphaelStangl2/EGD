#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>
#include <Arduino.h>
#include <iostream>
#include <cstddef>
#include <cstring>
#include <algorithm>
#include <Adafruit_BusIO_Register.h>

class BLEConnection {
  public:
    void connection();
    void init();
    bool isConnected();
    void sendValue(int value);

  private:
    BLEServer* server;
    BLECharacteristic* characteristic;
    bool connected;
};

void BLEConnection::connection() {
  connected = false;
}

void BLEConnection::init() {
  BLEDevice::init("EGD-SOS");
  server = BLEDevice::createServer();
  server->setCallbacks(new BLEServerCallbacks());

  BLEService* service = server->createService(BLEUUID((uint16_t)0x1700));
  characteristic = service->createCharacteristic(
    BLEUUID((uint16_t)0x1701),
    BLECharacteristic::PROPERTY_READ | BLECharacteristic::PROPERTY_NOTIFY
  );
  characteristic->addDescriptor(new BLE2902());
  service->start();
  server->getAdvertising()->addServiceUUID(BLEUUID((uint16_t)0x1700));
  server->getAdvertising()->start();
}

bool BLEConnection::isConnected() {
  return connected;
}

void BLEConnection::sendValue(int value) {
  std::string valueString = std::to_string(value);
  uint8_t* valueBytes = new uint8_t[valueString.length()];
  std::copy(valueString.begin(), valueString.end(), valueBytes);
  characteristic->setValue(valueBytes, valueString.length());
  characteristic->notify();
  delete[] valueBytes;
}
