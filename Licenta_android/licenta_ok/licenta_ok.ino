
#include "EEPROM.h"

#define D1 11    // directia de rotatie a motorului stang
#define M1 6     // PWM motor stanga
#define D2 5     // directia de rotatie a motorului drept
#define M2 3     // PWM motor dreapta


#define comandaStanga 'L'      // comanda UART pentru motorul stang
#define comandaDreapta 'R'     // comanda UART pentru motorul drept
#define comandaFetch 'F'       // comanda UART pentru operatie EEPROM
#define comandaRead 'r'        // comanda UART pentru operatia de read EEPROM
#define comandaWrite 'w'       // comanda UART pentru operatia de write EEPROM

char dataReceived;             // datele primite prin bluetooth

char dataMotorStanga[4];       // array pentru motorul stang
byte indexStanga = 0;          // index
char dataMotorDreapta[4];      // array pentru motorul stang
byte indexDreapta = 0;         // index 
char dataFetch[8];             // array pentru EEPROM
byte indexFetch = 0;           // index
char comanda;                  // comanda

unsigned long timpulCurent, timpulUltimeiComenzi, autoOFF;

void setup() {
  Serial.begin(9600);          // initializare UART
  pinMode(D1, OUTPUT);         // setare ca OUTPUT pin-ul directiei motorului stang
  pinMode(D2, OUTPUT);         // setare ca OUTPUT pin-ul directiei motorului drept


  timer_init();                // initializarea timer-ului 
}

void timer_init() {
  uint8_t sw_autoOFF = EEPROM.read(0);   // citire EEPROM
  if(sw_autoOFF == '1'){                 
    char data[3];
    data[0] = EEPROM.read(1);
    data[1] = EEPROM.read(2);
    data[2] = EEPROM.read(3);
    autoOFF = atoi(data)*100;        
  }
  else if(sw_autoOFF == '0'){         
    autoOFF = 999999;
  } 
  else if(sw_autoOFF == 255){ 
    autoOFF = 2500;                      // daca memoria EEPROm este goala, seteaza timpul la 2.5 s
  } 
timpulCurent = millis();               // citeste timpul scurs de la pornirea aplicatiei
}
 
void loop() {

  if (Serial.available() > 0) {                              // daca se primesc date de la UART
    dataReceived = Serial.read();                            // citeste datele
    if(dataReceived == comandaStanga) {           
      comanda = comandaStanga;                   
      memset(dataMotorStanga,0,sizeof(dataMotorStanga));     // goleste array
      indexStanga = 0;                                       // resetare array
    }
    else if(dataReceived == comandaDreapta) {      
      comanda = comandaDreapta;
      memset(dataMotorDreapta,0,sizeof(dataMotorDreapta));
      indexDreapta = 0;
    }   
    else if(dataReceived == comandaFetch) {      
      comanda = comandaFetch;
      memset(dataFetch,0,sizeof(dataFetch));
      indexFetch = 0;
    }
    else if(dataReceived == '\r') comanda = 'e';             // sfarsit de linie UART
    else if(dataReceived == '\t') comanda = 't';             // sfarsit de linie EEPROM
    
    if(comanda == comandaStanga && dataReceived != comandaStanga){
      dataMotorStanga[indexStanga] = dataReceived;              
      indexStanga++;                                         // incrementare array
    }
    else if(comanda == comandaDreapta && dataReceived != comandaDreapta){
      dataMotorDreapta[indexDreapta] = dataReceived;
      indexDreapta++;
    }   
    else if(comanda == comandaFetch && dataReceived != comandaFetch){
      dataFetch[indexFetch] = dataReceived;
      indexFetch++;
    }    
    else if(comanda == 'e'){                                  // sfarsit de linie UART
      controlCar(atoi(dataMotorStanga),atoi(dataMotorDreapta));
      delay(10);
    }
    else if(comanda == 't'){                                   //sfarsit de linie EEPROM
      operatie(dataFetch[0],dataFetch[1],dataFetch[2],dataFetch[3],dataFetch[4]);
    }
    timpulUltimeiComenzi = millis();                          // timpul scurs de la pornirea aplicatiei
  }
  if(millis() >= (timpulUltimeiComenzi + autoOFF)){     
    controlCar(0,0);                             
  }
}
void controlCar(int motorStanga, int motorDreapta){
  
  bool directiaStanga, directiaDreapta;                      // directia de rotatie a motoarelor
  byte valoareMotorStanga, valoareMotorDreapta;              // PWM (0-255)
  
  if(motorStanga > 0){
    valoareMotorStanga = motorStanga;
    directiaStanga = 0;
  }
  else if(motorStanga < 0){
    valoareMotorStanga = 255 - abs(motorStanga);
    directiaStanga = 1;
  }
  else {
    directiaStanga = 0;
    valoareMotorStanga = 0;
  }
 
  if(motorDreapta > 0){
    valoareMotorDreapta = motorDreapta;
    directiaDreapta = 0;
  }
  else if(motorDreapta < 0){
    valoareMotorDreapta = 255 - abs(motorDreapta);
    directiaDreapta = 1;
  }
  else {
    directiaDreapta = 0;
    valoareMotorDreapta = 0;
  }
   
  analogWrite(M1, valoareMotorStanga);            
  analogWrite(M2, valoareMotorDreapta);           
  digitalWrite(D1, directiaStanga);       
  digitalWrite(D2, directiaDreapta);       

}

void operatie(char comFetch, uint8_t a1, uint8_t a2, uint8_t a3, uint8_t a4){

  if(comFetch == comandaRead){		      // comanda read
    Serial.print("Fetch Data:");	      // send EEPROM data
    Serial.write(EEPROM.read(0));             // citeste valoarea din memorie de la adresa 0 si salveaza in UARTr
    Serial.write(EEPROM.read(1));
    Serial.write(EEPROM.read(2));
    Serial.write(EEPROM.read(3));
    Serial.print("\r\n");	              // marcare sfarsit transmisie 
  }
  else if(comFetch == comandaWrite){	      // comanda write
    EEPROM.write(0,a1);               
    EEPROM.write(1,a2);
    EEPROM.write(2,a3);
    EEPROM.write(3,a4);
    timer_init();		              // reinitialiare timer
    Serial.print("succes \r\n");	      
  }

}

