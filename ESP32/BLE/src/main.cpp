#include <BLEDevice.h>
#include <BLEServer.h>
#include <BLEUtils.h>
#include <BLE2902.h>
#include <Arduino.h>
#include <iostream>
#include <cstddef>
#include <cstring>
#include <algorithm>



using namespace std;


/* four data types being converted to char array */

int sentIntger = 1;

#define LED 2
#define SERVICE_UUID "1688a0c8-6cc9-11ed-a1eb-0242ac120002"
#define CHARASTERISTIC_UUID "1688a0c8-6cc9-11ed-a1eb-0242ac120002"

/* define the characteristic and it's propeties */
BLECharacteristic customCharacteristic(
  SERVICE_UUID,
  BLECharacteristic::PROPERTY_READ |
  BLECharacteristic::PROPERTY_NOTIFY
);

BLECharacteristic getDataCharacteristic(
  CHARASTERISTIC_UUID,
  BLECharacteristic::PROPERTY_WRITE 
);

/* This function handles server callbacks */
bool deviceConnected = false;

class ServerCallbacks: public BLEServerCallbacks {//
    void onConnect(BLEServer* MyServer) {
      Serial.println("Waiting for a client to connect....");
      deviceConnected = true;
    };
    void onDisconnect(BLEServer* MyServer) {
      deviceConnected = false;
    }
};



class MyReceiveCallbacks: public BLECharacteristicCallbacks { //charis. (socket) for getting data
    
    void onWrite(BLECharacteristic* rCharacteristc) {// when client (raphi) sends a request

      Serial.println("is in onWrite");
      std: string phoneValue = rCharacteristc->getValue();

        Serial.println("phonValue = " + phoneValue.length());
        if (phoneValue.length() > 0)
        {
          Serial.println("Start Recieving from");
          Serial.print("Data: ");

          for (int i = 0; i < phoneValue.length(); i++)
          {
            Serial.print(phoneValue[i]);
          }
          Serial.println();
          Serial.println("End Suiiiiiiiiiiiii");
      }
    };
   
   void onRead(BLECharacteristic* rCharacteristc) {// when client (raphi) sends a request

      Serial.println("is in onRead");
      std: string phoneValue = rCharacteristc->getValue();

        Serial.println("phonValue = " + phoneValue.length());
        if (phoneValue.length() > 0)
        {
          Serial.println("Start Recieving from");
          Serial.print("Data: ");

          for (int i = 0; i < phoneValue.length(); i++)
          {
            Serial.print(phoneValue[i]);
          }
          Serial.println();
          Serial.println("End Suiiiiiiiiiiiii");
      }
    };
};


/* define the UUID that our custom service will use */
#define serviceID BLEUUID((uint16_t)0x1700)


int randomNr; // for sending

void setup() {
  Serial.begin(921600);

  //für den Led
  pinMode(LED, OUTPUT);


  BLEDevice::init("EGD-SOS"); // Name your BLE Device

  BLEServer *MyServer = BLEDevice::createServer();  //Create the BLE Server
  MyServer->setCallbacks(new ServerCallbacks());  // Set the function that handles server callbacks

  BLEService *customService = MyServer->createService(serviceID); // Create the BLE Service
  
  customService->addCharacteristic(&customCharacteristic);  // Create a BLE Characteristic for the notify function

  customService->addCharacteristic(&getDataCharacteristic);  // Create a BLE for receiving date



  customCharacteristic.addDescriptor(new BLE2902());  // Need for notify function
  MyServer->getAdvertising()->addServiceUUID(serviceID);  // Configure Advertising
  customService->start(); // Start the service  

  MyServer->getAdvertising()->start();  // Start the server/advertising

  Serial.println("Waiting for a client to connect....");
}

void loop() {
  

  if(deviceConnected==false){

    digitalWrite(LED, HIGH);
    //Led is on
    delay(500);
    digitalWrite(LED, LOW);
    Serial.println("Waiting for a client to connect....");

    //Led is off
    delay(500);

  } else {

    digitalWrite(LED, HIGH);

    randomNr = random(1, 9);
    std::string sendIntinString = std::to_string(randomNr);

    byte bytes[sendIntinString.size()];
    

    for (size_t i = 0; i < sendIntinString.size(); i++) {
        bytes[i] = sendIntinString[i];
    }
   
    customCharacteristic.setValue(bytes, 1);
    customCharacteristic.notify();
  }
  delay(500);
}