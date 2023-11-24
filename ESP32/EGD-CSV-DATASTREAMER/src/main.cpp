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

//Für den Sensor

#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>

Adafruit_MPU6050 mpu;

//Ende

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

/*BLECharacteristic getDataCharacteristic(
  CHARASTERISTIC_UUID,
  BLECharacteristic::PROPERTY_WRITE 
);
*/
/* This function handles server callbacks */
 bool deviceConnected = false;
std::string sendIntinString = "3";
class ServerCallbacks: public BLEServerCallbacks {//
    void onConnect(BLEServer* MyServer) {
     // Serial.println("Waiting for a client to connect....");
      deviceConnected = true;

      byte bytess[sendIntinString.size()];

      for (size_t i = 0; i < sendIntinString.size(); i++) {
          sendIntinString = "3";
          bytess[i] = sendIntinString[i];
          customCharacteristic.setValue(bytess, 1);
          customCharacteristic.notify();
          
      }

    };
    void onDisconnect(BLEServer* MyServer) {
      deviceConnected = false;
    }
};

#define serviceID BLEUUID((uint16_t)0x1700)


int randomNr; // for sending
BLEServer *MyServer;
TaskHandle_t Task1;
bool accidentHappend = false;

bool axesSet = false; //Wenn die Achsen schon gesetzt sind soll es auf true gesetzt werden

//Um zu wissen welche Richtung für welche Achse zuständig ist
std::string vorneHintenAchseString = "";
std::string obenUntenAchseString = "";
std::string rechtsLinksAchseString = "";


int counterForFirstRow = 0;

void Task1code(void *parameter) {
  const int numValues = 50;    // Anzahl der zu speichernden Werte
  float xAchseArray[numValues]; // Array zur Speicherung der letzten Werte
  float yAchseArray[numValues];
  float zAchseArray[numValues];
  int currentIndex = 0;        // Aktueller Index im Array

  while(true){
      /* Get new sensor events with the readings */
      sensors_event_t a, g, temp;
      mpu.getEvent(&a, &g, &temp);

      /* Print out the values */
     /* Serial.println("Wir sind im Task1Code für den Sensor");
      Serial.println("Wir sind im Kern:");
      Serial.println(xPortGetCoreID());

      Serial.print("Acceleration X: ");
      Serial.print(a.acceleration.x);
      Serial.print(", Y: ");
      Serial.print(a.acceleration.y);
      Serial.print(", Z: ");
      Serial.print(a.acceleration.z);
      Serial.println(" m/s^2");

      Serial.print("Rotation X: ");
      Serial.print(g.gyro.x);
      Serial.print(", Y: ");
      Serial.print(g.gyro.y);
      Serial.print(", Z: ");
      Serial.print(g.gyro.z);
      Serial.println(" rad/s");

      Serial.print("Temperature: ");
      Serial.print(temp.temperature);
      Serial.println(" degC");

      Serial.println("");
*/

      // Bedingungen für die Achsenwerte
      const float threshold = 9.81;  // Schwellenwert für die Y-Achse
      const float percentage = 0.15; // +/- 15% Toleranz

       // Speichern des aktuellen Werts in das Array
      xAchseArray[currentIndex] = a.acceleration.x;
      yAchseArray[currentIndex] = a.acceleration.y;
      zAchseArray[currentIndex] = a.acceleration.z;
      currentIndex = (currentIndex + 1) % numValues; // Aktualisierung des Indexes, um den Array-Ringpuffer zu nutzen


      

      
      // Speichern der Achsenwerte in Variablen
      float obenUnten = 0;
      float vorneHinten = 0;
      float rechtsLinks = 0;


      // Vergleich der Werte im Array, um rauszufinden welche achse nach vorne und welche nach rechts und liks fährt
      float vorne = 0;
      float hinten = 0;
      float xGesamt = 0;
      float yGesamt = 0;
      float zGesamt = 0;


      
      
      if(axesSet==false)
      {
        
        if (a.acceleration.y >= (threshold * (1.0 - percentage)) && a.acceleration.y <= (threshold * (1.0 + percentage))) {
        // Wert der Y-Achse liegt im gewünschten Bereich das heißt es ist für oben und unten
        obenUntenAchseString = "yAchse";





        for (int i = 0; i < numValues; i++) {
          if (zAchseArray[i] > 0) 
          {
            vorne += zAchseArray[i];
          } else 
          {
            hinten += zAchseArray[i];
          }
        }
        zGesamt = vorne + (hinten * (-1));
        vorne = 0;
        hinten = 0;
        for (int i = 0; i < numValues; i++) 
        {
          if (xAchseArray[i] > 0) {
            vorne += xAchseArray[i];
          } else {
            hinten += xAchseArray[i];
          }
        }
        xGesamt = vorne + (hinten * (-1));

        double difference = (zGesamt > xGesamt) ? (zGesamt - xGesamt) : (xGesamt - zGesamt);

/*
        Serial.print("X Gesamt = ");
        Serial.println(xGesamt);
        Serial.print("Z Gesamt = ");
        Serial.println(zGesamt);
        

        Serial.print("Diffrence between tow: ");
        Serial.println(difference);
*/
        //Wenn der Unterschie xGesamt und yGesamt nicht zu groß ist dann hat das Auto wahrscheinlich noch nicht gestartet
        //Wenn trotzdem xGesamt viel größer ist als yGesamt dann ist wahrscheinlich die xAchsen zuständig für Vorne und hinten.
        if(axesSet==false)
        {
          if (difference>30  && zGesamt > xGesamt)
          {
            vorneHintenAchseString = "zAchse";
            rechtsLinksAchseString = "xAchse";
            axesSet = true;
          }
          else if(difference>30  && zGesamt < xGesamt)
          {
            vorneHintenAchseString = "xAchse";
            rechtsLinksAchseString = "zAchse";
            axesSet = true;
          }
        }
        
      }

      // Überprüfen der X-Achse
      else if (a.acceleration.x >= (threshold * (1.0 - percentage)) && a.acceleration.x <= (threshold * (1.0 + percentage))) {
        // Wert der X-Achse liegt im gewünschten das heißt es ist für oben und unten
        obenUntenAchseString = "xAchse";


        for (int i = 0; i < numValues; i++) {
          if (zAchseArray[i] > 0) 
          {
            vorne += zAchseArray[i];
          } else 
          {
            hinten += zAchseArray[i];
          }
        }
        zGesamt = vorne + (hinten * (-1));
        vorne = 0;
        hinten = 0;
        for (int i = 0; i < numValues; i++) 
        {
          if (yAchseArray[i] > 0) {
            vorne += yAchseArray[i];
          } else {
            hinten += yAchseArray[i];
          }
        }
        yGesamt = vorne + (hinten * (-1));

        double difference = (zGesamt > yGesamt) ? (zGesamt - yGesamt) : (yGesamt - zGesamt);

/*
        Serial.print("Y Gesamt = ");
        Serial.println(yGesamt);
        Serial.print("Z Gesamt = ");
        Serial.println(zGesamt);
        
        Serial.print("Diffrence between tow: ");
        Serial.println(difference);

    */    
        


        //Wenn der Unterschie xGesamt und yGesamt nicht zu groß ist dann hat das Auto wahrscheinlich noch nicht gestartet
        //Wenn trotzdem xGesamt viel größer ist als yGesamt dann ist wahrscheinlich die xAchsen zuständig für Vorne und hinten.
        if(axesSet==false)
        {
          if (difference>30  && zGesamt > yGesamt)
          {
            vorneHintenAchseString = "zAchse";
            rechtsLinksAchseString = "yAchse";
            axesSet = true;
          }
          else if(difference>30  && zGesamt < yGesamt)
          {
            vorneHintenAchseString = "yAchse";
            rechtsLinksAchseString = "zAchse";
            axesSet = true;
          }
        }
        
        
      }

      // Überprüfen der Z-Achse
      else if (a.acceleration.z >= (threshold * (1.0 - percentage)) && a.acceleration.z <= (threshold * (1.0 + percentage))) {
        // Wert der Z-Achse liegt im gewünschten Bereich das heißt es ist für oben und unten
        rechtsLinksAchseString = "zAchse";

        for (int i = 0; i < numValues; i++) 
        {
          if (xAchseArray[i] > 0) {
            vorne += xAchseArray[i];
          } else {
            hinten += xAchseArray[i];
          }
        }
        xGesamt = vorne + (hinten * (-1));
        vorne = 0;
        hinten = 0;
        for (int i = 0; i < numValues; i++) {
          if (yAchseArray[i] > 0) {
            vorne += yAchseArray[i];
          } else {
            hinten += yAchseArray[i];
          }
        }
        yGesamt = vorne + (hinten * (-1));

       /* Serial.print("Y Gesamt = ");
        Serial.println(yGesamt);
        Serial.print("X Gesamt = ");
        Serial.println(xGesamt);
        Serial.print("Diffrence between tow: ");
        Serial.println(difference);
        */

        double difference = (xGesamt > yGesamt) ? (xGesamt - yGesamt) : (yGesamt - xGesamt);
        //Wenn der Unterschie xGesamt und yGesamt nicht zu groß ist dann hat das Auto wahrscheinlich noch nicht gestartet
        //Wenn trotzdem xGesamt viel größer ist als yGesamt dann ist wahrscheinlich die xAchsen zuständig für Vorne und hinten.
        if(axesSet==false)
        {
          if (difference>30  && xGesamt > yGesamt)
          {
            vorneHintenAchseString = "xAchse";
            rechtsLinksAchseString = "yAchse";
            axesSet = true;
          }
          else if(difference>30  && xGesamt < yGesamt)
          {
            vorneHintenAchseString = "yAchse";
            rechtsLinksAchseString = "xAchse";
            axesSet = true;
          }
        }
        
        
      }
      }
      // Überprüfen der Y-Achse
      

      
      // Ausgabe des ermittelten Werts

      if (axesSet == true)
      {
        if(counterForFirstRow == 0)
        {
          Serial.print("Acceleration V/H: ");
          Serial.print(vorneHintenAchseString.c_str());
          Serial.print(",");


          Serial.print("R/L: ");
          Serial.print(rechtsLinksAchseString.c_str());
          Serial.print(",");

          Serial.print("O/U: ");
          Serial.print(obenUntenAchseString.c_str());
          Serial.print(",");

          Serial.println(); Serial.println();




          Serial.println("Acceleration (m/s^2) X, Acceleration (m/s^2) Y, Acceleration (m/s^2) Z, Rotation (rad/s) X, Rotation (rad/s) Y, Rotation (rad/s) Z, Temperature (degc)");

          counterForFirstRow++;
        }
        
        Serial.print(a.acceleration.x);
        Serial.print(",");
        Serial.print(a.acceleration.y);
        Serial.print(",");
        Serial.print(a.acceleration.z);
        Serial.print(",");

        //Rotation gyroscope
        Serial.print(g.gyro.x);
        Serial.print(",");
        Serial.print(g.gyro.y);
        Serial.print(",");
        Serial.print(g.gyro.z);
        Serial.print(",");

        Serial.print(temp.temperature);
        Serial.println();

        if(a.acceleration.x > 20 || a.acceleration.x < 0){
          sendIntinString = "1";
        }
      }

      //delay(1000);
  }
}

void setup() {
  Serial.begin(115200);

//Sensor Setup
while (!Serial)
    delay(10); // will pause Zero, Leonardo, etc until serial console opens

  //Serial.println("Adafruit MPU6050 test!");

  // Try to initialize!
  if (!mpu.begin()) {
   // Serial.println("Failed to find MPU6050 chip");
    while (1) {
      delay(10);
    }
  }
 // Serial.println("MPU6050 Found!");

  mpu.setAccelerometerRange(MPU6050_RANGE_8_G);
  //Serial.print("Accelerometer range set to: ");
  switch (mpu.getAccelerometerRange()) {
  case MPU6050_RANGE_2_G:
    //Serial.println("+-2G");
    break;
  case MPU6050_RANGE_4_G:
    //Serial.println("+-4G");
    break;
  case MPU6050_RANGE_8_G:
    //Serial.println("+-8G");
    break;
  case MPU6050_RANGE_16_G:
    //Serial.println("+-16G");
    break;
  }
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  //Serial.print("Gyro range set to: ");
  switch (mpu.getGyroRange()) {
  case MPU6050_RANGE_250_DEG:
    //Serial.println("+- 250 deg/s");
    break;
  case MPU6050_RANGE_500_DEG:
    //Serial.println("+- 500 deg/s");
    break;
  case MPU6050_RANGE_1000_DEG:
    //Serial.println("+- 1000 deg/s");
    break;
  case MPU6050_RANGE_2000_DEG:
    //Serial.println("+- 2000 deg/s");
    break;
  }

  mpu.setFilterBandwidth(MPU6050_BAND_5_HZ);
  //Serial.print("Filter bandwidth set to: ");
  switch (mpu.getFilterBandwidth()) {
  case MPU6050_BAND_260_HZ:
    //Serial.println("260 Hz");
    break;
  case MPU6050_BAND_184_HZ:
    //Serial.println("184 Hz");
    break;
  case MPU6050_BAND_94_HZ:
    //Serial.println("94 Hz");
    break;
  case MPU6050_BAND_44_HZ:
    //Serial.println("44 Hz");
    break;
  case MPU6050_BAND_21_HZ:
    //Serial.println("21 Hz");
    break;
  case MPU6050_BAND_10_HZ:
    //Serial.println("10 Hz");
    break;
  case MPU6050_BAND_5_HZ:
    //Serial.println("5 Hz");
    break;
  }

  //Serial.println("");

//Sensor ende

  //Serial.println("Wir sind im Setup");
  //für den Led
  pinMode(LED, OUTPUT);


  BLEDevice::init("EGD-SOS"); // Name your BLE Device

  MyServer = BLEDevice::createServer();  //Create the BLE Server
  MyServer->setCallbacks(new ServerCallbacks());  // Set the function that handles server callbacks

  BLEService *customService = MyServer->createService(serviceID); // Create the BLE Service
  
  customService->addCharacteristic(&customCharacteristic);  // Create a BLE Characteristic for the notify function

  //customService->addCharacteristic(&getDataCharacteristic);  // Create a BLE for receiving date
//Kern
   // Serial.println("Wir sind im Kern:");

  Serial.println(xPortGetCoreID());



  customCharacteristic.addDescriptor(new BLE2902());  // Need for notify function
  MyServer->getAdvertising()->addServiceUUID(serviceID);  // Configure Advertising
  customService->start(); // Start the service  

  MyServer->getAdvertising()->start();  // Start the server/advertising

 // Serial.println("Waiting for a client to connect....");


  //auf den 0ten kern
  xTaskCreatePinnedToCore(
    Task1code,   /* Funktion für die Task */
    "Task1",     /* Name der Task */
    10000,       /* Stackgröße in Wörtern */
    NULL,        /* Task-Eingabeparameter */
    0,           /* Priorität der Task */
    &Task1,      /* Task-Handle */
    0            /* Kern, auf dem die Task ausgeführt werden soll */
  );

}



void loop() {
  //Sensor

  

  
  //BLE


//Kern

/*
  Serial.println("Wir sind im Loop");
  Serial.println(",");
  Serial.println("Wir sind im Kern:");
  Serial.println(xPortGetCoreID());

  Serial.println("Anzahl der vebundenen Personen:");
  Serial.println(MyServer->getConnectedCount());
*/

  MyServer->getAdvertising()->start();  // Start the server/advertising

 // Serial.println("Advertising erfolgreich....");

  if(deviceConnected==false){

    
    digitalWrite(LED, HIGH);
    //Led is on
    delay(500);
    digitalWrite(LED, LOW);
   // Serial.println("Waiting for a client to connect....");

    //Led is off
    delay(500);

  } else {

    digitalWrite(LED, HIGH);
    //Serial.println("Wir sind beim Sendprozess....");

    randomNr = random(2, 9);
    byte bytes[sendIntinString.size()];

    if(sendIntinString == "1"){
     // sendIntinString = std::to_string(randomNr);

      for (size_t i = 0; i < sendIntinString.size(); i++) {
          bytes[i] = sendIntinString[i];
          customCharacteristic.setValue(bytes, 1);
          customCharacteristic.notify();
          sendIntinString = "3";
      }
    }
  

    
    

    /*for (size_t i = 0; i < sendIntinString.size(); i++) {
        bytes[i] = sendIntinString[i];
        sendIntinString = "3";
    }
   
    customCharacteristic.setValue(bytes, 1);
    customCharacteristic.notify();
  */
    delay(1000);
  }

}